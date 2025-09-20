package org.openmrs.pageobjects;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils extends BasePage{
    public CommonUtils(WebDriver driver) {
        super(driver);
    }

    public void captureScreenshot(){
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            String screenshotName = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss").format(new Date()).replaceAll("[^0-9]", "") + ".jpg";
            String destinationPath = System.getProperty("user.dir") + TestProperties.getTestProperty("screenshots.path") + screenshotName;

            FileUtils.copyFile(screenshot, new File(destinationPath));
        }catch(Exception e){
            System.out.println("Exception occurred while capturing the screenshot");
        }
    }

}
