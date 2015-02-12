package ru.spb.iac.ui.models.database;

import java.util.*;

/**
 * Created by manaev on 10.02.15.
 */
public class DbOperations {
    List<CommonDbOperation> operations;

    public CommonDbOperation get(int i) {
        for (CommonDbOperation operation : operations) {
            if (operation.getOrder().equals(i)) {
                return operation;
            }
        }
        return null;
    }

    public DbOperations() {
    }

    public DbOperations(List<CommonDbOperation> operations) {
        this.operations = operations;
    }

    public List<CommonDbOperation> getOperations() {
        if(operations == null){
            operations = new ArrayList<CommonDbOperation>();
        }
        return operations;
    }

    public void setOperations(List<CommonDbOperation> operations) {
        this.operations = operations;
    }
}
