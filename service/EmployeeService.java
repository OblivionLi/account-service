package account.service;

import account.exception.accountant.PaymentValidationException;
import account.model.payment.Payment;
import account.model.payment.PaymentResponse;
import account.model.user.User;
import account.repository.UserGroupRepository;
import account.repository.UserRepository;
import account.utils.MonthEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserGroupRepository userGroupRepository;

    public ResponseEntity<?> getUserPayment(Authentication authentication, String period) {
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.OK).body("User must login!");
        }

        List<PaymentResponse> paymentList = new ArrayList<>();

        UserDetails details = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(details.getUsername());

        if (user.getPaymentList().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(paymentList);
        }

        if (period != null) {
            String validDate = MonthEnum.validateDate(period);
            if (validDate.equals("invalid")) {
                throw new PaymentValidationException("Couldn't get user because period is invalid.");
            }

            user.setPaymentList(
                    user.getPaymentList()
                            .stream()
                            .filter(payment -> payment.getPeriod().equals(validDate))
                            .collect(Collectors.toList())
            );
        }

        List<Payment> sortedPayments = user.getPaymentList().stream()
                .sorted(Comparator.comparing((Payment p) -> {
                    String[] parts = p.getPeriod().split("-");
                    String monthName = parts[0];
                    return MonthEnum.valueOf(monthName).ordinal() + 1;
                }).reversed())
                .toList();

        for (Payment payment : sortedPayments) {
            PaymentResponse paymentResponse = new PaymentResponse(
                    user.getName(),
                    user.getLastname(),
                    payment.getPeriod(),
                    convertPaymentSalary(payment.getSalary())
            );
            paymentList.add(paymentResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).body(paymentList.size() == 1 ? paymentList.get(0) : paymentList);
    }

    private String convertPaymentSalary(long paymentSalary) {
        long dollars = paymentSalary / 100;
        long cents = paymentSalary % 100;
        return String.format("%d dollar(s) %d cent(s)", dollars, cents);
    }
}
