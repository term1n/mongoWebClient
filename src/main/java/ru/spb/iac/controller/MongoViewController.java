package ru.spb.iac.controller;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.*;
import ru.spb.iac.exceptions.*;
import ru.spb.iac.security.*;
import ru.spb.iac.security.enc.*;
import ru.spb.iac.security.oo.*;

import javax.validation.*;

/**
 * Created by manaev on 13.10.14.
 */
@Controller
@Log4j
public class MongoViewController {
    @Autowired
    @Qualifier("encrypter")
    BCryptHash hash;

    @Autowired
    @Qualifier("mwcDetailsService")
    MWCUserDetailsService mwcUDS;

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
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        model.addAttribute("user",new MWCUser());
        return "register";
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public ModelAndView registerUser(@Valid @ModelAttribute("user") MWCUser user, BindingResult result, WebRequest request)
    {
        if (result.hasErrors()) {
            ModelAndView temp = new ModelAndView();
            temp.addObject("error", result.getAllErrors());
            temp.addObject("user", user);
            temp.setViewName("register");
            return temp;
        } else{
            ModelAndView model = new ModelAndView();
            model.setViewName("login");
            user.setPassword(hash.getEncPassword(user.getPassword()));
            GrantedAuthority[] authorities = new GrantedAuthority[1];
            authorities[0] = new SimpleGrantedAuthority("ROLE_USER");
            user.setAuthorities(authorities);
            try {
                mwcUDS.getMongoTemplateFactory().getOperationsTemplate(mwcUDS.getMongoHere()).save(user);
            } catch (MongoException e) {
                log.error(e.getMessage(),e);
            }
            return model;
        }
    }

}
