package api.tests;

import api.base.ApiEndPoints;
import api.steps.ResourceSteps;
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

    private ResourceSteps resourceSteps = new ResourceSteps();
    private ApiEndPoints apiEndPoints = new ApiEndPoints();


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
        Response response = resourceSteps.getResourceById(apiEndPoints.getUNKNOWN(), 2);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("data.name"), "fuchsia rose");
    }

    @Test
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetListResource() {
        Response response = resourceSteps.getListResource(apiEndPoints.getUNKNOWN());
        Assert.assertEquals(response.getStatusCode(), 200);


    }

    @Test
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetSingleResourceNotFound() {
        Response response = resourceSteps.getResourceById(apiEndPoints.getUNKNOWN(),23);
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.body().asString(), "{}");
    }

    @Test(dataProvider = "dataResourceId")
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetSingleResourceNotFoundExtended(int dataResource) {
        Response response = resourceSteps.getResourceById(apiEndPoints.getUNKNOWN(), dataResource);
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.body().asString(), "{}");
    }
}