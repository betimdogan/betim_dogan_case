package com.insider.base;

import com.aventstack.extentreports.ExtentTest;
import com.insider.utils.ExtentReportManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    protected WebDriver driver;
    protected ExtentTest testLogger;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.testLogger = ExtentReportManager.getTest();
        if (this.testLogger == null) {
            throw new IllegalStateException("ExtentTest is null. Ensure startTest() is called before creating pages.");
        }
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        String title = driver.getTitle();
        testLogger.info("Page title retrieved: " + title);
        return title;
    }

    public void scrollToElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element);
            Thread.sleep(1000); // Small wait to visually confirm scrolling
            ExtentReportManager.logInfo("Scrolled to element: " + element.getText());
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to scroll to element: " + e.getMessage());
        }
    }
}
