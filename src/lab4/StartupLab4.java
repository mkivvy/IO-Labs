package lab4;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Mary
 */
public class StartupLab4 {
    public static void main(String[] args) {
        //declare the file name
        File myFile = new File(File.separatorChar + "Users" 
            + File.separatorChar + "Mary"
            + File.separatorChar + "Documents"
            + File.separatorChar + "AdvJavaCourse"
            + File.separatorChar + "ContactList.txt");
        
        //instantiate the FileService
        FileService myFileService = new FileService();
        //instantiate converter for easier viewing & writing preparation
        ListAndLinkedHashMapConverter converter = 
                new ListAndLinkedHashMapConverter();
        
//************TEST READING *******************************************        
        //instantiate the TextFileReader and TextFileFormatter
        TextFileReadLines myReader = new TextFileReadLines(myFile, 
                new CSVPlusFormatter(Delimiters.POUND_SIGN));
        //define the linkedhashmap returned by the fileservice for read all
        List<LinkedHashMap<String, String>> allMap = 
                myFileService.readAllTextFile(myReader, true); 
        System.out.println("all records: ");
        for (LinkedHashMap record : allMap) {
            System.out.println(record);
        }
        List<String> allList = converter.convertMapToList
                (allMap, Delimiters.TAB, true);
        displayData(allList);

        //define the linkedhashmap returned by the fileservice for read one
        List<LinkedHashMap<String, String>> oneMap = 
                myFileService.readOneTextFile(myReader, 3);  
        List<String> oneList = converter.convertMapToList
                (oneMap, Delimiters.TAB, false);
        System.out.println("Record 3: ");
        for (LinkedHashMap record : oneMap) {
            System.out.println(record);
        }
        displayData(oneList);
        
////************TEST WRITING ******************************************* 
//        //instantiate the TextFileWriter and TextFileFormatter
//        TextFileWriteLines myWriter = new TextFileWriteLines(myFile,
//               new CSVPlusFormatter(Delimiters.PERCENT_SIGN));
//        //define the arraylist for writing to file
//        List<String> myData = new ArrayList<String>();
////        myData.add("First Name%Last Name%Street Address%City%State%Zip%Email Address%Phone Nbr");
////        myData.add("Ariana%Dancer%122 Spruce Lane%Glenwood%IL%60425%noticeme@gmail.com%708-555-1232");
////        myData.add("Malaya%Science%1234 Wood Street%Griffith%IN%46309%giggles@yahoo.com%464-555-9875");
//        myData.add("Nimbus%King%N74 W24450 Red Tail Court%Sussex%WI%53089%walkme@gmail.com%262-555-0317");
//        myData.add("Hobbes%King%N74 W24450 Red Tail Court%Sussex%WI%53089%purrpurr@gmail.com%262-555-0701");
//        myData.add("Laura%Strejcek%405 Walker Road%Normal%IL%60621%myweing@gmail.com%796-555-6752");
//        List<LinkedHashMap<String, String>> myMap = prepareDataForWrite(myData, false, "%");
//        int a = myFileService.writeAllTextFile(myWriter, myMap, false);
//        System.out.println("records written = " + a);
//        
//        List<String> myData2 = new ArrayList<String>();
//        myData2.add("Deanna%Moore%431 Cortez Court%Naperville%IL%60789%ilovecats@gmail.com%708-555-6688");
//        List<LinkedHashMap<String, String>> myMap2 = prepareDataForWrite(myData2, false, "%");
//        int b = myFileService.writeOneTextFile(myWriter, myMap2);
//        System.out.println("records written = " + b);
    }
    
    public static void displayData (List<String> data){ 
        for (String s : data) {
            System.out.println(s);
        }
    }
    
    public static List<LinkedHashMap<String, String>> prepareDataForWrite 
            (List<String> data, boolean hasHeader, String delimiterStr) {
        List<LinkedHashMap<String, String>> recordMap = null;
        int lineCount = 0;
        String[] header = null;

        //loop through the list of strings passed in
        for (String s : data) {
            lineCount++;
            //split the line into a string array using the specified delimiter
            String[] splitData = s.split(delimiterStr);
            if (hasHeader && (lineCount == 1)) {
                header = splitData; //this is the header row, set header values
            }

            //create the linkedhashmap to contain record data
            LinkedHashMap<String, String> record =
                    new LinkedHashMap<String, String>();
            //loop through each field from the split line 
            for (int i = 0; i < splitData.length; i++) {
                if (hasHeader && lineCount == 1) {
                    continue; //already took care of header row above
                } else if (hasHeader) {
                    //populate String1 w/header field name & String2 w/
                    //matching data
                    record.put(header[i], splitData[i]);
                } else { //no header
                    //populate String1 w/field number to keep key unique
                    //& String2 w/data
                    record.put("" + i, splitData[i]);
                }
            } //finished 2nd for loop & have completed record
            if (hasHeader && lineCount == 1) {
                //do nothing - don't need a header row keyed w/header fields
            } else {
                recordMap.add(record);
            }
        } //finished 1st for loop & processed all input data - whew!!
        return recordMap;
    }
}
