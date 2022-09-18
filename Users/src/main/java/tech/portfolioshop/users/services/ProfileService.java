package tech.portfolioshop.users.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.portfolioshop.users.data.UserEntity;
import tech.portfolioshop.users.data.UserRepository;
import tech.portfolioshop.users.shared.UserDto;

@Service
public class ProfileService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto getUserByUserId(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return modelMapper.map(user, UserDto.class);
    }

    public void updateUser(UserDto userDetails) {
        UserEntity user = userRepository.findByUserId(userDetails.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if(userDetails.getName() != null) {
            user.setName(userDetails.getName());
        }
        if(userDetails.getImage() != null) {
            user.setImage(userDetails.getImage());
        }
        if(userDetails.getPhone() != null) {
            user.setPhone(userDetails.getPhone());
        }
        userRepository.save(user);
    }

    public void deleteUser(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setStatus(false);
        userRepository.save(user);
    }

}
