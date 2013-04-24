package lab4;

import java.io.File;
import java.io.IOException;

/**
 * TextFileWriteStrategy is the interface for writing data to a text file.
 * It expects the input data as a List of LinkedHashMaps and returns the
 * number of records written to the calling object.
 * <p>
 * The method writeAll is used to write all the records in the List of 
 * LinkedHashMaps passed in to a text file.
 * <p>
 * The method writeOne is used to write the one record in the List of 
 * LinkedHashMaps passed in to a text file.
 * 
 * @author Mary King, mking@my.wctc.edu
 * @version 1.0
 */
public abstract interface TextFileWriteStrategy<T> {

    /**
     * This method writes the records passed in to a text file.
     * The type of object passed in is defined by the individual write strategy
     * 
     * @param records is a a type of object defined by the individual strategy 
     * containing record data to be written to a text file, not null, not empty
     * @param hasHeader boolean indicating whether the input data contains
     * header information
     * @return the number of records written to the file, expressed as an int
     * @throws TextFileReadWriteException if input List is null or empty, the 
     * file is not found, or there is some other file write error
     */
    public abstract int writeRecords (T records, boolean hasHeader);
//            (List<LinkedHashMap<String, String>> records, boolean hasHeader);

    /**
     * Sets the value of the file name to be written to with the name of the 
     * file passed as input.
     *
     * @param fileName name of file to be written to, not null, not empty 
     * @throws IllegalArgumentException if fileName is null
     * @throws IOException if the file is not of type File or cannot be 
     * written to 
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
