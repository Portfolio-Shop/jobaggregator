package tech.portfolioshop.jobs.data;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity getByUserId(String userId);
}
