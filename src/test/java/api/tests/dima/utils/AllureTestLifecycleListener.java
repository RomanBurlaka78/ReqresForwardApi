package api.tests.dima.utils;

import api.base.ProjectUtils;
import io.qameta.allure.Attachment;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import static io.qameta.allure.model.Status.BROKEN;
import static io.qameta.allure.model.Status.FAILED;

public class AllureTestLifecycleListener implements TestLifecycleListener {

    public AllureTestLifecycleListener() {
    }

    @Override
    public void beforeTestStop(TestResult result) {
        if (FAILED == result.getStatus() || BROKEN == result.getStatus()) {
            WebDriver driver = SeleniumUtiles.getDriver();
            AttachmentsForTests.saveScreenshot(driver);
        }
    }

}
