package tech.portfolioshop.jobs.listeners;

import org.jobaggregator.kafka.config.KafkaTopics;
import org.jobaggregator.kafka.payload.UserResumeParsed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tech.portfolioshop.jobs.data.SkillsEntity;
import tech.portfolioshop.jobs.data.SkillsRepository;
import tech.portfolioshop.jobs.data.UserEntity;
import tech.portfolioshop.jobs.data.UserRepository;

import java.util.List;

@EnableKafka
@Service
public class UsersResumeParsedListener {

    private final UserRepository userRepository;
    private final SkillsRepository skillsRepository;

    @Autowired
    public UsersResumeParsedListener(UserRepository userRepository, SkillsRepository skillsRepository) {
        this.userRepository = userRepository;
        this.skillsRepository = skillsRepository;
    }

    @KafkaListener(topics = KafkaTopics.USER_RESUME_PARSED, groupId = "${spring.application.name}")
    public void userResumeParsed(String message) {
        UserResumeParsed user = new UserResumeParsed().deserialize(message);
        String userId = user.getUserId();
        UserEntity userEntity = userRepository.getByUserId(userId);
        skillsRepository.addSkillIfNotPresent(user.getSkills());
        List<SkillsEntity> skills = skillsRepository.findByName(user.getSkills());
        userEntity.setSkills(skills);
        userRepository.save(userEntity);
    }
}
