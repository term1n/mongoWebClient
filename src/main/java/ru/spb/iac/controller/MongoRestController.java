package ru.spb.iac.controller;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import ru.spb.iac.exceptions.*;
import ru.spb.iac.services.*;
import ru.spb.iac.ui.model.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * Created by manaev on 10.10.14.
 */
@Controller
@RequestMapping("/mongo")
@Log4j
public class MongoRestController extends CommonController {

    @Autowired
    @Qualifier("mongoService")
    MongoService mongoService;

    @Autowired
    @Qualifier("mongoTemplateFactory")
    MongoTemplateFactory mongoFactory;

    @RequestMapping(value = "/addDatabase", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void addDatabase(MongoAddress address, HttpServletResponse response) {
        if (hasHostPort(address)) {
            try {
                mongoFactory.initMap(address);
                Map<String,List<String>> respData = mongoFactory.getMongoInfo(address);
                writeSuccessAjaxResponse(response,respData);
            } catch (MongoException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port is undefined");
        }
    }

}
