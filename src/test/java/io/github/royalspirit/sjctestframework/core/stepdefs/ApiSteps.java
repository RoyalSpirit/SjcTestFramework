package io.github.royalspirit.sjctestframework.core.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Когда;
import io.github.royalspirit.sjctestframework.core.api.ApiContext;
import io.github.royalspirit.sjctestframework.core.api.ApiRequestExecutor;
import io.github.royalspirit.sjctestframework.core.api.ApiResponseAssertions;

import java.util.Map;

public class ApiSteps {

    private final ApiContext apiContext = new ApiContext();
    private final ApiRequestExecutor apiRequestExecutor = new ApiRequestExecutor();
    private final ApiResponseAssertions apiResponseAssertions = new ApiResponseAssertions(apiContext);

    @Когда("^(?:api|API) \\((?:sends GET request|отправляет GET запрос)\\) (?:to endpoint|на endpoint) \"([^\"]*)\"$")
    public void apiSendsGetRequest(String endpoint) {
        apiContext.setResponse(apiRequestExecutor.sendGetRequest(endpoint));
    }

    @Когда("^(?:api|API) \\((?:sends GET request|отправляет GET запрос)\\) (?:to endpoint|на endpoint) \"([^\"]*)\" (?:with query params|с параметрами):$")
    public void apiSendsGetRequestWithQueryParams(String endpoint, DataTable table) {
        Map<String, String> queryParams = table.asMap(String.class, String.class);
        apiContext.setResponse(apiRequestExecutor.sendGetRequest(endpoint, queryParams));
    }

    @Когда("^(?:api|API) \\((?:sends GET request|отправляет GET запрос)\\) (?:to endpoint|на endpoint) \"([^\"]*)\" (?:with headers|с headers|с заголовками):$")
    public void apiSendsGetRequestWithHeaders(String endpoint, DataTable table) {
        Map<String, String> headers = table.asMap(String.class, String.class);
        apiContext.setResponse(apiRequestExecutor.sendGetRequestWithHeaders(endpoint, headers));
    }

    @Когда("^(?:api|API) \\((?:sends POST request|отправляет POST запрос)\\) (?:to endpoint|на endpoint) \"([^\"]*)\" (?:with JSON body|с JSON body):$")
    public void apiSendsPostRequestWithJsonBody(String endpoint, String jsonBody) {
        apiContext.setResponse(apiRequestExecutor.sendPostRequest(endpoint, jsonBody));
    }

    @Когда("^(?:api|API) \\((?:sends POST request|отправляет POST запрос)\\) (?:to endpoint|на endpoint) \"([^\"]*)\" (?:with body|с body|с телом):$")
    public void apiSendsPostRequestWithBody(String endpoint, DataTable table) {
        Map<String, String> bodyFields = table.asMap(String.class, String.class);
        apiContext.setResponse(apiRequestExecutor.sendPostRequest(endpoint, bodyFields));
    }

    @Когда("^(?:api|API) \\((?:sends PUT request|отправляет PUT запрос)\\) (?:to endpoint|на endpoint) \"([^\"]*)\" (?:with JSON body|с JSON body):$")
    public void apiSendsPutRequestWithJsonBody(String endpoint, String jsonBody) {
        apiContext.setResponse(apiRequestExecutor.sendPutRequest(endpoint, jsonBody));
    }

    @Когда("^(?:api|API) \\((?:sends PATCH request|отправляет PATCH запрос)\\) (?:to endpoint|на endpoint) \"([^\"]*)\" (?:with JSON body|с JSON body):$")
    public void apiSendsPatchRequestWithJsonBody(String endpoint, String jsonBody) {
        apiContext.setResponse(apiRequestExecutor.sendPatchRequest(endpoint, jsonBody));
    }

    @Когда("^(?:api|API) \\((?:sends DELETE request|отправляет DELETE запрос)\\) (?:to endpoint|на endpoint) \"([^\"]*)\"$")
    public void apiSendsDeleteRequest(String endpoint) {
        apiContext.setResponse(apiRequestExecutor.sendDeleteRequest(endpoint));
    }

    @Когда("^(?:api|API) \\((?:checks response status code|проверяет статус ответа)\\) (?:equals|равен) \"([^\"]*)\"$")
    public void apiChecksResponseStatusCode(String expectedStatusCode) {
        apiResponseAssertions.assertStatusCode(expectedStatusCode);
    }

    @Когда("^(?:api|API) \\((?:checks response content type|проверяет content type ответа)\\) (?:contains|содержит) \"([^\"]*)\"$")
    public void apiChecksResponseContentType(String expectedContentType) {
        apiResponseAssertions.assertContentTypeContains(expectedContentType);
    }

    @Когда("^(?:api|API) \\((?:checks response header|проверяет header ответа)\\) \"([^\"]*)\" (?:contains|содержит) \"([^\"]*)\"$")
    public void apiChecksResponseHeader(String headerName, String expectedHeaderValue) {
        apiResponseAssertions.assertHeaderContains(headerName, expectedHeaderValue);
    }

    @Когда("^(?:api|API) \\((?:checks response header|проверяет header ответа)\\) \"([^\"]*)\" (?:equals|равен) \"([^\"]*)\"$")
    public void apiChecksResponseHeaderEquals(String headerName, String expectedHeaderValue) {
        apiResponseAssertions.assertHeaderEquals(headerName, expectedHeaderValue);
    }

    @Когда("^(?:api|API) \\((?:checks response field value|проверяет значение поля ответа)\\) \"([^\"]*)\" (?:equals|равно) \"([^\"]*)\"$")
    public void apiChecksResponseFieldValue(String fieldName, String expectedValue) {
        apiResponseAssertions.assertFieldValueEquals(fieldName, expectedValue);
    }

    @Когда("^(?:api|API) \\((?:checks response field value|проверяет значение поля ответа)\\) \"([^\"]*)\" (?:contains|содержит) \"([^\"]*)\"$")
    public void apiChecksResponseFieldValueContains(String fieldName, String expectedValue) {
        apiResponseAssertions.assertFieldValueContains(fieldName, expectedValue);
    }

    @Когда("^(?:api|API) \\((?:checks response field is not empty|проверяет что поле ответа не пустое)\\) \"([^\"]*)\"$")
    public void apiChecksResponseFieldIsNotEmpty(String fieldName) {
        apiResponseAssertions.assertFieldIsNotEmpty(fieldName);
    }

    @Когда("^(?:api|API) \\((?:checks response field is null|проверяет что поле ответа null)\\) \"([^\"]*)\"$")
    public void apiChecksResponseFieldIsNull(String fieldName) {
        apiResponseAssertions.assertFieldIsNull(fieldName);
    }

    @Когда("^(?:api|API) \\((?:checks response field does not exist|проверяет что поле ответа отсутствует)\\) \"([^\"]*)\"$")
    public void apiChecksResponseFieldDoesNotExist(String fieldName) {
        apiResponseAssertions.assertFieldDoesNotExist(fieldName);
    }

    @Когда("^(?:api|API) \\((?:checks response list is not empty|проверяет что список в ответе не пустой)\\)$")
    public void apiChecksResponseListIsNotEmpty() {
        apiResponseAssertions.assertResponseListIsNotEmpty();
    }

    @Когда("^(?:api|API) \\((?:checks response list size|проверяет размер списка в ответе)\\) (?:equals|равен) \"([^\"]*)\"$")
    public void apiChecksResponseListSize(String expectedSize) {
        apiResponseAssertions.assertResponseListSizeEquals(expectedSize);
    }

}
