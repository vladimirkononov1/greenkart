package greenKart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import pageObjects.GreenkartPage;
import resources.Base;

public class ValidateGreenkartPage extends Base {

	public WebDriver driver;
	public static Logger log = LogManager.getLogger(Base.class.getName());

	public GreenkartPage g;

	List<WebElement> productRemove;
	List<WebElement> products;
	List<WebElement> button;
	List<WebElement> addedProduct;

	@BeforeTest
	public void initialize() throws IOException {
		driver = initializeDriver();
		driver.get(prop.getProperty("url"));
		g = new GreenkartPage(driver);
	}

	@AfterTest(enabled = true)
	public void tearDown() {
		driver.quit();
		driver = null;
	}

	@BeforeMethod
	public void cleanUpCart() {
		// empty cart after test has finished
		if (g.getProceedButton().isEnabled()) {
			g.removeCartItems();
		} else {
			System.out.println("Cart is empty");
		}
	}

	// Verify GreenKart Logo
	@Test(enabled = false)
	public void validateTitle() {
		System.out.println(g.getLogo().getText());
		Assert.assertTrue(g.getLogo().isDisplayed(), "Logo is dislpayed");
		log.info("GreenKart logo is displayed");
	}

	// Validate Limited Offer
	@Test(enabled = false)
	public void validateOffer() {
		System.out.println(g.getOffer().getText());
		Assert.assertTrue(g.getOffer().isDisplayed(), "Offer is displayed");
		log.info("Offer link is displayed");

	}

	// Validate Top Deals link
	@Test(enabled = false)
	public void validateTopDeals() {
		System.out.println(g.getTopDeals().getText());
		Assert.assertTrue(g.getTopDeals().isDisplayed(), "Top Deals is not displayed");
		Assert.assertEquals(g.getTopDeals().getText(), "Top Deals", "Title is wrong or not displayed");
		log.info("Top Deals displayed");
	}

	// Validate Flight Booking link
	@Test(enabled = false)
	public void valiateFlightBooking() {
		System.out.println(g.getFlightBooking().getText());
//		Assert.assertTrue(g.getFlightBooking().isDisplayed(), "Flight Booking is not displayed");
		Assert.assertEquals(g.getFlightBooking().getText(), "Flight Booking");
//		g.getFlightBooking().click();
//		log.info("Flight Booking clicked");
	}

	// Validate Empty cart message
	@Test(enabled = false)
	public void validateCart() {
		System.out.println(g.cart().getText());
		Assert.assertTrue(g.cart().isDisplayed(), "Cart is not displayed");
		Assert.assertEquals(g.getEmptyCartText().getText(), "You cart is empty!");
		Assert.assertFalse(g.getProceedButton().isEnabled(), "Button should not be enabled");
	}

	// Validate product name on the page - NEED TO UPDATE, move itemsNeededList to resources!!!!
	@Test(enabled = true)
	public void validateProductNames() {
		List<WebElement> products = g.getProductNames();
		List<String> itemsNeededList = new ArrayList<String>();
		for (int i = 0; i < g.getProducts().size(); i++) {
			String[] name = products.get(i).getText().split("-");
			String formattedName = name[0].trim();
			itemsNeededList.add(formattedName);

			Assert.assertEquals(formattedName, itemsNeededList.get(i));
		}
		log.info("There are " + products.size() + " products on the GreenKart page");
		for(Object ob : itemsNeededList) {
			System.out.println(ob);
		}
//		Assert.assertEquals(products.get(0).getText(), "Brocolli - 1 Kg");


	}

	// Verify if items can be added to cart
	@Test(enabled = true)
	public void validateCartItems() throws InterruptedException {
		System.out.println("Validating Cart Items...");
		Thread.sleep(2000);
		g.addItem("Brocolli");
		String numItem = g.getNumItems().getText();
		g.cart().click();
		addedProduct = g.getProductAdded();
		Assert.assertTrue(g.getNumItems().getText().equals(numItem), "Number of items is not correct");
		System.out.println("Number of items in the cart is " + numItem);
		for(int i=0; i<addedProduct.size(); i++) {
			Assert.assertEquals(addedProduct.get(i).getText(), g.itemName);
			System.out.println("The Validated String is " + g.itemName);
		}

	}
	
	// Verify if items still present in cart upon refresh
	@Test(enabled = true)
	public void validateCartAfterRefresh() throws InterruptedException{
		System.out.println("Validating Cart After Refresh...");
		Thread.sleep(2000);
		addedProduct = g.getProductAdded();
		g.addItem("Brocolli");
		String numItem = g.getNumItems().getText();
		g.refreshPage();
		Assert.assertTrue(g.getNumItems().getText().equals(numItem), "Number of items is not correct");
		System.out.println("Number of items in the cart is " + numItem);

	}
	
	//Increase the count of item and verify if it is showing up in cart
	@Test(enabled = true)
	public void increaseItemCount() {
		System.out.println("Increasing Item Count in the cart...");
		g.addItem("Brocolli");
		g.addItem("Cucumber");
		System.out.println("Cart items count is " + g.getNumItems().getText());
		Assert.assertEquals(g.getNumItems().getText(), "2");
	}
}