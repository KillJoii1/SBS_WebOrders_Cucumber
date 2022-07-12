package browser.steps;

import browser.utils.ConfigReader;
import browser.utils.Driver;
import browser.utils.DropdownHandler;
import browser.utils.Waiter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;

import static browser.steps.Hooks.*;


public class SmartBearSteps {

    @And("user clicks on {string} link")
    public void userClicksOnLink(String linkText) {
        switch (linkText) {
            case "Check All":
                Waiter.forClickable(smartBearDefaultPage.checkAllInput).click();
                break;
            case "Uncheck All":
                Waiter.forClickable(smartBearDefaultPage.uncheckAllInput).click();
                break;
            case "Process":
                Waiter.forClickable(smartBearProcessPage.processLink).click();
                break;
            case "Delete Selected":
                Waiter.forClickable(smartBearDefaultPage.deleteSelectedInput).click();
                break;
            default: throw new RuntimeException("Feature link text invalid!");
        }
    }

    @And("validate below menu items are displayed")
    public void validateBelowMenuItemsAreDisplayed(DataTable dataTable) {
        List<String> menuItems = dataTable.asList();
        for (int i = 0; i < menuItems.size(); i++) {
            Assert.assertEquals(menuItems.get(i), Waiter.forTextVisibility(smartBearDefaultPage.menuItems.get(i)).getText());
        }
    }

    /**
     * Either verify the element as we access it, or with Waiter.validateList() method
     */
    @Then("all rows should be checked")
    public void allRowsShouldBeChecked() {
        for (int i = 0; i < smartBearDefaultPage.tableCheckboxes.size(); i++) {
            Assert.assertTrue(Waiter.forSelectable(smartBearDefaultPage.tableCheckboxes.get(i)).isSelected());
        }
    }

    @Then("all rows should be unchecked")
    public void allRowsShouldBeUnchecked() {
        Waiter.validateList(smartBearDefaultPage.tableCheckboxes);
        for (WebElement element : smartBearDefaultPage.tableCheckboxes) {
            Assert.assertFalse(element.isSelected());
        }
    }

    @And("user selects {string} as product")
    public void userSelectsAsProduct(String productText) {
        DropdownHandler.selectOptionByText(Waiter.forClickable(smartBearProcessPage.productDropdown), productText);
        smartBearProcessPage.fakerPerson.product = productText;
    }

    @And("user enters {int} as quantity")
    public void userEntersAsQuantity(int quantity) {
        Waiter.forClickable(smartBearProcessPage.quantityInput).sendKeys("" + quantity);
        smartBearProcessPage.fakerPerson.quantity = quantity;
    }

    @And("user enters all address information")
    public void userEntersAllAddressInformation() {
        Waiter.forClickable(smartBearProcessPage.customerNameInput).sendKeys(smartBearProcessPage.fakerPerson.name);
        Waiter.forClickable(smartBearProcessPage.streetAddressInput).sendKeys(smartBearProcessPage.fakerPerson.streetAddress);
        Waiter.forClickable(smartBearProcessPage.cityInput).sendKeys(smartBearProcessPage.fakerPerson.cityState);
        Waiter.forClickable(smartBearProcessPage.stateInput).sendKeys(smartBearProcessPage.fakerPerson.country);
        Waiter.forClickable(smartBearProcessPage.zipInput).sendKeys(smartBearProcessPage.fakerPerson.zip);
    }

    @And("user enters all payment information")
    public void userEntersAllPaymentInformation() {
        Waiter.forClickable(smartBearProcessPage.cardTypeInputs.get(1)).click();
        Waiter.forClickable(smartBearProcessPage.cardNumberInput).sendKeys(smartBearProcessPage.fakerPerson.cardNumber);
        Waiter.forClickable(smartBearProcessPage.cardExpireInput).sendKeys(smartBearProcessPage.fakerPerson.cardExpiration);
    }

    /**
     * Implemented lazy check of a single product per scenario.
     */
    @Then("user should see their order displayed in the \"List of All Orders\" table")
    public void userShouldSeeTheirOrderDisplayedInTheListOfAllOrdersTable() {
        Assert.assertEquals(smartBearProcessPage.fakerPerson.name,
                Waiter.forTextVisibility(smartBearDefaultPage.firstTableRowDetails.get(1)).getText());
    }

    @And("validate all information entered displayed correct with the order")
    public void validateAllInformationEnteredDisplayedCorrectWithTheOrder() {
        if (!ConfigReader.getProperty("browser").equals("headless")) {
            Waiter.validateList(smartBearDefaultPage.firstTableRowDetails);
            Assert.assertFalse(smartBearDefaultPage.firstTableRowDetails.get(1).isSelected());
        }
        Assert.assertEquals(smartBearProcessPage.fakerPerson.product, smartBearDefaultPage.firstTableRowDetails.get(2).getText());
        Assert.assertEquals(smartBearProcessPage.fakerPerson.quantity, Integer.parseInt(smartBearDefaultPage.firstTableRowDetails.get(3).getText()));
        Assert.assertEquals(smartBearProcessPage.fakerPerson.date, smartBearDefaultPage.firstTableRowDetails.get(4).getText());
        Assert.assertEquals(smartBearProcessPage.fakerPerson.streetAddress, smartBearDefaultPage.firstTableRowDetails.get(5).getText());
        Assert.assertEquals(smartBearProcessPage.fakerPerson.cityState, smartBearDefaultPage.firstTableRowDetails.get(6).getText());
        Assert.assertEquals(smartBearProcessPage.fakerPerson.country, smartBearDefaultPage.firstTableRowDetails.get(7).getText());
        Assert.assertEquals(smartBearProcessPage.fakerPerson.zip, smartBearDefaultPage.firstTableRowDetails.get(8).getText());
        Assert.assertEquals(smartBearProcessPage.fakerPerson.cardType, smartBearDefaultPage.firstTableRowDetails.get(9).getText());
        Assert.assertEquals(smartBearProcessPage.fakerPerson.cardNumber, smartBearDefaultPage.firstTableRowDetails.get(10).getText());
        Assert.assertEquals(smartBearProcessPage.fakerPerson.cardExpiration, smartBearDefaultPage.firstTableRowDetails.get(11).getText());
    }

    /**
     * Asserting that the 'emptyTableMessage' has been displayed could be enough to validate that the table has
     * been deleted but the assertion for .isDisplayed() could more appropriately fit in the "validate user sees
     * {string} message". Of course the test cases can be modified and more but this is a good time to show that
     * we can dynamically update the implicitWait if we know that the element should not be there, avoiding the
     * implicitWait defined in our 'config.properties' and temporarily setting it off.
     */
    @Then("validate all orders are deleted from the \"List of All Orders\" table")
    public void validateAllOrdersAreDeletedFromTheListOfAllOrdersTable() {
        Driver.updateImplicitWait(0);
        Assert.assertTrue(smartBearDefaultPage.firstTableRowDetails.isEmpty());
    }

    @And("validate user sees {string} message")
    public void validateUserSeesMessage(String message) {
        switch (message) {
            case "Invalid Login or Password.":
                Assert.assertTrue(smartBearLoginPage.failedLoginMessage.isDisplayed());
                break;
            case "New order has been successfully added.":
                Assert.assertTrue(smartBearProcessPage.newOrderVerificationLabel.isDisplayed());
                break;
            case "List of orders is empty. In order to add new order use this link.":
                Assert.assertTrue(smartBearDefaultPage.emptyTableMessage.isDisplayed());
                break;
        }
    }
}
