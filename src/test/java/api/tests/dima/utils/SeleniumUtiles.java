package api.tests.dima.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
public abstract class SeleniumUtiles {

    private static WebDriver driver;
    private static WebDriverWait webDriverWait;

    public  WebDriver getDriver() {
        if (driver != null) {
            return driver;
        }
        driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));
        //driver = new ChromeDriver();
        return driver;
    }
    public WebDriverWait getWebDriverWait(){
        if (webDriverWait != null) {
            return webDriverWait;
        }
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return webDriverWait;
    }
}
