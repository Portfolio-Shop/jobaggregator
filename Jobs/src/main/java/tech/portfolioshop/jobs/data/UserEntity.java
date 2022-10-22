package tech.portfolioshop.jobs.data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    private String email;
    private String phone;
    private String parsedResume;

    @OneToMany(mappedBy = "id")
    private List<SearchEntity> searches;

    @ManyToMany
    @JoinTable(
            name = "users_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<SkillsEntity> skills;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public UserEntity() {
    }

    public UserEntity(String uniqueId, String userId, String email, String phone, String parsedResume) {
        this.userId = userId;
        this.email = email;
        this.phone = phone;
        this.parsedResume = parsedResume;
    }

    public UserEntity(Long id, String uniqueId, String userId, String email, String phone, String parsedResume, List<SkillsEntity> skills, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.phone = phone;
        this.parsedResume = parsedResume;
        this.skills = skills;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getParsedResume() {
        return parsedResume;
    }

    public void setParsedResume(String parsedResume) {
        this.parsedResume = parsedResume;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<SearchEntity> getSearches() {
        return searches;
    }

    public void setSearches(List<SearchEntity> searches) {
        this.searches = searches;
    }

    public List<SkillsEntity> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillsEntity> skills) {
        this.skills = skills;
    }
}
