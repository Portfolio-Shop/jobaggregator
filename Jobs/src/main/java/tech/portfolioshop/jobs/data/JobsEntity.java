package tech.portfolioshop.jobs.data;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class JobsEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String jobId;

    @Column(nullable = false)
    private String query;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String employer;

    @Column(nullable = true)
    private String salary;

    @Column(nullable = true)
    private String descriptionHTML;

    @Column(nullable = false)
    private String skills;

    @Column(nullable = false)
    private String jobUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public JobsEntity() {
    }

    public JobsEntity(Long id, String jobId, String query, String location, String title, String employer, String salary, String descriptionHTML, String skills, LocalDateTime createdAt, String jobUrl) {
        this.id = id;
        this.jobId = jobId;
        this.query = query;
        this.location = location;
        this.title = title;
        this.employer = employer;
        this.salary = salary;
        this.descriptionHTML = descriptionHTML;
        this.skills = skills;
        this.createdAt = createdAt;
        this.jobUrl = jobUrl;
    }

    public JobsEntity(String query, String location, String title, String employer, String salary, String descriptionHTML, String skills, String jobUrl) {
        this.query = query;
        this.location = location;
        this.title = title;
        this.employer = employer;
        this.salary = salary;
        this.descriptionHTML = descriptionHTML;
        this.skills = skills;
        this.jobUrl = jobUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "JobsEntity{" + "id=" + id + ", jobId='" + jobId + '\'' + ", query='" + query + '\'' + ", location='" + location + '\'' + ", title='" + title + '\'' + ", employer='" + employer + '\'' + ", salary='" + salary + '\'' + ", descriptionHTML='" + descriptionHTML + '\'' + ", skills='" + skills + '\'' + ", createdAt=" + createdAt + '}';
    }
}
