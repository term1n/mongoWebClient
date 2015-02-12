package ru.spb.iac.services;

import com.mongodb.*;
import ru.spb.iac.exceptions.MongoException;
import ru.spb.iac.ui.models.*;

import java.util.*;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
public interface MongoService {
    /**
     * saves selected object to collection, mention that collection name in mongo address is strictly required
     * @param mongoAddress address of mongo collection
     * @param obj to save
     * @throws MongoException is thrown if collection name or database name doesn't exist
     */
    public void save(MongoAddress mongoAddress, Object obj) throws MongoException;
    public List<DBObject> findAll(MongoAddress mongoAddress) throws MongoException;
    public List<DBObject> find(MongoAddress mongoAddress, BasicDBObject query) throws MongoException;
    public List<DBObject> find(MongoAddress mongoAddress, BasicDBObject query,BasicDBObject fields) throws MongoException;
    public DBObject findOne(MongoAddress mongoAddress, BasicDBObject query) throws MongoException;
    public List<DBObject> find(MongoAddress mongoAddress, BasicDBObject query,BasicDBObject fields,int skip,int limit) throws MongoException;
    public void findAndRemove(MongoAddress mongoAddress, BasicDBObject query) throws MongoException;
    public void dropCollection(MongoAddress mongoAddress) throws MongoException;
    public long collectionSize(MongoAddress mongoAddress) throws MongoException;
    public long collectionSize(MongoAddress mongoAddress,BasicDBObject criteria) throws MongoException;
    public DBCollection getCollection(MongoAddress mongoAddress) throws MongoException;

}
