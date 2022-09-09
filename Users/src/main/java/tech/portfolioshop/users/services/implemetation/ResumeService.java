package tech.portfolioshop.users.services.implemetation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.portfolioshop.users.data.database.UserEntity;
import tech.portfolioshop.users.data.database.UserRepository;
import tech.portfolioshop.users.shared.ResumeDto;

@Service
public class ResumeService implements tech.portfolioshop.users.services.interfaces.ResumeService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public ResumeService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void uploadResume(ResumeDto resumeDto) {
        UserEntity userEntity = userRepository.findByUserId(resumeDto.getUserId());
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }
        userEntity.setResume(resumeDto.getResume());
        userRepository.save(userEntity);
    }

    @Override
    public ResumeDto getResume(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        return modelMapper.map(userEntity, ResumeDto.class);
    }
}
