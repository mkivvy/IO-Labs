package lab4;

import java.util.ArrayList;

/**
 *
 * @author Mary
 */
public class FileService {
    
    public ArrayList<String[]> readAllTextFile(TextFileReadStrategy textFile) {
        //validate input
        //note TextFileStrategy includes fileName & TextFileFormatStrategy
        return (textFile.readAll());
    }
    
    public String[] readOneTextFile(TextFileReadStrategy textFile, int recordNum) {
        //validate input
        //note TextFileStrategy includes fileName & TextFileFormatStrategy
        return (textFile.readOne(recordNum));
    }
    
//    public void writeAllRecords (ArrayList records) {
//        
//    }
}
