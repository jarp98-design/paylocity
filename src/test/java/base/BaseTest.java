package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;
import utils.ConfigReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    public WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(ConfigReader.get("baseUrl"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
