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
    //private static final Logger logger = LogManager.getLogger(ApiClient.class);

//    static {
//        RestAssured.filters(new FilterExperement());
//    }

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
        //logger.info("Executing GET request to: " + apiEndPoint);
        return given().get(apiEndPoint);
    }

    public Response post(String apiEndPoint, String body) {
        //logger.info("Executing POST request to: " + apiEndPoint);
        return given().body(body).post(apiEndPoint);
    }

    public Response put(String apiEndPoint, String body) {
        //logger.info("Executing PUT request to: " + apiEndPoint);
        return given().body(body).put(apiEndPoint);
    }

    public Response patch(String apiEndPoint, String body) {
        //logger.info("Executing PUT request to: " + apiEndPoint);

        return given().body(body).patch(apiEndPoint);
    }

    public Response delete(String apiEndPoint) {
        //logger.info("Executing DELETE request to: " + apiEndPoint);

        return given().delete(apiEndPoint);
    }
}