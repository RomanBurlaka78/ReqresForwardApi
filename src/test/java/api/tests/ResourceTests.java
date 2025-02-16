package api.tests;

import api.base.BaseTest;
import api.steps.ResourceSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@Epic("Reqres API Tests")
@Feature("Resource Management")
public class ResourceTests extends BaseTest {

    private ResourceSteps resourceSteps = new ResourceSteps();

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
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetSingleResourceById() {
        Response response = resourceSteps.getResourceById(2);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("data.name"), "fuchsia rose");
    }

    @Test
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetListResource() {
        Response response = resourceSteps.getListResource();
        Assert.assertEquals(response.getStatusCode(), 200);


    }

    @Test
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetSingleResourceNotFound() {
        Response response = resourceSteps.getResourceById(23);
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.body().asString(), "{}");
    }

    @Test(dataProvider = "dataResourceId")
    @Description("Test to verify getting a resource by ID")
    @Story("Get Resource")
    public void testGetSingleResourceNotFoundExtended(int dataResource) {
        Response response = resourceSteps.getResourceById(dataResource);
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.body().asString(), "{}");
    }
}