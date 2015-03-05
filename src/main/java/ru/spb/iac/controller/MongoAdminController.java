package ru.spb.iac.controller;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import ru.spb.iac.exceptions.*;
import ru.spb.iac.security.*;
import ru.spb.iac.security.oo.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * Created by manaev on 05.03.15.
 */
@Controller
@RequestMapping("/ad_services")
@Log4j
public class MongoAdminController extends CommonController {
    @Autowired
    @Qualifier("mwcDetailsService")
    MWCUserDetailsService mwcUDS;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/getMWCUsers", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void getMongoInfo(HttpServletResponse response) {
        try {
            List<MWCUserUi> uiusers = new ArrayList<>();
            for (MWCUser user : mwcUDS.getMongoTemplateFactory().getOperationsTemplate(mwcUDS.getMongoHere()).findAll(MWCUser.class)) {
                uiusers.add(user.toUser());
            }
            writeSuccessAjaxResponse(response, uiusers);
        } catch (MongoException e) {
            log.error(e.getMessage(), e);
            writeErrorAjaxResponse(response, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void updateUser(@RequestParam(value = "username", required = true) String username,
                    @RequestParam(value = "sauthorities", required = true) String authorities,
                    @RequestParam(value = "eMail", required = true) String eMail,
                    @RequestParam(value = "id", required = true) String id,
                    HttpServletResponse response) {
        try {
            GrantedAuthority[] auth = gson.fromJson(authorities, SimpleGrantedAuthority[].class);
            MWCUserUi user = new MWCUserUi(id,username,eMail,auth);
            Query query = new Query(Criteria.where("_id").is(user.getId()));
            MWCUser tUser = mwcUDS.getMongoTemplateFactory().getOperationsTemplate(mwcUDS.getMongoHere()).findOne(query, MWCUser.class, MWCUser.COLLECTION_NAME);
            tUser.update(user);
            mwcUDS.getMongoTemplateFactory().getOperationsTemplate(mwcUDS.getMongoHere()).save(tUser, MWCUser.COLLECTION_NAME);
            writeSuccessAjaxResponse(response, "Updated successfully");
        } catch (MongoException e) {
            log.error(e.getMessage(), e);
            writeErrorAjaxResponse(response, e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/removeUser", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void removeUser( @RequestParam(value = "id", required = true) String id,
                    HttpServletResponse response) {
        try {
            Query query = new Query(Criteria.where("_id").is(id));
            mwcUDS.getMongoTemplateFactory().getOperationsTemplate(mwcUDS.getMongoHere()).findAndRemove(query, MWCUser.class, MWCUser.COLLECTION_NAME);
            writeSuccessAjaxResponse(response, "Removed successfully");
        } catch (MongoException e) {
            log.error(e.getMessage(), e);
            writeErrorAjaxResponse(response, e.getMessage());
        }
    }
}
