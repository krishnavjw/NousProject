package Pages.PropertyManagementPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewNon_CustomerPropertyAccessInfoPage {
	
	@FindBy(xpath="//span[text()='View Non-Customer Property Access Info']")
	private WebElement page_Title;
	
	@FindBy(xpath="//a[contains(text(),'Edit')]")
	private WebElement edit_btn;
	
	public ViewNon_CustomerPropertyAccessInfoPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}


	public boolean verify_page_Title()
	{
		return page_Title.isDisplayed();
	}
	
	public void click_edit_btn()
	{
		edit_btn.click();;
	}


}
