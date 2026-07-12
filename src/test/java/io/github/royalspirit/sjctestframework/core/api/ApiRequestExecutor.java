package io.github.royalspirit.sjctestframework.core.api;

import groovy.json.JsonOutput;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.github.royalspirit.sjctestframework.core.api.ApiSpecification.requestSpecification;
import static io.github.royalspirit.sjctestframework.core.logging.LogFormatter.purple;
import static io.restassured.RestAssured.given;

public class ApiRequestExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ApiRequestExecutor.class);

    public Response sendGetRequest(String endpoint) {
        Response response = sendRequest("GET", endpoint, null, null, null);

        logger.info("GET request was sent to endpoint: '" + purple(endpoint) + "'.");
        logResponseBody(response);
        return response;
    }

    public Response sendGetRequest(String endpoint, Map<String, String> queryParams) {
        Response response = sendRequest("GET", endpoint, queryParams, null, null);

        logger.info("GET request was sent to endpoint: '" + purple(endpoint)
                + "' with query params: '" + purple(queryParams.toString()) + "'.");
        logResponseBody(response);
        return response;
    }

    public Response sendGetRequestWithHeaders(String endpoint, Map<String, String> headers) {
        Response response = sendRequest("GET", endpoint, null, headers, null);

        logger.info("GET request was sent to endpoint: '" + purple(endpoint)
                + "' with headers: '" + purple(headers.toString()) + "'.");
        logResponseBody(response);
        return response;
    }

    public Response sendPostRequest(String endpoint, String jsonBody) {
        Response response = sendRequest("POST", endpoint, null, null, jsonBody);

        logger.info("POST request was sent to endpoint: '" + purple(endpoint) + "' with JSON body.");
        logResponseBody(response);
        return response;
    }

    public Response sendPostRequest(String endpoint, Map<String, String> bodyFields) {
        Response response = sendRequest("POST", endpoint, null, null, JsonOutput.toJson(bodyFields));

        logger.info("POST request was sent to endpoint: '" + purple(endpoint)
                + "' with body fields: '" + purple(bodyFields.toString()) + "'.");
        logResponseBody(response);
        return response;
    }

    public Response sendPutRequest(String endpoint, String jsonBody) {
        Response response = sendRequest("PUT", endpoint, null, null, jsonBody);

        logger.info("PUT request was sent to endpoint: '" + purple(endpoint) + "' with JSON body.");
        logResponseBody(response);
        return response;
    }

    public Response sendPatchRequest(String endpoint, String jsonBody) {
        Response response = sendRequest("PATCH", endpoint, null, null, jsonBody);

        logger.info("PATCH request was sent to endpoint: '" + purple(endpoint) + "' with JSON body.");
        logResponseBody(response);
        return response;
    }

    public Response sendDeleteRequest(String endpoint) {
        Response response = sendRequest("DELETE", endpoint, null, null, null);

        logger.info("DELETE request was sent to endpoint: '" + purple(endpoint) + "'.");
        logResponseBody(response);
        return response;
    }

    private Response sendRequest(
            String method,
            String endpoint,
            Map<String, String> queryParams,
            Map<String, String> headers,
            Object body
    ) {
        RequestSpecification request = given()
                .spec(requestSpecification());

        if (queryParams != null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);
        }

        if (headers != null && !headers.isEmpty()) {
            request.headers(headers);
        }

        if (body != null) {
            request.body(body);
        }

        return request
                .when()
                .request(method, endpoint);
    }

    private void logResponseBody(Response response) {
        logger.debug("Response body:\n{}", response.getBody().asPrettyString());
    }

}
