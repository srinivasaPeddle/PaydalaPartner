package com.pack.executor.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.pack.executor.libraries.Library;
import com.pack.executor.stepdefs.StepDefs;

/**
 * The Class Reports.
 */
public class Reports {

	public static Logger logger = Logger.getLogger(Reports.class.getName());

	/** The extent. */
	public static ExtentReports extentReport;

	/** The test. */
	public static ExtentTest extentTest;

	/** The config. */
	public static Map<String, String> config;

	/** The report file. */
	public static String reportFile;

	/** The fail count. */
	public static int failCount = 0;

	/** Soft Assertion *. */
	/** Soft Assertion *. */
	public static SoftAssert softAssert;

	public static Map<String, String> testMap;

	public static String testCaseName;

	public static String screenShortFolder;
	public static String responseFolder;

	/**
	 * To create the new report.
	 * 
	 * @param reportFolder the report folder
	 * @param tcName       the tc name
	 * @throws Exception the exception
	 */
	public static void createNewReport(String tcName) throws Exception {
		File fileDirectry = new File(System.getProperty("user.dir") + "/" + AppData.getProperty("report_folder")
				+ "/report-" + Library.getDate());
		if (!fileDirectry.exists()) {
			fileDirectry.mkdir();
		}
		screenShortFolder = System.getProperty("user.dir") + "/" + AppData.getProperty("report_folder") + "/report-"
				+ Library.getDate();
		reportFile = screenShortFolder + "/" + tcName + "_" + Library.getDate("hhMMsss") + ".html";

		extentReport = new ExtentReports();
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFile);
		extentReport.attachReporter(htmlReporter);
		testMap = new HashMap<String, String>();
	}

	/**
	 * To add the test in report.
	 * 
	 * @param reportFolder the report folder
	 * @param reportName   the tc name
	 * @param description  the description
	 * @param iteration    the iteration
	 * @throws Exception the exception
	 */
	public static void startTest(String reportName, String scenarioName) throws Exception {
		if (extentReport == null) {
			createNewReport(reportName);
		}

		if (scenarioName != null) {
			testCaseName = scenarioName;
			extentTest = extentReport.createTest(scenarioName);
			testMap.put(testCaseName, "PASS");
		}
	}

	/**
	 * To define the success message.
	 * 
	 * @param message the message
	 * @throws IOException
	 */
	public static void pass(String stepName) {

		try {
			logger.info(stepName);
			extentTest.log(Status.PASS, stepName);

			if (StringUtils.equalsIgnoreCase(AppData.getProperty("screenshortForSuccessStep"), "Yes")) {
				String file_path = screenShortFolder + "/screenshots/" + Library.getDate("hhMMsss") + "_Pass" + ".jpg";
				File scrFile = ((TakesScreenshot) StepDefs.driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(file_path));
				extentTest.log(Status.INFO, "Snapshot below: " + extentTest.addScreencastFromPath(file_path));
			}
		} catch (Exception e) {
			logger.info("Unable to display pass message");
		}
	}

	/**
	 * To define the Failure message.
	 * 
	 * @param message the message
	 * @throws IOException
	 */

	public static void fail(String message, String cause) {
		try {
			logger.info("Reason: " + cause);
			// test.log(Status.FAIL, message);
			// test.log(Status.FAIL, message, "", "", false);
			extentTest.log(Status.FAIL, message);
			testMap.put(testCaseName, "FAIL");
			String file_path = screenShortFolder + "/screenshots/" + Library.getDate("hhMMsss") + "_Fail" + ".png";
			File scrFile = ((TakesScreenshot) StepDefs.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(file_path));
			extentTest.log(Status.FAIL, (Markup) extentTest.addScreencastFromPath(file_path));
			extentTest.log(Status.INFO, "Snapshot below: " + extentTest.addScreencastFromPath(file_path));
//			test.log(Status.INFO, cause);

			failCount++;
		} catch (Exception e) {
			logger.info("Unable to display Failure message" + message + " Reason: " + cause);
		}
	}

	public static void warning(String message) throws IOException {
		extentTest.log(Status.WARNING, message);
		String file_path = screenShortFolder + "/screenshots/" + Library.getDate("hhMMsss") + "_Warning" + ".jpg";
		File scrFile = ((TakesScreenshot) StepDefs.driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(file_path));
		extentTest.log(Status.INFO, "Snapshot below: " + extentTest.addScreencastFromPath(file_path));

	}

	/**
	 * To define the finishWithfail.
	 * 
	 * @param message the message
	 * @throws IOException
	 */

	public static void finishWithfail(String message) throws IOException {
		extentTest.log(Status.FAIL, message);
		testMap.put(testCaseName, "FAIL");
		failCount++;
	}

	/**
	 * To define the Skip message.
	 * 
	 * @param tcName    the tc name
	 * @param iteration the iteration
	 */
	public static void skip(String tcName, int iteration) {
		info(testCaseName + " Run mode has been mentioned as 'No'");
		testMap.put(testCaseName, "SKIP");
		extentTest.log(Status.SKIP, "Run mode has been mentioned as 'No'");
	}

	/**
	 * To complete the report.
	 * 
	 * @throws Exception
	 */
	public static void finishTest() throws Exception {
		extentReport.flush();
		if (failCount != 0) {
			softAssert = new SoftAssert();
			softAssert.fail();
		}
	}

	/**
	 * To print the regular output.
	 * 
	 * @param message the message
	 */
	// public static void info(String message) {
	// logger.info(message);
	// test.log(LogStatus.INFO, message);
	// }

	public static void info(String message) {
		logger.info(message);
		extentTest.log(Status.INFO, message);
	}

}