package ru.spb.iac.controller;

import lombok.extern.log4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by manaev on 13.10.14.
 */
@Controller
@Log4j
public class MongoViewController {

    @RequestMapping(value = "/mongoWebClient",method = RequestMethod.GET)
    public String hello(ModelMap model) {
        model.addAttribute("name", "Hello Mongo Web Client!");
        return "mongoWebClient";
    }

    @RequestMapping(value = {"/", "/login**"}, method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }


}
