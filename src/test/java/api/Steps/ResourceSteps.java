package api.Steps;

import api.Utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ResourceSteps {
    private ApiClient apiClient = ApiClient.getInstance();

    @Step("Get resource by ID: {resourceId}")
    public Response getResourceById(int resourceId) {
        return apiClient.get("/unknown/" + resourceId);
    }

    @Step("Get resource by ID: {page}")
    public Response getListResource(int page) {
        return apiClient.get("/unknown/" + page);
    }

    @Step("Get resource by ID: {resourceId}")
    public Response getSingleResourceNotFound(int resourceId) {
        return apiClient.get("/unknown/" + resourceId);

    }
}
