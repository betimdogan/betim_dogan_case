# Insider QA Automation Test

## 📌 Project Overview
This project is a Selenium-based test automation suite designed to validate key functionalities on Insider’s website, focusing on the **Careers** and **Jobs Listing** pages. The framework is developed using **Java, JUnit5, and Selenium WebDriver**, following the **Page Object Model (POM) design pattern**.

## 🔍 Test Case Description
### ✅ Automated Test Scenarios
1. **Verify Home Page**
    - Navigate to [Insider Home Page](https://useinsider.com/)
    - Check if the home page is loaded successfully
    - Validate the meta tags and critical elements

2. **Verify Careers Page & Its Sections**
    - Navigate to Careers Page via **Company > Careers** menu
    - Check if the **Careers page is loaded** correctly
    - Validate meta tags
    - Verify presence of **Locations, Teams, and Life at Insider** sections

3. **Verify Teams Block and Job Items**
    - Check if the **Teams block** is displayed
    - Validate the number of job items before clicking **See All Teams**
    - Click **See All Teams** and verify that additional job items are displayed

4. **Verify Locations Block**
    - Ensure the **Locations block** is visible
    - Validate the listed locations with expected details

5. **Verify Life at Insider Block**
    - Check if the **Life at Insider** block is present
    - Validate its title and description

6. **Verify Jobs Listing Page**
    - Navigate to [Quality Assurance Jobs Page](https://useinsider.com/careers/quality-assurance/)
    - Validate **meta tags** and critical elements
    - Ensure that the page loads correctly

7. **Verify Open Positions Page**
    - Click on **See all QA jobs** button
    - Ensure redirection to **Open Positions** page
    - Validate **meta tags** and page elements

8. **Verify Jobs Filtering by Location**
    - Navigate to **Open Positions Page**
    - Filter jobs by **Location: Istanbul, Turkiye** & **Department: Quality Assurance**
    - Validate job listings match the filters
    - Click **View Role** and verify redirection to the **Lever Application Form**

## ⚙️ Tech Stack & Tools Used
- **Programming Language:** Java
- **Test Framework:** JUnit 5
- **Browser Automation:** Selenium WebDriver
- **Reporting:** ExtentReports
- **Dependency Management:** Maven
- **Design Pattern:** Page Object Model (POM)

## 🏗️ Project Structure
```
 betim_dogan_case/
 ├── src/
 │   ├── main/
 │   │   ├── java/com/insider/
 │   │   │   ├── base/                # BasePage for common utilities
 │   │   │   ├── pages/               # Page classes for different sections
 │   │   │   │   ├── HomePage.java
 │   │   │   │   ├── CareersPage.java
 │   │   │   │   ├── JobsListingPage.java
 │   │   │   ├── utils/                # Config reader & reporting utilities
 │   │   │   │   ├── ConfigReader.java
 │   │   │   │   ├── ExtentReportManager.java
 │   ├── test/
 │   │   ├── java/com/insider/tests/   # Test cases
 │   │   │   ├── InsiderTest.java
 │   │   ├── resources/                # Configuration files
 │   │   │   ├── config.properties
 ├── test-output/                     # ExtentReports output
 ├── pom.xml                           # Maven dependencies
 ├── README.md
```

## 📌 Setup & Installation Guide
### Prerequisites
Ensure you have the following installed:
- **Java 11+**
- **Maven**
- **Google Chrome** (Latest version)
- **ChromeDriver** (Matching Chrome version)

### 🔧 Installation Steps
1. **Clone the Repository**
   ```sh
   git clone https://github.com/betimdogan/betim_dogan_case.git
   cd betim_dogan_case
   ```

2. **Install Dependencies**
   ```sh
   mvn clean install
   ```

3. **Run Tests**
   ```sh
   mvn test
   ```

## 📝 Detailed Test Execution
### Running Tests Individually
To run a specific test, use:
```sh
mvn -Dtest=InsiderTest#testVerifyJobsFilteringByLocation test
```

### Test Reports
- After execution, **ExtentReports** generates a detailed test report at:
  ```
  test-output/index.html
  ```
  Open the file in a browser to view the results visually.

## 🚀 Test Design Approach
- **Assertions**: Using JUnit's `assertTrue()` and `assertEquals()` for validations
- **Wait Mechanisms**: Implemented **Explicit Waits** using `WebDriverWait`
- **Optimized Locators**: Using **XPath & CSS Selectors** to improve test stability
- **Logging & Reporting**: Integrated **ExtentReports** for structured logging
- **POM Structure**: Following **Page Object Model (POM)** for better maintainability

## 🛠️ Troubleshooting
### ❌ Common Issues & Fixes
| Issue | Possible Fix |
|--------|--------------|
| `org.openqa.selenium.NoSuchElementException` | Check if the locator is correct & element is visible |
| `chrome not reachable` | Ensure ChromeDriver is updated to match your Chrome version |
| `Test failing due to timeout` | Increase WebDriver wait times |

## 📜 License
This project is licensed under the **MIT License**.

## 📧 Contact
For any issues or contributions, feel free to reach out:
- **GitHub:** [betimdogan_case](https://github.com/betimdogan/betim_dogan_case)

---
🚀 **Happy Testing!**

