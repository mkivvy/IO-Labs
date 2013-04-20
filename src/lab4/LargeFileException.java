package lab4;

import java.security.InvalidParameterException;

/**
 *
 * @author Mary
 */
public class LargeFileException extends InvalidParameterException {
    private final static String MESSAGE = "File is too large to process";
    
    public LargeFileException() {
        super(MESSAGE);
    }

    public LargeFileException(String message) {
        super(message);
    }
}
