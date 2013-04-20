package lab4;

import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Mary
 */
public abstract interface TextFileFormatStrategy {

    public abstract List<LinkedHashMap<String,String>> decodeRecords 
            (List<String> rawData, boolean hasHeader);
 
    public abstract List<String> encodeRecords
            (List<LinkedHashMap<String,String>> records, boolean hasHeader);
    
}
