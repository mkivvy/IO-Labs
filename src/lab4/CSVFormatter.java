package lab4;

/**
 *
 * @author Mary
 */
public class CSVFormatter implements TextFileFormatStrategy{
    private String delimiterStr = ",";
    private char delimiterChar = ',';
    public static final char NEW_LINE_CHAR = '\n';
    
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
    
}
