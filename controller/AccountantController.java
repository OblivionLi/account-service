package account.controller;

import account.model.payment.PaymentRequest;
import account.service.AccountantService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acct")
@Validated
public class AccountantController {
    @Autowired
    AccountantService accountantService;

    @PostMapping("/payments")
    public ResponseEntity<?> saveEmployeesPayments(
            @RequestBody
            @NotEmpty(message = "Payments list cannot be empty.")
            List<@Valid PaymentRequest> requestList
    ) {
        return accountantService.saveEmployeesPayments(requestList);
    }

    @PutMapping("/payments")
    public ResponseEntity<?> updateEmployeePayment(@Valid @RequestBody PaymentRequest request) {
        return accountantService.updateEmployeePayment(request);
    }
}
