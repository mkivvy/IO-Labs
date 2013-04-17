package lab4;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Mary
 */
public abstract interface TextFileReadStrategy {
    
    public abstract ArrayList<String[]> readAll();
    
    public abstract String[] readOne(int recordNum);
    
    public abstract int closeFile();
    
    public abstract void setFileName(File fileName);
}
