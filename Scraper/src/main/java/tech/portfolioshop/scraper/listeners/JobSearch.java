
package tech.portfolioshop.scraper.listeners;

import org.jobaggregator.kafka.config.KafkaTopics;
import org.jobaggregator.kafka.payload.JobSearchTriggered;
import org.jobaggregator.kafka.payload.ScrapperJobsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tech.portfolioshop.scraper.models.JobModel;
import tech.portfolioshop.scraper.services.JobsScrapperService;

import javax.naming.CannotProceedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@EnableKafka
@Component
public class JobSearch {
    private final JobsScrapperService jobsScrapperService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public JobSearch(JobsScrapperService jobsScrapperService, KafkaTemplate<String, String> kafkaTemplate) {
        this.jobsScrapperService = jobsScrapperService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = KafkaTopics.JOB_SEARCH_TRIGGERED, groupId = "${spring.application.name}")
    public void jobSearchTriggered(String message) throws CannotProceedException, ExecutionException, InterruptedException {
        JobSearchTriggered jobSearchTriggered = new JobSearchTriggered().deserialize(message);
        List<JobModel> jobs = jobsScrapperService.scrape(jobSearchTriggered.getQuery(), jobSearchTriggered.getLocation());
        System.out.println("Jobs: " + jobs.toString());
        kafkaTemplate.send((new ScrapperJobsResult()).getTopic(), jobs.toString());
    }

}
