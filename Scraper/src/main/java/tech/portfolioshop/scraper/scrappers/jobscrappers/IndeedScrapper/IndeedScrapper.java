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
        String seperatedQuery = "", seperatedLocation = "";
        for (String word : query.split(" ")) seperatedQuery += word + "-";
        for (String word : location.split(" ")) seperatedLocation += word + "-";
        seperatedQuery = seperatedQuery.trim();
        seperatedLocation = seperatedLocation.trim();
        if (seperatedQuery.length() > 0) seperatedQuery = seperatedQuery.substring(0, seperatedQuery.length() - 1);
        if (seperatedLocation.length() > 0)
            seperatedLocation = seperatedLocation.substring(0, seperatedLocation.length() - 1);
        return website.getBaseUrl() + "jobs";
    }

    @Override
    public List<JobModel> scrape() {
        //TODO: Create Scrapper for Indeed

        List<JobModel> jobModels;
        try {
            webDriver.get(generateUrl());
            Thread.sleep(7000);
            websiteTabHandle = webDriver.getWindowHandle();

        }
        catch (){

        }
        return null;
    }
}
