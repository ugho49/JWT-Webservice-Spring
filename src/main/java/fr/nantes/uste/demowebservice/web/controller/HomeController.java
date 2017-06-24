package fr.nantes.uste.demowebservice.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ughostephan on 23/06/2017.
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

}
