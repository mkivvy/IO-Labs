package lab4;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * TextFileReadStrategy is the interface for reading data from a text file
 * and returning it to the calling object as a List of LinkedHashMaps.
 * <p>
 * The method readAll is used to read all the records in a text file and 
 * return them to the caller.
 * <p>
 * The method readOne is used to read a specified record in a text file and
 * return that record to the caller.
 * 
 * @author Mary King, mking@my.wctc.edu
 * @version 1.0
 */
public abstract interface TextFileReadStrategy {
    
    /**
     * This method reads all the records in a specified file and returns them to
     * the caller as a List of LinkedHashMaps. 
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
    public abstract List<LinkedHashMap<String, String>> 
            readAll(boolean hasHeader) throws TextFileReadWriteException;
    
    /**
     * This method reads the specified file for the record requested by number.
     * It returns the the values for this record in a LinkedHashMap as part of 
     * a List. 
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
    public abstract List<LinkedHashMap<String, String>> 
            readOne(int recordNum) throws TextFileReadWriteException;
    
    /**
     * Sets the value of the file name to be read with the name of the file 
     * passed as input.
     *
     * @param fileName name of file to be read, not null, not empty 
     * @throws IllegalArgumentException if fileName is null
     * @throws IOException if the file does not exist, is not of type File, or 
     * cannot be read
     */
    public abstract void setFileName(File fileName) throws 
            IllegalArgumentException, IOException;
}
