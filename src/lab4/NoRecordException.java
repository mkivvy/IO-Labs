package lab4;

import java.security.InvalidParameterException;

/**
 *
 * @author Mary
 */
public class NoRecordException extends InvalidParameterException {
    private final static String MESSAGE = "No record found";
    
    public NoRecordException() {
        super(MESSAGE);
    }

    public NoRecordException(String message) {
        super(message);
    }
}
