package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BuyMerchandisePage {
	
	@FindBy(xpath="//div[@id='productStack']//a[@id='MerchandiseWindow-AddToCart']")
	private WebElement addItemsToCartBtn;
	
	
	@FindBy(xpath="(//div[@id='productStack']//div[@style='display: block;'])[1]//input[@class='product-quantity webchamp-textbox']")
	private WebElement quantityTextBox;
	
	@FindBy(xpath="(//div[@id='productStack']//div[@style='display: block;'])[1]//div[@class='floatleft product-details-container']//div[contains(@class,'price')]")
	private WebElement productPrice;
	
	@FindBy(xpath="//div[@id='Merchandise']//a[text()='Close']")
	private WebElement closeBtn;
	
	
	public BuyMerchandisePage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public String getProductPrice(){
		
		return productPrice.getText();
	}
	
	public void enterQuantity(String qty){
		quantityTextBox.sendKeys(qty);
		
	}
	
	public void clickAddItemsToCartBtn(){
		addItemsToCartBtn.click();
	}
	
	public void clickCloseBtn(){
		closeBtn.click();
	}
	
}
