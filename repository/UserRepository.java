package account.repository;

import account.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.YearMonth;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u JOIN FETCH u.paymentList p WHERE LOWER(u.email) = LOWER(:email) AND p.period = :period")
    User findByEmailAndPaymentPeriod(@Param("email") String email, @Param("period") String period);
}
