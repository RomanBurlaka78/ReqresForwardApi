package api.tests.dima.test;

import api.base.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.testng.TestInstanceParameter;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("tests version Dima")
@Feature("Rest Assured tests with @Factory")
public class UseFactoryAprouchTest extends BaseTest {

    @TestInstanceParameter
    private String combined;

    @org.testng.annotations.Factory(dataProvider = "dataAsPassword")
    public UseFactoryAprouchTest(String email, String password) {
        combined = "{\n" +
                "    \""+email+"\": \""+password+"\"\n" +
                "}";
    }

    public UseFactoryAprouchTest() {
    }

    @Test()
    @Description("Validation of error message after unsuccessful login by providing only email without password")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Story("Parameterized tests")
    public void testPassParametersViaFactory_1(){
        String expectedMessage = "Missing password";

        String responseMessage =
                given().
                    header("Content-Type", "application/json" ).
                    body(combined).
                    log().all().
                when().
                    post("https://reqres.in/api/register").
                then().
                    statusCode(400).
                    extract().response().jsonPath().getString("error");

        Assert.assertEquals(responseMessage, expectedMessage);
    }

    @Test
    @Description("This is empty test for learning purpose only")
    @Severity(SeverityLevel.MINOR)
    @Owner("Dima")
    @Story("Parameterized tests")
    public void testEmptyTest(){
        System.out.println(combined);
    }

    @DataProvider
    public String[][] dataAsPassword(){
        return new String[][] {
                {"email", "sydney@fife"},
                {"email", "someEmail@fife"}
        };
    }
}
