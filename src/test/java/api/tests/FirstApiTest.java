package api.tests;

import api.base.Specifications;
import api.pojo.CreateSingleUserPojo;
import api.pojo.ResponseSingleUserPojo;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class FirstApiTest {
    private static final String URL = "https://reqres.in";

    @Test
    public void testGetListUsers() {
        Specifications specifications = new Specifications();
        specifications.installSpec();
        Response response = given()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        //Should be assertions
    }


    @Test
    public void testSingleUsers() {
        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users/2")
                .then().log().all()
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue())
                .statusCode(200)
                .extract().response();

        //Should be assertions
    }

    @Test
    public void testSingleUserNotFound() {
        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users/23")
                .then().log().all()
                .statusCode(404)
                .extract().response();

        //Should be assertions
    }

    @Test
    public void testPostUser() {
        CreateSingleUserPojo postBody = new CreateSingleUserPojo("morpheus", "leader");

        ResponseSingleUserPojo response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .body(postBody)
                .post(URL + "/api/users")
                .then().log().all()
                .body("name", notNullValue())
                .statusCode(201)
                .extract().as(ResponseSingleUserPojo.class);

        //Should be assertions

    }

    @Test
    public void testPostUserResponse() {
        ResponseSingleUserPojo postBody = new ResponseSingleUserPojo("morpheus", "leader");

        ResponseSingleUserPojo response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .body(postBody)
                .post(URL + "/api/users")
                .then().log().all()
                .body("name", notNullValue())
                .statusCode(201)
                .extract().as(ResponseSingleUserPojo.class);

        //Should be assertions

    }


}
