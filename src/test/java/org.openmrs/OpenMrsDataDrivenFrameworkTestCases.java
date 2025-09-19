package org.openmrs;

import org.openmrs.pageobjects.TestProperties;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OpenMrsDataDrivenFrameworkTestCases extends OpenMrsBaseTest {

    @Test(priority = 0, dataProvider = "RegisterPatientTestData")
    public void registerPatientTest(String patientName, String gender, String dateOfBirth, String address, String phoneNumber, String patientRelatives) {
        Assert.assertTrue(homePage.verifyModuleTile("Register a patient"), "Register Patient Module is not displayed");
        homePage.clickModuleTile("Register a patient");
        Assert.assertTrue(homePage.verifyModulePage("Register a patient"), "Register Page is not displayed");
        registrationPage.enterPatientDetails(patientName, gender, dateOfBirth, address, phoneNumber, patientRelatives);
        Assert.assertTrue(registrationPage.verifyEnteredPatientDetails(patientName, gender, dateOfBirth, phoneNumber), "Registered details showing incorrect");
        registrationPage.clickConfirmButon();
        Assert.assertTrue(patientDetailsPage.verifyPatientName(patientName), "Patient Name is incorrect in Patient Details Page");
        TestProperties.setTestProperty("patient.id",patientDetailsPage.getPatientId(),"Updated by Automation Test");
    }

    @Test(priority = 1,dataProvider = "FindPatientTestData")
    public void findPatientTest(String patientName) {
        Assert.assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Find Patient Record Module is not displayed");
        homePage.clickModuleTile("Find Patient Record");
        Assert.assertTrue(homePage.verifyModulePage("Find Patient Record"), "Find Patient Record Page is not displayed");
        findPatientPage.searchPatientWithName(patientName);
        Assert.assertTrue(findPatientPage.verifySearchPatientTableColumnValue("Name", patientName), "Find Patient record is not matching");
        findPatientPage.clickSearchPatientTableFirstRecord();
        Assert.assertTrue(patientDetailsPage.verifyPatientName(patientName), "Find Patient Name is not matching in Patient Details Page");
    }

    @Test(priority = 2, dataProvider = "ActivateVisitsTestData")
    public void activateVisitsAndAddAttachmentsTest(String patientName, String uploadFileName, String caption) {
        Assert.assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Find Patient Record Module is not displayed");
        homePage.clickModuleTile("Find Patient Record");
        Assert.assertTrue(homePage.verifyModulePage("Find Patient Record"), "Find Patient Record Page is not displayed");
        findPatientPage.searchPatientWithName(patientName);
        Assert.assertTrue(findPatientPage.verifySearchPatientTableColumnValue("Name", patientName), "Find Patient record is not matching");
        findPatientPage.clickSearchPatientTableFirstRecord();
        Assert.assertTrue(patientDetailsPage.verifyPatientName(patientName), "Find Patient Name is not matching in Patient Details Page");
        patientDetailsPage.startVisits();
        Assert.assertTrue(visitsPage.verifyEndVisitLink(), "Start Visit Page is not displayed");
        visitsPage.clickAttachments();
        Assert.assertTrue(attachmentsPage.verifyAttachmentsPage(), "Attachments Page is not displayed");
        String filePath = System.getProperty("user.dir") + TestProperties.getTestProperty("upload.file.path")+uploadFileName;
        attachmentsPage.addAttachments(filePath, caption);
        Assert.assertTrue(attachmentsPage.verifyAddAttachments(caption), "Add Attachment failed");
    }

    @Test(priority = 3, dataProvider = "DeletePatientTestData")
    public void deletePatientTest(String patientName, String deleteReason) {
        Assert.assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Find Patient Record Module is not displayed");
        homePage.clickModuleTile("Find Patient Record");
        Assert.assertTrue(homePage.verifyModulePage("Find Patient Record"), "Find Patient Record Page is not displayed");
        findPatientPage.searchPatientWithName(patientName);
        Assert.assertTrue(findPatientPage.verifySearchPatientTableColumnValue("Name", patientName), "Find Patient record is not matching");
        findPatientPage.clickSearchPatientTableFirstRecord();
        Assert.assertTrue(patientDetailsPage.verifyPatientName(patientName), "Find Patient Name is not matching in Patient Details Page");
        patientDetailsPage.deletePatient(deleteReason);
        findPatientPage.searchPatientWithName(patientName);
        Assert.assertTrue(findPatientPage.verifyNoMatchingRecordsFoundMessage(),"Patient Record not deleted");
    }
}
