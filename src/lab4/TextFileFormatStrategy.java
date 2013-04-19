package lab4;

import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Mary
 */
public abstract interface TextFileFormatStrategy {

    public abstract List<LinkedHashMap<String,String>> decodeRecord 
            (List<String> rawData, boolean hasHeader);
 //   public abstract String[] decodeRecord(String record);

    public abstract List<String> encodeRecord
            (List<LinkedHashMap<String,String>> records, boolean hasHeader);
//    public abstract String encodeRecord(String[] fields);
    
}
