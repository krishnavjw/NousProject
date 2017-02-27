package Pages.MakePaymentPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class PaymentsPage {

	WebDriver driver;

	@FindBy(xpath="//div[@id='lease-payment']/div[@class='container-heading']/h3[contains(text(),'Payment')]")
	private WebElement paymentTitle;

	@FindBy(xpath="//section[@class='row orderTotal']/div/span[@class='js-total']")
	private WebElement totalDueInPaymentSection;

	@FindBy(xpath="//div[@class='command-row']/button[contains(text(),'Confirm With Customer')]")
	private WebElement confirmWithCustomer_Btn;

	@FindBy(xpath="//div[@id='payment-methods']//span[@class='k-widget k-dropdown k-header payment-method-dropdown']")
	private WebElement paymentSelectdropdwn;

	@FindBy(linkText="Cancel")
	private WebElement cancel_btn;

	@FindBy(linkText="Back to Dashboard")
	private WebElement BktoDash_btn;

	@FindBy(xpath="//div[@class='command-row']//button[text()='Submit']")
	private WebElement submit_btn;

	@FindBy(linkText="Apply")
	private WebElement apply_btn;

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

	@FindBy(xpath="//button[text()='Collect Signature(s)']")
	private WebElement Collect_Signature;

	@FindBy(xpath="//canvas")
	private WebElement canvas;

	@FindBy(xpath="//button[text()='Accept']")
	private WebElement accept;

	@FindBy(xpath="//a[text()='Approve']")
	private WebElement Approve_Btn;

	@FindBy(xpath="//div[@id='payment-methods']//span[@class='k-widget k-dropdown k-header payment-method-dropdown']")
	private WebElement paymentDropdown;

	@FindBy(xpath = "//div[@id='payment-table']//section[@class='payment-merchandise row']//span[text()='Add/Edit Cart']")
	private WebElement Add_EditMerchandise_lnk;

	@FindBy(xpath = "//div[@id='payment-table']/section/div[@class='first col']/following-sibling::div[@class='second col']//span")
	private WebElement totalDueNow_Amount;

	@FindBy(xpath = "//div[@id='payment-table']//section[@class='prorate row']//span[@class='k-input']")
	private WebElement payThroughDropdown;

	@FindBy(id="cashAmount")
	private WebElement enter_CashAmount;

	@FindBy(xpath = "//div[@id='payment-table']//section[@class='monthly-rent row']/div[text()='Monthly Rent']/following-sibling::div[@class='third col']/span")
	private WebElement monthlyRentAmount;

	@FindBy(xpath = "//div[@id='payment-table']//section[@class='insurance row']//div[contains(text(),'Insurance')]/following-sibling::div[@class='third col']/span")
	private WebElement insuranceAmount;

	@FindBy(xpath="//div[text()='Total Remaining']/following-sibling::div/div[@class='js-total-balance']")
	private WebElement get_TotalRemainingAmt;

	@FindBy(xpath="//div[text()='Total Remaining']/following-sibling::div/div[@class='js-total-balance']")
	private WebElement txt_TtlBal;


	@FindBy(id="employeeNumber")
	private WebElement TxtBx_AmtOwing_EmpNum;

	@FindBy(xpath="//a[contains(text(),'Ok')]")
	private WebElement Btn_AmtOwing_OK;

	@FindBy(xpath="//div[@id='payment-form']//a[@class='location-details']")
	private WebElement location;

	@FindBy(xpath="//div[@id='payment-form']//span[@class='js-space-identifier']")
	private WebElement spacedetails;

	@FindBy(xpath="//div[@id='payment-form']//span[@class='displayRentDueDate js-rent-due-date']")
	private WebElement nextDueDate;

	@FindBy(xpath="//div[@id='payment-form']//span[@class='js-unit-rent']")
	private WebElement totalDuenow;

	@FindBy(xpath="//div[@id='payment-form']//span[@class='js-next-unit-rent']")
	private WebElement nextDueamt;

	@FindBy(xpath="//div[@id='payment-form']//span[@class='js-unit-subtotal']")
	private WebElement totalDuenow_sub;

	@FindBy(xpath="//div[@id='payment-form']//span[@class='js-next-unit-subtotal']")
	private WebElement nextDueamt_sub;

	@FindBy(xpath="(//div[@id='payment-table']//section[@class='prorate row']//span[@class='k-input'])[1]")
	private WebElement paymentDropdown1;

	@FindBy(xpath="(//div[@id='payment-table']//section[@class='prorate row']//span[@class='k-input'])[2]")
	private WebElement paymentDropdown2;

	@FindBy(xpath="(//div[@id='payment-table']//section[@class='prorate row']//span[@class='k-input'])[3]")
	private WebElement paymentDropdown3;

	@FindBy(xpath="//a[@class='merchandise-add-edit-link']//span[contains(text(),'Add/Edit Cart')]")
	private WebElement addEditCart;

	@FindBy(xpath="//div[@id='paymentMethods']//div[@id='hasSufficientCashRadioButtons']/input[@value='true']")
	private WebElement enoughCash_Yes;

	@FindBy(xpath="//div[@id='paymentMethods']//div[@id='hasSufficientCashRadioButtons']/input[@value='false']")
	private WebElement enoughCash_No;

	@FindBy(xpath="//section[@class='payment-merchandise-promotion row payment-dropdown-section']//span[contains(text(),'Select Promotion')]")
	private WebElement selectPromotion;

	@FindBy(xpath="//section[@class='payment-merchandise-promotion row payment-dropdown-section']//button[contains(text(),'Apply')]")
	private WebElement applyButtonMer;


	@FindBy(xpath="//div[@id='hasSufficientCashRadioButtons']/input[1]")
	private WebElement yesRadioBtn;

	@FindBy(xpath="//div[@id='hasSufficientCashRadioButtons']/input[2]")
	private WebElement noRadioBtn;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='monthly-rent row']/div[@class='second col']/span")
	private WebElement monthly_rent;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='tax alt row']/div[@class='second col']/span")
	private WebElement rent_tax;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='fee row']/div[@class='second col']/span")
	private WebElement misc_charge;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='row orderTotal']/div[@class='second col']/span")
	private WebElement totalDue;


	@FindBy(xpath="//div[@class='payment-methods__balance']/div[text()='Total Remaining']/following-sibling::div[@class='payment-row__now']/div")
	private WebElement get_totalRemaningBalAmt;




	@FindBy(xpath="//div[@id='paymentMethods']//div[@id='cashAmount-wrapper']//input[@id='cashAmount']")
	private WebElement amount_cash;

	@FindBy(xpath="//div[@id='paymentMethods']//div[@class='payment-methods__cashonhand']/div")
	private WebElement extraCash_Indicator;

	@FindBy(xpath="//div[@class='notification alert k-notification-wrap']")
	private WebElement notification;


	@FindBy(xpath="//div[@id='misc-charge-view']//span[@class='k-widget k-dropdown k-header unit-options dropdown-options']")
	private WebElement paymentSelectdropdwn_mischarges;

	@FindBy(xpath="//div[@id='misc-charge-view']//span[@class='k-widget k-dropdown k-header charge-options dropdown-options']")
	private WebElement mischarges_dropdown;

	//=====================rudra

	@FindBy(xpath="//div[@id='payment-methods']//div[text()='Change Due']/following-sibling::div/div[@class='js-total-balance']")
	private WebElement changeDue;


	@FindBy(xpath="//div[@id='payment-table']//section[@class='insurance row']")
	private WebElement insurance;

	@FindBy(xpath="//div[@id='payment-form']//section[@class='row orderTotal']//span[@class='js-total']")
	private WebElement total_amount;
	
	@FindBy(xpath="//div[@class='space__payment-details']//div[contains(text(),'Insurance')]")
	private WebElement insurance_field;

	@FindBy(xpath="//div[@id='paymentMethods']//div[text()='Total Remaining']/following-sibling::div/div[@class='js-total-balance']")
	private WebElement totalRemaining;


	@FindBy(xpath="(//div[@id='payment-table']//section[@class='fee row']/div[@class='second col']/span)[1]")
	private WebElement miscAbandoned_charge;

	@FindBy(xpath="//div[contains(@class,'payment-method-form--check')]//a[text()='Enter Manually']")
	private WebElement MOenterManual_btn;
	
	@FindBy(xpath="//div[@id='payment-table']//section[@class='monthly-rent row']//span[@class='js-next-unit-rent']")
	private WebElement monthlyrent_Next;
	
	@FindBy(xpath="//div[@id='payment-form']//span[@class='js-merchandise-amount']")
	private WebElement merchandise_rentAmt;
	
	@FindBy(xpath="//div[@id='payment-form']//span[@class='js-tax']")
	private WebElement salesTax_Amt;
	
	@FindBy(xpath="//div[@id='payment-form']//span[@class='js-merchandise-subtotal']")
	private WebElement MerchandiseSubtotal_Amt;
	

	@FindBy(xpath="//div[@id='payment-table']//section[@class='fee row']//div[@class='second col']/span[@class='js-unit-fee']")
	private WebElement lateFee_Amt;
	
	@FindBy(xpath="//div[@id='payment-table']//div//section[@class='prorate row']//div/span[@class='k-widget k-dropdown k-header js-prepay payOptionDrop']")
	private WebElement payThrough_Dropdown;

	@FindBy(xpath="//div[@id='payment-table']//section//div//span[@class='js-total']")
	private WebElement total;

	@FindBy(xpath="//div[contains(@class,'payment-methods__method payment-method-form payment-method-form--credit-card')]//a[text()='Enter Manually']")
	private WebElement enterManual_btnCC;
	
	@FindBy(id="creditCardAmount")
	private WebElement enter_CCAmount;

	@FindBy(xpath="//input[@class='creditCardAutoPay']/following-sibling::span[@class='button']")
	private WebElement autopayCCCheckbox;
	
	public PaymentsPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}

	public String get_totalRemaningBalAmt(){

		return get_totalRemaningBalAmt.getText().trim();
	}

	public void clicAdd_EditMerchandise() {
		Add_EditMerchandise_lnk.click();
	}
	public String getTotal()
	{
		return total.getText().trim();
	}
	
	public String getTotalDueNow() {

		return totalDueNow_Amount.getText();
	}

	public String getMonthlyRentAmount() {

		return monthlyRentAmount.getText();
	}

	public String getInsuranceAmount() {

		return insuranceAmount.getText();
	}

	public String getPageTitle(){

		return paymentTitle.getText();
	}

	public String getTotalDueNowInPaymentSection(){

		return totalDueInPaymentSection.getText();
	}



	public void clickOnConfirmWithCustomer_Btn(){
		confirmWithCustomer_Btn.click();
	}

	public void clickCancelbtn()
	{
		cancel_btn.click();
	}
	public void clickBkToBashbtn()
	{
		BktoDash_btn.click();
	}

	public void clickSubmitbtn()
	{
		submit_btn.click();
	}

	public void Enter_accountNumber(String value)
	{
		accountNumber.sendKeys(value);
	}
	
	public void clickapplybtn()
	{
		apply_btn.click();
	}
	public void clickmanualentry()
	{
		enterManual_btn.click();
	}

	public void Select_dropdown()
	{
		paymentDropdown.click();

	}

	public void Enter_routingNumber(String value)
	{
		routingNumber.sendKeys(value);
	}
	public void Enter_CheckAccountNumber(String value)
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
		List<WebElement> ListWbEle=driver.findElements(By.xpath("//div[contains(@class,'k-list-container k-popup k-group k-reset')]/ul/li[@class='k-item']"));
		for (WebElement ele : ListWbEle) {
			String actualWbEleText=ele.getText();
			if(actualWbEleText.equalsIgnoreCase(expVal)) 
				ele.click();

		}

	}

	public void selectPaymentThrough(int inputValue) throws InterruptedException {
		payThroughDropdown.click();
		Thread.sleep(3000);
		WebElement optionsBox = driver.findElement(By.xpath("//div[@class='k-animation-container']//ul"));
		//optionsBox.click();
		List<WebElement> ListWbEle = optionsBox.findElements(By.tagName("li"));
		int Size = ListWbEle.size();
		for (int i = 0; i < Size; i++) {
			if (i == inputValue) {

				Actions builder = new Actions(driver);
				builder.moveToElement(ListWbEle.get(i)).click().build().perform();
				break;
			}
		}
	}

	public void selectPaymentThroughForMultipleSpaces_Anniversary(int AdvPay) throws InterruptedException {


		List<WebElement> ls_PayThroDrpDwn = driver.findElements(By.xpath("//div[@id='payment-table']//section[@class='prorate row']//span[@class='k-input']"));
		for(int j=0;j<ls_PayThroDrpDwn.size();j++)
		{
			Thread.sleep(1000);
			driver.findElement(By.xpath("(//div[@id='payment-table']//section[@class='prorate row']//span[@class='k-input'])["+j+"+1]")).click();
			Thread.sleep(3000);
			List<WebElement> ListWbEle = driver.findElements(By.xpath("(//div[@class='k-animation-container'])["+j+"+1]/div[@data-role='popup']/ul/li[text()='None']/following-sibling::li"));
			int Size = ListWbEle.size();
			for (int i = 1; i <= Size; i++) {
				if (i == AdvPay) {
					Actions builder = new Actions(driver);
					builder.moveToElement(ListWbEle.get(i-1)).click().build().perform();
					break;
				}
			}
		}

	}
	public boolean isPaymentsPageDisplayed(){

		return paymentTitle.isDisplayed();
	}

	public String get_TotalRemainingAmt(){
		return get_TotalRemainingAmt.getText().trim();


	}
	public String get_TtlBalAmt()
	{
		return txt_TtlBal.getText();
	}

	public void enter_EmployeeIDInAmtOwnig(String EmpID)
	{
		TxtBx_AmtOwing_EmpNum.sendKeys(EmpID);
	}


	public void clk_AmtOwing_OKBtn()
	{
		Btn_AmtOwing_OK.click();
	}

	public void selectPaymentThroughForMultipleSpaces(int AdvPay) throws InterruptedException {


		List<WebElement> ls_PayThroDrpDwn = driver.findElements(By.xpath("//div[@id='payment-table']//section[@class='prorate row']//span[@class='k-input']"));
		for(int j=0;j<ls_PayThroDrpDwn.size();j++)
		{
			Thread.sleep(1000);
			driver.findElement(By.xpath("(//div[@id='payment-table']//section[@class='prorate row']//span[@class='k-input'])["+j+"+1]")).click();
			Thread.sleep(3000);
			List<WebElement> ListWbEle = driver.findElements(By.xpath("(//div[@class='k-animation-container'])["+j+"+1]/div[@data-role='popup']/ul/li[text()='None']/following-sibling::li"));
			int Size = ListWbEle.size();
			for (int i = 0; i < Size; i++) {
				if (i == AdvPay) {
					ListWbEle.get(i).click();
					break;
				}
			}
		}

	}

	public boolean verify_PageTitle(){

		return paymentTitle.isDisplayed();
	}

	public String txt_location(){

		return location.getText();
	}

	public String txt_spacedetails(){

		return spacedetails.getText();
	}

	public String txt_nextDueDate(){

		return nextDueDate.getText();
	}

	public String txt_totalDuenow(){

		return totalDuenow.getText();
	}

	public String txt_nextDueamt(){

		return nextDueamt.getText();
	}

	public String txt_totalDuenow_sub(){

		return totalDuenow_sub.getText();
	}

	public String txt_nextDueamt_sub(){

		return nextDueamt_sub.getText();
	}

	public void selectPaymentThrough() throws InterruptedException {

		payThroughDropdown.click();
		Thread.sleep(3000);
		List<WebElement> ListWbEle = driver.findElements(By.xpath("//div[@class='k-animation-container']//ul//li"));
		Reporter.log("The size is:"+ListWbEle.size(),true);
		int Size = ListWbEle.size();
		for (int i = 0; i < Size; i++) {
			if (i == 1) {
				ListWbEle.get(i).click();
				break;
			}
		}

	}
	public void selectPaymentThrough_firstSpace() throws InterruptedException {

		paymentDropdown1.click();
		Thread.sleep(3000);
		List<WebElement> ListWbEle = driver.findElements(By.xpath("//div[@class='k-animation-container']//ul//li"));
		Reporter.log("The size is:"+ListWbEle.size(),true);
		int Size = ListWbEle.size();
		for (int i = 0; i < Size; i++) {
			if (i == 1) {
				ListWbEle.get(i).click();
				break;
			}
		}

	}


	public void selectPaymentThrough_SecSpaceSpace() throws InterruptedException {

		paymentDropdown1.click();
		Thread.sleep(3000);
		List<WebElement> ListWbEle = driver.findElements(By.xpath("//div[@class='k-animation-container']//ul//li"));
		Reporter.log("The size is:"+ListWbEle.size(),true);
		int Size = ListWbEle.size();
		for (int i = 0; i < Size; i++) {
			if (i == 2) {
				ListWbEle.get(i).click();
				break;
			}
		}
	}

	public void click_PayDropdwn(){
		paymentDropdown1.click();
	}


	public int options_Count() {
		List<WebElement> options= driver.findElements(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul/li"));
		int count=options.size();
		return count;
	}

	public void click_PayDropdwn2() {
		paymentDropdown2.click();

	}


	public void clk_AddEditCart(){
		addEditCart.click();
	}

	public void clk_Promotion(){
		selectPromotion.click();
	}

	public void clk_ApplybtnMer(){
		applyButtonMer.click();
	}

	public void selectPromotion() throws InterruptedException {

		selectPromotion.click();
		Thread.sleep(3000);
		List<WebElement> ListWbEle = driver.findElements(By.xpath("//div[@class='k-animation-container']//ul[@class='k-list k-reset ps-container']/li"));
		int Size = ListWbEle.size();
		for (int i = 0; i < Size; i++) {
			if (i == 2) {
				ListWbEle.get(i).click();
				break;
			}
		}

	}

	public void clear_CashAmount()

	{
		enter_CashAmount.clear();
	}
	public void clk_YesRadio(){
		yesRadioBtn.click();
	}

	public void clk_NoRadio(){
		noRadioBtn.click();

	}

	public String getMonthlyRent(){
		return monthly_rent.getText();
	}

	public String getRentTax(){
		return rent_tax.getText();
	}

	public String getMiscCharge(){
		return misc_charge.getText();
	}

	public String getTotalDue(){
		return totalDue.getText();
	}
	public String getTotalAmount(){
		return total_amount.getText().trim();
	}

	public void enterCashAmount(String text) throws InterruptedException{
		amount_cash.click();
		Thread.sleep(2000);
		amount_cash.sendKeys(Keys.chord(Keys.CONTROL,"a"));
		Thread.sleep(4000);
		amount_cash.sendKeys(text);
	}

	public String getText_ExtraCashMessage(){
		return extraCash_Indicator.getText();
	}

	public String getText_Notification(){
		return notification.getText();
	}

	public void click_EnoughCash_Yes(){
		enoughCash_Yes.click();

	}

	public void click_EnoughCash_No(){
		enoughCash_No.click();

	}

	public void selectPaymentMethod_mischarges(String expVal,WebDriver driver) throws InterruptedException{
		paymentSelectdropdwn_mischarges.click();
		Thread.sleep(2000);
		List<WebElement> ListWbEle2=driver.findElements(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul/li"));

		for(int i=0; i<ListWbEle2.size();i++){
			System.out.println(" the elements are--"+ListWbEle2.get(i).getText());
		}	

		for (WebElement ele : ListWbEle2) {
			String actualWbEleText=ele.getText();
			if(actualWbEleText.contains(expVal)) 
				ele.click();

		}

	}

	public void mischarges_dropdown(String inputValue,WebDriver driver) throws InterruptedException{
		mischarges_dropdown.click();
		Thread.sleep(2000);
		List<WebElement> ListWbEle1=driver.findElements(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul/li"));

		for(int i=0; i<ListWbEle1.size();i++){
			System.out.println(" the elements are--"+ListWbEle1.get(i).getText());
		}


		int Size = ListWbEle1.size();
		for (int i = 0; i < Size; i++) {

			if (ListWbEle1.get(i).getText().equals(inputValue)) {
				System.out.println("--"+ListWbEle1.get(i).getText());
				System.out.println("--"+inputValue);
				Actions builder = new Actions(driver);
				builder.moveToElement(ListWbEle1.get(i)).click().build().perform();
				break;
			}
		}


	}

	//=====================Rudra

	public void clickCashAmountField(){


		enter_CashAmount.click();
	}


	public void enter_CashAmount(String cashAmt){


		enter_CashAmount.sendKeys(cashAmt);
	}

	public String getChangeDue()
	{
		return changeDue.getText().trim();
	}

	public boolean verify_PaymentTitle(){

		return paymentTitle.isDisplayed();
	}

	public boolean verifyInsurance()
	{
		return insurance_field.isDisplayed();
	}




	public void click_Submitbtn()
	{
		submit_btn.click();
	}

	public boolean verifyInsuranceSection()
	{
		return insurance.isDisplayed();
	}

	public String getTotalRemaining()
	{
		return totalRemaining.getText().trim();
	}



	public String getMiscAbandonedCharge(){
		return miscAbandoned_charge.getText();
	}



	public void clickmanualentry_MO()
	{
		MOenterManual_btn.click();
	}

	public String getMonthlyRent_Next()
	{
		return monthlyrent_Next.getText().trim();
	}
	
	public String get_merchandise_rentAmt()
	{
		return merchandise_rentAmt.getText().trim();
	}
	
	public String get_salesTax_Amt()
	{
		return salesTax_Amt.getText().trim();
	}
	
	public String get_MerchandiseSubtotal_Amt()
	{
		return MerchandiseSubtotal_Amt.getText().trim();
	}
	
	public String gettotal_amount() {

		return total_amount.getText();
	}
	
	public boolean lateFee_Amt_isdisplayed(){
		return lateFee_Amt.isDisplayed();
	}


	public String get_lateFee_Amt(){
		return lateFee_Amt.getText();
	}
	
	public void click_paymentSelectdropdwn_Btn() {
		paymentSelectdropdwn.click();
	}
	
	public void clickPayThroughDropdown() {
		payThrough_Dropdown.click();
	}
	
	public void clickmanualentryCC()
	{
		enterManual_btnCC.click();
	}

	public void clickCCField(){


		enter_CCAmount.click();
	}
	

	public void enterCCAmount(String ccAmt){


		enter_CCAmount.sendKeys(ccAmt);
	}
	
	public void clickCCAutopayCheckBox()
	{
		autopayCCCheckbox.click();
	}

	
}



