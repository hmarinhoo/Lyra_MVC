package br.fiap.com.br.lyra.service;

import br.fiap.com.br.lyra.config.RabbitConfig;
import br.fiap.com.br.lyra.model.CareerTrail;
import br.fiap.com.br.lyra.model.Quiz;
import br.fiap.com.br.lyra.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageConsumer {

    private final QuizRepository quizRepository;
    private final AIService aiService;
    private final CareerTrailService careerTrailService;
    private final RabbitTemplate rabbitTemplate;

    public MessageConsumer(
            QuizRepository quizRepository,
            AIService aiService,
            CareerTrailService careerTrailService,
            RabbitTemplate rabbitTemplate
    ) {
        this.quizRepository = quizRepository;
        this.aiService = aiService;
        this.careerTrailService = careerTrailService;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Escuta a fila definida em RabbitConfig.TRAIL_QUEUE
     */
    @Transactional
    @RabbitListener(queues = RabbitConfig.TRAIL_QUEUE)
    public void processQuiz(Long quizId) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElse(null);

        if (quiz == null) {
            System.err.println("Mensagem inválida: Quiz não encontrado. ID: " + quizId);
            return; // apenas ignora, não reenvia
        }

        try {
            // Usa o JSON de respostas como contexto/prompt para a IA
            String aiResponse = aiService.generateCareerTrail(quiz.getAnswersJson());

            CareerTrail trail = CareerTrail.builder()
                    .user(quiz.getUser())
                    .content(aiResponse)
                    .createdAt(Instant.now())
                    .build();

            careerTrailService.save(trail);

            System.out.println("Trilha de carreira gerada com sucesso para quiz ID: " + quizId);

        } catch (Exception e) {
            // Aqui você captura erros da IA, como quota excedida
            System.err.println("Erro ao processar quiz ID " + quizId + ": " + e.getMessage());
            System.err.println("A mensagem NÃO será reencaminhada para evitar loop.");

            // opcional: envia manualmente para DLQ se quiser monitoramento
            rabbitTemplate.convertAndSend(
                    RabbitConfig.DLX,
                    RabbitConfig.DLQ_ROUTING,
                    quizId
            );
        }
    }
}
