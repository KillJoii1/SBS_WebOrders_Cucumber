package browser.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Driver {
    private Driver(){}

    private static WebDriver driver;

    public static WebDriver getDriver(){
        if (driver == null) {
            String browser = ConfigReader.getProperty("browser");
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().version("102").setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "safari":
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    driver = new SafariDriver();
                    break;
                case "headless":
                    driver = new HtmlUnitDriver(true);
                    break;
                default:
                    throw new NotFoundException("Browser driver failed to setup correctly!!");
            }
            if (!browser.equals("headless")) {
                setDriverWindow(ConfigReader.getProperty("window"));
            }
            driver.manage().timeouts().pageLoadTimeout(Long.parseLong(ConfigReader.getProperty("pageWait")), TimeUnit.SECONDS);
            setImplicitWait();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.quit();
            driver = null;
        }
    }

    public static void setDriverWindow(String key) {
        if (key.contains("max")) driver.manage().window().maximize();
        else if (key.contains("full")) driver.manage().window().fullscreen();
        else driver.manage().window().setSize(new Dimension(960, 540)); // tablet window size
    }

    /**
     * Mixing implicit waits with the added explicitWaits from Waiter utility class can produce inconsistent wait times.
     *      - PageLoadTimeout handles page loading well enough to avoid StaleElementException sync issues with page load
     *          but not necessarily handle DOM changes appropriately.
     *      - ImplicitWait is meant to find elements the first time, but any changes in a DOM once it's been loaded does
     *          not call to find the elements again, throwing a StaleElementException.
     *      - ImplicitWait struggles with finding elements not meant to be there anymore, for the same reason above.
     *
     *      - ExplicitWaits from custom Waiter utility class, as well as those in steps, are meant to test different
     *          StaleElementException workarounds for the reasons listed above.
     *
     *      - Testing 'ScheduledExecutorService' class to reset the impliciWait on the webdriver to default after
     *          forcing the implicitWait to a different value
     */

    public static void updateImplicitWait(long seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(Driver::setImplicitWait, seconds + 1, TimeUnit.SECONDS);
    }

    public static void setImplicitWait() {
        driver.manage().timeouts().implicitlyWait(Long.parseLong(ConfigReader.getProperty("implicitWait")), TimeUnit.SECONDS);
    }
}
