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
    private By locationsBlockTitle = By.xpath("//h3[contains(@class, 'category-title-media') and contains(text(), 'Our Locations')]");
    private By locationsBlockDescription = By.xpath("//p[contains(@class, 'mt-5 mb-0 mt-lg-0 mx-auto pl-0') and contains(text(), '28 offices across 6 continents, home to 1100+ Insiders')]");
    private By locationsList = By.cssSelector("ul.glide__slides > li.glide__slide");
    private By locationName = By.cssSelector("div.location-info p.mb-0");
    private By locationCountry = By.cssSelector("div.location-info div.position-relative span:first-child");
    private By lifeAtInsiderTitle = By.xpath("//h2[contains(@class, 'elementor-heading-title') and contains(text(), 'Life at Insider')]");
    private By lifeAtInsiderDescription = By.xpath("//p[contains(text(), 'We’re here to grow and drive growth')]");

    private WebDriverWait wait;

    public CareersPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement getTeamsBlockTitle() {
        return driver.findElement(teamsBlockTitle);
    }

    public List<WebElement> getJobItems() {
        return driver.findElements(jobItems);
    }


    public WebElement getSeeAllTeamsButton() {
        return driver.findElement(seeAllTeamsButton);
    }

    public List<WebElement> getLocationElements() {
        return driver.findElements(locationsList);
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
            WebElement teamsBlock = getTeamsBlockTitle();
            List<WebElement> jobItemElements = getJobItems();
            WebElement seeAllTeams = getSeeAllTeamsButton();

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
            List<WebElement> jobItemElements = getJobItems();
            int count = jobItemElements.size();

            // Scroll to last job item if present
            if (count > 0) {
                scrollToElement(jobItemElements.get(count - 1));
            }

            ExtentReportManager.logInfo("Current job item count: " + count);
            return count;
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to count job items: " + e.getMessage());
            return 0;
        }
    }


    public void clickSeeAllTeams() {
        try {
            WebElement seeAllTeams = getSeeAllTeamsButton();

            // Check job items count before clicking
            int initialJobItemCount = getJobItemCount();
            ExtentReportManager.logInfo("Initial job items count before clicking 'See all teams': " + initialJobItemCount);

            // Scroll to 'See all teams' button
            scrollToElement(seeAllTeams);

            wait.until(ExpectedConditions.elementToBeClickable(seeAllTeams));
            seeAllTeams.click();
            ExtentReportManager.logPass("Clicked on 'See all teams' button.");

            // Wait for job items to expand from 3 to 15
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(jobItems, 3));
            ExtentReportManager.logPass("Teams are expanded after clicking on 'See all teams' button.");

        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to click 'See all teams' button: " + e.getMessage());
        }
    }

    public boolean isLocationsBlockPresent() {
        try {
            WebElement locationsTitle = driver.findElement(locationsBlockTitle);
            WebElement locationsDescription = driver.findElement(locationsBlockDescription);

            scrollToElement(locationsTitle);
            wait.until(ExpectedConditions.visibilityOf(locationsTitle));
            boolean titleVisible = locationsTitle.isDisplayed();
            ExtentReportManager.logInfo("Locations block title expected: 'Our Locations', actual: " + locationsTitle.getText());

            scrollToElement(locationsDescription);
            wait.until(ExpectedConditions.visibilityOf(locationsDescription));
            boolean descriptionVisible = locationsDescription.isDisplayed();
            ExtentReportManager.logInfo("Locations block description expected: '28 offices across 6 continents, home to 1100+ Insiders', actual: " + locationsDescription.getText());

            // Verify locations list
            List<WebElement> locations = getLocationElements();
            int locationCount = locations.size();
            StringBuilder locationsLog = new StringBuilder(locationCount + " locations found: ");

            for (int i = 0; i < locations.size(); i++) {
                WebElement location = locations.get(i);
                scrollToElement(location); // Scroll to each location to ensure visibility
                Thread.sleep(500); // Small delay to allow content to load

                String city = "-";
                String country = "-";

                try {
                    city = location.findElement(locationName).getText();
                } catch (Exception e) {
                    ExtentReportManager.logInfo("City name not found for location " + (i + 1));
                }

                try {
                    country = location.findElement(locationCountry).getText();
                } catch (Exception e) {
                    ExtentReportManager.logInfo("Country name not found for location " + (i + 1));
                }

                locationsLog.append((i + 1) + ": " + city + " - " + country + ", ");
            }

            ExtentReportManager.logInfo(locationsLog.toString());

            boolean result = titleVisible && descriptionVisible && locationCount > 0;
            ExtentReportManager.logInfo("Locations block verification result: " + result);
            return result;
        } catch (Exception e) {
            ExtentReportManager.logFail("Error while checking Locations block elements: " + e.getMessage());
            return false;
        }
    }

    public boolean isLifeAtInsiderBlockPresent() {
        try {
            WebElement lifeTitle = driver.findElement(lifeAtInsiderTitle);
            WebElement lifeDescription = driver.findElement(lifeAtInsiderDescription);

            scrollToElement(lifeTitle);
            wait.until(ExpectedConditions.visibilityOf(lifeTitle));
            boolean titleVisible = lifeTitle.isDisplayed();
            ExtentReportManager.logInfo("Life at Insider block title expected: 'Life at Insider', actual: " + lifeTitle.getText());

            scrollToElement(lifeDescription);
            wait.until(ExpectedConditions.visibilityOf(lifeDescription));
            boolean descriptionVisible = lifeDescription.isDisplayed();
            ExtentReportManager.logInfo("Life at Insider block description verified: " + lifeDescription.getText());

            boolean result = titleVisible && descriptionVisible;
            ExtentReportManager.logInfo("Life at Insider block verification result: " + result);
            return result;
        } catch (Exception e) {
            ExtentReportManager.logFail("Error while checking Life at Insider block elements: " + e.getMessage());
            return false;
        }
    }

}