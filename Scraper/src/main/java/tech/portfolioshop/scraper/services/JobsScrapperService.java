package tech.portfolioshop.scraper.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tech.portfolioshop.scraper.models.JobModel;
import tech.portfolioshop.scraper.scrappers.jobscrappers.NaukariScrapper.NaukariScrapper;

import javax.naming.CannotProceedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class JobsScrapperService {

    public List<JobModel> scrape(String query, String location) throws CannotProceedException, ExecutionException, InterruptedException {
        CompletableFuture<List<JobModel>> naukariJobs = scrapeNaukari(query, location);
        CompletableFuture<List<JobModel>> indeedJobs = scrapeIndeed();
        CompletableFuture.allOf(naukariJobs, indeedJobs).join();
        List<JobModel> jobModels = new ArrayList<>();
        jobModels.addAll(naukariJobs.get());
        jobModels.addAll(indeedJobs.get());
        return jobModels;
    }

    @Async
    public CompletableFuture<List<JobModel>> scrapeNaukari(String query, String location) throws CannotProceedException {
        NaukariScrapper naukariScrapper = new NaukariScrapper(query, location);
        return CompletableFuture.completedFuture(naukariScrapper.scrape());
    }

    @Async
    public CompletableFuture<List<JobModel>> scrapeIndeed() throws CannotProceedException {
        NaukariScrapper naukariScrapper = new NaukariScrapper("java developer", "bangalore");
        return CompletableFuture.completedFuture(naukariScrapper.scrape());
    }

    @Async
    public CompletableFuture<List<JobModel>> scrapeGlassdoor(){
        return null;
    }
}