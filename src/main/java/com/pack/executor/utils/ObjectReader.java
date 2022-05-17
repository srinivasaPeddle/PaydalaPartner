package com.pack.executor.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * The Class ObjectReader.
 */
public class ObjectReader {

    /** The logger. */
	 static Logger logger = Logger.getLogger(ObjectReader.class.getName());

    /** The objects. */
    public static HashMap<String, Object> objects = null;

    static {
        /** The br. */
        BufferedReader br = null;
        if (objects == null) {
            objects = new HashMap<>();
            try {
                String csvSplitBy = AppData.getProperty("csvSplitBy");
                String[] files = AppData.getProperty("ObjectRepository").split(";");

                for (String file : files) {
                    logger.info("Fetching Object properties from " + file + " ...");
                    String line = "";
                    br = new BufferedReader(
                            new FileReader(System.getProperty("user.dir") + "/src/test/resources/" + file));

                    String[] colHeader = br.readLine().split(csvSplitBy);
                    List<Object> lstHeaders = Arrays.asList(colHeader);
                    int index = lstHeaders.indexOf("ELEMENTNAME");
                    while ((line = br.readLine()) != null) {
                        String[] rows = line.split(csvSplitBy);
                        for (int i = index + 1; i < rows.length; i++) {
                            objects.put(
                                    rows[index].trim().toUpperCase().replaceAll(" ", "_") + "_"
                                            + colHeader[i].trim().toUpperCase().replaceAll(" ", "_"),
                                    handleDoubleQuotes(rows[i]));
                        }
                    }
                }

            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
        }
    }

    
    /**
     * Handle double quotes.
     * @param value the value
     * @return the string
     */
    public static String handleDoubleQuotes(String value) {
        value = value.replace("\"\"", "\"").replace("$", ",");
        int index = value.indexOf("\"", 0);
        if (index == 0) {
            value = value.replaceFirst("\"", "");
        }
        index = value.indexOf("\"", value.length() - 1);
        if (index != -1 && index == value.length() - 1) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    /**
     * Gets the property.
     * @param property the property
     * @return the property
     */
    public static String getProperty(String property) {
        try {
            return objects.get(property.toUpperCase().trim().replaceAll(" ", "_").toUpperCase()).toString();
        } catch (Exception e) {
            return "";
        }
    }
}
