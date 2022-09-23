package tech.portfolioshop.scraper.scrappers.jobscrappers;

import tech.portfolioshop.scraper.models.Job;
import tech.portfolioshop.scraper.scrappers.webscrappers.SeleniumScrapper;

import java.util.List;

public class NaukariScrapper extends SeleniumScrapper {

    public NaukariScrapper(Websites website) {
        super(Websites.NAUKARI);
    }

    public NaukariScrapper(String query, String location) {
        super(Websites.NAUKARI, query, location);
    }

    @Override
    public String generateUrl() {
        //TODO: Create Generate Url Method for Scrapper
        return null;
    }

    @Override
    public List<Job> scrape() {
        //TODO: Create Scrapper for Naukari
        return null;
    }
}
