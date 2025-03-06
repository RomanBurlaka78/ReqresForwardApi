package api.tests;


import api.Steps.ResourceSteps;
import api.base.BaseTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


@Epic("Reqres API Tests")
@Feature("Resource Management")
public class ResourceTests extends BaseTest {

    private ResourceSteps resourceSteps = new ResourceSteps();

    @Test
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    public void testGetListResource() {
        Response response = resourceSteps.getListResource(1);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("data.name"), "cerulean");
    }

    @Test
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    public void testGetSingleResourceById() {
        Response response = resourceSteps.getResourceById(2);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("data.name"), "fuchsia rose");
    }

    @Test
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://reqres.in")
    public void testSingleResourceNotFound() {
        Response response = resourceSteps.getSingleResourceNotFound(23);
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.jsonPath().getString("data"),null);
    }
}
