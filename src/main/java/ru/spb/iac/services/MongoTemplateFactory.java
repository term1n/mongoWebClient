package ru.spb.iac.services;

import org.springframework.data.mongodb.core.*;
import ru.spb.iac.exceptions.*;
import ru.spb.iac.ui.model.*;

import java.util.*;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
public interface MongoTemplateFactory {
    /**
     * init MongoOperation's class instances for each database for the specified host and port of the mongodb
     * @param mongoAddress MongoAddress, host and port strictly required
     * @throws MongoException
     */
    public void initMap(MongoAddress mongoAddress) throws MongoException;

    /**
     * get MongoOperations instance to deal with collections of the selected database
     * @param mongoAddress MongoAddress, host, port and dbName strictly required
     * @return
     * @throws MongoException
     */
    public MongoOperations getOperationsTemplate(MongoAddress mongoAddress) throws MongoException;

    /**
     * get database names for the specified host and port of mongodb
     * @param mongoAddress MongoAddress, host and port strictly required
     * @return list of databases names or null
     */
    public List<String> getDBNames(MongoAddress mongoAddress);

    /**
     * get collections names for the selected database
     * @param mongoAddress MongoAddress, host, port and dbName strictly required
     * @return list of collection names or null
     */
    public List<String> getCollectionsName(MongoAddress mongoAddress);

    /**
     * get map of database name->collections names for the selected mongodb on host-port
     * @param mongoAddress MongoAddress, host and port strictly required
     * @return map or null
     */
    public Map<String,List<String>> getMongoInfo(MongoAddress mongoAddress);
}
