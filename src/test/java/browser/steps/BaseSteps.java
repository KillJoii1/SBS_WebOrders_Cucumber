package browser.steps;

import browser.utils.Waiter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import static browser.steps.Hooks.*;
import static browser.steps.Hooks.smartBearDefaultPage;

public class BaseSteps {
    @Given("user is on {string}")
    public void userIsOn(String url) {
        driver.get(url);
    }

    @When("user enters username as {string}")
    public void userEntersUsernameAs(String username) {
        Waiter.forClickable(smartBearLoginPage.usernameInput).sendKeys(username);
    }

    @And("user enters password as {string}")
    public void userEntersPasswordAs(String password) {
        Waiter.forClickable(smartBearLoginPage.passwordInput).sendKeys(password);
    }

    @And("user clicks on Login input")
    public void userClicksOnLoginInput() {
        Waiter.forClickable(smartBearLoginPage.loginInput).click();
    }

    @Then("user should be routed to {string}")
    public void userShouldBeRoutedTo(String url) {
        Waiter.forUrl(url);
        Assert.assertEquals(url, driver.getCurrentUrl());
    }

    @When("user clicks on {string} menu item")
    public void userClicksOnMenuItem(String menuText) {
        switch (menuText) {
            case "View all orders":
                Waiter.forClickable(smartBearDefaultPage.menuItems.get(0)).click();
                break;
            case "View all products":
                Waiter.forClickable(smartBearDefaultPage.menuItems.get(1)).click();
                break;
            case "Order":
                Waiter.forClickable(smartBearDefaultPage.menuItems.get(2)).click();
                break;
        }
    }
}
