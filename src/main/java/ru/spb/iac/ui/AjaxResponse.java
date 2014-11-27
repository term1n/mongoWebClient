package ru.spb.iac.ui;

/**
 * Created by manaev on 10.10.14.
 */
public class AjaxResponse {
    // Success ajax query
    public static final String STATUS_SUCCESS = "SUCCESS";
    // Failed ajax query
    public static final String STATUS_FAILED = "FAILED";
    // result status of request
    private String status;
    private Object errors;
    private Object model;

    public AjaxResponse(Object errors, Object model) {
        this(STATUS_FAILED, errors, model);
    }


    public AjaxResponse(Object model) {
        this(STATUS_SUCCESS, null, model);
    }

    AjaxResponse(String status, Object errors, Object model) {
        this.status = status;
        this.errors = errors;
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public Object getErrors() {
        return errors;
    }

    public Object getModel() {
        return model;
    }
}
