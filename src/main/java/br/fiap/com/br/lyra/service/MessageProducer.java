package br.fiap.com.br.lyra.service;

import br.fiap.com.br.lyra.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Envia o quizId para processamento na fila/exchange configurada em RabbitConfig.
     * Usa exchange + routing key (melhor prática para produção).
     */
    public void sendQuizForProcessing(Long quizId) {
        rabbitTemplate.convertAndSend(RabbitConfig.TRAIL_EXCHANGE, RabbitConfig.TRAIL_ROUTING, quizId);
    }
}
