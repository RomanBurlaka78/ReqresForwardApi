package api.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public abstract class BaseTest {

    private static final Specifications specifications = new Specifications();
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    @BeforeSuite
    public void init(){
        specifications.installSpec();
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestResult testResult){
        ProjectUtils.logf("Запускается %s.%s", this.getClass().getName(), method.getName());

    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        ProjectUtils.logf("Время выполнения %.3f сек", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000.0);
    }
}
