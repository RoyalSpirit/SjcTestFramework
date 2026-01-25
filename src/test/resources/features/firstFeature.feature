#language: en
Feature: First Test Feature

  @TEST-001
  Scenario: TEST-001 User login and check list of products
  * user is on page "Page Swag Labs"
  * user (fills field) "Username" with value "standard_user"
  * user (fills field) "Password" with value "secret_sauce"
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
    * user is on page "Menu Panel"
    * user (press button) "Logout"
    * user is on page "Page Swag Labs"

  @TEST-002
  Scenario: TEST-002 User login form check
    * user is on page "Page Swag Labs"
    * user (press button) "Login"
    * user (checks field or element equals expected value) "Login error message" equals "Epic sadface: Username is required"
    * user (fills field) "Username" with value "standard_user"
    * user (press button) "Login"
    * user (checks field or element equals expected value) "Login error message" equals "Epic sadface: Password is required"
    * user (clears field) "Username"
    * user (fills field) "Password" with value "secret_sauce"
    * user (press button) "Login"
    * user (checks field or element equals expected value) "Login error message" equals "Epic sadface: Username is required"
    * user (fills field) "Username" with value "bla-bla"
    * user (fills field) "Password" with value "bla-bla"
    * user (press button) "Login"
    * user (checks field or element equals expected value) "Login error message" equals "Epic sadface: Username and password do not match any user in this service"

  @TEST-003
  Scenario: TEST-003 Add and remove items from cart with checks
    * user is on page "Page Swag Labs"
    * user (fills field) "Username" with value "standard_user"
    * user (fills field) "Password" with value "secret_sauce"
    * user (press button) "Login"
    * user is on page "Products"
    # Removing previously added items from the cart
    * user (press button) "Menu"
    * user is on page "Menu Panel"
    * user (press button) "Reset App State"
    * user (press button) "Close Menu"
    # Executing the main scenario
    * user is on page "Products"
    * user (selects element from list) "Products list" based on name "Sauce Labs Bike Light"
    * user is on page "Detailed Product Information"
    * user (checks field or element equals expected value) "Product title" equals "Sauce Labs Bike Light"
    * user (press button) "Add to cart"
    * user (click on element) "Back to products"
    * user is on page "Products"
    * user (selects element from list) "Products list" based on name "Sauce Labs Onesie"
    * user is on page "Detailed Product Information"
    * user (checks field or element equals expected value) "Product title" equals "Sauce Labs Onesie"
    * user (press button) "Add to cart"
    * user (click on element) "Back to products"
    * user is on page "Products"
    * user (selects element from list) "Products list" based on name "Sauce Labs Fleece Jacket"
    * user is on page "Detailed Product Information"
    * user (checks field or element equals expected value) "Product title" equals "Sauce Labs Fleece Jacket"
    * user (press button) "Add to cart"
    * user (click on element) "Back to products"
    * user is on page "Products"
    * user (click on element) "Cart"
    * user is on page "Your Cart"
    * user (checks list of elements) "Products list in cart" with data:
      | Sauce Labs Bike Light     |
      | Sauce Labs Onesie         |
      | Sauce Labs Fleece Jacket  |
    * user (selects element from list) "Products list in cart" based on name "Sauce Labs Onesie"
    * user is on page "Detailed Product Information"
    * user (press button) "Remove"
    * user (click on element) "Cart"
    * user is on page "Your Cart"
    * user (checks list of elements) "Products list in cart" with data:
      | Sauce Labs Bike Light     |
      | Sauce Labs Fleece Jacket  |



