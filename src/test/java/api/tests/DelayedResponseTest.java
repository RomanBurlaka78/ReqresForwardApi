package api.tests;

import api.base.ApiEndPoints;
import api.services.DelayedService;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Reqres API Tests")
@Feature("Delayed response tests")
@Link("https://reqres.in/")
@Listeners(api.listeners.TestListener.class)
public class DelayedResponseTest {
    private DelayedService delayedService = new DelayedService();

    @Test
    @Description
    @Story("Get delayed")
    public void testGetDelayedResponse() {
        Response delayedResponse = delayedService.getDelayedResponse(ApiEndPoints.USER_DELAY, 3);
        Assert.assertEquals(delayedResponse.getStatusCode(), 200);
        Assert.assertEquals(delayedResponse.jsonPath().getString("total"), "12");
    }

}
