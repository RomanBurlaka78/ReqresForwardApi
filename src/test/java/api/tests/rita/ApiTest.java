package api.tests.rita;

import api.base.BaseTest;
import api.pojo.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.rootPath;
import static org.hamcrest.Matchers.*;

//@Epic("Api tests")
@Epic("tests version Margarita")
@Feature("User Management")
public class ApiTest extends BaseTest {

    @Test(priority = 0)
    @Owner("Rita")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Get response")
    @Description("Get list of users")
    @Link(name = "Reqres API Docs", url = "https://reqres.in/")
    @Issue("UM-001")
    @TmsLink("TMS-456")
    public void testGetListUsers() {

        step("Step 1. Send GET-request", () -> {
        Response response =
                //given(requestSpec.queryParam("page", 2), responseSpec)
                given().
                queryParam("page", 2)
                .get("/api/users");

        Allure.addAttachment("Response body", response.asString());

        response.then()
                .statusCode(200)
                .body("data", hasSize(greaterThan(0)));
        });

        step("Step 2. Extract users", () -> {
        List<?> users =
                //given(requestSpec.queryParam("page", 2), responseSpec)
                given()
                .queryParam("page", 2)
                .get("/api/users")
                .then()
                .extract().body().jsonPath().getList("data");

        Allure.addAttachment("User list", users.toString());

        Assert.assertEquals(users.size(), 6);
        });
    }

    @Test(priority = 1)
    @Owner("Rita")
    @Severity(SeverityLevel.NORMAL)
    @Story("Get response")
    @Description("Get single user")
    @Link(name = "Reqres API Docs", url = "https://reqres.in/")
    @Issue("UM-002")
    @TmsLink("TMS-457")
    public void testGetSingleUser() {

        int id = 2;

        UserPojo response = step("Step 1. Send GET-request to https://reqres.in/api/users/" + id, () -> {
        //return given(requestSpec.pathParam("id", id), responseSpec)
            return given()
                        .pathParam("id", id)
                        .get("/api/users/{id}")
                    .then()
                        .statusCode(200)
                        .extract().body().jsonPath().getObject("data", UserPojo.class);
        });

        step("Step 2. Verify response", () -> {
            Assert.assertEquals(id, response.getId());
            Assert.assertEquals("Janet", response.getFirst_name());
            Assert.assertEquals("Weaver", response.getLast_name());
            Assert.assertEquals("janet.weaver@reqres.in", response.getEmail());
            Assert.assertEquals("https://reqres.in/img/faces/2-image.jpg", response.getAvatar());
        });

    }

    @Test(priority = 2)
    @Owner("Rita")
    @Severity(SeverityLevel.NORMAL)
    @Story("Get response")
    @Description("Get invalid user by ID")
    @Link(name = "Reqres API Docs", url = "https://reqres.in/")
    @Issue("UM-003")
    @TmsLink("TMS-458")
    public void testGetInvalidUser() {

        int id = 23;

        Response response = step("Step 1. Send GET-request with invalid id" + id, () ->{
        //return given(requestSpec.pathParam("id", id), responseSpec)
        return given()
                .pathParam("id", id)
                .get("/api/users/{id}")
                .then()
                .statusCode(404)
                .extract().response();
        });

        step("Step 2. Verify response", () -> {
            Allure.addAttachment("Invalid user", response.asString());

            Assert.assertEquals(response.getBody().asString(), "{}");
        });
    }

    @Test(priority = 5)
    @Owner("Rita")
    @Severity(SeverityLevel.NORMAL)
    @Story("Post response")
    @Description("Create a user")
    @Link(name = "Reqres API Docs", url = "https://reqres.in/")
    @Issue("UM-004")
    @TmsLink("TMS-459")
    public void testPostCreateUser() {

        String endpoint = "/api/users";
        String name = "morpheus";
        String job = "leader";

        CreateSingleUserPojo postBody = new CreateSingleUserPojo(name, job);

        SingleUserPojo response = step("Step 1. Send POST-request to" + endpoint, () -> {
            //return given(requestSpec.body(postBody), responseSpec)
            return given()
                        .body(postBody)
                        .post(endpoint)
                    .then()
                        .statusCode(201)
                        .extract().as(SingleUserPojo.class);
        });

        step("Step 2. Verify user details: name - " + name + ", job - " + job, () -> {
            Allure.addAttachment("User details", response.toString());

            Assert.assertEquals(response.getName(), name);
            Assert.assertEquals(response.getJob(), job);
        });

    }

