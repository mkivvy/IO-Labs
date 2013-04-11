package lab1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Mary
 */
public class ContactFileReadWrite {
    private File myFile;
    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    public ContactFileReadWrite(File myFile) {
        this.myFile = myFile;
    }
    
    public void readAllContactFile () {
        ArrayList<String> fileContents = new ArrayList<String>();
        BufferedReader contactFile = null;
        try {
            contactFile = new BufferedReader(new FileReader(myFile));
            
            String line = contactFile.readLine();
            while (line != null) {
                fileContents.add(line);
                line = contactFile.readLine();
            }
        } catch (IOException ioe) {
            System.out.println("Problem reading file");
        } finally {
            try {
                contactFile.close();
            } catch (Exception e) {
                System.out.println("Error closing file");
            }
        }
         populateContacts(fileContents);
    }
    
    private void populateContacts(ArrayList<String> fileContents){
        ContactFileFormatter formatter = new ContactFileFormatter();
        for (String s : fileContents) {
            contacts.add(formatter.decodeContactRecord(s));
        }
    }
    
    public void displayContacts() {
        for (Contact c : contacts) {
        System.out.println(c.getFirstName() + " " + c.getLastName());
        System.out.println(c.getStreetAddr());
        System.out.println(c.getCity() + ", " + c.getState() 
                + " " + c.getZip());
        System.out.println("email: " + c.getEmailAddr());
        System.out.println("phone number: " + c.getPhoneNum());
        System.out.println("");
        }
    }
}
