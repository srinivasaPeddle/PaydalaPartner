package com.pack.executor.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.logging.Logger;
import static com.pack.executor.stepdefs.StepDefs.clientWithEndPoint;

import org.apache.commons.lang.StringUtils;

/**
 * The Class TestDataReader.
 */
public class TestDataReader {

	/** The logger. */
	static Logger logger = Logger.getLogger(TestDataReader.class.getName());

	public static String dataSource;

	/** The data. */
	public static HashMap<String, Object> data = null;

	static {
		/** The br. */
		BufferedReader br = null;
		if (data == null) {
			data = new HashMap<>();
			try {
				String csvSplitBy = AppData.getProperty("csvSplitBy");
				String[] files = AppData.getProperty("testData").split(";");

				for (String file : files) {
					logger.info("Fetching the data from " + file + " ...");
					String line = "";
					br = new BufferedReader(
							new FileReader(System.getProperty("user.dir") + "/src/test/resources/" + file));
					String[] colHeader = br.readLine().split(csvSplitBy);

					while ((line = br.readLine()) != null) {
						String[] rows = line.split(csvSplitBy);
						for (int i = 1; i < rows.length; i++) {
							data.put(rows[0].trim().toUpperCase().replaceAll(" ", "_") + "_"
									+ colHeader[i].trim().toUpperCase().replaceAll(" ", "_"), rows[i]);
						}
					}
				}
			} catch (Exception e) {
				logger.warning(e.getMessage());
			}
		}
	}

	public static String getProperty(String property) {
		String testData = "";
		try {
			testData = data.get(property.toUpperCase().trim().replaceAll(" ", "_").toUpperCase()).toString();
		} catch (Exception e) {
			testData = "";
		}
		if (StringUtils.isEmpty(testData)) {
			try {
//					testData = db.getPropertyFromDB(property, 1);
			} catch (Exception e) {
				testData = property;
			}
		}
		return testData;
	}

	public static String getTestData(String endPoint, String property) {
		String testData = "";
		if (StringUtils.isEmpty(testData)) {
			try {
//				testData = DatabaseUtil.getTestData(endPoint, property);
			} catch (Exception e) {
				testData = property;
			}
		}
		return testData;
	}
}
