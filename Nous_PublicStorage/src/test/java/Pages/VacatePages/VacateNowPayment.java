package Pages.VacatePages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VacateNowPayment {

	WebDriver driver;

	public VacateNowPayment(WebDriver driver){
		PageFactory.initElements(driver, this);
	}


	@FindBy(xpath="//div[@id='payment-table']//section[@class='unit-subtotal alt row']/div[contains(text(),'Space Subtotal')]/..")
	private WebElement space_subtotalbalance;


	//changed by basava
	@FindBy(xpath="//div[contains(text(),'Miscellaneous Charges Subtotal')]/following-sibling::div/span")
	private WebElement miscellaneous_subtotalbalance;

	//changed by basava
	@FindBy(xpath="//div[span[contains(text(),'Total Due')]]/following-sibling::div[@class='js-total second col']")
	private WebElement totaldue;

	@FindBy(xpath="//div[@class='payment-row__detail']//span[text()='Select']")
	private WebElement paymentMethoddrpdown;


	@FindBy(xpath="//a[text()='Apply']")
	private WebElement Apply_button;


	@FindBy(xpath="//a[@id='lnkSubmitPayment']")
	private WebElement Submit_Button;

	@FindBy(xpath="//ul[@class='k-list k-reset ps-container ps-active-y']//li[text()='Cash']")
	private WebElement select_Cash;

	@FindBy(xpath="//input[@id='cashAmount']")
	private WebElement cashAmount;


	//savmnvmvs
	@FindBy(xpath="//div[@id='payment-table']//section[@class='monthly-rent row']")
	private WebElement monthly_rent;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='tax alt row']")
	private WebElement rent_tax;


	@FindBy(xpath="//div[@id='payment-table']//section[@class='admin-fee alt row']/div[text()='Pre-paid Rent (credit)']/..")
	private WebElement prepaid_rent;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='admin-fee alt row']/div[text()='Pre-paid Rent Tax (credit)']/..")
	private WebElement prepaid_rent_tax;

	@FindBy(xpath="//div[@id='refund-table']//section[@class='refund-rent row']/div[text()='Pre-paid Rent Refund']/..")
	private WebElement prepaid_rent_refund;

	@FindBy(xpath="//div[@id='refund-table']//section[@class='refund-rent row']/div[text()='Pre-paid Rent Tax Refund']/..")
	private WebElement prepaid_rent_tax_refund;

	//add by basava
	@FindBy(xpath="//div[text()='Refund Total']/following-sibling::div/span")
	private WebElement refund_total;

	@FindBy(xpath="//span[text()='Payment']")
	private WebElement PaymentPageTttle;
	
	
	@FindBy(xpath="//div[contains(text(),' Is enough cash on hand to provide change for this transaction?')]/div/span[text()='Yes']/preceding-sibling::input")
	private WebElement clk_IsThisEnoughonHandYesRadioBtn;
	
	@FindBy(xpath="//a[@id='lnkConfirmWithCustomer']")
	private WebElement clk_ConfirmWithCust;



	public String getSpaceSubTotal(){
		return space_subtotalbalance.getText();
	}

	public String getMiscellaneousSubTotal(){
		return miscellaneous_subtotalbalance.getText();
	}

	public String getTotalDue(){
		return totaldue.getText();
	}



	public void click_PaymentMethoddrodown(){
		paymentMethoddrpdown.click();
	}


	public void select_Cash(){
		select_Cash.click();
	}


	public void enter_CashAmount(String amt){
		
		try {
			clickCashAmountField();
			Thread.sleep(2000);
			cashAmount.sendKeys(amt);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void clickCashAmountField(){
		
		cashAmount.click();
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_A);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_A);
	        robot.keyPress(KeyEvent.VK_DELETE);
	        robot.keyRelease(KeyEvent.VK_DELETE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}


	public void click_ApplyButton(){
		Apply_button.click();
	}

	public void click_SubmitButton(){
		Submit_Button.click();
	}

	public String get_MonthlyRent(){
		return monthly_rent.getText();
	}

	public String get_RentTax(){
		return rent_tax.getText();
	}

	public String get_PrepaidRent(){
		return prepaid_rent.getText();
	}

	public String get_PrepaidRentTax(){
		return prepaid_rent_tax.getText();
	}

	public String get_PrepaidRentRefund(){
		return prepaid_rent_refund.getText();
	}

	public String get_PrepaidRentTaxRefund(){
		return prepaid_rent_tax_refund.getText();
	}

	public String get_RefundTotal(){
		return refund_total.getText();
	}


	public boolean isPaymentPageDisplayed(){

		return PaymentPageTttle.isDisplayed();
	}
	
	public void clk_ConfirmWithCustomerBtn(){
		
		clk_ConfirmWithCust.click();
	}
	
	
	public void clk_IsThisEnoughonHandToChangeForThisTrnsYesRadioBtn(){
		
		clk_IsThisEnoughonHandYesRadioBtn.click();
	}

	public void select_Cash(String PaymentMethod,WebDriver driver) throws InterruptedException{
		click_PaymentMethoddrodown();
		Thread.sleep(2000);
		List<WebElement> AllPaymentMethod = driver.findElements(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']//li[@class='k-item']"));
		
		Thread.sleep(2000);
		for(WebElement Allvalues:AllPaymentMethod){
			String value=Allvalues.getText();
			Thread.sleep(2000);
			if(value.contains(PaymentMethod)){
				Allvalues.click();
				break;
			}
		}
	}

}
