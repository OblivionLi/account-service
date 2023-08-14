package account.validator.user;

import account.exception.user.UserAdministratorDeletionException;
import account.exception.user.UserHasNoRoleException;
import account.exception.user.UserRemoveLastRoleException;
import account.exception.user.UserUpdateRoleException;
import account.model.user.User;
import account.model.user.UserGroup;
import account.model.user.UserRole;
import account.repository.UserGroupRepository;
import account.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateUserRoleUpdate {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public static void validateOperation(String operation, User user, UserGroup userGroup, UserGroupRepository groupRepository) {
        switch (operation) {
            case "REMOVE" -> {
                if (!user.getUserGroups().contains(userGroup)) {
                    throw new UserHasNoRoleException("The user does not have a role!");
                }

                if (user.getUserGroups().contains(groupRepository.findByCode("ROLE_" + UserRole.ADMINISTRATOR.name()))) {
                    throw new UserAdministratorDeletionException("Can't remove ADMINISTRATOR role!");
                }

                if (user.getUserGroups().size() == 1) {
                    throw new UserRemoveLastRoleException("The user must have at least one role!");
                }
            }
            case "GRANT" -> {
                if (user.getUserGroups().contains(groupRepository.findByCode("ROLE_" + UserRole.ADMINISTRATOR.name()))
                        || userGroup.getCode().equals("ROLE_" + UserRole.ADMINISTRATOR.name())
                ) {
                    throw new UserUpdateRoleException("The user cannot combine administrative and business roles!");
                }
            }
        }
    }
}
