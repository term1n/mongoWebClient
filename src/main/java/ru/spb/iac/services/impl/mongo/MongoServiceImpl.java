package ru.spb.iac.services.impl.mongo;

import com.google.common.base.*;
import com.mongodb.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;
import ru.spb.iac.exceptions.MongoException;
import ru.spb.iac.services.*;
import ru.spb.iac.ui.model.*;

import java.util.*;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
@Log4j
@Service("mongoService")
//TODO check it (m.sekushin)
public class MongoServiceImpl implements MongoService {

    @Autowired
    @Qualifier("mongoTemplateFactory")
    private MongoTemplateFactory mongoTemplateFactory;

//    @Override
//    public void save(MongoAddress mongoAddress, Object obj) throws MongoException {
//        if(!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())){
//            MongoOperations mongoOperations = mongoTemplateFactory.getOperationsTemplate(mongoAddress);
//            mongoOperations.save(obj, mongoAddress.getCollName());
//        } else{
//            throw new MongoException("No collection name of database name specified");
//        }
//    }

    public void save(MongoAddress mongoAddress, Object obj) throws MongoException {
        if(!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())){
            MongoOperations mongoOperations = mongoTemplateFactory.getOperationsTemplate(mongoAddress);
            Object o = com.mongodb.util.JSON.parse(obj.toString());
            DBObject dbObj = (DBObject) o;
            DBCollection collection = mongoOperations.getCollection(mongoAddress.getCollName());
            collection.insert(dbObj);

        }
        else{
            throw new MongoException("No collection name of database name specified");
        }
    }


    @Override
    public void update(MongoAddress mongoAddress, Update update) {

    }


    public List<Object> findAll(MongoAddress mongoAddress) throws MongoException {
        List<Object> resultLst = null;
        MongoOperations mongoOperations = null;
        try {
            mongoOperations = mongoTemplateFactory.getOperationsTemplate(mongoAddress);
//            resultLst = mongoOperations.find(query, Object.class, mongoAddress.getCollName());
            resultLst = mongoOperations.findAll(Object.class, mongoAddress.getCollName());

        } catch (MongoException e) {
            log.error(e.getMessage(),e);
            throw e;
        }
        return resultLst;
    }

    @Override
    public Object findOne(MongoAddress mongoAddress, Query query) throws MongoException {
        MongoOperations mongoOperations = null;
        try {
            mongoOperations = mongoTemplateFactory.getOperationsTemplate(mongoAddress);
            //query=Query.query(Criteria.where("id").is(id))
            return mongoOperations.findOne(query, Object.class, mongoAddress.getCollName());
        } catch (MongoException e) {
            log.error(e.getMessage(),e);
            throw e;
        }
    }

    @Override
    public boolean remove(MongoAddress mongoAddress, Query query) {
        MongoOperations mongoOperations = null;
        try {
            mongoOperations = mongoTemplateFactory.getOperationsTemplate(mongoAddress);
            //query=Query.query(Criteria.where("id").is(id))
            mongoOperations.remove(query, Object.class, mongoAddress.getCollName());
            return true;
        } catch (MongoException e) {
            log.error(e.getMessage(),e);
            //throw e;
            return false;
        }

        //return false;
    }

//    @Override
//    public List<Object> find(MongoAddress mongoAddress, Query query) throws MongoException {
//        List<Object> resultLst = null;
//        MongoOperations mongoOperations = null;
//        try {
//            mongoOperations = mongoTemplateFactory.getOperationsTemplate(mongoAddress);
//            resultLst = mongoOperations.find(query, Object.class, mongoAddress.getCollName());
//        } catch (MongoException e) {
//            log.error(e.getMessage(),e);
//            throw e;
//        }
//        return resultLst;
//    }
//
//    @Override
//    public Object findOne(MongoAddress mongoAddress, Query query) throws MongoException {
//        MongoOperations mongoOperations = null;
//        try {
//            mongoOperations = mongoTemplateFactory.getOperationsTemplate(mongoAddress);
//            return mongoOperations.findOne(query, Object.class, mongoAddress.getCollName());
//        } catch (MongoException e) {
//            log.error(e.getMessage(),e);
//            throw e;
//        }
//    }
//
//    @Override
//    public boolean remove(MongoAddress mongoAddress, Query query) {
//        return false;
//    }
}