    @Test(dependsOnMethods = "testPostCreateUser")
    @Owner("Rita")
    @Severity(SeverityLevel.NORMAL)
    @Story("Put response")
    @Description("Update user")
    @Link(name = "Reqres API Docs", url = "https://reqres.in/")
    @Issue("UM-005")
    @TmsLink("TMS-460")
    public void testPutUser() {

        String name = "neo";
        String job = "the one";
        String endpoint = "api/users/2";

        SingleUserPojo updateUser = new SingleUserPojo(name, job);

        SingleUserPojo response = step("Step 1. Send PUT-request to endpoint: " + endpoint, () -> {
            //return given(requestSpec.body(updateUser), responseSpec)
            return given()
                        .body(updateUser)
                        .put(endpoint)
                    .then()
                        .statusCode(200)
                        .extract().as(SingleUserPojo.class);
                });

        step("Step 2. Verify user details: name - " + name + ", job - " + job, () -> {
            Allure.addAttachment("User details", response.toString());

            Assert.assertEquals(response.getName(), name);
            Assert.assertEquals(response.getJob(), job);
        });

    }

    @Test(priority = 6)
    @Owner("Rita")
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Patch response")
    @Description("Update user")
    @Link(name = "Reqres API Docs", url = "https://reqres.in/")
    @Issue("UM-006")
    @TmsLink("TMS-461")
    public void testPatchUser() {

        String name = "morpheus";
        String job = "zion resident";
        String endpoint = "api/users/2";

        SingleUserPojo updateUser = new SingleUserPojo(name, job);

        SingleUserPojo response = step("Step 1. Send PATCH-request to endpoint: " + endpoint, () -> {
            //return given(requestSpec.body(updateUser), responseSpec)
            return given()
                    .body(updateUser)
                    .patch(endpoint)
                    .then()
                    .statusCode(200)
                    .extract().as(SingleUserPojo.class);
        });

        step("Step 2. Verify user details: name - " + name + ", job - " + job, () -> {
            Allure.addAttachment("User details", response.toString());

            Assert.assertEquals(response.getName(), name);
            Assert.assertEquals(response.getJob(), job);
        });
    }

    @Test(priority = 7)
    @Owner("Rita")
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Delete response")
    @Description("Delete a user")
    @Link(name = "Reqres API Docs", url = "https://reqres.in/")
    @Issue("UM-007")
    @TmsLink("TMS-462")
    public void testDeleteUser() {

        String endpoint = "api/users/2";

        step("Step 1. Send DELETE-request to endpoint: " + endpoint, () -> {
            //given(requestSpec)
            given()
            .delete(endpoint)
            .then()
            .statusCode(204)
            .body(isEmptyString())
            .extract().response();
        });
    }

    @Test(priority = 8)
    @Owner("Rita")
    @Severity(SeverityLevel.NORMAL)
    @Story("Post response")
    @Description("Register a user")
    @Link(name = "Reqres API Docs", url = "https://reqres.in/")
    @Issue("UM-008")
    @TmsLink("TMS-463")
    public void testRegisterUser() {

        String email = "eve.holt@reqres.in";
        String pass = "pistol";
        String endpoint = "/api/register";

        Registration registerUser = new Registration(email, pass);

        RegisterPojo response = step("Step 1. Send POST-request", () -> {
                //return given(requestSpec.body(registerUser), responseSpec)
                return given()
                        .body(registerUser)
                        .post(endpoint)
                        .then()
                        .statusCode(200)
                        .extract().body().jsonPath().getObject(rootPath, RegisterPojo.class);
        });

        step("Step 2. Verify User details", () -> {
            Assert.assertEquals(response.getId(), 4);
            Assert.assertEquals(response.getToken(), "QpwL5tke4Pnpja7X4");
        });
    }

