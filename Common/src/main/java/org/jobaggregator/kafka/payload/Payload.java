package org.jobaggregator.kafka.payload;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;

public abstract class Payload {
    private final String Topic;

    protected Payload(String topic) {
        Topic = topic;
    }

    public abstract String serialize() throws IllegalAccessException;
    public abstract void deserialize(String json) throws IllegalAccessException;
    public final String getTopic() {
        return Topic;
    }

    @Bean
    public NewTopic topic(){
        return new NewTopic(Topic, 1, (short)1);
    }
}
