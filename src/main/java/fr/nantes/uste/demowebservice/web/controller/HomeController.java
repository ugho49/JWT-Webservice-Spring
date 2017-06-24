package fr.nantes.uste.demowebservice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by ughostephan on 23/06/2017.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        //return new ModelAndView("index");
        return "index";
    }

}
