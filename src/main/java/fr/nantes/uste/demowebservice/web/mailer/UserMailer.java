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

    /**
     * Notify new user.
     *
     * @param user          the user
     * @param clearPassword the clear password
     */
    public void notifyNewUser(final User user, final String clearPassword) {
        final StringBuilder sb = new StringBuilder();
        sb
            .append("Welcome ").append(user.getFirstname()).append(" ").append(user.getLastname().toUpperCase())
            .append(MailService.BREAK_LINE)
            .append("You're account have been created successfully")
            .append(MailService.NEW_LINE)
            .append("Here you're password: ").append(clearPassword)
            .append(MailService.NEW_LINE)
            .append("Thanks to register !");

        service.sendMail(user.getEmail(), "Welcome", sb.toString());
    }

    /**
     * Notify change password.
     *
     * @param user the user
     * @param new_password the new password
     */
    public void notifyChangePassword(final User user, final String new_password) {

        final StringBuilder sb = new StringBuilder();
        sb
                .append("You're password have been changed successfully")
                .append(MailService.BREAK_LINE)
                .append("Try to remind this password and delete this mail")
                .append(MailService.NEW_LINE)
                .append("Here you're new password: ").append(new_password)
                .append(MailService.NEW_LINE)
                .append(MailService.NEW_LINE)
                .append("Have a good day !");

        service.sendMail(user.getEmail(), "Password change", sb.toString());
    }
}
