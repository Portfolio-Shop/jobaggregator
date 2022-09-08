package tech.portfolioshop.users.models;

public class UserUpdateRequest {
    private String name;
    private String phone;
    private Byte[] image;

    public UserUpdateRequest(String name, String phone, Byte[] image) {
        this.name = name;
        this.phone = phone;
        this.image = image;
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
}
