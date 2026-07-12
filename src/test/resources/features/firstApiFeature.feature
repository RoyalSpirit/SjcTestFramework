#language: en
@api
Feature: First API Test Feature

  @API-TEST-001
  Scenario: API-TEST-001 Base api test
    * api (sends GET request) to endpoint "/posts/1"
    * api (checks response status code) equals "200"
    * api (checks response content type) contains "application/json"
    * api (checks response header) "Access-Control-Allow-Credentials" equals "true"
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
    * api (checks response field value) "[3].title" contains "occaecati"

  @API-TEST-003
  Scenario: API-TEST-003 Create post with JSON body
    * api (sends POST request) to endpoint "/posts" with JSON body:
      """
      {
        "title": "foo",
        "body": "bar",
        "userId": 1,
        "deletedAt": null
      }
      """
    * api (checks response status code) equals "201"
    * api (checks response field value) "title" equals "foo"
    * api (checks response field value) "body" equals "bar"
    * api (checks response field value) "userId" equals "1"
    * api (checks response field is null) "deletedAt"
    * api (checks response field does not exist) "archivedAt"
    * api (checks response field is not empty) "id"

  @API-TEST-004
  Scenario: API-TEST-004 Get post with request headers
    * api (sends GET request) to endpoint "/posts/1" with headers:
      | Accept | application/json |
    * api (checks response status code) equals "200"
    * api (checks response header) "Content-Type" contains "application/json"

  @API-TEST-005
  Scenario: API-TEST-005 Update and delete post
    * api (sends PUT request) to endpoint "/posts/1" with JSON body:
      """
      {
        "id": 1,
        "title": "updated title",
        "body": "updated body",
        "userId": 1
      }
      """
    * api (checks response status code) equals "200"
    * api (checks response field value) "title" equals "updated title"
    * api (sends PATCH request) to endpoint "/posts/1" with JSON body:
      """
      {
        "title": "patched title"
      }
      """
    * api (checks response status code) equals "200"
    * api (checks response field value) "title" equals "patched title"
    * api (sends DELETE request) to endpoint "/posts/1"
    * api (checks response status code) equals "200"

  @API-TEST-006
  Scenario: API-TEST-006 Create post with DataTable body
    * api (sends POST request) to endpoint "/posts" with body:
      | title  | table title |
      | body   | table body  |
      | userId | 1           |
    * api (checks response status code) equals "201"
    * api (checks response field value) "title" equals "table title"
    * api (checks response field value) "body" equals "table body"
    * api (checks response field value) "userId" equals "1"
    * api (checks response field is not empty) "id"
