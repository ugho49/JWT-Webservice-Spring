package fr.nantes.uste.demowebservice.web.mailer;

import fr.nantes.uste.demowebservice.web.bean.User;
import fr.nantes.uste.demowebservice.web.service.MailService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by ughostephan on 25/06/2017.
 */
@Component
public class UserMailer {

    @Resource
    private MailService service;

    public void notifyNewUser(final User user, final String clearPassword) {
        final StringBuilder sb = new StringBuilder();
        sb
            .append("You're account have been created successfully")
            .append("\n").append("Here you're password: ").append(clearPassword)
            .append("\n")
            .append("\n")
            .append("Thanks to register !");

        service.sendMail(user.getEmail(), "Welcome", sb.toString());
    }
}
