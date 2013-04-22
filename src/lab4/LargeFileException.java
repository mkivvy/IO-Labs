package lab4;

import java.security.InvalidParameterException;

/**
 * LargeFileException extends InvalidParameterException.
 * It is used when the file passed as input is larger than processing allows.
 * 
 * @author Mary King, mking@my.wctc.edu
 * @version 1.0
 */
public class LargeFileException extends InvalidParameterException {
    private final static String MESSAGE = "File is too large to process";
    
    /**
     * Constructs a LargeFileException with the default exception message.
     */
    public LargeFileException() {
        super(MESSAGE);
    }

    /**
     * Constructs a LargeFileException with the specified exception message.
     * 
     * @param message a detail message to be conveyed for this exception
     */
    public LargeFileException(String message) {
        super(message);
    }
}
