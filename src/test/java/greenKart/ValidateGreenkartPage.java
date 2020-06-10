package greenKart;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
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
}
