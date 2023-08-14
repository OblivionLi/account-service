package account.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Can't remove ADMINISTRATOR role!")
public class UserAdministratorDeletionException extends RuntimeException{
    private String message;

    public UserAdministratorDeletionException(String message) {
        super(message);
    }
}
