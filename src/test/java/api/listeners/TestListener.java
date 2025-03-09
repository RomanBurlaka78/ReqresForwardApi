package api.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static io.qameta.allure.model.Status.BROKEN;
import static io.qameta.allure.model.Status.FAILED;

public class TestListener implements ITestListener, TestLifecycleListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);
    private ITestResult iTestResult;

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("");
        logger.info("Test name: " + result.getName());
        logger.info(result.getTestClass());
        logger.info("Passed successfully");
        logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Allure.attachment("Test Passed", "Test passed successfully!");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("");
        logger.error("Test name: " + result.getName());
        logger.error(result.getTestClass());
        logger.error("    ******************       *******               ****       ****              ********");
        logger.error("    ******************      *********              ****       ****              ****   ***");
        logger.error("    *****                  ****   ****             ****       ****              ****    ***");
        logger.error("    ********              ****     ****            ****       ****              ****     ****");
        logger.error("    *****                ****       ****           ****       ****              ****      ****");
        logger.error(" ***********            *****************          ****       ****              ****     ****");
        logger.error(" ***********           *******************         ****       ****              ****    ***");
        logger.error("    *****             ****             ****        ****       ************      ****   ***");
        logger.error("    *****            ****               ****       ****       ************      ********");
        logger.error(result.getThrowable().fillInStackTrace());
        logger.error("------------------------------------------------------------------------");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: " + result.getName());
    }

    @Override
    public void beforeTestStop(TestResult result) {

        if (FAILED == result.getStatus() || BROKEN == result.getStatus()){
//            logger.error("Test failed");
//            logger.info("------------------------------------------------------------------------");
//            Allure.attachment("Test Failed","Test failed with exception: " + result.getStatusDetails());
            //Allure.attachment("Test Failed","Test failed with exception: " + iTestResult.getThrowable().getMessage());
        }
    }

    public void onFinish(ITestContext context) {
        // not implemented
    }
}
