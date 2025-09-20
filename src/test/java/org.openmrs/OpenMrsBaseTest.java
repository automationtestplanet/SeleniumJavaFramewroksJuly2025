package org.openmrs;

import org.openmrs.pageobjects.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class OpenMrsBaseTest {

    public WebDriver driver;
    public BasePage basePage;
    public LoginPage loginPage;
    public HomePage homePage;
    public RegistrationPage registrationPage;
    public PatientDetailsPage patientDetailsPage;
    public FindPatientPage findPatientPage;
    public VisitsPage visitsPage;
    public AttachmentsPage attachmentsPage;
    public CommonUtils commonUtils;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuit() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + TestProperties.getTestProperty("chrome.driver.path"));

    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        driver = new ChromeDriver();
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        registrationPage = new RegistrationPage(driver);
        patientDetailsPage = new PatientDetailsPage(driver);
        findPatientPage = new FindPatientPage(driver);
        visitsPage = new VisitsPage(driver);
        attachmentsPage = new AttachmentsPage(driver);
        commonUtils = new CommonUtils(driver);
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        basePage.navigateToUrl(TestProperties.getTestProperty("openmrs.qa"));
        Assert.assertEquals(loginPage.getPageTitle(), "Login", "Login page is not available");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        loginPage.loginToOpenMrs(TestProperties.getTestProperty("openmrs.user.name"), TestProperties.getTestProperty("openmrs.password"), "Registration Desk");
        Assert.assertEquals(loginPage.getPageTitle(), "Home", "Home Page is not available, Login Failed");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        homePage.logoutApplication();
        Assert.assertEquals(loginPage.getPageTitle(), "Login", "Login page is not available");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        driver.close();
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        driver = null;
        basePage = null;
        loginPage = null;
        homePage = null;
        registrationPage = null;
        patientDetailsPage = null;
        findPatientPage = null;
        visitsPage = null;
        attachmentsPage = null;
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuit() {

    }

    @DataProvider(name = "RegisterPatientTestData")
    public Iterator<String[]> registerPatientTestData() {
        String excelFilePath = System.getProperty("user.dir") + TestProperties.getTestProperty("data.driven.test.data");
        List<String[]> testData = ExcelUtils.readDataDrivenTestDataFromExcel(excelFilePath, "RegisterPatientTestData");
        Assert.assertNotNull(testData);
        return testData.iterator();
    }

    @DataProvider(name = "FindPatientTestData")
    public Iterator<String[]> findPatientTestData() {
        String excelFilePath = System.getProperty("user.dir") + TestProperties.getTestProperty("data.driven.test.data");
        List<String[]> testData = ExcelUtils.readDataDrivenTestDataFromExcel(excelFilePath, "FindPatientTestData");
        Assert.assertNotNull(testData);
        return testData.iterator();
    }

    @DataProvider(name = "ActivateVisitsTestData")
    public Iterator<String[]> activateVisitsTestData() {
        String excelFilePath = System.getProperty("user.dir") + TestProperties.getTestProperty("data.driven.test.data");
        List<String[]> testData = ExcelUtils.readDataDrivenTestDataFromExcel(excelFilePath, "ActivateVisitsTestData");
        Assert.assertNotNull(testData);
        return testData.iterator();
    }

    @DataProvider(name = "DeletePatientTestData")
    public Iterator<String[]> deletePatientTestData() {
        String excelFilePath = System.getProperty("user.dir") + TestProperties.getTestProperty("data.driven.test.data");
        List<String[]> testData = ExcelUtils.readDataDrivenTestDataFromExcel(excelFilePath, "DeletePatientTestData");
        Assert.assertNotNull(testData);
        return testData.iterator();
    }

    @DataProvider(name = "TestData")
    public Iterator<Object[]> hybridFrameworkTestData(Method testMethod) {
        String excelFilePath = System.getProperty("user.dir") + TestProperties.getTestProperty("hybrid.test.data");
        Iterator<Object[]> testData = ExcelUtils.readHybridFrameworkTestDataFromExcel(excelFilePath, "TestData", testMethod.getName());
        Assert.assertNotNull(testData);
        return testData;
    }

//    public static void main(String[] args) {
//        String excelFilePath = System.getProperty("user.dir") + TestProperties.getTestProperty("hybrid.test.data");
//        ExcelUtils.readHybridFrameworkTestDataFromExcel(excelFilePath, "TestData", "registerPatientTest");
//        ExcelUtils.readHybridFrameworkTestDataFromExcel(excelFilePath, "TestData", "findPatientTest");
//        ExcelUtils.readHybridFrameworkTestDataFromExcel(excelFilePath, "TestData", "activateVisitsAndAddAttachmentsTest");
//        ExcelUtils.readHybridFrameworkTestDataFromExcel(excelFilePath, "TestData", "deletePatientTest");
//    }

}
