package tech.portfolioshop.users.services.interfaces;

import tech.portfolioshop.users.shared.ResumeDto;

public interface ResumeService {
    ResumeDto getResume(String userId);
    void uploadResume(ResumeDto resumeDto);
}
