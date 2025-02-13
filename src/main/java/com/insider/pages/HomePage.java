package com.insider.pages;

import com.insider.base.BasePage;
import com.insider.utils.ExtentReportManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    // **Locators**
    private By logo = By.xpath("//img[@alt='insider_logo']");
    private By navbar = By.cssSelector("div#navbarNavDropdown");
    private By announceInfo = By.cssSelector("div.announce-info");
    private By metaOgTitle = By.cssSelector("meta[property='og:title']");
    private By cookieBannerTitle = By.id("wt-cli-cookie-banner-title");
    private By acceptAllButton = By.id("wt-cli-accept-all-btn");
    private By onlyNecessaryButton = By.id("wt-cli-accept-btn");
    private By declineButton = By.id("wt-cli-reject-btn");
    private By companyMenu = By.xpath("//a[contains(@class, 'nav-link dropdown-toggle') and contains(text(), 'Company')]");
    private By careersOption = By.xpath("//a[contains(@href, '/careers/') and contains(@class, 'dropdown-sub')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean verifyHomePageMetaTags() {
        return verifyMetaTags(
                "#1 Leader in Individualized, Cross-Channel CX — Insider",
                "Insider's CDP connects customer data, predicts behavior with AI, and individualizes experiences across channels",
                "https://useinsider.com/",
                "Insider"
        );
    }

    public boolean isLogoCorrect() {
        try {
            WebElement logoElement = driver.findElement(logo);
            String logoSrc = logoElement.getAttribute("src");
            String logoAlt = logoElement.getAttribute("alt");

            boolean result = logoSrc.equals("https://useinsider.com/assets/img/logo-old.png") &&
                    logoAlt.equals("insider_logo");

            ExtentReportManager.logInfo("Logo verification result: " + result);
            return result;
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to verify logo: " + e.getMessage());
            return false;
        }
    }

    public boolean isNavbarPresent() {
        boolean result = driver.findElement(navbar).isDisplayed();
        ExtentReportManager.logInfo("Navbar visibility check: " + result);
        return result;
    }

    public boolean isAnnounceInfoPresent() {
        try {
            WebElement announceInfoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(announceInfo));
            boolean result = announceInfoElement.isDisplayed();
            ExtentReportManager.logInfo("Announce info visibility check: " + result);
            return result;
        } catch (Exception e) {
            ExtentReportManager.logFail("Announce info verification failed: " + e.getMessage());
            return false;
        }
    }

    public String getOgTitle() {
        String title = driver.findElement(metaOgTitle).getAttribute("content");
        ExtentReportManager.logInfo("OG title retrieved: " + title);
        return title;
    }

    public void handleCookieBanner() {
        try {
            if (!driver.findElements(cookieBannerTitle).isEmpty()) {
                WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(acceptAllButton));
                acceptButton.click();
                ExtentReportManager.logPass("Cookie banner handled: Accepted all cookies.");
            }
        } catch (Exception e) {
            ExtentReportManager.logInfo("No cookie banner displayed.");
        }
    }

    public void clickCompanyMenu() {
        try {
            WebElement companyButton = waitForElementToBeClickable(driver.findElement(companyMenu));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", companyButton);
            hoverOverElement(companyButton);

            // Wait until dropdown opens
            wait.until(ExpectedConditions.attributeToBe(companyMenu, "aria-expanded", "true"));
            ExtentReportManager.logPass("Clicked and hovered over 'Company' menu.");
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to click 'Company' menu: " + e.getMessage());
        }
    }

    public void clickCareers() {
        try {
            clickCompanyMenu();
            WebElement careersButton = waitForElementToBeClickable(driver.findElement(careersOption));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", careersButton);
            ExtentReportManager.logPass("Clicked on 'Careers' link.");
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to click 'Careers' link: " + e.getMessage());
        }
    }
}
