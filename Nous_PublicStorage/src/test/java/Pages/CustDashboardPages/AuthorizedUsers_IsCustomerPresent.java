package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuthorizedUsers_IsCustomerPresent {

	@FindBy(xpath="//span[text()='Yes']/preceding-sibling::span")
	private WebElement RdBtn_Yes;
	
	@FindBy(xpath="//a[contains(text(),'Continue')]")
	private WebElement Btn_Continue;
	
	public AuthorizedUsers_IsCustomerPresent(WebDriver driver)
	
	{
		PageFactory.initElements(driver, this);
	}
	
	
	public void clk_YESRdButton()
	{
		RdBtn_Yes.click();
	}
	
	public void clk_ContButton()
	{
		Btn_Continue.click();
	}
	
}
