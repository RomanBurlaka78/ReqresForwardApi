package api.tests;

import api.base.ApiEndPoints;
import api.base.TestData;
import api.steps.AuthSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Reqres API Tests")
@Feature("Auth tests")
@Link("https://reqres.in/")
public class AuthTests {

    private AuthSteps authSteps = new AuthSteps();
    private TestData testData = new TestData();
    private ApiEndPoints apiEndPoints = new ApiEndPoints();


    @Test
    @Description("Test to verify register successful")
    @Story("Auth User register")
    public void testRegisterSuccessful() {
        Response response = authSteps.getRegisterSuccessful(apiEndPoints.getREGISTER_END_POINT(), testData.getEmail(), testData.getPassword());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), """
                {"id":4,"token":"QpwL5tke4Pnpja7X4"}""");
    }

    @Test
    @Description("Test to verify register unsuccessful")
    @Story("Auth User register")
    public void testRegisterUnSuccessful() {
        Response response = authSteps.getRegisterUnSuccessful(apiEndPoints.getREGISTER_END_POINT(), testData.getEmail());
        Assert.assertEquals(response.getStatusCode(), 400);

        Assert.assertEquals(response.body().asString(), """
                {"error":"Missing password"}""");
    }

    @Test
    @Description("Test to verify user`s successful login")
    @Story("Auth User login")
    public void testLoginSuccessful() {
        Response response = authSteps.postLoginSuccessful(apiEndPoints.getLOGIN_SUCCESSFUL(), testData.getEmailLogin(), testData.getPasswordLogin());
        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.body().asString(), """
                                                            {"token":"QpwL5tke4Pnpja7X4"}""");

    }

    @Test
    @Description("Test to verify login unsuccessful")
    @Story("Auth User login")
    public void testLoginUnSuccessful() {
        Response response = authSteps.postLoginUnSuccessful(apiEndPoints.getLOGIN_SUCCESSFUL(), testData.getEmailLogin());
        Assert.assertEquals(response.getStatusCode(), 400);

        Assert.assertEquals(response.body().asString(), """
                {"error":"Missing password"}""");

    }
}
