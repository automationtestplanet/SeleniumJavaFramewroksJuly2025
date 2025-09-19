package org.openmrs.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class AttachmentsPage extends BasePage {
    @FindBy(xpath = "//div[contains(text(),'Click or drop a file here.')]")
    private WebElement clickOrDropFileElement;

    @FindBy(xpath = "//h3[text()='Caption']//following-sibling::textarea")
    private WebElement captionInput;

    @FindBy(xpath = "//button[text()='Upload file']")
    private WebElement uploadFileButton;

    public AttachmentsPage(WebDriver driver) {
        super(driver);
    }

    public boolean verifyAttachmentsPage() {
        return clickOrDropFileElement.isDisplayed();
    }

    public void clickOrDropFile() {
        clickOrDropFileElement.click();
    }

    public void setCaption(String captionText) {
        captionInput.sendKeys(captionText);
    }

    public void clickUploadFileButton() {
        if (uploadFileButton.isEnabled()) {
            uploadFileButton.click();
        }
    }

    public void addAttachments(String filePath, String captionText) {
        try {
            clickOrDropFile();
            Thread.sleep(3000);
            StringSelection strSelect = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(strSelect, null);
            Robot robot = new Robot();
            Thread.sleep(2000);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(3000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(3000);
            setCaption(captionText);
            clickUploadFileButton();
        } catch (Exception e) {
            System.out.println("Exception occurred while add the attachments: " + e.getMessage());
        }
    }

    public boolean verifyAddAttachments(String caption) {
        return driver.findElement(By.xpath("//div[contains(@class,'att_thumbnail-caption-section')]//p[contains(text(),'" + caption + "')]")).isDisplayed();
    }
}
