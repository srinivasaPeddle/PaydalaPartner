package com.pack.executor.drivers;

import static com.pack.executor.drivers.DriverType.CHROME;

import org.openqa.selenium.WebDriver;

import com.pack.executor.utils.AppData;

/**
 * A factory for creating Driver objects.
 */
public class DriverFactory {

    /** The driver. */
    private WebDriver driver;

    /** The selected driver type. */
    private DriverType selectedDriverType;

    /**
     * Instantiates a new driver factory.
     */
    public DriverFactory() {
        DriverType driverType = CHROME;
        String browser = AppData.properties.getProperty("browser");
        try {
            driverType = DriverType.valueOf(browser.toUpperCase());
        } catch (Exception exception) {
        }
        selectedDriverType = driverType;
    }

    /**
     * Gets the driver.
     * @return the driver
     */
    public WebDriver getDriver() {
        if (driver == null) {
            instantiateWebDriver(selectedDriverType);
        }
        return driver;
    }

    /**
     * Gets the stored driver.
     * @return the stored driver
     */
    public WebDriver getStoredDriver() {
        return driver;
    }

    /**
     * Quit driver.
     */
    public void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Instantiate web driver.
     * @param driverType the driver type
     */
    private void instantiateWebDriver(DriverType driverType) {
        driver = driverType.getWebDriverObject();
    }
}
