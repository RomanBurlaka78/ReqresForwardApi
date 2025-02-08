package api.base;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class ListenersUtils implements ITestListener {

public  WebDriver driver;


    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Start test: " + result.getName());
        System.out.println("------------------------");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test success : " + result.getName());
        System.out.println("***************************");
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        System.out.println("***FAILED******FAILED******FAILED***");
        System.out.println("Test failed: " + testResult.getName());
        if (!testResult.isSuccess()) {
            Allure.addAttachment(
                    "screenshot.png",
                    "image/png",
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)),
                    "png");
        }


    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getName());
    }


    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
        System.out.println(String.format("Test failed with timeout %s", (result.getStartMillis()-result.getEndMillis())));
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Class  " + context.getName());

    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Class finished " + context.getName());
        System.out.println("****finished****************finished*******");
    }
}
