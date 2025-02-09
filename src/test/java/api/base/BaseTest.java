package api.base;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public abstract class BaseTest {

    private static Specifications specifications;
    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;

    public static Specifications getSpecifications() {
        if (specifications == null) {
            specifications = new Specifications();
        }
        return specifications;
    }

    public static void spec(){
        requestSpec = getSpecifications().setupRequest();
        responseSpec = getSpecifications().setupResponse();
    }

    public static void specNull() {
        requestSpec = null;
        responseSpec = null;
    }

    @BeforeMethod
    protected void beforeMethod(Method method) {
        ProjectUtils.logf("Запускается %s.%s", this.getClass().getName(), method.getName());
        spec();
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        ProjectUtils.logf("Время выполнения %.3f сек", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000.0);
        specNull();
    }
}
