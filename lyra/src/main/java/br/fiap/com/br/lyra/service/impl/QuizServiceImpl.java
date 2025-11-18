package br.fiap.com.br.lyra.service.impl;

import br.fiap.com.br.lyra.model.CareerTrail;
import br.fiap.com.br.lyra.model.Quiz;
import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.repository.CareerTrailRepository;
import br.fiap.com.br.lyra.repository.QuizRepository;
import br.fiap.com.br.lyra.repository.UserRepository;
import br.fiap.com.br.lyra.service.AIGenerativeService;
import br.fiap.com.br.lyra.service.QuizService;
import br.fiap.com.br.lyra.config.RabbitConfig;
import br.fiap.com.br.lyra.dto.GenerationRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final AIGenerativeService aiService;
    private final CareerTrailRepository trailRepository;
    private final RabbitTemplate rabbitTemplate;

    public QuizServiceImpl(QuizRepository quizRepository, UserRepository userRepository, AIGenerativeService aiService, CareerTrailRepository trailRepository, RabbitTemplate rabbitTemplate) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.aiService = aiService;
        this.trailRepository = trailRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @Transactional
    public Quiz submitQuiz(Long userId, String answersJson) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        Quiz q = new Quiz();
        q.setUser(user);
        q.setAnswersJson(answersJson);

        // Very simple heuristic to detect profile from answers (stub)
        String profile = detectProfileFromAnswers(answersJson);
        q.setProfile(profile);

        Quiz saved = quizRepository.save(q);
            // publish async generation request to RabbitMQ
            publishGenerationRequest(saved);
            return saved;
    }

    @Override
    @Transactional
    public CareerTrail generateAndSaveTrail(Quiz quiz) {
        String profile = quiz.getProfile();
        String context = quiz.getAnswersJson();
        String generated = aiService.generateCareerTrail(profile, context);
        CareerTrail t = new CareerTrail();
        t.setUser(quiz.getUser());
        t.setProfile(profile);
        t.setContent(generated);
        return trailRepository.save(t);
    }

    // publish async generation request
    public void publishGenerationRequest(Quiz quiz) {
        GenerationRequest req = new GenerationRequest(quiz.getId(), quiz.getUser().getId(), quiz.getProfile(), quiz.getAnswersJson());
        rabbitTemplate.convertAndSend(RabbitConfig.TRAIL_EXCHANGE, RabbitConfig.TRAIL_ROUTING, req);
    }

    private String detectProfileFromAnswers(String answersJson) {
        String lower = answersJson == null ? "" : answersJson.toLowerCase();
        if (lower.contains("tecn") || lower.contains("codigo") || lower.contains("program")) return "técnico";
        if (lower.contains("criativ") || lower.contains("design")) return "criativo";
        if (lower.contains("analit") || lower.contains("dados")) return "analítico";
        if (lower.contains("sustent") || lower.contains("ambien")) return "sustentável";
        return "generalista";
    }
}
