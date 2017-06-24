package fr.nantes.uste.demowebservice.web.request;

import java.io.Serializable;

/**
 * Created by ughostephan on 23/06/2017.
 */
public class JwtAuthenticationRequest implements Serializable {

    private String email;
    private String password;

    public JwtAuthenticationRequest() {
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
