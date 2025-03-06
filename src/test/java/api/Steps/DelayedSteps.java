package api.Steps;

import api.Utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class DelayedSteps {
    private ApiClient apiClient = ApiClient.getInstance();
    @Step("Get response : {delay}")
    public Response getDelayedResponse(int delay) {
        return apiClient.get("users?delay=" + delay);
    }
}
