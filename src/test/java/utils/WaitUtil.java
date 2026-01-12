package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtil {

    private WebDriver driver;
    private WebDriverWait wait;

    public WaitUtil(WebDriver driver, long timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public void waitForPageToLoad() throws InterruptedException {
        ExpectedCondition<Boolean> pageLoadCondition = driver ->
            ((JavascriptExecutor) driver)
                .executeScript("return document.readyState")
                .equals("complete");

        Thread.sleep(500);
        wait.until(pageLoadCondition);
    }

    public WebElement waitForVisibility(By locator) throws InterruptedException {
        Thread.sleep(500);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) throws InterruptedException {
        Thread.sleep(500);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
