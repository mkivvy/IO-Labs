package lab4;

/**
 *
 * @author Mary
 */
public class TextFileReadWriteException extends RuntimeException {
    private final static String MESSAGE = "Text file read/write request could "
            + "not be completed.";

    public TextFileReadWriteException() {
        super(MESSAGE);
    }

    public TextFileReadWriteException(String message) {
        super(message);
    }

    public TextFileReadWriteException(String message, Throwable thrwbl) {
        super(message, thrwbl);
    }

    public TextFileReadWriteException(Throwable thrwbl) {
        super(thrwbl);
    }

    public TextFileReadWriteException(String message, Throwable thrwbl, boolean bln, boolean bln1) {
        super(message, thrwbl, bln, bln1);
    }
    
}
