package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentReports getReporter() {
        if (extent == null) {
            String path = System.getProperty("user.dir") + "/reports/ExtentReport.html";

            // Create reports directory if it doesn't exist
            java.io.File reportsDir = new java.io.File(System.getProperty("user.dir") + "/reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
                System.out.println("✅ Created reports directory: " + reportsDir.getAbsolutePath());
            }

            System.out.println("📁 Report will be saved to: " + path);

            sparkReporter = new ExtentSparkReporter(path);
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // Configure the report
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Sprints E-commerce Test Report");
            sparkReporter.config().setReportName("Automation Test Results");

            // Set system information
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Environment", "Test");

            System.out.println("✅ ExtentReports initialized successfully!");
        }
        return extent;
    }

    public static ExtentTest createTest(String testName, String description) {
        ExtentTest extentTest = getReporter().createTest(testName, description);
        test.set(extentTest);
        return extentTest;
    }

    public static ExtentTest createTest(String testName) {
        ExtentTest extentTest = getReporter().createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void removeTest() {
        test.remove();
    }
}