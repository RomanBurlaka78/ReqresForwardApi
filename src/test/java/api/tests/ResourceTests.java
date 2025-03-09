package api.tests;

import api.base.ApiEndPoints;
import api.services.ResourceService;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Reqres API Tests")
@Feature("Resource management tests")
@Listeners(api.listeners.TestListener.class)
public class ResourceTests {

    private ResourceService resourceService = new ResourceService();

    @DataProvider
    public static Object[] dataResourceId() {
        return new Object[][]{
                {33},
                {233},
                {123},
                {45}
        };
    }

    @Test
    @Description("Test to verify a resource by ID")
    @Story("Get Resource")
    public void testGetSingleResourceById() {
        Response response = resourceService.getResourceById(ApiEndPoints.UNKNOWN, 2);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("data.name"), "fuchsia rose");
    }

    @Test
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetListResource() {
        Response response = resourceService.getListResource(ApiEndPoints.UNKNOWN);
        Assert.assertEquals(response.getStatusCode(), 200);


    }

    @Test
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetSingleResourceNotFound() {
        Response response = resourceService.getResourceById(ApiEndPoints.UNKNOWN,23);
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.body().asString(), "{}");
    }

    @Test(dataProvider = "dataResourceId")
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetSingleResourceNotFoundExtended(int dataResource) {
        Response response = resourceService.getResourceById(ApiEndPoints.UNKNOWN, dataResource);
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.body().asString(), "{}");
    }
}