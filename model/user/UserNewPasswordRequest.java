package account.model.user;

import jakarta.validation.constraints.NotEmpty;

public class UserNewPasswordRequest {
    @NotEmpty(message = "New password field cannot be empty!")
    private String new_password;

    public UserNewPasswordRequest() {
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
