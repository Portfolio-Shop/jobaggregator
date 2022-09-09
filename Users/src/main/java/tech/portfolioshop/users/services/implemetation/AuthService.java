package tech.portfolioshop.users.services.implemetation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.portfolioshop.users.data.database.UserEntity;
import tech.portfolioshop.users.data.database.UserRepository;
import tech.portfolioshop.users.shared.UserDto;

@Service
public class AuthService implements tech.portfolioshop.users.services.interfaces.AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto signup(UserDto userDetails) {
        String encryptedPassword = bCryptPasswordEncoder.encode(userDetails.getPassword());
        userDetails.setEncryptedPassword(encryptedPassword);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        userEntity.setUserId(java.util.UUID.randomUUID().toString());
        userEntity.setStatus(true);
        userEntity.setVerified(false);
        userRepository.save(userEntity);
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
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