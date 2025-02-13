package com.insider.tests;

import com.insider.pages.HomePage;
import com.insider.pages.CareersPage;
import com.insider.pages.JobsListingPage;
import com.insider.utils.ConfigReader;
import com.insider.utils.ExtentReportManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InsiderTest {
    private WebDriver driver;
    private HomePage homePage;
    private CareersPage careersPage;
    private JobsListingPage jobsListingPage;

    @BeforeAll
    public static void startReport() {
        ExtentReportManager.getInstance();
    }

    @BeforeEach
    public void setUp() {
        String browser = ConfigReader.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--disable-notifications"); // Disable pop-ups
            options.addArguments("--disable-popup-blocking");

            driver = new ChromeDriver(options);
        }

        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("baseUrl")); // Navigate to the base URL

        ExtentReportManager.startTest("HomePage BeforeEach Setup Verification Test");

        homePage = new HomePage(driver);
        homePage.handleCookieBanner();
    }

    @Test
    @Order(1)
    public void testHomePageVerifications() {
        ExtentReportManager.startTest("HomePage Verification Test");
        try {
            assertTrue(homePage.verifyHomePageMetaTags(), "Home page meta tag verification failed!");
            ExtentReportManager.logPass("Home page meta tags verified successfully.");

            assertEquals("#1 Leader in Individualized, Cross-Channel CX — Insider", homePage.getOgTitle(), "Meta 'og:title' mismatch.");
            ExtentReportManager.logPass("OG Title verification passed.");

            assertTrue(homePage.isLogoCorrect(), "Logo is incorrect.");
            ExtentReportManager.logPass("Logo verification passed.");

            assertTrue(homePage.isNavbarPresent(), "Navbar is missing.");
            ExtentReportManager.logPass("Navbar verification passed.");

            assertTrue(homePage.isAnnounceInfoPresent(), "Announce info missing.");
            ExtentReportManager.logPass("Announce info verification passed.");
        } catch (Exception e) {
            ExtentReportManager.logFail("Test failed: " + e.getMessage());
            throw e;
        }
    }


    @Test
    @Order(2)
    public void testCareersPageOpeningAndMetaTags() {
        ExtentReportManager.startTest("Careers Page Opening and Meta Tags Verification");
        homePage.clickCareers();
        careersPage = new CareersPage(driver);

        assertTrue(careersPage.isCareersPageOpened(), "Careers page did not open correctly.");
        ExtentReportManager.logPass("Careers page opened successfully.");

        assertTrue(careersPage.verifyCareersPageMetaTags(), "Meta tag verification failed!");
        ExtentReportManager.logPass("Meta tags verified successfully.");
    }

    @Test
    @Order(3)
    public void testTeamsBlockAndJobItems() {
        ExtentReportManager.startTest("Teams Block and Job Items Verification");
        homePage.clickCareers();
        careersPage = new CareersPage(driver);

        assertTrue(careersPage.isTeamsBlockPresent(), "Teams block is missing!");
        ExtentReportManager.logPass("Teams block is displayed successfully.");

        int initialJobItemCount = careersPage.getJobItemCount();
        assertEquals(3, initialJobItemCount, "Job items count before clicking 'See all teams' is incorrect!");
        ExtentReportManager.logInfo("Initial job items count before clicking 'See all teams': " + initialJobItemCount);

        careersPage.scrollToElement(careersPage.getSeeAllTeamsButton());
        careersPage.clickSeeAllTeams();

        int jobItemsCountAfterClick = careersPage.getJobItemCount();
        assertEquals(15, jobItemsCountAfterClick, "Job items count after 'See all teams' is incorrect!");
        ExtentReportManager.logPass("Job items count after clicking 'See all teams' is correct: " + jobItemsCountAfterClick);
    }

    @Test
    @Order(4)
    public void testLocationsBlock() {
        ExtentReportManager.startTest("Locations Block Verification");
        homePage.clickCareers();
        careersPage = new CareersPage(driver);

        assertTrue(careersPage.isLocationsBlockPresent(), "Locations block is missing!");
        ExtentReportManager.logPass("Locations block is displayed successfully.");
    }

    @Test
    @Order(5)
    public void testLifeAtInsiderBlock() {
        ExtentReportManager.startTest("Life At Insider Block Verification");
        homePage.clickCareers();
        careersPage = new CareersPage(driver);

        assertTrue(careersPage.isLifeAtInsiderBlockPresent(), "Life At Insider block is missing!");
        ExtentReportManager.logPass("Life At Insider block is displayed successfully.");
    }

    @Test
    @Order(6)
    public void testVerifyJobsListingPage() {
        ExtentReportManager.startTest("Jobs Listing Page Verification");
        driver.get("https://useinsider.com/careers/quality-assurance/");
        jobsListingPage = new JobsListingPage(driver);

        assertTrue(jobsListingPage.isJobsListingPageOpened(), "Jobs Listing Page did not open correctly.");
        ExtentReportManager.logPass("Navigated to Jobs Listing Page successfully.");

        assertTrue(jobsListingPage.verifyJobsListingPageMetaTags(), "Jobs Listing Page meta tag verification failed!");
        ExtentReportManager.logPass("Jobs Listing Page meta tags verified successfully.");

        assertTrue(jobsListingPage.isJobsListingPageElementsPresent(), "Jobs Listing Page elements verification failed!");
        ExtentReportManager.logPass("Jobs Listing Page elements verified successfully.");
    }

    @Test
    @Order(7)
    public void testOpenPositionsPage() {
        ExtentReportManager.startTest("Open Positions Page Verification Test");
        driver.get("https://useinsider.com/careers/quality-assurance/");
        JobsListingPage jobsListingPage = new JobsListingPage(driver);

        jobsListingPage.clickSeeAllQAJobs();
        assertTrue(jobsListingPage.verifyOpenPositionsPage(), "Open Positions Page URL verification failed!");
        ExtentReportManager.logPass("Successfully navigated to Open Positions Page.");

        assertTrue(jobsListingPage.verifyOpenPositionsPageMetaTags(), "Open Positions Page metadata verification failed!");
        ExtentReportManager.logPass("Open Positions Page metadata verified successfully.");

        assertTrue(jobsListingPage.verifyOpenPositionsElements(), "Open Positions Page elements verification failed!");
        ExtentReportManager.logPass("Open Positions Page elements verified successfully.");
    }

    @Test
    @Order(8)
    public void testVerifyJobsFilteringByLocation() {
        ExtentReportManager.startTest("Jobs Filtering by Location Test");
        driver.get("https://useinsider.com/careers/quality-assurance/");
        JobsListingPage jobsListingPage = new JobsListingPage(driver);

        jobsListingPage.clickSeeAllQAJobs();

        assertTrue(jobsListingPage.verifyOpenPositionsElements(), "Open Positions Page elements verification failed!");
        ExtentReportManager.logPass("Open Positions Page elements verified successfully.");

        jobsListingPage.filterByLocation("Istanbul, Turkiye");
        ExtentReportManager.logPass("Filtered job listings by location: Istanbul, Turkiye");

        // Verify that all jobs belong to the 'Quality Assurance' department
        assertTrue(jobsListingPage.verifyJobsDepartments("Quality Assurance"), "Job departments verification failed!");
        ExtentReportManager.logPass("All job listings belong to the 'Quality Assurance' department.");

        // Verify that all jobs are located in 'Istanbul, Turkiye'
        assertTrue(jobsListingPage.verifyJobsLocations("Istanbul, Turkiye"), "Job locations verification failed!");
        ExtentReportManager.logPass("All job listings are correctly located in 'Istanbul, Turkiye'.");

        // Hover and click on the 'View Role' button
        assertTrue(jobsListingPage.hoverAndClickViewRole(), "Failed to click 'View Role' button or incorrect redirection!");
        ExtentReportManager.logPass("Successfully hovered and clicked on 'View Role' button, verified redirection.");
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        ExtentReportManager.endTest();
    }
}