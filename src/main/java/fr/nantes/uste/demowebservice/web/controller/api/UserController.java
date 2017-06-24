package fr.nantes.uste.demowebservice.web.controller.api;

import fr.nantes.uste.demowebservice.web.bean.Roles;
import fr.nantes.uste.demowebservice.web.bean.User;
import fr.nantes.uste.demowebservice.web.request.AddUserRequest;
import fr.nantes.uste.demowebservice.web.request.UpdateUserRequest;
import fr.nantes.uste.demowebservice.web.service.UserService;
import fr.nantes.uste.demowebservice.web.util.DataEnvelop;
import fr.nantes.uste.demowebservice.web.validator.UserValidator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by ughostephan on 23/06/2017.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public ResponseEntity addUser(@Valid @ModelAttribute AddUserRequest request, BindingResult result) {

        new UserValidator(userService).validate(request, result);

        if (result.hasErrors()) {
            return DataEnvelop.CreateEnvelop(HttpStatus.BAD_REQUEST, "Bad request", result);
        }

        User u = new User();
        u.setFirstname(request.getFirstname());
        u.setLastname(request.getLastname());
        u.setEmail(request.getEmail());
        u.setBirthday(request.getBirthdayDate());
        u.setCity(request.getCity());
        u.setCountry(request.getCountry());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setCreated_at(new Date());
        u.addRole(Roles.ROLE_USER);
        u.setEnabled(true);

        return DataEnvelop.CreateEnvelop(HttpStatus.CREATED, userService.create(u));
    }

    @RequestMapping(value = "/user/{uid}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity updateUser(@Valid @ModelAttribute UpdateUserRequest request,
                                     BindingResult result,
                                     @PathVariable(name = "uid") String uid) {
        final User userFromToken = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userFromToken.getUid().equals(uid) && !userFromToken.hasRole(Roles.ROLE_ADMINISTRATOR)) {
            return DataEnvelop.CreateEnvelop(HttpStatus.UNAUTHORIZED, "You can't update someone else");
        }

        new UserValidator(userService).validate(request, result);

        if (result.hasErrors()) {
            return DataEnvelop.CreateEnvelop(HttpStatus.BAD_REQUEST, "Bad request", result);
        }

        final User u = userService.findById(uid);

        if (u == null) {
            return DataEnvelop.CreateEnvelop(HttpStatus.NOT_FOUND, "User not found");
        }

        /**
         * Set values to update
         */
        if (StringUtils.isNotEmpty(request.getFirstname())) {
            u.setFirstname(request.getFirstname());
        }

        if (StringUtils.isNotEmpty(request.getLastname())) {
            u.setLastname(request.getLastname());
        }

        if (StringUtils.isNotEmpty(request.getEmail())) {
            u.setEmail(request.getEmail());
        }

        if (StringUtils.isNotEmpty(request.getBirthday())) {
            u.setBirthday(request.getBirthdayDate());
        }

        if (StringUtils.isNotEmpty(request.getCity())) {
            u.setCity(request.getCity());
        }

        if (StringUtils.isNotEmpty(request.getCountry())) {
            u.setCountry(request.getCountry());
        }

        if (StringUtils.isNotEmpty(request.getPassword())) {
            u.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userService.update(u);

        return DataEnvelop.CreateEnvelop("User successfully updated");
    }

    @DeleteMapping("/user/{uid}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity deleteUser(@PathVariable(name = "uid") String uid) {
        final User userFromToken = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userFromToken.getUid().equals(uid)) {
            return DataEnvelop.CreateEnvelop(HttpStatus.UNAUTHORIZED, "You can't delete yourself");
        }

        final User u = userService.findById(uid);

        if (u == null) {
            return DataEnvelop.CreateEnvelop(HttpStatus.NOT_FOUND, "User not found");
        }

        userService.delete(uid);
        return DataEnvelop.CreateEnvelop("User successfully deleted");
    }
}
