package account.model.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserUpdateRoleRequest {
    @NotEmpty(message = "User field cannot be empty!")
    private String user;
    @NotEmpty(message = "Role field cannot be empty!")
    private String role;
    @NotEmpty(message = "Field requires either `GRANT` or `REMOVE` value.")
    @Pattern(regexp = "^(GRANT|REMOVE)$", message = "Operation must be either GRANT or REMOVE")
    private String operation;

    public UserUpdateRoleRequest() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
