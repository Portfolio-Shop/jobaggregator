package tech.portfolioshop.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tech.portfolioshop.users.configs.kafka.KafkaTopics;
import tech.portfolioshop.users.models.kafka.Payload;

@Service
public class KafkaProducerService<T extends Payload> {

    KafkaTemplate kafkaTemplate;

    @Autowired
    KafkaProducerService(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(T payload) {
        KafkaTopics topic = payload.getTopic();
        String serializedObject = payload.serialize();
        kafkaTemplate.send(String.valueOf(topic), serializedObject);
    }
}
