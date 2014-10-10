package ru.spb.iac.services;

import org.springframework.data.mongodb.core.query.*;
import ru.spb.iac.exceptions.*;
import ru.spb.iac.ui.model.*;

import java.util.*;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
public interface MongoService {
    public void save(MongoAddress mongoAddress, Object obj) throws MongoException;
    public void update(MongoAddress mongoAddress, Update update);
    public List<Object> find(MongoAddress mongoAddress, Query query);
    public void findOne(MongoAddress mongoAddress, Query query);
    public boolean remove(MongoAddress mongoAddress, Query query);
}
