package org.jobaggregator.kafka.payload;

import org.json.JSONObject;

import static org.jobaggregator.kafka.config.KafkaTopics.USER_DELETED;

public class UserDeleted extends Payload{
    private String userId;

    public UserDeleted() {
        super(USER_DELETED);
    }

    public UserDeleted(String userId) {
        super(USER_DELETED);

        this.userId = userId;
    }

    public String serialize(){
        JSONObject obj = new JSONObject();
        obj.put("userId", userId);
        return obj.toString();
    }
    public UserDeleted deserialize(String json){
        JSONObject obj = new JSONObject(json);
        this.userId = obj.getString("userId");
        return this;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
