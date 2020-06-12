package greenKart;

import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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

	//Verify GreenKart Logo
	@Test(enabled = true)
	public void validateTitle() {
		System.out.println(g.getLogo().getText());
		Assert.assertTrue(g.getLogo().isDisplayed(), "Logo is dislpayed");
		log.info("GreenKart logo is displayed");
	}

	//Validate Limited Offer
	@Test(enabled = true)
	public void validateOffer() {
		System.out.println(g.getOffer().getText());
		Assert.assertTrue(g.getOffer().isDisplayed(), "Offer is displayed");
		g.getOffer().click();
		log.info("Offer link is clickable");

	}

	//Validate Top Deals link
	@Test(enabled = true)
	public void validateTopDeals() {
		System.out.println(g.getTopDeals().getText());
		Assert.assertTrue(g.getTopDeals().isDisplayed(), "Top Deals is not displayed");
		g.getTopDeals().click();
		log.info("Top Deals clicked");
	}

	//Validate Flight Booking link
	@Test(enabled = true)
	public void valiateFlightBooking() {
		System.out.println(g.getFlightBooking().getText());
		Assert.assertTrue(g.getFlightBooking().isDisplayed(), "Flight Booking is not displayed");
		g.getFlightBooking().click();
		log.info("Flight Booking clicked");
	}

	//Validate Empty cart message
	@Test(enabled = true)
	public void validateCart() {
		System.out.println(g.cart().getText());
		Assert.assertTrue(g.cart().isDisplayed(), "Cart is not displayed");
		Assert.assertEquals(g.getEmptyCartText().getText(), "You cart is empty!");
		Assert.assertFalse(g.getProceedButton().isEnabled(), "Button should not be enabled");
	}

	@Test
	public void validateProductNames() {
		List<WebElement> products = g.getProductNames();
		for (int i = 0; i < g.getProducts().size(); i++) {
			System.out.println(i + 1 + " product is: " + products.get(i).getText());
		}
		log.info("There are " + products.size() + " products on the GreenKart page");
		Assert.assertEquals(products.get(0).getText(), "Brocolli - 1 Kg");

	}

	@Test(enabled = true)
	public void validateAddItemToCart() {
		productRemove = g.getProductRemove();
		products = g.getProductNames();
		button = g.getAddButtons();
		addedProduct = g.getProductAdded();
		String item = "";
		for (int i = 0; i < g.getProducts().size(); i++) {
			if (products.get(i).getText().contains("Brocolli")) {
				button.get(i).click();
				item = products.get(i).getText();
				System.out.println("added " + item);
			}
		}
		g.cart().click();
		for (int i = 0; i < addedProduct.size(); i++) {
			Assert.assertEquals(item, addedProduct.get(i).getText());
		}
		System.out.println(item + " added to the cart");

		if (g.getProceedButton().isEnabled()) {
			g.removeCartItems();
		} else {
			System.out.println("Cart is empty");
		}
	}

}
