package api.tests.dima;

import api.base.Specifications;
import api.pojo.RegisterPojo;
import api.pojo.Registration;
import api.pojo.SingleUserPojo;
import com.beust.jcommander.Parameter;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Epic("Api tests")
@Feature("Dima`s version of regres api tests")
@Link("https://reqres.in/")
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
    @Description("Test all the user`s id that are presented on the page")
    @Severity(SeverityLevel.MINOR)
    @Owner("Dima")
    public void testListOfUsersIdExisted(){
        Allure.step("get the response from API");
        Response response = given(requestSpecification,responseSpecification).
                get("/api/users?page=2").
                then().
                statusCode(200).
                extract().
                response();
        Allure.step("Convert response to the list of integers that represent the id`s resived in response");
        List <Integer> listOfUsersId = response.jsonPath().getList("data.id");
        Allure.step("Assert the id`s");
        Assert.assertEquals(List.of(7,8,9,10,11,12), listOfUsersId);
    }

    @Test
    @Description("Test the json schema and response body with aid of Json validator")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    public void testOfJsonScheemaWithResponseJsonBody() throws IOException {

        File file = new File("src/main/resources/JsonSchemaForListOfUsers.json");
        String schema = FileUtils.readFileToString(file, "UTF-8");
            given(requestSpecification,responseSpecification).
                get("/api/users?page=2").
            then().
                body(JsonSchemaValidator.matchesJsonSchema(schema));
    }

    @Test
    @Description("Test the id of a sinle userr")
    @Severity(SeverityLevel.TRIVIAL)
    @Owner("Dima")
    public void testVerifyUsersId(){

        int idOfUser = given(requestSpecification,responseSpecification).
                get("/api/users/2").jsonPath().getInt("data.id");
        Assert.assertEquals(idOfUser,2);
    }

    @Test(description = "use String class for verification")
    @Description("Test that the user`s id is not exist")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
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
    @Description("Test that the user`s id is not exist")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
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
    @Description("Get all the id`s of users presented as a resources")
    @Severity(SeverityLevel.MINOR)
    @Owner("Dima")
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
    @Description("Test id`s of a single user")
    @Severity(SeverityLevel.MINOR)
    @Owner("Dima")
    public void testSingleResourceId(){

        int idOfTheResourse = given(requestSpecification).
                when().
                get("/api/unknown/2").
                jsonPath().
                getInt("data.id");
        Assert.assertEquals(idOfTheResourse, 2);
    }

    @Test(dataProvider = "dataForTest", description = "Parameterized test " +
            "with @dataProvider, for id of user that dos not exist")
    @Story("Parameterized tests")
    @Description("Test id`s of a single user")
    @Severity(SeverityLevel.MINOR)
    @Owner("Dima")
    @Parameters({"id value of the user we are loocking for"})
    public void testSingleResourceNotFaund(String str){

        JSONObject jsonObject = new JSONObject();
        Response response = given(requestSpecification,responseSpecification).
                get("/api/unknown/"+str).
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

    @Test(description = "use .xml file for parameterization and @Optional for providing parameters by default")
    @Story("Parameterized tests")
    @Parameters({"name","job"})
    public void testOfEntrysNameUpdated(@Optional("Regular gay") String name, @Optional("tester") String job){

        reqwestSingleUserPojo.setName(name);
        reqwestSingleUserPojo.setJob(job);
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

    @DataProvider
    public String[] dataForTest(){

        return new String [] {"20", "21", "22", "23"};
    }
}
