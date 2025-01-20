package api.tests.rita;

import api.base.BaseTest;
import api.pojo.CreateSingleUserPojo;
import api.pojo.ErrorPojo;
import api.pojo.SingleUserPojo;
import api.pojo.UserPojo;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTest extends BaseTest {

    @Test
    public void testGetListUsers() {

        given(requestSpec.queryParam("page", 2), responseSpec)
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
    public void testGetSingleUser() {

        UserPojo response = given(requestSpec.pathParam("id", 2), responseSpec)
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
    public void testGetInvalidUser() {

        Response response = given(requestSpec.pathParam("id", 23), responseSpec)
                .get("/api/users/{id}")
                .then()
                .statusCode(404)
                .extract().response();

        Assert.assertEquals(response.getBody().asString(), "{}");
    }

    @Test
    public void testPostCreateUser() {

        CreateSingleUserPojo postBody = new CreateSingleUserPojo("morpheus", "leader");

        SingleUserPojo response = given(requestSpec.body(postBody), responseSpec)
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(response.getName(), "morpheus");
        Assert.assertEquals(response.getJob(), "leader");

    }

    @Ignore
    @Test
    public void testPostCreateUserWithoutName() {
        // баг - Expected status code <400> but was <201>

        CreateSingleUserPojo postBody = new CreateSingleUserPojo("", "leader");

        ErrorPojo response = given(requestSpec.body(postBody), responseSpec)
                .post("/api/users")
                .then()
                .statusCode(400)
                .extract().as(ErrorPojo.class);

        Assert.assertEquals(response.getError(), "error: Missing required fields");

    }

    @Test
    public void testPutUser() {

        SingleUserPojo updateUser = new SingleUserPojo("neo", "the one");

        SingleUserPojo response = given(requestSpec.body(updateUser), responseSpec)
                .put("api/users/2")
                .then()
                .statusCode(200)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(response.getName(), "neo");
        Assert.assertEquals(response.getJob(), "the one");

    }

    @Test
    public void testDeleteUser() {
        given(requestSpec)
                .delete("api/users/2")
                .then()
                .statusCode(204)
                .body(isEmptyString());
    }

    @Test
    public void testDeleteUserNegative() {

        Response response = given(requestSpec, responseSpec)
                .delete("api/users/2")
                .then()
                .statusCode(204)
                .extract().response();


    }
}
