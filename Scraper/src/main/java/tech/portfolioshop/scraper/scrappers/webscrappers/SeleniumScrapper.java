package tech.portfolioshop.scraper.scrappers.webscrappers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import tech.portfolioshop.scraper.models.Job;
import tech.portfolioshop.scraper.scrappers.jobscrappers.Websites;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public abstract class SeleniumScrapper {

    @NotNull
    private Websites website;
    private String query;
    private String location;
    private List<String> jobsDetailsUrl = new ArrayList<>();
    @NotNull
    private final WebDriver webDriver = new FirefoxDriver();

    public SeleniumScrapper(@NotNull Websites website) {
        this.website = website;
    }

    public SeleniumScrapper(@NotNull Websites website, String query, String location) {
        this.website = website;
        this.query = query;
        this.location = location;
    }

    public abstract String generateUrl();

    public abstract List<Job> scrape();

    public Websites getWebsite() {
        return website;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    @Override
    public String toString() {
        return "SeleniumScrapper{" +
                "webSiteBaseUrl='" + website.getBaseUrl() + '\'' +
                ", query='" + query + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public List<String> getJobsDetailsUrl() {
        return jobsDetailsUrl;
    }

    public void setJobsDetailsUrl(List<String> jobsDetailsUrl) {
        this.jobsDetailsUrl = jobsDetailsUrl;
    }
}