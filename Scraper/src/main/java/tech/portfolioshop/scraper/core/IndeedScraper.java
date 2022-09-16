package tech.portfolioshop.scraper.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.portfolioshop.scraper.models.ScrapeResult;
import tech.portfolioshop.scraper.utils.ScraperUtils;

import java.util.List;

@Service
public class IndeedScraper implements ScraperInterface{
    public final ScraperUtils scraperUtils;

    @Autowired
    public IndeedScraper(ScraperUtils scraperUtils) {
        this.scraperUtils = scraperUtils;
    }

    @Override
    public List<ScrapeResult> Scrape(String jobName) {
        return null;
    }
}
