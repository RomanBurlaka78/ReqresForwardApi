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
import static io.restassured.RestAssured.given;

//@Listeners({TestListener.class})
@Epic("tests version Dima")
@Feature("Mixed tests that are being executed via Rest Assured and Selenium")
public class MixedAPIAndSeleniumTests extends SeleniumUtiles {

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
                when().
                    get("https://simple-grocery-store-api.glitch.me" + products).
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
    public void testDaysOfLuckInMenu(){
        String expectedResult = "Горящие товары";
        getDriver().manage().window().maximize();
        Allure.step("get the URL", () -> {
            getDriver().get("https://aliexpress.ru/?spm=a2g2w.productlist.0.0.14d04aa6XaBpjY");
        });
        Allure.step("fetch the element and make assertion", () -> {
            WebElement element = getWebDriverWait().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.xpath("//span[contains(text(),'Горящие товары')]"))));
            Assert.assertEquals(element.getText(), expectedResult +1 );
        });
    }

    @Test
    @Story("Tests on Selenium")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dima")
    @Description("This test is entended to pass")
    public void testAv(){
        getDriver().get("https://av.by/");
        getDriver().manage().window().maximize();
        WebElement element = getDriver().findElement(By.xpath("//div[@class='header__logo']"));
        Assert.assertEquals(element.getDomAttribute("class"),"header__logo");
    }
}

