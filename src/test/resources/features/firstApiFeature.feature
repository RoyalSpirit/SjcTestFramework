#language: en
@api
Feature: First API Test Feature

  @API-TEST-001
  Scenario: API-TEST-001 Base api test
    * api (sends GET request) to endpoint "/posts/1"
    * api (checks response status code) equals "200"
    * api (checks response field value) "id" equals "1"
    * api (checks response field is not empty) "title"

  @API-TEST-002
  Scenario: API-TEST-002 Get posts by user id
    * api (sends GET request) to endpoint "/posts" with query params:
      | userId | 1 |
    * api (checks response status code) equals "200"
    * api (checks response list is not empty)
    * api (checks response list size) equals "10"
    * api (checks response field is not empty) "[0].title"
    * api (checks response field value) "[1].title" equals "qui est esse"

  @API-TEST-003
  Scenario: API-TEST-003 Create post
    * api (sends POST request) to endpoint "/posts" with JSON body:
      """
      {
        "title": "foo",
        "body": "bar",
        "userId": 1
      }
      """
    * api (checks response status code) equals "201"
    * api (checks response field value) "title" equals "foo"
    * api (checks response field value) "body" equals "bar"
    * api (checks response field value) "userId" equals "1"
    * api (checks response field is not empty) "id"
