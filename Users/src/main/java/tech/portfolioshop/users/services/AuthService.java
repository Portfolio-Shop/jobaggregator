package tech.portfolioshop.users.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.portfolioshop.users.data.UserEntity;
import tech.portfolioshop.users.data.UserRepository;
import tech.portfolioshop.users.shared.UserDto;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    public AuthService(
            UserRepository userRepository,
            ModelMapper modelMapper,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            KafkaTemplate<String,String> kafkaTemplate
    ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.kafkaTemplate = kafkaTemplate;
    }

    public UserDto signup(UserDto userDetails) {
        String encryptedPassword = bCryptPasswordEncoder.encode(userDetails.getPassword());
        userDetails.setEncryptedPassword(encryptedPassword);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        userEntity.setUserId(java.util.UUID.randomUUID().toString());
        userEntity.setStatus(true);
        userEntity.setVerified(false);
        userRepository.save(userEntity);
        kafkaTemplate.send("Hello", "Hello");
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserDto signin(UserDto userDetails) {
        UserEntity userEntity = userRepository.findByEmail(userDetails.getEmail());
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }
        if (!bCryptPasswordEncoder.matches(userDetails.getPassword(), userEntity.getEncryptedPassword())) {
            throw new RuntimeException("Wrong password");
        }
        return modelMapper.map(userEntity, UserDto.class);
    }
}