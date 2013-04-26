package FileService;

/**
 * TextFileReadWriteException extends RuntimeException.
 * It is used when an unexpected condition arises while reading or writing to 
 * a text file.
 * 
 * @author Mary King, mking@my.wctc.edu
 * @version 1.0
 */
public class TextFileReadWriteException extends RuntimeException {
    private final static String MESSAGE = "Text file read/write request could "
            + "not be completed.";

    /**
     * Constructs a TextFileReadWriteException with the default exception message.
     */
    public TextFileReadWriteException() {
        super(MESSAGE);
    }

    /**
     * Constructs a TextFileReadWriteException with the specified exception 
     * message.
     * 
     * @param message a detail message to be conveyed for this exception
     */
    public TextFileReadWriteException(String message) {
        super(message);
    }

    /**
     * Constructs a TextFileReadWriteException with the specified exception 
     * message and the cause.
     * 
     * @param message a detail message to be conveyed for this exception
     * @param cause the cause (which is saved for later retrieval by the 
     * Throwable.getCause() method). (A null value is permitted, and indicates 
     * that the cause is nonexistent or unknown.)
     */
    public TextFileReadWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a TextFileReadWriteException with the specified cause and 
     * a detail exception message of (cause==null ? null : cause.toString()) 
     * (which typically contains the class and detail message of cause).
     * 
     * @param cause the cause (which is saved for later retrieval by the 
     * Throwable.getCause() method). (A null value is permitted, and indicates 
     * that the cause is nonexistent or unknown.)
     */
    public TextFileReadWriteException(Throwable cause) {
        super(cause);
    }

}
