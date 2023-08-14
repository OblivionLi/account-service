package account.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private final String timestamp;
    private final int status;
    private final String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;
    private final String path;

    public ErrorResponse(HttpStatus status, String error, String message, String path) {
        this.timestamp = String.valueOf(System.currentTimeMillis());;
        this.status = status.value();;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
