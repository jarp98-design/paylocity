package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;
import utils.ConfigReader;
import utils.AuthApi;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

public class UIBaseTest {

    protected static Map<String, String> authCookies;
    public WebDriver driver;

    @BeforeSuite
    public void apiLogin() throws InterruptedException {
        authCookies = AuthApi.loginAndGetCookies();
    }

    @BeforeMethod
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(ConfigReader.get("baseUrl"), authCookies);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
