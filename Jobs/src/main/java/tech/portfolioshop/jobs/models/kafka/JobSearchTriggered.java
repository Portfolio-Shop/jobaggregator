package tech.portfolioshop.jobs.models.kafka;

import org.json.JSONObject;

public class JobSearchTriggered extends Payload{

    private String query;
    private String location;

    public JobSearchTriggered(String topic) {
        super(topic);
    }

    public JobSearchTriggered(String query, String location) {
        super("JOB_SEARCH_TRIGGERED");
        this.query = query;
        this.location = location;
    }

    @Override
    public String serialize() {
        JSONObject json = new JSONObject();
        json.put("query", query);
        json.put("location", location);
        return json.toString();
    }

    @Override
    public void deserialize(String json) {
        JSONObject obj = new JSONObject(json);
        this.query = obj.getString("query");
        this.location = obj.getString("location");
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
}
