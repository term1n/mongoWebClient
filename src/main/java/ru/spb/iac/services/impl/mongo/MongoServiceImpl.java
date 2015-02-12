package ru.spb.iac.services.impl.mongo;

import com.google.common.base.*;
import com.mongodb.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import ru.spb.iac.exceptions.MongoException;
import ru.spb.iac.services.*;
import ru.spb.iac.ui.models.*;

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
    public void dropCollection(MongoAddress mongoAddress) throws MongoException {
        if (!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())) {
            mongoTemplateFactory.getOperationsTemplate(mongoAddress).dropCollection(mongoAddress.getCollName());
        } else {
            throw new MongoException("No collection name of database name specified");
        }
    }

    @Override
    public DBCollection getCollection(MongoAddress mongoAddress) throws MongoException {
        if (!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())) {
            return mongoTemplateFactory.getOperationsTemplate(mongoAddress).getCollection(mongoAddress.getCollName());
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
    public long collectionSize(MongoAddress mongoAddress) throws MongoException {
        if (!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())) {
            DBCollection collection = mongoTemplateFactory.getOperationsTemplate(mongoAddress).getCollection(mongoAddress.getCollName());
            return collection.count();
        } else {
            throw new MongoException("No collection name of database name specified");
        }
    }

    @Override
    public long collectionSize(MongoAddress mongoAddress, BasicDBObject criteria) throws MongoException {
        if (!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())) {
            DBCollection collection = mongoTemplateFactory.getOperationsTemplate(mongoAddress).getCollection(mongoAddress.getCollName());
            return collection.count(criteria);
        } else {
            throw new MongoException("No collection name of database name specified");
        }
    }

    @Override
    public List<DBObject> find(MongoAddress mongoAddress, BasicDBObject query, BasicDBObject fields, int skip, int limit) throws MongoException {
        if (!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())) {
            DBCollection collection = mongoTemplateFactory.getOperationsTemplate(mongoAddress).getCollection(mongoAddress.getCollName());
            return basicOperations.getCursorBody(collection.find(query,fields).limit(limit).skip(skip));
        } else {
            throw new MongoException("No collection name of database name specified");
        }
    }

    @Override
    public List<DBObject> find(MongoAddress mongoAddress, BasicDBObject query,BasicDBObject fields) throws MongoException {
        if (!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())) {
            DBCollection collection = mongoTemplateFactory.getOperationsTemplate(mongoAddress).getCollection(mongoAddress.getCollName());
            return basicOperations.getCursorBody(collection.find(query,fields));
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


    @Override
    public void findAndRemove(MongoAddress mongoAddress, BasicDBObject query) throws MongoException {
        if (!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())) {
            DBCollection collection = mongoTemplateFactory.getOperationsTemplate(mongoAddress).getCollection(mongoAddress.getCollName());
            collection.remove(collection.findOne(query));
        } else {
            throw new MongoException("No collection name of database name specified");
        }
    }
}
