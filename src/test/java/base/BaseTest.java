package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;
import utils.DriverManager;

public class BaseTest {
    protected WebDriver driver;
    protected ConfigReader configReader;

    @BeforeMethod
    public void setUp() {
        configReader = new ConfigReader();
        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        //get from config
        driver.get(configReader.getProperty("url"));
    }
    public WebDriver getDriver() {
        return driver;
    }
    @AfterMethod
    public void quitDriver() {
        if (driver != null) {
            DriverManager.quitDriver();
        }
    }
}