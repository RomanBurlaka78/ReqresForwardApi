package api.tests;

import api.base.ApiEndPoints;
import api.steps.DelayedSteps;
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
    private DelayedSteps delayedSteps = new DelayedSteps();
    private ApiEndPoints apiEndPoints = new ApiEndPoints();


    @Test
    @Description
    @Story("Get delayed")
    public void testGetDelayedResponse() {
        Response delayedResponse = delayedSteps.getDelayedResponse(apiEndPoints.getUSER_DELAY(), 3);
        Assert.assertEquals(delayedResponse.getStatusCode(), 200);
        Assert.assertEquals(delayedResponse.jsonPath().getString("total"), "12");
    }

}
