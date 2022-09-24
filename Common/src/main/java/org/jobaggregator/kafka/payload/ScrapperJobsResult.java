package org.jobaggregator.kafka.payload;

import org.json.JSONObject;

import static org.jobaggregator.kafka.config.KafkaTopics.SCRAPPER_JOBS_RESULT;

public class ScrapperJobsResult extends Payload{

    private String query;
    private String location;
    private String title;
    private String employer;
    private String salary;
    private String descriptionHTML;
    private String skills;

    public ScrapperJobsResult() {
        super(SCRAPPER_JOBS_RESULT);
    }

    public ScrapperJobsResult(String query, String location, String title, String employer, String salary, String descriptionHTML, String skills) {
        super(SCRAPPER_JOBS_RESULT);
        this.query = query;
        this.location = location;
        this.title = title;
        this.employer = employer;
        this.salary = salary;
        this.descriptionHTML = descriptionHTML;
        this.skills = skills;
    }

    @Override
    public String serialize() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", query);
        jsonObject.put("location", location);
        jsonObject.put("title", title);
        jsonObject.put("employer", employer);
        jsonObject.put("salary", salary);
        jsonObject.put("descriptionHTML", descriptionHTML);
        jsonObject.put("skills", skills);
        return jsonObject.toString();
    }

    @Override
    public ScrapperJobsResult deserialize(String json) {
        JSONObject jsonObject = new JSONObject(json);
        this.query = jsonObject.getString("query");
        this.location = jsonObject.getString("location");
        this.title = jsonObject.getString("title");
        this.employer = jsonObject.getString("employer");
        this.salary = jsonObject.getString("salary");
        this.descriptionHTML = jsonObject.getString("descriptionHTML");
        this.skills = jsonObject.getString("skills");
        return this;
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
}
