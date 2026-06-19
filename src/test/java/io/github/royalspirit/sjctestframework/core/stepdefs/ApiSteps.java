package io.github.royalspirit.sjctestframework.core.stepdefs;

import io.cucumber.java.ru.Когда;
import io.github.royalspirit.sjctestframework.core.api.ApiContext;
import io.github.royalspirit.sjctestframework.core.api.ApiRequestExecutor;
import io.github.royalspirit.sjctestframework.core.api.ApiResponseAssertions;

public class ApiSteps {

    private final ApiContext apiContext = new ApiContext();
    private final ApiRequestExecutor apiRequestExecutor = new ApiRequestExecutor();
    private final ApiResponseAssertions apiResponseAssertions = new ApiResponseAssertions(apiContext);

    @Когда("^(?:api|API) \\((?:sends GET request|отправляет GET запрос)\\) (?:to endpoint|на endpoint) \"([^\"]*)\"$")
    public void apiSendsGetRequest(String endpoint) {
        apiContext.setResponse(apiRequestExecutor.sendGetRequest(endpoint));
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

}
