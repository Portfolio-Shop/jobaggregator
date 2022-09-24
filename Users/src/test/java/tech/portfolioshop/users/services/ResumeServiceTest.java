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
import org.springframework.test.context.TestPropertySource;
import tech.portfolioshop.users.data.UserEntity;
import tech.portfolioshop.users.data.UserRepository;
import tech.portfolioshop.users.shared.ResumeDto;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(
        locations = "classpath:application.test.properties"
)
class ResumeServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ResumeService resumeService;

    private String getRandomString() {
        Random random = new Random();
        return String.valueOf(random.nextInt());
    }

    private ResumeDto getMockResumeDto() {
        ResumeDto resumeDto = new ResumeDto();
        byte[] pdfBody = new byte[200];
        new Random().nextBytes(pdfBody);
        resumeDto.setResume(pdfBody);
        resumeDto.setUserId(getRandomString());
        return resumeDto;
    }

    private UserEntity getMockUserEntity() {
        return new UserEntity(
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                new byte[10],
                new byte[10]
        );
    }

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    @DisplayName("Upload resume successful test")
    void uploadResumeSuccessful() throws NotFoundException {
        ResumeDto resumeDto = getMockResumeDto();
        UserEntity userEntity = getMockUserEntity();
        Mockito.when(userRepository.findByUserId(resumeDto.getUserId())).thenReturn(userEntity);
        Mockito.when(userRepository.save(userEntity)).thenReturn(Mockito.any(UserEntity.class));
        resumeService.uploadResume(resumeDto);
        Mockito.verify(userRepository, Mockito.times(1)).save(userEntity);
    }

    @Test
    @DisplayName("Upload resume fail test with runtime exception")
    void uploadResumeException() {
        ResumeDto resumeDto = getMockResumeDto();
        UserEntity userEntity = getMockUserEntity();
        Mockito.when(userRepository.findByUserId(resumeDto.getUserId())).thenReturn(null);
        assertThrows(NotFoundException.class, () ->{
            resumeService.uploadResume(resumeDto);
        });
        Mockito.verify(userRepository, Mockito.never()).save(userEntity);
    }

    @Test
    @DisplayName("Get resume successful test")
    void getResumeSuccessful() throws NotFoundException {
        UserEntity userEntity = getMockUserEntity();
        Mockito.when(userRepository.findByUserId(userEntity.getUserId())).thenReturn(userEntity);
        resumeService.getResume(userEntity.getUserId());
        Mockito.verify(userRepository, Mockito.times(1)).findByUserId(userEntity.getUserId());
    }

    @Test
    @DisplayName("Get resume fail test")
    void getResumeException(){
        UserEntity userEntity = getMockUserEntity();
        Mockito.when(userRepository.findByUserId(userEntity.getUserId())).thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            resumeService.getResume(userEntity.getUserId());
        });
    }
}