package fr.nantes.uste.demowebservice.web.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by ughostephan on 23/06/2017.
 */
public class AuthenticationRequest implements Serializable {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public AuthenticationRequest() {
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
