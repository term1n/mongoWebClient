package ru.spb.iac.ui.model;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
public class MongoAddress {

    private String host;
    private Integer port;
    private String dbName;
    private String collName;

    public MongoAddress() {
    }

    public MongoAddress(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public MongoAddress(String host, Integer port, String dbName) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
    }

    public MongoAddress(String host, Integer port, String dbName, String collName) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.collName = collName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getCollName() {
        return collName;
    }

    public void setCollName(String collName) {
        this.collName = collName;
    }

    @Override
    public String toString() {
        return "MongoAddress{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", dbName='" + dbName + '\'' +
                ", collName='" + collName + '\'' +
                '}';
    }


}
