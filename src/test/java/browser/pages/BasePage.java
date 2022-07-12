package browser.pages;

import browser.utils.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BasePage {
    public BasePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(css = "#ctl00_menu>li>a")
    public List<WebElement> menuItems;

    @FindBy(css = "td>h1")
    public WebElement logoText;
}
