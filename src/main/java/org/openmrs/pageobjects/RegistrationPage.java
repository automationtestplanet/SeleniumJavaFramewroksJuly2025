package org.openmrs.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends BasePage {

    @FindBy(name = "givenName")
    private WebElement givenNameInput;
    @FindBy(name = "familyName")
    private WebElement familyNameInput;
    @FindBy(id = "next-button")
    private WebElement nextButton;
    @FindBy(id = "gender-field")
    private WebElement genderDropdown;
    @FindBy(id = "birthdateDay-field")
    private WebElement birthDateInput;
    @FindBy(id = "birthdateMonth-field")
    private WebElement birthMonthDropDown;
    @FindBy(id = "birthdateYear-field")
    private WebElement birthYearInput;
    @FindBy(id = "address1")
    private WebElement address1Input;
    @FindBy(id = "cityVillage")
    private WebElement cityInput;
    @FindBy(id = "stateProvince")
    private WebElement stateInput;
    @FindBy(id = "country")
    private WebElement countryInput;
    @FindBy(id = "postalCode")
    private WebElement postalCodeInput;
    @FindBy(name = "phoneNumber")
    private WebElement phoneNumberInput;
    @FindBy(id = "relationship_type")
    private WebElement patientRelationDropdown;
    @FindBy(css = "input[placeholder='Person Name']")
    private WebElement patientRelationName;
    @FindBy(xpath = "//span[contains(text(),'Name:')]//parent::p")
    private WebElement patientNameLabel;
    @FindBy(xpath = "//span[contains(text(),'Gender:')]//parent::p")
    private WebElement patientGenderLabel;
    @FindBy(xpath = "//span[contains(text(),'Birthdate:')]//parent::p")
    private WebElement patientDateOfBirthLabel;
    @FindBy(xpath = "//span[contains(text(),'Phone Number:')]//parent::p")
    private WebElement patientPhoneNumberLabel;
    @FindBy(css = "input[value='Confirm']")
    private WebElement confirmButton;
    @FindBy(id = "cancelSubmission")
    private WebElement cancelButton;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void setPatientGivenName(String givenName) {
        givenNameInput.sendKeys(givenName);
    }

    public void setPatientFamilyName(String familyName) {
        familyNameInput.sendKeys(familyName);
    }

    public void clickNextButton() {
        nextButton.click();
    }

    public void selectGender(String genderValue) {
        Select genderDropDown = new Select(genderDropdown);
        genderDropDown.selectByVisibleText(genderValue);
    }

    public void setBirthDate(String birthDate) {
        birthDateInput.sendKeys(birthDate);
    }

    public void selectBirthMonth(String birthMonth) {
        Select birthMonthDropdown = new Select(birthMonthDropDown);
        birthMonthDropdown.selectByVisibleText(birthMonth);
    }

    public void setBirthYear(String birthYear) {
        birthYearInput.sendKeys(birthYear);
    }

    public void setDateOfBirth(String dateOfBirth) {
        String[] dateOfBirthArr = dateOfBirth.split(",");
        setBirthDate(dateOfBirthArr[0].trim());
        selectBirthMonth(dateOfBirthArr[1].trim());
        setBirthYear(dateOfBirthArr[2].trim());
    }

    public void setAddress1(String address1) {
        address1Input.sendKeys(address1);
    }

    public void setCity(String city) {
        cityInput.sendKeys(city);
    }

    public void setState(String state) {
        stateInput.sendKeys(state);
    }

    public void setCountry(String country) {
        countryInput.sendKeys(country);
    }

    public void setPostalCode(String postalCode) {
        postalCodeInput.sendKeys(postalCode);
    }

    public void setAddress(String address) {
        String[] addressArr = address.split(",");
        setAddress1(addressArr[0].trim());
        setCity(addressArr[1].trim());
        setState(addressArr[2].trim());
        setCountry(addressArr[3].trim());
        setPostalCode(addressArr[4].trim());
    }

    public void setPhoneNumber(String phoneNumber) {
        phoneNumberInput.sendKeys(phoneNumber);
    }

    public void selectPatientRelation(String patientRelation) {
        Select relationshipDropDown = new Select(patientRelationDropdown);
        relationshipDropDown.selectByVisibleText(patientRelation);
    }

    public void setPatientRelationName(String relativeName) {
        patientRelationName.sendKeys(relativeName);
    }

    public void setPatientRelationDetails(String relationDetails) {
        String[] relationDetailsArr = relationDetails.split(",");
        selectPatientRelation(relationDetailsArr[0].trim());
        setPatientRelationName(relationDetailsArr[1].trim());
    }

    public String getPatientName() {
        return patientNameLabel.getText().trim();
    }

    public void setPatientName(String patientName) {
        String[] patientNameArr = patientName.split(",");
        setPatientGivenName(patientNameArr[0].trim());
        setPatientFamilyName(patientNameArr[1].trim());
    }

    public String getPatientGender() {
        return patientGenderLabel.getText().trim();
    }

    public String getPatientDateOfBirth() {
        return patientDateOfBirthLabel.getText().trim();
    }

    public String getPatientPhoneNumber() {
        return patientPhoneNumberLabel.getText().trim();
    }

    public boolean verifyEnteredPatientDetails(String expectedPatientName, String expectedGender, String expectedDateOfBirth, String expectedPhoneNumber) {
        return getPatientName().contains(expectedPatientName) && getPatientGender().contains(expectedGender) && getPatientDateOfBirth().contains(expectedDateOfBirth) && getPatientPhoneNumber().contains(expectedPhoneNumber);
    }

    public void clickConfirmButon() {
        confirmButton.click();
    }

    public void clickCancelButon() {
        cancelButton.click();
    }

    public void enterPatientDetails(String patientName, String patientGender, String patientDateOfBirth, String patientAddress, String patientPhoneNumber, String patientRelatives) {
        setPatientName(patientName);
        clickNextButton();
        selectGender(patientGender);
        clickNextButton();
        setDateOfBirth(patientDateOfBirth);
        clickNextButton();
        setAddress(patientAddress);
        clickNextButton();
        setPhoneNumber(patientPhoneNumber);
        clickNextButton();
        setPatientRelationDetails(patientRelatives);
        clickNextButton();
    }

}
