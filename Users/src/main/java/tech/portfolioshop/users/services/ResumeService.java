package tech.portfolioshop.users.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.portfolioshop.users.data.UserEntity;
import tech.portfolioshop.users.data.UserRepository;
import tech.portfolioshop.users.shared.ResumeDto;

@Service
public class ResumeService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    public ResumeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void uploadResume(ResumeDto resumeDto) {
        UserEntity userEntity = userRepository.findByUserId(resumeDto.getUserId());
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }
        userEntity.setResume(resumeDto.getResume());
        userRepository.save(userEntity);
    }

    public ResumeDto getResume(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity==null){
            throw new RuntimeException("User Not Found");
        }
        return modelMapper.map(userEntity, ResumeDto.class);
    }
}
