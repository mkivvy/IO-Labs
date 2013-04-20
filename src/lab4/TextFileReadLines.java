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
    private static final String NO_FILE_NAME_MSG = 
            "File name is empty.  A valid file name is required.";
    private static final String FILE_NOT_EXIST_MSG = " does not exist.";
    private static final String NOT_FILE_TYPE_MSG = " is not of type File ";
    private static final String NOT_READABLE_MSG = " cannot be read.";
    private static final String NO_FORMAT_STRATEGY_MSG = "No format strategy "
            + "was specified.  A valid format strategy is required.";
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
        try {
            setFileName(fileName);
            setFormatter(formatter);
        } catch (IllegalArgumentException ia) {
            errorMsg = ia.getMessage();
        } catch (IOException e) {
            errorMsg = e.getMessage();
        }
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
                throw new LargeFileException(LARGE_FILE_MSG + fileName);
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
        
        if (recordNum < FIRST_RECORD) {
            throw new IllegalArgumentException(RECORD_NUMBER_MSG);
        }
        hasHeader = false; //only reading 1 record & not including header
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
    public final void setFileName(File fileName) throws 
            IllegalArgumentException, IOException {
        if (fileName == null) {
            throw new IllegalArgumentException(NO_FILE_NAME_MSG);
        }
        if (!(fileName.exists())) {
            throw new IOException(fileName + FILE_NOT_EXIST_MSG);
        }
        if (!(fileName.isFile())){
            throw new IOException(fileName + NOT_FILE_TYPE_MSG);
        }
        if (!(fileName.canRead())){
            throw new IOException(fileName + NOT_READABLE_MSG);
        }

        this.fileName = fileName;
    }

    public final TextFileFormatStrategy getFormatter() {
        return formatter;
    }

    public final void setFormatter(TextFileFormatStrategy formatter) throws
            IllegalArgumentException {
        if (formatter == null) {
            throw new IllegalArgumentException(NO_FORMAT_STRATEGY_MSG);
        }
        this.formatter = formatter;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (this.fileName != null ? this.fileName.hashCode() : 0);
        hash = 31 * hash + (this.formatter != null ? this.formatter.hashCode() : 0);
        hash = 31 * hash + (this.decodedRecords != null ? this.decodedRecords.hashCode() : 0);
        hash = 31 * hash + (this.hasHeader ? 1 : 0);
        hash = 31 * hash + (this.errorMsg != null ? this.errorMsg.hashCode() : 0);
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
        final TextFileReadLines other = (TextFileReadLines) obj;
        if (this.fileName != other.fileName && (this.fileName == null || !this.fileName.equals(other.fileName))) {
            return false;
        }
        if (this.formatter != other.formatter && (this.formatter == null || !this.formatter.equals(other.formatter))) {
            return false;
        }
        if (this.decodedRecords != other.decodedRecords && (this.decodedRecords == null || !this.decodedRecords.equals(other.decodedRecords))) {
            return false;
        }
        if (this.hasHeader != other.hasHeader) {
            return false;
        }
        if ((this.errorMsg == null) ? (other.errorMsg != null) : !this.errorMsg.equals(other.errorMsg)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TextFileReadLines{" + "fileName=" + fileName + ", formatter=" + formatter + ", decodedRecords=" + decodedRecords + ", hasHeader=" + hasHeader + ", errorMsg=" + errorMsg + '}';
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
