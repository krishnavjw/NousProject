package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TransactionReversedPage {

	
	@FindBy(xpath="//div[@id='transaction-complete-modal']//div[@id='employeeNumber-wrapper']/input[@id='employeeNumber']")
	private WebElement employeeId;
	
	@FindBy(xpath="//div[@class='command-row clearfix-container']/a[contains(text(),'Ok')]")
	private WebElement okBtn;
	
	@FindBy(xpath="//span[text()='I have given refunded cash to the customer']/preceding-sibling::span")
	private WebElement chk_RefundCashToCustCheckBox;
	
	
	public TransactionReversedPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);

	}
	
	
	
	public void enter_EmployeeId(String text){
		employeeId.sendKeys(text);
	}
	
	public void click_OkBtn(){
		okBtn.click();
	}
	
	public void chk_RefundCashToCustCheckBox(){
		chk_RefundCashToCustCheckBox.click();
	}
	
}
