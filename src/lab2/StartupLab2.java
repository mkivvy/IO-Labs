package lab2;

import java.io.File;
import lab1.Contact;
import lab1.ContactFileReadWrite;

/**
 *
 * @author Mary
 */
public class StartupLab2 {
    public static void main(String[] args) {
        File myFile = new File(File.separatorChar + "Users" 
                + File.separatorChar + "Mary"
                + File.separatorChar + "Documents"
                + File.separatorChar + "AdvJavaCourse"
                + File.separatorChar + "ContactList.txt");
        
        //Find the second record in your three record text file and make it 
        //flexible so that you can change the record number that you are 
        //searching for. Then display that record by itself at the console.
        System.out.println("****Lab2****");
        ContactFileReadWrite contactRW = new ContactFileReadWrite(myFile);
        contactRW.readContactFileRecord(2);    
        contactRW.displayContacts();
        
    }
    
}
