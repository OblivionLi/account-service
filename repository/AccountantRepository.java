package account.repository;

import account.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountantRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE LOWER(p.employee) = LOWER(:employee) AND p.period = :period")
    Payment findByEmployeeAndPeriod(@Param("employee") String employee, @Param("period") String period);

    @Query("SELECT COUNT(p) FROM Payment p WHERE LOWER(p.employee) = LOWER(:employee) AND p.period = :period")
    long countByEmployeeAndPeriod(@Param("employee") String employee, @Param("period") String period);
}
