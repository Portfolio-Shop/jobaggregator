package org.jobaggregator.kafka.payload;

import org.json.JSONObject;

import static org.jobaggregator.kafka.config.KafkaTopics.JOB_SEARCH_TRIGGERED;

public class JobSearchTriggered extends Payload {
    private String query;
    private String location;


    public JobSearchTriggered() {
        super(JOB_SEARCH_TRIGGERED);
    }

    public JobSearchTriggered(String query, String location) {
        super(JOB_SEARCH_TRIGGERED);
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
    public JobSearchTriggered deserialize(String json) {
        JSONObject obj = new JSONObject(json);
        this.query = obj.getString("query");
        this.location = obj.getString("location");
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
}
