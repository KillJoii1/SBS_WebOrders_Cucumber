package browser.pages;

import browser.steps.Hooks;
import browser.utils.ConfigReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SmartBearProcessPage extends BasePage {
    public SmartBearProcessPage() {
        super();
        fakerPerson = new OrderDetails();
    }

    public class OrderDetails {
        public OrderDetails() {
            date = ConfigReader.getProperty("date"); // time wasn't matching site by a few hours, hardcoded
            name = Hooks.faker.name().fullName();
            streetAddress = Hooks.faker.address().streetAddress();
            cityState = Hooks.faker.address().city();
            country = Hooks.faker.address().country();
            zip = Hooks.faker.address().zipCode().substring(0, 5);
            cardType = "MasterCard";
            cardNumber = Hooks.faker.number().digits(16);
            cardExpiration = "07/28";
        }
        public String product;
        public int quantity;
        public String date;
        public String name;
        public String streetAddress;
        public String cityState;
        public String country;
        public String zip;
        public String cardType;
        public String cardNumber;
        public String cardExpiration;
    }

    public OrderDetails fakerPerson;

    @FindBy(css = "select[id^='ctl00_MainContent']")
    public WebElement productDropdown;

    @FindBy(css = "input[name*='Quantity']")
    public WebElement quantityInput;

    @FindBy(css = "input[name*='txtName']")
    public WebElement customerNameInput;

    @FindBy(css = "input[name*='TextBox2']")
    public WebElement streetAddressInput;

    @FindBy(css = "input[name*='TextBox3']")
    public WebElement cityInput;

    @FindBy(css = "input[name*='TextBox4']")
    public WebElement stateInput;

    @FindBy(css = "input[name*='TextBox5']")
    public WebElement zipInput;

    @FindBy(css = "input[id*='cardList']")
    public List<WebElement> cardTypeInputs;

    @FindBy(css = "input[name*='TextBox6']")
    public WebElement cardNumberInput;

    @FindBy(css = "input[name*='TextBox1']")
    public WebElement cardExpireInput;

    @FindBy(id = "ctl00_MainContent_fmwOrder_InsertButton")
    public WebElement processLink;

    @FindBy(css = "input[type='reset']")
    public WebElement resetInput;

    @FindBy(css = ".buttons_process>strong")
    public WebElement newOrderVerificationLabel;
}
