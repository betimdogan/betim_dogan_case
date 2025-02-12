package com.insider.tests;

import com.insider.pages.HomePage;
import com.insider.utils.ConfigReader;
import com.insider.utils.ExtentReportManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InsiderTest {
    private WebDriver driver;
    private HomePage homePage;

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
        // If needed, add support for other browsers here (Firefox, Edge, etc.)

        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("baseUrl")); // Navigate to the base URL

        // Call startTest() before initializing HomePage
        ExtentReportManager.startTest("HomePage Verification Test");

        homePage = new HomePage(driver);

        // Handle cookie banner before tests
        homePage.handleCookieBanner();
    }


    @Test
    public void testHomePageVerifications() {
        try {
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

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        ExtentReportManager.endTest();
    }
}
