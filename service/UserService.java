package account.service;

import account.exception.user.*;
import account.model.user.*;
import account.repository.UserGroupRepository;
import account.repository.UserRepository;
import account.utils.LoggingAction;
import account.validator.user.ValidateUserPassword;
import account.validator.user.ValidateUserRoleUpdate;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserGroupRepository groupRepository;
    @Autowired
    SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        logger.info("CHECK USER: {}", user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        return new UserDetailsImpl(user);
    }

    public ResponseEntity<UserResponse> register(UserSignUpRequest request) {
        securityService.addLogging(
                LoggingAction.CREATE_USER.name(),
                UserRole.Anonymous.name(),
                request.getEmail().toLowerCase(),
                "/api/auth/signup"
        );

        User existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser != null) {
            throw new UserExistException();
        }

        String getPasswordValidationMessage = ValidateUserPassword.getMessage(request.getPassword(), "", passwordEncoder);
        if (!getPasswordValidationMessage.isEmpty()) {
            throw new UserBadPasswordException(getPasswordValidationMessage);
        }

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getName(),
                request.getLastname(),
                request.getEmail().toLowerCase(),
                encryptedPassword,
                false
        );

        UserRole userRole = userRepository.count() < 1 ? UserRole.ADMINISTRATOR : UserRole.USER;
        UserGroup userGroup = groupRepository.findByCode("ROLE_" + userRole.name());

        user.setUserGroups(userGroup);
        user = userRepository.save(user);

        UserResponse userSignUpResponse = new UserResponse(
                user.getId(),
                request.getName(),
                request.getLastname(),
                request.getEmail(),
                user.getUserGroups()
        );
        return ResponseEntity.status(HttpStatus.OK).body(userSignUpResponse);
    }

    public ResponseEntity<UserNewPasswordResponse> changePassword(Authentication authentication, UserNewPasswordRequest request) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        securityService.addLogging(
                LoggingAction.CHANGE_PASSWORD.name(),
                details.getUsername(),
                details.getUsername(),
                "/api/auth/changepass"
        );

        User user = userRepository.findByEmail(details.getUsername());
        if (user == null) {
            throw new UserUnauthorizedException();
        }

        String getPasswordValidationMessage = ValidateUserPassword.getMessage(request.getNew_password(), user.getPassword(), passwordEncoder);
        if (!getPasswordValidationMessage.isEmpty()) {
            throw new UserBadPasswordException(getPasswordValidationMessage);
        }

        String encryptedPassword = passwordEncoder.encode(request.getNew_password());
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        UserNewPasswordResponse userNewPasswordResponse = new UserNewPasswordResponse(
                user.getEmail(),
                "The password has been updated successfully"
        );
        return ResponseEntity.status(HttpStatus.OK).body(userNewPasswordResponse);
    }

    public ResponseEntity<?> findUsers() {
        List<User> userList = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (userList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(userList);
        }

        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : userList) {
            UserResponse userResponse = new UserResponse(
                    user.getId(),
                    user.getName(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getUserGroups()
            );

            userResponseList.add(userResponse);
        }


        return ResponseEntity.status(HttpStatus.OK).body(userResponseList);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> deleteUser(String email, Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        securityService.addLogging(
                LoggingAction.DELETE_USER.name(),
                details.getUsername(),
                email.toLowerCase(),
                "/api/admin/user/" + email
        );

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }

        if (user.getUserGroups().contains(groupRepository.findByCode("ROLE_" + UserRole.ADMINISTRATOR.name()))) {
            throw new UserAdministratorDeletionException("Can't remove ADMINISTRATOR role!");
        }

        userRepository.delete(user);

        Map<String, String> response = new HashMap<>();
        response.put("user", email);
        response.put("status",  "Deleted successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Transactional
    public ResponseEntity<UserResponse> updateUserRole(UserUpdateRoleRequest request, Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(request.getUser());
        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }

        LoggingAction loggingAction;
        String objectMessage;
        if (request.getOperation().equals("REMOVE")) {
            loggingAction = LoggingAction.REMOVE_ROLE;
            objectMessage = "Remove role " + request.getRole() + " from " + user.getEmail();
        } else {
            loggingAction = LoggingAction.GRANT_ROLE;
            objectMessage = "Grant role " + request.getRole() + " to " + user.getEmail();
        }

        securityService.addLogging(
                loggingAction.name(),
                details.getUsername(),
                objectMessage,
                "/api/admin/user/role"
        );

        UserGroup userGroup = groupRepository.findByCode("ROLE_" + request.getRole());
        if (userGroup == null) {
            throw new UserRoleNotFoundException("Role not found!");
        }

        ValidateUserRoleUpdate.validateOperation(request.getOperation(), user, userGroup, groupRepository);

        switch (request.getOperation()) {
            case "REMOVE" -> user.getUserGroups().remove(userGroup);
            case "GRANT" -> user.setUserGroups(userGroup);
        }

        user = userRepository.save(user);

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getUserGroups()
        );
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    public ResponseEntity<Map<String, String>> updateUserAccess(UserUpdateAccessRequest request, Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(request.getUser());
        if (user == null) {
            throw new UserNotFoundException("Cannot find user to update its access.");
        }

        LoggingAction loggingAction;
        String objectMessage;
        if (request.getOperation().equals("LOCK")) {
            loggingAction = LoggingAction.LOCK_USER;
            objectMessage = "Lock user " + user.getEmail();
        } else {
            loggingAction = LoggingAction.UNLOCK_USER;
            objectMessage = "Unlock user " + user.getEmail();
        }

        securityService.addLogging(
                loggingAction.name(),
                details.getUsername(),
                objectMessage,
                "/api/admin/user/access"
        );

        switch (request.getOperation()) {
            case "LOCK" -> {
                if (user.getUserGroups().contains(groupRepository.findByCode("ROLE_" + UserRole.ADMINISTRATOR.name()))) {
                    throw new UserLockAdministratorException("Can't lock the ADMINISTRATOR!");
                }

                user.setLocked(true);
            }

            case "UNLOCK" -> {
                user.setLocked(false);
                user.setFailedLoginAttempts(0);
            }
        }

        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        String userStatus = request.getOperation().equals("LOCK") ? "locked" : "unlocked";
        response.put("status", "User " + user.getEmail() + " " + userStatus + "!");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
