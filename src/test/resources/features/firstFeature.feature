Feature: First Test Feature

  @TEST-001
  Scenario: TEST-001 User login and check list of products
  * user is on page "Page Swag Labs"
  * user (fills field) "Username" значением "standard_user"
  * user (fills field) "Password" значением "secret_sauce"
  * user (press button) "Login"
  * user is on page "Products"
  * user (checks list of elements) "Products list" with data:
    | Sauce Labs Backpack               |
    | Sauce Labs Bike Light             |
    | Sauce Labs Bolt T-Shirt           |
    | Sauce Labs Fleece Jacket          |
    | Sauce Labs Onesie                 |
    | Test.allTheThings() T-Shirt (Red) |
    * user (press button) "Menu"
    * user (press button) "Logout"
    * user is on page "Page Swag Labs"





