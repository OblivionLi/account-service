package account.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Can't lock the ADMINISTRATOR!")
public class UserLockAdministratorException extends RuntimeException{
    private String message;

    public UserLockAdministratorException(String message) {
        super(message);
    }
}
