package org.openmrs.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VisitsPage extends BasePage{
    public VisitsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[contains(@href,'EndVisit')]")
    private WebElement endVisitLink;

    @FindBy(xpath = "//a[contains(@href,'attachments')]")
    private WebElement attachmentsLink;

    public boolean verifyEndVisitLink(){
        return endVisitLink.isDisplayed();
    }

    public void clickAttachments(){
            attachmentsLink.click();
    }
}
