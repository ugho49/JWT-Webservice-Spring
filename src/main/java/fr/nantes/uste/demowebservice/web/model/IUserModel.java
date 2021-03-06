package fr.nantes.uste.demowebservice.web.model;

import fr.nantes.uste.demowebservice.web.bean.User;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ughostephan on 24/06/2017.
 */
public interface IUserModel {

    String getBirthday();

    String getEmail();

    default Date getBirthdayDate() {
        if (!StringUtils.isEmpty(getBirthday())) {

            try {
                SimpleDateFormat df = new SimpleDateFormat(User.BIRTHDAY_PATTERN);
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                return df.parse(getBirthday());
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }

        }
        return null;
    }
}
