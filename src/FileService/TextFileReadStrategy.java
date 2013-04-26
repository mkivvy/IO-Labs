package FileService;

import java.io.File;
import java.io.IOException;

/**
 * TextFileReadStrategy is the interface for reading data from a text file and
 * returning it to the calling object as a type defined by the individual
 * strategy.
 * <p>
 * The method readAll is used to read all the records in a text file and return
 * them to the caller.
 * <p>
 * The method readOne is used to read a specified record in a text file and
 * return that record to the caller.
 *
 * @author Mary King, mking@my.wctc.edu
 * @version 1.0
 */
public abstract interface TextFileReadStrategy<T> {

    /**
     * This method reads all the records in a specified file and returns them to
     * the caller as a type defined by the individual strategy.
     *
     * @param hasHeader boolean indicating whether the file to be read contains
     * a header record as the first record
     * @return a type of object defined by the individual strategy
     * @throws TextFileReadWriteException if no records are retrieved, the file
     * being read contains more than the maximum allowable number of records,
     * the file is not found or there is some other file error.
     */
    public abstract T readAll(boolean hasHeader)
            throws TextFileReadWriteException;

    /**
     * This method reads the specified file for the record requested by number.
     * It returns the the values for this record as a type defined by the
     * individual strategy.
     *
     * @param recordNum the number of the record to be returned to the caller,
     * not less than 1
     * @return a type of object defined by the individual strategy
     * @throws TextFileReadWriteException if the requested record number is less
     * than 1, the requested record is not found, the file is not found or there
     * is some other file error.
     */
    public abstract T readOne(int recordNum)
            throws TextFileReadWriteException;

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

    /**
     * Returns the value of the private variable fileName.
     *
     * @return the name of the file to be read
     */
    public abstract File getFileName();

    /**
     * Sets the value of the private variable for the TextFileFormatStrategy 
     * to be used in decoding file data in the previously specified file.
     *
     * @param formatter the formatting strategy to be used for reading from the
     * previously specified file, not null
     * @throws IllegalArgumentException if the TextFileFormatStrategy is null
     */
    public abstract void setFormatter(TextFileFormatStrategy formatter) throws
            IllegalArgumentException;

    /**
     * Returns the value of the private variable for the TextFileFormatStrategy 
     * to be used in decoding file data in the previously specified file.
     * 
     * @return the TextFileFormatStrategy to be used in reading file data
     */
    public abstract TextFileFormatStrategy getFormatter();
}
