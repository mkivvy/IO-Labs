package lab4;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * ListAndLinkedHashMapConverter converts a List of LinkedHashMaps into a
 * List of Strings and converts a List of Strings into a List of LinkedHashMaps.
 * This class can be used to prepare data to be used in conjunction with 
 * reading and writing text files using the TextFileReadStrategy and 
 * TextFileWriteStartaegy.
 * <p>
 * The method convertMapToList converts a List of LinkedHashMaps into a List of 
 * Strings containing records separated by the delimiter.
 * <p>
 * The method convertListToMap uses the delimiter to convert a List of Strings 
 * into a List of LinkedHashMaps containing a key field and value field in each
 * record in the map. 
 * 
 * @author Mary King, mking@my.wctc.edu
 * @version 1.0
*/
public class ListAndLinkedHashMapConverter {
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int FIRST = 1;
    public static final String DOUBLE_BACKSLASH = "\\";
    public static final String NO_MAP_RECORDS_MSG 
            = "There are no map records to convert to list.";
    public static final String NO_LIST_RECORDS_MSG 
            = "There are no list records to convert to map.";

    /**
     * This method converts a List of LinkedHashMaps (map) into a List of
     * Strings (listData).  This method also uses the Enum class delimiter 
     * (delim) passed in as the character included between each of the field 
     * values for each String returned.  If the input data contains header 
     * information as the key values, a header record is created as the first 
     * record in the returned List.
     * 
     * @param map a List of LinkedHashMaps containing record data, may or
     * may not include header information, not null, not empty
     * @param delim of type Delimiters specifying the character used as the
     * record separator
     * @param hasHeader boolean indicating whether the input List of 
     * LinkedHashMaps contains header information in the key fields
     * @return a List of Strings containing data separated by the specified
     * delimiter character
     * @throws NullPointerException if input map is null
     * @throws IllegalArgumentException if input map is empty
     */
    public final List<String> convertMapToList
            (List<LinkedHashMap<String, String>> map, Delimiters delim,
            boolean hasHeader) 
            throws NullPointerException, IllegalArgumentException {
        if (map == null) {
            throw new NullPointerException(NO_MAP_RECORDS_MSG);
        }
        if (map.isEmpty()) {
            throw new IllegalArgumentException(NO_MAP_RECORDS_MSG);
        }
        
        char delimChar = delim.getValue();
        List<String> listData = new ArrayList<String>();
        Set<String> keys = null;
        int recordCount = ZERO;
        StringBuilder line = new StringBuilder();
        for (LinkedHashMap record : map) {
            recordCount++;
            keys = record.keySet();
            int lastField = keys.size();
            int fieldCount = FIRST;
//************* header *************************            
            if (hasHeader && recordCount == FIRST) {
                //This creates the header row by appending each header name
                //followed by the delimiter
                for (String key : keys) {
                    line.append(key);
                    if (fieldCount < lastField) {
                        line.append(delimChar);
                    }//don't want delimiter after last field
                    fieldCount++;
                }
                listData.add(line.toString());
                //reset the contents of line
                line.delete(0, line.length());
            }
//************* header *************************            
            fieldCount = FIRST; //start w/ first field in record
            for (String key : keys) {
                String field = (String) record.get(key);
                line.append(field);
                if (fieldCount < lastField) {
                    line.append(delimChar);
                    }//don't want delimiter after last field
                fieldCount++;
            }
            listData.add(line.toString());
            line.delete(0, line.length());
        }
        return listData;
    }

    /**
     * This method converts a List of Strings (listData) into a List of 
     * LinkedHashMaps (mapData) containing a key field and value field in 
     * each record in the map.  If the input data does not contain a header
     * record, integers starting at zero are used as the record keys.  This
     * method also uses the Enum class delimiter (delim) passed in to split
     * the Strings of listData into fields for the returned mapData.
     * 
     * @param listData a List of Strings containing data separated by the
     * specified delimiter, not null, not empty
     * @param delim of type Delimiters specifying the character used as the
     * record separator
     * @param hasHeader boolean indicating whether the input List of Strings
     * contains a header String as the first String
     * @return a List of LinkedHashMaps containing a key field and value field 
     * in each record in the map.  If there is no header, integer values 
     * starting at zero are used for the key values.
     * @throws NullPointerException if input listData is null
     * @throws IllegalArgumentException if input listData is empty
     */
    public final List<LinkedHashMap<String, String>> convertListToMap
            (List<String> listData, Delimiters delim, boolean hasHeader) 
            throws NullPointerException, IllegalArgumentException {
        
        if (listData == null) {
            throw new NullPointerException(NO_LIST_RECORDS_MSG);
        }
        if (listData.isEmpty()) {
            throw new IllegalArgumentException(NO_LIST_RECORDS_MSG);
        }

        String delimiterStr = DOUBLE_BACKSLASH + delim.getValue();
        
        //arraylist of linkedhashmaps to be returned 
        List<LinkedHashMap<String, String>> mapData =
                new ArrayList<LinkedHashMap<String, String>>();

        //initialize temporary variables
        int lineCount = ZERO;
        String[] header = null;

        //loop through the list of strings passed in
        for (String data : listData) {
            lineCount++;
            //split the line into a string array using the specified delimiter
            String[] splitData = data.split(delimiterStr);
            if (hasHeader && (lineCount == FIRST)) {
                header = splitData; //this is the header row, set header values
            }

            //create the linkedhashmap to contain record data
            LinkedHashMap<String, String> record =
                    new LinkedHashMap<String, String>();
            //loop through each field from the split line 
            for (int i = ZERO; i < splitData.length; i++) {
                if (hasHeader && lineCount == FIRST) {
                    continue; //already took care of header row above
                } else if (hasHeader) {
                    //populate String1 w/header field name & String2 w/
                    //matching data
                    record.put(header[i], splitData[i]);
                } else { //no header
                    //populate String1 w/field number to keep key unique
                    //& String2 w/data
                    record.put("" + i, splitData[i]);
                }
            } //finished 2nd for loop & have completed record
            if (hasHeader && lineCount == FIRST) {
                //do nothing - don't need a header row keyed w/header fields
            } else {
                mapData.add(record);
            }
        } //finished 1st for loop & processed all input data - whew!!
        return mapData;
    }
}
