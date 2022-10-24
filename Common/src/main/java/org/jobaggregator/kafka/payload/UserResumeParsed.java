package org.jobaggregator.kafka.payload;

import org.jobaggregator.kafka.config.KafkaTopics;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserResumeParsed extends Payload{
    private String userId;
    private List<String> skills;

    public UserResumeParsed() {
        super(KafkaTopics.USER_RESUME_PARSED);
    }

    public UserResumeParsed(String userId, List<String> skills) {
        super(KafkaTopics.USER_RESUME_PARSED);
        this.userId = userId;
        this.skills = skills;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getSkills() {
        return skills;
    }
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    @Override
    public String serialize(){
        JSONObject obj = new JSONObject();
        obj.put("userId", userId);
        obj.put("skills", skills);
        return obj.toString();
    }

    @Override
    public UserResumeParsed deserialize(String json) {
        JSONObject obj = new JSONObject(json);
        userId = obj.getString("userId");
        skills = new ArrayList<>();
        for(Object s : obj.getJSONArray("skills").toList()){
            if (s instanceof String){
                skills.add((String) s);
            }
        }
        return this;
    }
}
