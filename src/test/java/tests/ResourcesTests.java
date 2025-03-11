package tests;

import testsUtils.services.ResourceService;
import api.apiEndPoints.ResourceEndPoints;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import testsUtils.testListeners.TestNgTestListener;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testsUtils.testsData.ResourceTestData;

@Epic("Reqres API Tests")
@Feature("Resource management tests")
@Listeners(TestNgTestListener.class)
public class ResourcesTests {

    private ResourceService resourceService = new ResourceService();

    @Test
    @Description("Test to verify a resource by ID")
    @Story("Get Resource")
    public void testGetSingleResourceById() {
        Response response = resourceService.getResourceById(ResourceEndPoints.UNKNOWN, ResourceTestData.VALID_RESOURCE_ID);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("data.name"), "fuchsia rose");
    }

    @Test
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetListResource() {
        Response response = resourceService.getListResource(ResourceEndPoints.UNKNOWN);
        Assert.assertEquals(response.getStatusCode(), 200);


    }

    @Test
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetSingleResourceNotFound() {
        Response response = resourceService.getResourceById(ResourceEndPoints.UNKNOWN, ResourceTestData.NOT_VALID_RESOURCE_ID);
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.body().asString(), "{}");
    }

    @Test(dataProviderClass = ResourceTestData.class, dataProvider = "dataResourceId")
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetSingleResourceNotFoundExtended(int dataResource) {
        Response response = resourceService.getResourceById(ResourceEndPoints.UNKNOWN, dataResource);
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.body().asString(), "{}");
    }
}