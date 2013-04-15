package lab4;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Mary
 */
public abstract interface TextFileStrategy {
    
    public abstract ArrayList readAll();
    
    public abstract ArrayList readOne(int recordNum);
    
    public abstract int writeAll(ArrayList list);
    
    public abstract int writeOne(ArrayList list);
    
    public abstract int closeFile();
    
    public abstract void setFileName(File fileName);
}
