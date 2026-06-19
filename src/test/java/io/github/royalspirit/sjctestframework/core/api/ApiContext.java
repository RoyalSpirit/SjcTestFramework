package io.github.royalspirit.sjctestframework.core.api;

import io.restassured.response.Response;

public class ApiContext {

    private Response response;

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        if (response == null) {
            throw new IllegalStateException("API response is empty. Send API request before checking response.");
        }

        return response;
    }

}
