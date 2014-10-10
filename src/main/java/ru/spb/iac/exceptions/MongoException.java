package ru.spb.iac.exceptions;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
public class MongoException extends Exception {
    public MongoException() {
    }
    public MongoException(String message) {
        super(message);
    }

    public MongoException(String message, Throwable cause) {
        super(message, cause);
    }

    public MongoException(Throwable cause) {
        super(cause);
    }

}
