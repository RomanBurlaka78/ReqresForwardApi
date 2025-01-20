package api.tests.Diana;


import api.base.BaseTest;
import api.pojo.*;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RegresTest extends BaseTest {
    protected SingleUserPojo reqwestSUP = new SingleUserPojo("morpheus", "leader", "2025-01-09T12:34:34.000Z");
    protected Registration registration = new Registration("eve.holt@reqres.in", "pistol");
    protected UserPojo singleuserP = new UserPojo(2, "janet.weaver@reqres.in", "Janet", "Weaver", "https://reqres.in/img/faces/2-image.jpg");


    @Test
    public void testVerifySingleUser() {

        UserPojo singleUsers = given(requestSpec.body(singleuserP),responseSpec)

                .get("/api/users/2")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject("data", UserPojo.class);

        Assert.assertEquals(singleUsers.getId(), 2);
        Assert.assertEquals(singleUsers.getFirst_name(), "Janet");
        Assert.assertEquals(singleUsers.getLast_name(), "Weaver");
        Assert.assertEquals(singleUsers.getEmail(), "janet.weaver@reqres.in");
        Assert.assertEquals(singleUsers.getAvatar(), "https://reqres.in/img/faces/2-image.jpg");
    }

    @Test
    public void testSingleUserNotExist() {

        String response = given(requestSpec,responseSpec)
                .get("/api/users/23")
                .then()
                .statusCode(404)
                .extract().asString();

        Assert.assertEquals(response, "{}");
    }

    @Test
    public void testGetListOfResource() {

        List<ResourcePojo> resources = given(requestSpec,responseSpec)
                .get("/api/unknown")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", ResourcePojo.class);

        Assert.assertEquals(resources.get(2).getColor(), "#BF1932");
        Assert.assertEquals(resources.get(2).getId(), 3);
        Assert.assertEquals(resources.get(2).getName(), "true red");
        Assert.assertEquals(resources.get(2).getPantone_value(), "19-1664");
        Assert.assertEquals(resources.get(2).getYear(), 2002);
    }

    @Test
    public void testGetSingleResourceId() {

        ResourcePojo resp = given(requestSpec,responseSpec)
                .get("/api/unknown/2")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject("data", ResourcePojo.class);

        Assert.assertEquals(resp.getId(), 2);
        Assert.assertEquals(resp.getName(), "fuchsia rose");
        Assert.assertEquals(resp.getYear(), 2001);
        Assert.assertEquals(resp.getColor(), "#C74375");
        Assert.assertEquals(resp.getPantone_value(), "17-2031");
    }

    @Test
    public void testSingleResourceIdNotExist() {

        String response = given(requestSpec,responseSpec)
                .get("/api/unknown/23")
                .then()
                .statusCode(404)
                .extract().asString();

        Assert.assertEquals(response,"{}");
    }

    @Test
    public void testCreateUser() {

        SingleUserPojo response = given(requestSpec.body(reqwestSUP),responseSpec)
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(response.getName(), "morpheus");
        Assert.assertEquals(response.getJob(), "leader");
    }

    @Test
    public void testUpdateUser() {

        reqwestSUP.setJob("zion resident");

        SingleUserPojo response = given(requestSpec.body(reqwestSUP),responseSpec)
                .put("/api/users/2")
                .then()
                .statusCode(200)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(response.getJob(), reqwestSUP.getJob());
        Assert.assertEquals(response.getName(), reqwestSUP.getName());
    }

    @Test
    public void testUpdateUserPatch() {

        reqwestSUP.setCreatedAt("2025-01-09T12:30:37.398Z");

        SingleUserPojo response = given(requestSpec.body(reqwestSUP),responseSpec)
                .patch("/api/users/2")
                .then()
                .statusCode(200)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(response.getCreatedAt(), reqwestSUP.getCreatedAt());
    }
    @Test
    public void testDeleteUser() {

        Response response = given(requestSpec,responseSpec)
                .delete("/api/users/2")
                .then()
                .statusCode(204)
                .extract().response();

        Assert.assertTrue(response.body().asString().isEmpty());
    }

    @Test
    public void testRegisterUserSuccessful() {

        RegisterPojo register = given(requestSpec.body(registration),responseSpec)
                .post("/api/register")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", RegisterPojo.class);

        Assert.assertEquals(register.getToken(), "QpwL5tke4Pnpja7X4");
        Assert.assertEquals(register.getId(), 4);
    }

    @Test
    public void testRegisterUserUnsuccessful() {

        registration.setEmail("sydney@fife");
        registration.setPassword("");

        ErrorPojo error = given(requestSpec.body(registration),responseSpec)
                .post("/api/register")
                .then()
                .statusCode(400)
                .extract().body().jsonPath().getObject(rootPath, ErrorPojo.class);

        Assert.assertEquals(error.getError(), "Missing password");
    }

    @Test
    public void testUserLoginRegister() {

        registration.setEmail("eve.holt@reqres.in");
        registration.setPassword("cityslicka");

        TokenPojo token = given(requestSpec.body(registration),responseSpec)
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(rootPath, TokenPojo.class);

        Assert.assertEquals(token.getToken(), "QpwL5tke4Pnpja7X4");
    }

    @Test
    public void testUserLoginRegisterUnsuccessful() {

        registration.setEmail("peter@klaven");
        registration.setPassword("");

        ErrorPojo response = given(requestSpec.body(registration),responseSpec)
                .post("/api/login")
                .then()
                .statusCode(400)
                .extract().body().jsonPath().getObject(rootPath, ErrorPojo.class);

        Assert.assertEquals(response.getError(), "Missing password");
    }

    @Test
    public void testDelayedResponse() {

        Response response = given(requestSpec,responseSpec)
                .get("/api/users?delay=3")
                .then()
                .statusCode(200)
                .time(lessThan(10000L))
                .extract().response();
    }

}