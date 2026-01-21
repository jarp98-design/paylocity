package pages;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Cookie;

import utils.WaitUtil;
import utils.ConfigAPI;

public class LoginPage {

    private WebDriver driver;
    private WaitUtil wait;

    private By username = By.id("Username");
    private By password = By.id("Password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMsg = By.cssSelector("div[class='text-danger validation-summary-errors']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtil(driver, 10);
    }

    public void open(String url) throws InterruptedException {
        driver.get(url);
        wait.waitForPageToLoad();
    }

    public void open(String url, Map<String, String> authCookies) throws InterruptedException {
        driver.get(url);

        authCookies.forEach((name, value) -> {
            Cookie cookie = new Cookie.Builder(name, value)
                .domain(ConfigAPI.DOMAIN)
                .path("/")
                .build();
            driver.manage().addCookie(cookie);
        });

        driver.navigate().refresh();
        wait.waitForPageToLoad();
    }

    public void enterUsername(String user) {
        driver.findElement(username).sendKeys(user);
    }

    public void enterPassword(String pass) {
        driver.findElement(password).sendKeys(pass);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void login(String user, String pass) throws InterruptedException {
        enterUsername(user);
        enterPassword(pass);
        clickLogin();
        wait.waitForPageToLoad();
    }

    public String errorMsg() {
        try {
            return driver.findElement(errorMsg).getText();
        } catch (Exception e) {
            return "";
        }
    }
}
