package tech.portfolioshop.jobs.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.portfolioshop.jobs.models.http.request.JobSearchRequest;
import tech.portfolioshop.jobs.models.http.response.JobResponse;
import tech.portfolioshop.jobs.services.JobsService;
import tech.portfolioshop.jobs.shared.JobsDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobControllers {
    private final JobsService jobsService;
    private final ModelMapper modelMapper;
    @Autowired
    public JobControllers(JobsService jobsService, ModelMapper modelMapper) {
        this.jobsService = jobsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<List<JobResponse>> findJobsByQuery(@RequestBody JobSearchRequest jobSearchRequest){
        String query = jobSearchRequest.getQuery();
        String location = jobSearchRequest.getLocation();
        List<JobsDto> jobs = jobsService.findJobByQuery(query, location);
        List<JobResponse> jobResponses = new ArrayList<>();
        for(JobsDto job : jobs){
            jobResponses.add(modelMapper.map(job, JobResponse.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(jobResponses);
    }
}
