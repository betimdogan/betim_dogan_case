package com.insider.tests;

import com.insider.pages.HomePage;
import com.insider.pages.CareersPage;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InsiderTest {
    private WebDriver driver;
    private HomePage homePage;
    private CareersPage careersPage;

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
    public void testLocationsBlock() {
        ExtentReportManager.startTest("Locations Block Verification");
        homePage.clickCareers();
        careersPage = new CareersPage(driver);

        assertTrue(careersPage.isLocationsBlockPresent(), "Locations block is missing!");
        ExtentReportManager.logPass("Locations block is displayed successfully.");
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        ExtentReportManager.endTest();
    }
}