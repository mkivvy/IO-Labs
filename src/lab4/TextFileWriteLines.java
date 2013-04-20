package lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Mary
 */
public class TextFileWriteLines implements TextFileWriteStrategy {
    
    private File fileName;
    private BufferedReader inputFile = null;
    private TextFileFormatStrategy formatter;
    private boolean hasHeader = false;
    private boolean fileExists = false;
    public static final int ZERO = 0;
    private String errorMsg;
    private static final int FIRST_RECORD = 1;
    private static final String NO_RECORDS_MSG = "No records to read in file ";
    private static final String RECORD_NUMBER_MSG =
            "Record number must be more than 0";
    private static final String NO_RECORD_FOUND_MSG = "Record not found in file ";
    private static final String FILE_NOT_FOUND_MSG = "File not found: ";
    private static final String FILE_ERR_MSG = "Cannot read file ";
    private static final String FILE_CLOSE_ERR_MSG = "Error closing file ";

    public TextFileWriteLines(File fileName, TextFileFormatStrategy formatter) {
        setFileName(fileName);
        setFormatter(formatter);
    }

    @Override
    public int writeAll(List<LinkedHashMap<String, String>> records,
            boolean hasHeader) {
        List<String> encodedRecords = new ArrayList<String>();
        //if file exists, append; otherwise it is created
        boolean append = true;
        PrintWriter out = null;
        int recordsWritten = 0;
        try {
            out = new PrintWriter(
                    new BufferedWriter(new FileWriter(fileName, append)));
            encodedRecords = formatter.encodeRecords(records, hasHeader);
            for (String s : encodedRecords) {
                out.println(s);
                recordsWritten++;
            }
        } catch (FileNotFoundException nfe) { //PrintWriter exception 
            errorMsg = FILE_NOT_FOUND_MSG + fileName;
        } catch (SecurityException se) { //PrintWriter exception 
            errorMsg = FILE_NOT_FOUND_MSG + fileName;
        } catch (IOException ioe) { //FileWriter, BufferedWriter exception
            errorMsg = FILE_ERR_MSG + fileName;
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                System.out.println(FILE_CLOSE_ERR_MSG);
            }
        }
        return recordsWritten;
    }

    @Override
    public int writeOne(List<LinkedHashMap<String, String>> records) {
        List<String> encodedRecords = new ArrayList<String>();
        hasHeader = false; //only writing one record, not header + record
        //if file exists, append; otherwise it is created
        boolean append = true;
        PrintWriter out = null;
        int recordsWritten = 0;
        try {
            out = new PrintWriter(
                    new BufferedWriter(new FileWriter(fileName, append)));
            encodedRecords = formatter.encodeRecords(records, hasHeader);
            for (String s : encodedRecords) {
                out.println(s);
                recordsWritten++;
            }
        } catch (FileNotFoundException nfe) { //PrintWriter exception 
            errorMsg = FILE_NOT_FOUND_MSG + fileName;
        } catch (SecurityException se) { //PrintWriter exception 
            errorMsg = FILE_NOT_FOUND_MSG + fileName;
        } catch (IOException ioe) { //FileWriter, BufferedWriter exception
            errorMsg = FILE_ERR_MSG + fileName;
        } finally {
            out.close();
        }
        return recordsWritten;
    }

    private int closeFile() {
        try {
            inputFile.close();
        } catch (IOException e) {
            errorMsg = FILE_CLOSE_ERR_MSG + fileName;
        }
        return 0;
    }

    @Override
    public final void setFileName(File fileName) {
        try {
            if (fileName.exists()) {
                fileExists = true;
                if (!(fileName.isFile())){
                    throw new IOException();
                }
                if (!(fileName.canWrite())){
                    throw new IOException();
                }
            }
        } catch (IOException e) {
            errorMsg = "File " + fileName + " does not exist.";
        }

        this.fileName = fileName;
    }

    public final TextFileFormatStrategy getFormatter() {
        return formatter;
    }

    public final void setFormatter(TextFileFormatStrategy formatter) {
        //validate
        this.formatter = formatter;
    }
    public static void main(String[] args) {
        File myFile = new File(File.separatorChar + "Users" 
            + File.separatorChar + "Mary"
            + File.separatorChar + "Documents"
            + File.separatorChar + "AdvJavaCourse"
            + File.separatorChar + "ContactList.txt");
        
        List<String> rawData = new ArrayList<String>();
//        rawData.add("Total Fees,Total Hours" );
//        rawData.add("21.65,34.50");
//        rawData.add("44.0,66.0");
        rawData.add("First Name#Last Name#Street Address#City#State#Zip#Email Address#Phone Nbr");
        rawData.add("Ariana#Dancer#122 Spruce Lane#Glenwood#IL#60425#noticeme@gmail.com#708-555-1232");
        rawData.add("Malaya#Science#1234 Wood Street#Griffith#IN#46309#giggles@yahoo.com#464-555-9875");
        rawData.add("Nimbus#King#N74 W24450 Red Tail Court#Sussex#WI#53089#walkme@gmail.com#262-555-0317");
        rawData.add("Hobbes#King#N74 W24450 Red Tail Court#Sussex#WI#53089#purrpurr@gmail.com#262-555-0701");
        rawData.add("Laura#Strejcek#405 Walker Road#Normal#IL#60621#myweing@gmail.com#796-555-6752");
        rawData.add("Deanna#Moore#431 Cortez Court#Naperville#IL#60789#ilovecats@gmail.com#708-555-6688");
        CSVPlusFormatter csv = new CSVPlusFormatter(Delimiters.POUND_SIGN);
        List<LinkedHashMap<String, String>> myMap =
                csv.decodeRecords(rawData, true);
        for (LinkedHashMap record : myMap) {
            System.out.println(record);
        }
        TextFileWriteLines myWriter = new TextFileWriteLines(myFile, csv);
        int i = myWriter.writeAll(myMap, true);
        System.out.println(i + " records written");
    }
}
