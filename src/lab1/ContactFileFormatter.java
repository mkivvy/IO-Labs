package lab1;

/**
 *
 * @author Mary
 */
public class ContactFileFormatter {
    public static final int FIRST_NAME_IDX = 0;
    public static final int LAST_NAME_IDX = 1;
    public static final int STREET_ADDR_IDX = 2;
    public static final int CITY_IDX = 3;
    public static final int STATE_IDX = 4;
    public static final int ZIP_IDX = 5;
    public static final int EMAIL_ADDR_IDX = 6;
    public static final int PHONE_NBR_IDX = 7;
    
    public ContactFileFormatter() {
    }
    
    public Contact decodeContactRecord (String contactRecord) {
        String[] fields = contactRecord.split("\\^");
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
}
