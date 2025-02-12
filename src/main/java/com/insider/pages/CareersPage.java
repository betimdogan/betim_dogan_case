package com.insider.pages;

import com.insider.base.BasePage;
import com.insider.utils.ExtentReportManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CareersPage extends BasePage {

    private By teamsBlockTitle = By.xpath("//h3[contains(@class, 'category-title-media') and contains(text(), 'Find your calling')]");
    private By jobItems = By.cssSelector("div.job-item.col-12.col-lg-4.mt-5");
    private By seeAllTeamsButton = By.xpath("//a[contains(text(), 'See all teams') and contains(@class, 'loadmore')]");

    private WebDriverWait wait;

    public CareersPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isCareersPageOpened() {
        boolean result = driver.getCurrentUrl().contains("https://useinsider.com/careers/");
        if (!result) {
            ExtentReportManager.logFail("Careers page did not open correctly.");
        } else {
            ExtentReportManager.logPass("Navigated to Careers page successfully.");
        }
        return result;
    }

    public boolean verifyCareersPageMetaTags() {
        try {
            String expectedTitle = "Ready to disrupt? | Insider Careers";
            String expectedDescription = "Learn about Insider story";
            String expectedUrl = "https://useinsider.com/careers/";
            String expectedSiteName = "Insider";

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

            if (!result) {
                ExtentReportManager.logFail("Meta tag verification failed!");
            } else {
                ExtentReportManager.logPass("Meta tag verification passed!");
            }
            return result;
        } catch (Exception e) {
            ExtentReportManager.logFail("Meta tag verification failed due to an exception: " + e.getMessage());
            return false;
        }
    }

    public boolean isTeamsBlockPresent() {
        try {
            WebElement teamsBlock = driver.findElement(teamsBlockTitle);
            List<WebElement> jobItemElements = driver.findElements(jobItems);
            WebElement seeAllTeams = driver.findElement(seeAllTeamsButton);

            scrollToElement(teamsBlock);
            wait.until(ExpectedConditions.visibilityOf(teamsBlock));
            boolean titleVisible = teamsBlock.isDisplayed();
            ExtentReportManager.logInfo("Teams block title expected: 'Find your calling', actual: " + teamsBlock.getText());

            scrollToElement(jobItemElements.get(0));
            boolean jobItemsVisible = jobItemElements.size() == 3;
            ExtentReportManager.logInfo("Job items count expected: 3, actual: " + jobItemElements.size());

            scrollToElement(seeAllTeams);
            boolean seeAllButtonVisible = seeAllTeams.isDisplayed();
            ExtentReportManager.logInfo("See All Teams button visible: " + seeAllButtonVisible);

            boolean result = titleVisible && jobItemsVisible && seeAllButtonVisible;
            ExtentReportManager.logInfo("Teams block verification result: " + result);
            return result;
        } catch (Exception e) {
            ExtentReportManager.logFail("Error while checking Teams block elements: " + e.getMessage());
            return false;
        }
    }

    public int getJobItemCount() {
        try {
            List<WebElement> jobItemElements = driver.findElements(jobItems);
            int count = jobItemElements.size();

            // Scroll to first job item if present
            if (count > 0) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", jobItemElements.get(0));
            }

            ExtentReportManager.logInfo("Current job item count: " + count);
            return count;
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to count job items: " + e.getMessage());
            return 0;
        }
    }

    public WebElement getSeeAllTeamsButton() {
        return driver.findElement(seeAllTeamsButton);
    }

    public void clickSeeAllTeams() {
        try {
            WebElement seeAllTeams = driver.findElement(seeAllTeamsButton);

            // Scroll to 'See all teams' button
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", seeAllTeams);

            wait.until(ExpectedConditions.elementToBeClickable(seeAllTeams));
            seeAllTeams.click();

            ExtentReportManager.logPass("Clicked on 'See all teams' button.");
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to click 'See all teams' button: " + e.getMessage());
        }
    }
}