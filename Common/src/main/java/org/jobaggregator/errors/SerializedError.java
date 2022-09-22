package org.jobaggregator.errors;

import javax.validation.constraints.NotNull;

public class SerializedError {

    @NotNull
    private String message;
    private String field;

    public SerializedError(@NotNull String message, String field) {
        this.message = message;
        this.field = field;
    }
    public SerializedError(@NotNull String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    @Override
    public String toString() {
        String result = "{";
        result += "\"message\": \"" + message + "\"";
        if (field != null) {
            result += ", \"field\": \"" + field + "\"";
        }
        result += "}";
        return result;
    }
}
