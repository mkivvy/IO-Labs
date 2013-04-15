package lab4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Mary
 */
public class ReadWriteLines implements TextFileStrategy {
    private File fileName;
    BufferedReader inputFile = null;
    ArrayList<String[]> fileRecords = new ArrayList<String[]>();
    TextFileFormatStrategy formatter;
    private static final int FIRST_RECORD = 1;

    public ReadWriteLines(File fileName, TextFileFormatStrategy formatter) {
        setFileName(fileName);
        setFormatter(formatter);
    }

    @Override
    public final ArrayList<String[]> readAll() {
        ArrayList<String> fileContents = new ArrayList<String>();
        
        try {
            inputFile = new BufferedReader(new FileReader(fileName));

            String line = inputFile.readLine();
            if (line == null) {
                //throw new IOException(NO_RECORDS_MSG);
            }
            while (line != null) {
                fileContents.add(line);
                line = inputFile.readLine();
            }
        } catch (FileNotFoundException nfe) {
            //System.out.println(FILE_NOT_FOUND_MSG);
        } catch (IOException ioe) {
            //System.out.println(FILE_ERR_MSG);
        } finally {
            closeFile();
        }
        return fileRecords;
    }

    @Override
    public ArrayList<String[]> readOne(int recordNum) {
        if (recordNum < FIRST_RECORD) {
            //System.out.println(NO_RECORD_FOUND_MSG);
        }
        ArrayList<String> fileContents = new ArrayList<String>();
        String line = null;
        try {
            inputFile = new BufferedReader(new FileReader(fileName));
            line = inputFile.readLine();
            if (line == null) {
                //throw new IOException(NO_RECORDS_MSG);
            }
            //if first record was requested, we are done; otherwise
            //loop until we find the requested record
            if (recordNum != FIRST_RECORD) {
                int count = 1;
                while ((line != null) && (count < recordNum)) {
                    line = inputFile.readLine();
                    count++;
                }
            }

        } catch (FileNotFoundException nfe) {
            //System.out.println(FILE_NOT_FOUND_MSG);
        } catch (IOException ioe) {
            //System.out.println(FILE_ERR_MSG);
        } catch (IllegalArgumentException iae) {
            //System.out.println(iae.getMessage());
        } finally {
            closeFile();
        }
        try {
            if (line == null) {
                throw new IOException();
//                throw new IOException(NO_RECORD_FOUND_MSG);
            }
            fileContents.add(line);
            formatRecord(fileContents);
        } catch (IOException ioe) {
            //System.out.println(ioe.getMessage());
        }
        return fileRecords;
    }

    private void formatRecord(ArrayList<String> fileContents) {
        TextFileFormatStrategy formatter = new CSVFormatter();
        for (String s : fileContents) {
            fileRecords.add(formatter.decodeRecord(s));
        }
    }

    @Override
    public int writeAll(ArrayList list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int writeOne(ArrayList list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public final int closeFile() {
        try {
            inputFile.close();
        } catch (Exception e) {
        //throw new Exception(FILE_CLOSE_ERR_MSG);
        }
        return 0;
    }

    @Override
    public final void setFileName(File fileName) {
        //throw new Exception("Not supported yet.");
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
