package Pages.Walkin_Reservation_Lease;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Leasing_PaymentMethodsPage 
{
	WebDriver driver;
	
	@FindBy(xpath="//div[@id='payment-methods']//span[@class='k-widget k-dropdown k-header payment-method-dropdown']")
	private WebElement paymentSelectdropdwn;

	@FindBy(id="cashAmount")
	private WebElement cash_field;

	@FindBy(id="checkAmount")
	private WebElement check_field;
	
	@FindBy(linkText="Apply")
	private WebElement apply_btn;

	@FindBy(xpath="//div[@id='lease-payment']//div//button[contains(text(),'Submit')]")
	private WebElement submit_btn;

	@FindBy(linkText="Enter Manually")
	private WebElement enterManual_btn;

	@FindBy(id="routingNumber")
	private WebElement routingNumber;

	@FindBy(id="accountNumber")
	private WebElement accountNumber;

	@FindBy(id="checkNumber")
	private WebElement checkNumber;

	@FindBy(id="checkAmount")
	private WebElement checkAmount;

	@FindBy(xpath="//div[@class='payment-method-form__field payment-method-form__field--autopay']//input[@class='checkAutoPay']/following-sibling::span[text()='Apply to AutoPay']/preceding-sibling::span")
	private WebElement autopaycheckbox;

	@FindBy(xpath="//div[@id='lease-payment']//div//button[contains(text(),'Collect Signature(s)')]")
	private WebElement Collect_Signature;

	@FindBy(xpath="//canvas")
	private WebElement canvas;

	@FindBy(xpath="//button[text()='Accept']")
	private WebElement accept;

	@FindBy(xpath="//a[text()='Approve']")
	private WebElement Approve_Btn;

	@FindBy(id = "cashAmount")
	private WebElement enter_CashAmount;

	@FindBy(xpath = "//button[text()='Confirm With Customer']")
	private WebElement clk_ConfirmWthCustBtn;

	@FindBy(xpath = "//div[text()='Monthly Rent']/following-sibling::div/span[@class='js-unit-rent']")
	private WebElement get_MonthlyRentDueNowAmt;

	@FindBy(xpath = "//div[contains(text(),' Promotion ')]/following-sibling::div/span[@class='js-unit-discount']")
	private WebElement get_PromotionDueNowAmt;

	@FindBy(xpath = "//div[contains(text(),' Insurance ')]/following-sibling::div/span[@class='js-unit-insurance js-unit-product-option']")
	private WebElement get_InsuranceDueNowAmt;

	@FindBy(xpath = "//div[text()='One-Time Administrative Fee']/following-sibling::div/span[@class='js-unit-adminfee js-unit-product-option']")
	private WebElement get_AdministrativeDueNowAmt;

	@FindBy(xpath = "//div[div[text()='Pay Through']]/following-sibling::div/span[@class='js-unit-paythrough']")
	private WebElement get_PaythroughDueNowAmt;

	@FindBy(xpath = "//div[div[span[text()='Merchandise']]]/following-sibling::div/span[@class='js-merchandise-amount']")
	private WebElement get_MerchandiseAmt;

	@FindBy(xpath = "//div[contains(text(),'Sales Tax')]/following-sibling::div/span[@class='js-tax']")
	private WebElement get_SalesTaxAmt;

	@FindBy(xpath = "//div[text()='Total Remaining']/following-sibling::div/div[@class='js-total-balance']")
	private WebElement get_TotalRemaingBalanceAmt;

	@FindBy(xpath="//a[@id='notes-add']")
	private WebElement addNotesLink;

	@FindBy(xpath="//section[@class='row orderTotal']//span[@class='js-total']")
	private WebElement total_Amount;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='prorate row']//span[@class='k-input']")
	private WebElement payThroughDropdown;
	
	@FindBy(xpath="//div[@class='command-row']/button[contains(text(),'Confirm With Customer')]")
	private WebElement confirmWithCustomer_Btn;
	
	@FindBy(xpath = "//div[text()='Pay Through']/following-sibling::div[@class='prepay-dropdown-container']")
	private WebElement clk_PayThroughdropdwn;
	
	@FindBy(id="cancelLeaseButton")
	private WebElement cancel_lease;

	@FindBy(xpath="//div[@id='customerAuthPanels']//div[@id='signature-panel-area']//div//a[@class='approve-button psbutton-normal']")
	private WebElement signature_ApproveBtn;
	
	@FindBy(id="creditCardAmount")
	private WebElement enter_CCAmount;
	
	@FindBy(xpath="//input[@class='creditCardAutoPay']/following-sibling::span[@class='button']")
	private WebElement autopayCCCheckbox;

	
	public Leasing_PaymentMethodsPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}

	public void clickSubmitbtn()
	{
		submit_btn.click();
	}

	public void clickPaymenetList()
	{
		paymentSelectdropdwn.click();
	}
	
	
	public boolean verifyAutopaycheckbox() {
		return autopaycheckbox.isDisplayed();
	}

	public void enterAmount(String amount)
	{
		cash_field.sendKeys(amount);
	}

	public void enterCheckAmount(String amount){
		check_field.sendKeys(amount);
	}
	
	public void clearCheckAmount(){
		check_field.clear();
	}
	public void clickApply_btn()
	{
		apply_btn.click();
	}

	public void clickSubmit_btn()
	{
		submit_btn.click();
	}

	public void clickapplybtn()
	{
		apply_btn.click();
	}
	public void clickmanualentry()
	{
		enterManual_btn.click();
	}

	public void click_SignatureApproveBtn() {
		signature_ApproveBtn.click();
	}
	
	public void Enter_routingNumber(String value)
	{
		routingNumber.sendKeys(value);
	}
	public void Enter_accountNumber(String value)
	{
		accountNumber.sendKeys(value);
	}
	public void Enter_checkNumber(String value)
	{
		checkNumber.sendKeys(value);
	}
	public void Enter_checkAmount(String value)
	{
		checkAmount.clear();
		checkAmount.sendKeys(value);
	}
	
	public void Select_autopaycheckbox() {
		autopaycheckbox.click();
	}
	public void click_CollectSignature()
	{
		Collect_Signature.click();
	}


	public void clickAccept_Btn() {
		accept.click();
	}

	public void clickApprove_Btn() {
		Approve_Btn.click();
	}

	public void selectPaymentMethod(String expVal,WebDriver driver){
		paymentSelectdropdwn.click();
		List<WebElement> ListWbEle=driver.findElements(By.xpath("//div[@class='k-animation-container']/div[@class='k-list-container k-popup k-group k-reset k-state-border-down']/ul[@class='k-list k-reset ps-container ps-active-y']//li[@class='k-item']"));
		for (WebElement ele : ListWbEle) {
			String actualWbEleText=ele.getText();
			if(actualWbEleText.equalsIgnoreCase(expVal)) 
				ele.click();

		}

	}
	
	public void clk_PayThroughdropdwn() {
		clk_PayThroughdropdwn.click();
	}

	/*public void selectPaymentMethod(String expVal, WebDriver driver) {
		paymentSelectdropdwn.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<WebElement> ListWbEle = driver
				.findElements(By.xpath("//div[@class='k-animation-container']/div[@data-role='popup']/ul/li"));
		for (WebElement ele : ListWbEle) {
			String actualWbEleText = ele.getText();
			if (actualWbEleText.equalsIgnoreCase(expVal))
				ele.click();

		}

	}*/

	public void enter_CashAmount(String cashAmt) {

		enter_CashAmount.clear();
		enter_CashAmount.sendKeys(cashAmt);
	}

	public void clk_ConfirmWthCustBtn() {

		clk_ConfirmWthCustBtn.click();
	}

	public String get_MonthlyRentDueNowAmt() {

		return get_MonthlyRentDueNowAmt.getText().trim();

	}

	public double getDoubleAmount(String inputValue) {

		String addValue = "";

		char[] c = inputValue.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ',' || c[i] == '$') {
				c[i] = ' ';
			} else if (Character.getNumericValue(c[i]) >= 0 && Character.getNumericValue(c[i]) <= 9 || c[i] == '.'
					|| c[i] == '-') {
				addValue = addValue + c[i];
			} else {
				System.out.println("Not able to handle");
			}
		}
		return Double.parseDouble(addValue);

	}

	public String get_PromotionDueNowAmt() {

		return get_PromotionDueNowAmt.getText().trim();
	}

	public String get_InsuranceDueNowAmt() {
		return get_InsuranceDueNowAmt.getText().trim();
	}

	public String get_AdministrativeDueNowAmt() {

		return get_AdministrativeDueNowAmt.getText().trim();
	}

	public String get_PaythroughDueNowAmt() {

		return get_PaythroughDueNowAmt.getText().trim();
	}

	public String get_MerchandiseAmt() {
		return get_MerchandiseAmt.getText().trim();
	}

	public String get_SalesTaxAmt() {
		return get_SalesTaxAmt.getText().trim();
	}

	public String get_TotalRemaingBalanceAmt() {

		return get_TotalRemaingBalanceAmt.getText().trim();
	}

	public String get_Amount() {
		return total_Amount.getText();
	}
	public void selectPaymentThrough() throws InterruptedException {

		payThroughDropdown.click();
		Thread.sleep(3000);
		List<WebElement> ListWbEle = driver.findElements(By.xpath("//div[@class='k-animation-container']//ul//li"));
		int Size = ListWbEle.size();
		for (int i = 0; i < Size; i++) {
			if (i == 1) {
				ListWbEle.get(i).click();
				break;
			}
		}

	}
	
	public void clickOnConfirmWithCustomer_Btn(){
		confirmWithCustomer_Btn.click();
	}
	
	
	public void clk_CancelLease(){
		cancel_lease.click();
	}

	public void enterCCAmount(String ccAmt){


		enter_CCAmount.sendKeys(ccAmt);
	}

	public void clickCCField(){


		enter_CCAmount.click();
	}
	
	public void clickCCAutopayCheckBox()
	{
		autopayCCCheckbox.click();
	}
}
