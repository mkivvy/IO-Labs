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
    public static final int ZERO = 0;
    private static final String SPACE_STR = " ";
    private static final String NO_FILE_NAME_MSG = 
            "File name is empty.  A valid file name is required.";
    private static final String NOT_FILE_TYPE_MSG = " is not of type File ";
    private static final String NOT_WRITABLE_MSG = " cannot be written to.";
    private static final String NO_FORMAT_STRATEGY_MSG = "No format strategy "
            + "was specified.  A valid format strategy is required.";
    private static final String NO_RECORDS_TO_WRITE_MSG = 
            "The input contains no records to be written.";
    private static final String FILE_NOT_FOUND_MSG = "File not found: ";
    private static final String FILE_ERR_MSG = "Cannot read file ";
    private static final String FILE_CLOSE_ERR_MSG = "Error closing file ";

    public TextFileWriteLines(File fileName, TextFileFormatStrategy formatter) 
            throws TextFileReadWriteException {
        try {
            setFileName(fileName);
            setFormatter(formatter);
        } catch (IllegalArgumentException ia) {
            throw new TextFileReadWriteException(ia.getMessage());
        } catch (IOException e) {
            throw new TextFileReadWriteException(e.getMessage());
        }
    }

    @Override
    public int writeAll(List<LinkedHashMap<String, String>> records,
            boolean hasHeader) throws TextFileReadWriteException {
        if (records == null) {
            throw new NullPointerException(NO_RECORDS_TO_WRITE_MSG);
        }
        if (records.isEmpty()) {
            throw new IllegalArgumentException(NO_RECORDS_TO_WRITE_MSG);
        }

        List<String> encodedRecords = new ArrayList<String>();
        //if file exists, append; otherwise it is created
        boolean append = true;
        PrintWriter out = null;
        int recordsWritten = 0;
        try {
            out = new PrintWriter(
                    new BufferedWriter(new FileWriter(fileName, append)));
            encodedRecords = formatter.encodeRecords(records, hasHeader);
            //formatter throws NullPointerException, IllegalArgumentException
            for (String s : encodedRecords) {
                out.println(s);
                recordsWritten++;
            }
        } catch (FileNotFoundException nf) { //PrintWriter exception 
            throw new TextFileReadWriteException(FILE_NOT_FOUND_MSG + fileName
                    + SPACE_STR + nf.getMessage());
        } catch (NullPointerException np) { 
            throw new TextFileReadWriteException(np.getMessage());
        } catch (IllegalArgumentException ia) { 
            throw new TextFileReadWriteException(ia.getMessage());
        } catch (SecurityException se) { //PrintWriter exception 
            throw new TextFileReadWriteException(FILE_NOT_FOUND_MSG + fileName
                    + SPACE_STR + se.getMessage());
        } catch (IOException io) { //FileWriter, BufferedWriter exception
            throw new TextFileReadWriteException(FILE_ERR_MSG + fileName
                    + SPACE_STR + io.getMessage());
        } finally {
            try {
                inputFile.close();
            } catch (IOException io) {
                throw new TextFileReadWriteException(FILE_CLOSE_ERR_MSG 
                        + fileName + io.getMessage());
            }
        }
        return recordsWritten;
    }

    @Override
    public int writeOne(List<LinkedHashMap<String, String>> records) 
            throws TextFileReadWriteException {
        if (records == null) {
            throw new NullPointerException(NO_RECORDS_TO_WRITE_MSG);
        }
        if (records.isEmpty()) {
            throw new IllegalArgumentException(NO_RECORDS_TO_WRITE_MSG);
        }

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
        } catch (FileNotFoundException nf) { //PrintWriter exception 
            throw new TextFileReadWriteException(FILE_NOT_FOUND_MSG + fileName
                    + SPACE_STR + nf.getMessage());
        } catch (NullPointerException np) { 
            throw new TextFileReadWriteException(np.getMessage());
        } catch (IllegalArgumentException ia) { 
            throw new TextFileReadWriteException(ia.getMessage());
        } catch (SecurityException se) { //PrintWriter exception 
            throw new TextFileReadWriteException(FILE_NOT_FOUND_MSG + fileName
                    + SPACE_STR + se.getMessage());
        } catch (IOException io) { //FileWriter, BufferedWriter exception
            throw new TextFileReadWriteException(FILE_ERR_MSG + fileName
                    + SPACE_STR + io.getMessage());
        } finally {
            try {
                inputFile.close();
            } catch (IOException io) {
                throw new TextFileReadWriteException(FILE_CLOSE_ERR_MSG 
                        + fileName + io.getMessage());
            }
        }
        return recordsWritten;
    }

    @Override
    public final void setFileName(File fileName)  
            throws IllegalArgumentException, IOException{
        if (fileName == null) {
            throw new IllegalArgumentException(NO_FILE_NAME_MSG);
        }
        if (fileName.exists()) {
            if (!(fileName.isFile())){
                throw new IOException(fileName + NOT_FILE_TYPE_MSG);
            }
            if (!(fileName.canWrite())){
                throw new IOException(fileName + NOT_WRITABLE_MSG);
            }
        }
        this.fileName = fileName;
    }

    public final TextFileFormatStrategy getFormatter() {
        return formatter;
    }

    public final void setFormatter(TextFileFormatStrategy formatter)  
            throws IllegalArgumentException {
        if (formatter == null) {
            throw new IllegalArgumentException(NO_FORMAT_STRATEGY_MSG);
        }
        this.formatter = formatter;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.fileName != null ? this.fileName.hashCode() : 0);
        hash = 41 * hash + (this.formatter != null ? this.formatter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TextFileWriteLines other = (TextFileWriteLines) obj;
        if (this.fileName != other.fileName && (this.fileName == null || !this.fileName.equals(other.fileName))) {
            return false;
        }
        if (this.formatter != other.formatter && (this.formatter == null || !this.formatter.equals(other.formatter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TextFileWriteLines{" + "fileName=" + fileName + ", formatter=" + formatter + ", hasHeader=" + hasHeader + '}';
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
