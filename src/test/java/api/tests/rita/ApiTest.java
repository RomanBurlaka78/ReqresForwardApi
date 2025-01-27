package api.tests.rita;

import api.base.BaseTest;
import api.pojo.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.rootPath;
import static org.hamcrest.Matchers.*;

@Epic("Api tests")
@Feature("User Management")
public class ApiTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Get response")
    @Description("Get list of users")
    public void testGetListUsers() {

        given(requestSpec.queryParam("page", 2).filter(new AllureRestAssured()), responseSpec)
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("data", hasSize(greaterThan(0)));

        List<?> users = given(requestSpec.queryParam("page", 2), responseSpec)
                .get("/api/users")
                .then()
                .extract().body().jsonPath().getList("data");

        Assert.assertEquals(users.size(), 6);

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Get response")
    @Description("Get single user")
    public void testGetSingleUser() {

        UserPojo response = given(requestSpec.pathParam("id", 2).filter(new AllureRestAssured()), responseSpec)
                .get("/api/users/{id}")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject("data", UserPojo.class);

        Assert.assertEquals(2, response.getId());
        Assert.assertEquals("Janet", response.getFirst_name());
        Assert.assertEquals("Weaver", response.getLast_name());
        Assert.assertEquals("janet.weaver@reqres.in", response.getEmail());
        Assert.assertEquals("https://reqres.in/img/faces/2-image.jpg", response.getAvatar());

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Get response")
    @Description("Get invalid user by ID")
    public void testGetInvalidUser() {

        Response response = given(requestSpec.pathParam("id", 23).filter(new AllureRestAssured()), responseSpec)
                .get("/api/users/{id}")
                .then()
                .statusCode(404)
                .extract().response();

        Assert.assertEquals(response.getBody().asString(), "{}");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Post response")
    @Description("Create a user")
    public void testPostCreateUser() {

        CreateSingleUserPojo postBody = new CreateSingleUserPojo("morpheus", "leader");

        SingleUserPojo response = given(requestSpec.body(postBody).filter(new AllureRestAssured()), responseSpec)
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(response.getName(), "morpheus");
        Assert.assertEquals(response.getJob(), "leader");

    }

    @Test(dependsOnMethods = "testPostCreateUser")
    @Severity(SeverityLevel.NORMAL)
    @Story("Put response")
    @Description("Update user")
    public void testPutUser() {

        SingleUserPojo updateUser = new SingleUserPojo("neo", "the one");

        SingleUserPojo response = given(requestSpec.body(updateUser).filter(new AllureRestAssured()), responseSpec)
                .put("api/users/2")
                .then()
                .statusCode(200)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(response.getName(), "neo");
        Assert.assertEquals(response.getJob(), "the one");

    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Patch response")
    @Description("Update user")
    public void testPatchUser() {

        SingleUserPojo updateUser = new SingleUserPojo("morpheus", "zion resident");

        SingleUserPojo response = given(requestSpec.body(updateUser).filter(new AllureRestAssured()), responseSpec)
                .patch("api/users/2")
                .then()
                .statusCode(200)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(response.getName(), "morpheus");
        Assert.assertEquals(response.getJob(), "zion resident");
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Delete response")
    @Description("Delete a user")
    public void testDeleteUser() {
        given(requestSpec.filter(new AllureRestAssured()))
                .delete("api/users/2")
                .then()
                .statusCode(204)
                .body(isEmptyString());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Post response")
    @Description("Register a user")
    public void testRegisterUser() {

        Registration registerUser = new Registration("eve.holt@reqres.in", "pistol");

        RegisterPojo response = given(requestSpec.body(registerUser).filter(new AllureRestAssured()), responseSpec)
                .post("/api/register")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(rootPath, RegisterPojo.class);

        Assert.assertEquals(response.getId(), 4);
        Assert.assertEquals(response.getToken(), "QpwL5tke4Pnpja7X4");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Post response")
    @Description("Login a user")
    public void testLoginUser() {

        Registration login = new Registration("eve.holt@reqres.in", "cityslicka");

        RegisterPojo response = given(requestSpec.body(login).filter(new AllureRestAssured()), responseSpec)
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(rootPath, RegisterPojo.class);

        Assert.assertEquals(response.getToken(), "QpwL5tke4Pnpja7X4");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Post response")
    @Description("Login a user without password")
    public void testLoginUserWithoutPassword() {

        Registration login = new Registration("eve.holt@reqres.in", null);

        ErrorPojo response = given(requestSpec.body(login).filter(new AllureRestAssured()), responseSpec)
                .post("/api/login")
                .then()
                .statusCode(400)
                .extract().body().jsonPath().getObject(rootPath, ErrorPojo.class);

        Assert.assertEquals(response.getError(), "Missing password");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Get response")
    @Description("Get single resource")
    public void testGetSingleResource() {
        ResourcePojo response = given(requestSpec.pathParam("id", 2).filter(new AllureRestAssured()), responseSpec)
                .get("/api/unknown/{id}")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject("data", ResourcePojo.class);

        Assert.assertEquals(2, response.getId());
        Assert.assertEquals("fuchsia rose", response.getName());
        Assert.assertEquals(2001, response.getYear());
        Assert.assertEquals("#C74375", response.getColor());
        Assert.assertEquals("17-2031", response.getPantone_value());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Get response")
    @Description("Get delayed response")
    public void testGetDelayedResponse() {
        given(requestSpec.queryParam("delay", 3).filter(new AllureRestAssured()), responseSpec)
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("data", hasSize(greaterThan(0)));

        List<?> users = given(requestSpec.queryParam("delay", 3), responseSpec)
                .get("/api/users")
                .then()
                .extract().body().jsonPath().getList("data");

        Assert.assertEquals(users.size(), 6);
    }

}