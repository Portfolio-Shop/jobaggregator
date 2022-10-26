package tech.portfolioshop.jobs.listeners;

import org.jobaggregator.kafka.config.KafkaTopics;
import org.jobaggregator.kafka.payload.UserDeleted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tech.portfolioshop.jobs.data.UserRepository;

@EnableKafka
@Service
public class UsersDeletedListener {

    private final UserRepository userRepository;

    @Autowired
    public UsersDeletedListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @KafkaListener(topics = KafkaTopics.USER_DELETED, groupId = "${spring.application.name}")
    public void userDeleted(String message) {
        UserDeleted user = new UserDeleted().deserialize(message);
        String userId = user.getUserId();
        userRepository.deleteByUserId(userId);
    }
}
