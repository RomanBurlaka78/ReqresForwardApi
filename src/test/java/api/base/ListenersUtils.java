package api.base;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenersUtils implements ITestListener {
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
    public void onTestFailure(ITestResult result) {
        System.out.println("***FAILED******FAILED******FAILED***");
        System.out.println("Test failed: " + result.getName());
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
