package tech.portfolioshop.scraper.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import tech.portfolioshop.scraper.models.ScrapeResult;

import java.util.List;

public interface ScraperInterface {
    FirefoxOptions options = new FirefoxOptions();
    WebDriver driver = new FirefoxDriver(options);
    List<ScrapeResult> Scrape(String jobName);
}
