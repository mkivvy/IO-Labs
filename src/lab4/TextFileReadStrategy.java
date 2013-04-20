package lab4;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Mary
 */
public abstract interface TextFileReadStrategy {
    
    public abstract List<LinkedHashMap<String, String>> 
            readAll(boolean hasHeader) throws TextFileReadException;
    
    public abstract List<LinkedHashMap<String, String>> 
            readOne(int recordNum) throws TextFileReadException;
    
    public abstract void setFileName(File fileName) throws 
            IllegalArgumentException, IOException;
}
