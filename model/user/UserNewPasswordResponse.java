package account.model.user;

public class UserNewPasswordResponse {
    private String email;
    private String status;

    public UserNewPasswordResponse(String email, String status) {
        this.email = email.toLowerCase();
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
