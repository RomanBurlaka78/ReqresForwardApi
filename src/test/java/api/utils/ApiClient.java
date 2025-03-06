package api.utils;


import api.filters.FilterExperement;
import api.specifications.ApiSpecifications;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private static ApiClient instance;

    static {
        RestAssured.filters(new FilterExperement());
    }

    private ApiClient() {
        ApiSpecifications.requestSpec();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public Response get(String apiEndPoint) {
        return given().get(apiEndPoint);
    }

    public Response post(String apiEndPoint, String body) {
        return given().body(body).post(apiEndPoint);
    }

    public Response put(String apiEndPoint, String body) {
        return given().body(body).put(apiEndPoint);
    }

    public Response patch(String apiEndPoint, String body) {
        return given().body(body).patch(apiEndPoint);
    }

    public Response delete(String apiEndPoint) {
        return given().delete(apiEndPoint);
    }
}