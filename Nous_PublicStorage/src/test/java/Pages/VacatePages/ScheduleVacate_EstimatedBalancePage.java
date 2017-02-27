package Pages.VacatePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ScheduleVacate_EstimatedBalancePage 
{
	@FindBy(xpath="//div[@id='estimated-balances']/div[starts-with(@id,'EstimatedBalance')]/h3")
	private WebElement Estimatedbalance_title;
	
	@FindBy(xpath="//div[@id='estimated-balances']//div[contains(text(),'Current Balance Due')]/following-sibling::div[@class='third col']")
	private WebElement current_balance;
	
	@FindBy(xpath="//div[@id='estimated-balances']//div[contains(text(),'Total Due')]/following-sibling::div[@id='unit-total-due']")
	private WebElement total_Due;
	
	@FindBy(linkText="Back to Dashboard")
	private WebElement backToDash;
	
	@FindBy(id="lnkCancel1")
	private WebElement cancel_btn;
	
	@FindBy(id="lnkReserve")
	private WebElement continue_btn;
	
	
	@FindBy(xpath="//div[contains(text(),'Total Due')]/following-sibling::div[@id='unit-total-due']")
	private WebElement get_TotalDueAmt;
	
	public ScheduleVacate_EstimatedBalancePage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public String verifyEstimate_Title()
	{
		return Estimatedbalance_title.getText();
	}
	
	public String getCurrent_Balance()
	{
		return current_balance.getText();
	}
	
	public String getTotal_Due()
	{
		return total_Due.getText();
	}
	
	public void clickCancel_btn()
	{
		cancel_btn.click();
	}
	
	public void clickContinue_btn()
	{
		continue_btn.click();
	}
	
	public void clickBackBashboard_btn()
	{
		backToDash.click();
	}
	

	public boolean verify_EstimatedBalDuepage(){
		return Estimatedbalance_title.isDisplayed();
	}
	
	public String get_TotalDueAmt(){
		
		return get_TotalDueAmt.getText();
	}




}
