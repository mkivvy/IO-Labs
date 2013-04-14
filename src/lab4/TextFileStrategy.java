package lab4;

import java.util.ArrayList;

/**
 *
 * @author Mary
 */
public abstract class TextFileStrategy {
    
    public abstract ArrayList readAll();
    
    public abstract ArrayList readOne();
    
    public abstract int writeAll(ArrayList list);
    
    public abstract int writeOne(ArrayList list);
    
    public abstract int closeFile();
    
    public abstract void setFileName(String fileName);
}
