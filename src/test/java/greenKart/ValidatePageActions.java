package greenKart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import pageObjects.GreenkartPage;
import resources.Base;

public class ValidatePageActions extends Base {

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
//		driver.close();
	}

	@BeforeMethod
	public void cleanUpCart() {
		// empty cart after test has finished
		g.getCart().click();
		if (g.getProceedButton().isEnabled()) {
			g.removeCartItems();
			g.getCart().click();
		} 
		else {
			System.out.println("Cart is empty");
			g.getCart().click();
		}
	}
	
	@AfterMethod(enabled = false)
	public void removeCardItems() {
		//empty cart if it's not empty
		g.getCart().click();
		if (g.getProceedButton().isEnabled()) {
			g.removeCartItems();
		} else {
			System.out.println("Cart is empty");
		}
		g.getCart().click();
	}
	

	//Validate button text switched to Added when pressed 
	@Test(enabled=false, priority = 1)
	public void changeButtonText() {
		List<WebElement> prices = g.getProductPrice();
		System.out.println("Click ADD TO CART...");
		g.addItem("Cucumber");
		String changedButtonText = g.waitForElement();
		Assert.assertEquals(changedButtonText, "✔ ADDED");
		System.out.println(changedButtonText);
	}
		
	//Verify the prices shown up for products on page
	@Test(enabled = false)
	public void productPrice() {
		List<WebElement> prices = g.getProductPrice();
		for(int i = 0; i < prices.size(); i++) {
			Assert.assertTrue(true, g.getProductPrice().get(i).getText());
			Assert.assertEquals(g.getProductPrice().get(i).getText(), prices.get(i).getText());
		}
	}
	
	//Verify limited offer link
	@Test(enabled=true)
	public void limitedOffer() throws InterruptedException {
		
		String currentPageHandle = driver.getWindowHandle();
		g.getOffer().click();
		
		ArrayList<String> tabHandles = new ArrayList<String>(driver.getWindowHandles());
		String expectedPageTitle = "Rahul Shetty Academy";
		boolean newTabFound = false;
		
		for(String eachHandle : tabHandles) {
			driver.switchTo().window(eachHandle);
			if(driver.getTitle().equalsIgnoreCase(expectedPageTitle)) {
				Assert.assertEquals(driver.getTitle(), expectedPageTitle);
				System.out.println(driver.getTitle());
				driver.close();
				driver.switchTo().window(currentPageHandle);
				newTabFound = true;

			}
		}
		
		System.out.println(driver.getTitle());
	}
		
}