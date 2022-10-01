package tech.portfolioshop.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.portfolioshop.scraper.models.JobModel;
import tech.portfolioshop.scraper.services.JobScrapperService;
import tech.portfolioshop.scraper.services.JobScrapperUtil;

import javax.naming.CannotProceedException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/scraper")
public class Controller {
    private JobScrapperService jobScrapperService;

    @Autowired
    public Controller(JobScrapperService jobScrapperService) {
        this.jobScrapperService = jobScrapperService;
    }

    @GetMapping("/test")
    public String test() throws CannotProceedException, ExecutionException, InterruptedException {
        List<JobModel> jobModels = jobScrapperService.scrape("java developer", "bangalore");
        return jobModels.toString();
    }
}
