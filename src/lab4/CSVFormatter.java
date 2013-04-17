package lab4;

/**
 *
 * @author Mary
 */
public class CSVFormatter implements TextFileFormatStrategy {

    private char delimiterChar;
    private String delimiterStr;
    public static final char NEW_LINE_CHAR = '\n';
    public static final String DOUBLE_BACKSLASH = "\\";
    public static final int ZERO = 0;
    public static final int ONE = 1;

    public CSVFormatter(char delimiterChar) {
        setDelimiterChar(delimiterChar);
    }

    @Override
    public final String[] decodeRecord(String record) {
        String[] fields = record.split(delimiterStr);
        return fields;
    }

    @Override
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

    public final char getDelimiterChar() {
        return delimiterChar;
    }

    public final void setDelimiterChar(char delimiterChar) {
        //validation
        this.delimiterChar = delimiterChar;
        setDelimiterStr(delimiterChar);
    }

    public final String getDelimiterStr() {
        return delimiterStr;
    }

    private void setDelimiterStr(char delimiterChar) {
        //delimiterChar does not require validation here as it is a private
        //method called only from setDelimiterChar where the char is
        //already validated
        this.delimiterStr = DOUBLE_BACKSLASH + delimiterChar;
    }

    public static void main(String[] args) {
        CSVFormatter csv = new CSVFormatter('^');
        String[] testStr = {"Deanna", "Moore", "431 Cortez Court",
            "Naperville", "IL", "60789", "ilovecats@gmail.com", "708-555-6688"};
        String out = csv.encodeRecord(testStr);
        System.out.println("endoded record: " + out);
        String[] out2 = csv.decodeRecord("Ariana^Dancer^122 Spruce Lane^"
                + "Glenwood^IL^60425^noticeme@gmail.com^708-555-1232");
        for (String s : out2) {
            System.out.println(s);
        }

    }
}
