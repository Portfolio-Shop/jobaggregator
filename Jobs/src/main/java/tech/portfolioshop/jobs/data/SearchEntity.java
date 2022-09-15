package tech.portfolioshop.jobs.data;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="searches")
public class SearchEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_user" ))
    private UserEntity user;

    @Column(nullable = false)
    private String query;

    @Column(nullable = true)
    private String location;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public SearchEntity() {
    }

    public SearchEntity(UserEntity user, String query, String location) {
        this.user = user;
        this.query = query;
        this.location = location;
    }

    public SearchEntity(Long id, UserEntity user, String query, String location, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.query = query;
        this.location = location;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
