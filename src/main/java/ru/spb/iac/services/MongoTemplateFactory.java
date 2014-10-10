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
    public void initMap(MongoAddress mongoAddress) throws MongoException;
    public MongoOperations getOperationsTemplate(MongoAddress mongoAddress);
    public List<String> getDBNames(MongoAddress mongoAddress);
    public List<String> getCollectionsName(MongoAddress mongoAddress);
    public Map<String,List<String>> getMongoInfo(MongoAddress mongoAddress);
}
