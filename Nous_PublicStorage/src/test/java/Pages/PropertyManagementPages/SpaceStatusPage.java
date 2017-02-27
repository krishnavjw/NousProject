package Pages.PropertyManagementPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SpaceStatusPage {

	@FindBy(xpath="//input[@id='spaceNumber']")
	private WebElement spaceNumber_Txt;
	
	@FindBy(xpath="//a[@id='updateButton']")
	private WebElement update_Btn;
	
	
	@FindBy(xpath="//a[contains(text(),'Back to Dashboard')]")
	private WebElement backToDashboard_Btn;
	
	
	@FindBy(xpath="//div[@id='filterSection']//div//span[@class='k-widget k-dropdown k-header js-lock-status-dropdown margin-left floatleft']")
	private WebElement lockStatusDropdown;
	
	@FindBy(xpath="//div[@id='filterSection']//div//span[@class='k-widget k-dropdown k-header js-maintanence-status-dropdown margin-left floatleft']")
	private WebElement maintanenceStatusDropdown;
	
	@FindBy(xpath="//div[@id='titleSection']/h3[text()='Space Status']")
	private WebElement spaceStatus_Header;
	
	@FindBy(xpath="//div[@id='employeeNumberEntry']//div[text()='New Status:']/following-sibling::div/span[contains(@class,'k-widget k-dropdown')]/span[@class='k-dropdown-wrap k-state-default']/span[text()='Select']")
	private WebElement dropDown_NewStatus;
	
	
	public SpaceStatusPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public void clickDropdown_NewStatus(){
		dropDown_NewStatus.click();
	}
	
	public boolean isDisplayed_SpaceStatus_Header(){
		return spaceStatus_Header.isDisplayed();
	}
	
	public void enterSpaceNumber(String spaceNumber)
	{
		 spaceNumber_Txt.sendKeys(spaceNumber);
	}
	
	public void clk_UpdateBtn()
	{
		 update_Btn.click();
	}
	
	public void clk_BackToDashboard_Btn()
	{
		backToDashboard_Btn.click();
	}
	
	public void clk_LockStatusDropdown()
	{
		lockStatusDropdown.click();
	}
	
	public void clk_MaintanenceStatusDropdown()
	{
		maintanenceStatusDropdown.click();
	}
	
	
}
