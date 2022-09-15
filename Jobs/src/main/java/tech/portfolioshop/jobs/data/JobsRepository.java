package tech.portfolioshop.jobs.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobsRepository extends CrudRepository<JobsEntity, Long> {

    @Query("SELECT j FROM JobsEntity j WHERE j.query=?1 AND j.location=?2")
    List<JobsEntity> findByQueryandLocation(String query, String location);
}
