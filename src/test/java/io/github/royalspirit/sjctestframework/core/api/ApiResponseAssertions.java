package io.github.royalspirit.sjctestframework.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
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
        int actual = apiContext.getResponse()
                .getStatusCode();

        assertEquals(expected, actual, "Response status code should match expected value. Expected: '"
                + expected + "', actual: '" + actual + "'.");

        logger.info("Response status code matched expected value: '" + green(expectedStatusCode) + "'.");
    }

    public void assertContentTypeContains(String expectedContentType) {
        String actualContentType = apiContext.getResponse()
                .getContentType();

        assertNotNull(actualContentType, "Response content type should not be null.");
        assertTrue(
                actualContentType.toLowerCase(Locale.ROOT).contains(expectedContentType.toLowerCase(Locale.ROOT)),
                "Response content type should contain expected value. Expected: '" + expectedContentType
                        + "', actual: '" + actualContentType + "'."
        );

        logger.info("Response content type contains expected value: '" + green(expectedContentType) + "'.");
    }

    public void assertHeaderContains(String headerName, String expectedHeaderValue) {
        String actualHeaderValue = apiContext.getResponse()
                .getHeader(headerName);

        assertNotNull(actualHeaderValue, "Response header '" + headerName + "' should not be null.");
        assertTrue(
                actualHeaderValue.toLowerCase(Locale.ROOT).contains(expectedHeaderValue.toLowerCase(Locale.ROOT)),
                "Response header '" + headerName + "' should contain expected value. Expected: '"
                        + expectedHeaderValue + "', actual: '" + actualHeaderValue + "'."
        );

        logger.info("Response header '" + purple(headerName) + "' contains expected value: '"
                + green(expectedHeaderValue) + "'.");
    }

    public void assertHeaderEquals(String headerName, String expectedHeaderValue) {
        String actualHeaderValue = apiContext.getResponse()
                .getHeader(headerName);

        assertNotNull(actualHeaderValue, "Response header '" + headerName + "' should not be null.");
        assertEquals(expectedHeaderValue, actualHeaderValue, "Response header '" + headerName
                + "' should match expected value. Expected: '" + expectedHeaderValue
                + "', actual: '" + actualHeaderValue + "'.");

        logger.info("Response header '" + purple(headerName) + "' matched expected value: '"
                + green(expectedHeaderValue) + "'.");
    }

    public void assertFieldValueEquals(String fieldName, String expectedValue) {
        Object actualValue = apiContext.getResponse()
                .jsonPath()
                .get(fieldName);

        assertNotNull(actualValue, "Response field '" + fieldName + "' should not be null.");
        assertEquals(expectedValue, String.valueOf(actualValue), "Response field '" + fieldName
                + "' should match expected value. Expected: '" + expectedValue + "', actual: '"
                + actualValue + "'.");

        logger.info("Response field '" + purple(fieldName) + "' matched expected value: '" + green(expectedValue) + "'.");
    }

    public void assertFieldValueContains(String fieldName, String expectedValue) {
        Object actualValue = apiContext.getResponse()
                .jsonPath()
                .get(fieldName);

        assertNotNull(actualValue, "Response field '" + fieldName + "' should not be null.");
        assertTrue(String.valueOf(actualValue).contains(expectedValue), "Response field '" + fieldName
                + "' should contain expected value. Expected: '" + expectedValue + "', actual: '"
                + actualValue + "'.");

        logger.info("Response field '" + purple(fieldName) + "' contains expected value: '"
                + green(expectedValue) + "'.");
    }

    public void assertFieldIsNull(String fieldName) {
        Object actualValue = apiContext.getResponse()
                .jsonPath()
                .get(fieldName);

        assertNull(actualValue, "Response field '" + fieldName + "' should be null. Actual: '"
                + actualValue + "'.");

        logger.info("Response field '" + yellow(fieldName) + "' is null.");
    }

    public void assertFieldDoesNotExist(String fieldName) {
        Object responseBody = apiContext.getResponse()
                .jsonPath()
                .get("$");
        Map<?, ?> responseMap = assertInstanceOf(Map.class, responseBody,
                "Response body should be a JSON object. Actual type: '" + getActualType(responseBody) + "'.");

        assertFalse(responseMap.containsKey(fieldName), "Response field '" + fieldName
                + "' should not exist. Actual response fields: '" + responseMap.keySet() + "'.");

        logger.info("Response field '" + yellow(fieldName) + "' does not exist.");
    }

    public void assertFieldIsNotEmpty(String fieldName) {
        Object actualValue = apiContext.getResponse()
                .jsonPath()
                .get(fieldName);

        assertNotNull(actualValue, "Response field '" + fieldName + "' should not be null.");

        if (actualValue instanceof String stringValue) {
            assertFalse(stringValue.isBlank(), "Response field '" + fieldName
                    + "' should not be empty. Actual: '" + actualValue + "'.");
        }

        if (actualValue instanceof Collection<?> collectionValue) {
            assertFalse(collectionValue.isEmpty(), "Response field '" + fieldName
                    + "' should not be empty. Actual collection size: '" + collectionValue.size() + "'.");
        }

        if (actualValue instanceof Map<?, ?> mapValue) {
            assertFalse(mapValue.isEmpty(), "Response field '" + fieldName
                    + "' should not be empty. Actual map size: '" + mapValue.size() + "'.");
        }

        logger.info("Response field '" + yellow(fieldName) + "' is not empty.");
    }

    public void assertResponseListIsNotEmpty() {
        List<?> responseList = getResponseBodyAsList();

        assertFalse(responseList.isEmpty(), "Response list should not be empty. Actual size: '"
                + responseList.size() + "'.");

        logger.info("Response list is not empty.");
    }

    public void assertResponseListSizeEquals(String expectedSize) {
        int expected = Integer.parseInt(expectedSize);
        List<?> responseList = getResponseBodyAsList();
        int actual = responseList.size();

        assertEquals(expected, actual, "Response list size should match expected value. Expected: '"
                + expected + "', actual: '" + actual + "'.");

        logger.info("Response list size matched expected value: '" + green(expectedSize) + "'.");
    }

    private List<?> getResponseBodyAsList() {
        Object responseBody = apiContext.getResponse()
                .jsonPath()
                .get("$");

        return assertInstanceOf(List.class, responseBody, "Response body should be a JSON array. Actual type: '"
                + getActualType(responseBody) + "'.");
    }

    private String getActualType(Object actualValue) {
        if (actualValue == null) {
            return "null";
        }

        return actualValue.getClass().getSimpleName();
    }

}
