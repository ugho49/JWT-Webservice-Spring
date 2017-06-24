package fr.nantes.uste.demowebservice.web.controller;

import fr.nantes.uste.demowebservice.web.util.DataEnvelop;
import fr.nantes.uste.demowebservice.web.bean.User;
import fr.nantes.uste.demowebservice.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ughostephan on 23/06/2017.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping()
    public DataEnvelop getUsers() {
        return DataEnvelop.CreateEnvelop(userService.getAll());
    }

    @GetMapping("/{id}")
    public DataEnvelop getUserById(@PathVariable(name = "id") String id) {
        final User u = userService.getById(id);

        if (u != null) {
            return DataEnvelop.CreateEnvelop(u);
        }

        return DataEnvelop.CreateEnvelop(HttpStatus.NO_CONTENT, "User not found");
    }

    @PostMapping("")
    public DataEnvelop addUser() {
        /*User u = new User();
        u.setFirstname("Lea");
        u.setLastname("STEPHAN");
        u.setEmail("lea.stephan@gmail.com");
        u.setPassword(passwordEncoder.encode("test"));
        u.setCreated_at(new Date());
        u.setRole(Roles.ROLE_USER.toString());
        u.setEnabled(true);
        userService.add(u);*/
        return DataEnvelop.CreateEnvelop(HttpStatus.CREATED, "User created");
    }

}
