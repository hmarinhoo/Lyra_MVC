package br.fiap.com.br.lyra.service;

import br.fiap.com.br.lyra.config.RabbitConfig;
import br.fiap.com.br.lyra.model.CareerTrail;
import br.fiap.com.br.lyra.model.Quiz;
import br.fiap.com.br.lyra.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageConsumer {

    private final QuizRepository quizRepository;
    private final AIService aiService;
    private final CareerTrailService careerTrailService;

    public MessageConsumer(
            QuizRepository quizRepository,
            AIService aiService,
            CareerTrailService careerTrailService
    ) {
        this.quizRepository = quizRepository;
        this.aiService = aiService;
        this.careerTrailService = careerTrailService;
    }

    /**
     * Escuta a fila definida em RabbitConfig.TRAIL_QUEUE
     */
    @Transactional
    @RabbitListener(queues = RabbitConfig.TRAIL_QUEUE)
    public void processQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        // Usa o JSON de respostas como contexto/prompt para a IA
        String aiResponse = aiService.generateCareerTrail(quiz.getAnswersJson());

        CareerTrail trail = CareerTrail.builder()
                .user(quiz.getUser())
                .content(aiResponse)
                .createdAt(Instant.now())
                .build();

        careerTrailService.save(trail);
    }
}
