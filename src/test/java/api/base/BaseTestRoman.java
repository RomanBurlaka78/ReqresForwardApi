package api.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public abstract class BaseTestRoman {

    public static Specifications specifications = new Specifications();
    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;

    public static void spec(){
        requestSpec = specifications.setupRequest().filter(new AllureRestAssured());
        responseSpec = specifications.setupResponse();
    }

    public static void setupSpec() {
        requestSpec = specifications.setupRequest();
        responseSpec = specifications.setupResponse();

    }
    @BeforeClass
    protected void addSpecToClass(){
        specifications.installSpec();
    }

    @BeforeMethod
    protected void beforeMethod(Method method) {
        ProjectUtils.logf("Запускается %s.%s", this.getClass().getName(), method.getName());

    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        ProjectUtils.logf("Время выполнения %.3f сек", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000.0);
    }
}
