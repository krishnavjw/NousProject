package Pages.CustDashboardPages;

import java.sql.Driver;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Keys;

public class CheckoutPage {

	private WebDriver driver;
	
	@FindBy(xpath="//div[@id='MerchandiseCheckOut']//h3[text()='Checkout']")
	public WebElement pagetitle;
	
	@FindBy(xpath="//div[@id='merchandiseCheckOutItems']//table//tbody/tr[1]/td[2]")
	public WebElement Product1_Price;
	
	@FindBy(xpath="//div[@id='merchandiseCheckOutItems']//table//tbody/tr[2]/td[2]")
	public WebElement Product2_Price;
	
	@FindBy(xpath="//div[@id='merchandiseCheckOutItems']//table//tbody/tr[1]/td[3]")
	public WebElement Product1_Quantity;
	
	@FindBy(xpath="//div[@id='merchandiseCheckOutItems']//table//tbody/tr[2]/td[3]")
	public WebElement Product2_Quantity;
	
	
	@FindBy(xpath="//div[@id='merchandiseCheckOutItems']//table//tbody/tr[1]/td[4]")
	public WebElement Product1_Amt;
	
	@FindBy(xpath="//div[@id='merchandiseCheckOutItems']//table//tbody/tr[2]/td[4]")
	public WebElement Product2_Amt;
	
	@FindBy(xpath="//div[@id='merchandise-payment']//div[contains(text(),' Promotions')]")
	public WebElement promotion_Section;
	
	@FindBy(xpath="//button[contains(text(),'Confirm With Customer')]")
	public WebElement ConfWithCust;
	
	@FindBy(xpath="//div[@id='paymentMethods']//span[text()='select']")
	public WebElement payDropdown;
	
	@FindBy(xpath="//ul/li[text()='Cash']")
	public WebElement cash;
	
	@FindBy(linkText="Edit Cart")
	public WebElement Editcart;
	
	@FindBy(xpath="//button[text()='Submit']")
	public WebElement Submit;
	
	@FindBy(id="cashAmount")
	public WebElement Amount;
	
	@FindBy(linkText="Apply")
	public WebElement Apply;
	
	@FindBy(xpath="//div[contains(@class,'k-list-container')]/ul/li[contains(text(),'Cash')]")
	public WebElement option_Cash;
	
	@FindBy(xpath="//div[@id='payment-methods']//div[@class='js-total-balance']")
	public WebElement totalPrice;
	
	@FindBy(linkText="Back To Dashboard")
	public WebElement backToDashboard;
	
	@FindBy(xpath="//div[@class='payment-row__detail']//span[contains(@class,'k-dropdown-wrap')]/span[text()='Select']")
	public WebElement payment_Dropdown;
	
	@FindBy(xpath="//ul/li[text()='Money Order']")
	public WebElement MO;
	
	@FindBy(xpath="//div[contains(@class,'payment-methods__method payment-method-form payment-method-form--check')]//a[text()='Enter Manually']")
	private WebElement enterManual_btn;
	
	@FindBy(id="routingNumber")
	private WebElement routingNumber;
	
	@FindBy(id="accountNumber")
	private WebElement checkAccNum;

	@FindBy(id="checkNumber")
	private WebElement checkNumber;

	@FindBy(id="checkAmount")
	private WebElement checkAmount;
	
