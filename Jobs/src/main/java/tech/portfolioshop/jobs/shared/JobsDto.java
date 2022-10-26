package tech.portfolioshop.jobs.shared;

import java.io.Serializable;
import java.util.List;

public class JobsDto implements Serializable {
    private String jobId;
    private String query;
    private String location;
    private String title;
    private String employer;
    private String salary;
    private String descriptionHTML;
    private List<String> skills;

    public JobsDto() {
    }

    public JobsDto(String jobId, String query, String location, String title, String employer, String salary, String descriptionHTML, List<String> skills) {
        this.jobId = jobId;
        this.query = query;
        this.location = location;
        this.title = title;
        this.employer = employer;
        this.salary = salary;
        this.descriptionHTML = descriptionHTML;
        this.skills = skills;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
