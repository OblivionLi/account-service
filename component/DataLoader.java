package account.component;

import account.model.user.UserGroup;
import account.model.user.UserRole;
import account.repository.UserGroupRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    UserGroupRepository userGroupRepository;

    @PostConstruct
    public void init() {
        try {
            userGroupRepository.saveAll(List.of(
                    new UserGroup("ROLE_" + UserRole.USER.name()),
                    new UserGroup("ROLE_" + UserRole.AUDITOR.name()),
                    new UserGroup("ROLE_" + UserRole.ACCOUNTANT.name()),
                    new UserGroup("ROLE_" + UserRole.ADMINISTRATOR.name())
            ));
        } catch (Exception ex) {
            logger.error("Couldn't load user group roles: " + ex.getMessage());
        }
    }
}
