package tech.portfolioshop.scraper.scrappers.webscrappers;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import tech.portfolioshop.scraper.models.JobModel;
import tech.portfolioshop.scraper.scrappers.jobscrappers.Websites;

import javax.naming.CannotProceedException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public abstract class SeleniumScrapper {

    @NotNull
    protected final Websites website;
    protected String query;
    protected String location;
    protected List<String> jobsDetailsUrls = new ArrayList<>();
    @NotNull
    protected final WebDriver webDriver;
    private static final String geckoDriverPath = "/drivers/geckodriver.exe";
    protected Actions action;

    public SeleniumScrapper(@NotNull Websites website) {
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        FirefoxOptions options = new FirefoxOptions();
//        options.setHeadless(true);
        webDriver = new FirefoxDriver(options);
        this.website = website;
        webDriver.manage().window().maximize();
        action = new Actions(webDriver);
    }

    public SeleniumScrapper(@NotNull Websites website, String query, String location) {
        System.out.println(geckoDriverPath);
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        FirefoxOptions options = new FirefoxOptions();
        //options.setHeadless(true);
        webDriver = new FirefoxDriver(options);
        webDriver.manage().window().maximize();
        this.website = website;
        this.query = query;
        this.location = location;
        action = new Actions(webDriver);
    }

    protected void ctrlClickElement(WebElement webElement){
        action.keyDown(Keys.CONTROL).build().perform();
        webElement.click();
        action.keyUp(Keys.CONTROL).build().perform();
    }

    public abstract String generateUrl();

    public abstract List<JobModel> scrape() throws CannotProceedException;

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

    public List<String> getJobsDetailsUrls() {
        return jobsDetailsUrls;
    }

    public void setJobsDetailsUrls(List<String> jobsDetailsUrls) {
        this.jobsDetailsUrls = jobsDetailsUrls;
    }
}