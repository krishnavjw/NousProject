package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmergencyContactPopup {

	public EmergencyContactPopup(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath = "//a[contains(text(),'Edit')]")
	private WebElement edit_btn;
	
	
	public void click_EditBtn(){
		edit_btn.click();
	}
	
}
