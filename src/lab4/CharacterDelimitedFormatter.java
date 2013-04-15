package lab4;

/**
 *
 * @author Mary
 */
public class CharacterDelimitedFormatter implements TextFileFormatStrategy {
    private String delimiterStr = ",";
    private char delimiterChar = ',';
    public static final char NEW_LINE_CHAR = '\n';

    public CharacterDelimitedFormatter() {
    }

    public CharacterDelimitedFormatter(String delimiterStr, char delimiterChar) {
        setDelimiterStr(delimiterStr);
        setDelimiterChar(delimiterChar);
    }

    @Override
    public final String[] decodeRecord (String record) {
        String[] fields = record.split(delimiterStr);
        return fields;
    }

    @Override
    public final String encodeRecord(String[] fields) {
        StringBuilder strBldRecord = new StringBuilder("" + NEW_LINE_CHAR);
        int lastField = fields.length - 1;
        for (int i = 0; i < fields.length; i++) {
            strBldRecord.append(fields[i]);
            if (i < lastField) { //don't want delimiter after last field
                strBldRecord.append(delimiterChar);
            }
        }
        return strBldRecord.toString();
    }

    public final String getDelimiterStr() {
        return delimiterStr;
    }

    public final void setDelimiterStr(String delimiterStr) {
        this.delimiterStr = delimiterStr;
    }

    public final char getDelimiterChar() {
        return delimiterChar;
    }

    public final void setDelimiterChar(char delimiterChar) {
        this.delimiterChar = delimiterChar;
    }

    public static void main(String[] args) {
        CharacterDelimitedFormatter df = new CharacterDelimitedFormatter("\\^", '^');
        String[] testStr = {"Deanna","Moore","431 Cortez Court",
            "Naperville","IL","60789","ilovecats@gmail.com","708-555-6688"};
        String out = df.encodeRecord(testStr);
        System.out.println("endoded record: " + out);
        String[] out2 = df.decodeRecord("Ariana^Dancer^122 Spruce Lane^"
                + "Glenwood^IL^60425^noticeme@gmail.com^708-555-1232");
        for (String s : out2) {
            System.out.println(s);
        }
    }
}
