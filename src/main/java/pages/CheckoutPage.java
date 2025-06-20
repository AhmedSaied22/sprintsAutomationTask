package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By emailField = By.id("customer-email");
    private By firstNameField = By.name("firstname");
    private By lastNameField = By.name("lastname");
    private By companyField = By.name("company");
    private By streetAddressField = By.name("street[0]");
    private By cityField = By.name("city");
    private By stateDropdown = By.name("region_id");
    private By zipCodeField = By.name("postcode");
    private By countryDropdown = By.name("country_id");
    private By phoneField = By.name("telephone");
    private By loadingMask = By.xpath("//div[contains(@class, 'loading-mask')]//div[contains(@class, 'loader')]");
    private By shippingMethodRadio = By.xpath("//input[@type='radio' and @name='ko_unique_1']");
    private By nextButton = By.xpath("//button[@class='button action continue primary']");
    private By placeOrderButton = By.xpath("//button[@title='Place Order']");
    private By orderSuccessMessage = By.xpath("//h1[contains(text(), 'Thank you for your purchase!')] | //span[contains(text(), 'Thank you for your purchase!')] | //*[contains(text(), 'Thank you for your purchase!')]");
    private By requiredFieldErrors = By.xpath("//div[contains(@class, 'field-error')]");

    public CheckoutPage(WebDriver driver) {
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

    private void waitForElementToDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    private void selectDropdownByVisibleText(By locator, String text) {
        WebElement dropdown = waitForElementToBeVisible(locator);
        Select select = new Select(dropdown);
        select.selectByVisibleText(text);
    }

    private void sleepInSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Page Actions
    public void fillShippingInformation(String email, String firstName, String lastName,
                                        String streetAddress, String city, String zipCode, String phone) {

        // Wait for checkout page to load
        waitForElementToBeVisible(emailField);

        // Fill email
        WebElement emailInput = driver.findElement(emailField);
        emailInput.clear();
        emailInput.sendKeys(email);

        // Fill first name
        WebElement firstNameInput = waitForElementToBeVisible(firstNameField);
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);

        // Fill last
        WebElement lastNameInput = driver.findElement(lastNameField);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);

        // Fill street
        WebElement streetInput = driver.findElement(streetAddressField);
        streetInput.clear();
        streetInput.sendKeys(streetAddress);

        // fill city
        WebElement cityInput = driver.findElement(cityField);
        cityInput.clear();
        cityInput.sendKeys("Cairo");


        selectDropdownByVisibleText(countryDropdown, "Egypt");
        
        // Wait for state dropdown to update based on country
        sleepInSeconds(1);

        // zip code
        WebElement zipInput = driver.findElement(zipCodeField);
        zipInput.clear();
        zipInput.sendKeys(zipCode);

        // fill phone
        WebElement phoneInput = driver.findElement(phoneField);
        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }

    public void fillPartialShippingInformation(String email, String firstName) {
        WebElement emailInput = waitForElementToBeVisible(emailField);
        emailInput.clear();
        emailInput.sendKeys(email);

        WebElement firstNameInput = waitForElementToBeVisible(firstNameField);
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    public void selectShippingMethod() {
        try {
            // Wait briefly to ensure shipping methods are loaded
            Thread.sleep(1500);
            
            List<WebElement> shippingMethods = driver.findElements(shippingMethodRadio);
            
            if (!shippingMethods.isEmpty()) {
                // choose the first shipping method "to make it easier"
                WebElement shippingMethod = shippingMethods.get(0);
                waitForElementToBeClickable(By.xpath("//input[@type='radio' and @name='ko_unique_1']")).click();
                
                Thread.sleep(1000);
            } else {
                System.out.println("No shipping methods available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickNext() {
        try {
            // Wait until next button is clickable
            WebElement nextBtn = waitForElementToBeClickable(nextButton);
            nextBtn.click();
            
            Thread.sleep(2000);
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                shortWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingMask));
            } catch (Exception e) {
            }
            
            waitForLoadingToComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void placeOrder() {
        try {
            // Wait until place order button is clickable
            WebElement placeOrderBtn = waitForElementToBeClickable(placeOrderButton);
            placeOrderBtn.click();
             Thread.sleep(2000);
            
            try {
                WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                longWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingMask));
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOrderSuccessful() {
        try {
            Thread.sleep(7000); // Increased wait time
            
            try {
                WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Increased timeout
                WebElement element = longWait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage));
                System.out.println("Success message found: " + element.getText());
                return true;
            } catch (Exception e) {
                System.out.println("First attempt to find success message failed: " + e.getMessage());
                // Try with JavaScript to check page content
                try {
                    String pageSource = driver.getPageSource();
                    if (pageSource.contains("Thank you for your purchase")) {
                        System.out.println("Success message found in page source");
                        return true;
                    }
                } catch (Exception jsException) {
                    System.out.println("JavaScript check failed: " + jsException.getMessage());
                }
                
                // Try one more time with direct element check
                boolean isPresent = isElementPresent(orderSuccessMessage);
                if (isPresent) {
                    System.out.println("Success message found through direct check");
                    return true;
                } else {
                    System.out.println("Success message not found in any check");
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in isOrderSuccessful: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean areRequiredFieldErrorsDisplayed() {
        return isElementPresent(requiredFieldErrors);
    }

    public boolean isCheckoutPageLoaded() {
        return isElementPresent(emailField);
    }

    private void waitForLoadingToComplete() {
        try {
            if (isElementPresent(loadingMask)) {
                waitForElementToDisappear(loadingMask);
            }
            sleepInSeconds(2);
        } catch (Exception e) {
        }
    }

    public String getOrderSuccessMessage() {
        try {
            WebElement successMsg = waitForElementToBeVisible(orderSuccessMessage);
            return successMsg.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
