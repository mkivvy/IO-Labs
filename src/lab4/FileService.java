package lab4;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * FileService provides access to reading and writing files.
 * Currently, it supports reading and writing of one or all records in text 
 * files but can be modified in the future to include binary files reads and
 * writes.
 * <p>
 * The method readAllTextFile uses the TextFileReadStrategy specified to read
 * all the records in a text file and returns the values for the records in 
 * a List of LinkedHashMaps.
 * <p>
 * The method readOneTextFile uses the TextFileReadStrategy specified to read
 * and return the requested record in a text file.
 * The returned record is formatted as LinkedHashMap and returned as part of a 
 * List.
 * <p>
 * The method writeAllTextFile uses the TextFileWriteStrategy specified to write
 * all the records passed in as a List of LinkedHashMaps to a text file.
 * <p>
 * The method writeOneTextFile uses the TextFileWriteStrategy specified to write
 * the one record passed in as a List of LinkedHashMaps to a text file.
 * 
 * @author Mary King, mking@my.wctc.edu
 * @version 1.0
 */
public class FileService {
    private static final int FIRST_RECORD = 1;
    private static final String NO_READ_STRATEGY_MSG = "No text file read "
            + "strategy was specified.  A valid text file read is required.";
    private static final String RECORD_NUMBER_MSG = 
            "Record number must be more than 0";
    private static final String NO_WRITE_STRATEGY_MSG = "No text file write "
            + "strategy was specified.  A valid text file write is required.";
    private static final String NO_RECORDS_TO_WRITE_MSG = 
            "The input contains no records to be written.";
    
    /**
     * This method reads all the records in a text file and returns the values 
     * for the records in a List of LinkedHashMaps.
     * 
     * @param textFileRead name of the TextFileReadStrategy to be used in 
     * reading the records from the file
     * @param hasHeader boolean indicating whether the file contains a header 
     * record
     * @return a List of LinkedHashMaps containing a key field and value field 
     * for the requested file record.  An arbitrary value is assigned to the 
     * key values if hasHeader is false.
     * @throws IllegalArgumentException if the TextFileReadStrategy is null 
     * @throws TextFileReadWriteException if there is an exception during the
     * running of the TextFileReadStrategy's readAll method
     */
    public final List<LinkedHashMap<String, String>> readAllTextFile
            (TextFileReadStrategy textFileRead, boolean hasHeader) 
            throws IllegalArgumentException, TextFileReadWriteException {
        if (textFileRead == null) {
            throw new IllegalArgumentException(NO_READ_STRATEGY_MSG);
        }
        try {
            List<LinkedHashMap<String, String>> readAllMap
                    = (List<LinkedHashMap<String, String>>) 
                    textFileRead.readAll(hasHeader);
            return (readAllMap);
        } catch (TextFileReadWriteException tfr) {
            throw new TextFileReadWriteException(tfr.getMessage());
        }
//        return (textFileRead.readAll(hasHeader));
    }
    
    /**
     * This method reads the text file for the record requested by number.
     * It returns the the values for this record in a LinkedHashMap as part of 
     * a List. 
     * 
     * @param textFileRead name of the TextFileReadStrategy to be used in 
     * reading the records from the file
     * @param recordNum the number of the record to be returned to the caller,
     * not less than 1
     * @return a List of LinkedHashMaps containing a key field and value field 
     * for the requested file record.  An arbitrary value is assigned to the 
     * key values since a header record is not being read.
     * @throws IllegalArgumentException if the TextFileReadStrategy is null or
     * the input recordNum is less than 1
     * @throws TextFileReadWriteException if there is an exception during the
     * running of the TextFileReadStrategy's readOne method
     */
    public final List<LinkedHashMap<String, String>> readOneTextFile
            (TextFileReadStrategy textFileRead, int recordNum) 
            throws IllegalArgumentException, TextFileReadWriteException {
        if (textFileRead == null) {
            throw new IllegalArgumentException(NO_READ_STRATEGY_MSG);
        }
        if (recordNum < FIRST_RECORD) {
            throw new IllegalArgumentException(RECORD_NUMBER_MSG);
        }
        try {
            List<LinkedHashMap<String, String>> readOneMap
                    = (List<LinkedHashMap<String, String>>) 
                    textFileRead.readOne(recordNum);
            return (readOneMap);
        } catch (TextFileReadWriteException tfr) {
            throw new TextFileReadWriteException(tfr.getMessage());
        }
//        return (textFileRead.readOne(recordNum));
    }
    
    /**
     * This method writes all the records passed in as a List of LinkedHashMaps
     * to a text file using the TextFileWriteStrategy specified.
     * 
     * @param textFileWrite name of the TextFileWriteStrategy to be used in 
     * writing the records to the file
     * @param records is a List of LinkedHashMaps containing record data to be
     * written to a text file, not null, not empty
     * @param hasHeader boolean indicating whether the input data contains
     * header information
     * @throws IllegalArgumentException if input List is null or empty or the 
     * TextFileWriteStrategy is null
     * @throws TextFileReadWriteException if there is an exception during the
     * running of the TextFileWriteStrategy's writeAll method
     */
    public final void writeTextFile(TextFileWriteStrategy textFileWrite,
            List<LinkedHashMap<String, String>> records, boolean hasHeader)  
            throws IllegalArgumentException, TextFileReadWriteException {
        if (textFileWrite == null) {
            throw new IllegalArgumentException(NO_WRITE_STRATEGY_MSG);
        }
        if ((records == null) || (records.isEmpty())) {
            throw new IllegalArgumentException(NO_RECORDS_TO_WRITE_MSG);
        }
        try {
            textFileWrite.writeRecords(records, hasHeader);
        } catch (TextFileReadWriteException tfw) {
            throw new TextFileReadWriteException(tfw.getMessage());
        }
//        return(textFileWrite.writeAll(records, hasHeader));
    }

}
