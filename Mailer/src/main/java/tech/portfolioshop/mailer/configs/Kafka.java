package tech.portfolioshop.mailer.configs;

import org.jobaggregator.kafka.config.KafkaConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class Kafka extends KafkaConfig {
    @Bean
    public KafkaTemplate<String, String> getKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
