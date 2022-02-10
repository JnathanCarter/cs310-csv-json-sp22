package edu.jsu.mcis;

import java.io.*;
import java.util.*;

import javax.imageio.stream.MemoryCacheImageOutputStream;

import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {

    /*
     * 
     * Consider the following CSV data:
     * 
     * "ID","Total","Assignment 1","Assignment 2","Exam 1"
     * "111278","611","146","128","337"
     * "111352","867","227","228","412"
     * "111373","461","96","90","275"
     * "111305","835","220","217","398"
     * "111399","898","226","229","443"
     * "111160","454","77","125","252"
     * "111276","579","130","111","338"
     * "111241","973","236","237","500"
     * 
     * The corresponding JSON data would be similar to the following:
     * 
     * {
     * "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
     * "rowHeaders":["111278","111352","111373","111305","111399","111160",
     * "111276","111241"],
     * "data":[[611,146,128,337],
     * [867,227,228,412],
     * [461,96,90,275],
     * [835,220,217,398],
     * [898,226,229,443],
     * [454,77,125,252],
     * [579,130,111,338],
     * [973,236,237,500]
     * ]
     * }
     * 
     * (Tabs and other whitespace have been added here for clarity.) Note the
     * curly braces, square brackets, and double-quotes! These indicate which
     * values should be encoded as strings, and which values should be encoded
     * as integers! The data files which contain this CSV and JSON data are
     * given in the "resources" package of this project.
     * 
     * Your task for this program is to complete the two conversion methods in
     * this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
     * above can be converted to JSON format, and vice-versa. Both methods
     * should return the converted data as strings, but the strings do not need
     * to include the newlines and whitespace shown in the examples; again,
     * this whitespace has been added only for clarity and readability.
     * 
     * NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
     * STRINGS!!! Leave ALL string conversion to the two data conversion
     * libraries we have discussed, OpenCSV and json-simple. See the "Data
     * Exchange" lecture notes for more details, including example code.
     * 
     */

    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {

        String results = "";

        try {
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();

            // INSERT YOUR CODE HERE

            // Add Hashmap and json arrays to hold csv data
            Map myMap = new LinkedHashMap();
            JSONArray columnHeaderArray = new JSONArray();
            JSONArray rowHeaderArray = new JSONArray();
            JSONArray dataArray = new JSONArray();

            // parse csv data into correct arrays
            int counter = 0;
            while (iterator.hasNext()) {
                String[] temp = iterator.next();
                // first row is added to the columnHeaderArray
                if (counter == 0) {
                    for (String element : temp) {
                        columnHeaderArray.add(element);
                    }
                }
                // Every other row is parsed to seperater rowHeader and data
                else {
                    JSONArray rowDataArray = new JSONArray();

                    for (int i = 0; i < temp.length; i++) {

                        // first element of row is the rowHeader
                        if (i == 0) {
                            rowHeaderArray.add(temp[i]);
                        }
                        // ever other element is the rowData
                        else {

                            rowDataArray.add(Integer.valueOf(temp[i]));

                        }

                    }
                    // ever individual row data array is added to the super-set dataArray[]
                    dataArray.add(rowDataArray);
                }
                ++counter;
            }

            // Populating Map Object with data
           
            myMap.put("rowHeaders", rowHeaderArray);
            myMap.put("data", dataArray);
             myMap.put("colHeaders", columnHeaderArray);

            // add to Results
            results = JSONValue.toJSONString(myMap);
            // results = obj.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results.trim();

    }

    public static String jsonToCsv(String jsonString) {

        String results = "";

        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");

            // INSERT YOUR CODE HERE
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);

            JSONArray headerArray = (JSONArray) jsonObject.get("colHeaders");
            JSONArray rowArray = (JSONArray) jsonObject.get("rowHeaders");
            JSONArray dataArray = (JSONArray) jsonObject.get("data");

           String[] tempHeader = new String[5];

            for (int i = 0; i < headerArray.size();i++) {
                tempHeader[i] = (String)headerArray.get(i);
            }

            csvWriter.writeNext(tempHeader);

            for (int i = 0; i < rowArray.size(); i++) {
                JSONArray currentData = (JSONArray) dataArray.get(i);
                String[] currentLineToWrite = new String[5];

                currentLineToWrite[0] =((String) rowArray.get(i));

                for (int j = 0; j < currentData.size(); j++) {
                    currentLineToWrite[j+1] = (Long.toString((long) currentData.get(j)));
                }
                csvWriter.writeNext(currentLineToWrite);
            }
            results = writer.toString();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return results.trim();

    }

}