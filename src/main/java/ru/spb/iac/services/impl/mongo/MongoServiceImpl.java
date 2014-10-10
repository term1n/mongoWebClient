package ru.spb.iac.services.impl.mongo;

import lombok.extern.log4j.Log4j;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ru.spb.iac.services.MongoService;
import ru.spb.iac.ui.model.MongoAddress;

import java.util.List;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
@Log4j
@Service("mongoService")
public class MongoServiceImpl implements MongoService {
    @Override
    public void save() {

    }

    @Override
    public void update() {

    }

    @Override
    public List<Object> find(MongoAddress mongoAddress, Query query) {
        return null;
    }

    @Override
    public void findOne() {

    }

    @Override
    public boolean remove() {
        return false;
    }
}
