package tech.portfolioshop.users.data;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;

    @Column(nullable = true)
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] resume;

    @Column(nullable = false)
    private boolean verified;

    @Column(nullable = false)
    private boolean status;

    public UserEntity() {
    }

    public UserEntity(String userId, String name, String email, String encryptedPassword, String phone, byte[] image, byte[] resume) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.phone = phone;
        this.image = image;
        this.resume = resume;
    }

    public UserEntity(Integer id, String userId, String name, String email, String encryptedPassword, String phone, byte[] image, byte[] resume, boolean verified, boolean status) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.phone = phone;
        this.image = image;
        this.resume = resume;
        this.verified = verified;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
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

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
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
}
