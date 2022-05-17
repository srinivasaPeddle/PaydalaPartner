package com.pack.executor.libraries;

import static com.pack.executor.stepdefs.StepDefs.driver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pack.executor.stepdefs.StepDefs;
import com.pack.executor.utils.AppData;
import com.pack.executor.utils.ObjectReader;
import com.pack.executor.utils.Reports;
import com.pack.executor.utils.TestDataReader;

import cucumber.api.Scenario;

/**
 * The Class Library.
 */
public class Library {

	/** The logger. */
	static Logger logger = Logger.getLogger(Library.class.getName());

	/** The js. */
	static JavascriptExecutor js = (JavascriptExecutor) driver;

	private static long timeOutLong = Long.parseLong(AppData.getProperty("waitLongSeconds"));
	private static long timeOutShort = Long.parseLong(AppData.getProperty("waitShortSeconds"));
	private static long timeOutNormal = Long.parseLong(AppData.getProperty("waitNormalSeconds"));
	private static long timeOutTiny = Long.parseLong(AppData.getProperty("waitTinySeconds"));

	// ******************************************************************************************************
	/**
	 * Click.
	 * 
	 * @param object the object
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************

	public static void click(String object) throws Exception {
		try {
			logger.info("Trying to click on " + object);
			if (object.contains(">")) {
				String[] objectNames = object.split(">");
				for (int i = 0; i < objectNames.length; i++) {
					getElement(objectNames[i]).click();

				}
			} else {
				getElement(object).click();
				
			}
			Reports.pass("Click on '" + object + "' button");
		} catch (Exception e) {
			Reports.fail("Unable to Click on '" + object + "' button", e.getMessage());
			throw e;
		}
	}

	// ******************************************************************************************************
	/**
	 * Enter text.
	 * 
	 * @param object the object name
	 * @param text   the text
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static void enterText(String object, String text) throws Exception {
		try {
			logger.info("Tring to enter " + text + " on " + object);
			WebElement element = getElement(object);
			element.clear();
			if (!StringUtils.isEmpty(TestDataReader.getProperty(text))) {
				text = TestDataReader.getProperty(text);
			}
			element.sendKeys(text);
			Reports.pass("Enter text into '" + object + "' as '" + text + "'");
			verifyObjectValue(object, "value", text);

		} catch (Exception e) {
			Reports.fail("Unable to Enter text into '" + object + "' as '" + text + "'", e.getMessage());
			throw e;
		}
	}

	// ******************************************************************************************************
	/**
	 * Doubleclick.
	 * 
	 * @param object the object
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static void doubleClick(String object) throws Exception {
		try {
			logger.info("Script is trying to double click on " + object);
			Actions action = new Actions(driver);
			action.moveToElement(getElement(object)).build().perform();
			action.doubleClick(getElement(object)).build().perform();
		} catch (Exception e) {
			Reports.fail("Unable to double click on '" + object + "'", e.getMessage());
			throw e;
		}
	}

	// ******************************************************************************************************
	/**
	 * Select text by index.
	 * 
	 * @param object the object
	 * @param index  the index
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static void selectTextByIndex(String object, int index) throws Exception {
		try {
			logger.info("Trying to select the index " + index + " in " + object);
			Select select = new Select(getElement(object));
			select.selectByIndex(index);
			Reports.pass("Selected the value using index '" + index + "' in " + object);
		} catch (Exception e) {
			Reports.fail("Unable to Select value into '" + object + "' using index " + index, e.getMessage());
			throw e;
		}
	}

	// ******************************************************************************************************
	/**
	 * Select check box.
	 * 
	 * @param object the object name
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static void selectCheckBox(String object) throws Exception {
		try {
			logger.info("Trying to select the checkbox " + object);
			if (StringUtils.isEmpty(getProperty(object, "class"))) {
				getElement(object).click();
				if (StringUtils.isEmpty(getProperty(object, "class"))) {
					Reports.warning("Checkbox has not selected");
				}
			}
			Reports.pass(object + " checkbox has Selected");
		} catch (Exception e) {
			Reports.fail("Unable to 'Select' the checkbox '" + object + "'", e.getMessage());
			throw e;
		}
	}

	// ******************************************************************************************************
	/**
	 * Un select check box.
	 * 
	 * @param object the object name
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static void unSelectCheckBox(String object) throws Exception {
		try {
			logger.info("Trying to unselect the checkbox '" + object + "'");
			if (!StringUtils.isEmpty(getProperty(object, "class"))) {
				getElement(object).click();
				if (!StringUtils.isEmpty(getProperty(object, "class"))) {
					Reports.warning("'" + object + "' Checkbox has not 'UnSelected'");
				}
			}
			Reports.pass("'" + object + "' checkbox has 'UnSelected'");
		} catch (Exception e) {
			Reports.fail("Unable to 'UnSelect' the checkbox '" + object + "'", e.getMessage());
			throw e;
		}
	}

	// ******************************************************************************************************
	/**
	 * Gets the property.
	 * 
	 * @param object    the object name
	 * @param attribute the property
	 * @return the property
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static String getProperty(String object, String attribute) throws Exception {
		try {
			WebElement element = getElement(object);
			logger.info("Trying to fetch the '" + attribute + "' attribute value of '" + object + "'");
			if (StringUtils.equalsAnyIgnoreCase(attribute, "Text")) {
				return element.getText();
			} else {
				return element.getAttribute(attribute);
			}
		} catch (Exception e) {
			Reports.fail("Unable to get the '" + attribute + "' attribute value of '" + object + "'", e.getMessage());
			throw e;
		}
	}

	// ******************************************************************************************************
	/**
	 * Verify object value.
	 * 
	 * @param object        the object name
	 * @param propName      the prop name
	 * @param expectedValue the expected value
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static void verifyObjectValue(String object, String propName, String expectedValue) throws Exception {
		logger.info("Verifying the '" + object + "' " + propName + " value");
		String actValue = getProperty(object, propName);
		if (StringUtils.containsIgnoreCase(actValue, expectedValue)) {
			Reports.pass("The '" + propName + "' of the '" + object + "' is '" + expectedValue + "'");
		} else {
			Reports.fail("The '" + propName + "' of the '" + object + "' should be '" + expectedValue
					+ "' But Actual value is '" + actValue + "'", "");
		}
	}

	// ******************************************************************************************************
	/**
	 * getXPath : To get
	 * 
	 * @param objectName
	 * @param strDynamicParameters
	 * @return
	 */
	// ******************************************************************************************************
	public static String getXPath(String objectName, String... strDynamicParameters) {
		try {
			objectName = objectName.trim().toUpperCase();
			String xpath = ObjectReader.getProperty(objectName + "_XPATH");

			String strDP1 = strDynamicParameters.length > 0 ? strDynamicParameters[0] : "";
			String strDP2 = strDynamicParameters.length > 1 ? strDynamicParameters[1] : "";
			String strDP3 = strDynamicParameters.length > 2 ? strDynamicParameters[2] : "";

			if (strDP1.length() > 0)
				xpath = xpath.replace("PAR1", strDP1);

			if (strDP2.length() > 0)
				xpath = xpath.replace("PAR2", strDP2);

			if (strDP3.length() > 0)
				xpath = xpath.replace("PAR3", strDP3);

			logger.info("Dynamic XPath is :" + xpath);
			return xpath;
		} catch (Exception e) {
			return null;
		}
	}

