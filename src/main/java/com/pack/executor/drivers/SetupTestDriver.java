package com.pack.executor.drivers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class SetupTestDriver.
 */
public class SetupTestDriver {

	/** The driver. */
	private WebDriver driver = null;

	/** The browser. */
	private String browser = null;

	/** The base url. */
	private String baseUrl = null;

	/** The os. */
	private String os = null;

	/** The node. */
	private String node = null;

	/**
	 * Instantiates a new setup test driver.
	 * 
	 * @param os
	 *            the os
	 * @param browser
	 *            the browser
	 * @param baseUrl
	 *            the base url
	 * @param node
	 *            the node
	 * @throws MalformedURLException
	 *             the malformed URL exception
	 */
	public SetupTestDriver(String os, String browser, String baseUrl, String node) throws MalformedURLException {
		this.browser = browser;
		this.os = os;
		this.baseUrl = baseUrl;
		this.node = node;

		Platform platform = Platform.fromString(os.toUpperCase());
		if (browser.equalsIgnoreCase("chrome")) {
			 ChromeOptions chromeOptions = new ChromeOptions();
			 chromeOptions.setCapability("platform", platform);
			 chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			 this.driver = new RemoteWebDriver(new URL(node + "/wd/hub"),
			 chromeOptions);
		} else if (browser.equalsIgnoreCase("firefox")) {
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setCapability("platform", platform);
			this.driver = new RemoteWebDriver(new URL(node + "/wd/hub"), firefoxOptions);
		} else {
			InternetExplorerOptions ieOption = new InternetExplorerOptions();
			ieOption.setCapability("platform", platform);
			this.driver = new RemoteWebDriver(new URL(node + "/wd/hub"), ieOption);
		}

		this.driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		this.driver.manage().window().maximize();
		this.driver.manage().deleteAllCookies();
		this.driver.get(baseUrl);

	}

	/**
	 * Gets the os.
	 * 
	 * @return the os
	 */
	public String getOs() {
		return this.os;
	}

	/**
	 * Gets the browser.
	 * 
	 * @return the browser
	 */
	public String getBrowser() {
		return this.browser;
	}

	/**
	 * Gets the base url.
	 * 
	 * @return the base url
	 */
	public String getBaseUrl() {
		return this.baseUrl;
	}

	/**
	 * Gets the node.
	 * 
	 * @return the node
	 */
	public String getNode() {
		return this.node;
	}

	/**
	 * Gets the driver.
	 * 
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return this.driver;
	}
}