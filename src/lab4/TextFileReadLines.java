package lab4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Mary
 */
public class TextFileReadLines implements TextFileReadStrategy {
    
    private File fileName;
    private BufferedReader inputFile = null;
    private TextFileFormatStrategy formatter;
    private List<LinkedHashMap<String, String>> decodedRecords =
                new ArrayList<LinkedHashMap<String, String>>();   
    private boolean hasHeader = false;
    public static final int ZERO = 0;
    public static final int MAX_RECORDS = 500;
    private String errorMsg;
    private static final int FIRST_RECORD = 1;
    private static final String LARGE_FILE_MSG = 
            "Cannot read all records.  " 
            + "File contains more than the maximum number of records allowed: " 
            + MAX_RECORDS + ".  File: ";
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
    public final List<LinkedHashMap<String, String>> readAll(boolean hasHeader) {
        this.hasHeader = hasHeader;
        ArrayList<String> fileContents = new ArrayList<String>();
        try {
            inputFile = new BufferedReader(new FileReader(fileName));
            int lineCount = ZERO;
            String line = inputFile.readLine();
            if (line == null) {
                throw new NoRecordException(NO_RECORDS_MSG + fileName);
            }
            while ((line != null) && (lineCount < MAX_RECORDS)){
                fileContents.add(line);
                line = inputFile.readLine();
                lineCount++;
            }
            if (lineCount == MAX_RECORDS) {
                throw new NoRecordException(LARGE_FILE_MSG + fileName);
                //errorMsg = FILE_ERR_MSG + fileName; //need catch for this
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
        decodedRecords = formatter.decodeRecords(fileContents, hasHeader);
        return decodedRecords;
    }

    @Override
    public final List<LinkedHashMap<String, String>> readOne(int recordNum) {
        hasHeader = false; //only reading 1 record
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
        fileContents.add(line);
        decodedRecords = formatter.decodeRecords(fileContents, hasHeader);
        return decodedRecords;
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
    
    public static void main(String[] args) {
        File myFile = new File(File.separatorChar + "Users" 
            + File.separatorChar + "Mary"
            + File.separatorChar + "Documents"
            + File.separatorChar + "AdvJavaCourse"
            + File.separatorChar + "ContactList.txt");
        
        TextFileReadLines myReader = new TextFileReadLines(myFile, 
                new CSVPlusFormatter(Delimiters.POUND_SIGN));
        List<LinkedHashMap<String, String>> recordMap = myReader.readAll(true);
//        List<LinkedHashMap<String, String>> recordMap = myReader.readOne(3);
//                new ArrayList<LinkedHashMap<String, String>>();        
        Set<String> keys = null;
        int recordCount = 0;
        int fieldCount = 1;
        StringBuilder line = new StringBuilder("\n");
        for (LinkedHashMap record : recordMap) {
            recordCount++;
            keys = record.keySet();
            int lastField = keys.size();
//************* header *************************            
            if (recordCount == 1) {
                for (String key : keys) {
                    line.append(key);
                    if (fieldCount < lastField) {
                        line.append('\t');
                    }//don't want delimiter after last field
                    fieldCount++;
                }
                System.out.println(line.toString());
                //reset the contents of line
                line.delete(ZERO, line.length());
            }
//************* header *************************            
            fieldCount = 1; //start w/ first field in record
            for (String key : keys) {
                String field = (String) record.get(key);
                line.append(field);
                if (fieldCount < lastField) {
                    line.append('\t');
                    }//don't want delimiter after last field
                fieldCount++;
            }
            System.out.println(line.toString());
            line.delete(ZERO, line.length());
        }
    }
}
