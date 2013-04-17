package lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Mary
 */
public class TextFileWriteLines implements TextFileWriteStrategy {
    
    private File fileName;
    private BufferedReader inputFile = null;
    private ArrayList<String[]> fileRecords = new ArrayList<String[]>();
    private TextFileFormatStrategy formatter = new CSVFormatter('^');
    public static final int ZERO = 0;
    private String errorMsg;
    private static final int FIRST_RECORD = 1;
    private static final String NO_RECORDS_MSG = "No records to read in file ";
    private static final String RECORD_NUMBER_MSG =
            "Record number must be more than 0";
    private static final String NO_RECORD_FOUND_MSG = "Record not found in file ";
    private static final String FILE_NOT_FOUND_MSG = "File not found: ";
    private static final String FILE_ERR_MSG = "Cannot read file ";
    private static final String FILE_CLOSE_ERR_MSG = "Error closing file ";

    public TextFileWriteLines(File fileName, TextFileFormatStrategy formatter) {
        setFileName(fileName);
        setFormatter(formatter);
    }

    @Override
    public int writeAll(ArrayList<String[]> records) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int writeOne(String[] records) {
        //if file exists, append; otherwise create
        boolean append = true;
        PrintWriter out = null;
        try {
            out = new PrintWriter(
                    new BufferedWriter(new FileWriter(fileName, append)));
//            out.printf(contactStr);
        } catch (IOException ioe) {
            System.out.println(FILE_ERR_MSG);
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                System.out.println(FILE_CLOSE_ERR_MSG);
            }
        }
        return 0;
    }

    @Override
    public final int closeFile() {
        try {
            inputFile.close();
        } catch (IOException e) {
            errorMsg = FILE_CLOSE_ERR_MSG + fileName;
        }
        return 0;
    }

    @Override
    public final void setFileName(File fileName) {
        try {
            if (!(fileName.exists())) {
                throw new IOException();
            }
            if (!(fileName.isFile())){
                throw new IOException();
            }
            if (!(fileName.canRead())){
                throw new IOException();
            }
        } catch (IOException e) {
            errorMsg = "File " + fileName + " does not exist.";
        }

        this.fileName = fileName;
    }

    public final TextFileFormatStrategy getFormatter() {
        return formatter;
    }

    public final void setFormatter(TextFileFormatStrategy formatter) {
        //throw new Exception("Not supported yet.");
        this.formatter = formatter;
    }
}
