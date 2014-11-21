package ru.spb.iac.services.impl.mongo;

/**
 * Created by manaev on 21.11.14.
 */

import com.google.common.collect.*;
import com.mongodb.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Basic operations with cursors and other stuff
 */
@Service("mongoServiceBasicOperations")
public class MongoServiceBasicOperations {

    public List<DBObject> getCursorBody(DBCursor cursor){
        List<DBObject> result;
        result = Lists.newArrayList(cursor.iterator());
        cursor.close();
        return result;
    }

}
