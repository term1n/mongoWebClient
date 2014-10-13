package ru.spb.iac.controller;

import lombok.extern.log4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by manaev on 13.10.14.
 */
@Controller
@Log4j
public class MongoViewController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String hello(ModelMap model) {
        model.addAttribute("name", "Hello Mongo Web Client!");
        return "mongoWebClient";

    }
}
