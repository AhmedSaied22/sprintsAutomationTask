package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductListingPage;

public class SearchTests extends BaseTest {

    @Test(priority = 1, description = "TC_001: Verify that the search returns accurate results for known products")
    public void testValidProductSearch() {
        // Initialize page objects
        HomePage homePage = new HomePage(driver);
        ProductListingPage productListingPage = new ProductListingPage(driver);
        
        // Navigate to home page
        homePage.navigateToHomePage();
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page should be loaded");
        
        // Search for "Hoodie"
        homePage.searchForProduct("Hoodie");
        
        // Verify search results
        Assert.assertTrue(productListingPage.isSearchResultsPageLoaded(), 
                         "Search results page should be loaded");
        
        Assert.assertTrue(productListingPage.areProductsDisplayed(), 
                         "Products should be displayed in search results");
        
        Assert.assertTrue(productListingPage.containsProductWithKeyword("Hoodie"), 
                         "Search results should contain hoodie-related products");
        
        int productCount = productListingPage.getProductCount();
        Assert.assertTrue(productCount > 0, "At least one product should be found");
        
        System.out.println("✅ TC_001 PASSED: Valid product search returned " + productCount + " results");
    }
    
    @Test(priority = 2, description = "TC_002: Ensure system handles empty search input without errors")
    public void testEmptySearch() {
        // Initialize page objects
        HomePage homePage = new HomePage(driver);
        ProductListingPage productListingPage = new ProductListingPage(driver);

        // Navigate to home page
        homePage.navigateToHomePage();
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page should be loaded");

        // Perform empty search
        homePage.performEmptySearch();
        // not worked cuz button not clickable
        boolean isResultsPageLoaded = productListingPage.isSearchResultsPageLoaded();

        if (isResultsPageLoaded) {
            boolean hasProducts = productListingPage.areProductsDisplayed();
            boolean hasNoResultsMessage = productListingPage.isNoResultsMessageDisplayed();

            Assert.assertTrue(hasProducts || hasNoResultsMessage,
                             "Either products should be displayed or 'no results' message should appear");

            System.out.println("✅ TC_002 PASSED: Empty search handled appropriately");
        } else {
            Assert.assertTrue(homePage.isHomePageLoaded(),
                             "Should stay on home page if search is not triggered");
            System.out.println("✅ TC_002 PASSED: Empty search not triggered (acceptable behavior)");
        }
    }

    @Test(priority = 3, description = "TC_003: Verify search for non-existent products shows appropriate message")
    public void testNonExistentProductSearch() {
        HomePage homePage = new HomePage(driver);
        ProductListingPage productListingPage = new ProductListingPage(driver);

        homePage.navigateToHomePage();
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page should be loaded");

        String nonExistentProduct = "xyznonexistentproduct123";
        homePage.searchForProduct(nonExistentProduct);

        Assert.assertTrue(productListingPage.isSearchResultsPageLoaded(),
                         "Search results page should be loaded");

        Assert.assertTrue(productListingPage.isNoResultsMessageDisplayed() ||
                         !productListingPage.areProductsDisplayed(),
                         "No results message should be displayed or no products should be shown");

        System.out.println("✅ TC_003 PASSED: Non-existent product search handled correctly");
    }
}