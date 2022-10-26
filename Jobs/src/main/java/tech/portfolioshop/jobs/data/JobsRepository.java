package tech.portfolioshop.jobs.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends CrudRepository<JobsEntity, Long> {
    @Query("SELECT j FROM JobsEntity j WHERE j.query=?1 AND j.location=?2")
    List<JobsEntity> findByQueryAndLocation(String query, String location);

    @Query("SELECT j FROM JobsEntity j WHERE j.query=?1")
    List<JobsEntity> findByQuery(String query);

    @Query("SELECT j FROM JobsEntity j WHERE j.location=?1")
    List<JobsEntity> findByLocation(String location);

    @Query("SELECT s.jobs from SkillsEntity s JOIN s.users u WHERE u.userId=?1")
    List<JobsEntity> findByRecommendation(String userId);
}
