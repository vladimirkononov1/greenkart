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
	
	@AfterMethod(enabled = true)
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
	@Test(enabled = true)
	public void changeButtonText() {
		System.out.println("Click ADD TO CART...");
		g.addItem("Cucumber");
		String changedButtonText = g.waitForElement();
		Assert.assertEquals(changedButtonText, "✔ ADDED");
		System.out.println(changedButtonText);
	}
	
	//Verify sum of products matches with amount displayed in cart
	@Test(enabled = true)
	public void productSum() throws InterruptedException {
		int kartPrice = 0;
		g.addItem("Cucumber");
		Thread.sleep(5000);
		g.addItem("Tomato");
		String sKart = g.getKartPrice().getText();
		kartPrice = Integer.parseInt(sKart);
		int sumItems = g.itemPrice("Cucumber", "Tomato");
		Assert.assertEquals(kartPrice, sumItems);
	}
}