package lab4;

/**
 *
 * @author Mary
 */
public class TextFileReadException extends RuntimeException {
    private final static String MESSAGE = "Text file read request could not "
            + "be completed.";

    public TextFileReadException() {
        super(MESSAGE);
    }

    public TextFileReadException(String message) {
        super(message);
    }

    public TextFileReadException(String message, Throwable thrwbl) {
        super(message, thrwbl);
    }

    public TextFileReadException(Throwable thrwbl) {
        super(thrwbl);
    }

    public TextFileReadException(String message, Throwable thrwbl, boolean bln, boolean bln1) {
        super(message, thrwbl, bln, bln1);
    }
    
}
