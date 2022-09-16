package tech.portfolioshop.scraper.models;

import java.util.List;

public class ScrapeResult {
    public String title;
    public String employer;
    public String experience;
    public String salary;
    public String location;
    public String descriptionHTML;
    public String url;
    public List<String> tags;

    public ScrapeResult(String title,
                        String employer,
                        String experience,
                        String salary,
                        String location,
                        String descriptionHTML,
                        String url,
                        List<String> tags) {
        this.title = title;
        this.employer = employer;
        this.experience = experience;
        this.salary = salary;
        this.location = location;
        this.descriptionHTML = descriptionHTML;
        this.url = url;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescriptionHTML() {
        return descriptionHTML;
    }

    public void setDescriptionHTML(String descriptionHTML) {
        this.descriptionHTML = descriptionHTML;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
