package lab4;

/**
 *
 * @author Mary
 */
public class CharacterDelimitedFormatter {
    public static final char NEW_LINE_CHAR = '\n';

    public CharacterDelimitedFormatter() {
    }

    public final String[] decodeRecord (String record, String delimiterStr) {
        String[] fields = record.split(delimiterStr);
        return fields;
    }

    public final String encodeRecord(String[] fields, char delimiterChar) {
        String recordString = "" + NEW_LINE_CHAR;
        int lastField = fields.length - 1;
        for (int i = 0; i < fields.length; i++) {
            recordString = recordString + fields[i];
            if (i < lastField) { //don't want delimiter after last field
                recordString = recordString + delimiterChar;
            }
        }
        return recordString;
    }

    public static void main(String[] args) {
        CharacterDelimitedFormatter df = new CharacterDelimitedFormatter();
        String[] testStr = {"Deanna","Moore","431 Cortez Court",
            "Naperville","IL","60789","ilovecats@gmail.com","708-555-6688"};
        String out = df.encodeRecord(testStr, '^');
        System.out.println("endoded record: " + out);
        String[] out2 = df.decodeRecord("Ariana^Dancer^122 Spruce Lane^"
                + "Glenwood^IL^60425^noticeme@gmail.com^708-555-1232", "\\^");
        for (String s : out2) {
            System.out.println(s);
        }
    }
}
