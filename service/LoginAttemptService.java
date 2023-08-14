package account.service;

import account.model.user.User;
import account.model.user.UserGroup;
import account.model.user.UserRole;
import account.repository.UserGroupRepository;
import account.repository.UserRepository;
import account.utils.LoggingAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptService {
    private static final int MAX_ATTEMPT = 4;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    SecurityService securityService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserGroupRepository userGroupRepository;

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
