package tech.portfolioshop.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.portfolioshop.scraper.models.Job;
import tech.portfolioshop.scraper.services.JobsScrapperService;

import javax.naming.CannotProceedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/scraper")
public class Controller {
    private JobsScrapperService jobsScrapperService;

    @Autowired
    public Controller(JobsScrapperService jobsScrapperService) {
        this.jobsScrapperService = jobsScrapperService;
    }

    @GetMapping("/test")
    public String test() throws CannotProceedException, ExecutionException, InterruptedException {
        CompletableFuture<List<Job>>  naukri = jobsScrapperService.scrapeNaukari("java developer", "bangalore");
        CompletableFuture<List<Job>>  indeed = jobsScrapperService.scrapeIndeed();
        CompletableFuture.allOf(naukri, indeed).join();
        List<Job> jobs = new ArrayList<>();
        jobs.addAll(naukri.get());
        jobs.addAll(indeed.get());
        return jobs.toString();
    }
}
