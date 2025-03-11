package tests;


import testsUtils.pojoModels.UserPojo;
import testsUtils.services.UserService;
import api.apiEndPoints.UserEndPoints;
import io.qameta.allure.*;
import io.restassured.response.Response;
import testsUtils.testListeners.TestNgTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testsUtils.testsData.UserTestData;

@Epic("Reqres API Tests")
@Feature("UserTests management tests")
@Link("https://reqres.in/")
@Listeners(TestNgTestListener.class)
public class UserTests {

    private UserService userService = new UserService();

    @Test
    @Description("Test to verify getting a list of users")
    @Story("Get UserTests")
    public void testGetListUsers() {
        Response response = userService.getListUsers(UserEndPoints.USERS_PAGE, UserTestData.PAGE_NUMBER);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(!response.jsonPath().getString("data").isEmpty());
    }

    @Test
    @Description("Test to verify getting a user by ID")
    @Story("Get UserTests")
    public void testGetUserById() {

        Response response = userService.getUserById(UserEndPoints.USERS_WITH_SLASH, UserTestData.EXISTED_USER_ID);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("data.email"), "janet.weaver@reqres.in");
    }

    @Test
    @Description("Test to verify getting a user by ID")
    @Story("Get UserTests")
    public void testGetUserNotFound() {

        Response response = userService.getUserById(UserEndPoints.USERS_WITH_SLASH, UserTestData.NOT_EXISTED_USER_ID);
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    @Description("Test to verify creating a new user")
    @Story("Create UserTests")
    public void testCreateUser() {

        Response response = userService.createUser(UserEndPoints.USERS, new UserPojo("morpheus", "leader"));
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "leader");
    }

    @Test
    @Description("Test to verify updating a user")
    @Story("Update UserTests")
    public void testUpdateUser() {

        Response response = userService.updateUser(UserEndPoints.API_USERS, UserTestData.EXISTED_USER_ID, new UserPojo("morpheus", "zion resident"));
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "zion resident");
    }

    @Test(priority = 1)
    @Description("Test to verify deleting a user")
    @Story("Delete UserTests")
    public void testDeleteUser() {

        Response response = userService.deleteUser(UserEndPoints.USERS_WITH_SLASH, UserTestData.EXISTED_USER_ID);
        Assert.assertEquals(response.getStatusCode(), 204);
    }

    @Test
    @Description
    @Story("Get delayed")
    public void testGetDelayedResponse() {
        Response delayedResponse = userService.getDelayedResponse(UserEndPoints.USER_DELAY, UserTestData.DALAY_TIME);
        Assert.assertEquals(delayedResponse.getStatusCode(), 200);
        Assert.assertEquals(delayedResponse.jsonPath().getString("total"), "12");
    }

}