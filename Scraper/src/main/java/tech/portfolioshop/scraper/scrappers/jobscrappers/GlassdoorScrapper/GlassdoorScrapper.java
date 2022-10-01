package tech.portfolioshop.scraper.scrappers.jobscrappers.GlassdoorScrapper;

import tech.portfolioshop.scraper.models.JobModel;
import tech.portfolioshop.scraper.scrappers.jobscrappers.Websites;
import tech.portfolioshop.scraper.scrappers.webscrappers.SeleniumScrapper;

import java.util.List;

public class GlassdoorScrapper extends SeleniumScrapper {

    public GlassdoorScrapper(Websites website) {
        super(Websites.GLASSDOOR);
    }

    public GlassdoorScrapper(Websites website, String query, String location) {
        super(Websites.GLASSDOOR, query, location);
    }

    @Override
    public String generateUrl() {
        //TODO: Create Generate Url Method for Scrapper
        return null;
    }

    @Override
    public List<JobModel> scrape() {
        //TODO: Create Scrapper for GlassDoor
        return null;
    }
}
