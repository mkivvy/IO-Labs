package lab1;

/**
 *
 * @author Mary
 */
public class ContactFileFormatter {
    public static final String FILE_RECORD_DELIM_STR = "\\^";
    public static final int FIRST_NAME_IDX = 0;
    public static final int LAST_NAME_IDX = 1;
    public static final int STREET_ADDR_IDX = 2;
    public static final int CITY_IDX = 3;
    public static final int STATE_IDX = 4;
    public static final int ZIP_IDX = 5;
    public static final int EMAIL_ADDR_IDX = 6;
    public static final int PHONE_NBR_IDX = 7;
    public static final char FILE_RECORD_DELIM_CHAR = '^';
    public static final char NEW_LINE_CHAR = '\n';
    
    public ContactFileFormatter() {
    }
    
    public Contact decodeContactRecord (String contactRecord) {
        String[] fields = contactRecord.split(FILE_RECORD_DELIM_STR);
        Contact contact = new Contact();
        contact.setFirstName(fields[FIRST_NAME_IDX]);
        contact.setLastName(fields[LAST_NAME_IDX]);
        contact.setStreetAddr(fields[STREET_ADDR_IDX]);
        contact.setCity(fields[CITY_IDX]);
        contact.setState(fields[STATE_IDX]);
        contact.setZip(fields[ZIP_IDX]);
        contact.setEmailAddr(fields[EMAIL_ADDR_IDX]);
        contact.setPhoneNum(fields[PHONE_NBR_IDX]);
        return contact;
    }
    
    public String encodeContactRecord (Contact contactRecord) {
        String s = NEW_LINE_CHAR
                + contactRecord.getFirstName() + FILE_RECORD_DELIM_CHAR 
                + contactRecord.getLastName() + FILE_RECORD_DELIM_CHAR
                + contactRecord.getStreetAddr() + FILE_RECORD_DELIM_CHAR
                + contactRecord.getCity() + FILE_RECORD_DELIM_CHAR
                + contactRecord.getState() + FILE_RECORD_DELIM_CHAR
                + contactRecord.getZip() + FILE_RECORD_DELIM_CHAR
                + contactRecord.getEmailAddr() + FILE_RECORD_DELIM_CHAR
                + contactRecord.getPhoneNum();
        return s;
    }
}
