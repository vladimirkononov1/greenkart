package greenKart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.GreenkartPage;
import resources.Base;

public class ValidateSearch extends Base {
	
	public WebDriver driver;
	public static Logger log = LogManager.getLogger(Base.class.getName());
	
	public GreenkartPage g;
	
	public By invisible = By.xpath("//h4[contains(text(),'Pumpkin')]");
			
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
	
	//validate search by beginning typing
	@Test(enabled=true)
	public void autoFill() {

		WebDriverWait wait = new WebDriverWait(driver, 3);

		String searchItem = "to";

		g.getSearch().clear();
		g.getSearch().sendKeys(searchItem);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(invisible));
		List<WebElement> products = g.getProductNames();
		List<String> productNames = new ArrayList<String>();
		

		for(int i=0; i<products.size(); i++) {
			String[] name = products.get(i).getText().split("-");
			String formattedName = name[0].trim();
			productNames.add(formattedName);
			System.out.println(productNames.get(i));

			Assert.assertTrue(productNames.get(i).contains(searchItem));
			
		}
		
	}
	
	//validate search by looking for specific item
	@Test(enabled=true)
	public void searchProduct() {
		
		WebDriverWait wait = new WebDriverWait(driver, 3);
		String searchItem = "Mushroom";
		
		g.getSearch().clear();
		g.getSearch().sendKeys(searchItem);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(invisible));

		List<WebElement> products = g.getProductNames();
		List<String> items = new ArrayList<String>();
		

		for(int i=0; i<products.size(); i++) {

			String[] item = (products.get(i).getText().split("-"));
			String formattedItem = item[0].trim();
			items.add(formattedItem);
			System.out.println(items.get(i));
		}
		Assert.assertTrue(products.size() == 1);
		
	}

	//validate search for not existing items
	@Test(enabled=true)
	public void notExisted() {

		String searchItem = "Doesn't exist";
		String expectedMessage = "Sorry, no products matched your search!";

		g.getSearch().clear();
		g.getSearch().sendKeys(searchItem);

		System.out.println(g.getWrongSearch().getText());
		Assert.assertEquals(g.getWrongSearch().getText(), expectedMessage);
		
	}

	
}
