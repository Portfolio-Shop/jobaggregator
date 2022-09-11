package tech.portfolioshop.users.data.database;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.email = ?1 AND u.status = true")
    UserEntity findByEmail(String email);
    @Query("SELECT u FROM UserEntity u WHERE u.userId = ?1 AND u.status = true")
    UserEntity findByUserId(String userId);
}
