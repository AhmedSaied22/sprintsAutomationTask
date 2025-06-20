package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class CartTests extends BaseTest {

    @Test(priority = 1, description = "TC_004: Add product to cart with selected size & color")
    public void testAddProductToCart() {
        HomePage homePage = new HomePage(driver);
        ProductListingPage productListingPage = new ProductListingPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.navigateToHomePage();
     
        homePage.searchForProduct("Hoodie");
        Assert.assertTrue(productListingPage.areProductsDisplayed(), "Products should be displayed");
        productListingPage.clickFirstProduct();
        Assert.assertTrue(productPage.isProductPageLoaded(), "Product page should be loaded");
        productPage.selectFirstAvailableSize();
        productPage.selectFirstAvailableColor();
        productPage.setQuantity("1");
        productPage.clickAddToCart();
        Assert.assertTrue(productPage.isSuccessMessageDisplayed(), "Success message should be displayed");
        
        try {
            Thread.sleep(500);
            productPage.clickCartIcon();
            cartPage.waitForCartToLoad();
            boolean hasItems = cartPage.hasItemsInCart();
            Assert.assertTrue(hasItems, "Cart should have at least one item");
            System.out.println("✅ TC_004 PASSED: Product added to cart successfully");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to verify cart items: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "TC_005: Remove product from cart")
    public void testRemoveProductFromCart() {
        HomePage homePage = new HomePage(driver);
        ProductListingPage productListingPage = new ProductListingPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        try {
            homePage.navigateToHomePage();
            homePage.searchForProduct("Hoodie");
            productListingPage.clickFirstProduct();
            productPage.selectFirstAvailableSize();
            productPage.selectFirstAvailableColor();
            productPage.setQuantity("1");
            productPage.clickAddToCart();
            
            Assert.assertTrue(productPage.isSuccessMessageDisplayed(), "Success message should be displayed");
                        Thread.sleep(500);
            productPage.clickCartIcon();
            
            cartPage.waitForCartToLoad();
            boolean hasItems = cartPage.hasItemsInCart();
            Assert.assertTrue(hasItems, "Cart should have items before removal");
            cartPage.removeFirstItem();
            Thread.sleep(500);
            Assert.assertTrue(cartPage.isEmptyCartMessageDisplayed() || cartPage.getCartItemCount() == 0,
                    "Cart should be empty after removal");
            System.out.println("✅ TC_005 PASSED: Product removed from cart successfully");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed during cart removal test: " + e.getMessage());
        }
    }

    @Test(priority = 3, description = "TC_006: Add to cart fails without selecting size/color")
    public void testAddToCartWithoutSelectingOptions() {
        HomePage homePage = new HomePage(driver);
        ProductListingPage productListingPage = new ProductListingPage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.navigateToHomePage();
        homePage.searchForProduct("Hoodie");
        productListingPage.clickFirstProduct();

        Assert.assertTrue(productPage.isProductPageLoaded(), "Product page should be loaded");
        productPage.clickAddToCart();

        Assert.assertTrue(productPage.isErrorMessageDisplayed(),
                "Error message should be displayed when required options are not selected");
        System.out.println("✅ TC_006 PASSED: Add to cart without options shows error");
    }
}
