#language: en
@api
Feature: First API Test Feature

  @API-TEST-001
  Scenario: API-TEST-001 Base api test
    * api (sends GET request) to endpoint "/posts/1"
    * api (checks response status code) equals "200"
    * api (checks response field value) "id" equals "1"
    * api (checks response field is not empty) "title"
