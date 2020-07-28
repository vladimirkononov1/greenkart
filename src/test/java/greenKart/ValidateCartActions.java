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

public class ValidateCartActions extends Base {

	public WebDriver driver;
	public static Logger log = LogManager.getLogger(Base.class.getName());

	public GreenkartPage g;

//	List<WebElement> productRemove;
//	List<WebElement> products;
//	List<WebElement> button;
//	List<WebElement> addedProduct;

	@BeforeTest
	public void initialize() throws IOException {
		driver = initializeDriver();
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
		
	//Verify sum of products matches with amount displayed in cart
	@Test(priority = 1, enabled = true)
	public void productSum() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		int kartPrice = 0;
		g.addItem("Cucumber");
		Thread.sleep(5000);
		g.addItem("Tomato");
		String sKart = g.getKartPrice().getText();
		kartPrice = Integer.parseInt(sKart);
		int sumItems = g.itemPrice("Cucumber", "Tomato");
		Assert.assertEquals(kartPrice, sumItems);
	}
		
	//Verify items in cart are displaing as expected
	@Test(priority = 2, enabled = true)
	public void itemsInCart() throws InterruptedException {
		List<String> test = null;
		String[] it1 = g.addItem("Tomato").split(" ");
		String item1 = it1[0].trim();
		Thread.sleep(5000);
		String[] it2 = g.addItem("Potato").split(" ");
		String item2 = it2[0].trim();
		Thread.sleep(3000);
		System.out.println(item1 + ", " + item2);
		g.getCart().click();
		List<WebElement> addedItems = g.getProductAdded();
		for(WebElement element : addedItems) {
			String[] tmp = element.getText().split(" ");
			String cartItem = tmp[0].trim();
			Assert.assertTrue(cartItem.equals(item1) || cartItem.equals(item2));
			if(item1.equals(cartItem) || item2.equals(cartItem)) {
				System.out.println(cartItem + " matches added product, test PASSED");
			} else {
				System.out.println("Test FAILED!!!");
			}
		}
	}
	
	//Verify the prices shown up for products on page
	@Test(priority = 3, enabled = false)
	public void productPrice() {
		List<WebElement> prices = g.getProductPrice();
		for(int i = 0; i < prices.size(); i++) {
			Assert.assertTrue(true, g.getProductPrice().get(i).getText());
			Assert.assertEquals(g.getProductPrice().get(i).getText(), prices.get(i).getText());
		}
	}
	
	//Verify empty cart message is displayed when no items are present in cart
	@Test (enabled = true)
	public void validateEmptyCartMessage()  {
		String message = "You cart is empty!";
		g.getCart().click();
		Assert.assertEquals(g.getEmptyCartText().getText(), message);
	}

}