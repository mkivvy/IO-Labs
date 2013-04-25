package lab4;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * CSVPlusQuoteFormatter uses the TextFileFormatStrategy interface to handle 
 * the encoding and decoding of formatted records using a character delimiter 
 * between fields wrapped in double quotes - e.g. "23.00","87.25" . 
 * The valid delimiter characters are specified in the Enum class Delimiters. 
 * In addition to the widely used comma and tab characters, Delimiters contains
 * other characters that my be used to separate record fields.
 * <p>
 * The Enum class delimiter is passed in at instantiation.
 * <p>
 * The method decodeRecords uses the delimiter to convert a List of Strings into
 * a List of LinkedHashMaps containing a key field and value field in each
 * record in the map.
 * <p>
 * The method encodeRecords converts a List of LinkedHashMaps into a List of
 * Strings containing records separated by the delimiter.
 *
 * @author Mary King, mking@my.wctc.edu
 * @version 1.0
 */
public class CSVPlusQuoteFormatter implements
    TextFileFormatStrategy<List<LinkedHashMap<String, String>>, List<String>> {
    
    private char delimiterChar;
    private String delimiterStr;
    public static final String DOUBLE_BACKSLASH = "\\";
    private static final String DOUBLE_QUOTE = "\"";
    public static final String NEW_LINE_STR = "\n";
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int FIRST = 1;
    public static final String NO_RECORDS_DECODE_MSG = 
            "There are no records to decode.";
    public static final String NO_RECORDS_ENCODE_MSG = 
            "There are no records to encode.";

    /**
     * Constructor instantiates the class setting the delimiter character and
     * delimiter String used for formatting.
     *
     * @param delimiter of type Delimiters specifying the character used as the
     * record's field separator
     */
    public CSVPlusQuoteFormatter(Delimiters delimiter) {
        setDelimiters(delimiter);
    }

    /**
     * This method converts a List of Strings (rawData) into a List of
     * LinkedHashMaps (decodedData) containing a key field and value field in
     * each record in the map. 
     * If the input data does not contain a header record, integers starting at 
     * zero are used as the record keys. 
     * This method uses the String delimiter set during instantiation, 
     * containing the delimiter character surrounded with double quotes, to 
     * separate the rawData into fields for the returned map.
     *
     * @param rawData a List of Strings containing record data separated by a
     * previously specified delimiter character, not null, not empty
     * @param hasHeader boolean indicating whether the input List of Strings
     * contains a header record as the first record
     * @return a List of LinkedHashMaps containing a key field and value field
     * in each record in the map. If there is no header, integer values starting
     * at zero are used for the key values.
     * @throws NullPointerException if input rawData is null
     * @throws IllegalArgumentException if input rawData is empty
     */
    @Override
    public final List<LinkedHashMap<String, String>> 
            decodeRecords(List<String> rawData, boolean hasHeader)
            throws NullPointerException, IllegalArgumentException {
        if (rawData == null) {
            throw new NullPointerException(NO_RECORDS_DECODE_MSG);
        }
        if (rawData.isEmpty()) {
            throw new IllegalArgumentException(NO_RECORDS_DECODE_MSG);
        }

        //First, create an arraylist of linkedhashmaps to be returned to caller
        List<LinkedHashMap<String, String>> decodedData =
                new ArrayList<LinkedHashMap<String, String>>();

        //initialize temporary variables
        int lineCount = ZERO;
        String[] header = null;

        //Now we loop through the list of strings(lines) passed in
        for (String data : rawData) {
            lineCount++;
            //remove beginning & ending double quotes
            String dataSubstr = data.substring(ONE, data.length() - ONE); 
            //split the line into a string array using the specified delimiter
            String[] splitData = dataSubstr.split(delimiterStr);
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

    /**
     * This method converts a List of LinkedHashMaps (records) into a List of
     * Strings (encodedData). 
     * The delimiter character specified at instantiation is used between each 
     * of the record values for each String returned. 
     * Each record value is also surrounded by double quotes.  
     * If the input data contains header information as the key values, a header
     * record is created as the first record in the returned List.
     *
     * @param records a List of LinkedHashMaps containing record data, may or
     * may not include header information, not null, not empty
     * @param hasHeader boolean indicating whether the input List of
     * LinkedHashMaps contains header information in the key fields
     * @return a String containing all record data separated by the previously
     * specified delimiter character with newline characters at the end of each
     * record
     * @throws NullPointerException if input records is null
     * @throws IllegalArgumentException if input records is empty
     */
    @Override
    public final List<String> encodeRecords
            (List<LinkedHashMap<String, String>> records, boolean hasHeader)
            throws NullPointerException, IllegalArgumentException {
        if (records == null) {
            throw new NullPointerException(NO_RECORDS_ENCODE_MSG);
        }
        if (records.isEmpty()) {
            throw new IllegalArgumentException(NO_RECORDS_ENCODE_MSG);
        }
        ArrayList<String> encodedData = new ArrayList<String>();
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
                    if (fieldCount < lastField) {
                        line.append(DOUBLE_QUOTE).append(key)
                                .append(DOUBLE_QUOTE)
                                .append(delimiterChar);
                    } else {
                        line.append(DOUBLE_QUOTE).append(key)
                                .append(DOUBLE_QUOTE);
                    }
                    fieldCount++;
                }
                encodedData.add(line.toString());
                line.delete(ZERO, line.length());
            }
            //************* header *************************            
            fieldCount = FIRST; //start w/ first field in record
            //add each field value from the map to the encodedData followed by delimiter
            for (String key : keys) {
                String field = (String) record.get(key);
                if (fieldCount < lastField) {
                    line.append(DOUBLE_QUOTE).append(field)
                            .append(DOUBLE_QUOTE)
                            .append(delimiterChar);
                } else {
                    line.append(DOUBLE_QUOTE).append(field)
                            .append(DOUBLE_QUOTE);
                }
                fieldCount++;
            }
            encodedData.add(line.toString());
            line.delete(ZERO, line.length());
        }
        return (encodedData);
    }

    /**
     * Returns the value for the private field delimiterChar used for encoding.
     *
     * @return the value for the private field delimiterChar
     */
    public final char getDelimiterChar() {
        return delimiterChar;
    }

    /**
     * Returns the value for the private field delimiterStr used for decoding.
     *
     * @return the value for the private field delimiterStr
     */
    public final String getDelimiterStr() {
        return delimiterStr;
    }

    /**
     * Sets the value of the private variables delimiterChar and delimiterStr
     * based on the Enum type passed in. 
     * The character version of the delimiter is used for encoding and the 
     * String version is used for decoding.
     *
     * @param delimiter the Enum type character used for formatting
     */
    public final void setDelimiters(Delimiters delimiter) {
        delimiterChar = delimiter.getValue();
        delimiterStr = DOUBLE_QUOTE + DOUBLE_BACKSLASH 
                + delimiterChar + DOUBLE_QUOTE;
    }

    /**
     * Calculates the hashCode for the class using the delimiterChar and
     * delimiterStr fields.
     *
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.delimiterChar;
        hash = 31 * hash + (this.delimiterStr != null ? this.delimiterStr.hashCode() : 0);
        return hash;
    }

    /**
     * Determines if two CSVPlusQuoteFormatter objects are equal based on the 
     * fields delimiterChar and delimiterStr.
     *
     * @param obj Object of type CSVPlusQuoteFormatter, not null
     * @return true if the objects are equal, false if the objects are unequal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CSVPlusQuoteFormatter other = (CSVPlusQuoteFormatter) obj;
        if (this.delimiterChar != other.delimiterChar) {
            return false;
        }
        if ((this.delimiterStr == null) ? (other.delimiterStr != null) : !this.delimiterStr.equals(other.delimiterStr)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a String object containing the values of all the private fields
     * of the CSVPlusQuoteFormatter class - delimiterChar and delimiterStr.
     *
     * @return a String containing the values of all the private fields of the
     * class
     */
    @Override
    public String toString() {
        return "CSVPlusQuoteFormatter{" + "delimiterChar=" + delimiterChar + ", delimiterStr=" + delimiterStr + '}';
    }

    public static void main(String[] args) {

        List<String> rawData = new ArrayList<String>();
        rawData.add("\"Total Fees\"|\"Total Hours\"" );
        rawData.add("\"21.65\"|\"34.50\"");
        rawData.add("\"44.0\"|\"66.0\"");
//        rawData.add("\"First Name\"#\"Last Name\"#\"Street Address\"#"
//                +"\"City\"#\"State\"#\"Zip\"#\"Email Address\"#\"Phone Nbr\"");
//        rawData.add("\"Malaya\"#\"Science\"#\"1234 Wood Street\"#"
//                +"\"Griffith\"#\"IN\"#\"46309\"#\"giggles@yahoo.com\"#\"464-555-9875\"");
//        rawData.add("\"Laura\"#\"Strejcek\"#\"405 Walker Road\"#"
//                + "\"Normal\"#\"IL\"#\"60621\"#\"myweing@gmail.com\"#\"796-555-6752\"");
        CSVPlusQuoteFormatter csv = new CSVPlusQuoteFormatter(Delimiters.VERTICAL_BAR);
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
