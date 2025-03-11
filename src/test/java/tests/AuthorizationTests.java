package tests;

import testsUtils.testsData.AuthorizationTestData;
import testsUtils.pojoModels.AuthorizationPojo;
import testsUtils.services.AuthorizationService;
import api.apiEndPoints.AuthorizationEndPoints;
import io.qameta.allure.*;
import io.restassured.response.Response;
import testsUtils.testListeners.TestNgTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Reqres API Tests")
@Feature("Auth tests")
@Link("https://reqres.in/")
@Listeners(TestNgTestListener.class)
public class AuthorizationTests extends AuthorizationTestData{

    private final AuthorizationService authorizationService = new AuthorizationService();

    @Test
    @Description("Test to verify register successful")
    @Story("Auth UserTests register")
    public void testRegisterSuccessful() {
        Response response = authorizationService.getRegisterSuccessful(AuthorizationEndPoints.REGISTER_END_POINT, new AuthorizationPojo(REGISTRATION_EMAIL, REGISTRATION_PASSWORD));

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), registerSuccessfulResponse);
    }

    @Test
    @Description("Test to verify register unsuccessful")
    @Story("Auth UserTests register")
    public void testRegisterUnSuccessful() {
        Response response = authorizationService.getRegisterUnSuccessful(AuthorizationEndPoints.REGISTER_END_POINT, REGISTRATION_EMAIL);
        Assert.assertEquals(response.getStatusCode(), 400);

        Assert.assertEquals(response.body().asString(), registerUnSuccessfulErrorMessageResponse);
    }

    @Test
    @Description("Test to verify user`s successful login")
    @Story("Auth UserTests login")
    public void testLoginSuccessful() {
        Response response = authorizationService.postLoginSuccessful(AuthorizationEndPoints.LOGIN_END_POINT, LOGIN_EMAIL, LOGIN_PASSWORD);
        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.body().asString(), loginSuccessfulResponse);

    }

    @Test
    @Description("Test to verify login unsuccessful")
    @Story("Auth UserTests login")
    public void testLoginUnSuccessful() {
        Response response = authorizationService.postLoginUnSuccessful(AuthorizationEndPoints.LOGIN_END_POINT, LOGIN_EMAIL);
        Assert.assertEquals(response.getStatusCode(), 400);

        Assert.assertEquals(response.body().asString(), registerUnSuccessfulErrorMessageResponse);

    }
}
