package api.utils;


import api.specifications.ApiSpecifications;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiClient {

    private static ApiClient instance;
    //private RequestSpecification requestSpec;
    private static final Logger logger = LogManager.getLogger(ApiClient.class);

    private ApiClient() {
        logger.info("Creating ApiClient instance");
        RestAssured.requestSpecification = ApiSpecifications.requestSpec();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public Response get(String apiEndPoint) {
        logger.info("Executing GET request to: " + apiEndPoint);
        return RestAssured.requestSpecification.get(apiEndPoint);
    }

    public Response post(String apiEndPoint, String body) {
        logger.info("Executing POST request to: " + apiEndPoint);
        return RestAssured.requestSpecification.body(body).post(apiEndPoint);
    }

    public Response put(String apiEndPoint, String body) {
        logger.info("Executing PUT request to: " + apiEndPoint);
        return RestAssured.requestSpecification.body(body).put(apiEndPoint);
    }

    public Response patch(String apiEndPoint, String body) {
        logger.info("Executing PUT request to: " + apiEndPoint);
        return RestAssured.requestSpecification.body(body).patch(apiEndPoint);
    }

    public Response delete(String apiEndPoint) {
        logger.info("Executing DELETE request to: " + apiEndPoint);
        return RestAssured.requestSpecification.delete(apiEndPoint);
    }
}