	// ******************************************************************************************************
	/**
	 * getElement : Get elements with parameters
	 * 
	 * @param objectName
	 * @param strDynamicParameters
	 * @return
	 */
	// ******************************************************************************************************
	public static WebElement getElement(String objectName, String... strDynamicParameters) {
		try {
			objectName = objectName.trim().toUpperCase();
			String xpath = ObjectReader.getProperty(objectName + "_XPATH");

			String strDP1 = strDynamicParameters.length > 0 ? strDynamicParameters[0] : "";
			String strDP2 = strDynamicParameters.length > 1 ? strDynamicParameters[1] : "";
			String strDP3 = strDynamicParameters.length > 2 ? strDynamicParameters[2] : "";

			if (strDP1.length() > 0)
				xpath = xpath.replace("PAR1", strDP1);

			if (strDP2.length() > 0)
				xpath = xpath.replace("PAR2", strDP2);

			if (strDP3.length() > 0)
				xpath = xpath.replace("PAR3", strDP3);

			logger.info("Dynamic XPath is :" + xpath);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(xpath))));
			
			return scrollandFindElement(By.xpath(xpath));

		} catch (Exception e) {
			return null;
		}

	}

	// ******************************************************************************************************
	/**
	 * Gets the elements.
	 * 
	 * @param objectName  the object name
	 * @param objectValue the object value
	 * @return the elements
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static List<WebElement> getElements(String objectName, String... objectValue) throws Exception {
		boolean elementFound = true;
		objectName = objectName.trim().toUpperCase();
		String xpath = ObjectReader.getProperty(objectName + "_XPATH");

		if (!StringUtils.isEmpty(xpath)) {
			throw new Exception("None of the Object properties has been specified");
		}

		if (!StringUtils.isEmpty(xpath)) {
			try {
				logger.info("Driver is trying to find the object using Xpath property " + xpath);
				return scrollandFindElements(By.xpath(xpath), 2);
			} catch (Exception e) {
				elementFound = false;
			}
		}

		if (!elementFound) {
			throw new Exception("Object could not be found with any given properties.");
		}
		return null;
	}

	// ******************************************************************************************************
	/**
	 * Click.
	 * 
	 * @param object the object
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************

	public static void clickWithSync(String object, String... strAttributeValue) {
		try {
			WebElement appName = Library.getElement(object, strAttributeValue);
			WebDriverWait wait = new WebDriverWait(StepDefs.driver, timeOutLong);
			appName = wait.until(ExpectedConditions.elementToBeClickable(appName));
			appName.click();
			Reports.pass("Click on '" + object + "' button");

		} catch (Exception e) {
			Reports.fail("Unable to Click on '" + object + "' button", e.getMessage());
		}
	}

	// ******************************************************************************************************
	/**
	 * 
	 * @param object
	 * @param strAttributeValue
	 * @throws Exception
	 */
	// ******************************************************************************************************
	public static void clickJS(String object, String... strAttributeValue) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) StepDefs.driver;
			WebElement appName = Library.getElement(object, strAttributeValue);
			js.executeScript("arguments[0].scrollIntoView(true);", appName);
			js.executeScript("arguments[0].click();", appName);
			Reports.pass("Click on '" + object + "' button using Java Script Successful");
		} catch (Exception e) {
			Reports.fail("Unable to Click on '" + object + "' button using Java Script Successful", e.getMessage());
		}
	}

	// ******************************************************************************************************
	/**
	 * 
	 * @param object
	 * @param strAttributeValue
	 * @throws Exception
	 */
	// ******************************************************************************************************
	public static void scrollToViewJS(String object, String... strAttributeValue) throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) StepDefs.driver;
			WebElement appName = Library.getElement(object, strAttributeValue);
			js.executeScript("arguments[0].scrollIntoView(true);", appName);
			Reports.pass("Scroll to '" + object + "' button using Java Script Successful");
		} catch (Exception e) {
			Reports.fail("Unable to Scroll to '" + object + "' button using Java Script Successful", e.getMessage());
		}
	}

	// ******************************************************************************************************
	/**
	 * Wait for element exist.
	 * 
	 * @param object   the object
	 * @param waitTime the wait time
	 * @return the web element
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static WebElement scrollandFindElement(By object) {
		scrollToViewObject(driver.findElement(object));
		return driver.findElement(object);
	}

	// ******************************************************************************************************
	/**
	 * Wait for element exist.
	 * 
	 * @param object   the object
	 * @param waitTime the wait time
	 * @return the web element
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static WebElement findElement(By object) throws Exception {
		return driver.findElement(object);
	}

	// ******************************************************************************************************
	/**
	 * Wait for element exist.
	 * 
	 * @param object   the object
	 * @param waitTime the wait time
	 * @return the web element
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static List<WebElement> scrollandFindElements(By object, int... waitTime) throws Exception {
		scrollToViewObject(driver.findElement(object));
		return driver.findElements(object);
	}

	// ******************************************************************************************************
	/**
	 * Scroll to view object.
	 * 
	 * @param element the element
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static void scrollToViewObject(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.perform();
	}

	// ******************************************************************************************************
	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	// ******************************************************************************************************
	public static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		Date time = c.getTime();
		String date = dateFormat.format(time);
		return date;
	}

	// ******************************************************************************************************
	/**
	 * Gets the date.
	 * 
	 * @param format the format
	 * @return the date
	 */
	// ******************************************************************************************************
	public static String getDate(String format) {
		return getDate(format, 0);
	}

	// ******************************************************************************************************
	/**
	 * Format date.
	 * 
	 * @param date   the date
	 * @param format the format
	 * @return the string
	 */
	// ******************************************************************************************************
	@SuppressWarnings("deprecation")
	public static String formatDate(String date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(new Date(date));
	}

	// ******************************************************************************************************
	/**
	 * Gets the date.
	 * 
	 * @param format the format
	 * @param future the future
	 * @return the date
	 */
	// ******************************************************************************************************
	public static String getDate(String format, int future) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, future);
		Date tomorrow = calendar.getTime();
		return simpleDateFormat.format(tomorrow);
	}

	// ******************************************************************************************************
	/**
	 * Random string.
	 * 
	 * @param length     the length
	 * @param stringCase the string case
	 * @return the string
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static String randomString(int length, String... stringCase) throws Exception {
		String rndString = "";
		String letters = "";

		if (stringCase.length > 0) {
			if (StringUtils.equalsIgnoreCase("UCASE", stringCase[0])) {
				letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			} else if (StringUtils.equalsIgnoreCase("LCASE", stringCase[0])) {
				letters = "abcdefghijklmnopqrstuvwxyz";
			} else {
				letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
			}
		} else {
			letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		}

		for (int i = 0; i < length; i++) {
			double a = Math.random();
			int x = (int) (a * 100);

			if (letters.length() == 26) {
				if (x > 25 && x < 52) {
					x = x - 26;
				} else if (x > 51 && x < 78) {
					x = x - 52;
				} else if (x > 77 && x < 99) {
					x = x - 78;
				} else {
					x = 1;
				}
			} else {
				if (x > 51 && x < 78) {
					x = x - 26;
				} else if (x > 77 && x < 99) {
					x = x - 52;
				} else {
					x = 1;
				}
			}

			char letter = letters.charAt(x);
			rndString = rndString + String.valueOf(letter);
		}

		return rndString;
	}

	// ******************************************************************************************************
	/**
	 * Random number.
	 * 
	 * @param length the length
	 * @return the string
	 * @throws Exception the exception
	 */
	// ******************************************************************************************************
	public static String randomNumber(int length) throws Exception {
		String rndNumber = "";
		String numbers = "1234567890";

		for (int i = 0; i < length; i++) {
			double a = Math.random();
			int x = (int) (a * 10);

			if (x > 9) {
				x = x - 10;
			}
			char number = numbers.charAt(x);
			rndNumber = rndNumber + number;
		}
		return rndNumber;
	}

	// ******************************************************************************************************
	/**
	 * Gets the feature file name.
	 * 
	 * @param scenario the scenario
	 * @return the feature file name
	 */
	// ******************************************************************************************************
	public static String getFeatureFileName(Scenario scenario) {
		String rawFeatureName = scenario.getId().split(";")[0];
		return rawFeatureName.substring(0, 1).toUpperCase() + rawFeatureName.substring(1);
	}

	// **************************************************************************************
	/**
	 * To Click on Tile.
	 * 
	 * @param objectName1 the object name 1
	 * @param objectName2 the object name 2
	 */
	// **************************************************************************************
	public static void clickOnTile(String objectName1, String objectName2) {

		AppData.properties.setProperty("waitLongSeconds", "2");
		Actions action = new Actions(driver);
		try {
			Thread.sleep(4000);
			action.moveToElement(getElement(objectName1)).click().build().perform();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			// Coming to exception, to be fixed
			Thread.sleep(2000);
			getElement(objectName2).click();
		} catch (Exception e) {
			AppData.properties.setProperty("waitLongSeconds", "60");
		} finally {
			AppData.properties.setProperty("waitLongSeconds", "60");
		}

	}

	// **************************************************************************************
	/**
	 * To select dropdown with Text.
	 * 
	 * @param object      the object
	 * @param txtToSelect the txt to select
	 * @throws Exception the exception
	 */
	// **************************************************************************************
	public static void SelectDropDownWithText(String object, String txtToSelect) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		Actions action = new Actions(driver);
		WebElement selectCtrl = wait.until(ExpectedConditions.elementToBeClickable(getElement(object)));
		action.moveToElement(selectCtrl).click().sendKeys(txtToSelect).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(1000);
	}

	// **************************************************************************************
	/**
	 * To generate Username with Alpha Numaric characters.
	 * 
	 * @param strPrifix the str prifix
	 * @return : Alphanumeric user name
	 */
	// **************************************************************************************
	public static String getRandomAlphaname(String strPrifix) {
		String username = RandomStringUtils.randomAlphabetic(8);
		username = "auto" + strPrifix + "_" + username;
		return username;
	}

	// **************************************************************************************
	/**
	 * To generate Username with Alpha Numaric characters.
	 * 
	 * @return : Alphanumeric user name
	 */
	// **************************************************************************************
	public static String getAlphaNumaricUsername() {
		String username = RandomStringUtils.randomAlphanumeric(3);
		username = "auto_" + username;
		return username;
	}

	// ***********************************************************************************************
	/**
	 * To scroll to the bottom of the page.
	 */
	// ***********************************************************************************************
	public static void scrollToBottom() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	// ***********************************************************************************************
	/**
	 * To scroll to the bottom of the page.
	 */
	// ***********************************************************************************************
	public static void scrollToTop() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0)");
	}

	// **************************************************************************************
	/**
	 * To select dropdown with Text.
	 * 
	 * @param object      the object
	 * @param txtToSelect the txt to select
	 * @throws Exception the exception
	 */
	// **************************************************************************************
	public static void SelectDropDownWithTextAdvanced(String object, String txtToSelect) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		Actions action = new Actions(driver);
		WebElement selectCtrl = wait.until(ExpectedConditions.elementToBeClickable(getElement(object)));
		Thread.sleep(1000);
		action.moveToElement(selectCtrl).click().build().perform();
		Thread.sleep(2000);
		driver.findElement(
				By.xpath("//div[@class='select2-result-label']" + "[contains(text(),'" + txtToSelect + "')]")).click();
		Thread.sleep(1000);
	}

	// **************************************************************************************
	/**
	 * To check if the attribute is available.
	 * 
	 * @param strElementID the str element ID
	 * @param attribute    the attribute
	 * @return Ture if exist , false if attribute is absent
	 * @throws Exception the exception
	 */
	// **************************************************************************************
	private static boolean isAttributePresent(String strElementID, String attribute) throws Exception {
		WebElement eleCheckBox = Library.getElement(strElementID);
		Boolean result = false;
		try {
			String value = eleCheckBox.getAttribute(attribute);

			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	// **************************************************************************************
	/**
	 * Click checkbox if not clicked.
	 * 
	 * @param strElementID the str element ID
	 * @param value        the value
	 * @throws Exception the exception
	 */
	// **************************************************************************************
	public static void ClickCheckboxIfNotClicked(String strElementID, String value) throws Exception {

		if (isAttributePresent(strElementID, value)) {
			logger.info("Check Box : " + strElementID + "is Already Checked");
		} else {
			Library.getElement(strElementID).click();
		}

	}
}
