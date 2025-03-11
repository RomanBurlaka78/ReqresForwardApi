package restAssuredConfigurations.specifications;

import api.apiEndPoints.CommonEndPoints;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class GlobalRequestSpecification {

    public static void setupGlobalRequestSpecification() {
        RequestSpecification requestSpecification1 = new RequestSpecBuilder()
                .setBaseUri(CommonEndPoints.BASE_URL)
                .setBasePath(CommonEndPoints.BASE_PATH)
                .addFilter(new AllureRestAssured())
                .build().contentType("application/json");
        RestAssured.requestSpecification=requestSpecification1;
    }

}