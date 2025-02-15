package api.tests;

import api.base.BaseTest;
import api.steps.DelayedSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Reqres API Tests")
@Feature("Delayed response")
@Link("https://reqres.in/")


public class DelayedResponseTest extends BaseTest {
    private DelayedSteps delayedSteps = new DelayedSteps();

    @Test
    @Description
    @Story("Get delayed")
    public void testGetDelayedResponse() {
        Response delayedResponse = delayedSteps.getDelayedResponse(3);
        Assert.assertEquals(delayedResponse.getStatusCode(), 200);
        Assert.assertEquals(delayedResponse.jsonPath().getString("total"), "12");
    }

}
