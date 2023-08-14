package account.repository;

import account.model.user.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    @Query("SELECT uG FROM UserGroup uG WHERE uG.code = :code")
    UserGroup findByCode(@Param("code") String code);
}
