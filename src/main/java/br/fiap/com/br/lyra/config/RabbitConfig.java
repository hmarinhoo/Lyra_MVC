package br.fiap.com.br.lyra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String TRAIL_QUEUE = "trail-generation-queue";
    public static final String TRAIL_EXCHANGE = "trail-exchange";
    public static final String TRAIL_ROUTING = "trail.generate";

    @Bean
    public Queue trailQueue() {
        return new Queue(TRAIL_QUEUE, true);
    }

    @Bean
    public DirectExchange trailExchange() {
        return new DirectExchange(TRAIL_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue trailQueue, DirectExchange trailExchange) {
        return BindingBuilder.bind(trailQueue).to(trailExchange).with(TRAIL_ROUTING);
    }
}
