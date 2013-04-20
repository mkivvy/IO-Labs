package lab4;

import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Mary
 */
public class FileService {
    private static final int FIRST_RECORD = 1;
    private static final String NO_READ_STRATEGY_MSG = "No text file read "
            + "strategy was specified.  A valid text file read is required.";
    private static final String RECORD_NUMBER_MSG = 
            "Record number must be more than 0";

    public List<LinkedHashMap<String, String>> readAllTextFile
            (TextFileReadStrategy textFileRead, boolean hasHeader) 
            throws IllegalArgumentException, TextFileReadException {
        if (textFileRead == null) {
            throw new IllegalArgumentException(NO_READ_STRATEGY_MSG);
        }
        try {
            List<LinkedHashMap<String, String>> readAllMap
                    = textFileRead.readAll(hasHeader);
            return (readAllMap);
        } catch (TextFileReadException tfr) {
            throw new TextFileReadException(tfr.getMessage());
        }
//        return (textFileRead.readAll(hasHeader));
    }
    
    public List<LinkedHashMap<String, String>> readOneTextFile
            (TextFileReadStrategy textFileRead, int recordNum) 
            throws IllegalArgumentException, TextFileReadException {
        if (textFileRead == null) {
            throw new IllegalArgumentException(NO_READ_STRATEGY_MSG);
        }
        if (recordNum < FIRST_RECORD) {
            throw new IllegalArgumentException(RECORD_NUMBER_MSG);
        }
        try {
            List<LinkedHashMap<String, String>> readOneMap
                    = textFileRead.readOne(recordNum);
            return (readOneMap);
        } catch (TextFileReadException tfr) {
            throw new TextFileReadException(tfr.getMessage());
        }
//        return (textFileRead.readOne(recordNum));
    }
    
    public int writeAllTextFile(TextFileWriteStrategy textFileWrite,
            List<LinkedHashMap<String, String>> records, boolean hasHeader) {
        //validate
        return(textFileWrite.writeAll(records, hasHeader));
    }

    public int writeOneTextFile(TextFileWriteStrategy textFileWrite,
            List<LinkedHashMap<String, String>> records) {
        //validate
        return(textFileWrite.writeOne(records));
    }

}
