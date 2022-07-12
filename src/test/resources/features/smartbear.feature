@AllTest
Feature: SmartBear Functionalities
  @Smoke
  Scenario: Validate Invalid Login Attempt
    Given user is on "http://secure.smartbearsoftware.com/samples/testcomplete12/WebOrders/login.aspx"
    When user enters username as "abcd"
    And user enters password as "abcd1234"
    And user clicks on Login input
    Then validate user sees "Invalid Login or Password." message

  @Smoke
  Scenario: Validate Valid Login Attempt
    Given user is on "http://secure.smartbearsoftware.com/samples/testcomplete12/WebOrders/login.aspx"
    When user enters username as "Tester"
    And user enters password as "test"
    And user clicks on Login input
    Then user should be routed to "http://secure.smartbearsoftware.com/samples/testcomplete12/weborders/"

  @Regression
  Scenario: Validate "Web Orders" Menu Items
    Given user is on "http://secure.smartbearsoftware.com/samples/testcomplete12/WebOrders/login.aspx"
    When user enters username as "Tester"
    And user enters password as "test"
    And user clicks on Login input
    Then user should be routed to "http://secure.smartbearsoftware.com/samples/testcomplete12/weborders/"
    And validate below menu items are displayed
      | View all orders | View all products | Order |

  @Regression
  Scenario: Validate "Check All" And "Uncheck All" Links
    Given user is on "http://secure.smartbearsoftware.com/samples/testcomplete12/WebOrders/login.aspx"
    When user enters username as "Tester"
    And user enters password as "test"
    And user clicks on Login input
    Then user should be routed to "http://secure.smartbearsoftware.com/samples/testcomplete12/weborders/"
    When user clicks on "Check All" link
    Then all rows should be checked
    When user clicks on "Uncheck All" link
    Then all rows should be unchecked

  @Regression
  Scenario: Validate Adding New Order
    Given user is on "http://secure.smartbearsoftware.com/samples/testcomplete12/WebOrders/login.aspx"
    When user enters username as "Tester"
    And user enters password as "test"
    And user clicks on Login input
    Then user should be routed to "http://secure.smartbearsoftware.com/samples/testcomplete12/weborders/"
    When user clicks on "Order" menu item
    And user selects "FamilyAlbum" as product
    And user enters 2 as quantity
    And user enters all address information
    And user enters all payment information
    And user clicks on "Process" link
    And validate user sees "New order has been successfully added." message
    And user clicks on "View all orders" menu item
    And user should see their order displayed in the "List of All Orders" table
    And validate all information entered displayed correct with the order

  @Smoke
  Scenario: Validate "Delete Selected" button
    Given user is on "http://secure.smartbearsoftware.com/samples/testcomplete12/WebOrders/login.aspx"
    When user enters username as "Tester"
    And user enters password as "test"
    And user clicks on Login input
    Then user should be routed to "http://secure.smartbearsoftware.com/samples/testcomplete12/weborders/"
    When user clicks on "Check All" link
    And user clicks on "Delete Selected" link
    Then validate all orders are deleted from the "List of All Orders" table
    And validate user sees "List of orders is empty. In order to add new order use this link." message