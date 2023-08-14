package account.component;

import account.service.LoginAttemptService;
import account.service.SecurityService;
import account.utils.LoggingAction;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    LoginAttemptService loginAttemptService;
    @Autowired
    SecurityService securityService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        securityService.addLogging(
                LoggingAction.LOGIN_FAILED.name(),
                event.getAuthentication().getName(),
                httpServletRequest.getRequestURI(),
                httpServletRequest.getRequestURI()
        );
        final String username = event.getAuthentication().getName();
        if (username != null) {
            loginAttemptService.loginFailure(username, httpServletRequest.getRequestURI());
        }
    }
}
