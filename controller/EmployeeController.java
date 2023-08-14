package account.controller;

import account.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empl")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/payment")
    @Transactional
    public ResponseEntity<?> getUserPayment(
            Authentication authentication,
            @RequestParam(required = false)
            String period
    ) {
        return employeeService.getUserPayment(authentication, period);
    }
}
