package lab4;

/**
 *
 * @author Mary
 */
public class Formatter2 {
    private char delimiterChar;
    private String delimiterStr;
    public static final char NEW_LINE_CHAR = '\n';
    public static final String DOUBLE_BACKSLASH = "\\";
    public static final int ZERO = 0;
    public static final int ONE = 1;

    public Formatter2(DelimiterNames2 delimiter) {
        setDelimiters(delimiter);
    }
   
    public final String[] decodeRecord(String record) {
    String[] fields = record.split(delimiterStr);
    return fields;
    }
    
    public final String encodeRecord(String[] fields) {
        StringBuilder strBldRecord = new StringBuilder("" + NEW_LINE_CHAR);
        int lastField = fields.length - ONE; //last field is 1 less than length
        for (int i = ZERO; i < fields.length; i++) {
            strBldRecord.append(fields[i]);
            if (i < lastField) { //don't want delimiter after last field
                strBldRecord.append(delimiterChar);
            }
        }
        return strBldRecord.toString();
    }

    public final void setDelimiters(DelimiterNames2 delimiter) {
        delimiterChar = delimiter.getValue();
        delimiterStr = DOUBLE_BACKSLASH + delimiterChar;
    }

    public static void main(String[] args) {
        Formatter2 csv = new Formatter2(DelimiterNames2.TAB);
        String[] testStr = {"Deanna", "Moore", "431 Cortez Court",
            "Naperville", "IL", "60789", "ilovecats@gmail.com", "708-555-6688"};
        String out = csv.encodeRecord(testStr);
        System.out.println("\nendoded record: " + out);
        String[] out2 = csv.decodeRecord("Ariana\tDancer\t122 Spruce Lane\t"
                + "Glenwood IL\t60425\tnoticeme@gmail.com\t708-555-1232");
        for (String s : out2) {
            System.out.println(s);
        }
    }
}
