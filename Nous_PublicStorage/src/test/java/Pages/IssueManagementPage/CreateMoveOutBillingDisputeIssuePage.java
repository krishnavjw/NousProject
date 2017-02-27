package Pages.IssueManagementPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateMoveOutBillingDisputeIssuePage {
	
	@FindBy(xpath="//div[@id='createMoveoutBillingDispute']//h3[text()='Create Move Out Billing Dispute Issue']")
	public WebElement pagetitle;
	
	
	@FindBy(xpath="//span/textarea[@placeholder='Enter explanation...']")
	public WebElement explanation;
	
	@FindBy(xpath="//a[text()='Create Issue']")
	public WebElement createIssue_Btn;
	
	@FindBy(xpath="//span[text()='Last Gate Code Access is not available']/preceding-sibling::span")
	public WebElement lastGateCodeChkbox;
	
	

	@FindBy(xpath="//input[@id='ClaimedVacateHours']")
	public WebElement ClaimedHour;
	
	
	@FindBy(xpath="//input[@id='ClaimedVacateMinutes']")
	public WebElement ClaimedMinutes;
	
	

	@FindBy(xpath="//input[@id='ClaimedVacateAMPM']/following-sibling::span[text()='AM']")
	public WebElement AM_Chkbox;
	
	
	
	//input[@id='ClaimedVacateAMPM']/following-sibling::span[text()='AM']
	
	
	//input[@id='ClaimedVacateHours']
	
	
	public CreateMoveOutBillingDisputeIssuePage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);

	}
	
	
	public boolean verify_pagetitle()
	{
		return pagetitle.isDisplayed();
	}
	
	public void enter_Explanation(String text)
	{
		explanation.sendKeys(text);;
	}
	
	
	
	
	

	public void click_createIssue_Btn()
	{
		createIssue_Btn.click();
	}
	
	
	public void click_lastGateCodeChkbox()
	{
		lastGateCodeChkbox.click();
	}
	
	
	public void enter_ClaimedVacateTime(String hour,String Minute ) throws InterruptedException
	{
		ClaimedHour.sendKeys(hour);
		Thread.sleep(2000);
		ClaimedMinutes.sendKeys(Minute);
		Thread.sleep(2000);
		AM_Chkbox.click();
		
		
		
	}
	

	
	

}
