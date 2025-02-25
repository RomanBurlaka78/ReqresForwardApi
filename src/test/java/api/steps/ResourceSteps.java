package api.steps;

import api.base.ApiEndPoints;
import api.utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ResourceSteps extends DataManager {
    private ApiClient apiClient = ApiClient.getInstance();

    @Step("Get resource by ID: {resourceId}")
    public Response getResourceById(String apiEndPoint, int resourceId) {
        return apiClient.get(generateBodyFromStringAndInt(apiEndPoint, resourceId));

    }

    @Step("Get list resource")
    public Response getListResource(String apiEndPoint) {
       return apiClient.get(apiEndPoint);
    }

}