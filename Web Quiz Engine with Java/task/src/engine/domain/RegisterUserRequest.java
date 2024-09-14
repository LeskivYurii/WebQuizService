package engine.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterUserRequest {

    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]+")
    private String email;
    @Size(min = 5)
    private String password;

    public RegisterUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public RegisterUserRequest() {
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
