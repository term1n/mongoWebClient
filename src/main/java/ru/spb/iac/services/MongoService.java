package ru.spb.iac.services;

import com.mongodb.*;
import ru.spb.iac.exceptions.MongoException;
import ru.spb.iac.ui.model.*;

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
    public DBObject findOne(MongoAddress mongoAddress, BasicDBObject query) throws MongoException;
}
