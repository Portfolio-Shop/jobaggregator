package tech.portfolioshop.users.models.http.request;

import java.util.Arrays;

public class UserUpdateRequest {
    /**
     * All User Data Update Requests are wrapped as an instance of this model.
     */
    private String name;
    private String phone;
    private Byte[] image;

    public UserUpdateRequest(String name, String phone, Byte[] image) {
        this.name = name;
        this.phone = phone;
        this.image = image;
    }

    public UserUpdateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "UserUpdateRequest{" + "name='" + name + '\'' + ", phone='" + phone + '\'' + ", image=" + Arrays.toString(image) + '}';
    }
}
