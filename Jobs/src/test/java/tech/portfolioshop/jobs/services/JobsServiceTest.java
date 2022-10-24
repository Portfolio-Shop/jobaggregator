package tech.portfolioshop.jobs.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import tech.portfolioshop.jobs.data.*;
import tech.portfolioshop.jobs.shared.JobsDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JobsServiceTest {
    @Mock
    private JobsRepository jobsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SearchRepository searchRepository;

    @Mock
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private JobsService jobsService;

    private List<JobsEntity> getMockJobs() {
        List<JobsEntity> ret = new ArrayList<>();
        JobsEntity job1 = new JobsEntity(
               "test-query-1",
                "test-location-1", "test-title-1",
                "test-company-1", "test-salary-1",
                "<p>test-description-1</p>", null,
                "test-url-1"
        );
        JobsEntity job2 = new JobsEntity(
                "test-query-2",
                "test-location-2", "test-title-2",
                "test-company-2", "test-salary-2",
                "<p>test-description-2</p>", null,
                "test-url-2"
        );
        ret.add(job1);
        ret.add(job2);
        return ret;
    }
    private UserEntity getMockUser(){
        return new UserEntity("test-userid", "test-email", "test-password", null);
    }

    @Test
    @DisplayName("Sucessfully get jobs")
    void getJobs() {
        List<JobsEntity> mockJobs = getMockJobs();

        Mockito.when(jobsRepository.findByQueryAndLocation(Mockito.anyString(), Mockito.anyString())).thenReturn(mockJobs);
        List<JobsDto> jobs = jobsService.findJobByQuery("test-query", "test-location");
        assertEquals(2, jobs.size());

        assertEquals(mockJobs.get(0).getQuery(), jobs.get(0).getQuery());
        assertEquals(mockJobs.get(0).getLocation(), jobs.get(0).getLocation());
        assertEquals(mockJobs.get(0).getTitle(), jobs.get(0).getTitle());
        assertEquals(mockJobs.get(0).getEmployer(), jobs.get(0).getEmployer());
        assertEquals(mockJobs.get(0).getSalary(), jobs.get(0).getSalary());
        assertEquals(mockJobs.get(0).getDescriptionHTML(), jobs.get(0).getDescriptionHTML());

        assertEquals(mockJobs.get(1).getQuery(), jobs.get(1).getQuery());
        assertEquals(mockJobs.get(1).getLocation(), jobs.get(1).getLocation());
        assertEquals(mockJobs.get(1).getTitle(), jobs.get(1).getTitle());
        assertEquals(mockJobs.get(1).getEmployer(), jobs.get(1).getEmployer());
        assertEquals(mockJobs.get(1).getSalary(), jobs.get(1).getSalary());
        assertEquals(mockJobs.get(1).getDescriptionHTML(), jobs.get(1).getDescriptionHTML());
    }

    @Test
    @DisplayName("Sucessfully get 0 jobs")
    void get0Jobs() {
        List<JobsEntity> mockJobs = new ArrayList<>();

        Mockito.when(jobsRepository.findByQueryAndLocation(Mockito.anyString(), Mockito.anyString())).thenReturn(mockJobs);
        List<JobsDto> jobs = jobsService.findJobByQuery("test-query-3", "test-location-3");
        assertEquals(0, jobs.size());
    }

    @Test
    @DisplayName("Sucessfully get recommendation")
    void getRecommendation() {
        List<JobsEntity> mockJobs = getMockJobs();

        Mockito.when(jobsRepository.findByRecommendation("test-user-id")).thenReturn(mockJobs);
        List<JobsDto> jobs = jobsService.findJobByRecommendation("test-user-id");
        assertEquals(2, jobs.size());

        assertEquals(mockJobs.get(0).getQuery(), jobs.get(0).getQuery());
        assertEquals(mockJobs.get(0).getLocation(), jobs.get(0).getLocation());
        assertEquals(mockJobs.get(0).getTitle(), jobs.get(0).getTitle());
        assertEquals(mockJobs.get(0).getEmployer(), jobs.get(0).getEmployer());
        assertEquals(mockJobs.get(0).getSalary(), jobs.get(0).getSalary());
        assertEquals(mockJobs.get(0).getDescriptionHTML(), jobs.get(0).getDescriptionHTML());

        assertEquals(mockJobs.get(1).getQuery(), jobs.get(1).getQuery());
        assertEquals(mockJobs.get(1).getLocation(), jobs.get(1).getLocation());
        assertEquals(mockJobs.get(1).getTitle(), jobs.get(1).getTitle());
        assertEquals(mockJobs.get(1).getEmployer(), jobs.get(1).getEmployer());
        assertEquals(mockJobs.get(1).getSalary(), jobs.get(1).getSalary());
        assertEquals(mockJobs.get(1).getDescriptionHTML(), jobs.get(1).getDescriptionHTML());
    }

    @Test
    @DisplayName("Sucessfully get 0 recommendation")
    void get0Recommendation() {
        List<JobsEntity> mockJobs = new ArrayList<>();

        Mockito.when(jobsRepository.findByRecommendation("test-user-id")).thenReturn(mockJobs);
        List<JobsDto> jobs = jobsService.findJobByRecommendation("test-user-id");
        assertEquals(0, jobs.size());
    }

    @Test
    @DisplayName("Store search in db")
    void storeSearch() {
        List<JobsEntity> mockJobs = getMockJobs();
        Mockito.when(jobsRepository.findByQueryAndLocation(Mockito.anyString(), Mockito.anyString())).thenReturn(mockJobs);
        Mockito.when(userRepository.getByUserId(Mockito.anyString())).thenReturn(getMockUser());
        Mockito.when(searchRepository.save(Mockito.any(SearchEntity.class))).thenReturn(new SearchEntity());

        List<JobsDto> jobs = jobsService.findJobByQuery("test-query", "test-location", "test-user-id");
        Mockito.verify(searchRepository, Mockito.times(1)).save(Mockito.any(SearchEntity.class));

        assertEquals(mockJobs.get(0).getQuery(), jobs.get(0).getQuery());
        assertEquals(mockJobs.get(0).getLocation(), jobs.get(0).getLocation());
        assertEquals(mockJobs.get(0).getTitle(), jobs.get(0).getTitle());
        assertEquals(mockJobs.get(0).getEmployer(), jobs.get(0).getEmployer());
        assertEquals(mockJobs.get(0).getSalary(), jobs.get(0).getSalary());
        assertEquals(mockJobs.get(0).getDescriptionHTML(), jobs.get(0).getDescriptionHTML());

        assertEquals(mockJobs.get(1).getQuery(), jobs.get(1).getQuery());
        assertEquals(mockJobs.get(1).getLocation(), jobs.get(1).getLocation());
        assertEquals(mockJobs.get(1).getTitle(), jobs.get(1).getTitle());
        assertEquals(mockJobs.get(1).getEmployer(), jobs.get(1).getEmployer());
        assertEquals(mockJobs.get(1).getSalary(), jobs.get(1).getSalary());
        assertEquals(mockJobs.get(1).getDescriptionHTML(), jobs.get(1).getDescriptionHTML());
    }
}