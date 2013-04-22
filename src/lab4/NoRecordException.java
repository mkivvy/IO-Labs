package lab4;

import java.security.InvalidParameterException;

/**
 * NoRecordException extends InvalidParameterException.
 * It is used when a record is expected but is not found.
 * 
 * @author Mary King, mking@my.wctc.edu
 * @version 1.0
 */
public class NoRecordException extends InvalidParameterException {
    private final static String MESSAGE = "No record found";
    
    /**
     * Constructs a NoRecordException with the default exception message.
     */
    public NoRecordException() {
        super(MESSAGE);
    }

    /**
     * Constructs a NoRecordException with the specified exception message.
     * 
     * @param message a detail message to be conveyed for this exception
     */
    public NoRecordException(String message) {
        super(message);
    }
}
