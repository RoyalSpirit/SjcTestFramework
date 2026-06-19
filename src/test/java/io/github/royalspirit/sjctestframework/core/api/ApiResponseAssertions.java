package io.github.royalspirit.sjctestframework.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

import static io.github.royalspirit.sjctestframework.core.logging.LogFormatter.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApiResponseAssertions {

    private final ApiContext apiContext;
    private static final Logger logger = LoggerFactory.getLogger(ApiResponseAssertions.class);

    public ApiResponseAssertions(ApiContext apiContext) {
        this.apiContext = apiContext;
    }

    public void assertStatusCode(String expectedStatusCode) {
        int expected = Integer.parseInt(expectedStatusCode);

        apiContext.getResponse()
                .then()
                .statusCode(expected);

        logger.info("Response status code matched expected value: '" + green(expectedStatusCode) + "'.");
    }

    public void assertFieldValueEquals(String fieldName, String expectedValue) {
        Object actualValue = apiContext.getResponse()
                .jsonPath()
                .get(fieldName);

        assertNotNull(actualValue, "Response field '" + fieldName + "' should not be null.");
        assertEquals(expectedValue, String.valueOf(actualValue));

        logger.info("Response field '" + purple(fieldName) + "' matched expected value: '" + green(expectedValue) + "'.");
    }

    public void assertFieldIsNotEmpty(String fieldName) {
        Object actualValue = apiContext.getResponse()
                .jsonPath()
                .get(fieldName);

        assertNotNull(actualValue, "Response field '" + fieldName + "' should not be null.");

        if (actualValue instanceof String stringValue) {
            assertFalse(stringValue.isBlank(), "Response field '" + fieldName + "' should not be empty.");
        }

        if (actualValue instanceof Collection<?> collectionValue) {
            assertFalse(collectionValue.isEmpty(), "Response field '" + fieldName + "' should not be empty.");
        }

        if (actualValue instanceof Map<?, ?> mapValue) {
            assertFalse(mapValue.isEmpty(), "Response field '" + fieldName + "' should not be empty.");
        }

        logger.info("Response field '" + yellow(fieldName) + "' is not empty.");
    }

}
