package tech.portfolioshop.jobs.listeners;

import org.jobaggregator.kafka.payload.ScrapperJobsResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tech.portfolioshop.jobs.data.JobsEntity;
import tech.portfolioshop.jobs.data.JobsRepository;

import java.util.*;

@EnableKafka
@Component
public class ScrapperJobsListener {

    private final ModelMapper modelMapper;
    private final JobsRepository jobsRepository;

    @Autowired
    public ScrapperJobsListener(ModelMapper modelMapper, JobsRepository jobsRepository) {
        this.modelMapper = modelMapper;
        this.jobsRepository = jobsRepository;
    }

    @KafkaListener(topics = "SCRAPPER_JOBS_RESULT", groupId = "${spring.application.name}")
    public void jobsScrapped(String jobs) throws IllegalAccessException {
        JSONArray jsonArray = new JSONArray(jobs);
        List<ScrapperJobsResult> scrapperJobsResults = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ScrapperJobsResult scrapperJobsResult = new ScrapperJobsResult();
            scrapperJobsResult.deserialize(jsonObject.toString());
            scrapperJobsResults.add(scrapperJobsResult);
        }
        List<JobsEntity> jobsEntities = new ArrayList<>();
        for (ScrapperJobsResult scrapperJobsResult : scrapperJobsResults) {
            JobsEntity jobsEntity = modelMapper.map(scrapperJobsResult, JobsEntity.class);
            jobsEntities.add(jobsEntity);
            jobsEntities.get(jobsEntities.size()-1).setJobId(UUID.randomUUID().toString());
        }
        jobsRepository.saveAll(jobsEntities);
    }
}
