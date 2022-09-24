package tech.portfolioshop.users.services;

import org.jobaggregator.errors.NotFoundException;
import org.jobaggregator.errors.UnauthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.portfolioshop.users.data.UserEntity;
import tech.portfolioshop.users.data.UserRepository;
import tech.portfolioshop.users.shared.UserDto;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Autowired
    public AuthService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

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

    public UserDto signin(UserDto userDetails) throws NotFoundException, UnauthorizedException {
        UserEntity userEntity = userRepository.findByEmail(userDetails.getEmail());
        if (userEntity == null) {
            throw new NotFoundException("User not found");
        }
        if (!bCryptPasswordEncoder.matches(userDetails.getPassword(), userEntity.getEncryptedPassword())) {
            throw new UnauthorizedException("Wrong password");
        }
        return modelMapper.map(userEntity, UserDto.class);
    }
}