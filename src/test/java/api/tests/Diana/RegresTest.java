package api.tests.Diana;

import api.base.Specifications;
import api.pojo.PojoResource;
import api.pojo.Registration;
import api.pojo.SingleUserPojo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RegresTest {

    private static final String URL = "https://reqres.in";
    Specifications spec = new Specifications();
    protected SingleUserPojo reqwestSUP = new SingleUserPojo("morpheus", "leader", "2025-01-09T12:34:34.000Z");
    protected Registration registration = new Registration("eve.holt@reqres.in", "pistol");


    @Test
    public void testVerifySingleUser() {
        spec.installSpec();

        int singleUsers = given()
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .body("data.first_name", equalTo("Janet"))
                .body("data.last_name", equalTo("Weaver"))
                .extract().body().jsonPath().getInt("data.id");

          Assert.assertEquals(singleUsers, 2);

//        Assert.assertEquals(singleUsers.("data.first_name"), "Janet");
//        Assert.assertEquals(singleUsers.jsonPath().getString("data.last_name"), "Weaver");
//        Assert.assertEquals(singleUsers.jsonPath().getString("data.email"), "janet.weaver@reqres.in");

    }

    @Test
    public void testSingleUserNotExist() {
        spec.installSpec();
        String response = given()
                .when()
                .get("/api/users/23")
                .then()
                .statusCode(404)
                .extract().asString();

        Assert.assertEquals(response, "{}");

    }

    @Test
    public void testGetListOfResource() {
        spec.installSpec();

        List<PojoResource> resources = given()
                .when()
                .get("/api/unknown")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", PojoResource.class);

        Assert.assertEquals(resources.get(2).color, "#BF1932");
    }

    @Test
    public void testGetSingleResourceId() {
        spec.installSpec();

        int resp = given()
                .when()
                .get("/api/unknown/2")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getInt("data.id");

        Assert.assertEquals(resp, 2);
    }

    @Test
    public void testSingleResourceIdNotExist() {
        spec.installSpec();

        String response = given()
                .when()
                .get("/api/unknown/23")
                .then()
                .statusCode(404)
                .extract().asString();

        Assert.assertEquals(response,"{}");
    }

    @Test
    public void testCreateUser() {
        spec.installSpec();

        SingleUserPojo response = given()
                .when()
                .body(reqwestSUP)
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(response.getName(), "morpheus");
        Assert.assertEquals(response.getJob(), "leader");
    }

    @Test
    public void testUpdateUser() {

        spec.installSpec();
        reqwestSUP.setJob("zion resident");

        SingleUserPojo response = given()
                .when()
                .body(reqwestSUP)
                .put("/api/users/2")
                .then()
                .statusCode(200)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(response.getJob(), "zion resident");
    }

    @Test
    public void testUpdateUserPatch() {

        spec.installSpec();
        reqwestSUP.setCreatedAt("2025-01-09T12:30:37.398Z");

        SingleUserPojo response = given()
                .when()
                .body(reqwestSUP)
                .patch("/api/users/2")
                .then()
                .statusCode(200)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(response.getCreatedAt(), reqwestSUP.getCreatedAt());
    }
    @Test
    public void testDeleteUser() {

        spec.installSpec();

        Response response = given()
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204)
                .extract().response();

        Assert.assertTrue(response.body().asString().isEmpty());
    }

    @Test
    public void testRegisterUser() {

        spec.installSpec();
        String token = "QpwL5tke4Pnpja7X4";
        //int id = 4;
        String response = given()
                .when()
                .body(registration)
                .post("/api/register")
                .then()
                .statusCode(200)
                .extract().response().jsonPath().getString("token");

        Assert.assertEquals(response, token);
      //  Assert.assertEquals(response, id); ???
    }

    @Test
    public void testRegisterUserUnsuccessful() {

        spec.installSpec();
        registration.setEmail("sydney@fife");
        registration.setPassword("");

        String response = given()
                .when()
                .body(registration)
                .post("/api/register")
                .then()
                .statusCode(400)
                .extract().body().jsonPath().getString("error");

        Assert.assertEquals(response, "Missing password");

    }

    @Test
    public void testUserLoginRegister() {

        spec.installSpec();
        registration.setEmail("eve.holt@reqres.in");
        registration.setPassword("cityslicka");

        String response = given()
                .when()
                .body(registration)
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getString("token");

        Assert.assertEquals(response, "QpwL5tke4Pnpja7X4");
    }

    @Test
    public void testUserLoginRegisterUnsuccessful() {

        spec.installSpec();
        registration.setEmail("peter@klaven");
        registration.setPassword("");

        Response response = given()
                .when()
                .body(registration)
                .post("/api/login")
                .then()
                .statusCode(400)
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("error"), "Missing password");
    }

    @Test
    public void testDelayedResponse() {

        spec.installSpec();

        Response response = given()
                .when()
                .get("/api/users?delay=3")
                .then()
                .statusCode(200)
                .time(lessThan(10000L))
                .extract().response();

    }

}
