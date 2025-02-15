package api.specifications;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.expect;
import static io.restassured.filter.log.LogDetail.ALL;

public class ApiSpecifications {

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api")
                .addFilter(new AllureRestAssured())
                .log(ALL)
                .build();
    }

    public static ResponseSpecification responseSpec() {
        return  new ResponseSpecBuilder()
                .log(ALL)
                .build();
    }

    public static ResponseSpecification responseSpec(int statusCode) {
        return expect()
                .statusCode(statusCode)
                .log().all();
    }

    public static void installSpecification(RequestSpecification requestSpec) {
        RestAssured.requestSpecification = requestSpec();
        RestAssured.responseSpecification = responseSpec();
    }
}