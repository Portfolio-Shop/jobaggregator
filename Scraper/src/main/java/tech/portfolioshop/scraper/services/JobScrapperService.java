package tech.portfolioshop.scraper.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.portfolioshop.scraper.models.JobModel;

import javax.naming.CannotProceedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class JobScrapperService {

    private final JobScrapperUtil jobScrapperUtil;

    @Autowired
    public JobScrapperService(JobScrapperUtil jobScrapperUtil) {
        this.jobScrapperUtil = jobScrapperUtil;
    }

    public List<JobModel> scrape(String query, String location) throws CannotProceedException, ExecutionException, InterruptedException {
        CompletableFuture<List<JobModel>> naukariJobs = jobScrapperUtil.scrapeNaukari(query, location);
        CompletableFuture<List<JobModel>> indeedJobs = jobScrapperUtil.scrapeIndeed();
        CompletableFuture.allOf(naukariJobs, indeedJobs).join();
        List<JobModel> jobModels = new ArrayList<>();
        jobModels.addAll(naukariJobs.get());
        jobModels.addAll(indeedJobs.get());
        return jobModels;
    }
}
