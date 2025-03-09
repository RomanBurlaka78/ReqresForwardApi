package api.tests;

import api.base.ApiEndPoints;
import api.base.TestData;
import api.services.AuthService;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Reqres API Tests")
@Feature("Auth tests")
@Link("https://reqres.in/")
@Listeners(api.listeners.TestListener.class)
public class AuthTests {

    private AuthService authService = new AuthService();
    private TestData testData = new TestData();

    @Test
    @Description("Test to verify register successful")
    @Story("Auth User register")
    public void testRegisterSuccessful() {
        Response response = authService.getRegisterSuccessful(ApiEndPoints.REGISTER_END_POINT, testData.getEmail(), testData.getPassword());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), """
                {"id":4,"token":"QpwL5tke4Pnpja7X4"}""");
    }

    @Test
    @Description("Test to verify register unsuccessful")
    @Story("Auth User register")
    public void testRegisterUnSuccessful() {
        Response response = authService.getRegisterUnSuccessful(ApiEndPoints.REGISTER_END_POINT, testData.getEmail());
        Assert.assertEquals(response.getStatusCode(), 400);

        Assert.assertEquals(response.body().asString(), """
                {"error":"Missing password"}""");
    }

    @Test
    @Description("Test to verify user`s successful login")
    @Story("Auth User login")
    public void testLoginSuccessful() {
        Response response = authService.postLoginSuccessful(ApiEndPoints.LOGIN_SUCCESSFUL, testData.getEmailLogin(), testData.getPasswordLogin());
        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.body().asString(), """
                                                            {"token":"QpwL5tke4Pnpja7X4"}""");

    }

    @Test
    @Description("Test to verify login unsuccessful")
    @Story("Auth User login")
    public void testLoginUnSuccessful() {
        Response response = authService.postLoginUnSuccessful(ApiEndPoints.LOGIN_SUCCESSFUL, testData.getEmailLogin());
        Assert.assertEquals(response.getStatusCode(), 400);

        Assert.assertEquals(response.body().asString(), """
                {"error":"Missing password"}""");

    }
}
