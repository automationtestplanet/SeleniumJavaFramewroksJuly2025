package org.openmrs;

import org.openmrs.pageobjects.TestProperties;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OpenMrsKeywordDrivenFrameworkTestCases extends OpenMrsBaseTest {

    @Test(priority = 0)
    public void registerPatientTest() {
        Assert.assertTrue(homePage.verifyModuleTile("Register a patient"), "Register Patient Module is not displayed");
        homePage.clickModuleTile("Register a patient");
        Assert.assertTrue(homePage.verifyModulePage("Register a patient"), "Register Page is not displayed");
        registrationPage.enterPatientDetails("Test, User1", "Male", "01, January, 1990", "Near Icici bank Sr Nagar, Hyderabad, Telangana, India, 500038", "9876543210", "Parent, TestUser1 Parent");
        Assert.assertTrue(registrationPage.verifyEnteredPatientDetails("Test, User1", "Male", "01, January, 1990", "9876543210"), "Registered details showing incorrect");
        registrationPage.clickConfirmButon();
        Assert.assertTrue(patientDetailsPage.verifyPatientName("Test, User1"), "Patient Name is incorrect in Patient Details Page");
        TestProperties.setTestProperty("patient.id",patientDetailsPage.getPatientId(),"Updated by Automation Test");
    }

    @Test(priority = 1)
    public void findPatientTest() {
        Assert.assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Find Patient Record Module is not displayed");
        homePage.clickModuleTile("Find Patient Record");
        Assert.assertTrue(homePage.verifyModulePage("Find Patient Record"), "Find Patient Record Page is not displayed");
        findPatientPage.searchPatientWithName("Test User1");
        Assert.assertTrue(findPatientPage.verifySearchPatientTableColumnValue("Name", "Test User1"), "Find Patient record is not matching");
        findPatientPage.clickSearchPatientTableFirstRecord();
        Assert.assertTrue(patientDetailsPage.verifyPatientName("Test User1"), "Find Patient Name is not matching in Patient Details Page");
    }

    @Test(priority = 2)
    public void activateVisitsAndAddAttachmentsTest() {
        Assert.assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Find Patient Record Module is not displayed");
        homePage.clickModuleTile("Find Patient Record");
        Assert.assertTrue(homePage.verifyModulePage("Find Patient Record"), "Find Patient Record Page is not displayed");
        findPatientPage.searchPatientWithName("Test User1");
        Assert.assertTrue(findPatientPage.verifySearchPatientTableColumnValue("Name", "Test User1"), "Find Patient record is not matching");
        findPatientPage.clickSearchPatientTableFirstRecord();
        Assert.assertTrue(patientDetailsPage.verifyPatientName("Test User1"), "Find Patient Name is not matching in Patient Details Page");
        patientDetailsPage.startVisits();
        Assert.assertTrue(visitsPage.verifyEndVisitLink(), "Start Visit Page is not displayed");
        visitsPage.clickAttachments();
        Assert.assertTrue(attachmentsPage.verifyAttachmentsPage(), "Attachments Page is not displayed");
        String filePath = System.getProperty("user.dir") + TestProperties.getTestProperty("upload.file.path"+"UploadFile.pdf");
        attachmentsPage.addAttachments(filePath, "TestCaption");
        Assert.assertTrue(attachmentsPage.verifyAddAttachments("TestCaption"), "Add Attachment failed");
    }

    @Test(priority = 3)
    public void deletePatientTest() {
        Assert.assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Find Patient Record Module is not displayed");
        homePage.clickModuleTile("Find Patient Record");
        Assert.assertTrue(homePage.verifyModulePage("Find Patient Record"), "Find Patient Record Page is not displayed");
        findPatientPage.searchPatientWithName("Test User1");
        Assert.assertTrue(findPatientPage.verifySearchPatientTableColumnValue("Name", "Test User1"), "Find Patient record is not matching");
        findPatientPage.clickSearchPatientTableFirstRecord();
        Assert.assertTrue(patientDetailsPage.verifyPatientName("Test User1"), "Find Patient Name is not matching in Patient Details Page");
        patientDetailsPage.deletePatient("Other");
        findPatientPage.searchPatientWithName("Test User1");
        Assert.assertTrue(findPatientPage.verifyNoMatchingRecordsFoundMessage(),"Patient Record not deleted");
    }
}
