package lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Mary
 */
public class ContactFileReadWrite {

    private static final String NO_RECORDS_MSG = "No records to read.";
    private static final String FILE_NOT_FOUND_MSG = "File not found";
    private static final String FILE_ERR_MSG = "Problem reading file";
    private static final String FILE_CLOSE_ERR_MSG = "Error closing file";
    private static final int FIRST_RECORD = 1;
    private File myFile;
    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    public ContactFileReadWrite(File myFile) {
        this.myFile = myFile;
        if (!myFile.exists()) {
            System.out.println(FILE_NOT_FOUND_MSG);
        }
    }

    public void readAllContactFile() {
        ArrayList<String> fileContents = new ArrayList<String>();
        BufferedReader contactFile = null;
        try {
            contactFile = new BufferedReader(new FileReader(myFile));

            String line = contactFile.readLine();
            if (line == null) {
                throw new IOException(NO_RECORDS_MSG);
            }
            while (line != null) {
                fileContents.add(line);
                line = contactFile.readLine();
            }
        } catch (FileNotFoundException nfe) {
            System.out.println(FILE_NOT_FOUND_MSG);
        } catch (IOException ioe) {
            System.out.println(FILE_ERR_MSG);
        } finally {
            try {
                contactFile.close();
            } catch (Exception e) {
                System.out.println(FILE_CLOSE_ERR_MSG);
            }
        }
        populateContacts(fileContents);
    }

    public void readContactFileRecord(int recordNum) {
        if (recordNum < FIRST_RECORD) {
            throw new IllegalArgumentException(NO_RECORDS_MSG);
        }
        ArrayList<String> fileContents = new ArrayList<String>();
        BufferedReader contactFile = null;
        String line = null;
        try {
            contactFile = new BufferedReader(new FileReader(myFile));
            line = contactFile.readLine();
            if (line == null) {
                throw new IOException(NO_RECORDS_MSG);
            }
            //if first record was requested, we are done; otherwise
            //loop until we find the requested record
            if (recordNum != FIRST_RECORD) {
                int count = 1;
                while ((line != null) && (count < recordNum)) {
                    line = contactFile.readLine();
                    count++;
                }
            }

        } catch (FileNotFoundException nfe) {
            System.out.println(FILE_NOT_FOUND_MSG);
        } catch (IOException ioe) {
            System.out.println(FILE_ERR_MSG);
        } finally {
            try {
                contactFile.close();
            } catch (Exception e) {
                System.out.println(FILE_CLOSE_ERR_MSG);
            }
        }
        fileContents.add(line);
        populateContacts(fileContents);
    }

    private void populateContacts(ArrayList<String> fileContents) {
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

    public void displayRecord(int recordNum) {
        if (recordNum < FIRST_RECORD) {
            throw new IllegalArgumentException(NO_RECORDS_MSG);
        }
        recordNum--; //offset is 1 less than record number
        Contact c = contacts.get(recordNum);

        System.out.println(c.getFirstName() + " " + c.getLastName());
        System.out.println(c.getStreetAddr());
        System.out.println(c.getCity() + ", " + c.getState()
                + " " + c.getZip());
        System.out.println("email: " + c.getEmailAddr());
        System.out.println("phone number: " + c.getPhoneNum());
        System.out.println("");
    }

    public void addContactRecord(Contact contact) {
        ContactFileFormatter formatter = new ContactFileFormatter();
        String contactStr = formatter.encodeContactRecord(contact);
        
        boolean append = true;
        PrintWriter out = null;
        try {
            out = new PrintWriter(
                    new BufferedWriter(new FileWriter(myFile, append)));
            out.printf(contactStr);
        } catch (IOException ioe) {
            System.out.println(FILE_ERR_MSG);
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                System.out.println(FILE_CLOSE_ERR_MSG);
            }
        }

    }
}
