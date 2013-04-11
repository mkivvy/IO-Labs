package lab1;

import java.io.File;

/**
 *
 * @author Mary
 */
public class Startup {
    public static void main(String[] args) {
        File myFile = new File(File.separatorChar + "Users" 
                + File.separatorChar + "Mary"
                + File.separatorChar + "Documents"
                + File.separatorChar + "AdvJavaCourse"
                + File.separatorChar + "ContactList.txt");
        
        ContactFileReadWrite contactRW = new ContactFileReadWrite(myFile);
        contactRW.readAllContactFile();
        contactRW.displayContacts();
    }
}
