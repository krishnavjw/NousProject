package Pages.IssueManagementPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IssueDecisionModelWindow {
	private WebDriver driver;
	public IssueDecisionModelWindow(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//span[text()='Decline']")
	private WebElement declineRdBtn;
	
	@FindBy(xpath="//span[text()='PM to Call Customer']")
	private WebElement PMToCallRdBtn;
	
	@FindBy(xpath="//textarea [@id='notesText']")
	private WebElement notesEdt;
	
	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement empIdEdt;
	
	@FindBy(xpath="//a[contains(text(),'Confirm')]")
	private WebElement confirmBtn;
	
	@FindBy(xpath="//span[text()='Approve']")
	private WebElement approveRdBtn;
	
	public void clkDeclineRdBtn(){
		declineRdBtn.click();
	}
	
	public void clkPMToCallRdBtn(){
		PMToCallRdBtn.click();
	}
	
	public void enterNotesEdit(String notesToEnter){
		notesEdt.sendKeys(notesToEnter);
	}
	
	public void enterEmpIdEdit(String empId){
		empIdEdt.sendKeys(empId);
	}
	
	public void clkConfirmBtn(){
		confirmBtn.click();
	}
	
	public void clkApproveRdBtn(){
		approveRdBtn.click();
	}
	
}
