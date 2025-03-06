package api.Utils;

import api.Specifications.ApiSpecifications;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiClient {

    private static ApiClient instance;
    private RequestSpecification requestSpecification;
    private static final Logger logger = LogManager.getLogger(ApiClient.class);

    private ApiClient() {
        logger.info("Creating ApiClient");
        requestSpecification = RestAssured.given()
                // Используем RequestSpecification, установленный в BaseTest
                .spec(ApiSpecifications.requestSpec())
                .contentType("application/json");
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            logger.info("Initializing new ApiClient instance");
            instance = new ApiClient();
        }
        return instance;
    }

    public Response get(String path) {
        logger.info("Executing GET request to: " + path);
        return requestSpecification.get(path);
    }

    public Response post(String path, String body) {
        logger.info("Executing POST request to: " + path);
        return requestSpecification.body(body).post(path);
    }

    public Response put(String path, String body) {
        logger.info("Executing PUT request to: " + path);
        return requestSpecification.body(body).put(path);
    }

    public Response patch(String path, String body) {
        logger.info("Executing PUT request to: " + path);
        return requestSpecification.body(body).patch(path);
    }

    public Response delete(String path) {
        logger.info("Executing DELETE request to: " + path);
        return requestSpecification.delete(path);
    }
}