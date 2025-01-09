package api.tests.sergeyk;

import api.base.Specifications;
import api.pojo.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.rootPath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class RegresWithPojoTest {

    private static final String NAME = "morpheus";
    private static final String PATCH_NAME = "Morpheus";
    private static final String JOB = "leader";
    private static final String PUT_JOB = "zion resident";
    private static final String PATCH_JOB = "Zion Resident";
    private static final int USER_ID = 2;
    private static final int NOT_USER_ID = 23;

    public Specifications specifications = new Specifications();
    public RequestSpecification request;

    @BeforeMethod
    public void spec(){
        request = specifications.setupRequest();
        specifications.installSpec();
    }

    @Test
    public void testListUsers() {

        Response response = given(request.queryParam("page", 2))
                .get("/api/users")
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath data = response.body().jsonPath();
        List<UserPojo> usersList = data.getList("data", UserPojo.class);

        Assert.assertEquals(data.getInt("page"), 2);
        Assert.assertEquals(List.of(7, 8, 9, 10, 11, 12), data.getList("data.id"));
        Assert.assertEquals(data.getInt("per_page"), usersList.size());
        Assert.assertEquals(usersList.get(0).getId(), 7);
        Assert.assertEquals(usersList.get(0).getEmail(), "michael.lawson@reqres.in");
        Assert.assertEquals(usersList.get(0).getFirst_name(), "Michael");
        Assert.assertEquals(usersList.get(0).getLast_name(), "Lawson");
        Assert.assertEquals(usersList.get(0).getAvatar(), "https://reqres.in/img/faces/7-image.jpg");
    }

    @Test
    public void testSingleUser() {

        UserPojo user = given(request.pathParam("id", USER_ID))
                .get("/api/users/{id}")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject("data", UserPojo.class);

        Assert.assertEquals(user.getId(), USER_ID);
        Assert.assertEquals(user.getEmail(), "janet.weaver@reqres.in");
        Assert.assertEquals(user.getFirst_name(), "Janet");
        Assert.assertEquals(user.getLast_name(), "Weaver");
        Assert.assertEquals(user.getAvatar(), "https://reqres.in/img/faces/2-image.jpg");
    }

    @Test
    public void testSingleUserNotFound() {

        RestAssured.given(request.pathParam("id", NOT_USER_ID))
                .get("/api/users/{id}")
                .then()
                .statusCode(404)
                .body(equalTo("{}"))
                .extract().response();
    }

    @Test
    public void testListResource() {

        Response response = given()
                .get("/api/unknown")
                .then()
                .statusCode(200)
                .extract().response();

        List<ResourcePojo> resourceList = response.body().jsonPath().getList("data", ResourcePojo.class);

        Assert.assertEquals(response.body().jsonPath().getInt("per_page"), resourceList.size());
        Assert.assertEquals(resourceList.get(2).getId(), 3);
        Assert.assertEquals(resourceList.get(2).getName(), "true red");
        Assert.assertEquals(resourceList.get(2).getYear(), 2002);
        Assert.assertEquals(resourceList.get(2).getColor(), "#BF1932");
        Assert.assertEquals(resourceList.get(2).getPantone_value(), "19-1664");
    }

    @Test
    public void testSingleResource() {

        ResourcePojo resource = given(request.pathParam("id", USER_ID))
                .get("/api/unknown/{id}")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject("data", ResourcePojo.class);

        Assert.assertEquals(resource.getId(), 2);
        Assert.assertEquals(resource.getName(), "fuchsia rose");
        Assert.assertEquals(resource.getYear(), 2001);
        Assert.assertEquals(resource.getColor(), "#C74375");
        Assert.assertEquals(resource.getPantone_value(), "17-2031");
    }

    @Test
    public void testSingleResourceNotFound() {

        RestAssured.given(request.pathParam("id", NOT_USER_ID))
                .get("/api/unknown/{id}")
                .then()
                .statusCode(404)
                .body(equalTo("{}"))
                .extract().response();
    }

    @Test
    public void testCreate() {

        CreateSingleUserPojo userBody = new CreateSingleUserPojo(NAME, JOB);

        SingleUserPojo user = given(request.body(userBody))
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(user.getName(), NAME);
        Assert.assertEquals(user.getJob(), JOB);
    }

    @Test
    public void testUpdatePut() {

        CreateSingleUserPojo userBody = new CreateSingleUserPojo(NAME, PUT_JOB);

        SingleUserPojo user = given(request.pathParam("id", USER_ID).body(userBody))
                .put("/api/users/{id}")
                .then()
                .statusCode(200)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(user.getName(), NAME);
        Assert.assertEquals(user.getJob(), PUT_JOB);
    }

    @Test
    public void testUpdatePatch() {

        CreateSingleUserPojo userBody = new CreateSingleUserPojo(PATCH_NAME, PATCH_JOB);

        SingleUserPojo user = RestAssured.given(request.pathParam("id", USER_ID).body(userBody))
                .patch("/api/users/{id}")
                .then()
                .statusCode(200)
                .extract().as(SingleUserPojo.class);

        Assert.assertEquals(user.getName(), PATCH_NAME);
        Assert.assertEquals(user.getJob(), PATCH_JOB);
    }

    @Test
    public void testDelete() {

        RestAssured.given(request.pathParam("id", USER_ID))
                .delete("/api/users/{id}")
                .then()
                .statusCode(204)
                .body(equalTo(""))
                .extract().response();
    }

    @Test
    public void testRegisterSuccessful() {

        Registration registerBody = new Registration("eve.holt@reqres.in", "pistol");

        RegisterPojo register = given(request.body(registerBody))
                .post("/api/register")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(rootPath, RegisterPojo.class);

        Assert.assertEquals(register.getId(), 4);
        Assert.assertEquals(register.getToken(), "QpwL5tke4Pnpja7X4");
    }

    @Test
    public void testRegisterUnsuccessful() {

        Registration registerBody = new Registration("sydney@file", null);

        ErrorPojo error = given(request.body(registerBody))
                .post("/api/register")
                .then()
                .statusCode(400)
                .extract().body().jsonPath().getObject(rootPath, ErrorPojo.class);

        Assert.assertEquals(error.getError(), "Missing password");
    }

    @Test
    public void testLoginSuccessful() {

        Registration login = new Registration("eve.holt@reqres.in", "cityslicka");

        TokenPojo token = given(request.body(login))
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(rootPath, TokenPojo.class);

        Assert.assertEquals(token.getToken(), "QpwL5tke4Pnpja7X4");
    }

    @Test
    public void testLoginUnsuccessful() {

        Registration login = new Registration("peter@klaven", null);

        ErrorPojo error = given(request.body(login))
                .post("/api/login")
                .then()
                .statusCode(400)
                .extract().body().jsonPath().getObject(rootPath, ErrorPojo.class);

        Assert.assertEquals(error.getError(), "Missing password");
    }

    @Test
    public void testDelayedResponse() {

        RestAssured.given(request.queryParam("delay", 3))
                .get("/api/users")
                .then()
                .statusCode(200)
                .time(lessThan(6000L));
    }
}