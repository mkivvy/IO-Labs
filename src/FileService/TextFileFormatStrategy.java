package FileService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * TextFileFormatStrategy is the interface for reformatting data from a text
 * file.
 * Data is decoded and converted from a List of Strings into a List of 
 * LinkedHashMaps.
 * Data is encoded and converted from a List of LinkedHashMaps into a List of
 * Strings.
 * 
 * @author Mary King, mking@my.wctc.edu
 * @version 1.0
 */
public abstract interface TextFileFormatStrategy<T,E> {

    /**
     * This method converts a List of Strings (rawData) into a List of 
     * LinkedHashMaps (decodedData) containing a key field and value field in 
     * each record in the map.  
     * 
     * @param rawData a List of Strings containing record data, not null, 
     * not empty
     * @param hasHeader boolean indicating whether the input List of Strings
     * contains a header record as the first record
     * @return a List of LinkedHashMaps containing a key field and value field 
     * in each record in the map.  If there is no header, another value will
     * used for the key values.
     * @throws NullPointerException if input rawData is null
     * @throws IllegalArgumentException if input rawData is empty
     */
    public abstract T decodeRecords (E rawData, boolean hasHeader)
            throws NullPointerException, IllegalArgumentException;
 
    /**
     * This method converts a List of LinkedHashMaps (records) into a List of
     * Strings (encodedData).  If the input data contains header information as 
     * the key values, a header record is created as the first record in the 
     * returned List.
     * 
     * @param records a List of LinkedHashMaps containing record data, may or
     * may not include header information, not null, not empty
     * @param hasHeader boolean indicating whether the input List of 
     * LinkedHashMaps contains header information in the key fields
     * @return a String containing all record data to be written
     * @throws NullPointerException if input records is null
     * @throws IllegalArgumentException if input records is empty
     */
    public abstract List<String> encodeRecords (T records, boolean hasHeader)
            throws NullPointerException, IllegalArgumentException;
    
}
