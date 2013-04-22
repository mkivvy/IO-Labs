package lab4;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

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
public abstract interface TextFileWriteStrategy {

    /**
     * This method writes all the records passed in as a List of LinkedHashMaps
     * to a text file.
     * 
     * @param records is a List of LinkedHashMaps containing record data to be
     * written to a text file, not null, not empty
     * @param hasHeader boolean indicating whether the input data contains
     * header information
     * @return the number of records written to the file, expressed as an int
     * @throws TextFileReadWriteException if input List is null or empty, the 
     * file is not found, or there is some other file write error
     */
    public abstract int writeAll
            (List<LinkedHashMap<String, String>> records, boolean hasHeader);

    /**
     * This method writes the one record passed in as a List of LinkedHashMaps
     * to a text file.
     * 
     * @param records is a List of LinkedHashMaps containing record data to be
     * written to a text file, not null, not empty
     * @return the number of records written to the file, expressed as an int
     * @throws TextFileReadWriteException if input List is null or empty, the 
     * file is not found, or there is some other file write error
     */
    public abstract int writeOne
            (List<LinkedHashMap<String, String>> records);

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
}
