package fr.nantes.uste.demowebservice.web.controller.api;

import fr.nantes.uste.demowebservice.web.bean.Role;
import fr.nantes.uste.demowebservice.web.bean.User;
import fr.nantes.uste.demowebservice.web.mailer.UserMailer;
import fr.nantes.uste.demowebservice.web.model.AddUserModel;
import fr.nantes.uste.demowebservice.web.model.ChangePasswordModel;
import fr.nantes.uste.demowebservice.web.model.UpdateUserModel;
import fr.nantes.uste.demowebservice.web.service.UserService;
import fr.nantes.uste.demowebservice.web.util.DataEnvelop;
import fr.nantes.uste.demowebservice.web.validator.ChangePasswordValidator;
import fr.nantes.uste.demowebservice.web.validator.UserValidator;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

/**
 * Created by ughostephan on 23/06/2017.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserMailer mailer;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity getUsers() {
        return DataEnvelop.CreateEnvelop(userService.findAll());
    }

    @GetMapping("/user/{uid}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity getUserById(@PathVariable(name = "uid") String uid) {
        final User u = userService.findById(uid);

        if (u == null) {
            return DataEnvelop.CreateEnvelop(HttpStatus.NOT_FOUND, "User not found");
        }

        return DataEnvelop.CreateEnvelop(u);
    }

    @GetMapping("/user")
    public ResponseEntity getCurrentUser() {
        final User userFromToken = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //final User user = userService.getById(userFromToken.getUid());

        if (userFromToken == null) {
            return DataEnvelop.CreateEnvelop(HttpStatus.NOT_FOUND, "User not found");
        }

        return DataEnvelop.CreateEnvelop(userFromToken);
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity addUser(@Valid @ModelAttribute AddUserModel model, BindingResult result) {

        new UserValidator(userService).validate(model, result);

        if (result.hasErrors()) {
            return DataEnvelop.CreateEnvelop(HttpStatus.BAD_REQUEST, "Bad request", result);
        }

        final User userToAdd = new User();
        userToAdd.setFirstname(model.getFirstname());
        userToAdd.setLastname(model.getLastname());
        userToAdd.setEmail(model.getEmail());
        userToAdd.setBirthday(model.getBirthdayDate());
        userToAdd.setCity(model.getCity());
        userToAdd.setCountry(model.getCountry());
        userToAdd.setPassword(passwordEncoder.encode(model.getPassword()));
        userToAdd.setCreated_at(new Date());
        userToAdd.addRole(Role.ROLE_USER);
        userToAdd.setEnabled(true);

        final User userAdded = userService.create(userToAdd);
        mailer.notifyNewUser(userAdded, model.getPassword());

        return DataEnvelop.CreateEnvelop(HttpStatus.CREATED, userAdded);
    }

    @RequestMapping(value = "/user/{uid}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity updateUser(@Valid @ModelAttribute UpdateUserModel model,
                                     BindingResult result,
                                     @PathVariable(name = "uid") String uid) {

        // User from Token
        final User userFromToken = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userFromToken.getUid().equals(uid) && !userFromToken.hasRole(Role.ROLE_ADMINISTRATOR)) {
            return DataEnvelop.CreateEnvelop(HttpStatus.UNAUTHORIZED, "You can't update someone else");
        }

        new UserValidator(userService)
                .validate(model, result);

        if (result.hasErrors()) {
            return DataEnvelop.CreateEnvelop(HttpStatus.BAD_REQUEST, "Bad request", result);
        }

        // Find user to update
        final User userToUpdate = userService.findById(uid);

        if (userToUpdate == null) {
            return DataEnvelop.CreateEnvelop(HttpStatus.NOT_FOUND, "User not found");
        }

        /**
         * Set values to update
         */
        if (StringUtils.isNotEmpty(model.getFirstname())) {
            userToUpdate.setFirstname(model.getFirstname());
        }

        if (StringUtils.isNotEmpty(model.getLastname())) {
            userToUpdate.setLastname(model.getLastname());
        }

        if (StringUtils.isNotEmpty(model.getEmail())) {
            userToUpdate.setEmail(model.getEmail());
        }

        if (StringUtils.isNotEmpty(model.getBirthday())) {
            userToUpdate.setBirthday(model.getBirthdayDate());
        }

        if (StringUtils.isNotEmpty(model.getCity())) {
            userToUpdate.setCity(model.getCity());
        }

        if (StringUtils.isNotEmpty(model.getCountry())) {
            userToUpdate.setCountry(model.getCountry());
        }

        // Update user
        userService.update(userToUpdate);

        return DataEnvelop.CreateEnvelop("User successfully updated");
    }

    @RequestMapping(value = "/user/change-password/{uid}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity changePassword(@Valid @ModelAttribute ChangePasswordModel model,
                                         BindingResult result,
                                         @PathVariable(name = "uid") String uid) {

        // User from Token
        final User userFromToken = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userFromToken.getUid().equals(uid)) {
            return DataEnvelop.CreateEnvelop(HttpStatus.UNAUTHORIZED, "You can't update someone else password");
        }

        new ChangePasswordValidator(uid, userService, passwordEncoder)
                .validate(model, result);

        if (result.hasErrors()) {
            return DataEnvelop.CreateEnvelop(HttpStatus.BAD_REQUEST, "Bad request", result);
        }

        // Find user to update
        final User userToUpdate = userService.findById(uid);

        if (userToUpdate == null) {
            return DataEnvelop.CreateEnvelop(HttpStatus.NOT_FOUND, "User not found");
        }

        // Change password
        userToUpdate.setPassword(passwordEncoder.encode(model.getNew_password()));

        // Update User
        userService.update(userToUpdate);

        return DataEnvelop.CreateEnvelop("Password successfully updated");
    }

    @DeleteMapping("/user/{uid}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity deleteUser(@PathVariable(name = "uid") String uid) {

        // User from Token
        final User userFromToken = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userFromToken.getUid().equals(uid)) {
            return DataEnvelop.CreateEnvelop(HttpStatus.UNAUTHORIZED, "You can't delete yourself");
        }

        // Find user to delete
        final User u = userService.findById(uid);

        if (u == null) {
            return DataEnvelop.CreateEnvelop(HttpStatus.NOT_FOUND, "User not found");
        }

        // Delete User
        userService.delete(uid);

        return DataEnvelop.CreateEnvelop("User successfully deleted");
    }
}
