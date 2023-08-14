package account.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserUpdateAccessRequest {
    @NotEmpty(message = "User field cannot be empty!")
    @Email
    private String user;
    @NotEmpty
    @Pattern(regexp = "^(LOCK|UNLOCK)$", message = "Operation must be either LOCK or UNLOCK")
    private String operation;

    public UserUpdateAccessRequest() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
