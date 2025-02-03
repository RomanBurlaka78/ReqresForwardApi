package api.tests.dima.test;

import api.tests.dima.utils.SeleniumUtiles;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

import static api.tests.dima.utils.SeleniumUtiles.*;
import static api.tests.dima.utils.SeleniumUtiles.getDriver;
import static io.restassured.RestAssured.given;

//@Listeners({TestListener.class})
@Epic("Api tests")
@Feature("Mixed tests that are being executed via Rest Assured and Selenium")
public class MixedAPIAndSeleniumTests {

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
                        get("https://simple-grocery-store-api.glitch.me" + products).
                        then().
                        statusCode(200).
                        extract().
                        response();

        System.out.println(response.asPrettyString());

    }

//    @Test(enabled = false)
//    @Story("Tests on Selenium")
//    @Severity(SeverityLevel.NORMAL)
//    @Owner("Dima")
//    @Description("This test is entended to fail for porpose of taking sceenshot and render it to a Allure report")
//    public void testLogotypeOfUdemyMainPage() throws InterruptedException {
//
//        String expectedValue = "lazy" + "y";
//        Allure.step("get to web page www.udemy.com", () -> {
//            SeleniumUtiles.getDriver().get("https://www.udemy.com/");
//        });
//        Allure.step("fetch the element and make assertion", () -> {
//            WebElement element = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(SeleniumUtiles.getDriver().findElement(By.xpath("//img[@alt='Udemy']"))));
//            Assert.assertEquals(element.getAttribute("loading"), expectedValue);
//        });
//    }

    @Test
    @Story("Tests on Selenium")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Description("This test is entended to fail for porpose of taking sceenshot and render it to a Allure report")
    public void testDaysOfLuckInMenu(){
        String expectedResult = "Горящие товары";
        getDriver().manage().window().maximize();
        Allure.step("get the URL", () -> {
            getDriver().get("https://aliexpress.ru/?spm=a2g2w.productlist.0.0.14d04aa6XaBpjY");
        });
        Allure.step("fetch the element and make assertion", () -> {
            WebElement element = getWebDriverWait().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.xpath("//span[contains(text(),'Горящие товары')]"))));
            Assert.assertEquals(element.getText(), expectedResult);
        });
    }
}

