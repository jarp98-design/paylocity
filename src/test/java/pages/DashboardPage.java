package pages;

import java.io.ObjectInputFilter.Config;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.WaitUtil;
import utils.ConfigReader;

public class DashboardPage {

    private WebDriver driver;
    private WaitUtil wait;

    private By addEmployeeBtn = By.id("add");
    private By firstname = By.id("firstName");
    private By lastname = By.id("lastName");
    private By dependents = By.id("dependants");
    private By addBtn = By.id("addEmployee");
    private By logoutLink = By.linkText("Log Out");
    private By employeesTable = By.cssSelector("#employeesTable tbody tr");
    private By firstNameRow = By.xpath("td[3]");
    private By lastNameRow = By.xpath("td[2]");
    private By dependantsRow = By.xpath("td[4]");
    private By salaryRow = By.xpath("td[5]");
    private By grossPayRow = By.xpath("td[6]");
    private By benefitsCostRow = By.xpath("td[7]");
    private By netPayRow = By.xpath("td[8]");
    private By deleteEmployeeBtn = By.id("deleteEmployee");
    private By deleteFirstName = By.id("deleteFirstName");
    private By deleteLastName = By.id("deleteLastName");
    private By editEmployeeIcon = By.cssSelector("i.fas.fa-edit"); 
    private By deleteEmployeeIcon = By.cssSelector("i.fas.fa-times");
    private By updateEmployeeBtn = By.id("updateEmployee");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtil(driver, 10);
    }

    public void logout() throws InterruptedException {
        driver.findElement(logoutLink).click();
        wait.waitForPageToLoad();
    }

    public void clickAddEmployee() {
        driver.findElement(addEmployeeBtn).click();
    }

    public void enterFirstName(String firstname) {
        driver.findElement(this.firstname).clear();
        driver.findElement(this.firstname).sendKeys(firstname);
    }

    public void enterLastName(String lastname) {
        driver.findElement(this.lastname).clear();
        driver.findElement(this.lastname).sendKeys(lastname);
    }

    public void enterDependents(String dependents) {
        driver.findElement(this.dependents).clear();
        driver.findElement(this.dependents).sendKeys(dependents);
    }

    public void clickAdd() {
        driver.findElement(addBtn).click();
    }

    public List<WebElement> getEmployeesTable() throws InterruptedException {
        wait.waitForVisibility(employeesTable);
        return driver.findElements(employeesTable);
    }

    public void addNewEmployee(String firstname, String lastname, String dependents) throws InterruptedException {
        clickAddEmployee();
        enterFirstName(firstname);
        enterLastName(lastname);
        enterDependents(dependents);
        clickAdd();
        wait.waitForPageToLoad();
    }

    public List<String> getAllEmployeeNames() throws InterruptedException {
        List<WebElement> rows = getEmployeesTable();
        List<String> employeeNames = new java.util.ArrayList<>();

        for (WebElement row : rows) {
            String fullName = row.findElement(firstNameRow).getText() + " " + row.findElement(lastNameRow).getText();
            employeeNames.add(fullName);
        }

        return employeeNames;
    }

    public String getEmployeeDependents(String firstName, String lastName) throws InterruptedException {
        List<WebElement> rows = getEmployeesTable();
        
        for (WebElement row : rows) {
            String fname = row.findElement(firstNameRow).getText();
            String lname = row.findElement(lastNameRow).getText();
            
            if (fname.equals(firstName) && lname.equals(lastName)) {
                return row.findElement(dependantsRow).getText();
            }
        }
        
        return "";
    }

    public String verifyAllEmployeesSalary() throws InterruptedException {
        List<WebElement> rows = getEmployeesTable();

        double expectedSalary = Double.parseDouble(ConfigReader.get("salaryPerCheck")) * Double.parseDouble(ConfigReader.get("paychecks"));

        for (WebElement row : rows) {
            String fullName = row.findElement(firstNameRow).getText() + " " + row.findElement(lastNameRow).getText();
            String salary = row.findElement(salaryRow).getText();
            
            if (!salary.equals(String.valueOf(String.format("%.2f", expectedSalary)))) {
                return fullName;
            }
        }   
        
        return "";
    }

    public String verifyAllEmployeesGrossPay() throws InterruptedException {
        List<WebElement> rows = getEmployeesTable();

        double expectedGrossPay = Double.parseDouble(ConfigReader.get("salaryPerCheck"));

        for (WebElement row : rows) {
            String fullName = row.findElement(firstNameRow).getText() + " " + row.findElement(lastNameRow).getText();
            String grossPay = row.findElement(grossPayRow).getText();

            if (!grossPay.equals(String.format("%.2f", expectedGrossPay))) {
                return fullName;
            }
        }   
        
        return "";
    }

    public String verifyAllEmployeesBenefitsCost() throws InterruptedException {
        List<WebElement> rows = getEmployeesTable();

        double benefitsPerEmployee = Double.parseDouble(ConfigReader.get("benefitsPerEmployee"));
        double dependentsCost = Double.parseDouble(ConfigReader.get("dependentsCost"));
        double paychecks = Double.parseDouble(ConfigReader.get("paychecks"));

        double costPerEmployee = benefitsPerEmployee / paychecks;

        for (WebElement row : rows) {
            String fullName = row.findElement(firstNameRow).getText() + " " + row.findElement(lastNameRow).getText();
            String ndependents = row.findElement(dependantsRow).getText();
            String benefitsCost = row.findElement(benefitsCostRow).getText();

            double costPerDependents = (dependentsCost * Double.parseDouble(ndependents)) / paychecks;
            double totalCostPerEmployee = costPerEmployee + costPerDependents;

            if (!benefitsCost.equals(String.format("%.2f", totalCostPerEmployee))) {
                return fullName;
            }
        }   
        
        return "";
    }

    public String verifyAllEmployeesNetPay() throws InterruptedException {
        List<WebElement> rows = getEmployeesTable();

        double salaryPerCheck = Double.parseDouble(ConfigReader.get("salaryPerCheck"));

        for (WebElement row : rows) {
            String fullName = row.findElement(firstNameRow).getText() + " " + row.findElement(lastNameRow).getText();
            
            String grossPay = row.findElement(grossPayRow).getText();
            String benefitsCost = row.findElement(benefitsCostRow).getText();
            String netPay = row.findElement(netPayRow).getText();

            double netPayExpected = Double.parseDouble(grossPay) - Double.parseDouble(benefitsCost);

            if (!netPay.equals(String.format("%.2f", netPayExpected))) {
                return fullName;
            }
        }   
        
        return "";
    }

    public void clickEditEmployeeIcon(WebElement row) throws InterruptedException {
        row.findElement(editEmployeeIcon).click();
    }

    public void clickDeleteEmployeeIcon(WebElement row) throws InterruptedException {
        row.findElement(deleteEmployeeIcon).click();
    }

    public void clickDeleteEmployee() throws InterruptedException {
        wait.waitForClickable(deleteEmployeeBtn);
        driver.findElement(deleteEmployeeBtn).click();
    }

    public String getDeleteFirstName() {
        return driver.findElement(deleteFirstName).getText();
    }

    public String getDeleteLastName() {
        return driver.findElement(deleteLastName).getText();
    }

    public boolean editEmployee(String firstName, String lastName, String newFirstName, String newLastName, String newDependats) throws InterruptedException {
        List<WebElement> rows = getEmployeesTable();
        
        for (WebElement row : rows) {
            String fname = row.findElement(firstNameRow).getText();
            String lname = row.findElement(lastNameRow).getText();
            
            if (fname.equals(firstName) && lname.equals(lastName)) {
                clickEditEmployeeIcon(row);
                enterFirstName(newFirstName);
                enterLastName(newLastName);
                enterDependents(newDependats);
                clickUpdateBtn();
                wait.waitForPageToLoad();
                return true;    
            }
        }
        return false;
    }

    public boolean deleteEmployee(String firstName, String lastName) throws InterruptedException {
        List<WebElement> rows = getEmployeesTable();
        
        for (WebElement row : rows) {
            String fname = row.findElement(firstNameRow).getText();
            String lname = row.findElement(lastNameRow).getText();
            
            if (fname.equals(firstName) && lname.equals(lastName)) {
                clickDeleteEmployeeIcon(row);
                String delFirstName = getDeleteFirstName();
                String delLastName = getDeleteLastName();
                if (delFirstName.equals(firstName) && delLastName.equals(lastName)) {
                    clickDeleteEmployee();
                    wait.waitForPageToLoad();
                    return true;
                }
            }
        }
        return false;
    }

    public void clickUpdateBtn() {
        driver.findElement(updateEmployeeBtn).click();
    }
}  
