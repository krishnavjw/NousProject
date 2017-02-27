package Pages.PropertyManagementPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PropertyAccessManagementPopUp 
{
	@FindBy(xpath="//div[@id='chooseWorkflow']//span[text()='View Property Access Codes']/preceding-sibling::span")
	private WebElement viewPropAccessCodes;
	
	
	@FindBy(xpath="//div[@id='chooseWorkflow']//span[text()='Create Non-Customer Gate Code']/preceding-sibling::span")
	private WebElement createNonCustGateCode;
	
	@FindBy(xpath="//div[@id='chooseWorkflow']//span[text()='View Access Configuration']/preceding-sibling::span")
	private WebElement viewAccessConfig;
	
	
	@FindBy(xpath="//div[@id='chooseWorkflow']//span[text()='Download All Property Access Codes']/preceding-sibling::span")
	private WebElement downloadAllPropAccessCode;
	
	@FindBy(xpath="//a[contains(text(),'Launch')]")
	private WebElement launch_btn;

	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement cancel_btn;
	
	public PropertyAccessManagementPopUp(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public void clickLaunch_btn()
	{
		launch_btn.click();
	}
	
	public void clickCancel_btn()
	{
		cancel_btn.click();
	}
	
	public void clickViewPropAccessCodes()
	{
		viewPropAccessCodes.click();
	}
	
	public void clickCreateNonCustGateCode()
	{
		createNonCustGateCode.click();
	}
	
	public void clickViewAccessConfig()
	{
		viewAccessConfig.click();
	}
	
	public void clickDownloadAllPropAccessCode()
	{
		downloadAllPropAccessCode.click();
	}
	

}
