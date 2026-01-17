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




