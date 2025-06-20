package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By cartItems = By.xpath("//tbody[@class='cart item']");
    private By proceedToCheckoutButton = By.xpath("//button[@title='Proceed to Checkout']");
    private By viewCartLink = By.xpath("//a[contains(@href, 'checkout/cart')]");
    private By cartTitle = By.xpath("//h1[contains(@class, 'page-title')]");
    private By emptyCartMessage = By.xpath("//div[contains(@class, 'cart-empty')]");
    private By removeButtons = By.xpath("//a[@title='Remove item']");
    private By productNames = By.xpath("//strong[@class='product-item-name']//a");

    public CartPage(WebDriver driver) {
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
    public void navigateToCart() {
        try {
            // انتظر حتى يكون رابط عرض السلة قابلاً للنقر
            WebElement viewCart = waitForElementToBeClickable(viewCartLink);
            viewCart.click();
            Thread.sleep(500);
            waitForElementToBeVisible(cartTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCartPageLoaded() {
        return isElementPresent(cartTitle);
    }

    public boolean hasItemsInCart() {
        try {
            Thread.sleep(500);
            
            List<WebElement> items = driver.findElements(cartItems);
            if (items.size() > 0) {
                return true;
            }
            if (isElementPresent(emptyCartMessage)) {
                return false;
            }
            
            List<WebElement> productNameElements = driver.findElements(productNames);
            return productNameElements.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getCartItemCount() {
        List<WebElement> items = driver.findElements(cartItems);
        return items.size();
    }

    public void proceedToCheckout() {
        WebElement checkoutBtn = waitForElementToBeClickable(proceedToCheckoutButton);
        checkoutBtn.click();
    }

    public void removeFirstItem() {
        try {
            List<WebElement> removeBtns = driver.findElements(removeButtons);
                if (!removeBtns.isEmpty()) {
                WebElement removeBtn = removeBtns.get(0);
                waitForElementToBeClickable(removeButtons).click();
                Thread.sleep(500);
                By loadingMask = By.xpath("//div[contains(@class, 'loading-mask')]//div[contains(@class, 'loader')]");
                try {
                    WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    shortWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingMask));
                } catch (Exception e) {
                }
                
                try {
                    WebDriverWait messageWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    messageWait.until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(emptyCartMessage),
                        ExpectedConditions.numberOfElementsToBeLessThan(cartItems, removeBtns.size())
                    ));
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isEmptyCartMessageDisplayed() {
        return isElementPresent(emptyCartMessage);
    }

    public String getFirstProductName() {
        List<WebElement> productNameElements = driver.findElements(productNames);
        if (!productNameElements.isEmpty()) {
            return productNameElements.get(0).getText();
        }
        return "";
    }

    public boolean containsProduct(String productName) {
        List<WebElement> productNameElements = driver.findElements(productNames);
        for (WebElement nameElement : productNameElements) {
            if (nameElement.getText().contains(productName)) {
                return true;
            }
        }
        return false;
    }

    public void waitForCartToLoad() {
        try {
            waitForElementToBeVisible(cartTitle);
            Thread.sleep(500);
            By loadingMask = By.xpath("//div[contains(@class, 'loading-mask')]//div[contains(@class, 'loader')]");
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
                shortWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingMask));
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
