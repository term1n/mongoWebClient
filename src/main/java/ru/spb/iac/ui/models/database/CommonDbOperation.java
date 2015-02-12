package ru.spb.iac.ui.models.database;

/**
 * Created by manaev on 10.02.15.
 */
public class CommonDbOperation {
    String operation;
    String query;
    Integer order;

    public CommonDbOperation() {
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public CommonDbOperation(String operation, String query, Integer order) {
        this.operation = operation;
        this.query = query;
        this.order = order;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
