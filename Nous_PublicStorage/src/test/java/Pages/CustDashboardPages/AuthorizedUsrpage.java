package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuthorizedUsrpage {

	
	
	
	@FindBy(xpath="//a[contains(text(),'Edit')]")
	private WebElement Btn_Edit;
	
	
	@FindBy(xpath="//span[text()='Authorized Users']")
	private WebElement Hdr_AuthorisedUsersPopup;
	
	
	public AuthorizedUsrpage(WebDriver driver)
	
	{
		PageFactory.initElements(driver, this);
	}
	
	public void clk_EditBtn()
	{
		Btn_Edit.click();
	}
	
	
	public boolean verify_AuthorizedAccessPopupIsDisplayed()
	{
		return Hdr_AuthorisedUsersPopup.isDisplayed();
	}
	
}
