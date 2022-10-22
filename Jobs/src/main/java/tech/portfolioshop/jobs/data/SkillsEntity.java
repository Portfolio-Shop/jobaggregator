package tech.portfolioshop.jobs.data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "skills")
public class SkillsEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    private Set<UserEntity> users;

    @ManyToMany(mappedBy = "skills")
    private Set<JobsEntity> jobs;

    public SkillsEntity() {
    }

    public SkillsEntity(Long id, String name, Set<UserEntity> users, Set<JobsEntity> jobs) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.jobs = jobs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public Set<JobsEntity> getJobs() {
        return jobs;
    }

    public void setJobs(Set<JobsEntity> jobs) {
        this.jobs = jobs;
    }

}
