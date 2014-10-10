package ru.spb.iac.services.impl.mongo;

import com.google.common.base.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;
import ru.spb.iac.exceptions.*;
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
    @Qualifier("mongoTemplateFactory")
    private MongoTemplateFactory mongoTemplateFactory;

    @Override
    public void save(MongoAddress mongoAddress, Object obj) throws MongoException {
        if(!Strings.isNullOrEmpty(mongoAddress.getDbName()) && !Strings.isNullOrEmpty(mongoAddress.getCollName())){
            MongoOperations mongoOperations = mongoTemplateFactory.getOperationsTemplate(mongoAddress);
            mongoOperations.save(obj, mongoAddress.getCollName());
        } else{
            throw new MongoException("No collection name of database name specified");
        }
    }

    @Override
    public void update(MongoAddress mongoAddress, Update update) {

    }

    @Override
    public List<Object> find(MongoAddress mongoAddress, Query query) {
        List<Object> resultLst = null;
        MongoOperations mongoOperations = mongoTemplateFactory.getOperationsTemplate(mongoAddress);
        resultLst = mongoOperations.find(query, Object.class, mongoAddress.getCollName());
        return resultLst;
    }

    @Override
    public void findOne(MongoAddress mongoAddress, Query query) {

    }

    @Override
    public boolean remove(MongoAddress mongoAddress, Query query) {
        return false;
    }
}
