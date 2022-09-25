package tech.portfolioshop.scraper.scrappers.jobscrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import tech.portfolioshop.scraper.models.Job;
import tech.portfolioshop.scraper.scrappers.webscrappers.SeleniumScrapper;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class NaukariScrapper extends SeleniumScrapper {

    String websiteTabHandle;
    HashMap<WebElement, String> webElementToTabMapper;
    Logger logger = Logger.getLogger("NaukariScrapper");

    public NaukariScrapper(Websites website) {
        super(Websites.NAUKARI);
        webElementToTabMapper = new HashMap<>();
    }

    public NaukariScrapper(String query, String location) {
        super(Websites.NAUKARI, query, location);
        this.query = query;
        this.location = location;
        webElementToTabMapper = new HashMap<>();
    }


    @Override
    public List<Job> scrape() {
        try {
            System.out.println("LOGS" + generateUrl());
            webDriver.get(generateUrl());
            Thread.sleep(12000);
            websiteTabHandle = webDriver.getWindowHandle();
            WebElement jobList = getJobListSelector();
            System.out.println("LOGS" + jobList);
            openAndMapJobTabs(jobList);
            Thread.sleep(12000);
            System.out.println(listAllJobs());
        } catch (Exception e) {
            logger.severe("Could Not Scrap the website: "+e.getMessage());
            webDriver.close();
        }
        return null;
    }

    private void openAndMapJobTabs(WebElement jobList) {
        List<WebElement> jobRowsSelector = jobList.findElements(By.tagName("article"));
        for (WebElement jobRow : jobRowsSelector) {
            try {
                ctrlClickElement(jobRow);
                Thread.sleep(4000);
                System.out.println(webDriver.getWindowHandle());
                webElementToTabMapper.put(jobRow, webDriver.getWindowHandle());
                webDriver.switchTo().window(websiteTabHandle);
            } catch (Exception e) {
                logger.info("Could Not Click Open Job Tab: "+e.getMessage());
            }
        }
    }

    private List<Job> listAllJobs(){
        List<Job> jobs = new ArrayList<>();
        for(WebElement jobSelector: webElementToTabMapper.keySet()){
            try{
                jobs.add(scrapeJobSelector(jobSelector));
            }catch (Exception e){
                logger.info("Could not scrape a job: "+e.getMessage());
            }
        }
        return jobs;
    }

    private Job scrapeJobSelector(WebElement jobSelector) {
        try {
            String title = jobSelector.findElement(By.className("title")).getText();
            String employer = jobSelector.findElement(By.className("subTitle")).getText();
            WebElement extraInfoSelector = jobSelector.findElement(By.tagName("ul"));
            String salary = extraInfoSelector.findElements(By.tagName("span")).get(0).getText();
            String experience = extraInfoSelector.findElements(By.tagName("span")).get(1).getText();
            String skills = "";
            WebElement skillsSelector = jobSelector.findElement(By.className("tags"));
            for (WebElement skill : skillsSelector.findElements(By.tagName("li"))) {
                skills += skill.getText() + " ";
            }
            webDriver.switchTo().window(webElementToTabMapper.get(jobSelector));
            String jobUrl = webDriver.getCurrentUrl();
            System.out.println("joburl"+jobUrl);
            WebElement jobDescriptionSelector = jobSelector.findElement(By.className("job-desc"));
            WebElement aboutCompanySelector = jobSelector.findElement(By.className("about-company"));
            String descriptionHTML = jobDescriptionSelector.getAttribute("innerHTML") + aboutCompanySelector.getAttribute("innerHTML");
            return new Job(query, location, title, employer, salary, descriptionHTML, skills + experience, jobUrl);
        } catch (Exception e) {
            throw new RuntimeException("Could Not Scrape Job: "+e.getMessage());
        }
    }

    private WebElement getJobListSelector() {
        //TODO: Handle all popups and timeout functionality here
        return webDriver.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/section[2]/div[2]"));
    }

    @Override
    public String generateUrl() {
        String seperatedQuery = "", seperatedLocation = "";
        for (String word : query.split(" ")) seperatedQuery += word + "-";
        for (String word : location.split(" ")) seperatedLocation += word + "-";
        seperatedQuery = seperatedQuery.trim();
        seperatedLocation = seperatedLocation.trim();
        if (seperatedQuery.length() > 0) seperatedQuery = seperatedQuery.substring(0, seperatedQuery.length() - 1);
        if (seperatedLocation.length() > 0)
            seperatedLocation = seperatedLocation.substring(0, seperatedLocation.length() - 1);
        return website.getBaseUrl() + seperatedQuery + "-jobs-in-" + seperatedLocation;
    }

}
