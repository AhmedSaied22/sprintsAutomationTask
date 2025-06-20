package utils;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

public class TestListener implements ITestListener {

    private ExtentReports extent = ExtentReportManager.getReporter();

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = "";

        // Get test description from @Test annotation
        Test testAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
        if (testAnnotation != null && !testAnnotation.description().isEmpty()) {
            description = testAnnotation.description();
        }

        ExtentTest test = ExtentReportManager.createTest(testName, description);
        test.info("🚀 Starting test: " + testName);
        if (!description.isEmpty()) {
            test.info("📝 Description: " + description);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.PASS, "✅ Test passed successfully");
            test.info("⏱️ Execution time: " + (result.getEndMillis() - result.getStartMillis()) + "ms");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, "❌ Test failed: " + result.getThrowable().getMessage());
            test.info("⏱️ Execution time: " + (result.getEndMillis() - result.getStartMillis()) + "ms");

            // Take screenshot
            try {
                Object testInstance = result.getInstance();
                if (testInstance instanceof BaseTest) {
                    WebDriver driver = ((BaseTest) testInstance).getDriver();
                    if (driver != null) {
                        String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getName());
                        if (screenshotPath != null && !screenshotPath.isEmpty()) {
                            test.addScreenCaptureFromPath(screenshotPath);
                            test.info("📸 Screenshot captured");
                        }
                    }
                }
            } catch (Exception e) {
                test.log(Status.WARNING, "⚠️ Could not capture screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.SKIP, "⚠️ Test skipped: " + result.getThrowable().getMessage());
        }
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("🏁 Starting Test Suite: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("🏁 Finished Test Suite: " + context.getName());
        System.out.println("📊 Total tests: " + context.getAllTestMethods().length);
        System.out.println("✅ Passed: " + context.getPassedTests().size());
        System.out.println("❌ Failed: " + context.getFailedTests().size());
        System.out.println("⚠️ Skipped: " + context.getSkippedTests().size());

        if (extent != null) {
            extent.flush();
            System.out.println("📝 ExtentReport generated successfully!");
        }
        ExtentReportManager.removeTest();
    }
}