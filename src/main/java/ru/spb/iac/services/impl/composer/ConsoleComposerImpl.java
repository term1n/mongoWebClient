package ru.spb.iac.services.impl.composer;

import com.mongodb.*;
import lombok.extern.log4j.*;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.type.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import ru.spb.iac.enums.*;
import ru.spb.iac.exceptions.*;
import ru.spb.iac.exceptions.MongoException;
import ru.spb.iac.services.*;
import ru.spb.iac.services.impl.mongo.*;
import ru.spb.iac.ui.models.*;
import ru.spb.iac.ui.models.database.*;

import java.io.*;
import java.util.*;

/**
 * Created by manaev on 11.02.15.
 */
@Log4j
@Service("consoleComposerService")
public class ConsoleComposerImpl implements ConsoleComposer {

    @Autowired
    @Qualifier("mongoServiceBasicOperations")
    MongoServiceBasicOperations basicOperations;

    @Autowired
    @Qualifier("mongoService")
    MongoService mongoService;

    public BasicDBObject composeCriteria(String query) throws IOException {
        BasicDBObject criteria = new BasicDBObject();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(
                query,
                new TypeReference<Map<String, Object>>() {
                }
        );
        for (String key : map.keySet()) {
            criteria.put(key, map.get(key));
        }
        return criteria;
    }

    @Override
    public Object execute(MongoAddress address, DbOperations operations) throws MongoException, IOException {
        DBCursor cursor = null;
        try {
            switch (OperationTypes.valueOf(operations.get(0).getOperation())) {
                case FIND: {
                    cursor = mongoService.getCollection(address).find(composeCriteria(operations.get(0).getQuery()));
                    switch (operations.getOperations().size()) {
                        case 1: {
                            return basicOperations.getCursorBody(cursor);
                        }
                        case 2: {
                            Object temp = getLevel(operations, 1, cursor);
                            return receiveCursor(cursor, temp);
                        }
                        case 3: {
                            Object temp = getLevel(operations, 1, cursor);
                            if (temp instanceof DBCursor) {
                                temp = getLevel(operations, 2, (DBCursor) temp);
                                return receiveCursor(cursor, temp);
                            } else {
                                return temp;
                            }
                        }
                        default: {
                            throw new MongoException("Operation not supported");
                        }
                    }
                }
                case AGGREGATE: {
                    throw new MongoException("Operation not supported");
                }
                case SORT: {
                    throw new MongoException("Operation not supported");
                }
                case COUNT: {
                    throw new MongoException("Operation not supported");
                }
                default:{
                    throw new MongoException("Operation not supported");
                }
            }
        } catch (MongoConsoleException e) {
            throw new MongoException(e.getMessage(), e);
        } catch (IOException e) {
            throw new MongoException(e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public Object execute(MongoAddress address, DbOperations operations, Integer skip, Integer limit) throws MongoException {
        DBCursor cursor = null;
        try {
            switch (OperationTypes.valueOf(operations.get(0).getOperation())) {
                case FIND: {
                    cursor = mongoService.getCollection(address).find(composeCriteria(operations.get(0).getQuery()),new BasicDBObject("_id", true)).skip(skip).limit(limit);
                    switch (operations.getOperations().size()) {
                        case 1: {
                            return basicOperations.getCursorBody(cursor);
                        }
                        case 2: {
                            Object temp = getLevel(operations, 1, cursor);
                            return receiveCursor(cursor, temp);
                        }
                        case 3: {
                            Object temp = getLevel(operations, 1, cursor);
                            if (temp instanceof DBCursor) {
                                temp = getLevel(operations, 2, (DBCursor) temp);
                                return receiveCursor(cursor, temp);
                            } else {
                                return temp;
                            }
                        }
                        default: {
                            throw new MongoException("Operation not supported");
                        }
                    }
                }
                case AGGREGATE: {
                    throw new MongoException("Operation not supported");
                }
                case SORT: {
                    throw new MongoException("Operation not supported");
                }
                case COUNT: {
                    throw new MongoException("Operation not supported");
                }
                default:{
                    throw new MongoException("Operation not supported");
                }
            }
        } catch (MongoConsoleException e) {
            throw new MongoException(e.getMessage(), e);
        } catch (IOException e) {
            throw new MongoException(e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private Object receiveCursor(DBCursor cursor, Object temp) {
        if (temp instanceof DBCursor) {
            return basicOperations.getCursorBody(cursor);
        } else {
            return temp;
        }
    }

    public Object getLevel(DbOperations operations, int level, DBCursor cursor) throws IOException, MongoConsoleException {
        switch (OperationTypes.valueOf(operations.get(level).getOperation())) {
            case FIND: {
                return cursor.getCollection().find(composeCriteria(operations.get(level).getQuery()),new BasicDBObject("_id", true));
            }
            case AGGREGATE: {
                throw new MongoConsoleException("Operation not supported");
            }
            case SORT: {
                return cursor.sort(composeCriteria(operations.get(level).getQuery()));
            }
            case COUNT: {
                int i = cursor.count();
                cursor.close();
                return i;
            }
            default:{
                throw new MongoConsoleException("Operation not supported");
            }
        }
    }

}
