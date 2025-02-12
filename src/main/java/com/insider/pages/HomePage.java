package com.insider.pages;

import com.insider.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    // Locators
    private By logo = By.xpath("//img[@alt='insider_logo']");
    private By navbar = By.cssSelector("div#navbarNavDropdown");
    private By announceInfo = By.cssSelector("div.announce-info");
    private By metaOgTitle = By.cssSelector("meta[property='og:title']");
    private By cookieBannerTitle = By.id("wt-cli-cookie-banner-title");
    private By acceptAllButton = By.id("wt-cli-accept-all-btn");
    private By onlyNecessaryButton = By.id("wt-cli-accept-btn");
    private By declineButton = By.id("wt-cli-reject-btn");

    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Actions
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
}