package greenKart;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.GreenkartPage;
import resources.Base;

public class ExternalLinks extends Base{

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
		driver.quit();
		driver = null;
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
}
