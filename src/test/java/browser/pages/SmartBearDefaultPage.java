package browser.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SmartBearDefaultPage extends BasePage {
    public SmartBearDefaultPage() {
        super();
    }

    @FindBy(id = "ctl00_MainContent_btnCheckAll")
    public WebElement checkAllInput;

    @FindBy(id = "ctl00_MainContent_btnUncheckAll")
    public WebElement uncheckAllInput;

    @FindBy(id = "ctl00_MainContent_btnDelete")
    public WebElement deleteSelectedInput;

    @FindBy(id = "ctl00_MainContent_orderMessage")
    public WebElement emptyTableMessage;

    @FindBy(css = "input[type='checkbox']")
    public List<WebElement> tableCheckboxes;

    @FindBy(css = ".SampleTable tr:nth-child(2) td")
    public List<WebElement> firstTableRowDetails;
}
