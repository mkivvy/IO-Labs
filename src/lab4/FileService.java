package lab4;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Mary
 */
public class FileService {

    
    //note FileStrategys include fileName & TextFileFormatStrategy
    public List<LinkedHashMap<String, String>> readAllTextFile
            (TextFileReadStrategy textFileRead, boolean hasHeader) {
        //validate input
        return (textFileRead.readAll(hasHeader));
    }
    
    public List<LinkedHashMap<String, String>> readOneTextFile
            (TextFileReadStrategy textFileRead, int recordNum) {
        //validate input
        return (textFileRead.readOne(recordNum));
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
