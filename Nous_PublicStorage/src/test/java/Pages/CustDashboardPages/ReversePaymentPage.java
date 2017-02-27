package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReversePaymentPage {

	public ReversePaymentPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);

	}
	
	@FindBy(xpath="//div[@id='reverse-payment-form']/div/div[@class='transaction-details']")
	public WebElement transaction_details;
	
	@FindBy(xpath="//*[@id='reverse-payment-form']//div[@class='transaction-detail__info']//span[@class='k-select']/span[text()='select']")
	public WebElement reason_dropdown;
	
	@FindBy(xpath="//div[@id='reverse-payment-form']//a[text()='Back to Dashboard']")
	public WebElement back_to_dashboard_btn;
	
	@FindBy(xpath="//div[@id='reverse-payment-form']//a[text()='Cancel']")
	public WebElement cancel_btn;
	
	@FindBy(xpath="//div[@id='reverse-payment-form']//button[text()='Reverse']")
	public WebElement reverse_btn;
	
	@FindBy(xpath="//div[@id='reverse-payment-form']//button[text()='Reapply']")
	public WebElement reapply_btn;
	
	@FindBy(xpath="//div[@id='reverse-payment-form']//div[@class='transaction-detail__info']/textarea[@name='Note']")
	public WebElement note;
	
	@FindBy(xpath="//div[@id='Reason-list']/ul/li[text()='Check Incorrect / Incomplete']")
	public WebElement sel_reason;
	
	@FindBy(xpath="//div[@id='Reason-list']/ul/li[text()='Misapplied Payment']")
	public WebElement select_reason;
	
	@FindBy(xpath="//div[@id='enoughCashAvailableRadioButtons']//span[contains(text(),'No')]/preceding-sibling::span[@class='button']")
	private WebElement isEnoughNoBtn;
	
	@FindBy(xpath="//div[@id='enoughCashAvailableRadioButtons']//span[contains(text(),'Yes')]/preceding-sibling::span[@class='button']")
	private WebElement isEnoughYesBtn;
	
	
		public String getTxnDetails(){
		return transaction_details.getText();
	}
	
	public void SelectValueFromReasonListeason(){
		sel_reason.click();
	}
	
	public void SelectValueFromReasonList(){
		select_reason.click();
	}
	
	public boolean backToDashboardBtn_Displayed(){
		return back_to_dashboard_btn.isDisplayed();
	}
	
	public boolean cancelBtn_Displayed(){
		return cancel_btn.isDisplayed();
	}
	
	public boolean reverseBtn_Displayed(){
		return reverse_btn.isDisplayed();
	}
	
	public boolean reapplyBtn_Displayed(){
		return reapply_btn.isDisplayed();
	}
	
	public void enter_Note(String text){
		note.sendKeys(text);
	}
	
	public void click_ReverseBtn(){
		reverse_btn.click();
	}
	
	public void clickReason(){
		reason_dropdown.click();
	}
	public void clk_NoRadioBtn(){
		isEnoughNoBtn.click();
	}
	
	public void clk_YesRadioBtn() {
		isEnoughYesBtn.click();
		
	}
	
}
