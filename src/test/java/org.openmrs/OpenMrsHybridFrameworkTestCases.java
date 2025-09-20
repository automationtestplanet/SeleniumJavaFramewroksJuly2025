package org.openmrs;

import org.openmrs.pageobjects.TestProperties;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class OpenMrsHybridFrameworkTestCases extends OpenMrsBaseTest {

    @Test(priority = 0, dataProvider = "TestData")
    public void registerPatientTest(Map<String,String> testData) {
        Assert.assertTrue(homePage.verifyModuleTile("Register a patient"), "Register Patient Module is not displayed");
        commonUtils.captureScreenshot();
        homePage.clickModuleTile("Register a patient");
        Assert.assertTrue(homePage.verifyModulePage("Register a patient"), "Register Page is not displayed");
        commonUtils.captureScreenshot();
        registrationPage.enterPatientDetails(testData.get("Name"), testData.get("Gender"), testData.get("DateOfBirth"), testData.get("Address"), testData.get("PhoneNumber"), testData.get("Relatives"));
        Assert.assertTrue(registrationPage.verifyEnteredPatientDetails(testData.get("Name"), testData.get("Gender"), testData.get("DateOfBirth"), testData.get("PhoneNumber")), "Registered details showing incorrect");
        commonUtils.captureScreenshot();
        registrationPage.clickConfirmButon();
        Assert.assertTrue(patientDetailsPage.verifyPatientName(testData.get("Name")), "Patient Name is incorrect in Patient Details Page");
        commonUtils.captureScreenshot();
        TestProperties.setTestProperty("patient.id",patientDetailsPage.getPatientId(),"Updated by Automation Test");
    }

    @Test(priority = 1,dataProvider = "TestData")
    public void findPatientTest(Map<String,String> testData) {
        Assert.assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Find Patient Record Module is not displayed");
        commonUtils.captureScreenshot();
        homePage.clickModuleTile("Find Patient Record");
        Assert.assertTrue(homePage.verifyModulePage("Find Patient Record"), "Find Patient Record Page is not displayed");
        commonUtils.captureScreenshot();
        findPatientPage.searchPatientWithName(testData.get("Name"));
        Assert.assertTrue(findPatientPage.verifySearchPatientTableColumnValue("Name", testData.get("Name")), "Find Patient record is not matching");
        commonUtils.captureScreenshot();
        findPatientPage.clickSearchPatientTableFirstRecord();
        Assert.assertTrue(patientDetailsPage.verifyPatientName(testData.get("Name")), "Find Patient Name is not matching in Patient Details Page");
        commonUtils.captureScreenshot();
    }

    @Test(priority = 2, dataProvider = "TestData")
    public void activateVisitsAndAddAttachmentsTest(Map<String,String> testData) {
        Assert.assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Find Patient Record Module is not displayed");
        commonUtils.captureScreenshot();
        homePage.clickModuleTile("Find Patient Record");
        Assert.assertTrue(homePage.verifyModulePage("Find Patient Record"), "Find Patient Record Page is not displayed");
        commonUtils.captureScreenshot();
        findPatientPage.searchPatientWithName(testData.get("Name"));
        Assert.assertTrue(findPatientPage.verifySearchPatientTableColumnValue("Name", testData.get("Name")), "Find Patient record is not matching");
        commonUtils.captureScreenshot();
        findPatientPage.clickSearchPatientTableFirstRecord();
        Assert.assertTrue(patientDetailsPage.verifyPatientName(testData.get("Name")), "Find Patient Name is not matching in Patient Details Page");
        commonUtils.captureScreenshot();
        patientDetailsPage.startVisits();
        Assert.assertTrue(visitsPage.verifyEndVisitLink(), "Start Visit Page is not displayed");
        commonUtils.captureScreenshot();
        visitsPage.clickAttachments();
        Assert.assertTrue(attachmentsPage.verifyAttachmentsPage(), "Attachments Page is not displayed");
        commonUtils.captureScreenshot();
        String filePath = System.getProperty("user.dir") + TestProperties.getTestProperty("upload.file.path")+testData.get("UploadFileName");
        attachmentsPage.addAttachments(filePath, testData.get("Caption"));
        Assert.assertTrue(attachmentsPage.verifyAddAttachments(testData.get("Caption")), "Add Attachment failed");
        commonUtils.captureScreenshot();
    }

    @Test(priority = 3, dataProvider = "TestData")
    public void deletePatientTest(Map<String,String> testData) {
        Assert.assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Find Patient Record Module is not displayed");
        commonUtils.captureScreenshot();
        homePage.clickModuleTile("Find Patient Record");
        Assert.assertTrue(homePage.verifyModulePage("Find Patient Record"), "Find Patient Record Page is not displayed");
        commonUtils.captureScreenshot();
        findPatientPage.searchPatientWithName(testData.get("Name"));
        Assert.assertTrue(findPatientPage.verifySearchPatientTableColumnValue("Name", testData.get("Name")), "Find Patient record is not matching");
        commonUtils.captureScreenshot();
        findPatientPage.clickSearchPatientTableFirstRecord();
        Assert.assertTrue(patientDetailsPage.verifyPatientName(testData.get("Name")), "Find Patient Name is not matching in Patient Details Page");
        commonUtils.captureScreenshot();
        patientDetailsPage.deletePatient(testData.get("Reason"));
        findPatientPage.searchPatientWithName(testData.get("Name"));
        Assert.assertTrue(findPatientPage.verifyNoMatchingRecordsFoundMessage(),"Patient Record not deleted");
        commonUtils.captureScreenshot();
    }
}
