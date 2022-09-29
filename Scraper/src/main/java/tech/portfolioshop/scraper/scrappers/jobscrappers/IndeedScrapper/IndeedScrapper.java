package tech.portfolioshop.scraper.scrappers.jobscrappers.IndeedScrapper;

import tech.portfolioshop.scraper.models.JobModel;
import tech.portfolioshop.scraper.scrappers.jobscrappers.Websites;
import tech.portfolioshop.scraper.scrappers.webscrappers.SeleniumScrapper;

import java.util.List;

public class IndeedScrapper extends SeleniumScrapper {

    public IndeedScrapper(Websites website) {
        super(Websites.INDEED);
    }

    public IndeedScrapper(Websites website, String query, String location) {
        super(Websites.INDEED, query, location);
    }

    @Override
    public String generateUrl() {
        //TODO: Create Generate Url Method for Scrapper
        return null;
    }

    @Override
    public List<JobModel> scrape() {
        //TODO: Create Scrapper for Indeed
        return null;
    }
}
