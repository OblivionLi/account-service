package account.model.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class PaymentRequest {
    @NotEmpty(message = "Employee field cannot be empty!")
    @Email(message = "Employee field must be a valid email!")
    private String employee;
    @NotEmpty(message = "Period field cannot be empty!")
    private String period;
    @Min(value = 1, message = "Salary cannot be negative or 0.")
    private long salary;

    public PaymentRequest() {
    }

    public PaymentRequest(String employee, String period, long salary) {
        this.employee = employee;
        this.period = period;
        this.salary = salary;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }
}
