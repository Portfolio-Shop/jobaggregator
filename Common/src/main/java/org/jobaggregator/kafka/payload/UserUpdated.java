package org.jobaggregator.kafka.payload;

import org.json.JSONObject;

import static org.jobaggregator.kafka.config.KafkaTopics.USER_UPDATED;

public class UserUpdated extends Payload{
    private String name;
    private String userId;
    private String phone;

    public UserUpdated() {
        super(USER_UPDATED);
    }

    public UserUpdated(String name, String userId, String phone) {
        super(USER_UPDATED);

        this.name = name;
        this.userId = userId;
        this.phone = phone;
    }

    public String serialize(){
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("userId", userId);
        obj.put("phone", phone);
        return obj.toString();
    }
    public UserUpdated deserialize(String json){
        JSONObject obj = new JSONObject(json);
        name = obj.getString("name");
        userId = obj.getString("userId");
        phone = obj.getString("phone");
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
