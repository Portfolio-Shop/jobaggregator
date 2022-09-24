package org.jobaggregator.kafka.config;

public interface KafkaTopics {
    String USER_CREATED = "USER_CREATED";
    String USER_UPDATED = "USER_UPDATED";
    String USER_DELETED = "USER_DELETED";
    String JOB_SEARCH_TRIGGERED = "JOB_SEARCH_TRIGGERED";
    String SCRAPPER_JOBS_RESULT = "SCRAPPER_JOBS_RESULT";
}
