package tech.portfolioshop.users.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignUpRequest {
    @NotNull(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min=8, max=16, message = "Password must be between 8 and 16 characters long")
    private String password;

    @NotNull(message = "Name is required")
    private String name;

    private String phone;
    private Byte[] image;

    public SignUpRequest(String email, String password, String name, String phone, Byte[] image) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.image = image;
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