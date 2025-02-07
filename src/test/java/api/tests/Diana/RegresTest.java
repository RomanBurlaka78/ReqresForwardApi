package api.tests.Diana;

import api.base.BaseTest;
import api.pojo.*;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("Api tests")
public class RegresTest extends BaseTest {
    protected SingleUserPojo reqwestSUP = new SingleUserPojo("morpheus", "leader", "2025-01-09T12:34:34.000Z");
    protected Registration registration = new Registration("eve.holt@reqres.in", "pistol");
    protected UserPojo singleuserP = new UserPojo(2, "janet.weaver@reqres.in", "Janet", "Weaver", "https://reqres.in/img/faces/2-image.jpg");


    @Test
    @Story("Get response")
    @Description("Get list Of Users")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testVerifySingleUser() {

        Allure.step("Step 1. Send GET - request to https://reqres.in/api/users/");
        UserPojo singleUsers = given(requestSpec.body(singleuserP), responseSpec)

                .get("/api/users/2")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject("data", UserPojo.class);

        Allure.step("Step 2. Verify response");
            Assert.assertEquals(singleUsers.getId(), 2);
            Assert.assertEquals(singleUsers.getFirst_name(), "Janet");
            Assert.assertEquals(singleUsers.getLast_name(), "Weaver");
            Assert.assertEquals(singleUsers.getEmail(), "janet.weaver@reqres.in");
            Assert.assertEquals(singleUsers.getAvatar(), "https://reqres.in/img/faces/2-image.jpg");

    }

    @Test
    @Story("Get response")
    @Description("Get list Of Users Not Found")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testSingleUserNotExist() {

        Allure.step("Step 1. Send GET - request with invalid id");
        String response = given(requestSpec,responseSpec)
                .get("/api/users/23")
                .then()
                .statusCode(404)
                .extract().asString();

        Allure.step("Step 2. Verify response");
        Assert.assertEquals(response, "{}");
    }

    @Test
    @Story("Get response")
    @Description("Get list Of resource")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testGetListOfResource() {

        Allure.step("Step 1. Send GET - request https://reqres.in");
        List<ResourcePojo> resources = given(requestSpec,responseSpec)
                .get("/api/unknown")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", ResourcePojo.class);

        Allure.step("Step 2. Verify details: color, id, name, year, pantone_value");
        Assert.assertEquals(resources.get(2).getColor(), "#BF1932");
        Assert.assertEquals(resources.get(2).getId(), 3);
        Assert.assertEquals(resources.get(2).getName(), "true red");
        Assert.assertEquals(resources.get(2).getPantone_value(), "19-1664");
        Assert.assertEquals(resources.get(2).getYear(), 2002);
    }

    @Test
    @Story("Get response")
    @Description("Get list Of resource id")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testGetSingleResourceId() {

        Allure.step("Step 1. Send GET - request https://reqres.in");
        ResourcePojo resp = given(requestSpec,responseSpec)
                .get("/api/unknown/2")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject("data", ResourcePojo.class);

        Allure.step("Step 2. Verify id details: color, name, year, pantone_value");
        Assert.assertEquals(resp.getId(), 2);
        Assert.assertEquals(resp.getName(), "fuchsia rose");
        Assert.assertEquals(resp.getYear(), 2001);
        Assert.assertEquals(resp.getColor(), "#C74375");
        Assert.assertEquals(resp.getPantone_value(), "17-2031");
    }

    @Test
    @Story("Get response")
    @Description("Get list Of resource id not found")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testSingleResourceIdNotExist() {

        Allure.step("Step 1. Send GET - request with invalid id");
        String response = given(requestSpec,responseSpec)
                .get("/api/unknown/23")
                .then()
                .statusCode(404)
                .extract().asString();

        Allure.step("Step 2. Verify response");
        Assert.assertEquals(response,"{}");
    }

    @Test
    @Story("Post response")
    @Description("Create single user")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testCreateUser() {

        Allure.step("Step 1. Send POST - request to https://reqres.in");
        SingleUserPojo response = given(requestSpec.body(reqwestSUP),responseSpec)
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract().as(SingleUserPojo.class);

        Allure.step("Step 2. Verify details: name, job");
        Assert.assertEquals(response.getName(), "morpheus");
        Assert.assertEquals(response.getJob(), "leader");
    }

