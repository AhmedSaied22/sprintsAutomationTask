package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ProductListingPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By productItems = By.xpath("//ol[@class='products list items product-items']/li");
    private By productTitles = By.xpath("//a[@class='product-item-link']");
    private By noResultsMessage = By.xpath("//div[contains(@class, 'message')]//div[contains(text(), 'Your search returned no results')]");
    private By searchResultsMessage = By.xpath("//div[contains(@class, 'toolbar-number')]");
    private By firstProductLink = By.xpath("(//a[@class='product-item-link'])[1]");
    private By productImages = By.xpath("//img[@class='product-image-photo']");

    public ProductListingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Helper methods>>
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

    // Actions
    public boolean areProductsDisplayed() {
        List<WebElement> products = driver.findElements(productItems);
        return products.size() > 0;
    }

    public boolean isNoResultsMessageDisplayed() {
        return isElementPresent(noResultsMessage);
    }

    public int getProductCount() {
        List<WebElement> products = driver.findElements(productItems);
        return products.size();
    }

    public boolean containsProductWithKeyword(String keyword) {
        List<WebElement> titles = driver.findElements(productTitles);
        for (WebElement title : titles) {
            if (title.getText().toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void clickFirstProduct() {
        WebElement firstProduct = waitForElementToBeClickable(firstProductLink);
        firstProduct.click();
    }

    public String getFirstProductName() {
        WebElement firstProduct = waitForElementToBeVisible(firstProductLink);
        return firstProduct.getText();
    }

    public boolean isSearchResultsPageLoaded() {
        return isElementPresent(productItems) || isElementPresent(noResultsMessage);
    }
}
