package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By searchBox = By.id("search");
    private By searchButton = By.xpath("//button[@title='Search']");
    private By logoLink = By.xpath("//a[@class='logo']");
    private By menMenu = By.xpath("//span[text()='Men']");
    private By topsSubmenu = By.xpath("//a[contains(@href, 'men/tops-men')]");
    private By hoodiesLink = By.xpath("//a[contains(@href, 'hoodies-and-sweatshirts-men')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Page Actions
    public void navigateToHomePage() {
        WebElement logo = waitForElementToBeClickable(logoLink);
        logo.click();
    }

    public void searchForProduct(String productName) {
        WebElement searchField = waitForElementToBeVisible(searchBox);
        searchField.clear();
        searchField.sendKeys(productName);

        WebElement searchBtn = waitForElementToBeClickable(searchButton);
        searchBtn.click();
    }

    public void performEmptySearch() {
        WebElement searchField = waitForElementToBeVisible(searchBox);
        searchField.clear();

        WebElement searchBtn = waitForElementToBeClickable(searchButton);
        searchBtn.click();
    }

    public void navigateToHoodiesSection() {
        // Navigate to Men > Tops > Hoodies
        WebElement menMenuElement = waitForElementToBeClickable(menMenu);
        menMenuElement.click();

        WebElement topsElement = waitForElementToBeClickable(topsSubmenu);
        topsElement.click();

        WebElement hoodiesElement = waitForElementToBeClickable(hoodiesLink);
        hoodiesElement.click();
    }

    public boolean isHomePageLoaded() {
        return isElementPresent(logoLink);
    }
}
