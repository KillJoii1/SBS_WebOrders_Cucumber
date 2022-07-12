package browser.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SmartBearLoginPage extends BasePage {
    public SmartBearLoginPage() {
        super();
    }

    @FindBy(id = "ctl00_MainContent_username")
    public WebElement usernameInput;

    @FindBy(id = "ctl00_MainContent_password")
    public WebElement passwordInput;

    @FindBy(id = "ctl00_MainContent_login_button")
    public WebElement loginInput;

    @FindBy(id = "ctl00_MainContent_status")
    public WebElement failedLoginMessage;
}
