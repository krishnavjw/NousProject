package Pages.AuthorizedUsersDetails;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuthorizedUsers {
	
	WebDriver driver;
	
	@FindBy(xpath="//input[contains(@placeholder,'First Name')]")
	private WebElement FirstName;
	
	
	@FindBy(xpath="//input[contains(@placeholder,'Last Name')]")
	private WebElement LastName;
	
	@FindBy(xpath="//input[contains(@id,'Phone_AreaCode')]")
	private WebElement PhoneAreaCode;
	
	
	@FindBy(xpath="//input[contains(@id,'Phone_Exchange')]")
	private WebElement PhoneExchange;
	
	@FindBy(xpath="//input[contains(@id,'Phone_LineNumber')]")
	private WebElement PhoneLineNumber;
	
	@FindBy(xpath="//label[@class='webchamp-checkbox']//span[@class='button']")
	private WebElement siteChkBox;
	
	
	@FindBy(xpath="//a[text()='Clear']")
	private WebElement clearButton;
	
	
	
	
	
	
	
	
	
	
	
	public AuthorizedUsers(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void enter_FirstName(String text){
		FirstName.sendKeys(text);
	}
	
	public void enter_LastName(String text){
		LastName.sendKeys(text);
	}
	
	
	public void enter_PhoneAreaCode(String text){
		PhoneAreaCode.sendKeys(text);
	}
	
	
	public void enter_PhoneExchange(String text){
		PhoneExchange.sendKeys(text);
	}
	
	
	public void enter_PhoneLineNumber(String text){
		PhoneLineNumber.sendKeys(text);
	}
	
	
	public void selectPhoneType(WebDriver driver,String PhoneType){
		driver.findElement(By.xpath("//span[@class='k-input']")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		List<WebElement> listInDropDown = driver.findElements(
				By.xpath("//ul[contains(@id,'Phone_PhoneTypeID_listbox')]/li[contains(@class,'k-item')]"));
		for(WebElement phoneType:listInDropDown)
			if (phoneType.getText().equals(PhoneType)) {
				js.executeScript("arguments[0].click();", phoneType);
			}
		} 
		
	
	public void clk_SpaceCheckBox(){
		siteChkBox.click();
	}
	
	
	public void clk_Clear(){
		clearButton.click();
	}
	
	
	
	
	
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

