package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DM_IssueDetailsPage {
	
	private WebDriver driver;
	
	@FindBy(xpath="//h3[starts-with(text(),'Issue')]")
	private WebElement pagetitle;
	
	@FindBy(id="approvedeclineButton")
	private WebElement continueBtn;
	
	@FindBy(xpath="//span[text()='Issue Decision']")
	private WebElement issuetitle;
	
	@FindBy(xpath="//div[@id='issueDecisionTemplate']//span[text()='Approve']/preceding-sibling::span[@class='button']")
	private WebElement approveRadioBtn;
	
	@FindBy(xpath="//div[@id='issueDecisionTemplate']//span[text()='Decline']/preceding-sibling::span[@class='button']")
	private WebElement declineRadioBtn;
	
	@FindBy(xpath="//div[@id='issueDecisionTemplate']//span[text()='PM to Call Customer']/preceding-sibling::span[@class='button']")
	private WebElement pmToCalCust;
	
	@FindBy(xpath="//div[@id='issueDecisionTemplate']//span[text()='Approver to Call Customer']/preceding-sibling::span[@class='button']")
	private WebElement approveToCalCust;
	
	@FindBy(id="notesText")
	private WebElement noteTxt;
	
	@FindBy(id="employeeNumber")
	private WebElement employeeTxt;
	
	@FindBy(xpath="//a[contains(text(),'Confirm')]")
	private WebElement confirmBtn;
	
	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement cancelBtn;
	
	
	
	public DM_IssueDetailsPage(WebDriver driver){
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean verify_Title(){
		return pagetitle.isDisplayed();
	}
	
	public void clk_ContinueBtn(){
		continueBtn.click();
	}
	
	public boolean verify_PageTitleIssue(){
		return issuetitle.isDisplayed();
	}
	
	public void clk_ApproveRadioBtn(){
		approveRadioBtn.click();
	}
	
	public void clk_DeclineRadioBtn(){
		declineRadioBtn.click();
	}
	
	public void clk_PmToCallCustRadioBtn(){
		pmToCalCust.click();
	}
	
	public void clk_ApproveToCalCust(){
		approveToCalCust.click();
	}
	
	public void enterNotes(String note){
		noteTxt.sendKeys(note);
	}
	
	public void enterEmployeeId(String emp){
		employeeTxt.sendKeys(emp);
	}
	
	public void clk_ConfirmBtn(){
		confirmBtn.click();
	}
	
	public void clk_CancelBtn(){
		cancelBtn.click();
	}
	


}
