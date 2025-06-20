package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutTests extends BaseTest {

    @Test(priority = 1, description = "TC_010: Successful checkout using standard flow")
    public void testSuccessfulCheckout() {
        HomePage homePage = new HomePage(driver);
        ProductListingPage productListingPage = new ProductListingPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        try {
            homePage.navigateToHomePage();
            homePage.searchForProduct("Hoodie");
            productListingPage.clickFirstProduct();
            
            productPage.selectFirstAvailableSize();
            productPage.selectFirstAvailableColor();
            productPage.setQuantity("1");
            productPage.clickAddToCart();
            
            Assert.assertTrue(productPage.isSuccessMessageDisplayed(), "Success message should be displayed");
            productPage.clickCartIcon();
            
            // Wait for cart page to load
            cartPage.waitForCartToLoad();
            
            // Verify items exist in cart
            boolean hasItems = cartPage.hasItemsInCart();
            Assert.assertTrue(hasItems, "Cart should have at least one item before checkout");
            
            cartPage.proceedToCheckout();
            checkoutPage.fillShippingInformation(
                    "ahmedsaied2019@gmail.com", "Ahmed", "Saied", "123 Main St", "Cairo", "11511", "1234567890");
            
            checkoutPage.selectShippingMethod();
            checkoutPage.clickNext();
            checkoutPage.placeOrder();
            
            // Verify order success
            Assert.assertTrue(checkoutPage.isOrderSuccessful(), "Order should be placed successfully");
            System.out.println("✅ TC_010 PASSED: Order placed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed during checkout process: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "TC_011: Checkout fails with missing required fields")
    public void testCheckoutWithMissingFields() {
        HomePage homePage = new HomePage(driver);
        ProductListingPage productListingPage = new ProductListingPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        try {
            homePage.navigateToHomePage();
            homePage.searchForProduct("Hoodie");
            productListingPage.clickFirstProduct();
            
            productPage.selectFirstAvailableSize();
            productPage.selectFirstAvailableColor();
            productPage.setQuantity("1");
            productPage.clickAddToCart();
            Thread.sleep(1000);
            Assert.assertTrue(productPage.isSuccessMessageDisplayed(), "Success message should be displayed");
            productPage.clickCartIcon();
            
            cartPage.waitForCartToLoad();
              boolean hasItems = cartPage.hasItemsInCart();
            Assert.assertTrue(hasItems, "Cart should have at least one item before checkout");
            cartPage.proceedToCheckout();
            checkoutPage.fillPartialShippingInformation("testuser@example.com", "Test");
            
            Thread.sleep(1000);
         checkoutPage.selectShippingMethod();
                        Thread.sleep(1000);
            checkoutPage.clickNext();
            Thread.sleep(1000);
            Assert.assertTrue(checkoutPage.areRequiredFieldErrorsDisplayed(),
                    "Validation errors should be displayed for missing fields");
            System.out.println("✅ TC_011 PASSED: Checkout with missing fields shows validation errors");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed during checkout validation test: " + e.getMessage());
        }
    }
}
