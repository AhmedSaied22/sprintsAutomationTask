# E-commerce Website Testing with Selenium Automation

## Project Overview
Automate and validate key user flows in the e-commerce experience:

- Product search
- Adding items to the cart
- Completing a purchase


## 🧪 Technologies Used

- Java
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model (POM)

---
## Project Structure
```
sprintsTask/
├── src/
│   ├── main/java/
│   │   └── pages/           # Page Object classes
│   └── test/java/
│       ├── base/            # Base test setup
│       ├── tests/           # Test classes
│       └── utils/           # Utility classes
├── pom.xml                  # Maven dependencies
└── testng.xml              # TestNG configuration
```

## Test Cases
The project includes the following test cases:

### Search Tests
- TC_001: Verify that the search returns accurate results for known products
- TC_002: Ensure system handles empty search input without errors
- TC_003: Verify search for non-existent products shows appropriate message

### Cart Tests
- TC_004: Add product to cart with selected size & color
- TC_005: Remove product from cart
- TC_006: Add to cart fails without selecting size/color

### Checkout Tests
- TC_010: Successful checkout using standard flow
- TC_011: Checkout fails with missing required fields


## Setup Instructions

### Prerequisites
- Java JDK 17 or higher
- Maven
- Chrome browser

### Installation
1. Clone the repository:
   ```
   git clone <https://github.com/AhmedSaied22/sprintsAutomationTask>
   cd sprintsTask
   ```

2. Install dependencies:
   ```
   mvn clean install -DskipTests
   ```

## Running Tests

### Run all tests
```
mvn clean test
```
or run testng.xml file

### Run specific test class
```
mvn clean test -Dtest=SearchTests
```

### Run with specific TestNG XML file
```
mvn clean test -DsuiteXmlFile=testng.xml
```

## Test Reports
After running the tests, TestNG generates reports in reports folder with screenshot folder.

## Bug Reporting
Any issues found during testing are documented in the `BugReport.md` file, prioritizing those that impact user experience or functionality.

## Security Testing
Basic security validation has been performed focusing on:
- Input validation
- Common injection vulnerabilities (e.g., SQL injection, XSS)

## Manual Task
in This Link [Drive](https://docs.google.com/spreadsheets/d/1uZV7iLE72e8lBXsiv79D_1v64PSQyiX6/edit?usp=drive_link&ouid=113430173013318065519&rtpof=true&sd=true)
🧑‍💻 Author
Ahmed Saied
📧 ahmed.saied22@gmail.com
🔗 [LinkedIn](https://www.linkedin.com/in/ahmed-saieed/)

