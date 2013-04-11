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
        
        //1. Read all records and output to console
        System.out.println("****Lab1 part 1****");
        ContactFileReadWrite contactRW = new ContactFileReadWrite(myFile);
        contactRW.readAllContactFile();
        contactRW.displayContacts();
        
        //2. Read just the second record and output to the console
        System.out.println("****Lab1 part 2****");
        ContactFileReadWrite contactRW2 = new ContactFileReadWrite(myFile);
        contactRW2.readContactFileRecord(2);    
        contactRW2.displayContacts();
        
        //3. Add a new record by appending the file. Then read all 
        //records back in and output to console to confirm that the 
        //new record can be read.
        System.out.println("****Lab1 part 3****");
        ContactFileReadWrite contactRW3 = new ContactFileReadWrite(myFile);
        Contact friend = new Contact("Deanna","Moore","431 Cortez Court",
            "Naperville","IL","60789","ilovecats@gmail.com","708-555-6688");
        contactRW3.addContactRecord(friend);
        contactRW3.readAllContactFile();
        contactRW3.displayContacts();
    }
}
