package tech.portfolioshop.users.data.database;

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
    private Byte[] image;

    @Column(nullable = true)
    @Lob
    private Byte[] resume;

    public UserEntity() {
    }

    public UserEntity(String userId, String name, String email, String encryptedPassword, String phone, Byte[] image, Byte[] resume) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.phone = phone;
        this.image = image;
        this.resume = resume;
    }

    public UserEntity(Integer id, String userId, String name, String email, String encryptedPassword, String phone, Byte[] image, Byte[] resume) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.phone = phone;
        this.image = image;
        this.resume = resume;
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

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Byte[] getResume() {
        return resume;
    }

    public void setResume(Byte[] resume) {
        this.resume = resume;
    }
}
