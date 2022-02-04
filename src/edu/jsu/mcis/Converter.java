package edu.jsu.mcis;

import java.io.*;
import java.util.*;
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

        // Add json object and json arrays to hold csv data
        JSONObject obj = new JSONObject();
        JSONArray columnHeaderArray = new JSONArray();
        JSONArray rowHeaderArray = new JSONArray();
        JSONArray dataArray = new JSONArray();

        try {

            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();

            // INSERT YOUR CODE HERE

            // parse csv data into correct arrays
            int counter = 0;
            while (iterator.hasNext()) {
                String[] temp = iterator.next();
                if (counter == 0) {
                    for (String element : temp) {
                        columnHeaderArray.add(element);
                    }
                } else {
                    for (int i = 0; i < temp.length; i++) {
                        JSONArray tempDataArray = new JSONArray();

                        if (i == 0) {
                            rowHeaderArray.add(temp[i]);
                        } else {

                            tempDataArray.add(Integer.valueOf(temp[i]));

                        }
                        dataArray.add(tempDataArray);
                    }
                }

            }

            // Populating JSONObject with data
            obj.put("colHeaders", columnHeaderArray);
            obj.put("rowHeaders", rowHeaderArray);
            obj.put("data", dataArray);

            // add to Results
            results = obj.toString().trim();

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

        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return results.trim();

    }

}