package api.steps;

import api.utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DelayedSteps extends DataManager{

    private ApiClient apiClient = ApiClient.getInstance();

    @Step("Get response : {delay}")
    public Response getDelayedResponse(String apiEndPoint, int delay) {
        return apiClient.get(generateBodyFromStringAndInt(apiEndPoint, delay));

    }
}
