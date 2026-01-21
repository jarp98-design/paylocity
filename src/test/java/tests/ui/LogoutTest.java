package tests.ui;

import base.BaseTest;
import org.testng.annotations.Test;

import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import org.testng.Assert;

public class LogoutTest extends BaseTest {
    
    @Test
	public void logoutTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertEquals(driver.getTitle(), "Employees - Paylocity Benefits Dashboard");

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.logout();
        Assert.assertEquals(driver.getTitle(), "Log In - Paylocity Benefits Dashboard");
	}
}   