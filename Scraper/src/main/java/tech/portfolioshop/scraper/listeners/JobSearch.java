//package tech.portfolioshop.scraper.listeners;
//
//import org.jobaggregator.kafka.payload.JobSearchTriggered;
//import org.jobaggregator.kafka.payload.ScrapperJobsResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@EnableKafka
//@Component
//public class JobSearch {
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    @Autowired
//    public JobSearch(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    @KafkaListener(topics = "JOB_SEARCH_TRIGGERED", groupId = "${spring.application.name}")
//    public void jobSearchTriggered(String message) throws IllegalAccessException {
//        JobSearchTriggered jobSearchTriggered = new JobSearchTriggered(null, null);
//        jobSearchTriggered.deserialize(message);
//        kafkaTemplate.send((new ScrapperJobsResult()).getTopic(), mockScrapperResults(jobSearchTriggered.getQuery(), jobSearchTriggered.getLocation()));
//    }
//
//    public String mockScrapperResults(String query, String location) throws IllegalAccessException {
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            ScrapperJobsResult scrapperJobsResult = new ScrapperJobsResult(query, location, getRandomString(), getRandomString(), getRandomString(), getRandomString(), getRandomString(), getRandomString());
//            list.add(scrapperJobsResult.serialize());
//        }
//        return list.toString();
//    }
//
//    public String getRandomString() {
//        return "AYAZ";
//    }
//}
