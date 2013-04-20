package lab4;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Mary
 */
public class ListAndLinkedHashMapConverter {
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int FIRST = 1;
    public static final String DOUBLE_BACKSLASH = "\\";

    public final List<String> convertMapToList
            (List<LinkedHashMap<String, String>> map, Delimiters delim,
            boolean hasHeader) {
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

    public final List<LinkedHashMap<String, String>> convertListToMap
            (List<String> listData, Delimiters delim, boolean hasHeader) {
        
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
