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

}
