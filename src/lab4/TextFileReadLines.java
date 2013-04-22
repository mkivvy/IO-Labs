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
 * TextFileReadLines uses the TextFileReadStrategy interface to read records
 * in the specified file.
 * It also contains a TextFileFormatStrategy which is used to decode the
 * records.
 * The decoded set of records are returned to the calling class.
 * <p>
 * The method readAll reads all the records in the specified file, decodes them,
 * and then returns them to the calling class.
 * If the boolean hasHeader indicates the file contains a header record as the
 * first record, the values are used as keys for the List of LinkedHashMaps
 * that is returned.
 * <p>
 * The method readOne reads the records in the specified file until it reaches
 * the record number specified. It then decode this record and returns it to 
 * the calling class as the only map in a List of LinkedHashMaps.
 * This map contains arbitrary values in the key fields in the map as only
 * the one record is handled (not the specified record plus the header record).
 * 
 * @author Mary King, mking@my.wctc.edu
 * @version 1.0
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
    private static final int FIRST_RECORD = 1;
    private static final String SPACE_STR = " ";
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
    
    /**
     * Constructor instantiates the class setting the file name and the
     * TextFileFormatStrategy.
     *
     * @param fileName name of the file to be read, not null, not empty
     * @param formatter name of the TextFileFormatStrategy to be used in 
     * decoding the file records
     * @throws TextFileReadWriteException if file name or formatter is not valid
     */
    public TextFileReadLines(File fileName, TextFileFormatStrategy formatter) 
            throws TextFileReadWriteException {
        try {
            setFileName(fileName);
            setFormatter(formatter);
        } catch (IllegalArgumentException ia) {
            throw new TextFileReadWriteException(ia.getMessage());
        } catch (IOException io) {
            throw new TextFileReadWriteException(io.getMessage());
        }
    }

    /**
     * This method reads all the records in a specified file and stores them in
     * an ArrayList of Strings.  It then passes this List into the TextFile-
     * FormatStrategy's decodeRecords method which converts the List of 
     * Strings into a List of LinkedHashMaps.  Each map in the list contains
     * records containing a value field with the fields values from the file and
     * a key field.   The key field value contains a header field value if a 
     * header record exists, otherwise it contains an arbitrary value.  This
     * List of LinkedHashMaps is returned to the caller. 
     * 
     * @param hasHeader boolean indicating whether the file to be read contains
     * a header record as the first record
     * @return a List of LinkedHashMaps containing a key field and value field 
     * in each record in the map.  If there is no header, integer values 
     * starting at zero are used for the key values.
     * @throws TextFileReadWriteException if no records are retrieved, the
     * file being read contains more than the maximum allowable number of
     * records, the file is not found or there is some other file error.
     */
    @Override
    public final List<LinkedHashMap<String, String>> readAll(boolean hasHeader) 
            throws TextFileReadWriteException {
        
        this.hasHeader = hasHeader;
        ArrayList<String> fileContents = new ArrayList<String>();
        
        try { //this try block does the file processing
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
        } catch (NoRecordException nr) { //Custom - Invalid Parm/IllegalArg
            throw new TextFileReadWriteException(nr.getMessage());
        } catch (LargeFileException lf) { //Custom - Invalid Parm/IllegalArg
            throw new TextFileReadWriteException(lf.getMessage());
        } catch (IllegalArgumentException ia) { //BufferedReader exception
            throw new TextFileReadWriteException(FILE_ERR_MSG + fileName
                    + SPACE_STR + ia.getMessage());
        } catch (FileNotFoundException nf) { //FileReader exception 
            throw new TextFileReadWriteException(FILE_NOT_FOUND_MSG + fileName
                    + SPACE_STR + nf.getMessage());
        } catch (IOException io) { //BufferedReader read exception
            throw new TextFileReadWriteException(FILE_ERR_MSG + fileName
                    + SPACE_STR + io.getMessage());
        } finally {
            try {
                inputFile.close();
            } catch (IOException io) {
                throw new TextFileReadWriteException(FILE_CLOSE_ERR_MSG 
                        + fileName + SPACE_STR + io.getMessage());
            }
        }
        try { //this try block does the post-read processing
            decodedRecords = formatter.decodeRecords(fileContents, hasHeader);
        } catch (NullPointerException np) { 
            throw new TextFileReadWriteException(np.getMessage());
        } catch (IllegalArgumentException ia) { 
            throw new TextFileReadWriteException(ia.getMessage());
        }
        return decodedRecords;
    }

    /**
     * This method reads the specified file until it reads the requested record
     * number.  It stores the values for this record in a String contained
     * in an ArrayList to pass into the TextFileFormatStrategy's decodeRecords 
     * method. It is converted into a LinkedHashMap which is part of a List. 
     * The map contains the record values in the value fields of the map and 
     * an arbitrary value in each key field since a header record is not sent.
     * The List of LinkedHashMaps (with the one map) is returned to the caller. 
     * 
     * @param recordNum the number of the record to be returned to the caller,
     * not less than 1
     * @return a List of LinkedHashMaps containing a key field and value field 
     * for the requested file record.  An arbitrary value is assigned to the 
     * key values since a header record is not being read.
     * @throws TextFileReadWriteException if the requested record number is less
     * than 1, the requested record is not found, the file is not found or there 
     * is some other file error.
     */
    @Override
    public final List<LinkedHashMap<String, String>> readOne(int recordNum) 
            throws TextFileReadWriteException {
        
        if (recordNum < FIRST_RECORD) {
            throw new TextFileReadWriteException(RECORD_NUMBER_MSG);
        }
        hasHeader = false; //only reading 1 record & not including header
        ArrayList<String> fileContents = new ArrayList<String>();
        String line = null;
        
        try { //this try block does the file processing
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

        } catch (NoRecordException nr) { //Custom - Invalid Parm/IllegalArg
            throw new TextFileReadWriteException(nr.getMessage());
        } catch (IllegalArgumentException ia) { //BufferedReader exception
            throw new TextFileReadWriteException(FILE_ERR_MSG + fileName
                    + SPACE_STR + ia.getMessage());
        } catch (FileNotFoundException nf) { //FileReader exception 
            throw new TextFileReadWriteException(FILE_NOT_FOUND_MSG + fileName
                    + SPACE_STR + nf.getMessage());
        } catch (IOException io) { //BufferedReader read exception
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
        
        try { //this try block does the post-read processing
            if (line == null) { //requested record was not found
                throw new NoRecordException(NO_RECORD_FOUND_MSG + fileName);
            }
            fileContents.add(line);
            decodedRecords = formatter.decodeRecords(fileContents, hasHeader);
        } catch (NoRecordException nr) { //Custom - Invalid Parm/IllegalArg
            throw new TextFileReadWriteException(nr.getMessage());
        } catch (NullPointerException np) { 
            throw new TextFileReadWriteException(np.getMessage());
        } catch (IllegalArgumentException ia) { 
            throw new TextFileReadWriteException(ia.getMessage());
        }
        return decodedRecords;
    }

    /**
     * Returns the value of the private variable fileName.
     * 
     * @return the name of the file to be read
     */
    public final File getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the private variable fileName, to be used for reading, 
     * with the name of the file passed as input.
     *
     * @param fileName name of file to be read, not null, not empty 
     * @throws IllegalArgumentException if fileName is null
     * @throws IOException if the file does not exist, is not of type File, or 
     * cannot be read
     */
    @Override
    public final void setFileName(File fileName) throws 
            IllegalArgumentException, IOException {
        if ((fileName == null) || (fileName.length() == ZERO)) {
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

    /**
     * Returns the value of the private variable for the TextFileFormatStrategy 
     * to be used in decoding file data in the previously specified file.
     * 
     * @return the TextFileFormatStrategy to be used in reading file data
     */
    public final TextFileFormatStrategy getFormatter() {
        return formatter;
    }

    /**
     * Sets the value of the private variable for the TextFileFormatStrategy 
     * to be used in decoding file data in the previously specified file.
     *
     * @param formatter the formatting strategy to be used for reading from the
     * previously specified file
     * @throws IllegalArgumentException if the TextFileFormatStrategy is null
     */
    public final void setFormatter(TextFileFormatStrategy formatter) throws
            IllegalArgumentException {
        if (formatter == null) {
            throw new IllegalArgumentException(NO_FORMAT_STRATEGY_MSG);
        }
        this.formatter = formatter;
    }

    /**
     * Calculates the hashCode for the class using the fileName, formatter, 
     * decodedRecords, and hasHeader fields.
     *
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (this.fileName != null ? this.fileName.hashCode() : 0);
        hash = 31 * hash + (this.formatter != null ? this.formatter.hashCode() : 0);
        hash = 31 * hash + (this.decodedRecords != null ? this.decodedRecords.hashCode() : 0);
        hash = 31 * hash + (this.hasHeader ? 1 : 0);
        return hash;
    }

    /**
     * Determines if two TextFileReadLines objects are equal based on the
     * fields fileName, formatter, decodedRecords, and hasHeader.
     *
     * @param obj Object of type TextFileReadLines, not null
     * @return true if the objects are equal, false if the objects are unequal
     */
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
        return true;
    }

    /**
     * Returns a String object containing the values of the private fields
     * of the TextFileReadLines class - fileName, formatter, decodedRecords,
     * and hasHeader.
     *
     * @return a String containing the values of the private fields of the class
     */
    @Override
    public String toString() {
        return "TextFileReadLines{" + "fileName=" + fileName + ", formatter=" + formatter + ", decodedRecords=" + decodedRecords + ", hasHeader=" + hasHeader + '}';
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
