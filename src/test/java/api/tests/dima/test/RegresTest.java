package api.tests.dima.test;

import api.base.Specifications;
import api.pojo.Registration;
import api.pojo.SingleUserPojo;
import api.tests.dima.utils.AttachmentsForTests;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
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

    @BeforeClass
    public void setUp(){
        requestSpecification = specifications.setupRequest();
        responseSpecification = specifications.setupResponse();
    }

    @Test
    @Description("Test all the user`s id that are presented on the page, line-45")
    @Severity(SeverityLevel.MINOR)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testListOfUsersIdExisted(){
        Allure.step("get the response from API");
        Response response =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                    get("/api/users?page=2").
                then().
                    spec(responseSpecification).
                    statusCode(200).
                    extract().
                    response();
        Allure.step("Convert response to the list of integers that represent the id`s resived in response");
        List <Integer> listOfUsersId = response.jsonPath().getList("data.id");
        Allure.step("Assert the id`s");
        Assert.assertEquals(List.of(7,8,9,10,11,12), listOfUsersId);
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(requestSpecification);
        System.out.println(queryable.getDefinedFilters());
    }

    @Test
    @Description("Test the json schema and response body with aid of Json validator, line-65, and attaching the JSON scheema document")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testOfJsonScheemaWithResponseJsonBody() throws IOException {

        File file = new File("src/main/resources/JsonSchemaForListOfUsers.json");
        String schema = FileUtils.readFileToString(file, "UTF-8");
            //given(requestSpecification.filter(new AllureRestAssured()),responseSpecification).
            given().
                spec(requestSpecification).
                    filter(new AllureRestAssured()).
                get("/api/users?page=2").
            then().
                spec(responseSpecification).
                body(JsonSchemaValidator.matchesJsonSchema(schema));
        //AttachmentsForTests.attachDataJson();
        System.out.println("************************************************");
        System.out.println("************************************************");
        System.out.println("************************************************");
        System.out.println("************************************************");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(requestSpecification);
        System.out.println(queryable.getDefinedFilters());
    }

    @Test
    @Description("Test the id of a sinle userr, line-80")
    @Severity(SeverityLevel.TRIVIAL)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testVerifyUsersId(){

        int idOfUser =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                when().
                    get("/api/users/2").
                then().
                    spec(responseSpecification).
                    extract().
                    jsonPath().getInt("data.id");

        Assert.assertEquals(idOfUser,2);
    }

    @Test(description = "use String class for verification, line-92")
    @Description("Test that the user`s id is not exist")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testUserIsNotExist(){

        String expectedResult = "{}";
        String response =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                when().
                    get("/api/users/23").
                then().
                    spec(responseSpecification).
                    assertThat().
                    statusCode(404).
                    extract().asString();

        Assert.assertEquals(response, expectedResult);
    }

    @Test(description = "use Rest Assured class JSONObject for verification, line-110")
    @Description("Test that the user`s id is not exist")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testUserIsNotExistJson() {

        JSONObject jsonpObject = new JSONObject();
        Response response =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                when().
                    get("/api/users/23").
                then().
                    spec(responseSpecification).
                    assertThat().
                    statusCode(404).
                    extract().response();

        Assert.assertEquals(response.body().asString(), jsonpObject.toString());
    }

    @Test
    @Description("Get all the id`s of users presented as a resources, line-129")
    @Severity(SeverityLevel.MINOR)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testGetListOfResourses(){

        Response response =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                when().
                    get("/api/unknown").
                then().
                    spec(responseSpecification).
                    statusCode(200).
                    extract().response();

        Assert.assertEquals(List.of(1,2,3,4,5,6), response.jsonPath().getList("data.id"));
    }

    @Test
    @Description("Test id`s of a single user, line-146")
    @Severity(SeverityLevel.MINOR)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testSingleResourceId(){

        int idOfTheResourse =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                when().
                    get("/api/unknown/2").
                then().
                    spec(responseSpecification).
                    extract().
                    jsonPath().
                    getInt("data.id");

        Assert.assertEquals(idOfTheResourse, 2);
    }

    @Test(dataProvider = "dataForTest")
    @Story("Parameterized tests")
    @Description("Parameterized test with @dataProvider, for id of user that dos not exist, line-161")
    @Severity(SeverityLevel.MINOR)
    @Owner("Dima")
    @Parameters({"id value of the user we are loocking for"})
    public void testSingleResourceNotFaund(String str){

        JSONObject jsonObject = new JSONObject();
        Response response =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                when().
                    get("/api/unknown/"+str).
                then().
                    spec(responseSpecification).
                    assertThat().
                    statusCode(404).
                    extract().response();
        Assert.assertEquals(response.body().asString(), jsonObject.toString());
    }

    @Test
    @Description("Create a user and verify the name of that user is equal to one that was in a response, line-180")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testIdOfCreatedEntry(){

        Allure.parameter("name", reqwestSingleUserPojo.getName());
        Allure.parameter("job", reqwestSingleUserPojo.getJob() );

        SingleUserPojo responseSingleUserPojo =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                when().
                    body(reqwestSingleUserPojo).
                    post("/api/users").
                then().
                    spec(responseSpecification).
                    statusCode(201).
                    extract().as(SingleUserPojo.class);

        Assert.assertEquals(responseSingleUserPojo.getName(), reqwestSingleUserPojo.getName());
    }

    @Test
    @Description("Create a user and verify the id of that user is equal to one that was in a response, line-200")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testIdOfCreatedEntryInAResponseBody(){

                given().
                  spec(requestSpecification).
                  filter(new AllureRestAssured()).
                when().
                    body(reqwestSingleUserPojo).
                    post("/api/users").
                then().
                    spec(responseSpecification).
                    statusCode(201).
                    body("name",equalTo("Neo"));
    }

    @Test
    @Description("Use .xml file for parameterization and @Optional for providing parameters by default, line-216")
    @Severity(SeverityLevel.NORMAL)
    @Story("Parameterized tests")
    @Parameters({"name","job"})
    public void testOfEntrysNameUpdated(@Optional("Regular gay") String name, @Optional("tester") String job){

        reqwestSingleUserPojo.setName(name);
        reqwestSingleUserPojo.setJob(job);

        SingleUserPojo responseSingleUserPojo =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                when().
                    body(reqwestSingleUserPojo).
                    put("/api/users/2").
                then().
                    spec(responseSpecification).
                    statusCode(200).
                    extract().as(SingleUserPojo.class);

        Assert.assertEquals(responseSingleUserPojo.getName(), reqwestSingleUserPojo.getName());
    }

    @Test
    @Description("Delete a user entry by specifying the user`s id, line-239")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void deleteEntry(){
        Response response =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                when().
                    delete("/api/users/2").
                then().
                    spec(responseSpecification).
                    assertThat().
                    statusCode(204).
                    extract().response();

        Assert.assertTrue(response.body().asString().isEmpty());
    }

    @Test
    @Description("Get the registration token by providing valid email and password, validation of received token, line-257")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testRegisteredToken(){

        String expectedToken = "QpwL5tke4Pnpja7X4";
        String responseToken =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                when().
                    body(registration).
                    post("/api/register").
                then().
                    spec(responseSpecification).
                    statusCode(200).
                    extract().response().jsonPath().getString("token");

        Assert.assertEquals(responseToken, expectedToken);
    }

    @Test()
    @Description("Verify the error message for invalid registration with missing password, line-275")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testRegisterUnsuccessfulToken(){

        String expectedMessage = "Missing password";
        File file = new File("src/main/resources/not_valid_registration.json");

        String responseMessage =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                    body(file).
                when().
                    post("/api/register").
                then().
                    spec(responseSpecification).
                    statusCode(400).
                    extract().response().jsonPath().getString("error");

        Assert.assertEquals(responseMessage, expectedMessage);
    }

    @Test
    @Description("Validation of token received after registration with valid email and password, line-296 ")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testTokenForLoginSuccesful(){

        String expectedToken = "QpwL5tke4Pnpja7X4";

                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                when().
                    body(new Registration("eve.holt@reqres.in", "cityslicka")).
                    post("api/login").
                then().
                    spec(responseSpecification).
                    statusCode(200).
                    body("token",equalTo(expectedToken));

    }

    @Test()
    @Description("Validation of error message after unsuccessful login by providing only email without password, line-315")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testErrorMessageOfUnsuccessfulLogin(){

        String temp = """
                {
                    "email": "peter@klaven"
                }
                """;

        String expectedMessage = "Missing password";
        File file = new File("src/main/resources/not_valid_registration.json");

        String responseMessage =
                given().
                    spec(requestSpecification).
                    filter(new AllureRestAssured()).
                    body(temp).
                when().
                    post("/api/register").
                then().
                    spec(responseSpecification).
                    statusCode(400).
                    extract().response().jsonPath().getString("error");

        Assert.assertEquals(responseMessage, expectedMessage);
    }

//  in progress
//    @Test
//    @Description("Create a user and verify the name of that user is equal to one that was in a response")
//    @Severity(SeverityLevel.NORMAL)
//    @Owner("Dima")
//    @Story("Rest Assured tests")
//    public void delayedResponse(){
//        Response response =
//                given(requestSpecification).
//                when().
//                get("/api/users?delay=3").
//                then().
//                    extract().response();
//        System.out.println(response.asPrettyString());
//    }

    @DataProvider
    public String[] dataForTest(){

        return new String [] {"20", "21", "22", "23"};
    }


}
