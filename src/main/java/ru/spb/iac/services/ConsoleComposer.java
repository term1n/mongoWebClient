package ru.spb.iac.services;

import com.mongodb.*;
import ru.spb.iac.exceptions.MongoException;
import ru.spb.iac.ui.models.*;
import ru.spb.iac.ui.models.database.*;

import java.io.*;

/**
 * Created by manaev on 11.02.15.
 */
public interface ConsoleComposer {
    public Object execute(MongoAddress address, DbOperations operations, Integer skip, Integer limit) throws MongoException, IOException;
    public Object execute(MongoAddress address, DbOperations operations) throws MongoException, IOException;
    public BasicDBObject composeCriteria(String query) throws IOException;
}
