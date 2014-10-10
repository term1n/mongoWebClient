package ru.spb.iac.services.impl;

import com.mongodb.Mongo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ru.spb.iac.exceptions.MongoException;
import ru.spb.iac.services.MongoTemplateFactory;
import ru.spb.iac.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
@Log4j
@Service("mongoTemplateFactory")
public class MongoTemplateFactoryImpl implements MongoTemplateFactory{
    /**
     * Map of all instances of mongo operations
     * class to definate datebase on selected
     * host:port.
     */
    private Map<String, Map<String, MongoOperations>> templatesMap;

    @Autowired
    @Qualifier("commonUtils")
    private CommonUtils commonUtils;

    private MongoFactoryBean mongoFactoryBean;

    public MongoTemplateFactoryImpl() {
        mongoFactoryBean = new MongoFactoryBean();
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
        if (!templatesMap.containsKey(key)){
            mongoFactoryBean.setHost(host);
            mongoFactoryBean.setPort(port);
            MongoFactoryBean(host)
            Mongo mongo = null;
            try {
                mongo = mongoFactoryBean.getObject();
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                throw new MongoException(e.getCause());
            }
            List<String> list = mongo.getDatabaseNames();
            Map<String, MongoOperations> map = new HashMap<String, MongoOperations>();
            for(String db : list){
                map.put(db, new MongoTemplate(mongo,db));
            }
            templatesMap.put(key,map);
        }
    }

    @Override
    public MongoOperations getOperationsTemplate(String host, Integer port, String dbName) {
        return null;
    }

    @Override
    public List<String> getDBNames(String host, Integer port) {

        return null;
    }

    @Override
    public List<String> getCollectionsName(String host, Integer port, String dbName) {
        return null;
    }
}
