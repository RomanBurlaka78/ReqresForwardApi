package api.specifications;

import api.base.ApiEndPoints;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.*;

public class ApiSpecifications {

    public static void requestSpec() {
        RequestSpecification requestSpecification1 = new RequestSpecBuilder()
                .setBaseUri(ApiEndPoints.BASE_URL)
                .setBasePath(ApiEndPoints.BASE_PATH)
                .addFilter(new AllureRestAssured())
                .build().contentType("application/json");
        RestAssured.requestSpecification=requestSpecification1;
    }

}