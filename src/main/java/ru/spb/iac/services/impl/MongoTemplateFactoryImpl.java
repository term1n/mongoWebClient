package ru.spb.iac.services.impl;

import com.mongodb.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.stereotype.*;
import ru.spb.iac.exceptions.MongoException;
import ru.spb.iac.services.*;
import ru.spb.iac.utils.*;

import java.util.*;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
@Log4j
@Service("mongoTemplateFactory")
public class MongoTemplateFactoryImpl implements MongoTemplateFactory {
    /**
     * Map of all instances of mongo operations
     * class to definate datebase on selected
     * host:port.
     */
    private Map<String, Map<String, MongoOperations>> templatesMap;

    @Autowired
    @Qualifier("commonUtils")
    private CommonUtils commonUtils;

    public MongoTemplateFactoryImpl() {
        this.templatesMap = new HashMap<String, Map<String, MongoOperations>>();
    }

    public Map<String, Map<String, MongoOperations>> getTemplatesMap() {
        return templatesMap;
    }

    public void setTemplatesMap(Map<String, Map<String, MongoOperations>> templatesMap) {
        this.templatesMap = templatesMap;
    }

    @Override
    public void initMap(String host, Integer port) throws MongoException {
        String key = commonUtils.convertToKey(host, port);
        if (!templatesMap.containsKey(key)) {
            try {
                MongoClient mongoClient = new MongoClient(host, port);
                List<String> list = mongoClient.getDatabaseNames();
                Map<String, MongoOperations> map = new HashMap<String, MongoOperations>();
                for (String db : list) {
                    map.put(db, new MongoTemplate(new SimpleMongoDbFactory(mongoClient, db)));
                }
                templatesMap.put(key, map);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new MongoException(e.getCause());
            }
        }
    }

    @Override
    public MongoOperations getOperationsTemplate(String host, Integer port, String dbName) {
        String key = commonUtils.convertToKey(host, port);
        if (templatesMap.containsKey(key)) {
            return templatesMap.get(key).get(dbName);
        }
        return null;
    }

    @Override
    public List<String> getDBNames(String host, Integer port) {
        String key = commonUtils.convertToKey(host, port);
        if (templatesMap.containsKey(key)) {
            return new ArrayList<String>(templatesMap.get(key).keySet());
        }
        return null;
    }

    @Override
    public List<String> getCollectionsName(String host, Integer port, String dbName) {
        String key = commonUtils.convertToKey(host, port);
        if (templatesMap.containsKey(key)) {
            MongoOperations temp = templatesMap.get(key).get(dbName);
            if (temp != null) {
                return new ArrayList<String>(temp.getCollectionNames());
            }
        }
        return null;
    }
}
