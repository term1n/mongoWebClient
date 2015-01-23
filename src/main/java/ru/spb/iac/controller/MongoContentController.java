package ru.spb.iac.controller;

import com.google.common.base.*;
import com.google.common.primitives.*;
import com.mongodb.*;
import com.mongodb.util.*;
import lombok.extern.log4j.*;
import org.bson.types.*;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import ru.spb.iac.exceptions.MongoException;
import ru.spb.iac.services.*;
import ru.spb.iac.ui.*;
import ru.spb.iac.ui.models.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.*;
import java.io.IOException;
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
    @Qualifier("mongoTemplateFactory")
    MongoTemplateFactory mongoFactory;

    Map<String, Integer> operationsMap;

    @PostConstruct
    public void initOperationsMap(){
        operationsMap = new TreeMap<String, Integer>();
        operationsMap.put("find", 1);
    }

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
    void collectionSize(MongoAddress address, HttpServletResponse response, @RequestParam(required = false,value = "query") String query,@RequestParam(required = false,value = "operation") String operation) throws IOException {
        if (hasHostPortDbNColl(address)) {
            try {
                mongoFactory.initMap(address);

                if(!Strings.isNullOrEmpty(query)){
                    BasicDBObject criteria = composeCriteria(query);

                    switch (operationsMap.get(operation)){
                        case 1: {
                            writeSuccessAjaxResponse(response, JSON.serialize(mongoService.collectionSize(address, criteria)));
                            break;
                        }
                        default: {
                            writeErrorAjaxResponse(response, new AjaxResponse("UNSUPPORTED OPERATION") );
                        }
                    }
                }  else{
                    writeSuccessAjaxResponse(response, JSON.serialize(mongoService.collectionSize(address)));
                }

            } catch (MongoException e) {
                log.error(e.getMessage(), e);
                writeErrorAjaxResponse(response, e.getMessage());
            }
        } else {
            writeErrorAjaxResponse(response, "Host or port or database or collection is undefined");
        }
    }

    private BasicDBObject composeCriteria(String query) throws IOException {
        BasicDBObject criteria = new BasicDBObject();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(
                query,
                new TypeReference<Map<String, Object>>() {
                });
        for (String key : map.keySet()){
            criteria.put(key, map.get(key));
        }
        return criteria;
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
                writeSuccessAjaxResponse(response, JSON.serialize(mongoService.find(address,new BasicDBObject(),new BasicDBObject("_id",true))));
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
    @RequestMapping(value = "/viewPaginatedCollectionEntities", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void viewPaginatedCollectionEntities(MongoAddress address, @RequestParam(value = "skip") String skip, @RequestParam(value = "limit") String limit, HttpServletResponse response) {
        if (hasHostPortDbNColl(address)) {
            try {
                mongoFactory.initMap(address);
                writeSuccessAjaxResponse(response, JSON.serialize(mongoService.find(address,new BasicDBObject(),new BasicDBObject("_id",true), Ints.tryParse(skip),Ints.tryParse(limit))));
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
    void updateEntity(MongoAddress address, @RequestParam(value = "dbObject") String dbObject,  HttpServletResponse response) {
        if (hasHostPortDbNColl(address) && !Strings.isNullOrEmpty(dbObject)) {
            try {
                mongoFactory.initMap(address);
                DBObject temp = (DBObject)JSON.parse(dbObject);
                mongoService.save(address,temp);
                writeSuccessAjaxResponse(response,new AjaxResponse("SUCCESS"));
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
    void dropCollection(MongoAddress address,  HttpServletResponse response) {
        if (hasHostPortDbNColl(address)) {
            try {
                mongoFactory.initMap(address);
                mongoService.dropCollection(address);
                writeSuccessAjaxResponse(response,new AjaxResponse("SUCCESS"));
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
    void deleteEntity(MongoAddress address, @RequestParam(value = "id") String id,  HttpServletResponse response) {
        if (hasHostPortDbNColl(address) && !Strings.isNullOrEmpty(id)) {
            try {
                mongoFactory.initMap(address);
                BasicDBObject query = new BasicDBObject();
                query.put("_id", new ObjectId(id));
                mongoService.findAndRemove(address,query);
                writeSuccessAjaxResponse(response,new AjaxResponse("SUCCESS"));
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

    @RequestMapping(value = "/mongoConsole", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void console(MongoAddress address, @RequestParam(value = "query") String query,@RequestParam(value = "operation") String operation, @RequestParam(value = "skip") String skip, @RequestParam(value = "limit") String limit, HttpServletResponse response) {
        if (hasHostPortDbNColl(address) && operationsMap.containsKey(operation)) {
            try {
                mongoFactory.initMap(address);
                BasicDBObject criteria = composeCriteria(query);

                switch (operationsMap.get(operation)){
                    case 1: {
                        writeSuccessAjaxResponse(response, JSON.serialize(mongoService.find(address, criteria,new BasicDBObject("_id",true),Ints.tryParse(skip),Ints.tryParse(limit))));
                        break;
                    }
                    default: {
                        writeErrorAjaxResponse(response, new AjaxResponse("UNSUPPORTED OPERATION") );
                    }
                }


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
        } else {
            writeErrorAjaxResponse(response, "Host or port or database or collection is undefined");
        }
    }



}
