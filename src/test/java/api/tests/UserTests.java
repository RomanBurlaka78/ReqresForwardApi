package api.tests;

import api.Steps.UserSteps;
import api.base.BaseTest;
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
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    public void testGetListUsers() {
            Response response = userSteps.getListUsers(2);
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertTrue(!response.jsonPath().getString("data").isEmpty());
        }

    @Test
    @Description("Test to verify getting a user by ID")
    @Story("Get User")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    public void testSingleUser() {
        Response response = userSteps.getUserId(2);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("data.first_name"), "Janet");
        Assert.assertEquals(response.jsonPath().getString("data.last_name"), "Weaver");
        Assert.assertEquals(response.jsonPath().getString("data.email"), "janet.weaver@reqres.in");
        Assert.assertEquals(response.jsonPath().getString("data.avatar"), "https://reqres.in/img/faces/2-image.jpg");
        }

    @Test
    @Description("Test to verify getting a user by ID")
    @Story("Get User")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    public void testUserNotFound() {
        Response response = userSteps.getUserId(23);
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.jsonPath().getString("data"),null);
    }

    @Test
    @Description("Test to create single user")
    @Story("Create User")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    public void testCreateUser() {
        Response response = userSteps.createUser("morpheus","leader");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "leader");

    }

    @Test
    @Description("Test to verify updating a user")
    @Story("Update User")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    public void testUpdateUser() {
        Response response = userSteps.updateUser(2,"morpheus", "zion resident");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "zion resident");
    }

    @Test
    @Description("Test to verify updating a user")
    @Story("Update User")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    public void testUpdatePatchUser() {
        Response response = userSteps.updateUser(2,"morpheus", "zion resident");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "zion resident");
    }

    @Test(priority = 1)
    @Description("Test to delete a user")
    @Story("Delete User")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    public void testDeleteUser() {
        Response response = userSteps.deleteUser(2);
        Assert.assertEquals(response.getStatusCode(), 204);

    }
}
