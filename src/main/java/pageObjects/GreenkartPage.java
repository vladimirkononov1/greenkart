package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GreenkartPage {
	public WebDriver driver;
	
//	WebDriverWait wait = new WebDriverWait(driver, 20);

	private By offer = By.xpath("//a[@class='cart-header-navlink blinkingText']");
	private By logo = By.xpath("//div[@class='brand greenLogo']");
	private By search = By.cssSelector("form input");
	private By searchButton = By.cssSelector("button.search-button");
	private By topDeals = By.xpath("//*[contains(text(),'Top Deals')]");
	private By flightBooking = By.xpath("//*[contains(text(),'Flight Booking')]");
	private By kart = By.cssSelector("a[class='cart-icon']");
	private By products = By.cssSelector("div[class='product']");
	private By productNames = By.cssSelector("h4[class='product-name']");
	private By addButton = By.xpath("//*[contains(text(),'ADD TO CART')]");
	private By productAdded = By.cssSelector("div[class='cart-preview active'] p[class='product-name']");
	private By emptyCartText = By.cssSelector("div[class='cart-preview active'] h2");
	private By productRemove = By.cssSelector("[class='cart-preview active'] a[class='product-remove']");
	private By proceedButton = By.xpath("//button[contains(text(), 'PROCEED TO CHECKOUT')]");
	private By numItems = By.xpath("//tr[1]//td[3]//strong[1]");
	private By addedText = By.className("added");
	private By productPrice = By.className("product-price");
	private By kartPrice = By.xpath("//div[@class='cart-info']/table/tbody/tr[2]/td[3]/strong");

	List<WebElement> button;
	List<WebElement> addedProduct;
//	List<WebElement> product;
	public String itemName = "";

	public GreenkartPage(WebDriver driver) {
		this.driver = driver;
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

	public WebElement getEmptyCartText() {
		return driver.findElement(emptyCartText);
	}

	public void setEmptyCartText(By emptyCartText) {
		this.emptyCartText = emptyCartText;
	}

	public WebElement getTopDeals() {
		return driver.findElement(topDeals);
	}

	public WebElement getFlightBooking() {
		return driver.findElement(flightBooking);
	}

	public WebElement getCart() {
		return driver.findElement(kart);
	}

	public List<WebElement> getProducts() {
		return driver.findElements(products);
	}

	public List<WebElement> getProductNames() {
		return driver.findElements(productNames);
	}

	public List<WebElement> getAddButtons() {
		return driver.findElements(addButton);
	}

	public List<WebElement> getProductAdded() {
		return driver.findElements(productAdded);
	}

	public List<WebElement> getProductRemove() {
		return driver.findElements(productRemove);
	}

	public WebElement getProceedButton() {
		return driver.findElement(proceedButton);
	}

	public WebElement getNumItems() {
		return driver.findElement(numItems);
	}

	public WebElement getAddedText() {
		return driver.findElement(addedText);
	}

	public List<WebElement> getProductPrice() {
		return driver.findElements(productPrice);
	}
	
	public WebElement getKartPrice() {
		return driver.findElement(kartPrice);
	}

	public void removeCartItems() {
		GreenkartPage g = new GreenkartPage(driver);
		List<WebElement> remove = g.getProductRemove();
		for (int i = 0; i < remove.size(); i++) {
			WebElement item = remove.get(i);
			item.click();
		}
	}

	public String addItem(String item) {
		GreenkartPage g = new GreenkartPage(driver);
		List<WebElement> product;
		button = g.getAddButtons();
		product = g.getProductNames();
		for (int i = 0; i < g.getProducts().size(); i++) {
			String name = product.get(i).getText();
			if (name.contains(item)) {
//				wait.until(ExpectedConditions.elementToBeClickable(button.get(i))).click();
				button.get(i).click();
				itemName = product.get(i).getText();
				System.out.println("added " + itemName);
			}
		}
		return itemName;
	}
	
	public String addMultipleItems(String item) {
		GreenkartPage g = new GreenkartPage(driver);
		List<WebElement> product;
		button = g.getAddButtons();
		product = g.getProductNames();
		for (int i = 0; i < g.getProducts().size(); i++) {
			WebElement increment = product.get(i).findElement(By.className("increment"));
			String name = product.get(i).getText();
			if (name.contains(item)) {
				button.get(i).click();
				increment.click();
				itemName = product.get(i).getText();
				System.out.println("added " + itemName);
			}
		}
		return itemName;
	}


	public void refreshPage() {
		GreenkartPage g = new GreenkartPage(driver);
		driver.navigate().refresh();
	}

	public int itemPrice(String item1, String item2) {
		GreenkartPage g = new GreenkartPage(driver);
		List<WebElement> product = g.getProducts();
		int price1 = 0;
		int price2 = 0;
		int sum = 0;
		for (int i = 0; i < getProductNames().size(); i++) {
			String wrap = product.get(i).getText();
			if (wrap.contains(item1)) {
				WebElement inside1 = product.get(i).findElement(By.className("product-price"));
				String s1 = inside1.getText();
				price1 = Integer.parseInt(s1);
				System.out.println("Price for " + item1 + " is " + price1);

			} else if (wrap.contains(item2)) {
				WebElement inside2 = product.get(i).findElement(By.className("product-price"));
				String s2 = inside2.getText();
				price2 = Integer.parseInt(s2);
				System.out.println("Price for " + item2 + " is " + price2);
			}
			sum = price1 + price2;
		} 
		return sum;
	}

}
