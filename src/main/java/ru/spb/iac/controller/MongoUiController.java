package ru.spb.iac.controller;

import com.mongodb.util.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.query.*;
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
public class MongoUiController extends CommonController {

    @Autowired
    @Qualifier("mongoService")
    MongoService mongoService;

    @Autowired
    @Qualifier("mongoTemplateFactory")
    MongoTemplateFactory mongoFactory;

    /**
     * get map of database names and collections in the database
     *
     * @param address  MongoAddress of the database
     * @param response response to client
     */
    @RequestMapping(value = "/getMongoInfo", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void getMongoInfo(MongoAddress address, HttpServletResponse response) {
        if (hasHostPort(address)) {
            try {
                mongoFactory.initMap(address);
                Map<String, List<String>> respData = mongoFactory.getMongoInfo(address);
                writeSuccessAjaxResponse(response, respData);
            } catch (MongoException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port is undefined");
        }
    }

    /**
     * get list of object id's in the collection
     *
     * @param address  MongoAddress of the database
     * @param response response to client
     */
    @RequestMapping(value = "/viewCollectionEntities", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void viewCollectionEntities(MongoAddress address, HttpServletResponse response) {
        if (hasHostPortDbNColl(address)) {
            try {
                mongoFactory.initMap(address);
                Query q = new Query();
                q.fields().include("_id");
                writeSuccessAjaxResponse(response, JSON.serialize(mongoService.find(address, q)));
            } catch (MongoException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port or database or collection is undefined");
        }
    }

    /**
     * get entity of the collection
     *
     * @param objectId object id of the element in collection
     * @param address  MongoAddress of the database
     * @param response response to client
     */
    @RequestMapping(value = "/viewCollectionEntity", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void viewCollectionEntity(MongoAddress address, @RequestParam(value = "id") String objectId, HttpServletResponse response) {
        if (hasHostPortDbNColl(address)) {
            try {
                mongoFactory.initMap(address);
                Query q = new Query(Criteria.where("_id").is(objectId));
                q.fields().exclude("_id");
                writeSuccessAjaxResponse(response, JSON.serialize(mongoService.findOne(address, q)));
            } catch (MongoException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port or database or collection is undefined");
        }
    }

}
