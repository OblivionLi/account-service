package account.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.LOCKED, reason = "User account is locked")
public class UserIsLockedException extends LockedException {
    private String message;

    public UserIsLockedException(String message) {
        super(message);
    }

    public UserIsLockedException(String message, Throwable t) {
        super(message, t);
    }
}
