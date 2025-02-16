package api.tests;


import api.base.BaseTest;
import api.pojo.UserPojo;
import api.steps.UserSteps;
import com.beust.ah.A;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Reqres API Tests")
@Feature("User Management")
@Link("https://reqres.in/")
public class UserTests extends BaseTest {

    private UserSteps userSteps = new UserSteps();
    @Test
    @Description("Test to verify getting a list of users")
    @Story("Get User")
    public void testGetListUsers() {
        Response response = userSteps.getListUsers(2);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(!response.jsonPath().getString("data").isEmpty());
    }
    @Test
    @Description("Test to verify getting a user by ID")
    @Story("Get User")
    public void testGetUserById() {
        int userId = 2;
        Response response = userSteps.getUserById(userId);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("data.email"), "janet.weaver@reqres.in");
    }

    @Test
    @Description("Test to verify getting a user by ID")
    @Story("Get User")
    public void testGetUserNotFound() {
        int userId = 23;
        Response response = userSteps.getUserById(userId);
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    @Description("Test to verify creating a new user")
    @Story("Create User")
    public void testCreateUser() {
        Response response = userSteps.createUser("morpheus", "leader");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "leader");
    }

    @Test
    @Description("Test to verify updating a user")
    @Story("Update User")
    public void testPutUpdateUser() {
        Response response = userSteps.updateUser(2, "morpheus", "zion resident");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "zion resident");
    }

    @Test
    @Description("Test to verify updating a user")
    @Story("Update User")
    public void testPatchUpdateUser() {
        Response response = userSteps.updateUser(2, "morpheus", "zion resident");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "zion resident");
    }

    @Test(priority = 1)
    @Description("Test to verify deleting a user")
    @Story("Delete User")
    public void testDeleteUser() {
        Response response = userSteps.deleteUser(2);
        Assert.assertEquals(response.getStatusCode(), 204);
    }
//    """Pojo Tests"""

    @Test
    @Description("Test to verify getting a user by ID")
    @Story("Get User")
    public void testGetUserByIdPojo() {
        int userId = 2;
        Response response = userSteps.getUserById(userId);
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