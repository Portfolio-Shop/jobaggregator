package tech.portfolioshop.scraper.models;

import org.jobaggregator.kafka.payload.ScrapperJobsResult;

public class Job extends ScrapperJobsResult {

    private String query;
    private String location;
    private String title;
    private String employer;
    private String salary;
    private String descriptionHTML;
    private String skills;

    public Job() {
    }

    public Job(String query, String location, String title, String employer, String salary, String descriptionHTML, String skills) {
        this.query = query;
        this.location = location;
        this.title = title;
        this.employer = employer;
        this.salary = salary;
        this.descriptionHTML = descriptionHTML;
        this.skills = skills;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDescriptionHTML() {
        return descriptionHTML;
    }

    public void setDescriptionHTML(String descriptionHTML) {
        this.descriptionHTML = descriptionHTML;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Job{" +
                "query='" + query + '\'' +
                ", location='" + location + '\'' +
                ", title='" + title + '\'' +
                ", employer='" + employer + '\'' +
                ", salary='" + salary + '\'' +
                ", descriptionHTML='" + descriptionHTML + '\'' +
                ", skills='" + skills + '\'' +
                '}';
    }
}
