package io.github.royalspirit.sjctestframework.core.api;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.github.royalspirit.sjctestframework.core.api.ApiSpecification.requestSpecification;
import static io.github.royalspirit.sjctestframework.core.logging.LogFormatter.purple;
import static io.restassured.RestAssured.given;

public class ApiRequestExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ApiRequestExecutor.class);

    public Response sendGetRequest(String endpoint) {
        Response response = given()
                .spec(requestSpecification())
                .when()
                .get(endpoint);

        logger.info("GET request was sent to endpoint: '" + purple(endpoint) + "'.");
        logResponseBody(response);
        return response;
    }

    public Response sendGetRequest(String endpoint, Map<String, String> queryParams) {
        Response response = given()
                .spec(requestSpecification())
                .queryParams(queryParams)
                .when()
                .get(endpoint);

        logger.info("GET request was sent to endpoint: '" + purple(endpoint)
                + "' with query params: '" + purple(queryParams.toString()) + "'.");
        logResponseBody(response);
        return response;
    }

    public Response sendPostRequest(String endpoint, String jsonBody) {
        Response response = given()
                .spec(requestSpecification())
                .body(jsonBody)
                .when()
                .post(endpoint);

        logger.info("POST request was sent to endpoint: '" + purple(endpoint) + "' with JSON body.");
        logResponseBody(response);
        return response;
    }

    private void logResponseBody(Response response) {
        logger.debug("Response body:\n{}", response.getBody().asPrettyString());
    }

}
