package api.listeners;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Started: " + result.getName());

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Passed successfully");
        logger.info("*************************************************************************");
        Allure.attachment("Test Passed", "Test passed successfully!");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed");
        Allure.attachment("Test Failed","Test failed with exception: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: " + result.getName());
    }
}
