package browser.utils;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DropdownHandler {

    public static void selectOptionByText(WebElement element, String optionText) {
        try {
            new Select(element).selectByVisibleText(optionText);
        } catch (Exception e) {
            throw new NotFoundException("Option in dropdown invalid on feature!!");
        }
    }
}
