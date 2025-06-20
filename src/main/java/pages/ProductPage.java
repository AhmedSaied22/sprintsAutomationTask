package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By productTitle = By.xpath("//span[@class='base']");
    private By sizeOptions = By.xpath("//div[@class='swatch-attribute size']//div[@class='swatch-option text']");
    private By colorOptions = By.xpath("//div[@class='swatch-attribute color']//div[@class='swatch-option color']");
    private By quantityInput = By.id("qty");
    private By addToCartButton = By.id("product-addtocart-button");
    private By successMessage = By.xpath("//div[contains(@class, 'message-success')]");
    private By errorMessage = By.xpath("//div[contains(@class, 'message-error')]");
    private By requiredFieldError = By.xpath("//div[contains(@class, 'mage-error')]");
    private By cartIcon = By.xpath("//a[@class='action showcart']");
    private By cartCounter = By.xpath("//span[@class='counter-number']");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Helper methods
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
    public String getProductTitle() {
        WebElement title = waitForElementToBeVisible(productTitle);
        return title.getText();
    }

    public void selectSize(String size) {
        List<WebElement> sizes = driver.findElements(sizeOptions);
        for (WebElement sizeOption : sizes) {
            if (sizeOption.getText().equals(size)) {
                sizeOption.click();
                break;
            }
        }
    }

    public void selectFirstAvailableSize() {
        List<WebElement> sizes = driver.findElements(sizeOptions);
        if (!sizes.isEmpty()) {
            sizes.get(0).click();
        }
    }

    public void selectColor(String color) {
        List<WebElement> colors = driver.findElements(colorOptions);
        for (WebElement colorOption : colors) {
            if (colorOption.getAttribute("aria-label").contains(color)) {
                colorOption.click();
                break;
            }
        }
    }

    public void selectFirstAvailableColor() {
        List<WebElement> colors = driver.findElements(colorOptions);
        if (!colors.isEmpty()) {
            colors.get(0).click();
        }
    }

    public void setQuantity(String quantity) {
        WebElement qtyField = waitForElementToBeVisible(quantityInput);
        qtyField.clear();
        qtyField.sendKeys(quantity);
    }

    public void clearQuantity() {
        WebElement qtyField = waitForElementToBeVisible(quantityInput);
        qtyField.clear();
    }

    public void clickAddToCart() {
        WebElement addToCartBtn = waitForElementToBeClickable(addToCartButton);
        addToCartBtn.click();
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            longWait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
             Thread.sleep(500);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        return isElementPresent(errorMessage) || isElementPresent(requiredFieldError);
    }

    public String getErrorMessage() {
        if (isElementPresent(errorMessage)) {
            return driver.findElement(errorMessage).getText();
        } else if (isElementPresent(requiredFieldError)) {
            return driver.findElement(requiredFieldError).getText();
        }
        return "";
    }

    public void clickCartIcon() {
        try {
            try {
                waitForElementToBeVisible(successMessage);
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            
            WebElement cart = waitForElementToBeClickable(cartIcon);
            cart.click();
            
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCartCount() {
        try {
            WebElement counter = waitForElementToBeVisible(cartCounter);
            return counter.getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public boolean isProductPageLoaded() {
        return isElementPresent(productTitle) && isElementPresent(addToCartButton);
    }
}
