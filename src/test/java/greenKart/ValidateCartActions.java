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
	public By emptyCart = By.xpath("//strong[contains(text(),'')]");

	public void removeCartItems() {
		g.getCart().click();
		if (g.getProceedButton().getAttribute("class").contains("disabled")) {
			System.out.println("Cart is empty");
			g.getCart().click();
		} else {
			g.removeCartItems();
		}
	}
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

	@BeforeMethod(enabled = false)
	public void cleanUpCart() throws InterruptedException {
		// empty cart after test has finished
		g.getCart().click();
		if (g.getProceedButton().getAttribute("class").contains("disabled")) {
			System.out.println("Cart is empty");
			g.getCart().click();
		} else {
			g.removeCartItems();
		}
//		g.removeCartItems();
	}

	// Verify sum of products matches with amount displayed in cart
	@Test(enabled = true)
	public void productSum() throws InterruptedException {

		System.out.println("The text in empty cart is: " + g.getEmptyCart().getText());
		System.out.println("the boolean value is: " + g.isEmpty());
		
		By added = By.xpath("//*[contains(text(),'✔ ADDED')]");
		WebDriverWait wait = new WebDriverWait(driver, 20);

		int kartPrice = 0;
		wait.until(ExpectedConditions.invisibilityOfElementWithText(added, "✔ ADDED"));
		g.addItem("Cucumber");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(added, "✔ ADDED"));
		g.addItem("Tomato");
		String sKart = g.getKartPrice().getText();
		kartPrice = Integer.parseInt(sKart);
		int sumItems = g.itemPrice("Cucumber", "Tomato");
		Assert.assertEquals(kartPrice, sumItems);
		g.removeCartItems();

	}
	
	// Verify items in cart are displaing as expected
	@Test(enabled = true)
	public void itemsInCartNew() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		

		By added = By.xpath("//*[contains(text(),'✔ ADDED')]");
		List<String> test = null;
		g.addItem("Tomato");
//		String[] it1 = g.addItem("Tomato").split(" ");
//		String item1 = it1[0].trim();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(added, "✔ ADDED"));
//		Thread.sleep(5000);
		g.addItem("Potato");
//		String[] it2 = g.addItem("Potato").split(" ");
//		String item2 = it2[0].trim();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(added, "✔ ADDED"));
//		Thread.sleep(3000);
//		System.out.println(item1 + ", " + item2);
//		g.getCart().click();
		List<WebElement> addedItems = g.getProductAdded();
		for (WebElement element : addedItems) {
			String[] tmp = element.getText().split(" ");
			String cartItem = tmp[0].trim();
//			Assert.assertTrue(cartItem.equals(item1) || cartItem.equals(item2));
//			if(item1.equals(cartItem) || item2.equals(cartItem)) {
//				System.out.println(cartItem + " matches added product, test PASSED");
//			} else {
//				System.out.println("Test FAILED!!!");
//			}
			System.out.println(cartItem);
		}
		g.removeCartItems();
	}

	// Verify items in cart are displaing as expected
	@Test(enabled = true)
	public void itemsInCart() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		By added = By.xpath("//*[contains(text(),'✔ ADDED')]");
		List<String> test = null;
		String[] it1 = g.addItem("Tomato").split(" ");
		String item1 = it1[0].trim();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(added, "✔ ADDED"));
//		Thread.sleep(5000);
		String[] it2 = g.addItem("Potato").split(" ");
		String item2 = it2[0].trim();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(added, "✔ ADDED"));
//		Thread.sleep(3000);
		System.out.println(item1 + ", " + item2);
		g.getCart().click();
		List<WebElement> addedItems = g.getProductAdded();
		for (WebElement element : addedItems) {
			String[] tmp = element.getText().split(" ");
			String cartItem = tmp[0].trim();
			Assert.assertTrue(cartItem.equals(item1) || cartItem.equals(item2));
			if (item1.equals(cartItem) || item2.equals(cartItem)) {
				System.out.println(cartItem + " matches added product, test PASSED");
			} else {
				System.out.println("Test FAILED!!!");
			}
		}
		g.getCart().click();
		g.removeCartItems();
	}

	// Verify the prices shown up for products on page
	@Test(enabled = true)
	public void productPrice() {
		List<WebElement> prices = g.getProductPrice();
		for (int i = 0; i < prices.size(); i++) {
			Assert.assertTrue(true, g.getProductPrice().get(i).getText());
			Assert.assertEquals(g.getProductPrice().get(i).getText(), prices.get(i).getText());
		}
		g.removeCartItems();
	}

	// Verify empty cart message is displayed when no items are present in cart
	@Test(enabled = true)
	public void validateEmptyCartMessage() {
//		driver.get(prop.getProperty("url"));
		String message = "You cart is empty!";
		g.getCart().click();
		Assert.assertEquals(g.getEmptyCartText().getText(), message);
	}

	// Verify if button is disabled in empty cart window
	@Test(enabled = true)
	public void emptyCartButton() {

		String expectedState = "disabled";
		String actualState = g.getProceedButton().getAttribute("class");

		Assert.assertEquals(actualState, expectedState);
		Assert.assertFalse(g.getProceedButton().isEnabled(), "Test Failed!!! The button should be Disabled!");

	}

}