package account.exception.accountant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Payment not found!")
public class PaymentNotFoundException extends RuntimeException{
    private String message;

    public PaymentNotFoundException(String message) {
        super(message);
    }
}
