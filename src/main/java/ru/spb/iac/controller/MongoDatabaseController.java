package ru.spb.iac.controller;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import ru.spb.iac.exceptions.*;
import ru.spb.iac.services.*;
import ru.spb.iac.ui.models.*;
import ru.spb.iac.ui.models.database.*;
import ru.spb.iac.utils.converter.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * Created by manaev on 24.11.14.
 */
@Controller
@RequestMapping("/control")
@Log4j
public class MongoDatabaseController extends CommonController {

    @Autowired
    @Qualifier("mongoService")
    MongoService mongoService;

    @Autowired
    @Qualifier("mongoTemplateFactory")
    MongoTemplateFactory mongoFactory;

    @Autowired
    LMConverter<DatabaseInfo,String,List<String>> lmConverter;

    @RequestMapping(value = "/testMongoConnection", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void testMongoConnection(MongoAddress address, HttpServletResponse response) {
        if (hasHostPort(address)) {
            try {
                if(mongoFactory.testConnection(address)){
                    writeSuccessAjaxResponse(response,"Connection successful");
                }else{
                    writeErrorAjaxResponse(response,"Can't establish connection");
                }
            } catch (MongoException e) {
                writeErrorAjaxResponse(response,e.getCause());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port is undefined");
        }
    }

    @RequestMapping(value = "/createMongoConnection", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void createMongoConnection(MongoAddress address, HttpServletResponse response) {
        if (hasHostPort(address)) {
            try {
                if(mongoFactory.testConnection(address)){
                    mongoFactory.initMap(address);
                    Map<String, List<String>> mongoInfo = mongoFactory.getMongoInfo(address);
                    writeSuccessAjaxResponse(response,lmConverter.convert(mongoInfo));
                }else{
                    writeErrorAjaxResponse(response,"Can't establish connection");
                }
            } catch (MongoException e) {
                writeErrorAjaxResponse(response,e.getCause());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port is undefined");
        }
    }

}
