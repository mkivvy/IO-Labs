package lab4;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Mary
 */
public class CSVPlusFormatter implements TextFileFormatStrategy {

    private char delimiterChar;
    private String delimiterStr;
    public static final String DOUBLE_BACKSLASH = "\\";
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int FIRST = 1;

    public CSVPlusFormatter(Delimiters delimiter) {
        setDelimiters(delimiter);
    }

    @Override
    public List<LinkedHashMap<String, String>> decodeRecords
            (List<String> rawData, boolean hasHeader) {

        //First, create an arraylist of linkedhashmaps to be returned to caller
        List<LinkedHashMap<String, String>> decodedData =
                new ArrayList<LinkedHashMap<String, String>>();

        //initialize temporary variables
        int lineCount = ZERO;
        String[] header = null;

        //Now we loop through the list of strings(lines) passed in
        for (String data : rawData) {
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
                decodedData.add(record);
            }
        } //finished 1st for loop & processed all input data - whew!!
        return decodedData;
    }

    @Override
    public final List<String> encodeRecords
            (List<LinkedHashMap<String, String>> records, boolean hasHeader) {
        //initialize output, an array of Strings ready to be written
        List<String> encodedData = new ArrayList<String>();
        //initialize temporary variables
        int recordCount = ZERO;
        StringBuilder line = new StringBuilder();
        Set<String> keys = null;
        //loop through all the records in the map
        for (LinkedHashMap record : records) {
            recordCount++;
            keys = record.keySet(); //get the map keys
            int lastField = keys.size(); //determine the record size
            int fieldCount = FIRST; //start w/ first field in record
            //************* header *************************            
            if (hasHeader && recordCount == FIRST) {
                //This creates the header row by appending each header name
                //followed by the delimiter
                for (String key : keys) {
                    line.append(key);
                    if (fieldCount < lastField) {
                        line.append(delimiterChar);
                    }//don't want delimiter after last field
                    fieldCount++;
                }
                //add the header row to the output list
                encodedData.add(line.toString());
                //reset the contents of line
                line.delete(ZERO, line.length());
            }
            //************* header *************************            
            fieldCount = FIRST; //start w/ first field in record
            //add each field value from the map to the line followed by delimiter
            for (String key : keys) {
                String field = (String) record.get(key);
                line.append(field);
                if (fieldCount < lastField) {
                    line.append(delimiterChar);
                }//don't want delimiter after last field
                fieldCount++;
            }
            //add the line to output list & reset contents of line
            encodedData.add(line.toString());
            line.delete(ZERO, line.length());
        }
        return encodedData;
    }

    public final char getDelimiterChar() {
        return delimiterChar;
    }

    public final String getDelimiterStr() {
        return delimiterStr;
    }

    public final void setDelimiters(Delimiters delimiter) {
        delimiterChar = delimiter.getValue();
        delimiterStr = DOUBLE_BACKSLASH + delimiterChar;
//        System.out.printf("char=%c  string =%s", delimiterChar, delimiterStr);
    }

    public static void main(String[] args) {

        List<String> rawData = new ArrayList<String>();
//        rawData.add("Total Fees,Total Hours" );
//        rawData.add("21.65,34.50");
//        rawData.add("44.0,66.0");
        rawData.add("First Name!Last Name!Street Address!City!State!Zip!Email Address!Phone Nbr");
        rawData.add("Malaya!Science!1234 Wood Street!Griffith!IN!46309!giggles@yahoo.com!464-555-9875");
        rawData.add("Laura!Strecjek!405 Walker Road!Normal!IL!60621!myweing@gmail.com!796-555-6752");
        CSVPlusFormatter csv = new CSVPlusFormatter(Delimiters.EXLAMATION_POINT);
        List<LinkedHashMap<String, String>> myMap =
                csv.decodeRecords(rawData, true);
        for (LinkedHashMap record : myMap) {
            System.out.println(record);
        }
        List<String> recordStrings = csv.encodeRecords(myMap, true);
        for (String s : recordStrings) {
            System.out.println(s);
        }
    }
}
