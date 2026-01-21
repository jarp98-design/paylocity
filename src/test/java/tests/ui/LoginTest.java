package tests.ui;

import base.BaseTest;
import org.testng.annotations.Test;

import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import org.testng.Assert;

public class LoginTest extends BaseTest {
    
    @Test
	public void validLoginTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertEquals(driver.getTitle(), "Employees - Paylocity Benefits Dashboard");
	}

    @Test
	public void noDataLoginTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");
        Assert.assertEquals(driver.getTitle(), "Log In - Paylocity Benefits Dashboard");
        Assert.assertTrue(loginPage.errorMsg().contains("There were one or more problems that prevented you from logging in"), "Error message does not match expected text or it was not displayed");
	}

    @Test
	public void wrongUserPasswordLoginTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("WrongUser", "WrongPassword");
        Assert.assertEquals(driver.getTitle(), "Log In - Paylocity Benefits Dashboard");
        Assert.assertTrue(loginPage.errorMsg().contains("There were one or more problems that prevented you from logging in"), "Error message does not match expected text or it was not displayed");
	}
}   