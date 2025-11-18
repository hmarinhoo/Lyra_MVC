package br.fiap.com.br.lyra.messaging;

import br.fiap.com.br.lyra.dto.GenerationRequest;
import br.fiap.com.br.lyra.model.Quiz;
import br.fiap.com.br.lyra.repository.QuizRepository;
import br.fiap.com.br.lyra.service.impl.QuizServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TrailMessageListener {

    private final QuizRepository quizRepository;
    private final QuizServiceImpl quizService;

    public TrailMessageListener(QuizRepository quizRepository, QuizServiceImpl quizService) {
        this.quizRepository = quizRepository;
        this.quizService = quizService;
    }

    @RabbitListener(queues = "trail-generation-queue")
    public void handleGenerationRequest(GenerationRequest req) {
        if (req == null || req.getQuizId() == null) return;
        Quiz quiz = quizRepository.findById(req.getQuizId()).orElse(null);
        if (quiz == null) return;
        // generate and persist trail
        quizService.generateAndSaveTrail(quiz);
    }
}
