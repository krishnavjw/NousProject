package Pages.CustDashboardPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaymentDueDetailsPage 
{
	@FindBy(xpath="//span[text()='Payment Due Details']")
	private WebElement paymentDueTitle;

	@FindBy(xpath="//div[@class='space__identifier']/div[@class='unit']")
	private WebElement spaceId;

	@FindBy(xpath="//div[@class='space__identifier']//a[@class='location-details']")
	private WebElement locationNumber;

	@FindBy(xpath="//div[@class='space__payment-details']//div[contains(text(),' Monthly Rent')]/following-sibling::div[contains(@class,'space__payment-details__row__detail--due-now')]")
	private WebElement monthlyRent_DueNow;

	@FindBy(xpath="//div[@class='space__payment-details']//div[contains(text(),'Insurance')]")
	private WebElement insurance_field;

	@FindBy(xpath="//div[@class='space__payment-details']//div[contains(text(),'Insurance')]/following-sibling::div[contains(@class,'space__payment-details__row__detail--due-now')]")
	private WebElement insurance_DueNow;

	@FindBy(xpath="//div[@id='paymentDetailsDialog']//div[contains(text(),'Total Due Now:')]/following-sibling::div[contains(@class,'total-due-now__details__detail--total')]")
	private WebElement Total_DueNow;

	@FindBy(xpath="//div[@ class='k-window-content k-content']//a[contains(text(),'Make Payment')]")
	private WebElement makePayment_button;

	// *********** Sandeep G //

	@FindBy(xpath="//div[@id='paymentDetailsDialog']//div[@class='unit']")
	private List<WebElement> multipleSpaces;

	@FindBy(xpath="//div[@class='command-row clearfix-container']//a[contains(text(),'Cancel')]")
	private WebElement clk_CancelBtn;

	@FindBy(xpath="//div[contains(text(),'Damages Fee')]/following-sibling::div[@class='space__payment-details__row__detail space__payment-details__row__detail--due-now']")
	private WebElement get_DamagesFeeAmt;

	@FindBy(xpath="//div[@class='space__payment-details']//div[contains(text(),'Damages')]")
	private WebElement misc_field;

	@FindBy(xpath="//div[@class='space__payment-details']//div[contains(text(),'Damages')]/following-sibling::div[contains(@class,'space__payment-details__row__detail--due-now')]")
	private WebElement misc_DueNow;

	@FindBy(xpath="//div[@class='space__payment-details']//div[contains(text(),'Abandoned')]")
	private WebElement miscAbandoned_field;

	@FindBy(xpath="//div[@class='space__payment-details']//div[contains(text(),'Abandoned')]/following-sibling::div[contains(@class,'space__payment-details__row__detail--due-now')]")
	private WebElement miscAbandoned_DueNow;

	@FindBy(xpath="//div[@class='space__payment-details']//div[contains(text(),' Monthly Rent')]/following-sibling::div[contains(@class,'space__payment-details__row__detail--due-next')]")
	private WebElement monthlyRent_NextDueNow;


	@FindBy(xpath="//div[@class='space__payment-details']//div[contains(text(),'Cleaning Fee')]")
	private WebElement cleaingFee_field;


	@FindBy(xpath="//div[@class='space__payment-details']//div[contains(text(),'Cleaning Fee')]/following-sibling::div[contains(@class,'space__payment-details__row__detail--due-now')]")
	private WebElement cleaingFee_DueNow;




	public PaymentDueDetailsPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

	public boolean verifyPaymentDueTitle()
	{
		return paymentDueTitle.isDisplayed();
	}

	public String getSpaceid()
	{
		return spaceId.getText();
	}

	public String getLocationNumber()
	{
		return locationNumber.getText();
	}

	public String getMonthlyrent()
	{
		return monthlyRent_DueNow.getText();
	}

	public boolean verifyInsurance()
	{
		return insurance_field.isDisplayed();
	}

	public String getInsurance_DueNow()
	{
		return insurance_DueNow.getText();	
	}

	public String getTotal_DueNow()
	{
		return Total_DueNow.getText();	
	}

	public void click_MakePayment()
	{
		makePayment_button.click();

	}

	public List<WebElement> getMultipleSpaces(){
		return multipleSpaces;
	}
	public void clk_CancelBtn(){

		clk_CancelBtn.click();
	}
	public String get_DamagesFeeAmt(){

		return get_DamagesFeeAmt.getText();
	}

	public boolean verifyCleaningFee()
	{

		if(cleaingFee_field!=null){
			return true;
		}
		else{
			return false;
		}

	}

	public String getCleaningFee_DueNow()
	{
		return cleaingFee_DueNow.getText();	
	}


	public boolean verifyMiscCharges()
	{
		if(misc_field!=null) {
			return true;
		}
		else
		{
			return false;
		}
	}

	public String getMiscCharge_DueNow()
	{
		return misc_DueNow.getText();	

	}

	public boolean verifyMiscAbandonedCharges()
	{

		if(miscAbandoned_field!=null) {
			return true;
		}
		else
		{
			return false;
		}

	}

	public String getMiscAbandonedCharge_DueNow()
	{
		return miscAbandoned_DueNow.getText();	
	}

	public String getNextMonthlyrent()
	{
		return monthlyRent_NextDueNow.getText().trim();
	}





}
