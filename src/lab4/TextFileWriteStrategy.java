package lab4;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Mary
 */
public abstract interface TextFileWriteStrategy {

    public abstract int writeAll
            (List<LinkedHashMap<String, String>> records, boolean hasHeader);

    public abstract int writeOne
            (List<LinkedHashMap<String, String>> records);

    public abstract void setFileName(File fileName) throws 
            IllegalArgumentException, IOException;
}
