package api.services;


import api.restAssuredFilters.FilterExperement;
import api.specifications.ApiSpecifications;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public abstract class BaseService {


    static {
        ApiSpecifications.requestSpec();
        RestAssured.filters(new FilterExperement());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

//    private BaseService() {
//        ApiSpecifications.requestSpec();
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//    }

//    public static BaseService getInstance() {
//        if (instance == null) {
//            instance = new BaseService();
//        }
//        return instance;
//    }

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