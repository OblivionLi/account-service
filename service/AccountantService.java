package account.service;

import account.exception.accountant.PaymentNotFoundException;
import account.exception.accountant.PaymentValidationException;
import account.model.payment.Payment;
import account.model.payment.PaymentRequest;
import account.model.user.User;
import account.repository.AccountantRepository;
import account.repository.UserRepository;
import account.utils.MonthEnum;
import account.utils.ResponseSuccessMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountantService {
    @Autowired
    AccountantRepository accountantRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> saveEmployeesPayments(List<PaymentRequest> requestList) {
        List<Payment> payments = new ArrayList<>();

        for (PaymentRequest paymentRequest : requestList) {
            String validDate = MonthEnum.validateDate(paymentRequest.getPeriod());
            if (validDate.equals("invalid")) {
                throw new PaymentValidationException("Period date is invalid.");
            }

            User user = userRepository.findByEmail(paymentRequest.getEmployee());
            if (user == null) {
                throw new PaymentValidationException("User not found by email " + paymentRequest.getEmployee());
            }

            long count = accountantRepository.countByEmployeeAndPeriod(user.getEmail(), validDate);
            if (count > 0) {
                throw new PaymentValidationException("Couldn't save payments because an employee already has a payment period. Use update method instead.");
            }

            Payment payment = new Payment(paymentRequest.getEmployee(), validDate, paymentRequest.getSalary());
            payment.setUser(user);
            payments.add(payment);
        }

        try {
            accountantRepository.saveAll(payments);
        } catch (Exception ex) {
            throw new PaymentValidationException("Couldn't save payments because data is invalid. Please check that period it valid and unique for each employee." + ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessMessage.getResponseSuccessMessage("Added successfully!"));
    }

    public ResponseEntity<?> updateEmployeePayment(PaymentRequest request) {
        String validDate = MonthEnum.validateDate(request.getPeriod());
        if (validDate.equals("invalid")) {
            throw new PaymentValidationException("Couldn't update payment because period is invalid.");
        }

        Payment payment = accountantRepository.findByEmployeeAndPeriod(request.getEmployee(), validDate);
        if (payment == null) {
            throw new PaymentNotFoundException("Couldn't find employee's payment to update.");
        }

        payment.setPeriod(validDate);
        payment.setSalary(request.getSalary());

        accountantRepository.save(payment);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessMessage.getResponseSuccessMessage("Updated successfully!"));
    }
}
