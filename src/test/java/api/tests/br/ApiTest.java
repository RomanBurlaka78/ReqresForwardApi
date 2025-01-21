package api.tests.br;

import api.base.Specifications;
import api.pojo.CreateSingleUserPojo;
import api.pojo.DataListUsers;
import api.pojo.SingleUserPojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Epic("Api tests")
public class ApiTest {
    Specifications specifications = new Specifications();

    @Test
    @Story("Get response")
    @Description("Get list Of Users")
    public void testGetListOfUsers() {
        specifications.installSpec();

        List<Object> itemList = given()
                .filter(new AllureRestAssured())
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("data");

        LinkedHashMap<Object, Object> listHashMapUsers = new LinkedHashMap<>((Map<?, ?>) itemList.get(0));

        Assert.assertEquals(listHashMapUsers.get("id"), 7);
        Assert.assertEquals(listHashMapUsers.get("email"), "michael.lawson@reqres.in");
        Assert.assertEquals(listHashMapUsers.get("first_name"), "Michael");
        Assert.assertEquals(listHashMapUsers.get("last_name"), "Lawson");
        Assert.assertEquals(listHashMapUsers.get("avatar"), "https://reqres.in/img/faces/7-image.jpg");


    }


    @Test
    @Story("Get response")
    @Description("Get list of users with deserialization Pojo class")
    public void testGetListUserPojo() {
        specifications.installSpec();
        List<DataListUsers> itemList = given()
                .filter(new AllureRestAssured())
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", DataListUsers.class);

        Assert.assertEquals(itemList.get(0).id, 7);

        itemList.stream().forEach(System.out::println);

    }

    @Test
    @Story("Get response")
    @Description("Get information about single user")
    public void testSingleUsers() {
        Response response = given()
                .filter(new AllureRestAssured())
                .when()
                .get("/api/users/2")
                .then()
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue())
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = new JsonPath(response.asString());
        System.out.println(jsonPath);

    }

    @Test
    @Story("Get response")
    @Description("Get information about single user not found")
    public void testSingleUserNotFound() {
        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .get("/api/users/23")
                .then().log().all()
                .statusCode(404)
                .extract().response();

        //Should be assertions
    }

    @Test
    @Story("Post response")
    @Description("Create  single user")
    public void testPostUser() {
        CreateSingleUserPojo postBody = new CreateSingleUserPojo("morpheus", "leader");

        SingleUserPojo response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .body(postBody)
                .post("/api/users")
                .then().log().all()
                .body("name", notNullValue())
                .statusCode(201)
                .extract().as(SingleUserPojo.class);

        //Should be assertions

    }



}
