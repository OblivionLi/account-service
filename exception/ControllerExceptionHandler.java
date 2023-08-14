package account.exception;

import account.exception.accountant.PaymentNotFoundException;
import account.exception.accountant.PaymentValidationException;
import account.exception.user.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(UserIsLockedException.class)
//    public ResponseEntity<ErrorResponse> handleUserIsLockedException(
//            UserIsLockedException ex,
//            WebRequest request
//    ) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.UNAUTHORIZED,
//                "Unauthorized",
//                ex.getMessage(),
//                ((ServletWebRequest) request).getRequest().getRequestURI()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatusCode status,
//            WebRequest request
//    ) {
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", new Date());
//        body.put("status", status.value());
//        body.put("error", "Bad Request");
//
//        List<String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .collect(Collectors.toList());
//
//        body.put("message", errors);
//        body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());
//
//        return new ResponseEntity<>(body, headers, status);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
//            ConstraintViolationException ex,
//            WebRequest request
//    ) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.BAD_REQUEST,
//                "Bad Request",
//                "Invalid data passed to endpoint. " + ex.getMessage(),
//                ((ServletWebRequest) request).getRequest().getRequestURI()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ErrorResponse> handleBadCredentialsException(
//            BadCredentialsException ex,
//            WebRequest request
//    ) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.UNAUTHORIZED,
//                "Unauthorized",
//                "Invalid credentials.",
//                ((ServletWebRequest) request).getRequest().getRequestURI()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(PaymentValidationException.class)
//    public ResponseEntity<ErrorResponse> handlePaymentValidationException(
//            PaymentValidationException ex,
//            WebRequest request
//    ) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.BAD_REQUEST,
//                "Bad Request",
//                ex.getMessage(),
//                ((ServletWebRequest) request).getRequest().getRequestURI()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(PaymentNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handlePaymentNotFoundException(
//            PaymentNotFoundException ex,
//            WebRequest request
//    ) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.NOT_FOUND,
//                "Bad Request",
//                ex.getMessage(),
//                ((ServletWebRequest) request).getRequest().getRequestURI()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(UserBadPasswordException.class)
//    public ResponseEntity<ErrorResponse> handleUserBadPasswordException(
//            UserBadPasswordException ex,
//            WebRequest request
//    ) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.BAD_REQUEST,
//                "Bad Request",
//                ex.getMessage(),
//                ((ServletWebRequest) request).getRequest().getRequestURI()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
}
