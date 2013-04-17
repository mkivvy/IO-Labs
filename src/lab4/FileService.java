package lab4;

import java.util.ArrayList;

/**
 *
 * @author Mary
 */
public class FileService {

    //note FileStrategys include fileName & TextFileFormatStrategy
    public ArrayList<String[]> readAllTextFile(TextFileReadStrategy textFileRead) {
        //validate input
        return (textFileRead.readAll());
    }
    
    public String[] readOneTextFile(TextFileReadStrategy textFileRead, 
            int recordNum) {
        //validate input
        return (textFileRead.readOne(recordNum));
    }
    
    public int writeAllTextFile(TextFileWriteStrategy textFileWrite,
            ArrayList<String[]> records) {
        //validate
        textFileWrite.writeAll(records);
        return 0;
    }

    public int writeOneTextFile(TextFileWriteStrategy textFileWrite,
            String[] recordFields) {
        //validate
        textFileWrite.writeOne(recordFields);
        return 0;
    }

}
