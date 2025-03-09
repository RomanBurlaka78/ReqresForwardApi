package api.services;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ResourceService extends BaseService {

    @Step("Get resource by ID: {resourceId}")
    public Response getResourceById(String apiEndPoint, int resourceId) {
        return get(apiEndPoint + resourceId);

    }

    @Step("Get list resource")
    public Response getListResource(String apiEndPoint) {
       return get(apiEndPoint);
    }

}