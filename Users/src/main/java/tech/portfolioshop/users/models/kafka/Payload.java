package tech.portfolioshop.users.models.kafka;

import tech.portfolioshop.users.configs.kafka.KafkaTopics;

public abstract class Payload {
    private final KafkaTopics Topic;

    protected Payload(KafkaTopics topic) {
        Topic = topic;
    }

    public abstract String serialize();
    public abstract void deserialize(String json);
    public final KafkaTopics getTopic() {
        return Topic;
    }
}
