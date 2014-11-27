package ru.spb.iac.ui.models.database;

import java.util.*;

/**
 * Created by manaev on 26.11.14.
 */
public class DatabaseInfo {
    private String dbName;
    private List<String> collectionNames;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public List<String> getCollectionNames() {
        return collectionNames;
    }

    public void setCollectionNames(List<String> collectionNames) {
        this.collectionNames = collectionNames;
    }

    public DatabaseInfo() {
    }


    public DatabaseInfo(String dbName, List<String> collectionNames) {
        this.dbName = dbName;
        this.collectionNames = collectionNames;
    }
}
