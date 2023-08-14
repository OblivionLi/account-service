package account.service;

import account.model.user.User;
import account.repository.UserRepository;
import account.utils.LoggingAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptService {
    private static final int MAX_ATTEMPT = 4;

    @Autowired
    SecurityService securityService;
    @Autowired
    UserRepository userRepository;

    public void loginSuccess(String username) {
        User user = userRepository.findByEmail(username);
        user.setFailedLoginAttempts(0);
        userRepository.save(user);
    }

    public void loginFailure(String username, String requestUri) {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            return;
        }

        boolean hasAdministratorRole = user.getUserGroups().stream()
                .anyMatch(userGroup -> "ROLE_ADMINISTRATOR".equals(userGroup.getCode()));

        if (hasAdministratorRole) {
            return;
        }

        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

        if (user.getFailedLoginAttempts() > MAX_ATTEMPT) {
            user.setLocked(true);
            securityService.addLogging(
                    LoggingAction.BRUTE_FORCE.name(),
                    username,
                    requestUri,
                    requestUri
            );
            securityService.addLogging(
                    LoggingAction.LOCK_USER.name(),
                    username,
                    "Lock user " + username,
                    requestUri
            );
        }
        userRepository.save(user);
    }
}
