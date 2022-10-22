package tech.portfolioshop.jobs.data;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transaction;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity getByUserId(String userId);

    void deleteByUserId(String userId);

    Transaction beginTransaction();
}
