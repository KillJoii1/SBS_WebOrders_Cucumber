package browser.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

/**
 * This waiter utility class modified to run most element executions and handle StaleElementReferenceExceptions at once.
 * Implementing such methods on smartbear application may be unnecessary, but used to test any inconsistent wait length.
 */
public class Waiter {
    private static final int staleCheckCounter = Integer.parseInt(ConfigReader.getProperty("staleCheckCounter"));
    private static WebDriverWait wait;

    public static WebDriverWait getWaiter() {
        if (wait == null) wait = new WebDriverWait(Driver.getDriver(), Long.parseLong(ConfigReader.getProperty("explicitWait")));
        return wait;
    }

    public static void quitWaiter() {
        if (wait != null) {
            wait = null;
        }
    }

    /**
     *   Meant for debugging use only.
     */
    public static void pause(double seconds){
        try {
            Thread.sleep((long) (seconds * 1000L));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static WebElement forTextVisibility(WebElement element){
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (StaleElementReferenceException e) {
            updateStale(findBy(element), locator(element));
            wait.until(ExpectedConditions.visibilityOf(element));
        }
        return element;
    }

    public static WebElement forClickable(WebElement element){
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (StaleElementReferenceException e) {
            updateStale(findBy(element), locator(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }
        return element;
    }

    public static WebElement forSelectable(WebElement element){
        try {
            wait.until(ExpectedConditions.elementToBeSelected(element));
        } catch (StaleElementReferenceException e) {
            updateStale(findBy(element), locator(element));
            wait.until(ExpectedConditions.elementToBeSelected(element));
        }
        return element;
    }

    public static void forUrl(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
    }

    private static void updateStale(String findBy, String locator) {
        int counter = 0;
        while (counter < staleCheckCounter) {
            try {
                switch (findBy) {
                    case "id":
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locator)));
                        break;
                    case "css selector":
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locator)));
                        break;
                    case "xpath":
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
                        break;
                    default: throw new RuntimeException("Incorrect 'FindBy' in 'updateStale' method... -> " + findBy);
                }
                break;
            } catch (StaleElementReferenceException e) {
                if (++counter == staleCheckCounter) throw e;
                // Else, do nothing, loop back and try to find element presence again
            }
        }
    }

    /**
     *     Only confirmed working with @FindBy
     */
    public static void validateList(List<WebElement> elements) {
        int counter = 0;
        while (counter < staleCheckCounter) {
            try {
                switch (findBy(elements.get(0))) {
                    case "id":
                        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(locator(elements.get(0)))));
                        break;
                    case "css selector":
                        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(locator(elements.get(0)))));
                        break;
                    case "xpath":
                        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator(elements.get(0)))));
                        break;
                    default: throw new RuntimeException("Incorrect 'FindBy' in 'updateStale' method... -> " + findBy(elements.get(0)));
                }
                break;
            } catch (StaleElementReferenceException e) {
                if (++counter == staleCheckCounter) throw e;
                // Else, do nothing, loop back and try to find element presence again
            }
        }
    }

    /**
     * Return the @FindBy method used for the WebElement as a String
     * Note: Headless was forced to follow cssSelector
     */
    public static String findBy(WebElement element) {
        if (ConfigReader.getProperty("browser").equals("headless")) return "css selector";
        if (element.toString().contains("-> id")) return "id";
        if (element.toString().contains("-> css selector")) return "css selector";
        if (element.toString().contains("-> xpath")) return "xpath";
        return "N/A";
    }

    /**
     * Return the locator used to find the WebElement as a String
     * Note: HTMLUnitDriver toString() on element returns the full HTML as a String, where this
     *       method then creates a dummy cssSelector locator from the tag and first attribute.
     *       It may not always work as intended.
     */
    public static String locator(WebElement element) {
        StringBuilder locator = new StringBuilder(element.toString());
        if (ConfigReader.getProperty("browser").equals("headless")) {
            String attribute = locator.substring(locator.indexOf(" ") + 1, locator.indexOf("\"", locator.indexOf("\"") + 1) + 1);
            return element.getTagName() + "[" + attribute + "]";
        }
        locator.delete(0, locator.lastIndexOf("->") + 3);
        locator.delete(0, locator.indexOf(":") + 1);
        locator.deleteCharAt(locator.lastIndexOf("]"));
        return locator.toString();
    }
}
