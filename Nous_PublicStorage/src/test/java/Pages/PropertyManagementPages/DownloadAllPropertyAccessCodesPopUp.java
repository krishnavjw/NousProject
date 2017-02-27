package Pages.PropertyManagementPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DownloadAllPropertyAccessCodesPopUp 
{
	@FindBy(id="employeeNumber")
	private WebElement empId;
	
	@FindBy(xpath="//a[contains(text(),'Download')]")
	private WebElement download_btn;
	
	public DownloadAllPropertyAccessCodesPopUp(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public void enterEmployeeId(String emp)
	{
		empId.sendKeys(emp);
	}
	
	public void clickDownload_btn()
	{
		download_btn.click();
	}

	public boolean verifyEmployeeIDField() 
	{
		return empId.isDisplayed();
		
	}

	public boolean verifyDownloadButton() 
	{
		
		return download_btn.isDisplayed();
	}

}
