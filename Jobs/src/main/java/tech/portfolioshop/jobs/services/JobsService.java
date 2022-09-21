package tech.portfolioshop.jobs.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tech.portfolioshop.jobs.data.*;
import tech.portfolioshop.jobs.shared.JobsDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobsService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final JobsRepository jobsRepository;
    private final SearchRepository searchRepository;


    public JobsService(ModelMapper modelMapper,
                       UserRepository userRepository,
                       JobsRepository jobsRepository,
                       SearchRepository searchRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.jobsRepository = jobsRepository;
        this.searchRepository = searchRepository;
    }

    public List<JobsDto> findJobByQuery(String query, String location, String userId){
        UserEntity user = userRepository.getByUserId(userId);

        SearchEntity search = new SearchEntity();
        search.setQuery(query);
        search.setLocation(location);
        search.setUser(user);
        searchRepository.save(search);

        List<JobsEntity> jobs = jobsRepository.findByQueryandLocation(query,location);
        List<JobsDto> jobsDtos = new ArrayList<>();
        for(JobsEntity job : jobs){
            jobsDtos.add(modelMapper.map(job, JobsDto.class));
        }
        return jobsDtos;
    }
    public List<JobsDto> findJobByQuery(String query, String location){
        List<JobsEntity> jobs = jobsRepository.findByQueryandLocation(query,location);
        List<JobsDto> jobsDtos = new ArrayList<>();
        for(JobsEntity job : jobs){
            jobsDtos.add(modelMapper.map(job, JobsDto.class));
        }
        return jobsDtos;
    }
}
