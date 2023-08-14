package account.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User provided bad password!")
public class UserBadPasswordException extends RuntimeException{
    public UserBadPasswordException(String message) {
        super(message);
    }
}
