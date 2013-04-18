package lab4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Mary
 */
public class TextFileReadLines implements TextFileReadStrategy {
    
    private File fileName;
    private BufferedReader inputFile = null;
    private ArrayList<String[]> fileRecords = new ArrayList<String[]>();
    private TextFileFormatStrategy formatter = new CSVPlusFormatter('^');
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

    public TextFileReadLines(File fileName, TextFileFormatStrategy formatter) {
        setFileName(fileName);
        setFormatter(formatter);
    }

    @Override
    public final ArrayList<String[]> readAll() {
        ArrayList<String> fileContents = new ArrayList<String>();
        try {
            inputFile = new BufferedReader(new FileReader(fileName));

            String line = inputFile.readLine();
            if (line == null) {
                throw new NoRecordException(NO_RECORDS_MSG + fileName);
            }
            while (line != null) {
                fileContents.add(line);
                line = inputFile.readLine();
            }
        } catch (NoRecordException nre) { //Custom - Invalid Parm/IllegalArg
            errorMsg = nre.getMessage();
        } catch (IllegalArgumentException iae) { //BufferedReader exception
            errorMsg = FILE_ERR_MSG + fileName;
        } catch (FileNotFoundException nfe) { //FileReader exception 
            errorMsg = FILE_NOT_FOUND_MSG + fileName;
        } catch (IOException ioe) { //BufferedReader read exception
            errorMsg = FILE_ERR_MSG + fileName;
        } finally {
            closeFile();
        }
        formatRecords(fileContents);
        return fileRecords;
    }

    @Override
    public final String[] readOne(int recordNum) {
        if (recordNum < FIRST_RECORD) {
            throw new IllegalArgumentException(RECORD_NUMBER_MSG);
        }
        ArrayList<String> fileContents = new ArrayList<String>();
        String line = null;
        try {
            inputFile = new BufferedReader(new FileReader(fileName));
            line = inputFile.readLine();
            if (line == null) {
                throw new NoRecordException(NO_RECORDS_MSG + fileName);
            }
            //if first record was requested, we are done; otherwise
            //loop until we find the requested record
            if (recordNum != FIRST_RECORD) {
                int count = 1;
                while ((line != null) && (count < recordNum)) {
                    line = inputFile.readLine();
                    count++;
                }
            }

        } catch (NoRecordException nre) { //Custom - Invalid Parm/IllegalArg
            errorMsg = nre.getMessage();
        } catch (IllegalArgumentException iae) { //BufferedReader exception
            errorMsg = FILE_ERR_MSG + fileName;
        } catch (FileNotFoundException nfe) { //FileReader exception 
            errorMsg = FILE_NOT_FOUND_MSG + fileName;
        } catch (IOException ioe) { //BufferedReader read exception
            errorMsg = FILE_ERR_MSG + fileName;
        } finally {
            closeFile();
        }
        try {
            if (line == null) {
                throw new NoRecordException(NO_RECORD_FOUND_MSG + fileName);
            }
        } catch (NoRecordException nre) { //Custom - Invalid Parm/IllegalArg
            errorMsg = nre.getMessage();
        }
        return formatter.decodeRecord(line);
    }

    private void formatRecords(ArrayList<String> fileContents) {
        for (String s : fileContents) {
            fileRecords.add(formatter.decodeRecord(s));
        }
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
            if (!(fileName.exists())) {
                throw new IOException();
            }
            if (!(fileName.isFile())){
                throw new IOException();
            }
            if (!(fileName.canRead())){
                throw new IOException();
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
        //throw new Exception("Not supported yet.");
        this.formatter = formatter;
    }
}
