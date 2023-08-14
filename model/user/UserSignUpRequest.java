package account.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserSignUpRequest {
    @NotEmpty(message = "Name field cannot be empty!")
    private String name;
    @NotEmpty(message = "Lastname field cannot be empty!")
    private String lastname;
    @Email(message = "Email field must be of valid format!")
    @NotEmpty(message = "Email field cannot be empty!")
    @Pattern(regexp = "^[\\w-.]+@acme\\.com$", message = "Email must be an @acme.com address")
    private String email;
    @NotEmpty(message = "Password field cannot be empty!")
    private String password;

    public UserSignUpRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
