package tech.portfolioshop.jobs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class JobsApplication {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public JobsApplication(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(JobsApplication.class, args);
    }
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
    @EventListener(ApplicationReadyEvent.class)
    public void connectToKafka() {
        kafkaTemplate.send("connect", "connect");
    }
}
