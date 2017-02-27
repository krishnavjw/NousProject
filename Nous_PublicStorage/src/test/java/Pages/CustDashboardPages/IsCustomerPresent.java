package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IsCustomerPresent {

	public IsCustomerPresent(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//div[@class='padding-bottom radio-group']//span[text()='Yes']")
	private WebElement yes_radio;
	
	@FindBy(xpath="//a[contains(text(),'Continue')]")
	private WebElement continue_btn;
	
	
	public void click_YesBtn(){
		yes_radio.click();
	}
	
	public void click_ContinueBtn(){
		continue_btn.click();
	}
	
}
