package io.github.royalspirit.sjctestframework.core.api;

import io.github.royalspirit.sjctestframework.core.GetPropertyValues;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ApiSpecification {

    private ApiSpecification() {
    }

    public static RequestSpecification requestSpecification() {
        String baseUrl = GetPropertyValues.getRequiredProperty("api.base.url");
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }

}
