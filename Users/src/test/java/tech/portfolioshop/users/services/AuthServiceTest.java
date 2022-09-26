package tech.portfolioshop.users.services;

import org.jobaggregator.errors.BadRequestException;
import org.jobaggregator.errors.NotFoundException;
import org.jobaggregator.errors.UnauthorizedException;
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
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;


    private UserDto getMockUser() {
        UserDto userDto = new UserDto();
        userDto.setName("test");
        userDto.setEmail("test@test.test");
        userDto.setPhone("1234567890");
        userDto.setPassword("test1234567890");
        return userDto;
    }

    private UserEntity getMockUserEntity() {
        UserEntity userEntity = new ModelMapper().map(getMockUser(), UserEntity.class);
        userEntity.setUserId(java.util.UUID.randomUUID().toString());
        userEntity.setEncryptedPassword(new BCryptPasswordEncoder().encode(getMockUser().getPassword()));
        return userEntity;
    }

    private String getRandomString() {
        Random random = new Random();
        return String.valueOf(random.nextInt());
    }

    @Test
    @DisplayName("Test signup method")
    public void signupSuccessfully() {
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(getMockUserEntity());
        UserDto userDto = getMockUser();
        authService.signup(userDto);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }

    @Test
    @DisplayName("Signup method Fails with null password")
    public void signupFailedWithNullPassword() {
        UserDto userDto = getMockUser();
        userDto.setPassword(null);
        assertThrows(BadRequestException.class, () -> {
            authService.signup(userDto);
        });
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(UserEntity.class));
    }

    @Test
    @DisplayName("Sign In method Successfully")
    public void signInSuccessfully() throws UnauthorizedException, NotFoundException {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getMockUserEntity());
        UserDto userDto = getMockUser();
        authService.signin(userDto);
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(Mockito.anyString());
    }

    @Test
    @DisplayName("Sign In method Fails with wrong password")
    public void signInFailedWithWrongPassword() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getMockUserEntity());
        UserDto userDto = getMockUser();
        userDto.setPassword(getRandomString());
        assertThrows(UnauthorizedException.class, () -> {
            authService.signin(userDto);
        });
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(Mockito.anyString());
    }

    @Test
    @DisplayName("Sign In method Fails with null password")
    public void signInFailedWithNullPassword() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getMockUserEntity());
        UserDto userDto = getMockUser();
        userDto.setPassword(null);
        assertThrows(UnauthorizedException.class, () -> {
            authService.signin(userDto);
        });
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(Mockito.anyString());
    }
}