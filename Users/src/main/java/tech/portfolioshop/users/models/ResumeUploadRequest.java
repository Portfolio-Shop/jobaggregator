package tech.portfolioshop.users.models;

import org.springframework.web.multipart.MultipartFile;

public class ResumeUploadRequest {
    /**
     * All Resume Upload Requests are wrapped as an instance of this model.
     */
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

    @Override
    public String toString() {
        return "ResumeUploadRequest{" +
                "resume=" + resume +
                '}';
    }
}
