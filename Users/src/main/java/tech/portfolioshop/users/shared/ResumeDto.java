package tech.portfolioshop.users.shared;

import java.io.Serializable;
import java.util.Arrays;

public class ResumeDto implements Serializable {
    /**
     * Data Transfer Object for the service. Used internally to parse and communicate with resume data wrapped as an instance of this class.
     */
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

    @Override
    public String toString() {
        return "ResumeDto{" +
                "userId='" + userId + '\'' +
                ", resume=" + Arrays.toString(resume) +
                '}';
    }
}
