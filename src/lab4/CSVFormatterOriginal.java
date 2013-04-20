package lab4;

/**
 *
 * @author Mary
 */
public class CSVFormatterOriginal {
    private char delimiterChar;
    private String delimiterStr;
    public static final String NEW_LINE_STR = "\n";
    public static final String DOUBLE_BACKSLASH = "\\";
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int FIRST = 1;

    public CSVFormatterOriginal(Delimiters delimiter) {
        setDelimiters(delimiter);
    }

//    public final String[] decodeRecord(String record) {
//        String[] fields = record.split(delimiterStr);
//        return fields;
//    }

//    public final String encodeRecord(String[] fields) {
//        StringBuilder strBldRecord = new StringBuilder(NEW_LINE_STR);
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
        }
}
