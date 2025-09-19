package org.openmrs.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(partialLinkText = "Logout")
    WebElement logoutElement;

    @FindBy(css = "i[class='icon-home small']")
    WebElement homeIcon;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean verifyModuleTile(String moduleName) {
        return driver.findElement(By.partialLinkText(moduleName)).isDisplayed();
    }

    public void clickModuleTile(String moduleName) {
        driver.findElement(By.partialLinkText(moduleName)).click();
    }

    public boolean verifyModulePage(String expectedPageName) {
        return driver.findElement(By.xpath("//h2[contains(text(),'" + expectedPageName + "')]")).isDisplayed();
    }

    public void logoutApplication() {
        try {
            Thread.sleep(5000);
            logoutElement.click();
        } catch (Exception e) {
            System.out.println("Exception occurred while clicking logout application: " + e.getMessage());
        }
    }

    public void clickHomeButton() {
        homeIcon.click();
    }
}
