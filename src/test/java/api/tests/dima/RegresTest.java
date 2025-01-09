package api.tests.dima;

import api.base.Specifications;
import api.pojo.RegisterPojo;
import api.pojo.Registration;
import api.pojo.SingleUserPojo;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RegresTest {

    protected Specifications specifications = new Specifications();
    protected RequestSpecification requestSpecification;
    protected ResponseSpecification responseSpecification;

    protected SingleUserPojo reqwestSingleUserPojo = new SingleUserPojo("Neo", "Matrica pioner");
    protected Registration registration = new Registration("eve.holt@reqres.in", "pistolet");


    {
        requestSpecification = specifications.setupRequest();
        responseSpecification = specifications.setupResponse();
    }

    @Test
    public void testListOfUsersIdExisted(){

        Response response = given(requestSpecification,responseSpecification).
                get("/api/users?page=2").
                then().
                statusCode(200).
                extract().
                response();

        List <Integer> listOfUsersId = response.jsonPath().getList("data.id");
        Assert.assertEquals(List.of(7,8,9,10,11,12), listOfUsersId);
    }

    @Test
    public void testVerifyUsersId(){

        int idOfUser = given(requestSpecification,responseSpecification).
                get("/api/users/2").jsonPath().getInt("data.id");
        Assert.assertEquals(idOfUser,2);
    }

    @Test(description = "use String class for verification")
    public void testUserIsNotExist(){
        String expectedResult = "{}";
        String response = given(requestSpecification).
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
        Response response = given(requestSpecification).
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

        Response response = given(requestSpecification).
                when().
                get("/api/unknown").
                then().
                statusCode(200).
                extract().response();

        Assert.assertEquals(List.of(1,2,3,4,5,6), response.jsonPath().getList("data.id"));
    }

    @Test
    public void testSingleResourceId(){

        int idOfTheResourse = given(requestSpecification).
                when().
                get("/api/unknown/2").
                jsonPath().
                getInt("data.id");
        Assert.assertEquals(idOfTheResourse, 2);
    }

    @Test
    public void testSingleResourceNotFaund(){

        JSONObject jsonObject = new JSONObject();
        Response response = given(requestSpecification,responseSpecification).
                get("/api/unknown/23").
                then().
                assertThat().
                statusCode(404).
                extract().response();
        Assert.assertEquals(response.body().asString(), jsonObject.toString());
    }

    @Test
    public void testIdOfCreatedEntry(){

        SingleUserPojo responseSingleUserPojo =
                given(requestSpecification).
                when().
                    body(reqwestSingleUserPojo).
                    post("/api/users").
                then().
                    statusCode(201).
                    extract().as(SingleUserPojo.class);

        Assert.assertEquals(responseSingleUserPojo.getName(), reqwestSingleUserPojo.getName());

    }

    @Test
    public void testIdOfCreatedEntryInAResponseBody(){

        given(requestSpecification).
                when().
                body(reqwestSingleUserPojo).
                post("/api/users").
                then().
                statusCode(201).
                body("name",equalTo("Neo"));
    }

    @Test
    public void testOfEntrysNameUpdated(){

        reqwestSingleUserPojo.setName("Trinity");
        reqwestSingleUserPojo.setJob("rebel hero");
        SingleUserPojo responseSingleUserPojo = given(requestSpecification).
                when().
                body(reqwestSingleUserPojo).
                put("/api/users/2").
                then().
                log().all().
                statusCode(200).
                extract().as(SingleUserPojo.class);

        Assert.assertEquals(responseSingleUserPojo.getName(), reqwestSingleUserPojo.getName());

    }

    @Test
    public void deleteEntry(){
        Response response = given(requestSpecification,responseSpecification).
                //when().
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
        String responseToken = given(requestSpecification).
                when().
                body(registration).
                post("/api/register").
                then().
                statusCode(200).
                extract().response().jsonPath().getString("token");
        Assert.assertEquals(responseToken, expectedToken);
    }

    @Test(description = "создал файл, куда записал JSON, создал File подставив путь к файлу")
    public void testRegisterUnsuccessfulToken(){
        String expectedMessage = "Missing password";
        File file = new File("src/main/resources/not_valid_registration.json");

        String responseMessage =
                given(requestSpecification).
                    body(file).
                when().
                    post("/api/register").
                then().
                    statusCode(400).
                    extract().response().jsonPath().getString("error");
        Assert.assertEquals(responseMessage, expectedMessage);
    }

    @Test
    public void testTokenForLoginSuccesful(){

        String expectedToken = "QpwL5tke4Pnpja7X4";

                given(requestSpecification).
                when().
                    body(new Registration("eve.holt@reqres.in", "cityslicka")).
                    post("api/login").
                then().
                    statusCode(200).
                    body("token",equalTo(expectedToken));

    }

    @Test()
    public void testErrorMessageOfUnsuccessfulLogin(){
        String expectedMessage = "Missing password";
        File file = new File("src/main/resources/not_valid_registration.json");

        String responseMessage =
                given(requestSpecification).
                        body(file).
                        when().
                        post("/api/register").
                        then().
                        statusCode(400).
                        extract().response().jsonPath().getString("error");
        Assert.assertEquals(responseMessage, expectedMessage);
    }

    @Test
    public void delateResponse(){
        Response response =
                given(requestSpecification).
                when().
                get("/api/users?delay=3").
                then().
                    extract().response();
        System.out.println(response.asPrettyString());
    }
}
