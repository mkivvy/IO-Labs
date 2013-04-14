package lab4;

/**
 *
 * @author Mary
 */
public abstract interface TextFileFormatterStrategy {

    public abstract String[] decodeRecord(String record, String delimiterStr);

    public abstract String encodeRecord(String[] fields, char delimiterChar);
    
}
