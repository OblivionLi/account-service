package account.exception.accountant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Payment request values are not correct!")
public class PaymentValidationException extends RuntimeException{
    private String message;

    public PaymentValidationException(String message) {
        super(message);
    }
}
