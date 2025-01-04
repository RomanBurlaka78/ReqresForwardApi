package api.tests.dima;

import api.base.Specifications;
import api.pojo.Registration;
import api.pojo.SingleUserPojo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.given;

public class RegresTest {

    protected Specifications requestSpec = new Specifications();
    protected RequestSpecification spec;

    protected SingleUserPojo reqwestSingleUserPojo = new SingleUserPojo("Neo", "Matrica pioner");
    protected Registration registration = new Registration("eve.holt@reqres.in", "pistolet");


    {
        spec = requestSpec.setupRequest();

    }

    @Test
    public void testListOfUsersIdExisted(){

        spec.queryParam("page", 2);
        Response response = given(spec).
                when().
                get("/api/users").
                then().
                statusCode(200).
                extract().
                response();

        List <Integer> listOfUsersId = response.jsonPath().getList("data.id");
        Assert.assertEquals(List.of(7,8,9,10,11,12), listOfUsersId);
    }

    @Test
    public void testVerifyUsersId(){

        int idOfUser = given(spec).
                when().
                get("/api/users/2").jsonPath().getInt("data.id");
        Assert.assertEquals(idOfUser,2);
    }

    @Test(description = "use String class for verification")
    public void testUserIsNotExist(){
        String expectedResult = "{}";
        String response = given(spec).
                when().
                get("/api/users/23").
                then().
                assertThat().
                statusCode(404).
                extract().asString();

        Assert.assertEquals(response, expectedResult);
    }

    @Test(description = "use Rest Assured class JSONObject for verification")
    public void testUserIsNotExistJson() {

        JSONObject jsonpObject = new JSONObject();
        Response response = given(spec).
                when().
                get("/api/users/23").
                then().
                assertThat().
                statusCode(404).
                extract().response();

        Assert.assertEquals(response.body().asString(), jsonpObject.toString());
    }

    @Test
    public void testGetListOfResourses(){
        spec.pathParam("resourse", "unknown");

        Response response = given(spec).
                when().
                get("/api/{resourse}").
                then().
                statusCode(200).
                extract().response();

        Assert.assertEquals(List.of(1,2,3,4,5,6), response.jsonPath().getList("data.id"));
    }

    @Test
    public void testSingleResourceId(){
        spec.pathParam("resourse", "unknown");
        //spec.queryParam("id",2);
        int idOfTheResourse = given(spec).
                when().
                get("/api/{resourse}/2").
                jsonPath().
                getInt("data.id");
        Assert.assertEquals(idOfTheResourse, 2);
    }

    @Test
    public void testSingleResourceNotFaund(){

        spec.pathParam("resourse", "unknown");

        JSONObject jsonObject = new JSONObject();
        Response response = given(spec).
                when().
                get("/api/{resourse}/23").
                then().
                assertThat().
                statusCode(404).
                extract().response();
        Assert.assertEquals(response.body().asString(), jsonObject.toString());
    }

    @Test
    public void testIdOfCreatedEntry(){

        spec.body(reqwestSingleUserPojo);
        SingleUserPojo responseSingleUserPojo = given(spec).
                when().
                post("/api/users").
                then().
                log().all().
                statusCode(201).
                extract().as(SingleUserPojo.class);

        Assert.assertEquals(responseSingleUserPojo.getName(), reqwestSingleUserPojo.getName());

    }

    @Test
    public void testOfEntrysNameUpdated(){

        reqwestSingleUserPojo.setName("Trinity");
        reqwestSingleUserPojo.setJob("rebel hero");
        spec.body(reqwestSingleUserPojo);
        SingleUserPojo responseSingleUserPojo = given(spec).
                when().
                put("/api/users/2").
                then().
                log().all().
                statusCode(200).
                extract().as(SingleUserPojo.class);

        Assert.assertEquals(responseSingleUserPojo.getName(), reqwestSingleUserPojo.getName());

    }

    @Test
    public void deleteEntry(){
        Response response = given(spec).
                when().
                delete("/api/users/2").
                then().
                assertThat().
                statusCode(204).
                extract().response();
        System.out.println(response.body().asString());
        Assert.assertTrue(response.body().asString().isEmpty());
    }

    @Test
    public void testRegisteredToken(){
        String expectedToken = "QpwL5tke4Pnpja7X4";
        spec.body(registration);
        String responseToken = given(spec).
                when().
                post("/api/register").
                then().
                statusCode(200).
                extract().response().jsonPath().getString("token");
        Assert.assertEquals(responseToken, expectedToken);
    }
}