	public CheckoutPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}
	
	public void click_BackToDashboard(){
		backToDashboard.click();
	}
	
	public void enterAmountFromTotalPrice(){
		String price = totalPrice.getText();
		Amount.clear();
		Amount.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		Amount.sendKeys(price);
		
	}
	
	public void select_PaymentCash() throws InterruptedException{
		payment_Dropdown.click();
		Thread.sleep(3000);
		option_Cash.click();
	}
	
	public boolean verify_pagetitle()
	{
		return pagetitle.isDisplayed();
	}
	
	public int  get_Countofproducts(){
		List<WebElement> count=driver.findElements(By.xpath("//div[@id='merchandiseCheckOutItems']//table//tbody/tr"));
		System.out.println(count.size());
		return count.size();
	
	}
	
	public String get_Product1_Price(){
		return Product1_Price.getText();
	}
	
	public String get_Product2_Price(){
		return Product2_Price.getText();
	}
	
	public String get_Product1_Quantity(){
		return Product1_Quantity.getText();
	}
	
	
	public String get_Product2_Quantity(){
		return Product2_Quantity.getText();
	}
	
	public String get_Product1_Amt(){
		return Product1_Amt.getText();
	}
	
	
	public String get_Product2_Amt(){
		return Product2_Amt.getText();
	}
	
	public boolean verify_promotion_Section()
	{
		return promotion_Section.isDisplayed();
	}
	
	public void click_ConfWithCust() {
		ConfWithCust.click();
	}
	
	public void click_payDropdown() {
		payDropdown.click();
	}
	
	public void sel_Cash_fromDropdown() {
		cash.click();
	}
	
	public boolean verify_Editcart() {
		return Editcart.isDisplayed();
	}
	
	public boolean verify_Submit() {
		return Submit.isDisplayed();
	}
	
	
	public void click_Editcart() {
		Editcart.click();
	}
	
	public void click_Submit() {
		Submit.click();
	}
	
	public void txt_Amount() {
		String total=driver.findElement(By.xpath("//span[@class='js-merchandise-total']")).getText();
		Amount.clear();
		Amount.sendKeys(total);
	}
	
	public void click_Apply() {
		Apply.click();
	}
	
	public void verify_payDropdown() {
		payDropdown.isDisplayed();
	}
	
	public void sel_MO_fromDropdown() {
		MO.click();
	}
	
	public void clickmanualentry()
	{
		enterManual_btn.click();
	}
	
	
	public void Enter_routingNumber(String value)
	{
		routingNumber.sendKeys(value);
	}
	
	
	public void Enter_CheckAccountNumber(String value)
	{
		checkAccNum.sendKeys(value);
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
	
	
	public void enter_checkAmount( ){
		String total=driver.findElement(By.xpath("//span[@class='js-merchandise-total']")).getText();
		checkAmount.clear();
		checkAmount.sendKeys(total); 
	}
	
	
	@FindBy(xpath="//span[@class='js-merchandise-total']")
	public WebElement totalAmount;
	
	@FindBy(xpath="//span[@class='js-merchandise-subtotal']")
	public WebElement subtotalAmount;
	
	public String get_totalAmount(){
		return totalAmount.getText();
	}
	
	public String get_subtotalAmount(){
		return subtotalAmount.getText();
	}
	
	
	@FindBy(xpath="//span[@class='js-tax']")
	public WebElement salestax;
	
	public String get_salestax(){
		return salestax.getText();
	}
	
	@FindBy(xpath="//a[@id='addTaxExemptLink']/span")
	public WebElement addTaxExempt_lnk;
	
	public void click_addTaxExempt_lnk(){
		addTaxExempt_lnk.click();
	}
	
	
	
	@FindBy(xpath="//a[text()='Upload']")
	public WebElement upload_btn;
	
	
	
	public void click_upload_btn(){
		upload_btn.click();
	}
	
	
	
	@FindBy(xpath="//input[@id='TaxExemptModel_EncryptedItem_EncryptedIdDisplay']")
	public WebElement TaxExemptNumber;
	
	
	public void enter_TaxExemptNumber(String TaxNo){
		TaxExemptNumber.sendKeys(TaxNo);
	}
	
	
	@FindBy(xpath="//div[@id='merchandiseTaxExempt']//label//span[text()='No Expiration Date']")
	public WebElement NoExpirationDate_Chk;
	
	public void click_NoExpirationDate_Chk(){
		NoExpirationDate_Chk.click();
	}
	
	@FindBy(xpath="//div[contains(text(),'Is enough cash on hand')]")
	public WebElement enoughCashOnHand;
	
	public boolean verify_EnoughCashOnhand_Msg(){
		return enoughCashOnHand.isDisplayed();
	}
	
	

	@FindBy(xpath=" //div[@id='hasSufficientCashRadioButtons']//span[text()='Yes']/preceding-sibling::input")
	public WebElement Yes_RadioBtn;
	
	@FindBy(xpath="(//span[text()='No']/preceding-sibling::input[@class='sufficientCash'])[2]")
	public WebElement No_RadioBtn;
	
	
	public void click_Yes_RadioBtn() {
		Yes_RadioBtn.click();
	}
	
	public void click_No_RadioBtn() {
	     No_RadioBtn.click();
	}
	
	
	@FindBy(linkText="Approve")
	public WebElement Approve;
	
	public void click_Approve(){
		Approve.click();
	}
	
	@FindBy(xpath="//div[contains(text(),'Change Due')]/following-sibling::div/div[@class='js-total-balance']")
	public WebElement Changedue;
	
	public String get_changedue(){
		return Changedue.getText();
	}
	
	@FindBy(xpath="//button[contains(text(),'Collect Signature(s)')]")
	private WebElement CollectSignature_btn;
	
	public void click_CollectSignature()
	{
		CollectSignature_btn.click();
	}
	
	
	public void entermoreAmount(){
		String total=driver.findElement(By.xpath("//span[@class='js-merchandise-total']")).getText().replace("$", "");
		double amount=Double.parseDouble(total)+10;
		String Amount="$"+amount;
		checkAmount.clear();
		checkAmount.sendKeys(Amount);

		}
	
	@FindBy(xpath="//div[@class='payment-methods__balance']//div[contains(text(),'Cannot Accept Overpayment')]")
	public WebElement OverlapMessage;
	
	public String  getOverPaymentErrorMessage() {
		return OverlapMessage.getText();
	}
	
	@FindBy(xpath="//a[@class='remove-button']")
	public WebElement Remove;
	
	public void click_RemoveBtn() {
	     Remove.click();
	}
	
	
}
