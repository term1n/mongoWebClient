package ru.spb.iac.services;

import org.springframework.data.mongodb.core.MongoOperations;
import ru.spb.iac.ui.model.MongoAddress;

import java.util.List;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
public interface MongoTemplateFactory {
    public void initMap(MongoAddress mongoAddress) throws Exception;
    public MongoOperations getOperationsTemplate(MongoAddress mongoAddress);
    public List<String> getDBNames(MongoAddress mongoAddress);
    public List<String> getCollectionsName(MongoAddress mongoAddress);
}