    @Test
    @Story("Put response")
    @Description("Update single user")
    @Severity(SeverityLevel.TRIVIAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testUpdateUser() {

        reqwestSUP.setJob("zion resident");

        Allure.step("Step 1. Send PUT - request to https://reqres.in");
        SingleUserPojo response = given(requestSpec.body(reqwestSUP),responseSpec)
                .put("/api/users/2")
                .then()
                .statusCode(200)
                .extract().as(SingleUserPojo.class);

        Allure.step("Step 2. Verify change user details: name, job");
        Assert.assertEquals(response.getJob(), reqwestSUP.getJob());
        Assert.assertEquals(response.getName(), reqwestSUP.getName());
    }

    @Test
    @Story("Patch response")
    @Description("Update single user")
    @Severity(SeverityLevel.TRIVIAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testUpdateUserPatch() {

        reqwestSUP.setCreatedAt("2025-01-09T12:30:37.398Z");

        Allure.step("Step 1. Send PATCH - request to https://reqres.in");
        SingleUserPojo response = given(requestSpec.body(reqwestSUP),responseSpec)
                .patch("/api/users/2")
                .then()
                .statusCode(200)
                .extract().as(SingleUserPojo.class);

        Allure.step("Step 2. Verify update date");
        Assert.assertEquals(response.getCreatedAt(), reqwestSUP.getCreatedAt());
    }
    @Test
    @Story("Delete response")
    @Description("Delete single user")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testDeleteUser() {

        Allure.step("Step 1. Send DELETE - request to https://reqres.in");
        Response response = given(requestSpec,responseSpec)
                .delete("/api/users/2")
                .then()
                .statusCode(204)
                .extract().response();

        Allure.step("Step 2. Verify empty body");
        Assert.assertTrue(response.body().asString().isEmpty());
    }

    @Test
    @Story("Post response")
    @Description("User registration successful")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testRegisterUserSuccessful() {

        Allure.step("Step 1. Send POST - request to https://reqres.in");
        RegisterPojo register = given(requestSpec.body(registration),responseSpec)
                .post("/api/register")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", RegisterPojo.class);

        Allure.step("Step 2. Verify details: token, id");
        Assert.assertEquals(register.getToken(), "QpwL5tke4Pnpja7X4");
        Assert.assertEquals(register.getId(), 4);
    }

    @Test
    @Story("Post response")
    @Description("User registration unsuccessful")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testRegisterUserUnsuccessful() {

        registration.setEmail("sydney@fife");
        registration.setPassword("");

        Allure.step("Step 1. Send POST - request to https://reqres.in");
        ErrorPojo error = given(requestSpec.body(registration),responseSpec)
                .post("/api/register")
                .then()
                .statusCode(400)
                .extract().body().jsonPath().getObject(rootPath, ErrorPojo.class);

        Allure.step("Step 2. Verify error message");
        Assert.assertEquals(error.getError(), "Missing password");
    }

    @Test
    @Story("Post response")
    @Description("Login successful")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testUserLoginRegister() {

        registration.setEmail("eve.holt@reqres.in");
        registration.setPassword("cityslicka");

        Allure.step("Step 1. Send POST - request to https://reqres.in");
        TokenPojo token = given(requestSpec.body(registration),responseSpec)
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(rootPath, TokenPojo.class);

        Allure.step("Step 2. Verify token");
        Assert.assertEquals(token.getToken(), "QpwL5tke4Pnpja7X4");
    }

    @Test
    @Story("Post response")
    @Description("Login unsuccessful")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testUserLoginRegisterUnsuccessful() {

        registration.setEmail("peter@klaven");
        registration.setPassword("");

        Allure.step("Step 1. Send POST - request to https://reqres.in");
        ErrorPojo response = given(requestSpec.body(registration),responseSpec)
                .post("/api/login")
                .then()
                .statusCode(400)
                .extract().body().jsonPath().getObject(rootPath, ErrorPojo.class);

        Allure.step("Step 2. Verify error message");
        Assert.assertEquals(response.getError(), "Missing password");
    }

    @Test
    @Story("Post response")
    @Description("Delayed response")
    @Severity(SeverityLevel.MINOR)
    @Link("https://reqres.in")
    @Owner("DianaK")
    public void testDelayedResponse() {

        Allure.step("Step 1. Send GET - request to https://reqres.in");
        Response response = given(requestSpec,responseSpec)
                .get("/api/users?delay=3")
                .then()
                .statusCode(200)
                .time(lessThan(10000L))
                .extract().response();
    }

}