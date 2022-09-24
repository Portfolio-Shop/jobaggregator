package org.jobaggregator.kafka.payload;

import org.json.JSONObject;

import static org.jobaggregator.kafka.config.KafkaTopics.USER_CREATED;

public class UserCreated extends Payload{
    private String name;
    private String email;
    private String userId;
    private String phone;


    public UserCreated() {
        super(USER_CREATED);
    }

    public UserCreated(String name, String email, String userId, String phone) {
        super(USER_CREATED);
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.phone = phone;
    }

    public String serialize(){
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("email", email);
        obj.put("userId", userId);
        obj.put("phone", phone);
        return obj.toString();
    }
    public UserCreated deserialize(String json){
        JSONObject obj = new JSONObject(json);
        name = obj.getString("name");
        email = obj.getString("name");
        userId = obj.getString("userId");
        if(obj.has("phone")){
            phone = obj.getString("phone");
        }
        return this;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
