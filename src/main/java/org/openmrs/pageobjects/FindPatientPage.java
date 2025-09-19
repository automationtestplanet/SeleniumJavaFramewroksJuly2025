package org.openmrs.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FindPatientPage extends BasePage {

    @FindBy(id = "patient-search")
    private WebElement patientSearchInput;
    @FindBy(xpath = "//table[@id='patient-search-results-table']/thead/tr/th/div")
    private List<WebElement> searchPatientTableColumns;

    @FindBy(xpath = "//table[@id='patient-search-results-table']/tbody/tr[1]/td[1]")
    private WebElement searchPatientTableFirstRecord;

    @FindBy(xpath = "//td[text()='No matching records found']")
    private WebElement noMatchingRecordsFoundElement;

    public FindPatientPage(WebDriver driver) {
        super(driver);
    }

    public void searchPatientWithName(String patientName) {
        patientSearchInput.sendKeys(patientName);
    }

    public Map<String, Integer> getSearchPatientTableColumnNamesMap() {
        Map<String, Integer> searchPatientTablecolumnsMap = new LinkedHashMap<>();
        int index = 1;
        for (WebElement eachColumnElement : searchPatientTableColumns) {
            searchPatientTablecolumnsMap.put(eachColumnElement.getText().trim(), index);
            index++;
        }
        return searchPatientTablecolumnsMap;
    }

    public int getSearchPatientTableColumnIndex(String columnName) {
        return getSearchPatientTableColumnNamesMap().get(columnName);
    }

    public String getSearchPatientTableColumnValue(String columnName) {
        return driver.findElement(By.xpath("//table[@id='patient-search-results-table']/tbody/tr/td[" + getSearchPatientTableColumnIndex(columnName) + "]")).getText().trim();
    }

    public boolean verifySearchPatientTableColumnValue(String columnName, String expectedValue) {
        try {
            Thread.sleep(3000);
            return getSearchPatientTableColumnValue(columnName).equals(expectedValue);
        } catch (Exception e) {
            System.out.println("Exception occurred while verifying the patient filtered record: " + e.getMessage());
            return false;
        }
    }

    public void clickSearchPatientTableFirstRecord() {
        searchPatientTableFirstRecord.click();
    }

    public boolean verifyNoMatchingRecordsFoundMessage(){
        return noMatchingRecordsFoundElement.isDisplayed();
    }
}
