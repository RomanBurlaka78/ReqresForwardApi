package api.tests.dima.utils;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class AttachmentsForTests {

    @Attachment(value = "Page Screenshot", type = "image/png", fileExtension = ".png")
    public static byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "JSON schema", type = "text/plain", fileExtension = ".txt")
    public static String attachDataJson() throws IOException {
        File file = new File("src/main/resources/JsonSchemaForListOfUsers.json");
        return FileUtils.readFileToString(file, "UTF-8");
    }
}
