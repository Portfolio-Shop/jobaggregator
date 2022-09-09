package tech.portfolioshop.users.shared;

public class ResumeDto {
    private String userId;
    private byte[] resume;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }
}
