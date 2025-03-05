package api.listeners;

import api.filters.FilterExperement;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);
    FilterExperement filterExperement = new FilterExperement();

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Started: " + result.getName());

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Passed successfully");
        logger.info("*************************************************************************");
        Allure.addAttachment("Test Passed", "text/plain", "Test passed successfully!");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: {}", result.getName());
        logger.error(filterExperement.filter(RestAssured.requestSpecification, ));
        Allure.addAttachment("Test Failed", "text/plain", "Test failed with exception: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: " + result.getName());
    }
}
