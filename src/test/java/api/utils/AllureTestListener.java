package api.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.qameta.allure.model.Status.BROKEN;
import static io.qameta.allure.model.Status.FAILED;

public class AllureTestListener implements TestLifecycleListener {

    private Logger logger = LogManager.getLogger(AllureTestListener.class);

    @Override
    public void beforeTestStop(TestResult result) {

        if (FAILED == result.getStatus() || BROKEN == result.getStatus()){
            Allure.attachment("Test Failed","Test failed with exception: " + result.getAttachments());
        }
    }

}
