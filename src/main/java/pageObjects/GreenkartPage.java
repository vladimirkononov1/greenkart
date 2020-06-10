package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GreenkartPage {
	public WebDriver driver;
	
	private By offer = By.xpath("//a[@class='cart-header-navlink blinkingText']");
	private By logo = By.xpath("//div[@class='brand greenLogo']");
	private By search = By.cssSelector("form input");
	private By searchButton = By.cssSelector("button.search-button");
	
	
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

}
