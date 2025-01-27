package api.tests.dima.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumUtiles {

    private static WebDriver driver = new ChromeDriver();

    private static final WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds((10)));

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWebDriverWait(){
        return webDriverWait;
    }
}
