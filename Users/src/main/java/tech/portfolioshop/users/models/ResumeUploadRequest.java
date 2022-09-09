package tech.portfolioshop.users.models;

import org.springframework.web.multipart.MultipartFile;

public class ResumeUploadRequest {
    private MultipartFile resume;

    public ResumeUploadRequest(MultipartFile resume) {
        this.resume = resume;
    }

    public MultipartFile getResume() {
        return resume;
    }

    public void setResume(MultipartFile resume) {
        this.resume = resume;
    }
}
