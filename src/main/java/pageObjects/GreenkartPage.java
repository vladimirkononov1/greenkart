package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GreenkartPage {
	public WebDriver driver;
	
	private By offer = By.xpath("//a[@class='cart-header-navlink blinkingText']");
	private By logo = By.xpath("//div[@class='brand greenLogo']");
	private By search = By.cssSelector("form input");
	private By searchButton = By.cssSelector("button.search-button");
	private By topDeals = By.xpath("//*[contains(text(),'Top Deals')]");
	private By flightBooking = By.xpath("//*[contains(text(),'Flight Booking')]");
	private By cart = By.cssSelector("a[class='cart-icon']");
	private By products = By.cssSelector("div[class='product']");
	private By productNames = By.cssSelector("h4[class='product-name']");
	
	
	public GreenkartPage(WebDriver driver) {
		this.driver=driver;
	}
	
	public WebElement getOffer() {
		return driver.findElement(offer);
	}
	
	public WebElement getLogo() {
		return driver.findElement(logo);
	}
	
	public WebElement getSearch() {
		return driver.findElement(search);
	}
	
	public WebElement getSearchButton() {
		return driver.findElement(searchButton);
	}
	
	public WebElement getTopDeals() {
		return driver.findElement(topDeals);
	}

	public WebElement getFlightBooking() {
		return driver.findElement(flightBooking);
	}
	
	public WebElement cart() {
		return driver.findElement(cart);
	}

	public List<WebElement> getProducts() {
		return driver.findElements(products);
	}
	
	public List<WebElement> getProductNames() {
		return driver.findElements(productNames);
	}

}

