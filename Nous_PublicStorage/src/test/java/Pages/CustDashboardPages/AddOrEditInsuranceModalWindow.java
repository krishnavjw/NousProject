package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddOrEditInsuranceModalWindow {
	
	
	@FindBy(xpath="//span[text()='Add/Edit Insurance']")
	private WebElement hdr_AddEditInsurance;
	
	@FindBy(xpath="//a[contains(text(),'Launch')]")
	private WebElement btn_Launch;
	
	
	public AddOrEditInsuranceModalWindow(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public boolean verify_AddEditInsuranceHdr()
	{
		return hdr_AddEditInsurance.isDisplayed();
	}
	
	public void clk_LaunchBtn()
	{
		btn_Launch.click();
	}

}
