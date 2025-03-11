package testsUtils.services;


import restAssuredConfigurations.filters.HttpDitalesReader;
import restAssuredConfigurations.specifications.GlobalRequestSpecification;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public abstract class BaseService {


    static {
        GlobalRequestSpecification.setupGlobalRequestSpecification();
        RestAssured.filters(new HttpDitalesReader());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public Response get(String apiEndPoint) {
        return given().get(apiEndPoint);
    }

    public Response post(String apiEndPoint, Object object) {
        return given().body(object).post(apiEndPoint);
    }

    public Response put(String apiEndPoint, Object object) {
        return given().body(object).put(apiEndPoint);
    }

//    public Response patch(String apiEndPoint, String body) {
//        return given().body(body).patch(apiEndPoint);
//    }

    public Response delete(String apiEndPoint) {
        return given().delete(apiEndPoint);
    }
}