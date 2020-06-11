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
	
	@BeforeTest
	public void initialize() throws IOException {
		driver = initializeDriver();
		driver.get(prop.getProperty("url"));
		g = new GreenkartPage(driver);
	}
	
	@AfterTest
	public void tearDown() {
		driver.close();
		driver = null;
	}
	
	@Test(enabled=true)
	public void validateTitle() {
		System.out.println(g.getLogo().getText());
		Assert.assertTrue(g.getLogo().isDisplayed(), "Logo is dislpayed");
		log.info("GreenKart logo is displayed");
	}
	
	@Test
	public void validateOffer() {
		System.out.println(g.getOffer().getText());
		Assert.assertTrue(g.getOffer().isDisplayed(), "Offer is displayed");
		g.getOffer().click();
		log.info("Offer link is clickable");
		
	}
	
	@Test
	public void validateTopDeals() {
		System.out.println(g.getTopDeals().getText());
		Assert.assertTrue(g.getTopDeals().isDisplayed(), "Top Deals is not displayed");
		g.getTopDeals().click();
		log.info("Top Deals clicked");
	}
	
	@Test
	public void valiateFlightBooking() {
		System.out.println(g.getFlightBooking().getText());
		Assert.assertTrue(g.getFlightBooking().isDisplayed(), "Flight Booking is not displayed");
		g.getFlightBooking().click();
		log.info("Flight Booking clicked");
	}
	
	@Test
	public void valiateCart() {
		System.out.println(g.cart().getText());
		Assert.assertTrue(g.cart().isDisplayed(), "Cart is not displayed");
		g.cart().click();
		log.info("Cart clicked");
	}
	
	@Test
	public void validateProductNames() {
		List<WebElement> products = g.getProductNames();
		for(int i=0; i<g.getProducts().size(); i++) {
			System.out.println(i+1 + " product is: " + products.get(i).getText());
		}
		log.info("There are " + products.size() + " products on the GreenKart page");
	}
	
}
