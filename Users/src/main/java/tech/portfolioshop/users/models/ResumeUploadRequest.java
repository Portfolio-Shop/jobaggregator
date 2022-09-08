package tech.portfolioshop.users.models;

public class ResumeUploadRequest {
    private Byte[] resume;

    public ResumeUploadRequest(Byte[] resume) {
        this.resume = resume;
    }

    public Byte[] getResume() {
        return resume;
    }

    public void setResume(Byte[] resume) {
        this.resume = resume;
    }
}
