package br.fiap.com.br.lyra.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    public static final String TRAIL_QUEUE = "trail-generation-queue";
    public static final String TRAIL_EXCHANGE = "trail-exchange";
    public static final String TRAIL_ROUTING = "trail.generate";

    public static final String DLX = "trail-dlx";
    public static final String DLQ = "trail-dead-queue";
    public static final String DLQ_ROUTING = "trail.error";

    // ==========================
    // QUEUE PRINCIPAL (com DLQ + retry limitado)
    // ==========================
    @Bean
    public Queue trailQueue() {

        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DLX);
        args.put("x-dead-letter-routing-key", DLQ_ROUTING);

        // Máximo de redeliveries (ex: 3 tentativas)
        args.put("x-max-length", 10000);
        args.put("x-message-ttl", 30000); // 30s antes de ser reencaminhada
        args.put("x-max-priority", 10);

        return new Queue(TRAIL_QUEUE, true, false, false, args);
    }

    @Bean
    public DirectExchange trailExchange() {
        return new DirectExchange(TRAIL_EXCHANGE);
    }

    @Bean
    public Binding mainBinding() {
        return BindingBuilder.bind(trailQueue())
                .to(trailExchange())
                .with(TRAIL_ROUTING);
    }

    // ==========================
    // DEAD LETTER QUEUE
    // ==========================
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX);
    }

    @Bean 
    public Queue deadLetterQueue() {
        return new Queue(DLQ, true);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DLQ_ROUTING);
    }
}
