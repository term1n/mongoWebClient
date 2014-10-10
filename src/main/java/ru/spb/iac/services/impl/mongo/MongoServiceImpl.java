package ru.spb.iac.services.impl.mongo;

import com.mongodb.DBObject;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.spb.iac.services.MongoService;
import ru.spb.iac.services.MongoTemplateFactory;
import ru.spb.iac.services.impl.templates.MongoTemplateFactoryImpl;
import ru.spb.iac.ui.model.MongoAddress;

import java.util.List;

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
    public void save(MongoAddress mongoAddress, Object obj) {

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
