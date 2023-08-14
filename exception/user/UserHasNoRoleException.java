package account.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The user does not have a role!")
public class UserHasNoRoleException extends RuntimeException{
    private String message;

    public UserHasNoRoleException(String message) {
        super(message);
    }
}
