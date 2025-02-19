package api.steps;

import api.utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ResourceSteps {
    private ApiClient apiClient = ApiClient.getInstance();

    @Step("Get resource by ID: {resourceId}")
    public Response getResourceById(int resourceId) {
        return apiClient.get("/unknown/" + resourceId);

    }

    @Step("Get list resource")
    public Response getListResource() {
       return apiClient.get("/unknown");
    }

}