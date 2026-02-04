package airtribe.job_schedular.worker;

import airtribe.job_schedular.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobPublisher.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(Long jobId) {
        LOGGER.info("Publishing job {} to RabbitMQ", jobId);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.JOB_EXCHANGE,
                RabbitMQConfig.JOB_ROUTING_KEY,
                jobId
        );
    }
}
