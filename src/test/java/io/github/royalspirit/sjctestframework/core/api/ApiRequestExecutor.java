package io.github.royalspirit.sjctestframework.core.api;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        return response;
    }

}
