package org.jobaggregator.kafka.services;

import org.jobaggregator.kafka.payload.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService<T extends Payload> {

    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    KafkaProducerService(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(T payload) {
        String topic = payload.getTopic();
        String serializedObject = payload.serialize();
        kafkaTemplate.send(topic, serializedObject);
    }
}
