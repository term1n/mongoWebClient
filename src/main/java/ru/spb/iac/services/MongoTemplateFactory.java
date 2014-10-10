package ru.spb.iac.services;

import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
public interface MongoTemplateFactory {
    public void initMap(String host, Integer port) throws Exception;
    public MongoOperations getOperationsTemplate(String host, Integer port, String dbName);
    public List<String> getDBNames(String host, Integer port);
    public List<String> getCollectionsName(String host, Integer port, String dbName);
}
