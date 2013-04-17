package lab4;

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
public class TextFileWriteLines implements TextFileWriteStrategy {
    
    private File fileName;
    private BufferedReader inputFile = null;
    private TextFileFormatStrategy formatter = new CSVFormatter('^');
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
    public int writeAll(ArrayList<String[]> records) {
        //if file exists, append; otherwise it is created
        boolean append = true;
        PrintWriter out = null;
        try {
            out = new PrintWriter(
                    new BufferedWriter(new FileWriter(fileName, append)));
            String encodedRecord;
            for (String[] sa : records) {
                encodedRecord = formatter.encodeRecord(sa);
                out.printf(encodedRecord);
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
        return 0;
    }

    @Override
    public int writeOne(String[] recordFields) {
        //if file exists, append; otherwise it is created
        boolean append = true;
        PrintWriter out = null;
        try {
            out = new PrintWriter(
                    new BufferedWriter(new FileWriter(fileName, append)));
            String encodedRecord = formatter.encodeRecord(recordFields);
            out.printf(encodedRecord);
        } catch (FileNotFoundException nfe) { //PrintWriter exception 
            errorMsg = FILE_NOT_FOUND_MSG + fileName;
        } catch (SecurityException se) { //PrintWriter exception 
            errorMsg = FILE_NOT_FOUND_MSG + fileName;
        } catch (IOException ioe) { //FileWriter, BufferedWriter exception
            errorMsg = FILE_ERR_MSG + fileName;
        } finally {
            out.close();
        }
        return 0;
    }

    @Override
    public final int closeFile() {
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
}
