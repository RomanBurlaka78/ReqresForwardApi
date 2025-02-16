package api.steps;

import api.utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DelayedSteps {
    private ApiClient apiClient = ApiClient.getInstance();
    @Step("Get response : {delay}")
    public Response getDelayedResponse(int delay) {
        return apiClient.get("users?delay=" + delay);
    }
}
