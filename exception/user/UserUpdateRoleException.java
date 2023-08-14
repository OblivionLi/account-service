package account.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The user cannot combine administrative and business roles!")
public class UserUpdateRoleException extends RuntimeException{
    private String message;

    public UserUpdateRoleException(String message) {
        super(message);
    }
}
