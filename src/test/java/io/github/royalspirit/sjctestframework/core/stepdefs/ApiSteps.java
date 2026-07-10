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

    @Когда("^(?:api|API) \\((?:sends POST request|отправляет POST запрос)\\) (?:to endpoint|на endpoint) \"([^\"]*)\" (?:with JSON body|с JSON body):$")
    public void apiSendsPostRequestWithJsonBody(String endpoint, String jsonBody) {
        apiContext.setResponse(apiRequestExecutor.sendPostRequest(endpoint, jsonBody));
    }

    @Когда("^(?:api|API) \\((?:checks response status code|проверяет статус ответа)\\) (?:equals|равен) \"([^\"]*)\"$")
    public void apiChecksResponseStatusCode(String expectedStatusCode) {
        apiResponseAssertions.assertStatusCode(expectedStatusCode);
    }

    @Когда("^(?:api|API) \\((?:checks response field value|проверяет значение поля ответа)\\) \"([^\"]*)\" (?:equals|равно) \"([^\"]*)\"$")
    public void apiChecksResponseFieldValue(String fieldName, String expectedValue) {
        apiResponseAssertions.assertFieldValueEquals(fieldName, expectedValue);
    }

    @Когда("^(?:api|API) \\((?:checks response field is not empty|проверяет что поле ответа не пустое)\\) \"([^\"]*)\"$")
    public void apiChecksResponseFieldIsNotEmpty(String fieldName) {
        apiResponseAssertions.assertFieldIsNotEmpty(fieldName);
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
