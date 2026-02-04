package airtribe.job_schedular.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;


@Configuration
public class RabbitMQConfig {

    public static final String JOB_QUEUE = "job.execution.queue";
    public static final String JOB_EXCHANGE = "job.execution.exchange";
    public static final String JOB_ROUTING_KEY = "job.execute";

    @Bean
    public Queue jobQueue() {
        return QueueBuilder.durable(JOB_QUEUE).build();
    }

    @Bean
    public DirectExchange jobExchange() {
        return new DirectExchange(JOB_EXCHANGE);
    }

    @Bean
    public Binding jobBinding() {
        return BindingBuilder
                .bind(jobQueue())
                .to(jobExchange())
                .with(JOB_ROUTING_KEY);
    }
}
