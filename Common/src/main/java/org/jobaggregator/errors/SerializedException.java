package org.jobaggregator.errors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class SerializedException {

    @NotNull
    private String message;
    @NotNull
    private String name;
    private String field;

    private final LocalDateTime timestamp= LocalDateTime.now();

    public SerializedException(@NotNull String message,@NotNull String name, String field) {
        this.message = message;
        this.name = name;
        this.field = field;
    }
    public SerializedException(@NotNull String message, @NotNull String name) {
        this.message = message;
        this.name = name;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
}
