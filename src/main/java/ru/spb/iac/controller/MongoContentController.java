package ru.spb.iac.controller;

import com.google.common.base.*;
import com.google.common.primitives.*;
import com.mongodb.*;
import com.mongodb.util.*;
import lombok.extern.log4j.*;
import org.bson.types.*;
import org.codehaus.jackson.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import ru.spb.iac.enums.*;
import ru.spb.iac.exceptions.MongoException;
import ru.spb.iac.services.*;
import ru.spb.iac.services.impl.mongo.*;
import ru.spb.iac.ui.*;
import ru.spb.iac.ui.models.*;
import ru.spb.iac.ui.models.database.*;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 * Created by manaev on 10.10.14.
 */
@Controller
@RequestMapping("/mongo")
@Log4j
public class MongoContentController extends CommonController {

    @Autowired
    @Qualifier("mongoService")
    MongoService mongoService;

    @Autowired
    @Qualifier("consoleComposerService")
    ConsoleComposer composer;

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
    @RequestMapping(value = "/collectionSize", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void collectionSize(MongoAddress address, HttpServletResponse response,
                        @RequestParam(required = false, value = "fquery") String fquery,
                        @RequestParam(required = false, value = "foperation") String foperation,
                        @RequestParam(value = "dbOper", required = false) String dbOper,
                        @RequestParam(value = "skip", required = false) String skip
    ) throws IOException {
        if (hasHostPortDbNColl(address)) {
            try {
                Integer iSkip = null;
                if(Strings.isNullOrEmpty(skip)){
                    iSkip = 0;
                }else{
                    iSkip = Ints.tryParse(skip);
                }
                mongoFactory.initMap(address);
                if (!Strings.isNullOrEmpty(fquery)) {
                    if(OperationTypes.valueOf(foperation) == OperationTypes.FIND){
                        writeSuccessAjaxResponse(response, JSON.serialize(mongoService.collectionSize(address, composer.composeCriteria(fquery))-iSkip));
                    }else{
                        writeSuccessAjaxResponse(response, "NaN");
                    }
                } else {
                    writeSuccessAjaxResponse(response, JSON.serialize(mongoService.collectionSize(address)-iSkip));
                }

            } catch (MongoException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port or database or collection is undefined");
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
                writeSuccessAjaxResponse(response, JSON.serialize(mongoService.find(address, new BasicDBObject(), new BasicDBObject("_id", true))));
            } catch (MongoException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port or database or collection is undefined");
        }
    }

    /**
     * get paginated list of object id's in the collection
     *
     * @param address  MongoAddress of the database
     * @param response response to client
     */
    void viewPaginatedCollectionEntities(MongoAddress address, String skip, String limit, HttpServletResponse response) {
        if (hasHostPortDbNColl(address)) {
            try {
                mongoFactory.initMap(address);
                writeSuccessAjaxResponse(response, JSON.serialize(mongoService.find(address, new BasicDBObject(), new BasicDBObject("_id", true), Ints.tryParse(skip), Ints.tryParse(limit))));
            } catch (MongoException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port or database or collection is undefined");
        }
    }

    @RequestMapping(value = "/updateEntity", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void updateEntity(MongoAddress address, @RequestParam(value = "dbObject") String dbObject, HttpServletResponse response) {
        if (hasHostPortDbNColl(address) && !Strings.isNullOrEmpty(dbObject)) {
            try {
                mongoFactory.initMap(address);
                DBObject temp = (DBObject) JSON.parse(dbObject);
                mongoService.save(address, temp);
                writeSuccessAjaxResponse(response, new AjaxResponse("SUCCESS"));
            } catch (MongoException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port or database or collection is undefined");
        }
    }

    @RequestMapping(value = "/dropCollection", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void dropCollection(MongoAddress address, HttpServletResponse response) {
        if (hasHostPortDbNColl(address)) {
            try {
                mongoFactory.initMap(address);
                mongoService.dropCollection(address);
                writeSuccessAjaxResponse(response, new AjaxResponse("SUCCESS"));
            } catch (MongoException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port or database or collection is undefined");
        }
    }

    @RequestMapping(value = "/deleteEntity", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void deleteEntity(MongoAddress address, @RequestParam(value = "id") String id, HttpServletResponse response) {
        if (hasHostPortDbNColl(address) && !Strings.isNullOrEmpty(id)) {
            try {
                mongoFactory.initMap(address);
                BasicDBObject query = new BasicDBObject();
                query.put("_id", new ObjectId(id));
                mongoService.findAndRemove(address, query);
                writeSuccessAjaxResponse(response, new AjaxResponse("SUCCESS"));
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
                BasicDBObject query = new BasicDBObject();
                query.put("_id", new ObjectId(objectId));
                writeSuccessAjaxResponse(response, JSON.serialize(mongoService.findOne(address, query)));
            } catch (MongoException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port or database or collection is undefined");
        }
    }

    @Autowired
    @Qualifier("mongoServiceBasicOperations")
    MongoServiceBasicOperations basicOperations;

    @RequestMapping(value = "/mongoConsole", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    @SuppressWarnings("unchecked")
    void console(MongoAddress address,
                 @RequestParam(value = "fquery", required = false) String fquery, @RequestParam(value = "foperation", required = false) String foperation,
                 @RequestParam(value = "dbOper", required = false) String dbOper,
                 @RequestParam(value = "skip", required = false) String skip, @RequestParam(value = "limit", required = false) String limit,
                 HttpServletResponse response) {
        if (hasHostPortDbNColl(address) && Strings.isNullOrEmpty(foperation)) {
            viewPaginatedCollectionEntities(address, skip, limit, response);
        } else if (hasHostPortDbNColl(address)) {
            DbOperations operations = null;
            if (!Strings.isNullOrEmpty(dbOper)) {
                operations = gson.fromJson(dbOper, DbOperations.class);
            }
            try {
                mongoFactory.initMap(address);
                assert operations != null;
                operations.getOperations().add(new CommonDbOperation(foperation, fquery, 0));
                writeSuccessAjaxResponse(response, JSON.serialize(composer.execute(address, operations, Ints.tryParse(skip), Ints.tryParse(limit))));
            } catch (MongoException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            }
        }

    }


}
