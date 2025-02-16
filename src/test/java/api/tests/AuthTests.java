package api.tests;

import api.base.BaseTest;
import api.base.TestData;
import api.steps.AuthSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.openqa.selenium.devtools.v85.dom.model.BackendNode;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Reqres API Tests")
@Feature("Auth management")
@Link("https://reqres.in/")
public class AuthTests extends BaseTest {
    AuthSteps authSteps = new AuthSteps();
    TestData testData = new TestData();


    @Test
    @Description("Test to verify register successful")
    @Story("Auth User register")
    public void testRegisterSuccessful() {
        Response response = authSteps.getRegisterSuccessful(testData.getEmail(),testData.getPassword());
        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.body().asString(), """
                {"id":4,"token":"QpwL5tke4Pnpja7X4"}""");


    }

    @Test
    @Description("Test to verify register unsuccessful")
    @Story("Auth User register")
    public void testRegisterUnSuccessful() {
        Response response = authSteps.getRegisterUnSuccessful(testData.getEmail());
        Assert.assertEquals(response.getStatusCode(), 400);

        Assert.assertEquals(response.body().asString(), """
                {"error":"Missing password"}""");
    }

    @Test
    @Description("Test to verify login unsuccessful")
    @Story("Auth User login")
    public void testLoginSuccessful() {
        Response response = authSteps.postLoginSuccessful(testData.getEmailLogin(), testData.getPasswordLogin());
        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.body().asString(), """
                                                            {"token":"QpwL5tke4Pnpja7X4"}""");

    }

    @Test
    @Description("Test to verify login unsuccessful")
    @Story("Auth User login")
    public void testLoginUnSuccessful() {
        Response response = authSteps.postLoginUnSuccessful(testData.getEmailLogin());
        Assert.assertEquals(response.getStatusCode(), 400);

        Assert.assertEquals(response.body().asString(), """
                {"error":"Missing password"}""");

    }
}
