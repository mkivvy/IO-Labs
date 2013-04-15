package lab4;

/**
 *
 * @author Mary
 */
public abstract interface TextFileFormatStrategy {

    public abstract String[] decodeRecord(String record);

    public abstract String encodeRecord(String[] fields);
    
}
