package api.tests.Diana;

import com.beust.ah.A;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RegresTest {

    private static final String URL = "https://reqres.in";

    @Test
    public void testVerifySingleUser() {
        Response singleUsers = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users/2")
                .then().log().all()
                .statusCode(200)
                .extract().response();

        Assert.assertEquals(singleUsers.jsonPath().getInt("data.id"), 2);
        Assert.assertEquals(singleUsers.jsonPath().getString("data.first_name"), "Janet");
        Assert.assertEquals(singleUsers.jsonPath().getString("data.last_name"), "Weaver");
        Assert.assertEquals(singleUsers.jsonPath().getString("data.email"), "janet.weaver@reqres.in");

    }

    @Test
    public void testSingleUserNotExist() {
        RestAssured.given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users/23")
                .then().log().all()
                .statusCode(404)
                .body(equalTo("{}"))
                .extract().response();

    }

    @Test
    public void testGetListOfResource() {
       Response response = given()
               .when().log().all()
                .contentType(ContentType.JSON)
                .get(URL + "/api/unknown")
                .then().log().all()
                .statusCode(200)
                .extract().response();

       Assert.assertEquals(List.of(1,2,3,4,5,6),response.jsonPath().getList("data.id"));
    }

    @Test
    public void testGetSingleResourceId() {
        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .get(URL + "/api/unknown/2")
                .then().log().all()
                .statusCode(200)
                //.body("data.id", equalTo(2))
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("data.id"), "2");
    }

    @Test
    public void testSingleResourceIdNotExist() {
        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .get(URL + "/api/unknown/23")
                .then().log().all()
                .statusCode(404)
                .extract().response();

    }

    @Test
    public void testCreateUser() {

        String body = """
               {
               "name": "morpheus",
               "job": "leader"
               }
                """;

        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .post(URL + "/api/users")
                .then().log().all()
                .statusCode(201)
//                .body("name" , equalTo("morpheus"))
//                .body("job", equalTo("leader"))
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "leader");
    }

    @Test
    public void testUpdateUser() {

        String body = """
                {
                "name": "morpheus",
                "job": "zion resident"
                 }
                """;

        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .put(URL + "/api/users/2")
                .then().log().all()
                .statusCode(200)
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "zion resident");
    }

    @Test
    public void testUpdateUserPatch() {

        String body = """
                {
                "name": "morpheus",
                "job": "zion resident"
                  }
                """;

        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .patch(URL + "/api/users/2")
                .then().log().all()
                .statusCode(200)
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "zion resident");
    }
    @Test
    public void testDeleteUser() {

        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete(URL + "/api/users/2")
                .then().log().all()
                .statusCode(204)
                .extract().response();

        Assert.assertTrue(response.body().asString().isEmpty());
    }

    @Test
    public void testRegisterUser() {
        String body = """
                {
                "email": "eve.holt@reqres.in",
                "password": "pistol"
                }
                """;

        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .post(URL + "/api/register")
                .then().log().all()
                .statusCode(200)
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("id"), "4");
        Assert.assertEquals(response.jsonPath().getString("token"), "QpwL5tke4Pnpja7X4");
    }

    @Test
    public void testRegisterUserUnsuccessful() {
        String body = """
                {
                     "email": "sydney@fife"
                 }
                """;

        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .post(URL + "/api/register")
                .then().log().all()
                .statusCode(400)
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("error"), "Missing password");
    }

    @Test
    public void testUserLoginRegister() {
        String body = """
                {
                      "email": "eve.holt@reqres.in",
                      "password": "cityslicka"
                  }
                """;

        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .post(URL + "/api/login")
                .then().log().all()
                .statusCode(200)
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("token"), "QpwL5tke4Pnpja7X4");
    }

    @Test
    public void testUserLoginRegisterUnsuccessful() {
        String body = """
                {
                       "email": "peter@klaven"
                   }
                """;

        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .post(URL + "/api/login")
                .then().log().all()
                .statusCode(400)
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("error"), "Missing password");
    }

    @Test
    public void testDelayedResponse() {

        Response response = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users?delay=3")
                .then().log().all()
                .statusCode(200)
                .time(lessThan(10000L))
                .extract().response();

    }

}
