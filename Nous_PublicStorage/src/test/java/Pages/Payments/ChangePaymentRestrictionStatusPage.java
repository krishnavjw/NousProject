package Pages.Payments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChangePaymentRestrictionStatusPage {
public ChangePaymentRestrictionStatusPage(WebDriver driver){
	PageFactory.initElements(driver, this);
}

	@FindBy(xpath="//span[text()='Change Payment Restriction Status']")
	private WebElement changePaymentRestrictionStatusPageTitle;

	@FindBy(xpath="//input[@id='NoChecks']/following-sibling::span[@class='button']")
	private WebElement noChecksChkbox;
	
	@FindBy(xpath="//input[@id='NoCreditCards']/following-sibling::span[@class='button']")
	private WebElement noCreditCardsChkBox;
	
	@FindBy(xpath="//input[@id='NoPayments']/following-sibling::span[@class='button']")
	private WebElement noPaymentsChkBox;
	
	@FindBy(id="noteText")
	private WebElement noteEdt;
	
	@FindBy(id="employeeNumber")
	private WebElement empIdEdt;
	
	@FindBy(partialLinkText="Cancel")
	private WebElement cancelBtn;
	
	@FindBy(partialLinkText="Update")
	private WebElement updateBtn;
	
	public boolean is_changePaymentRestrictionStatusPageTitle(){
		return changePaymentRestrictionStatusPageTitle.isDisplayed();
		}
	
	public void click_noCreditCardsChkBox(){
		noCreditCardsChkBox.click();
	}
	
	public void click_noChecksChkbox(){
		noChecksChkbox.click();
	}
	
	public void click_noPaymentsChkBox(){
		noPaymentsChkBox.click();
	}
	
	public void enter_noteEdt(String note){
		noteEdt.sendKeys(note);
	}
	
	public void enter_empIdEdt(String empId){
		empIdEdt.sendKeys(empId);
	}
	
	public void click_updateBtn(){
		updateBtn.click();
	}
	public void updateBtn()
	{
		updateBtn.click();
	}
	
}