    @Test(priority = 9)
    @Owner("Rita")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Post response")
    @Description("Login a user")
    @Link(name = "Reqres API Docs", url = "https://reqres.in/")
    @Issue("UM-009")
    @TmsLink("TMS-464")
    public void testLoginUser() {

        String email = "eve.holt@reqres.in";
        String pass = "cityslicka";
        String endpoint = "/api/login";

        Registration login = new Registration(email, pass);

        RegisterPojo response = step("Step 1. Send POST-request", () -> {
                //return given(requestSpec.body(login), responseSpec)
                return given().
                        body(login)
                        .post(endpoint)
                        .then()
                        .statusCode(200)
                        .extract().body().jsonPath().getObject(rootPath, RegisterPojo.class);
        });

        step("Step 2. Verify response", () -> {
            Assert.assertEquals(response.getToken(), "QpwL5tke4Pnpja7X4");
        });
    }

    @Test(priority = 10)
    @Owner("Rita")
    @Severity(SeverityLevel.NORMAL)
    @Story("Post response")
    @Description("Login a user without password")
    @Link(name = "Reqres API Docs", url = "https://reqres.in/")
    @Issue("UM-010")
    @TmsLink("TMS-465")
    public void testLoginUserWithoutPassword() {

        String email = "eve.holt@reqres.in";
        String endpoint = "/api/login";

        Registration login = new Registration(email, null);

        ErrorPojo response = step("Step 1. Send POST-request without password", () -> {
                //return given(requestSpec.body(login), responseSpec)
                return given()
                        .body(login)
                        .post(endpoint)
                        .then()
                        .statusCode(400)
                        .extract().body().jsonPath().getObject(rootPath, ErrorPojo.class);
        });

        step("Step 2. Verify the error", () -> {
            Allure.addAttachment("Error message", response.getError());

            Assert.assertEquals(response.getError(), "Missing password");
        });
    }

    @Test(priority = 3)
    @Owner("Rita")
    @Severity(SeverityLevel.NORMAL)
    @Story("Get response")
    @Description("Get single resource")
    @Link(name = "Reqres API Docs", url = "https://reqres.in/")
    @Issue(value = "UM-011")
    @TmsLink("TMS-466")
    public void testGetSingleResource() {

        int id = 2;
        String endpoint = "/api/unknown/{id}";

        ResourcePojo response = step("Step 1. Send GET-request to endpoint: " + endpoint + "with id = " + id, () -> {
                //return given(requestSpec.pathParam("id", id), responseSpec)
                return given()
                        .pathParam("id", id)
                        .get(endpoint)
                        .then()
                        .statusCode(200)
                        .extract().body().jsonPath().getObject("data", ResourcePojo.class);
        });

        step("Step 2. Verify user details", () -> {
            Assert.assertEquals(id, response.getId());
            Assert.assertEquals("fuchsia rose", response.getName());
            Assert.assertEquals(2001, response.getYear());
            Assert.assertEquals("#C74375", response.getColor());
            Assert.assertEquals("17-2031", response.getPantone_value());
        });
    }

    @Test(priority = 4)
    @Owner("Rita")
    @Severity(SeverityLevel.NORMAL)
    @Story("Get response")
    @Description("Get delayed response")
    @Link(name = "Reqres API Docs", url = "https://reqres.in/")
    @Issue(value = "UM-012")
    @TmsLink("TMS-467")
    public void testGetDelayedResponse() {

        String endpoint = "/api/users";
        int objects = 3;

        step("Step 1. Send GET-request to endpoint " + endpoint, () -> {
                //given(requestSpec.queryParam("delay", objects), responseSpec)
                given()
                        .queryParam("delay", objects)
                        .get(endpoint)
                        .then()
                        .statusCode(200)
                        .body("data", hasSize(greaterThan(0)));
        });

        step("Step 2. Extract the response", () -> {
        List<?> users =
                //iven(requestSpec.queryParam("delay", objects), responseSpec)
                given().queryParam("delay", objects)
                .get(endpoint)
                .then()
                .extract().body().jsonPath().getList("data");

        Assert.assertEquals(users.size(), 6);
        });
    }
}