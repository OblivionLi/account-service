package account.controller;

import account.model.user.*;
import account.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserSignUpRequest request) {
        return userService.register(request);
    }

    @PostMapping("/changepass")
    public ResponseEntity<UserNewPasswordResponse> changePassword(Authentication authentication, @Valid @RequestBody UserNewPasswordRequest request) {
        return userService.changePassword(authentication, request);
    }
}
