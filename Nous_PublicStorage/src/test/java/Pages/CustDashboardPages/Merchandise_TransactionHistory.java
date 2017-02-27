package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Merchandise_TransactionHistory {
	
	@FindBy(xpath="//span[contains(text(),'Merchandise Transaction History')]")
	public WebElement pagetitle;
	
	@FindBy(xpath="//div[@id='merchandiseTransactionsListContainer']//span[text()='select']")
	public WebElement sel_dropdown;
	
	@FindBy(xpath="//div[@id='merchandiseTransactions-list']/ul[@id='merchandiseTransactions_listbox']/li[1]")
	public WebElement option_dropdown;
	
	@FindBy(partialLinkText="Submit")
	public WebElement Submit;	
	
	@FindBy(xpath="//span[text()='Customer Name:']/following-sibling::div")
	public WebElement customerName;
	
	@FindBy(xpath="//span[text()='Account Number:']/following-sibling::div")
	public WebElement accNumber;
	
	@FindBy(id="noMerchandiseTransactionsContainer")
	public WebElement noMerchadiseTransactMsg;
	
	@FindBy(partialLinkText="Cancel")
	public WebElement cancel_Btn;
	

	public Merchandise_TransactionHistory(WebDriver driver)
	{
		PageFactory.initElements(driver, this);

	}
	
	
	public boolean verify_pagetitle()
	{
		return pagetitle.isDisplayed();
	}

	public void click_dropdown(){
		sel_dropdown.click();
	}
	
	public void click_option_dropdown(){
		option_dropdown.click();
	}
	
	public void click_Submit(){
		Submit.click();
	}
	
	public String get_CustomerName()
	{
		return customerName.getText();
	}
	
	public String get_accNumber()
	{
		return accNumber.getText();
	}
	
	
	
	public String get_noMerchadiseTransactMsg()
	{
		return noMerchadiseTransactMsg.getText();
	}
	
	public void click_cancel_Btn(){
		cancel_Btn.click();
	}
	
	
}
