package fr.nantes.uste.demowebservice.web.controller;

import fr.nantes.uste.demowebservice.web.bean.Roles;
import fr.nantes.uste.demowebservice.web.bean.User;
import fr.nantes.uste.demowebservice.web.request.AddUserRequest;
import fr.nantes.uste.demowebservice.web.service.UserService;
import fr.nantes.uste.demowebservice.web.util.DataEnvelop;
import fr.nantes.uste.demowebservice.web.validator.AddUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public DataEnvelop getUsers() {
        return DataEnvelop.CreateEnvelop(userService.getAll());
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public DataEnvelop getUserById(@PathVariable(name = "id") String id) {
        final User u = userService.getById(id);

        if (u != null) {
            return DataEnvelop.CreateEnvelop(u);
        }

        return DataEnvelop.CreateEnvelop(HttpStatus.NO_CONTENT, "User not found");
    }

    @GetMapping("/user")
    public DataEnvelop getCurrentUser() {
        final User userFromToken = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //final User user = userService.getById(userFromToken.getUid());

        if (userFromToken != null) {
            return DataEnvelop.CreateEnvelop(userFromToken);
        }

        return DataEnvelop.CreateEnvelop(HttpStatus.NO_CONTENT, "User not found");
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public DataEnvelop addUser(@Valid @ModelAttribute AddUserRequest request, BindingResult result) {

        new AddUserValidator(userService).validate(request, result);

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
        // TODO : change password
        u.setPassword(passwordEncoder.encode("test"));
        u.setCreated_at(new Date());
        u.addRole(Roles.ROLE_USER);
        u.setEnabled(true);

        return DataEnvelop.CreateEnvelop(userService.add(u), HttpStatus.CREATED);
    }

}
