package tech.portfolioshop.users.data.services.payloads;

import tech.portfolioshop.users.data.services.KafkaTopics;

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
