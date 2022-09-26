package tech.portfolioshop.users.services;

import org.jobaggregator.errors.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import tech.portfolioshop.users.data.UserEntity;
import tech.portfolioshop.users.data.UserRepository;
import tech.portfolioshop.users.shared.UserDto;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(
        locations = "classpath:application.test.properties"
)
public class ProfileServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private  ProfileService profileService;

    private UserDto getMockUserDto(){
        UserDto userDto = new UserDto();
        userDto.setUserId(getRandomString());
        userDto.setName("test");
        userDto.setEmail("test@test.test");
        userDto.setPhone("1234567890");
        userDto.setPassword("test1234567890");
        return userDto;
    }

    private String getRandomString() {
        Random random = new Random();
        return String.valueOf(random.nextInt());
    }

    private UserEntity getMockUserEntity() {
        UserEntity userEntity = new ModelMapper().map(getMockUserDto(), UserEntity.class);
        userEntity.setUserId(java.util.UUID.randomUUID().toString());
        userEntity.setEncryptedPassword(new BCryptPasswordEncoder().encode(getMockUserDto().getPassword()));
        return userEntity;
    }

    @Test
    @DisplayName("Get User by id successful service call")
    public void getUserByIdSuccessfully() throws NotFoundException {
        String userId = getRandomString();
        Mockito.when(userRepository.findByUserId(userId)).thenReturn(getMockUserEntity());
        profileService.getUserByUserId(userId);
        Mockito.verify(userRepository, Mockito.times(1)).findByUserId(Mockito.anyString());
    }

    @Test
    @DisplayName("Get User by id failed service call")
    public void getUserByIdFail(){
        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(null);
        assertThrows(NotFoundException.class, () ->{
            profileService.getUserByUserId(Mockito.anyString());
        });
        Mockito.verify(userRepository, Mockito.times(1)).findByUserId(Mockito.anyString());
    }

    @Test
    @DisplayName("Update User service call success with valid flow")
    public void updateUserSuccessful() throws NotFoundException {
        UserDto userDto = getMockUserDto();
        UserEntity userEntity = getMockUserEntity();
        Mockito.when(userRepository.findByUserId(userDto.getUserId())).thenReturn(userEntity);
        Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);
        profileService.updateUser(userDto);
        Mockito.verify(userRepository, Mockito.times(1)).findByUserId(userDto.getUserId());
        Mockito.verify(userRepository, Mockito.times(1)).save(userEntity);
    }

    @Test
    @DisplayName("Update User service call fail with invalid flow")
    public void updateUserFail(){
        UserDto userDto = getMockUserDto();
        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            profileService.updateUser(userDto);
        });
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(UserEntity.class));
    }

    @Test
    @DisplayName("Delete User successful service call with valid flow")
    public void deleteSuccessful() throws NotFoundException {
        String userId = getRandomString();
        UserEntity userEntity = getMockUserEntity();
        Mockito.when(userRepository.findByUserId(userId)).thenReturn(userEntity);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
        profileService.deleteUser(userId);
        Mockito.verify(userRepository, Mockito.times(1)).findByUserId(userId);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }

    @Test
    @DisplayName("Delete User failed service call with invalid flow")
    public void deleteFail(){
        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            profileService.deleteUser(Mockito.anyString());
        });
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(UserEntity.class));
    }
}
