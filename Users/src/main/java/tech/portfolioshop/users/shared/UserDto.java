package tech.portfolioshop.users.shared;

import java.io.Serializable;
import java.util.Arrays;

public class UserDto implements Serializable {
    /**
     * Data Transfer Object for the service. Used internally to parse and communicate with user data wrapped as an instance of this class.
     */
    private String name;
    private String email;
    private String password;
    private String userId;
    private String phone;
    private byte[] image;
    private String encryptedPassword;
    private boolean verified;
    private boolean status;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userId='" + userId + '\'' +
                ", phone='" + phone + '\'' +
                ", image=" + Arrays.toString(image) +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", verified=" + verified +
                ", status=" + status +
                '}';
    }
}
