package account.controller;

import account.model.user.UserResponse;
import account.model.user.UserUpdateAccessRequest;
import account.model.user.UserUpdateRoleRequest;
import account.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/user/")
    public ResponseEntity<?> findUsers() {
        return userService.findUsers();
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<Map<String, String>> deleteUser(
            @PathVariable
            @NotEmpty
            @Email
            String email,
            Authentication authentication
    ) {
        return userService.deleteUser(email, authentication);
    }

    @PutMapping("/user/role")
    public ResponseEntity<UserResponse> updateUserRole(@Valid @RequestBody UserUpdateRoleRequest request, Authentication authentication) {
        return userService.updateUserRole(request, authentication);
    }

    @PutMapping("/user/access")
    public ResponseEntity<Map<String, String>> updateUserAccess(@Valid @RequestBody UserUpdateAccessRequest request, Authentication authentication) {
        return userService.updateUserAccess(request, authentication);
    }
}
