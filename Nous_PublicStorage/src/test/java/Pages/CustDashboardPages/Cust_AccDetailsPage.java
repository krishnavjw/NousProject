package Pages.CustDashboardPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Cust_AccDetailsPage {

	WebDriver driver;

	@FindBy(xpath = "//div[@class='container-heading']/div/h3")
	private WebElement cust_dbTitle;

	@FindBy(xpath = "//div[@class='balance-section which-unit']//div[@class='unit']")
	public WebElement space_Num;

	@FindBy(xpath = "//span[text()=' Location']")
	public WebElement Location;

	@FindBy(xpath = "//a[@class='location-details']")
	public WebElement Location_details;

	@FindBy(xpath = "//div[text()='Total Due Now']")
	public WebElement Search_totalduenow;

	@FindBy(xpath = "//div[text()='Total Due Now']/following-sibling::span[@class='balance-section-date']")
	public WebElement total_NowDate;

	@FindBy(xpath = "//div[@class='balance-section due-now']/div[@class='sub-section mid due-now-amount']/div")
	public WebElement total_BalanceNow1;

	@FindBy(xpath = "//div[text()='Next Payment Due']")
	public WebElement Search_Nextduenow;

	@FindBy(xpath = "//div[text()='Next Payment Due']/following-sibling::span[@class='balance-section-date']")
	public WebElement Search_NextpaymentDue;

	@FindBy(xpath = "//div[@class='balance-section due-next']//div[@class='sub-section mid']/div")
	public WebElement search_NextPaymentDueAmount;

	@FindBy(xpath = "//div[text()='Space is Delinquent']")
	public WebElement space_DelinquentLink;

	@FindBy(xpath = "//a[contains(text(),'Make Payment')]")
	public WebElement makePayement_Link;

	@FindBy(xpath = "//a[contains(text(),'View Details')]")
	public WebElement viewDetails_Link;

	@FindBy(xpath = "(//div[@class='padding-top padding-bottom']/a)[2]")
	public WebElement manage_Autoplaylk;

	@FindBy(xpath = "//li/span[text()='Customer Info']")
	public WebElement custInfo_Tab;

	@FindBy(xpath = "//div[contains(text(),'Current Account:')]/following-sibling::div")
	public WebElement custCurrAccno;

	@FindBy(xpath = "//div[contains(text(),'Address:')]/following-sibling::div[@class='content floatright']")
	public WebElement cust_address;

	@FindBy(id = "editAccountDetails")
	public WebElement editAcc_btn;

	@FindBy(xpath = "//li/span[text()='Space Details']")
	public WebElement spaceDetails_Tab;

	@FindBy(xpath = "//li/span[text()='Account Activities']")
	public WebElement AccActivities_Tab;

	@FindBy(xpath = "//li/span[text()='Documents']")
	public WebElement documents_Tab;

	@FindBy(xpath = "//div[@class='balance-section due-now']/div[@class='sub-section mid due-now-amount']")
	public WebElement totalDueNow;

	@FindBy(xpath = "//div[@id='customerDashboard']//div//span[@class='important-info-title']")
	public WebElement importantInformationSection;

	@FindBy(xpath = "//div[@id='customerDashboard']//div[@class='actions clearfix-container']/a[contains(text(),'Create Note')]")
	public WebElement note_Btn;

	@FindBy(xpath = "//div[@class='floatleft space']//span[@class='space-rentalunitnumber']")
	public WebElement space_num;

	@FindBy(xpath = "(//td[contains(text(),'Miscellaneous Charge')]/../td[1]/a)[1]")
	public WebElement Miscellaneous_Charge;

	@FindBy(xpath = "//td[contains(text(),'Abandoned Goods Small:')]/following-sibling::td[2]")
	public WebElement Abandoned_Goods_Small;

	@FindBy(xpath = "//div[@class='total-due-now__details__detail total-due-now__details__detail--total']")
	public WebElement viewdetails_total_due;

	@FindBy(xpath = "//a[contains(text(),'View Details')]")
	public WebElement viewdetails;

	@FindBy(xpath = "(//td[contains(text(),'Adjustment')]/../td[1]/a)[1]")
	public WebElement Adjustment_Charge;

	@FindBy(xpath = "//a[contains(text(),'Create Note')]")
	private WebElement createNote;

	@FindBy(xpath = "//span[contains(text(),'Important Information')]")
	private WebElement importantInformation;

	@FindBy(xpath = "//div[@id='customerDashboard']//div[@class='balance-section due-now']//div[text()='Total Due Now']/../../following-sibling::div[@class='sub-section bottompaidthru']/div")
	private WebElement lateChargedateForTotalDueNow;

	@FindBy(xpath = "//td[contains(text(),'Check')]/../td[1]/a")
	public WebElement clk_ExpandBtn;

	@FindBy(xpath = "//div[@id='customerDashboard']//div[@class='balance-section due-next']//div[@class='sub-section mid']//div")
	private WebElement nextPaymentDuebal;

	@FindBy(xpath = "//div[@id='customerDashboard']//div[@class='balance-section due-next']//div[@class='bold sub-sectiontitle']/following-sibling::span")
	private WebElement dateForNextPaymentDue;

	@FindBy(xpath = "//div[@class='payment-reversal']/a")
	public WebElement Lnk_RevPaymnt;

	@FindBy(xpath = "(//td[contains(text(),'Money Order')]/../td[1]/a)[1]")
	public WebElement clk_ExpandMoneyoderLink;

	@FindBy(xpath = "(//td[contains(text(),'Check')]/../td[1]/a)[1]")
	public WebElement clk_ExpandChecklink;

	@FindBy(xpath = "//td[contains(text(),'Payment (Money Order)')]/../td[1]/a")
	private WebElement moneyorderExpand;

	@FindBy(xpath = "//div[@id='customerDashboard']//span[contains(text(),'Important Information')]")
	public WebElement importantInfo;

	@FindBy(xpath = "//div[contains(text(),'Email:')]/following-sibling::div/div[@class='padding-bottom']/div/span")
	private List<WebElement> get_emailId;

	@FindBy(xpath = "(//div[@class='padding-top padding-bottom']/a)[2]")
	private WebElement manage_Autopay;

	@FindBy(xpath = "//td[contains(text(),'Merge')]//preceding-sibling::td[@class='k-hierarchy-cell']")
	private WebElement mergeactivity;

	@FindBy(xpath = "//a[text()='Unmerge']")
	private WebElement unmergelink;

	@FindBy(xpath = "//div[@class='actions clearfix-container']//span[contains(text(),'Quick Links')]")
	private WebElement quicklink_drpdown;

	@FindBy(xpath = "//div[@id='customerDashboard']//a[contains(text(),'Back To Dashboard')]")
	private WebElement backToDashboard;

	@FindBy(xpath = "//div[text()='Space Vacated']")
	private WebElement spaceVactedText;

	@FindBy(xpath = "//span[text()=' Location']/following-sibling::a[@class='location-details']")
	public WebElement locNum;

	@FindBy(id = "editAccountDetails")
	public WebElement EditAccDetails;

	@FindBy(xpath = "//div[@class='balance-section due-now']//div[@class='sub-section mid due-now-amount']/div")
	public WebElement total_BalanceNow;

	@FindBy(xpath = "//div[@class='balance-section due-next']//div[@class='sub-section mid']/div")
	public WebElement NextPaymentDueAmount;
	
	@FindBy(xpath="//ul[@class='k-list k-reset ps-container ps-active-y']/li[contains(text(),'Create Issue')]")
	private WebElement createIssue;
	
	@FindBy(xpath="//ul[@class='k-list k-reset ps-container ps-active-y']/li[contains(text(),'Buy Merchandise')]")
	private WebElement buyMerchandise;
	
	@FindBy(xpath="//ul[@class='k-list k-reset ps-container ps-active-y']/li[contains(text(),'Return Merchandise')]")
	private WebElement returnMerchandise;
	
	@FindBy(xpath="//ul[@class='k-list k-reset ps-container ps-active-y']/li[contains(text(),'Add Space')]")
	private WebElement addSpace;
	
	@FindBy(xpath="//div[contains(text(),'No Payments')]")
	private WebElement noPayment_Flag;

	
	@FindBy(xpath="//div[text()='Gate Code:']/following-sibling::div")
	private WebElement gateCode;
	
	@FindBy(xpath="//div[text()='Move-In Promotion:']/following-sibling::div/div")
	private WebElement promo;
	
	@FindBy(xpath="//div[@class='important-info-title title'][text()='None at this time.']")
	private WebElement importMessNone;
	
	@FindBy(xpath="//span[@class='important-info-title title bold'][text()='DO NOT RENT']")
	private WebElement importMessDoNotRent;
	
	@FindBy(xpath="//div[contains(text(),'Bankruptcy Declared')]")
	private WebElement bankruptcy;
	
	@FindBy(xpath="//div[contains(text(),'Bankruptcy Declared')]/following-sibling::div[contains(text(),'(unverified)')]")
	private WebElement bankruptcy_unverified;
	
	@FindBy(xpath="//div[contains(text(),'Bankruptcy Declared')]/following-sibling::div[contains(text(),'(verified)')]")
	private WebElement bankruptcy_verified;
	
	@FindBy(xpath="//div[contains(text(),'Deceased Notified')]")
	private WebElement deceasedNotified;
	
	@FindBy(xpath="//div[contains(text(),'Deceased Notified')]/following-sibling::div[contains(text(),'(unverified)')]")
	private WebElement deceasedNotified_Unverified;
	
	@FindBy(xpath="//div[contains(text(),'Deceased Notified')]/following-sibling::div[contains(text(),'(verified)')]")
	private WebElement deceasedNotified_Verified;
	
	@FindBy(xpath="//div[contains(text(),'No Checks')]")
	private WebElement noChecks_Flag;
	
	@FindBy(xpath="//div[contains(text(),'No Credit Cards')]")
	private WebElement noCreditCard_Flag;
	
	@FindBy(xpath="//div[contains(text(),'No Credit Cards/No Checks')]")
	private WebElement noCreditAndChecks_Flag;
	
	@FindBy(xpath="//div[@id='customerDashboard']//div/div[@class='customer-categories bold']")
	private WebElement get_TypeOfCust;

	@FindBy(xpath="//div[@id='customerDashboard']//div/div[@class='customer-categories bold']/span[2]")
	private WebElement get_CustLevel;
	
	@FindBy(xpath="//div[@id='customerDashboard']//div/div[@class='customer-categories bold']/span[3]")
	private WebElement get_BusinessCustGradeLevel;
	
	@FindBy(xpath="//div[@id='customerDashboard']//div[@class='customer-categories bold']/span[1]")
	private WebElement get_BusinessCustNames;
	
	@FindBy(xpath="//div[@id='customerDashboard']//a[text()='Auction Buyer']")
	private WebElement auctionLink;
	
	@FindBy(xpath="//a[@class='payment-commitment-date-link']")
	private WebElement paymentdate;
	
	
	public Cust_AccDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String getCustDashboardTitle() {
		return cust_dbTitle.getText();
	}

	public void clickSpaceDetails_tab() {
		spaceDetails_Tab.click();
	}

	public void clickMakePayment_Btn() {
		makePayement_Link.click();
	}

	public String getTotalDueNow() {
		return totalDueNow.getText();
	}

	public void clickOnManageAutoPay_Lnk() {
		manage_Autoplaylk.click();
	}

	public String getCustSpaceNum() {
		return space_Num.getText();
	}

	public boolean verify_DocumentsTab() {
		return documents_Tab.isDisplayed();
	}

	public void clk_DocumentsTab() {
		documents_Tab.click();
	}

	public boolean verifyCustAccDashboard() {
		return makePayement_Link.isDisplayed();
	}

	public boolean isCustdbTitleDisplayed() {
		return cust_dbTitle.isDisplayed();
	}

	public boolean Verify_CustDashboard() {
		return cust_dbTitle.isDisplayed();
	}

	public void click_AccountActivities() {
		AccActivities_Tab.click();
	}

	public String getcustCurrAccno() {
		return custCurrAccno.getText();
	}

	public boolean verifyTotalDueSection() {
		return Search_totalduenow.isDisplayed();
	}

	public boolean verifyNextPaymentDueSection() {
		return Search_NextpaymentDue.isDisplayed();
	}

	public boolean verifyMakePaymentLink() {
		return makePayement_Link.isDisplayed();
	}

	public boolean verifyViewDetailsLink() {
		return viewDetails_Link.isDisplayed();
	}

	public boolean verifyManageAutopayLink() {
		return manage_Autoplaylk.isDisplayed();
	}

	public boolean verifyImportantInformationSection() {
		return importantInformationSection.isDisplayed();
	}
	
	
	

	public boolean verify_NoPayments(){
		return noPayment_Flag.isDisplayed();
	}
	

	public boolean verifyNoteButton() {
		return note_Btn.isDisplayed();
	}

	public boolean verifyDocumentsTab() {
		return documents_Tab.isDisplayed();
	}

	public boolean verifyCustomerInfoTab() {
		return custInfo_Tab.isDisplayed();
	}

	public void clickViewDetails() {
		viewDetails_Link.click();
	}

	public boolean verifySpaceDetailsTab() {
		return spaceDetails_Tab.isDisplayed();
	}

	public boolean verifyAccountActivitiesTab() {
		return AccActivities_Tab.isDisplayed();
	}

	public void clk_quicklink() {
		quicklink_drpdown.click();
	}

	public void sel_quickLinks_dropDown(WebDriver driver) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(250,0)", "");
		Thread.sleep(5000);
		js.executeScript("window.scrollBy(0,250)", "");
		driver.findElement(By.xpath("//div[@class='actions clearfix-container']//span[contains(text(),'Quick Links')]"))
				.click();

		JavascriptExecutor je = (JavascriptExecutor) driver;

		// Identify the WebElement which will appear after scrolling down
		List<WebElement> elements = driver
				.findElements(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li"));
		for (WebElement ele : elements) {
			if (ele.getText().equalsIgnoreCase("Add Space")) {
				je.executeScript("arguments[0].scrollIntoView(true);", ele);
				je.executeScript("arguments[0].click();", ele);
			}
		}
	}

	public String getTotalDue() {
		return total_BalanceNow1.getText();
	}

	public String Abandoned_Goods_gettext(String spaceNum) {
		return driver.findElement(By.xpath("//div[@data-rentalunitnumber='" + spaceNum
				+ "']//div[contains(text(),'Abandoned Goods Small')]/following-sibling::div[1]")).getText();
	}

	public String spacenum_gettext() {
		return space_num.getText();
	}

	public boolean VerifyMiscellaneousCharge_Link() {
		return Miscellaneous_Charge.isDisplayed();
	}

	public void VerifyMiscellaneousCharge_click() {
		Miscellaneous_Charge.click();
	}

	public void quicklinks_dropdown(int inputValue, WebDriver driver) throws InterruptedException {
		quicklink_drpdown.click();
		Thread.sleep(2000);
		WebElement ListWbEle1 = driver.findElement(
				By.xpath("//div[@class='k-list-container k-popup k-group k-reset quick-links-list']/ul/li[10]"));
		ListWbEle1.click();

	}

	public String Abandoned_Goods_Small() {
		return Abandoned_Goods_Small.getText();
	}

	public String viewdetails_total_due() {
		return viewdetails_total_due.getText();
	}

	public void viewdetails_click() {
		viewdetails.click();
	}

	public boolean VerifyAdjustmentCharge_Link() {
		return Adjustment_Charge.isDisplayed();
	}

	public void VerifyAdjustmentCharge_click() {
		Adjustment_Charge.click();
	}

	public void clk_CheckExpandLink() {
		clk_ExpandChecklink.click();
	}

	public String getTotalDueNowTxt() {
		return Search_totalduenow.getText();
	}

	public String getNextPaymentDueTxt() {
		return Search_Nextduenow.getText();
	}

	public String getMakePaymentTxt() {
		return makePayement_Link.getText();
	}

	public String getViewDetailsTxt() {
		return viewDetails_Link.getText();
	}

	public String getManageAutoPayTxt() {
		return manage_Autopay.getText();
	}

	public String getCreateNoteTxt() {
		return createNote.getText();
	}

	public String getImportantInformationTxt() {
		return importantInformation.getText();
	}

	public String getCustomerInfoTabTxt() {
		return custInfo_Tab.getText();
	}

	public String getSpaceDetailsTabTxt() {
		return spaceDetails_Tab.getText();
	}

	public String getAccountActivitiesTabTxt() {
		return AccActivities_Tab.getText();
	}

	public String getDocumentsTabTxt() {
		return documents_Tab.getText();
	}

	public boolean is_makePayement_Link() {
		return makePayement_Link.isDisplayed();
	}

	public void clk_EditAccountDetailsBtn() {

		editAcc_btn.click();
	}

	public boolean getEmailid(String exp_email) {
		boolean b = false;
		for (int i = 0; i < get_emailId.size(); i++) {

			String email = get_emailId.get(i).getText();
			if (email.contains(exp_email)) {
				b = true;
				break;

			}
		}
		return b;
	}

	public void clk_MoneyOrderExpandLink() {
		(new WebDriverWait(driver, 120)).until(ExpectedConditions.elementToBeClickable(clk_ExpandMoneyoderLink));
		clk_ExpandMoneyoderLink.click();
	}

	public String getlateChargedateForTotalDueNow() {
		return lateChargedateForTotalDueNow.getText();
	}

	public void clk_ReversePaymntLnk() {
		Lnk_RevPaymnt.click();
	}

	public void clk_ViewDetailsLink() {
		viewDetails_Link.click();
	}

	public void clk_ExpandBtn() {
		clk_ExpandBtn.click();
	}

	public void clk_Acc_ActivitiesTab() {
		AccActivities_Tab.click();
	}

	public String txt_TotalDueNowAmt() {
		return total_BalanceNow.getText();
	}

	public String txt_NextpaymentDueAmt() {
		return search_NextPaymentDueAmount.getText();
	}

	public boolean verify_TotalDueNowAmt() {
		return total_BalanceNow.isDisplayed();
	}

	public boolean verify_NextpaymentDueAmt() {
		return search_NextPaymentDueAmount.isDisplayed();
	}

	public String txt_NextpaymentDueDate() {
		return Search_NextpaymentDue.getText();
	}

	public boolean verify_NextpaymentDueDate() {
		return Search_NextpaymentDue.isDisplayed();
	}

	public String getNextPaymentDueAmt() {
		return search_NextPaymentDueAmount.getText();
	}

	public String getNextPaymentDate() {
		return Search_NextpaymentDue.getText();
	}

	public boolean verify_CreateNote() {
		return createNote.isDisplayed();
	}

	public boolean verify_ImportantInfo() {
		return importantInfo.isDisplayed();
	}

	public boolean verify_MakePaymentLnk() {
		return makePayement_Link.isDisplayed();
	}

	public boolean verify_ViewDetails() {
		return viewDetails_Link.isDisplayed();
	}

	public void clk_CheckExpandLinkMoneyOrder() {
		moneyorderExpand.click();
	}

	public String getNextPaymentDueInCustDashboard() {
		return nextPaymentDuebal.getText().trim();
	}

	public String getDateForNextPaymentDue() {
		return dateForNextPaymentDue.getText();
	}

	public void click_BackToDashboard() {
		backToDashboard.click();
	}

	public void clk_AccActivities() {
		AccActivities_Tab.click();
	}

	public boolean isUnmergeLinkDisplayed(WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].scrollIntoView(true);", unmergelink);
		return unmergelink.isDisplayed();
	}

	public void clkOnMergeActivity() {
		mergeactivity.click();
	}

	public boolean verify_CustomerInfoTab() {
		return custInfo_Tab.isDisplayed();
	}

	public boolean verify_SpaceDetailsTab() {
		return spaceDetails_Tab.isDisplayed();
	}

	public boolean verify_AccountActivitiesTab() {
		return AccActivities_Tab.isDisplayed();
	}

	public void clk_EditAccDetails() {
		editAcc_btn.click();
	}

	public void click_custInfo_Tab() {
		custInfo_Tab.click();
	}

	public void clk_editAcc_btn() {
		editAcc_btn.click();
	}

	public void click_EditAccDetails() {
		EditAccDetails.click();
	}

	@FindBy(xpath = "//span[text()='Customer Info']")
	private WebElement custInfoPageTitle;

	public String getCustInfoPageTitle() {
		return custInfoPageTitle.getText();
	}

	public boolean isNextduenowDisplayed() {

		return Search_Nextduenow.isDisplayed();
	}

	public boolean isSpaceVactedTextDisplayed() {

		return spaceVactedText.isDisplayed();
	}

	public String getLocationNumber() {
		return locNum.getText();
	}

	public String getNextPaymentDueAmount() {
		return NextPaymentDueAmount.getText().trim();
	}
	
	
	public boolean verifyImportantInfoNone(){
		return importMessNone.isDisplayed();
	}
	
	public boolean verify_DoNotRentFlag(){
		return importMessDoNotRent.isDisplayed();
	}
	
	public boolean verify_BankruptcyFlag(){
		return bankruptcy.isDisplayed();
	}
	
	public boolean verify_BankruptcyUnverified(){
		return bankruptcy_unverified.isDisplayed();
	}
	
	public boolean verify_BankruptcyVerified(){
		return bankruptcy_verified.isDisplayed();
	}
	
	public boolean verify_DeceasedNotifiedFlag(){
		return deceasedNotified.isDisplayed();
	}
	
	public boolean verify_DeceasedNotifiedUnverified(){
		return deceasedNotified_Unverified.isDisplayed();
	}
	
	public boolean verify_DeceasedNotifiedVerified(){
		return deceasedNotified_Verified.isDisplayed();
	}
	
	public boolean verify_noChecks() {
		return noChecks_Flag.isDisplayed();
	}
	
	public boolean verify_noCreditCard(){
		return noCreditCard_Flag.isDisplayed();
	}
	
	public boolean verify_noCreditAndChecks(){
		return noCreditAndChecks_Flag.isDisplayed();
	}
	
	public boolean verify_custInfo_Tab() {
		return custInfo_Tab.isDisplayed();		
	}
	
	public boolean verify_EditAccDetails() {
		return EditAccDetails.isDisplayed();		
	}

	public boolean verify_CustSpaceNum(){
		return space_Num.isDisplayed();
	}
	
	public boolean verify_Spacenum(){
		return Location_details.isDisplayed();
	}
	
	public boolean verify_NextPaymentDueColumn(){
		return Search_Nextduenow.isDisplayed();
	}
	
	public void clk_CreateNoteBtn(){
		createNote.click();
	}
	
	public boolean verify_QuickLink(){
		return quicklink_drpdown.isDisplayed();
	}
	
	public String getQuicktext(){
		return quicklink_drpdown.getText();
	}
	
	public void clk_CreateIssue(){
		createIssue.click();
	}
	
	public void clk_BuyMerchandise(){
		buyMerchandise.click();
	}
	
	public void clk_ReturnMerchandise(){
		returnMerchandise.click();
	}
	
	public void clk_AddSpace(){
		addSpace.click();
	}
	
	
	public String getGateCode()
	{
		return gateCode.getText().trim();
	}
	
	public String getPromotion()
	{
		return promo.getText().trim();
	}
	

	public void get_spacenumber_Clickicon(String value) throws InterruptedException{
		List<WebElement> lst=driver.findElements(By.xpath("//div[@id='space-details-grid']//table//tbody/tr/td[2]/div//span[@class='space-rentalunitnumber']"));
		for(WebElement ele:lst){
			if((ele.getText().trim()).equals(value)){
				driver.findElement(By.xpath("//div[@id='space-details-grid']//table//tbody/tr/td[2]/div//span[text()='"+value+"']/../../../preceding-sibling::td[1]/a")).click();
			Thread.sleep(1000);
			}
		}
		}	
	
	@FindBy(xpath="//h1[@class='customer-name bold']")
	public WebElement customerName;
	
	public String get_customerName(){
		return customerName.getText();
	}
	
	public boolean isSpaceNumDisplayed(){

		return space_Num.isDisplayed();
	}
	

	public String getTypeOfCustomer(){

		return get_TypeOfCust.getText();
	}

	public String getCustomerLevel(){

		return get_CustLevel.getText().replace("|", "").trim();
	}
	
	public String getBusinessCustomerNames(){
		
		return get_BusinessCustNames.getText().trim();
	}
	
	public String getBusinessCustomerGradeLevel(){
		
		return  get_BusinessCustGradeLevel.getText().replace("|", "").trim();
	}
	
	public boolean isBackToDashBaordDisplayed(){

		return backToDashboard.isDisplayed();
	}
	
	public void Click_Auctionlink(){
		auctionLink.click();
	}
	
	public boolean isDisplayedAuctionLink(){
		return auctionLink.isDisplayed();
	}

	public boolean isSpaceDetailsTabDisplayed(){
		
		return spaceDetails_Tab.isDisplayed();
	}
	
	public boolean isAccountAcctivitiesTabDisplayed(){
		
		return AccActivities_Tab.isDisplayed();
	}
	
	public boolean quicklinkDrpDownDispalyed(){
		return quicklink_drpdown.isDisplayed();
	}
	
	public void click_PaymentDate(){
		paymentdate.click();
	}
	
	public String getPaymentDate(){
		return paymentdate.getText().trim();
	}
	
	
	@FindBy(xpath="//td[contains(text(),'Miscellaneous Charge')]/preceding-sibling::td[6]")
	public WebElement Miscellaneous_ChargeArrowbutton ;	
	
	
	public WebElement MiscellaneousChargeArrowbutton(){

		return Miscellaneous_ChargeArrowbutton ;
	}
	
}
