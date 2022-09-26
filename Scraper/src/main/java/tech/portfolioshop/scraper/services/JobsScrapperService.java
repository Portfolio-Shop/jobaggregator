package tech.portfolioshop.scraper.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tech.portfolioshop.scraper.models.Job;
import tech.portfolioshop.scraper.scrappers.jobscrappers.NaukariScrapper.NaukariScrapper;

import javax.naming.CannotProceedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class JobsScrapperService {

    public List<Job> scrape(String query, String location) throws CannotProceedException, ExecutionException, InterruptedException {
        CompletableFuture<List<Job>> naukariJobs = scrapeNaukari(query, location);
        CompletableFuture<List<Job>> indeedJobs = scrapeIndeed();
        CompletableFuture.allOf(naukariJobs, indeedJobs).join();
        List<Job> jobs = new ArrayList<>();
        jobs.addAll(naukariJobs.get());
        jobs.addAll(indeedJobs.get());
        return jobs;
    }

    @Async
    public CompletableFuture<List<Job>> scrapeNaukari(String query, String location) throws CannotProceedException {
        NaukariScrapper naukariScrapper = new NaukariScrapper(query, location);
        return CompletableFuture.completedFuture(naukariScrapper.scrape());
    }

    @Async
    public CompletableFuture<List<Job>> scrapeIndeed() throws CannotProceedException {
        NaukariScrapper naukariScrapper = new NaukariScrapper("java developer", "bangalore");
        return CompletableFuture.completedFuture(naukariScrapper.scrape());
    }

    @Async
    public CompletableFuture<List<Job>> scrapeGlassdoor(){
        return null;
    }
}