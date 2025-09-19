package org.openmrs.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement userNameElement;

    @FindBy(name = "password")
    private WebElement passwordElement;

    @FindBy(id = "loginButton")
    private WebElement loginButtonElement;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void setUserName(String userName) {
        userNameElement.sendKeys(userName);
    }

    public void setPassword(String password) {
        passwordElement.sendKeys(password);
    }

    public void selectModule(String moduleName) {
        driver.findElement(By.id(moduleName)).click();
    }

    public void clickLogin() {
        loginButtonElement.click();
    }

    public void loginToOpenMrs(String userName, String password, String moduleName) {
        try {
            setUserName(userName);
            setPassword(password);
            selectModule(moduleName);
            clickLogin();
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Exception occurred while login to the application: " + e.getMessage());
        }
    }
}
