package Pages.MakePaymentPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TransactionCompletePopup 
{



	@FindBy(id="employeeNumber")
	private WebElement empnum;
	
	@FindBy(xpath="//div[@class='k-window-content k-content']//a[contains(text(),'Cancel')]")
	private WebElement cancel_btn;
	
	@FindBy(xpath="//div[@id='transaction-complete-modal']//div[@class='change-due-container']")
	private WebElement txnmessage;
	
	@FindBy(partialLinkText="Ok")
	private WebElement ok_btn;
	
	@FindBy(xpath="//a[contains(text(),'Continue')]")
	private WebElement continue_Btn;
	
	//=========================rudra
	@FindBy(xpath="//div[@id='transaction-complete-modal']//div[contains(text(),'Amount:')]/following-sibling::div")
    private WebElement amount;	
	
	@FindBy(xpath="//div[@id='transaction-complete-modal']//div[@class='change-due-amount']")
	private WebElement changeDue;
	

     @FindBy(xpath="//span[text()='Transaction Complete']")
	private WebElement trans_Title;
	
	@FindBy(xpath="//div[@id='transaction-complete-modal']//div[contains(text(),' Space(s):')]/following-sibling::div")
	private WebElement trans_SpaceId;
	
	@FindBy(xpath="//div[@id='transaction-complete-modal']//div[contains(text(),' Amount:')]/following-sibling::div")
	private WebElement trans_Amount;
	
	@FindBy(xpath="//span[text()='Amount Owing']")
	private WebElement amount_Owing;
	
	public TransactionCompletePopup(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public void enterEmpNum(String empNum)
	{
		empnum.sendKeys(empNum);
	}
	
	public void clickOk_btn()
	{
		ok_btn.click();
	}
	
	public void clickContinueBtn(){
		continue_Btn.click();
	}
	
	public void click_CancelBtn(){
		cancel_btn.click();
	}
	
	public String getMessage_txnmessage(){
		return txnmessage.getText();
	}
	
	//============================Rudra
	
	public String getAmount()
	{
		return amount.getText().trim();
	}
	
	public String getChangeDue()
	{
		return changeDue.getText().trim();
	}
	
	public boolean verifyTrans_Title()
	{
		return trans_Title.isDisplayed();
	}
	
	public String getSpaceId()
	{
		return trans_SpaceId.getText().trim();
	}
	
	public boolean verifyAmountOwingTitle()
	{
		return amount_Owing.isDisplayed();
	}
	
	
}
