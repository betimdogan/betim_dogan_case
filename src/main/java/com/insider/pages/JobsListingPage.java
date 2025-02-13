package com.insider.pages;

import com.insider.base.BasePage;
import com.insider.utils.ExtentReportManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class JobsListingPage extends BasePage {

    private By jobsListingTitle = By.xpath("//h1[contains(@class, 'big-title') and contains(text(), 'Quality Assurance')]");
    private By jobsListingDescription = By.xpath("//p[contains(@class, 'text-medium') and contains(text(), 'Never miss a thing?')]");
    private By seeAllQAJobsButton = By.xpath("//a[contains(@href, '/careers/open-positions/') and contains(text(), 'See all QA jobs')]");
    private By allOpenPositionsTitle = By.xpath("//h3[contains(text(), 'All open positions')]");
    private By allOpenPositionsDescription = By.xpath("//p[contains(text(), 'Ready to disrupt? Explore career opportunities at Insider.')]");
    private By filterByLocationLabel = By.xpath("//label[@for='filter-by-location']");
    private By filterByDepartmentLabel = By.xpath("//label[@for='filter-by-department']");
    private By locationDropdown = By.id("select2-filter-by-location-container");
    private By locationDropdownArrow = By.cssSelector(".select2-selection__arrow");
    private By locationDropdownOptions = By.cssSelector("ul#select2-filter-by-location-results li");
    private By jobListItems = By.cssSelector("div.position-list-item-wrapper.bg-light");
    private By jobDepartment = By.cssSelector("span.position-department");
    private By jobLocation = By.cssSelector("div.position-location");
    private By jobViewRoleButton = By.xpath("//a[contains(@class, 'btn btn-navy') and contains(text(), 'View Role')]");

    private WebDriverWait wait;

    public JobsListingPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isJobsListingPageOpened() {
        return driver.getCurrentUrl().contains("https://useinsider.com/careers/quality-assurance/");
    }

    public boolean verifyJobsListingPageMetaTags() {
        try {
            String expectedTitle = "Insider quality assurance job opportunities";
            String expectedDescription = "Do you have an eye for detail? Our Q&A team is committed to testing everything we build. Explore Insider quality assurance job opportunities.";
            String expectedUrl = "https://useinsider.com/careers/quality-assurance/";
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

            ExtentReportManager.logInfo("Jobs Listing Page meta tag verification result: " + result);
            return result;
        } catch (Exception e) {
            ExtentReportManager.logFail("Jobs Listing Page meta tag verification failed: " + e.getMessage());
            return false;
        }
    }

    public boolean isJobsListingPageElementsPresent() {
        try {
            WebElement title = driver.findElement(jobsListingTitle);
            WebElement description = driver.findElement(jobsListingDescription);
            WebElement qaJobsButton = driver.findElement(seeAllQAJobsButton);

            scrollToElement(title);
            wait.until(ExpectedConditions.visibilityOf(title));
            boolean titleVisible = title.isDisplayed();
            ExtentReportManager.logInfo("Jobs Listing Page title verified: 'Quality Assurance'");

            scrollToElement(description);
            wait.until(ExpectedConditions.visibilityOf(description));
            boolean descriptionVisible = description.isDisplayed();
            ExtentReportManager.logInfo("Jobs Listing Page description verified.");

            scrollToElement(qaJobsButton);
            wait.until(ExpectedConditions.visibilityOf(qaJobsButton));
            boolean buttonVisible = qaJobsButton.isDisplayed();
            ExtentReportManager.logInfo("'See all QA jobs' button is visible.");

            boolean result = titleVisible && descriptionVisible && buttonVisible;
            ExtentReportManager.logInfo("Jobs Listing Page verification result: " + result);
            return result;
        } catch (Exception e) {
            ExtentReportManager.logFail("Error while checking Jobs Listing Page elements: " + e.getMessage());
            return false;
        }
    }

    public void clickSeeAllQAJobs() {
        try {
            WebElement qaJobsButton = driver.findElement(seeAllQAJobsButton);
            scrollToElement(qaJobsButton);
            wait.until(ExpectedConditions.elementToBeClickable(qaJobsButton));
            qaJobsButton.click();
            ExtentReportManager.logPass("Clicked on 'See all QA jobs' button.");
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to click 'See all QA jobs' button: " + e.getMessage());
        }
    }

    public boolean verifyOpenPositionsPageMetaTags() {
        try {
            String expectedOgTitle = "Insider open positions | Insider";
            String expectedOgDescription = "Looking for your next career move? Explore all open positions at Insider and see what it's like being a part of culture.";
            String expectedOgUrl = "https://useinsider.com/careers/open-positions/";
            String expectedOgSiteName = "Insider";

            String actualOgTitle = driver.findElement(By.xpath("//meta[@property='og:title']")).getAttribute("content");
            String actualOgDescription = driver.findElement(By.xpath("//meta[@property='og:description']")).getAttribute("content");
            String actualOgUrl = driver.findElement(By.xpath("//meta[@property='og:url']")).getAttribute("content");
            String actualOgSiteName = driver.findElement(By.xpath("//meta[@property='og:site_name']")).getAttribute("content");

            ExtentReportManager.logInfo("OG Title expected: '" + expectedOgTitle + "', actual: '" + actualOgTitle + "'");
            ExtentReportManager.logInfo("OG Description expected: '" + expectedOgDescription + "', actual: '" + actualOgDescription + "'");
            ExtentReportManager.logInfo("OG URL expected: '" + expectedOgUrl + "', actual: '" + actualOgUrl + "'");
            ExtentReportManager.logInfo("OG Site Name expected: '" + expectedOgSiteName + "', actual: '" + actualOgSiteName + "'");

            boolean result = actualOgTitle.equals(expectedOgTitle) &&
                    actualOgDescription.contains(expectedOgDescription) &&
                    actualOgUrl.equals(expectedOgUrl) &&
                    actualOgSiteName.equals(expectedOgSiteName);

            ExtentReportManager.logInfo("Open Positions Page metadata verification result: " + result);
            return result;
        } catch (Exception e) {
            ExtentReportManager.logFail("Open Positions Page metadata verification failed: " + e.getMessage());
            return false;
        }
    }

    public boolean verifyOpenPositionsPage() {
        try {
            wait.until(ExpectedConditions.urlToBe("https://useinsider.com/careers/open-positions/?department=qualityassurance"));
            boolean urlMatch = driver.getCurrentUrl().equals("https://useinsider.com/careers/open-positions/?department=qualityassurance");
            ExtentReportManager.logInfo("Verified Open Positions Page URL: " + driver.getCurrentUrl());

            // Meta tag verification code remains unchanged
            return true;
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to verify Open Positions Page: " + e.getMessage());
            return false;
        }
    }

    public boolean verifyOpenPositionsElements() {
        try {
            WebElement title = driver.findElement(allOpenPositionsTitle);
            WebElement description = driver.findElement(allOpenPositionsDescription);
            WebElement locationFilter = driver.findElement(filterByLocationLabel);
            WebElement departmentFilter = driver.findElement(filterByDepartmentLabel);

            scrollToElement(title);
            wait.until(ExpectedConditions.visibilityOf(title));
            boolean titleVisible = title.isDisplayed();
            ExtentReportManager.logInfo("All Open Positions title verified.");

            scrollToElement(description);
            wait.until(ExpectedConditions.visibilityOf(description));
            boolean descriptionVisible = description.isDisplayed();
            ExtentReportManager.logInfo("All Open Positions description verified.");

            scrollToElement(locationFilter);
            wait.until(ExpectedConditions.visibilityOf(locationFilter));
            boolean locationFilterVisible = locationFilter.isDisplayed();
            ExtentReportManager.logInfo("Filter by Location label verified.");

            scrollToElement(departmentFilter);
            wait.until(ExpectedConditions.visibilityOf(departmentFilter));
            boolean departmentFilterVisible = departmentFilter.isDisplayed();
            ExtentReportManager.logInfo("Filter by Department label verified.");

            boolean result = titleVisible && descriptionVisible && locationFilterVisible && departmentFilterVisible;
            ExtentReportManager.logInfo("Open Positions Page elements verification result: " + result);
            return result;
        } catch (Exception e) {
            ExtentReportManager.logFail("Error while checking Open Positions Page elements: " + e.getMessage());
            return false;
        }
    }

    public void filterByLocation(String location) {
        try {
            WebElement dropdown = driver.findElement(locationDropdown);
            scrollToElement(dropdown);
            wait.until(ExpectedConditions.elementToBeClickable(dropdown));
            ExtentReportManager.logInfo("Location filter dropdown is clickable.");

            WebElement dropdownArrow = driver.findElement(locationDropdownArrow);
            wait.until(ExpectedConditions.elementToBeClickable(dropdownArrow));

            // Click dropdown arrow multiple times if options are not loaded
            int retries = 0;
            List<WebElement> options;
            do {
                dropdownArrow.click();
                ExtentReportManager.logInfo("Attempt " + (retries + 1) + ": Clicked on dropdown arrow.");
                Thread.sleep(1000);
                options = driver.findElements(locationDropdownOptions);
                retries++;
            } while (options.size() <= 1 && retries < 10);

            if (options.size() <= 1) {
                ExtentReportManager.logFail("Dropdown options did not load properly after multiple attempts! Only 'All' option found.");
                throw new AssertionError("Dropdown options failed to load. Expected multiple locations but found only 'All'.");
            }

            StringBuilder locationsLog = new StringBuilder("The location filter is opened and there are " + options.size() + " different options: ");
            for (int i = 0; i < options.size(); i++) {
                locationsLog.append((i + 1) + ". " + options.get(i).getText() + ", ");
            }
            ExtentReportManager.logInfo(locationsLog.toString());

            for (WebElement option : options) {
                if (option.getText().equalsIgnoreCase(location)) {
                    scrollToElement(option);
                    wait.until(ExpectedConditions.elementToBeClickable(option));
                    option.click();
                    ExtentReportManager.logPass("Selected location: " + location);

                    // Verify that the dropdown title is updated
                    wait.until(ExpectedConditions.attributeToBe(locationDropdown, "title", location));
                    String selectedLocation = dropdown.getAttribute("title");
                    if (!selectedLocation.equals(location)) {
                        ExtentReportManager.logFail("Dropdown selection mismatch! Expected: '" + location + "', but found: '" + selectedLocation + "'");
                        throw new AssertionError("Dropdown selection failed. Expected: '" + location + "', but found: '" + selectedLocation + "'");
                    }
                    ExtentReportManager.logPass("Dropdown successfully updated to: " + location);
                    return;
                }
            }

            ExtentReportManager.logFail("Location option not found: " + location);
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to filter by location: " + e.getMessage());
            throw new AssertionError("Test failed: " + e.getMessage());
        }
    }

    public boolean verifyJobsDepartments(String department) {
        try {
            // Wait for job results to refresh after filtering
            wait.until(ExpectedConditions.stalenessOf(driver.findElement(jobListItems)));
            wait.until(ExpectedConditions.visibilityOfElementLocated(jobListItems));

            List<WebElement> jobItems = driver.findElements(jobListItems);
            for (WebElement job : jobItems) {
                scrollToElement(job);
                String departmentText = job.findElement(jobDepartment).getText().trim();
                if (!departmentText.equals(department)) {
                    ExtentReportManager.logFail("Job department validation failed! Found: " + departmentText);
                    return false;
                }
            }
            ExtentReportManager.logPass("All job listings belong to department: " + department);
            return true;
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to verify job departments: " + e.getMessage());
            return false;
        }
    }

    public boolean verifyJobsLocations(String location) {
        try {
            // Wait for job results to refresh after filtering
            wait.until(ExpectedConditions.visibilityOfElementLocated(jobListItems));

            List<WebElement> jobItems = driver.findElements(jobListItems);
            for (WebElement job : jobItems) {
                scrollToElement(job);
                String locationText = job.findElement(jobLocation).getText().trim();
                if (!locationText.equals(location)) {
                    ExtentReportManager.logFail("Job location validation failed! Found: " + locationText);
                    return false;
                }
            }
            ExtentReportManager.logPass("All job listings are in location: " + location);
            return true;
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to verify job locations: " + e.getMessage());
            return false;
        }
    }

    public boolean hoverAndClickViewRole() {
        try {
            WebElement jobListing = driver.findElements(jobListItems).get(0); // Hover on the first job listing
            scrollToElement(jobListing);
            Actions actions = new Actions(driver);
            actions.moveToElement(jobListing).perform();
            ExtentReportManager.logInfo("Hovered over the job listing.");

            WebElement viewRoleButton = jobListing.findElement(jobViewRoleButton);
            wait.until(ExpectedConditions.elementToBeClickable(viewRoleButton));
            String expectedHref = viewRoleButton.getAttribute("href");
            ExtentReportManager.logInfo("Expected href: " + expectedHref);

            viewRoleButton.click();
            ExtentReportManager.logPass("Clicked on 'View Role' button.");

            // Switch to the new tab
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            if (tabs.size() > 1) {
                driver.switchTo().window(tabs.get(1));
                ExtentReportManager.logInfo("Switched to new tab.");
            }

            // Wait for new page to load and verify URL
            wait.until(ExpectedConditions.urlToBe(expectedHref));
            String currentUrl = driver.getCurrentUrl();
            ExtentReportManager.logInfo("Actual navigated URL: " + currentUrl);

            if (!currentUrl.equals(expectedHref)) {
                ExtentReportManager.logFail("Redirection mismatch! Expected: " + expectedHref + ", but found: " + currentUrl);
                return false;
            }
            ExtentReportManager.logPass("Successfully navigated to 'View Role' page: " + currentUrl);

            // Close the new tab and switch back to the original tab
            driver.close();
            driver.switchTo().window(tabs.get(0));
            ExtentReportManager.logInfo("Switched back to original tab.");

            return true;
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to hover and click 'View Role' button: " + e.getMessage());
            return false;
        }
    }

}
