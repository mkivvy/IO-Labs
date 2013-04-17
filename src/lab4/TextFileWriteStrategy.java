package lab4;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Mary
 */
public abstract interface TextFileWriteStrategy {

    public abstract int writeAll(ArrayList<String[]> records);

    public abstract int writeOne(String[] recordFields);

    public abstract int closeFile();

    public abstract void setFileName(File fileName);
}
