package browser.steps;

import browser.pages.SmartBearDefaultPage;
import browser.pages.SmartBearLoginPage;
import browser.pages.SmartBearProcessPage;
import browser.utils.ConfigReader;
import browser.utils.Driver;
import browser.utils.Waiter;
import com.github.javafaker.Faker;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Hooks {
    public static WebDriver driver;
    public static WebDriverWait waiter;
    public static Faker faker;
    public static SmartBearLoginPage smartBearLoginPage;
    public static SmartBearDefaultPage smartBearDefaultPage;
    public static SmartBearProcessPage smartBearProcessPage;

    @Before
    public void setup() {
        driver = Driver.getDriver();
        waiter = Waiter.getWaiter();
        faker = new Faker();
        smartBearLoginPage = new SmartBearLoginPage();
        smartBearDefaultPage = new SmartBearDefaultPage();
        smartBearProcessPage = new SmartBearProcessPage();
    }

    @After
    public void teardown(Scenario scenario){
        if (!ConfigReader.getProperty("browser").equals("headless")) {
            try{
                if(scenario.isFailed()){
                    byte[] screenshot = ((TakesScreenshot) Driver.getDriver())
                            .getScreenshotAs(OutputType.BYTES);

                    scenario.embed(screenshot, "image/png");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Waiter.quitWaiter();
        Driver.quitDriver();
    }
}
