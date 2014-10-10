package ru.spb.iac.services;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.spb.iac.ui.model.MongoAddress;

import java.util.List;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
public interface MongoService {
    public void save(MongoAddress mongoAddress, Object obj);
    public void update(MongoAddress mongoAddress, Update update);
    public List<Object> find(MongoAddress mongoAddress, Query query);
    public void findOne(MongoAddress mongoAddress, Query query);
    public boolean remove(MongoAddress mongoAddress, Query query);
}
