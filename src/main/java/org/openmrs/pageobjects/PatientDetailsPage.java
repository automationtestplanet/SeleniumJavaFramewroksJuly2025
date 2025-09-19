package org.openmrs.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PatientDetailsPage extends BasePage {

    @FindBy(xpath = "//em[contains(text(),'Family Name')]//preceding-sibling::span")
    private WebElement familyNameLabel;
    @FindBy(xpath = "//em[contains(text(),'Patient ID')]//following-sibling::span")
    private WebElement patientIdLabel;

    @FindBy(xpath = "//div[contains(text(),'Start Visit')]//ancestor::a")
    private WebElement startVisitsLink;

    @FindBy(id = "start-visit-with-visittype-confirm")
    private WebElement startVisitsConfirmButton;

    @FindBy(xpath = "//div[contains(text(),'Delete Patient')]//ancestor::a")
    private WebElement deletePatientLink;

    @FindBy(id = "delete-reason")
    private WebElement deleteReasonInput;

    @FindBy(css = "div[id='delete-patient-creation-dialog'] button[class='confirm right']")
    private WebElement deleteConfirmButton;

    public PatientDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getGivenName() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement givenNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//em[contains(text(),'Given')]//preceding-sibling::span")));
        return givenNameElement.getText().trim();
    }

    public String getFamilyName() {
        return familyNameLabel.getText().trim();
    }

    public boolean verifyPatientName(String patientName) {
        String[] patientNameArr;
        if (patientName.contains(",")) {
            patientNameArr = patientName.split(",");
        } else {
            patientNameArr = patientName.split(" ");
        }
        return getGivenName().equals(patientNameArr[0].trim()) && getFamilyName().equals(patientNameArr[1].trim());
    }

    public String getPatientId() {
        return patientIdLabel.getText().trim();
    }

    public void clickStartVisits() {
        startVisitsLink.click();
    }

    public void clickStartVisitsConfirmButton() {
        startVisitsConfirmButton.click();
    }

    public void startVisits(){
        clickStartVisits();
        clickStartVisitsConfirmButton();
    }

    public void clickDeletePatientLink(){
        deletePatientLink.click();
    }

    public void setDeleteReason(String reason){
        deleteReasonInput.sendKeys(reason);
    }

    public void clickDeleteConfirmButton(){
        deleteConfirmButton.click();
    }

    public void deletePatient(String reason){
        clickDeletePatientLink();
        setDeleteReason(reason);
        clickDeleteConfirmButton();
    }

}
