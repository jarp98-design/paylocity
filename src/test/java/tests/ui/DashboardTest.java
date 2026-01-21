package tests.ui;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import java.util.List;
import org.testng.Assert;

public class DashboardTest extends BaseTest {

    @Test
	public void addEmployeeTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertEquals(driver.getTitle(), "Employees - Paylocity Benefits Dashboard");

        DashboardPage dashboardPage = new DashboardPage(driver);

        dashboardPage.addNewEmployee(
            ConfigReader.get("firstName"), 
            ConfigReader.get("lastName"), 
            ConfigReader.get("dependents")
        );

        List<String> employees = dashboardPage.getAllEmployeeNames();
        String fullName = ConfigReader.get("firstName") + " " + ConfigReader.get("lastName");
        Assert.assertTrue(employees.contains(fullName), "New employee " + fullName + " was not added successfully");

        String actualDependents = dashboardPage.getEmployeeDependents(
            ConfigReader.get("firstName"), 
            ConfigReader.get("lastName")
        );
        Assert.assertEquals(actualDependents, ConfigReader.get("dependents"), "Dependents count does not match for employee " + fullName);
	}

    @Test
	public void employeesSalaryTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertEquals(driver.getTitle(), "Employees - Paylocity Benefits Dashboard");

        DashboardPage dashboardPage = new DashboardPage(driver);
        String employeeSalaryMismatch = dashboardPage.verifyAllEmployeesSalary();
        if (!employeeSalaryMismatch.isEmpty()) {
            Assert.fail("Salary mismatch found for employee: " + employeeSalaryMismatch);
        }
	}

    @Test
	public void employeesGrossPayTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertEquals(driver.getTitle(), "Employees - Paylocity Benefits Dashboard");

        DashboardPage dashboardPage = new DashboardPage(driver);
        String employeeGrossPayMismatch = dashboardPage.verifyAllEmployeesGrossPay();
        if (!employeeGrossPayMismatch.isEmpty()) {
            Assert.fail("Gross Pay mismatch found for employee: " + employeeGrossPayMismatch);
        }
	}

    @Test
	public void employeesBenefitsCostTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertEquals(driver.getTitle(), "Employees - Paylocity Benefits Dashboard");

        DashboardPage dashboardPage = new DashboardPage(driver);
        String employeeBenefitsCostMismatch = dashboardPage.verifyAllEmployeesBenefitsCost();
        if (!employeeBenefitsCostMismatch.isEmpty()) {
            Assert.fail("Benefits Cost mismatch found for employee: " + employeeBenefitsCostMismatch);
        }
	}

    @Test
	public void employeesNetPayTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertEquals(driver.getTitle(), "Employees - Paylocity Benefits Dashboard");

        DashboardPage dashboardPage = new DashboardPage(driver);
        String employeeNetPayMismatch = dashboardPage.verifyAllEmployeesNetPay();
        if (!employeeNetPayMismatch.isEmpty()) {
            Assert.fail("Net Pay mismatch found for employee: " + employeeNetPayMismatch);
        }
	}

    @Test (dependsOnMethods = {"addEmployeeTest"})
	public void editEmployeeTest() throws InterruptedException {
        String newFirstName = ConfigReader.get("newFirstName");
        String newLastName = ConfigReader.get("newLastName");
        String newDependents = ConfigReader.get("newDependents");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertEquals(driver.getTitle(), "Employees - Paylocity Benefits Dashboard");

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(
            dashboardPage.editEmployee(
                ConfigReader.get("firstName"), 
                ConfigReader.get("lastName"),
                ConfigReader.get("newFirstName"),
                ConfigReader.get("newLastName"),
                ConfigReader.get("newDependats")
            ), 
            "Employee " + ConfigReader.get("firstName") + " " + ConfigReader.get("lastName") + " was not edited successfully or the employee was not found"
        );

        List<String> employees = dashboardPage.getAllEmployeeNames();
        String fullName = ConfigReader.get("newFirstName") + " " + ConfigReader.get("newLastName");
        Assert.assertTrue(employees.contains(fullName), "Employee " + fullName + " was not updated successfully");

        String actualDependents = dashboardPage.getEmployeeDependents(
            ConfigReader.get("newFirstName"), 
            ConfigReader.get("newLastName")
        );
        Assert.assertEquals(actualDependents, newDependents, "Dependents count was not properly updated for employee " + fullName);
	}

    @Test (dependsOnMethods = {"editEmployeeTest"})
	public void deleteEmployeeTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertEquals(driver.getTitle(), "Employees - Paylocity Benefits Dashboard");

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(
            dashboardPage.deleteEmployee(
                ConfigReader.get("newFirstName"), 
                ConfigReader.get("newLastName")
            ),
            "Employee " + ConfigReader.get("newFirstName") + " " + ConfigReader.get("newLastName") + " was not deleted successfully or was not found"
        );
	}
}
