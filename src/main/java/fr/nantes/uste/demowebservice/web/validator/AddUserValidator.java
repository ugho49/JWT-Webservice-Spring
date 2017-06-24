package fr.nantes.uste.demowebservice.web.validator;


import fr.nantes.uste.demowebservice.web.bean.User;
import fr.nantes.uste.demowebservice.web.request.AddUserRequest;
import fr.nantes.uste.demowebservice.web.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ughostephan on 24/06/2017.
 */
public class AddUserValidator implements Validator{

    private UserService userService;

    public AddUserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class clazz) {
        return AddUserRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final AddUserRequest request = (AddUserRequest) target;

        if (!StringUtils.isEmpty(request.getBirthday())) {
            try {
                SimpleDateFormat df = new SimpleDateFormat(User.BIRTHDAY_PATTERN);
                Date birthday = df.parse(request.getBirthday());

                if (birthday.after(new Date())) {
                    errors.rejectValue("birthday", null, "should be in the past");
                }
            } catch (ParseException e) {
                errors.rejectValue("birthday",  null, "not a well-formed date '" + User.BIRTHDAY_PATTERN + "'");
            }
        }

        if (!StringUtils.isEmpty(request.getEmail())) {
            if (userService.getByEmail(request.getEmail()) != null) {
                errors.rejectValue("email",  null, "already used");
            }
        }
    }

}
