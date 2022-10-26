package tech.portfolioshop.jobs.data;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface SkillsRepository extends CrudRepository<SkillsEntity, Long> {
    @Query("SELECT s.name FROM SkillsEntity s")
    List<String> findAllSkills();

    @Query("SELECT s FROM SkillsEntity s WHERE s.name IN ?1")
    List<SkillsEntity> findByName(List<String> skills);

    @Modifying
    @Query(value = "INSERT INTO skills (name) VALUES (?1) ON CONFLICT DO NOTHING", nativeQuery = true)
    @Transactional
    void addSkillIfNotPresent(List<String> skills);
}
