package org.jobaggregator.kafka.payload;

import org.json.JSONObject;
import java.lang.reflect.Field;

public class ScrapperJobsResult extends Payload{

    private String query;
    private String location;
    private String title;
    private String employer;
    private String salary;
    private String descriptionHTML;
    private String skills;

    public ScrapperJobsResult() {
        super("SCRAPPER_JOBS_RESULT");
    }

    public ScrapperJobsResult(String query, String location, String title, String employer, String salary, String descriptionHTML, String skills) {
        super("SCRAPPER_JOBS_RESULT");
        this.query = query;
        this.location = location;
        this.title = title;
        this.employer = employer;
        this.salary = salary;
        this.descriptionHTML = descriptionHTML;
        this.skills = skills;
    }

    @Override
    public String serialize() throws IllegalAccessException {
        JSONObject jsonObject = new JSONObject();
        Field[] scrapperJobsResultFields = this.getClass().getFields();
        for(Field field:scrapperJobsResultFields){
            jsonObject.put(field.getName(), field.get(this));
        }
        return jsonObject.toString();
    }

    @Override
    public void deserialize(String json) throws IllegalAccessException {
        JSONObject jsonObject = new JSONObject(json);
        Field[] scrapperJobsResultFields = this.getClass().getFields();
        for(Field field:scrapperJobsResultFields){
            field.set(this, jsonObject.get(field.getName()));
        }
    }
}
