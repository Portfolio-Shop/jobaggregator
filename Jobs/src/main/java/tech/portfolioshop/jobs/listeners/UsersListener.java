package tech.portfolioshop.jobs.listeners;

import org.jobaggregator.kafka.config.KafkaTopics;
import org.jobaggregator.kafka.payload.UserCreated;
import org.jobaggregator.kafka.payload.UserDeleted;
import org.jobaggregator.kafka.payload.UserResumeParsed;
import org.jobaggregator.kafka.payload.UserUpdated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;

import tech.portfolioshop.jobs.data.SkillsEntity;
import tech.portfolioshop.jobs.data.SkillsRepository;
import tech.portfolioshop.jobs.data.UserEntity;
import tech.portfolioshop.jobs.data.UserRepository;

import java.util.List;

@EnableKafka
@Service
public class UsersListener {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final SkillsRepository skillsRepository;

    @Autowired
    public UsersListener(ModelMapper modelMapper, UserRepository userRepository, SkillsRepository skillsRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.skillsRepository = skillsRepository;
    }


    @KafkaListener(topics = KafkaTopics.USER_CREATED, groupId = "${spring.application.name}")
    public void userCreated(String message) {
        System.out.println(message);
        UserCreated user = new UserCreated().deserialize(message);
        String userId = user.getUserId();
        user.setUserId(null);
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setUserId(userId);
        userRepository.save(userEntity);
    }
    @KafkaListener(topics = KafkaTopics.USER_UPDATED, groupId = "${spring.application.name}")
    public void userUpdated(String message) {
        UserUpdated user = new UserUpdated().deserialize(message);
        String userId = user.getUserId();
        user.setUserId(null);
        UserEntity userEntity = modelMapper.map(user,UserEntity.class);
        userEntity.setUserId(userId);
        userRepository.save(userEntity);
    }

    @KafkaListener(topics = KafkaTopics.USER_DELETED, groupId = "${spring.application.name}")
    public void userDeleted(String message) {
        UserDeleted user = new UserDeleted().deserialize(message);
        String userId = user.getUserId();
        userRepository.deleteByUserId(userId);
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
