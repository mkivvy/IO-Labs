package lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Mary
 */
public class TextFileReadWrite {
    public static void main(String[] args) {
        
        File myFile = new File(File.separatorChar + "Users" 
                + File.separatorChar + "Mary"
                + File.separatorChar + "Documents"
                + File.separatorChar + "AdvJavaCourse"
                + File.separatorChar + "ContactList.txt");
        
        BufferedReader contacts = null;
        try {
            contacts = new BufferedReader(new FileReader(myFile));
            String line = contacts.readLine();
            String[] lineFields;
            while (line != null) {
                lineFields = line.split("\\^");
                //for (String s: lineFields) {
//                for (int i = 0; i < lineFields.length; i++) {
//                    System.out.println(lineFields[i]);
//                }
                displayFormatedContacts(lineFields);
                line = contacts.readLine();
            }
        } catch (IOException ioe) {
            System.out.println("Problem reading file - lab 1");
        } finally {
            try {
                contacts.close();
            } catch (Exception e) {
                System.out.println("Error closing file - lab 1");
            }
        }
        
        //This time, we just want to see the second record in the file
        try {
            contacts = new BufferedReader(new FileReader(myFile));
            String line = contacts.readLine();
            line = contacts.readLine();
            String[] lineFields = line.split("\\^");
            System.out.println("**********Second Record **************");
            displayFormatedContacts(lineFields);
        } catch (IOException ioe) {
            System.out.println("Problem reading file - lab 2");
        } finally {
            try {
                contacts.close();
            } catch (Exception e) {
                System.out.println("Error closing file - lab 2");
            }
        }
        
        //This last time, we are adding a record to the file & outputing  all
        //records in the file
         boolean append = true;
         PrintWriter out = null;
        try {
            out = new PrintWriter(
                    new BufferedWriter(new FileWriter(myFile, append)));
            out.printf("Deanna^Moore^431 Cortez Court^Naperville^IL^60789^"
                    + "Ilovecats@gmail.com^708-555-6688");
        } catch (IOException ioe) {
            System.out.println("Problem reading file - lab 3a");
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                System.out.println("Error closing file - lab 3a");
            }
        }
        try {
            contacts = new BufferedReader(new FileReader(myFile));
            String line = contacts.readLine();
            String[] lineFields;
            while (line != null) {
                lineFields = line.split("\\^");
                displayFormatedContacts(lineFields);
                line = contacts.readLine();
            }
        } catch (IOException ioe) {
            System.out.println("Problem reading file - lab 3b");
        } finally {
            try {
                contacts.close();
            } catch (Exception e) {
                System.out.println("Error closing file - lab 3b");
            }
        }
        
    }

    public static void displayFormatedContacts (String [] data) {
        int firstNameIdx = 0;
        int lastNameIdx = 1;
        int streetAddrIdx = 2;
        int cityIdx = 3;
        int stateIdx = 4;
        int zipIdx = 5;
        int emailAddrIdx = 6;
        int phoneNumIdx = 7;
        
        System.out.println(data[firstNameIdx] + " " + data[lastNameIdx]);
        System.out.println(data[streetAddrIdx]);
        System.out.println(data[cityIdx] + ", " + data[stateIdx] 
                + " " + data[zipIdx]);
        System.out.println("email: " + data[emailAddrIdx]);
        System.out.println("phone number: " + data[phoneNumIdx]);
        System.out.println("");
    }
    
}
