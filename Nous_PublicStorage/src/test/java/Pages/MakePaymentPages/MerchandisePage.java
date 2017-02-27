package Pages.MakePaymentPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MerchandisePage {
	
	private WebDriver driver;
	
	@FindBy(xpath="//span[@id='Merchandise_wnd_title']")
	private WebElement merchandise_Title;
	
	@FindBy(xpath="//a[@id='MerchandiseWindow-AddToCart']")
	private WebElement addItemsToCart;
	
	@FindBy(xpath="//div[@id='Merchandise']//a[contains(text(),'Close')]")
	private WebElement closeBtn;
	
	@FindBy(xpath="//div[@id='productCategoryList']//ul/li//span[contains(text(),'All')]/preceding-sibling::span[@class='button']")
	private WebElement allOption;
	
	@FindBy(xpath="//div[@id='productCategoryList']//ul/li//span[contains(text(),'Boxes')]/preceding-sibling::span[@class='button']")
	private WebElement Boxes_Radiobtn;
	
	@FindBy(xpath="//div[@id='productStack']//div[text()='Box File Letter']//../div[@class='double-margin-top']//div[text()='Quantity:']/following-sibling::div/input")
	private WebElement EnterQuantity_txtBox;
	
	
	
	public MerchandisePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}
	
	public boolean isTitleMerchandise(){
		return merchandise_Title.isDisplayed();
	}
	
	public void clk_AddItemsToCart(){
		addItemsToCart.click();
	}
	
	
	public void clk_BoxesRadioBtn(){
		Boxes_Radiobtn.click();
	}
	
	public void clk_CloseBtn(){
		closeBtn.click();
	}
	
	public void  clk_AllOption() {
		if(!allOption.isSelected()){
			allOption.click();
		}
		
	}
	
	public void enter_Quantity(String Quantity){
		EnterQuantity_txtBox.sendKeys(Quantity);
	}
	
	
	
	public void browseByCat(String expVal,WebDriver driver){
		List<WebElement> listEle=driver.findElements(By.xpath("//div[@id='productCategoryList']/ul/li"));
		for (WebElement ele : listEle) {
			WebElement actualWbEleText =ele.findElement(By.xpath("//span[contains(text(),'"+expVal+"')]"));
			String actualTxt = actualWbEleText.getText();
			if(actualTxt.equals(expVal))
			{
				actualWbEleText.findElement(By.xpath("//span[contains(text(),'"+expVal+"')]/preceding-sibling::span[@class='button']")).click();
			}

		}

	}
	
	
	
	
	

}
