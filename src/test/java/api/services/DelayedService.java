package api.services;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DelayedService extends BaseService {

    @Step("Get response : {delay}")
    public Response getDelayedResponse(String apiEndPoint, int delay) {
        return get(apiEndPoint+delay);

    }
}
