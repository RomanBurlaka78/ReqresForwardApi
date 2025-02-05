package api.tests.dima.utils;

import api.base.ProjectUtils;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestListener extends SeleniumUtiles implements ITestListener {

    @Override
    @Description("Вариант с сохранением скриншота локально")
    public void onTestFailure(ITestResult result) {
            TakesScreenshot takesScreenshot= (TakesScreenshot) getDriver();
            File temporary = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File screenShot = new File("C:\\Users\\user\\IdeaProjects\\ReqresForwardApi\\ScreenShots\\wholeScreenShot.png");
        try {
            FileUtils.copyFile(temporary, screenShot);
            screenShotAtachment();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Attachment(value = "screenshot", type = "image/png", fileExtension = ".png")
    public byte[] screenShotAtachment() throws IOException {
        return Files.readAllBytes(Paths.get("C:/Users/user/IdeaProjects/ReqresForwardApi/ScreenShots/wholeScreenShot.png"));
        //return ((TakesScreenshot)ProjectUtils.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

}
