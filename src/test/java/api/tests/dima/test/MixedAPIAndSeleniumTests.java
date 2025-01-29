package api.tests.dima.test;

import api.base.ProjectUtils;
import api.tests.dima.utils.SeleniumUtiles;
import api.tests.dima.utils.TestListener;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;


import java.io.IOException;

import static io.restassured.RestAssured.given;
//@Listeners({TestListener.class})
@Epic("Api tests")
@Feature("Mixed tests that are being executed via Rest Assured and Selenium")
public class MixedAPIAndSeleniumTests{


    @Test
    @Description("Get all the resources (books) available in the API, at current time. With allure parameters and attachment")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Story("Rest Assured tests")
    public void testgetAllTheBooks() throws IOException {
        String products = "/products";
        Allure.parameter("end point for API", products);
        Response response =
                given().
                    filter(new AllureRestAssured()).
                when().
                    get("https://simple-grocery-store-api.glitch.me"+products).
                then().
                    statusCode(200).
                    extract().
                    response();

        System.out.println(response.asPrettyString());

    }

    @Test
    @Story("Tests on Selenium")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Description("This test is entended to fail for porpose of taking sceenshot and render it to a Allure report")
    public void testLogotypeOfUdemyMainPage(){

        String expectedValue = "lazy" + "y";
        Allure.step("get to web page www.udemy.com", () -> {
            SeleniumUtiles.getDriver().get("https://www.udemy.com/");
        });
        Allure.step("fetch the element and make assertion", () -> {
            WebElement element = SeleniumUtiles.getWebDriverWait().until(ExpectedConditions.visibilityOf(SeleniumUtiles.getDriver().findElement(By.xpath("//img[@src='https://frontends.udemycdn.com/frontends-homepage/staticx/udemy/images/v7/logo-udemy.svg']"))));
            Assert.assertEquals(element.getAttribute("loading"), expectedValue);
        });
    }








}
