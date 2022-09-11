package tech.portfolioshop.users.data.services;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tech.portfolioshop.users.data.services.payloads.Payload;

@Service
public class KafkaProducer<T extends Payload> {

    KafkaTemplate kafkaTemplate;

    @Autowired
    KafkaProducer(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(T payload) {
        KafkaTopics topic = payload.getTopic();
        String serializedObject = payload.serialize();
        kafkaTemplate.send(String.valueOf(topic), serializedObject);
    }
}
