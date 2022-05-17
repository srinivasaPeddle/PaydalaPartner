package com.pack.executor.drivers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;

/**
 * The Class DriverBase.
 */
public class DriverBase {

    /** The web driver thread pool. */
    private static List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<>());

    /** The driver factory thread. */
    private static ThreadLocal<DriverFactory> driverFactoryThread;

    /**
     * Instantiate driver object.
     */
    public static void instantiateDriverObject() {
        driverFactoryThread = ThreadLocal.withInitial(() -> {
            DriverFactory driverFactory = new DriverFactory();
            webDriverThreadPool.add(driverFactory);
            return driverFactory;
        });
    }

    /**
     * Gets the driver.
     * @return the driver
     */
    public static WebDriver getDriver() {
        if (!isWebdriverInitialized()) {
            instantiateDriverObject();
        }
        return driverFactoryThread.get().getDriver();
    }

    /**
     * Checks if is webdriver initialized.
     * @return true, if is webdriver initialized
     */
    public static boolean isWebdriverInitialized() {
        return driverFactoryThread != null;
    }

    /**
     * Clear cookies.
     */
    public static void clearCookies() {
        try {
            driverFactoryThread.get().getStoredDriver().manage().deleteAllCookies();
        } catch (Exception ignored) {
        }
    }

    /**
     * Close driver objects.
     */
    public static void closeDriverObjects() {
        for (DriverFactory driverFactory : webDriverThreadPool) {
            driverFactory.quitDriver();
        }
    }
}