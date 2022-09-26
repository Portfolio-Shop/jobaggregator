package tech.portfolioshop.scraper.models;

import org.jobaggregator.kafka.payload.ScrapperJobsResult;

public class JobModel extends ScrapperJobsResult {
    public JobModel(){super();}
    public JobModel(String query, String location, String title, String employer, String salary, String descriptionHTML, String skills, String jobUrl){
        super(query, location, title, employer, salary, descriptionHTML, skills);
    }

    @Override
    public String toString() {
        return serialize();
    }
}