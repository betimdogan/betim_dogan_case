package com.insider.pages;

import com.insider.base.BasePage;
import com.insider.utils.ExtentReportManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    // Locators
    private By logo = By.xpath("//img[@alt='insider_logo']");
    private By navbar = By.cssSelector("div#navbarNavDropdown");
    private By announceInfo = By.cssSelector("div.announce-info");
    private By metaOgTitle = By.cssSelector("meta[property='og:title']");
    private By metaOgDescription = By.cssSelector("meta[property='og:description']");
    private By metaOgUrl = By.cssSelector("meta[property='og:url']");
    private By metaOgSiteName = By.cssSelector("meta[property='og:site_name']");
    private By cookieBannerTitle = By.id("wt-cli-cookie-banner-title");
    private By acceptAllButton = By.id("wt-cli-accept-all-btn");
    private By onlyNecessaryButton = By.id("wt-cli-accept-btn");
    private By declineButton = By.id("wt-cli-reject-btn");
    private By companyMenu = By.xpath("//a[contains(@class, 'nav-link dropdown-toggle') and contains(text(), 'Company')]");
    private By careersOption = By.xpath("//a[contains(@href, '/careers/') and contains(@class, 'dropdown-sub')]");

    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean verifyHomePageMetaTags() {
        try {
            String expectedTitle = "#1 Leader in Individualized, Cross-Channel CX — Insider";
            String expectedDescription = "Insider's CDP connects customer data, predicts behavior with AI, and individualizes experiences across channels";
            String expectedUrl = "https://useinsider.com/";
            String expectedSiteName = "Insider";

            String actualTitle = driver.findElement(metaOgTitle).getAttribute("content");
            String actualDescription = driver.findElement(metaOgDescription).getAttribute("content");
            String actualUrl = driver.findElement(metaOgUrl).getAttribute("content");
            String actualSiteName = driver.findElement(metaOgSiteName).getAttribute("content");

            ExtentReportManager.logInfo("OG Title expected: '" + expectedTitle + "', actual: '" + actualTitle + "'");
            ExtentReportManager.logInfo("OG Description expected: '" + expectedDescription + "', actual: '" + actualDescription + "'");
            ExtentReportManager.logInfo("OG URL expected: '" + expectedUrl + "', actual: '" + actualUrl + "'");
            ExtentReportManager.logInfo("OG Site Name expected: '" + expectedSiteName + "', actual: '" + actualSiteName + "'");

            boolean result = actualTitle.equals(expectedTitle) &&
                    actualDescription.contains(expectedDescription) &&
                    actualUrl.equals(expectedUrl) &&
                    actualSiteName.equals(expectedSiteName);

            ExtentReportManager.logInfo("Home page meta tag verification result: " + result);
            return result;
        } catch (Exception e) {
            ExtentReportManager.logFail("Home page meta tag verification failed: " + e.getMessage());
            return false;
        }
    }

    public boolean isLogoCorrect() {
        WebElement logoElement = driver.findElement(logo);
        String logoSrc = logoElement.getAttribute("src");
        String logoAlt = logoElement.getAttribute("alt");
        boolean result = logoSrc.equals("https://useinsider.com/assets/img/logo-old.png") && logoAlt.equals("insider_logo");
        testLogger.info("Logo verification result: " + result);
        return result;
    }

    public boolean isNavbarPresent() {
        boolean result = driver.findElement(navbar).isDisplayed();
        testLogger.info("Navbar visibility check: " + result);
        return result;
    }

    public boolean isAnnounceInfoPresent() {
        WebElement announceInfoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(announceInfo));
        boolean result = announceInfoElement.isDisplayed();
        testLogger.info("Announce info visibility check: " + result);
        return result;
    }

    public String getOgTitle() {
        String title = driver.findElement(metaOgTitle).getAttribute("content");
        testLogger.info("OG title retrieved: " + title);
        return title;
    }

    // Handle Cookie Banner
    public void handleCookieBanner() {
        try {
            if (!driver.findElements(cookieBannerTitle).isEmpty()) {
                WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(acceptAllButton));
                acceptButton.click();
                testLogger.info("Cookie banner handled: Accepted all cookies.");
            }
        } catch (Exception e) {
            testLogger.info("No cookie banner displayed.");
        }
    }

    public void clickCompanyMenu() {
        WebElement companyButton = driver.findElement(companyMenu);

        // Click the "Company" menu using JavaScript to ensure it opens
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", companyButton);

        // Hover over "Company" to keep it open
        Actions actions = new Actions(driver);
        actions.moveToElement(companyButton).perform();

        // Wait until the dropdown is fully opened
        wait.until(ExpectedConditions.attributeToBe(companyMenu, "aria-expanded", "true"));

        testLogger.info("Clicked and hovered over 'Company' menu to keep it open.");
    }

    public void clickCareers() {
        clickCompanyMenu(); // Ensure dropdown is open before clicking Careers

        // Find the "Careers" option and wait until it's clickable
        WebElement careersButton = wait.until(ExpectedConditions.elementToBeClickable(careersOption));

        // Click "Careers" using JavaScript to avoid hover issues
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", careersButton);

        testLogger.info("Clicked on 'Careers' link.");
    }
}