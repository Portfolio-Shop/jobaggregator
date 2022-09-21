package tech.portfolioshop.jobs.listeners;

import org.jobaggregator.kafka.payload.UserCreated;
import org.jobaggregator.kafka.payload.UserDeleted;
import org.jobaggregator.kafka.payload.UserUpdated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tech.portfolioshop.jobs.data.UserEntity;
import tech.portfolioshop.jobs.data.UserRepository;

@EnableKafka
@Component
public class UsersListener {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public UsersListener(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    @KafkaListener(topics = "USER_CREATED", groupId = "${spring.application.name}")
    public void userCreated(String message) {
        UserCreated user = new UserCreated(null, null,null,null);
        user.deserialize(message);
        String userId = user.getUserId();
        user.setUserId(null);
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setUserId(userId);
        userRepository.save(userEntity);
    }
    @KafkaListener(topics = "USER_UPDATED", groupId = "${spring.application.name}")
    public void userUpdated(String message) {
        UserUpdated user = new UserUpdated(null, null,null);
        user.deserialize(message);
        String userId = user.getUserId();
        user.setUserId(null);
        UserEntity userEntity = modelMapper.map(user,UserEntity.class);
        userEntity.setUserId(userId);
        userRepository.save(userEntity);
    }

    @KafkaListener(topics = "USER_DELETED", groupId = "${spring.application.name}")
    public void userDeleted(String message) {
        UserDeleted user = new UserDeleted(null);
        user.deserialize(message);
        String userId = user.getUserId();
        userRepository.deleteByUserId(userId);
    }
}
