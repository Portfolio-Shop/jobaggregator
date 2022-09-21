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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
        List<ScrapperJobsResult> scrapperJobsResults = new ArrayList<>(jsonArray.length());
        Collections.fill(scrapperJobsResults, new ScrapperJobsResult());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            scrapperJobsResults.get(i).deserialize(jsonObject.toString());
        }
        List<JobsEntity> jobsEntities = new ArrayList<>();
        for (ScrapperJobsResult scrapperJobsResult : scrapperJobsResults) {
            jobsEntities.add(modelMapper.map(scrapperJobsResult, JobsEntity.class));
        }
        jobsRepository.saveAll(jobsEntities);
    }
}
