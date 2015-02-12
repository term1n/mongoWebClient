package ru.spb.iac.exceptions;

/**
 * Created by manaev on 11.02.15.
 */
public class MongoConsoleException extends Exception {
    public MongoConsoleException() {
    }
    public MongoConsoleException(String message) {
        super(message);
    }

    public MongoConsoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public MongoConsoleException(Throwable cause) {
        super(cause);
    }
}
