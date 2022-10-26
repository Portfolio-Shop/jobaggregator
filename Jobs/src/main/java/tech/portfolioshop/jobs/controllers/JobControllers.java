package tech.portfolioshop.jobs.controllers;

import io.jsonwebtoken.Jwts;
import org.jobaggregator.errors.UnauthorizedException;
import org.jobaggregator.kafka.payload.JobSearchTriggered;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import tech.portfolioshop.jobs.models.request.JobSearchRequest;
import tech.portfolioshop.jobs.models.response.JobResponse;
import tech.portfolioshop.jobs.services.JobsService;
import tech.portfolioshop.jobs.shared.JobsDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobControllers {
    private final JobsService jobsService;
    private final ModelMapper modelMapper;
    private final Environment environment;
    private  final KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    public JobControllers(JobsService jobsService,
                          ModelMapper modelMapper,
                          Environment environment,
                          KafkaTemplate<String, String> kafkaTemplate) {
        this.jobsService = jobsService;
        this.modelMapper = modelMapper;
        this.environment = environment;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<List<JobResponse>> findJobsByQuery(@RequestBody JobSearchRequest jobSearchRequest){
        String query = jobSearchRequest.getQuery();
        String location = jobSearchRequest.getLocation();
        List<JobsDto> jobs = jobsService.findJobByQuery(query, location);
        List<JobResponse> jobResponses = new ArrayList<>();
        if(jobs.size()<Integer.parseInt("0"+environment.getProperty("jobs.query.minimum"))){
            JobSearchTriggered jobSearchTriggered = new JobSearchTriggered(query, location);
            kafkaTemplate.send(jobSearchTriggered.getTopic(), jobSearchTriggered.serialize());
        }
        for(JobsDto job : jobs){
            jobResponses.add(modelMapper.map(job, JobResponse.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(jobResponses);
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> findJobsByRecommendation(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws UnauthorizedException {
        String userId;
        try{
            userId = Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret"))
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();
        }catch (Exception e){
            throw new UnauthorizedException("Invalid token or session expired");
        }
        if (userId == null) {
            throw new UnauthorizedException("No user id found");
        }
        List<JobsDto> jobs = jobsService.findJobByRecommendation(userId);
        List<JobResponse> jobResponses = new ArrayList<>();
        for(JobsDto job : jobs){
            jobResponses.add(modelMapper.map(job, JobResponse.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(jobResponses);
    }
}
