package lab4;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Mary
 */
public abstract interface TextFileReadStrategy {
    
    public abstract List<LinkedHashMap<String, String>> readAll(boolean hasHeader);
    
    public abstract List<LinkedHashMap<String, String>> readOne(int recordNum);
    
    public abstract void setFileName(File fileName);
}
