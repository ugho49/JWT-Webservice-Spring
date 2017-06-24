package fr.nantes.uste.demowebservice.web.request;

import fr.nantes.uste.demowebservice.web.bean.User;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ughostephan on 23/06/2017.
 */
public class AddUserRequest implements Serializable {

    @NotBlank
    @Size(max = 100)
    private String firstname;

    @NotBlank
    @Size(max = 100)
    private String lastname;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    private String birthday;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String country;

    public AddUserRequest() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public String getBirthday() {
        return birthday;
    }

    public Date getBirthdayDate() {
        if (!StringUtils.isEmpty(birthday)) {

            try {
                SimpleDateFormat df = new SimpleDateFormat(User.BIRTHDAY_PATTERN);
                return df.parse(birthday);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }

        }
        return null;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
