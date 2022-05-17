package com.pack.executor.stepdefs;

import static com.pack.executor.libraries.Library.enterText;
import static com.pack.executor.libraries.Library.click;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.pack.executor.drivers.DriverBase;
import com.pack.executor.libraries.Library;
import com.pack.executor.utils.AppData;
import com.pack.executor.utils.Reports;
import com.pack.executor.utils.TestDataReader;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * The Class StepDefs.
 */
public class StepDefs {

	/** The logger. */
	public Logger logger = Logger.getLogger(StepDefs.class.getName());

	/** The driver. */
	public static WebDriver driver;

	/** The report name. */
	public static String reportName;

	/** The client with end point. */
	public static String clientWithEndPoint = "";

	/** The feature map. */
	public static TreeMap<String, Integer> featureMap = new TreeMap<>();

	/** The skip test. */
	public boolean skipTest;

	/** The skip next iteration. */
	public static boolean skipNextIteration = false;

	/** The scenario. */
	public Scenario scenario;

	/** The exception. */
	public String exception;

	/** The dynamic map. */
	public static Map<String, String> dynamicMap = null;

	// ******************************************************************************************************

	// **************************************************************************************
	/**
	 * Sets the up.
	 * 
	 * @param scenario the new up
	 * @throws Throwable the throwable
	 */
	// **************************************************************************************
	@Before
	public void setUp(Scenario scenario) throws Throwable {
		String featureName = Library.getFeatureFileName(scenario);
		if (StringUtils.isEmpty(reportName)) {
			reportName = featureName.split("-")[0].trim();
		}
		
		Reports.startTest(StepDefs.reportName, scenario.getName());
		
		driver = DriverBase.getDriver();
		if (Reports.extentReport != null)
			Reports.extentTest = Reports.extentTest.createNode(scenario.getName(), "");
		logger.info("running scenario: " + scenario.getName());
	}

	// **************************************************************************************
	/**
	 * Navigate to url.
	 * 
	 * @param page the page
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// **************************************************************************************

	@Given("^I Navigate to (.*) Page$")
	public void navigateToUrl(String page) throws IOException {
		try {
			logger.info("Navigate to " + page);
			driver.get(AppData.getProperty("appUrl"));
			driver.manage().window().maximize();
			Reports.pass("Navigate to " + page + " Page");
		} catch (Exception e) {
			Reports.fail("Unable to Launch the browser or navigate to the desired url.", e.getMessage());
			Assert.fail();
		}
	}

	// **************************************************************************************
	/**
	 * Login.
	 * 
	 * @param userName the user name
	 * @param password the password
	 */
	// **************************************************************************************
	@When("^I login to application with (.*),(.*)$")
	public void login(String userName, String password) {
		try {
			enterText("userName", userName);
			enterText("password", password);
			clickOnObject("login", "button");
			Thread.sleep(4000);
		} catch (Exception e) {
			Reports.fail("Failed to login to application !!", "Issue is :" + e.getStackTrace());
		}
	}

	// **************************************************************************************
	/**
	 * Logout.
	 */
	// **************************************************************************************
	@When("^I logout from the application$")
	public void logout() {
		try {
			click("logout");
		} catch (Exception e) {
			Reports.fail("Failed to logout from the application !!", "Issue is :" + e.getStackTrace());
		}
	}

	// **************************************************************************************
	/**
	 * Select check box.
	 * 
	 * @param type   the type
	 * @param object the object
	 */
	// **************************************************************************************
	@Given("^I (.*) (.*) checkbox$")
	public void selectCheckBox(String type, String object) {
		try {
			if (StringUtils.equals(type, "Select")) {
				Library.selectCheckBox(object);
			} else {
				Library.unSelectCheckBox(object);
			}
		} catch (Exception e) {
			Reports.fail("Failed to select checkbox item !!" + object, "Issue is :" + e.getStackTrace());
		}
	}

	// **************************************************************************************
	/**
	 * Click on object.
	 * 
	 * @param object the object
	 * @param type   the type
	 */
	// **************************************************************************************
	@When("^I Click on (.*) (.*)$")
	public void clickOnObject(String object, String type) {
		try {
			Library.click(object);
		} catch (Exception e) {
			Reports.fail("Failed to click on object !!" + object, "Issue is :" + e.getStackTrace());
		}
	}

	// **************************************************************************************
	/**
	 * Tear down.
	 * 
	 * @param scenario the scenario
	 * @throws Throwable the throwable
	 */
	// **************************************************************************************
	@After
	public void tearDown(Scenario scenario) throws Throwable {
		DriverBase.closeDriverObjects();
		Reports.finishTest();
	}

	// **************************************************************************************
	/**
	 * Validate text.
	 * 
	 * @param objectName the object name
	 * @param property   the property
	 * @throws Throwable the throwable
	 */
	// **************************************************************************************
	@Then("^I validate (.*) value as (.*)$")
	public void validateText(String objectName, String property) throws Throwable {
		try {
			if (!StringUtils.isEmpty(TestDataReader.getProperty(property))) {
				property = TestDataReader.getProperty(property);
			}
			logger.info("validate text " + objectName + " as " + property);
			String totalRisk_Actval = Library.getElement(objectName).getText();
			if (totalRisk_Actval.equalsIgnoreCase(property)) {
				Reports.pass("Validate text  " + objectName + " as " + property);
			} else {
				exception = "Text validation fails " + objectName + " as " + property;
				Assert.fail(exception);
			}

		} catch (Exception e) {
			Reports.fail("Failed to validate the text !!", "Text is :" + e.getMessage());
		}
	}
	
	/*
	 * Then I should see the loggedin user
	 */
	@Then("I should see the (.*)")
	public void validatePageTitle(String pgTitle, String type)  throws Throwable {
		
	}
}
