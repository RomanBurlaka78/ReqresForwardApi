package api.tests;


import api.base.ApiEndPoints;
import api.pojo.UserPojo;
import api.services.UserService;
import api.servicesData.UserTestData;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Reqres API Tests")
@Feature("User management tests")
@Link("https://reqres.in/")
@Listeners(api.listeners.TestListener.class)
public class UserTests {

    private UserService userService = new UserService();

    @Test
    @Description("Test to verify getting a list of users")
    @Story("Get User")
    public void testGetListUsers() {
        Response response = userService.getListUsers(ApiEndPoints.USERS_PAGE, UserTestData.pageNumber);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(!response.jsonPath().getString("data").isEmpty());
    }

    @Test
    @Description("Test to verify getting a user by ID")
    @Story("Get User")
    public void testGetUserById() {

        Response response = userService.getUserById(ApiEndPoints.USERS_WITH_SLASH, UserTestData.existedUsersId);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("data.email"), "janet.weaver@reqres.in");
    }

    @Test
    @Description("Test to verify getting a user by ID")
    @Story("Get User")
    public void testGetUserNotFound() {

        Response response = userService.getUserById(ApiEndPoints.USERS_WITH_SLASH, UserTestData.notExistedUsersId);
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    @Description("Test to verify creating a new user")
    @Story("Create User")
    public void testCreateUser() {

        Response response = userService.createUser(ApiEndPoints.USERS, "morpheus", "leader");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "leader");
    }

    @Test
    @Description("Test to verify updating a user")
    @Story("Update User")
    public void testUpdateUser() {

        Response response = userService.updateUser(ApiEndPoints.API_USERS, 2, "morpheus", "zion resident");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "zion resident");
    }

    @Test
    @Description("Test to verify updating a user")
    @Story("Update User")
    public void testPatchUpdateUser() {

        Response response = userService.updateUser(ApiEndPoints.USERS_WITH_SLASH, 2, "morpheus", "zion resident");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "zion resident");
    }

    @Test(priority = 1)
    @Description("Test to verify deleting a user")
    @Story("Delete User")
    public void testDeleteUser() {

        Response response = userService.deleteUser(ApiEndPoints.USERS_WITH_SLASH, 2);
        Assert.assertEquals(response.getStatusCode(), 204);
    }
//    """Pojo Tests"""

    @Test
    @Description("Test to verify getting a user by ID")
    @Story("Get User")
    public void testGetUserByIdPojo() {

        Response response = userService.getUserById(ApiEndPoints.USERS_WITH_SLASH, 2);
        Assert.assertEquals(response.getStatusCode(), 200);


        Assert.assertEquals(response.jsonPath().getString("data.email"), "janet.weaver@reqres.in");

        UserPojo pojo =  response.body().jsonPath().getObject("data", UserPojo.class);
        Assert.assertEquals(pojo.getId(), 2);
        Assert.assertEquals(pojo.getFirst_name(), "Janet");
        Assert.assertEquals(pojo.getLast_name(), "Weaver");
        Assert.assertEquals(pojo.getEmail(), "janet.weaver@reqres.in");
        Assert.assertEquals(pojo.getAvatar(), "https://reqres.in/img/faces/2-image.jpg");
    }
}