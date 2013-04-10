package lab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Lab2File {

    public static void main(String[] args) {

        File myFile = new File(File.separatorChar + "Users"
                + File.separatorChar + "Mary"
                + File.separatorChar + "Documents"
                + File.separatorChar + "AdvJavaCourse"
                + File.separatorChar + "ContactList.txt");

        int recordToDisplay = 2;
        BufferedReader contacts = null;
        try {
            contacts = new BufferedReader(new FileReader(myFile));
            String line = null;
            for (int i = 1; i <= recordToDisplay; i++) {
                line = contacts.readLine();
            }
            String[] lineFields = line.split("\\^");
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


    }

    public static void displayFormatedContacts(String[] data) {
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
