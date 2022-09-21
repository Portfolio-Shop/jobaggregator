package tech.portfolioshop.jobs.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends CrudRepository<JobsEntity, Long> {
    @Query("SELECT j FROM JobsEntity j WHERE j.query=?1 AND j.location=?2")
    List<JobsEntity> findByQueryAndLocation(String query, String location);
}
