package ru.spb.iac.services.impl.mongo;

import com.google.common.base.*;
import com.mongodb.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
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
public class MongoServiceImpl implements MongoService {

    @Autowired
    @Qualifier("mongoServiceBasicOperations")
    MongoServiceBasicOperations basicOperations;

    @Autowired
    @Qualifier("mongoTemplateFactory")
    private MongoTemplateFactory mongoTemplateFactory;

    @Override
    public void save(MongoAddress mongoAddress, Object obj) throws MongoException {
        if (!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())) {
            mongoTemplateFactory.getOperationsTemplate(mongoAddress).save(obj, mongoAddress.getCollName());
        } else {
            throw new MongoException("No collection name of database name specified");
        }
    }
    @Override
    public List<DBObject> findAll(MongoAddress mongoAddress) throws MongoException {
        if (!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())) {
            DBCollection collection = mongoTemplateFactory.getOperationsTemplate(mongoAddress).getCollection(mongoAddress.getCollName());
            return basicOperations.getCursorBody(collection.find());

        } else {
            throw new MongoException("No collection name of database name specified");
        }
    }

    @Override
    public List<DBObject> find(MongoAddress mongoAddress, BasicDBObject query) throws MongoException {
        if (!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())) {
            DBCollection collection = mongoTemplateFactory.getOperationsTemplate(mongoAddress).getCollection(mongoAddress.getCollName());
            return basicOperations.getCursorBody(collection.find(query));
        } else {
            throw new MongoException("No collection name of database name specified");
        }
    }

    @Override
    public DBObject findOne(MongoAddress mongoAddress, BasicDBObject query) throws MongoException {
        if (!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())) {
            DBCollection collection = mongoTemplateFactory.getOperationsTemplate(mongoAddress).getCollection(mongoAddress.getCollName());
            return collection.findOne(query);
        } else {
            throw new MongoException("No collection name of database name specified");
        }
    }
}
