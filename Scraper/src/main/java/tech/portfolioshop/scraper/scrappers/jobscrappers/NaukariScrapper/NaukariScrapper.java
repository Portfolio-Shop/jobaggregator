package tech.portfolioshop.scraper.scrappers.jobscrappers.NaukariScrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import tech.portfolioshop.scraper.models.JobModel;
import tech.portfolioshop.scraper.scrappers.jobscrappers.Websites;
import tech.portfolioshop.scraper.scrappers.webscrappers.SeleniumScrapper;

import javax.naming.CannotProceedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NaukariScrapper extends SeleniumScrapper {

    String websiteTabHandle;
    List<String> jobTabsHandles;
    Logger logger = Logger.getLogger("NaukariScrapper");

    public NaukariScrapper(String query, String location) {
        super(Websites.NAUKARI, query, location);
        this.query = query;
        this.location = location;
        jobTabsHandles = new ArrayList<>();
    }

    @Override
    public List<JobModel> scrape() throws CannotProceedException {
        List<JobModel> jobModels;
        try {
            webDriver.get(generateUrl());
            Thread.sleep(7000);
            websiteTabHandle = webDriver.getWindowHandle();
            WebElement jobList = getJobListSelector();
            openAndMapJobTabs(jobList);
            Thread.sleep(7000);
            jobModels = listAllJobs();
            if(jobModels.size() == 0) {
                throw new RuntimeException();
            }
            webDriver.quit();
        } catch (Exception e) {
            logger.severe("Could Not Scrap the website: " + e.getMessage());
            webDriver.quit();
            throw new CannotProceedException("Could Not Scrap the website: " + e.getMessage());
        }
        return jobModels;
    }

    private void openAndMapJobTabs(WebElement jobList) {
        List<WebElement> jobRowsSelector = jobList.findElements(By.tagName("article"));
        try {
            for (WebElement jobRow : jobRowsSelector) {
                try {
                    ctrlClickElement(jobRow);
                    Thread.sleep(1000);
                    webDriver.switchTo().window(websiteTabHandle);
                } catch (Exception e) {
                    logger.info("Could Not Click Open Job Tab: " + e.getMessage());
                }
            }
            for (String tabHandle : webDriver.getWindowHandles()) {
                if (!tabHandle.equals(websiteTabHandle)) {
                    jobTabsHandles.add(tabHandle);
                }
            }
        } catch (Exception e) {
            logger.severe("Failed to Map and Open any Tab: " + e.getMessage());
        }
    }

    private List<JobModel> listAllJobs() {
        List<JobModel> jobModels = new ArrayList<>();
        for (String jobTabHandel : jobTabsHandles) {
            try {
                jobModels.add(scrapeJobSelector(jobTabHandel));
            } catch (Exception e) {
                logger.info("Could not scrape a job: " + e.getMessage());
            }
        }
        return jobModels;
    }

    private JobModel scrapeJobSelector(String jobTabHandel) {
        try {
            webDriver.switchTo().window(jobTabHandel);
            Thread.sleep(2000);
            String title;
            try{
                title = webDriver.findElement(By.xpath(NaukariXPaths.TITLE.getValue())).getText();
            }catch (Exception e){
                title = this.query;
            }
            String employer;
            try{
                employer = webDriver.findElement(By.xpath(NaukariXPaths.EMPLOYER.getValue())).getText();
            }catch(Exception e){
                employer = "Unknown";
            }
            String salary;
            try{
                salary = webDriver.findElement(By.xpath(NaukariXPaths.SALARY.getValue())).getText();
            }catch(Exception e){
                salary = "Not Disclosed";
            }
            String experience;
            try{
                experience = webDriver.findElement(By.xpath(NaukariXPaths.EXPERIENCE.getValue())).getText();
            }catch(Exception e){
                experience = "";
            }
            String jobLocation;
            try{
                jobLocation = webDriver.findElement(By.xpath(NaukariXPaths.JOBLOCATION.getValue())).getText();
            }catch(Exception e){
                jobLocation = this.location;
            }
            String skills = "";
            WebElement skillsSelector = webDriver.findElement(By.xpath(NaukariXPaths.SKILLSELECTOR.getValue()));
            for (WebElement skill : skillsSelector.findElements(By.tagName("a"))) {
                skills += skill.getText() + " ";
            }
            String jobUrl = webDriver.getCurrentUrl();
            String descriptionHTML;
            String jobDescription;
            try{
                jobDescription = webDriver.findElement(By.xpath(NaukariXPaths.JOBDESCSELECTOR.getValue())).getAttribute("innerHTML");
            }catch(Exception e){
                jobDescription = "";
            }
            String aboutCompany;
            try{
                aboutCompany = webDriver.findElement(By.xpath(NaukariXPaths.ABOUTCOMPANYSELECTOR.getValue())).getAttribute("innerHTML");
            }catch(Exception e){
                aboutCompany = "";
            }
            descriptionHTML = jobDescription + aboutCompany;
            webDriver.switchTo().window(websiteTabHandle);
            return new JobModel(query, jobLocation, title, employer, salary, descriptionHTML, skills + experience, jobUrl);
        } catch (Exception e) {
            logger.info("Could Not Scrape Job: " + e.getMessage());
            throw new RuntimeException("Could Not Scrape Job: " + e.getMessage());
        }
    }

    private WebElement getJobListSelector() {
        //TODO: Handle all popups and timeout functionality here
        return webDriver.findElement(By.xpath(NaukariXPaths.JOBLIST.getValue()));
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
