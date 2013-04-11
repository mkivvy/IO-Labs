package lab3;

import java.io.File;
import lab1.ContactFileReadWrite;

/**
 *
 * @author Mary
 */
public class StartupLab3 {
    public static void main(String[] args) {
        File myFile = new File(File.separatorChar + "Users" 
                + File.separatorChar + "Mary"
                + File.separatorChar + "Documents"
                + File.separatorChar + "AdvJavaCourse"
                + File.separatorChar + "ContactList.txt");
        
        //Find and display the city only in the second record in your three 
        //record text file.
        System.out.println("****Lab3****");
        ContactFileReadWrite contactRW = new ContactFileReadWrite(myFile);
        contactRW.readContactFileRecord(2);    
        contactRW.displayContactsCity();
        
    }
}
