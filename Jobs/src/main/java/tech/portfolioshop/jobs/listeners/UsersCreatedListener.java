package tech.portfolioshop.jobs.listeners;

import org.jobaggregator.kafka.config.KafkaTopics;
import org.jobaggregator.kafka.payload.UserCreated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;

import tech.portfolioshop.jobs.data.UserEntity;
import tech.portfolioshop.jobs.data.UserRepository;

@EnableKafka
@Service
public class UsersCreatedListener {

    private final ModelMapper modelMapper = new ModelMapper();
    private final UserRepository userRepository;

    @Autowired
    public UsersCreatedListener(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
