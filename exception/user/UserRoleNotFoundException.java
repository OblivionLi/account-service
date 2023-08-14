package account.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Role not found!")
public class UserRoleNotFoundException extends RuntimeException {
    private String message;

    public UserRoleNotFoundException(String message) {
        super(message);
    }
}
