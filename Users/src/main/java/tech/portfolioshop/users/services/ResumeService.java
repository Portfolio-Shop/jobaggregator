package tech.portfolioshop.users.services;

import org.jobaggregator.errors.NotFoundException;
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

    public void uploadResume(ResumeDto resumeDto) throws NotFoundException {
        UserEntity userEntity = userRepository.findByUserId(resumeDto.getUserId());
        if (userEntity == null) {
            throw new NotFoundException("User not found");
        }
        userEntity.setResume(resumeDto.getResume());
        userRepository.save(userEntity);
    }

    public ResumeDto getResume(String userId) throws NotFoundException {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity==null){
            throw new NotFoundException("User not found");
        }
        return modelMapper.map(userEntity, ResumeDto.class);
    }
}
