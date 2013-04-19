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
    public static final String NEW_LINE_STR = "\n";
    public static final String DOUBLE_BACKSLASH = "\\";
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int FIRST = 1;

    public CSVPlusFormatter(Delimiters delimiter) {
        setDelimiters(delimiter);
    }

    @Override
    public List<LinkedHashMap<String, String>> decodeRecord(List<String> rawData, boolean hasHeader) {

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
//    public final String[] decodeRecord(String record) {
//        String[] fields = record.split(delimiterStr);
//        return fields;
//    }

    @Override
    public final List<String> encodeRecord(List<LinkedHashMap<String, String>> records, boolean hasHeader) {
//        StringBuilder strBldRecord = new StringBuilder(NEW_LINE_STR);
//        int lastField = fields.length - ONE; //last field is 1 less than length
//        for (int i = ZERO; i < fields.length; i++) {
//            strBldRecord.append(fields[i]);
//            if (i < lastField) { //don't want delimiter after last field
//                strBldRecord.append(delimiterChar);
//            }
//        }
//        return strBldRecord.toString();
        //input = multiple records, possibly w/ headers
        //output = an array of Strings ready to be written
        List<String> encodedData = new ArrayList<String>();
        //initialize temporary variables
        int recordCount = ZERO;
        StringBuilder line = new StringBuilder();
        Set<String> keys = null;
        for (LinkedHashMap record : records) {
            line.append(NEW_LINE_STR);
            recordCount++;
            keys = record.keySet();
            int lastField = keys.size();
            int fieldCount = 1;
            if (hasHeader && recordCount == FIRST) {
                for (String key : keys) {
                    line.append(key);
                    if (fieldCount < lastField) {
                        line.append(delimiterChar);
                    }//don't want delimiter after last field
                }
                encodedData.add(line.toString());
                line.delete(ZERO, line.length());
                line.append(NEW_LINE_STR);
            }
            for (String key : keys) {
                String field = (String) record.get(key);
                line.append(field);
                if (fieldCount < lastField) {
                    line.append(delimiterChar);
                }//don't want delimiter after last field
            }
            encodedData.add(line.toString());
            line.delete(ZERO, line.length());
        }
        return encodedData;
    }
//    public final String encodeRecord(String[] fields) {
//        StringBuilder strBldRecord = new StringBuilder("" + NEW_LINE_STR);
//        int lastField = fields.length - ONE; //last field is 1 less than length
//        for (int i = ZERO; i < fields.length; i++) {
//            strBldRecord.append(fields[i]);
//            if (i < lastField) { //don't want delimiter after last field
//                strBldRecord.append(delimiterChar);
//            }
//        }
//        return strBldRecord.toString();
//    }

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

//        CSVPlusFormatter csv = new CSVPlusFormatter(DelimiterNames.CARET);
//        String[] testStr = {"Deanna", "Moore", "431 Cortez Court",
//            "Naperville", "IL", "60789", "ilovecats@gmail.com", "708-555-6688"};
//        String out = csv.encodeRecord(testStr);
//        System.out.println("endoded record: " + out);
//        String[] out2 = csv.decodeRecord("Ariana^Dancer^122 Spruce Lane^"
//                + "Glenwood^IL^60425^noticeme@gmail.com^708-555-1232");
//        for (String s : out2) {
//            System.out.println(s);
//        }
        List<String> rawData = new ArrayList<String>();
        //rawData.add("Total Fees,Total Hours" );
        //rawData.add("21.65,34.50");
        //rawData.add("44.0,66.0");
        rawData.add("First Name!Last Name!Street Address!City!State!Zip!Email Address!Phone Nbr");
        rawData.add("Malaya!Science!1234 Wood Street!Griffith!IN!46309!giggles@yahoo.com!464-555-9875");
        rawData.add("Laura!Strecjek!405 Walker Road!Normal!IL!60621!mywedding@gmail.com!796-555-6752");
        CSVPlusFormatter csv = new CSVPlusFormatter(Delimiters.EXLAMATION_POINT);
        List<LinkedHashMap<String, String>> myMap =
                csv.decodeRecord(rawData, true);
        for (LinkedHashMap record : myMap) {
            System.out.println(record);
        }
        List<String> recordStrings = csv.encodeRecord(myMap, true);
        for (String s : recordStrings) {
            System.out.println(s);
        }
    }
}
