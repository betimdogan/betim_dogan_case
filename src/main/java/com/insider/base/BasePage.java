package com.insider.base;

import com.aventstack.extentreports.ExtentTest;
import com.insider.utils.ExtentReportManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ExtentTest testLogger;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Standardized wait time
        this.testLogger = ExtentReportManager.getTest();

        if (this.testLogger == null) {
            throw new IllegalStateException("ExtentTest is null. Ensure startTest() is called before creating pages.");
        }
        PageFactory.initElements(driver, this);
    }

    // COMMONS
    public boolean verifyMetaTags(String expectedTitle, String expectedDescription, String expectedUrl, String expectedSiteName) {
        try {
            String actualTitle = driver.findElement(By.xpath("//meta[@property='og:title']")).getAttribute("content");
            String actualDescription = driver.findElement(By.xpath("//meta[@property='og:description']")).getAttribute("content");
            String actualUrl = driver.findElement(By.xpath("//meta[@property='og:url']")).getAttribute("content");
            String actualSiteName = driver.findElement(By.xpath("//meta[@property='og:site_name']")).getAttribute("content");

            ExtentReportManager.logInfo("OG Title expected: '" + expectedTitle + "', actual: '" + actualTitle + "'");
            ExtentReportManager.logInfo("OG Description expected: '" + expectedDescription + "', actual: '" + actualDescription + "'");
            ExtentReportManager.logInfo("OG URL expected: '" + expectedUrl + "', actual: '" + actualUrl + "'");
            ExtentReportManager.logInfo("OG Site Name expected: '" + expectedSiteName + "', actual: '" + actualSiteName + "'");

            boolean result = actualTitle.equals(expectedTitle) &&
                    actualDescription.contains(expectedDescription) &&
                    actualUrl.equals(expectedUrl) &&
                    actualSiteName.equals(expectedSiteName);

            ExtentReportManager.logInfo(result ? "Meta tag verification passed!" : "Meta tag verification failed!");
            return result;
        } catch (Exception e) {
            ExtentReportManager.logFail("Meta tag verification failed due to an exception: " + e.getMessage());
            return false;
        }
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

    public WebElement waitForElementToBeClickable(WebElement element) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            testLogger.fail("Timeout waiting for element to be clickable: " + element);
            throw e;
        }
    }

    public WebElement waitForElementToBeVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            testLogger.fail("Timeout waiting for element to be visible: " + locator);
            throw e;
        }
    }

    public void hoverOverElement(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            testLogger.info("Hovered over element: " + element.getText());
        } catch (Exception e) {
            testLogger.fail("Failed to hover over element: " + e.getMessage());
        }
    }

    public void clickElement(WebElement element) {
        try {
            waitForElementToBeClickable(element).click();
            testLogger.info("Clicked on element: " + element.getText());
        } catch (Exception e) {
            testLogger.fail("Failed to click element: " + e.getMessage());
        }
    }
}