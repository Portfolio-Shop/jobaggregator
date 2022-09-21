package tech.portfolioshop.scraper.listeners;

import org.jobaggregator.kafka.payload.JobSearchTriggered;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
public class JobSearch {

    @KafkaListener(topics = "JOB_SEARCH_TRIGGERED", groupId = "${spring.application.name}")
    public void jobSearchTriggered(String message) {
        JobSearchTriggered jobSearchTriggered = new JobSearchTriggered(null, null);
        jobSearchTriggered.deserialize(message);
        System.out.println(message);
    }
}
