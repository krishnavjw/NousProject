package Pages.Walkin_Reservation_Lease;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewReservationPage {

	@FindBy(xpath = "//span[text()='Reservation Number:']/following-sibling::span")
	private WebElement Txt_ReservationNo;

	@FindBy(xpath = "//div//h3[text()='View Reservation']")
	private WebElement get_ViewReservationTitle;

	@FindBy(xpath = "//td//div//a[@class='edit-icon']")
	private WebElement EditBtnInSpaceInfo;

	@FindBy(xpath = "//div[@id='reservationPage']//h3[text()='View Reservation']")
	private WebElement ViewResHeader;

	@FindBy(xpath = "//div[@class='reservation-info-bar clearfix-container']")
	private WebElement resInfoBar;

	@FindBy(xpath = "//div//h3[text()='Cancelled Reservation']")
	private WebElement cancelledReservationHeader;

	// Sandeep
	@FindBy(xpath = "//form[@id='reservationForm']/span[@class='button-container']/a[contains(text(),'Edit')]")
	private WebElement editBtn;

	@FindBy(xpath = "//form[@id='reservationForm']//span/a[@id='notes-add']")
	private WebElement noteBtn;

	@FindBy(xpath = "//div[@id='contactInformation']//input[@id='ContactModel_FirstName']")
	private WebElement cusInfo_FirstName;

	@FindBy(xpath = "//div[@id='contactInformation']//input[@id='ContactModel_LastName']")
	private WebElement cusInfo_LastName;

	@FindBy(xpath = "//a[@id='cancelButton']")
	private WebElement cancelBtn;

	@FindBy(xpath = "//form[@id='reservationForm']/span/a[contains(text(),'Apply')]")
	private WebElement applyBtn;

	@FindBy(xpath = "//a[text()='Rent']")
	private WebElement clk_RentBtn;
	
	@FindBy(xpath="//input[@id='ContactModel_Email_Email']")
	private WebElement email;

	@FindBy(xpath = "//div[@id='reservationPage']//div//a[contains(text(),'Back To Dashboard')]")
	private WebElement backToDashboardBtn;

	@FindBy(xpath = "//div[@id='contactInformation']//div[@class='reservation-contact-display toggle']//div[@class='customer-phone clearfix-container']/span/following-sibling::div//span[@class='phone-number margin-right']")
	private WebElement custinfo_PhoneNumber;

	@FindBy(xpath = "//div[@id='contactInformation']//div[@class='reservation-contact-display toggle']//div[@class='customer-email clearfix-container']//span[@class='email-address floatleft']")
	private WebElement custinfo_EmailId;

	@FindBy(xpath = "//div[@id='expectedMoveInDate']//span[@class='dual-datepicker-control']")
	private WebElement moveInDate;

	@FindBy(xpath = "//td[@class='grid-cell-space']//div[@class='unit-grid-link unit-grid-link-rental-unit']")
	private WebElement spaceNum;

	@FindBy(xpath = "//div[@class='reservation-contact']//div[@class='customer-name clearfix-container']/span[contains(@class,'full-name')]")
	private WebElement customerName;

	@FindBy(xpath = "//div[@id='header-logo-container']/a/img[@alt='Public Storage']")
	private WebElement publicStorageLogo;

	@FindBy(xpath = "//div[@id='reservationPage']//a[text()='Back To Dashboard']")
	private WebElement backToDashboard;

	@FindBy(xpath = "//div[@id='reservationPage']//a[text()='Restore Reservation']")
	private WebElement restoreReservation;

	@FindBy(xpath = "//div[@id='reservationPage']/div[4]/div[1]/span[contains(text(),'Cancel Reservation')]")
	private WebElement cancel_PagTitle;

	@FindBy(xpath = "//div[@id='reservationPage']/div[4]/div[2]/div[2]/div/a[2]")
	private WebElement cancelButton;

	@FindBy(xpath = "//a[contains(text(),'Confirm Reservation')]")
	private WebElement clk_confirmReservationButton;

	@FindBy(id = "createReservationButton")
	private WebElement clk_CreateReservationButton;

	@FindBy(id = "cancelButton")
	private WebElement clk_CancelReservationButton;

	@FindBy(xpath = "//div[@class='command-row']//a[text()='Back To Dashboard']")
	private WebElement clk_BackToDashBoard;

	@FindBy(xpath = "//form[@id='reservationForm']/span/a[contains(text(),'Edit')]")
	private WebElement edit_Btn;

	@FindBy(xpath = "//span[@class='float-right half-padding-left']/a[@id='notes-add']")
	private WebElement Note_Btn;

	@FindBy(xpath = "//div[@id='reservationPage']//form[@id='reservationForm']/span/a[contains(text(),'Apply')]")
	private WebElement Apply_Btn;

	@FindBy(xpath = "//div[@id='reservationPage']//form[@id='reservationForm']/span/a[contains(text(),'Cancel')]")
	private WebElement cancel_Btn;

	@FindBy(xpath = "//div[@class='reservation-contact']//span[text()='select']")
	private WebElement phone_DrpDwn;

	@FindBy(xpath = "//div[@id='ContactModel_Phone_PhoneTypeID-list']/ul/li[contains(text(),'Cell')]")
	private WebElement selVal_PhDrpDwn;

	@FindBy(id = "ContactModel_Phone_AreaCode")
	private WebElement areaCode;

	@FindBy(id = "ContactModel_Phone_Exchange")
	private WebElement exchange;

	@FindBy(id = "ContactModel_Phone_LineNumber")
	private WebElement lineNum;

	@FindBy(linkText = "Back To Dashboard")
	private WebElement backToDshBoard;
	
	@FindBy(xpath="//div[@id='reservationDates']//span[contains(@class,'move-in-date-display')]")
	private WebElement getMoveInDate;
	
	@FindBy(xpath="//div/b[text()='Reservation Status:']/following-sibling::span[contains(@class,'reservation-status')]")
	private WebElement getReservnStatus;
	
	@FindBy(xpath="//div[@id='reservationPage']//div[contains(@class,'reservation-info-bar')]/div/span[text()='Reservation Number:']/following-sibling::span")
	private WebElement resNum;
	
	@FindBy(xpath="//div[@id='contactInformation']//div[contains(@class,'customer-name')]/span[text()='Name:']/following-sibling::span")
	private WebElement resName;
	
	@FindBy(xpath="//div[@id='contactInformation']//div[contains(@class,'customer-phone')]/div/span[contains(@class,'phone-number')]")
	private WebElement resPhone;
	
	
	@FindBy(xpath="//div[@id='contactInformation']//div[contains(@class,'customer-email')]/span[contains(@class,'email-address')]")
	private WebElement resEmail;
	

	@FindBy(xpath="(//ul[contains(@class,'spaces-info-container')]//label[@class='webchamp-radio-button']/span[@class='button'])[1]")
	private WebElement selectSpace_Reassign;
	
	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement employeeId_Reassign;
	
	@FindBy(partialLinkText="Assign Selected Space")
	private WebElement assignSelectedSpace;
	
	@FindBy(partialLinkText="Reassign")
	private WebElement reassignButton;
	
	@FindBy(xpath="//span[contains(@class,'space-assignment-reason')]")
	private WebElement spaceAssignmentReason;
	
	@FindBy(xpath="//ul/li[@class='k-item']")
	private List<WebElement> reasons;

	public ViewReservationPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public String getResNum(){

		return resNum.getText();
	}
	
	public String getResName(){

		return resName.getText();
	}
	
	public String getResPhone(){

		return resPhone.getText();
	}
	
	public void assignSelectedSpace(){
		assignSelectedSpace.click();
	}
	
	public void click_ReassignButton(){
		reassignButton.click();
	}
	
	public void enterEmployeeId_Reassign(String text){
		employeeId_Reassign.sendKeys(text);
	}
	
	public void selectSpace_Reassign(){
		selectSpace_Reassign.click();
	}
	
	
	public void selectSpaceAssignmentReason(String text) throws InterruptedException{
		spaceAssignmentReason.click();
		Thread.sleep(3000);
		for(WebElement element: reasons){
			if(element.getText().contains(text)){
				element.click();
				break;
			}
		}
		
	}

	public WebElement cancel() {
		return cancelBtn;
	}

	public void clk_CancelBtn() {
		cancelBtn.click();
	}

	public String get_SpaceNnum() {
		return spaceNum.getText();
	}

	public boolean IsViewResDisplayed() {
		return ViewResHeader.isDisplayed();
	}

	public String getResInfo() {
		return resInfoBar.getText();
	}

	public String get_ReservationNumber() {
		String ResNo = Txt_ReservationNo.getText();
		return ResNo;
	}

	public String get_ViewReservationTitle() {
		return get_ViewReservationTitle.getText();
	}

	public boolean isDisplayedCancelTitle() {
		return cancel_PagTitle.isDisplayed();
	}

	public WebElement cancelReservationCancelBtn(){
		return cancelButton;
	}
	
	public void clk_CancelResBtn() {
		cancelButton.click();
	}

	public WebElement clk_EditBtnInSpaceInfo() {
		return EditBtnInSpaceInfo;
	}

	public void enterFirstName(String value) {
		cusInfo_FirstName.sendKeys(value);
	}

	public boolean verifyFirstNameField() {
		return cusInfo_FirstName.isEnabled();
	}

	public void enterLastName(String value) {
		cusInfo_LastName.sendKeys(value);
	}

	public boolean verifyEditBtn() {
		return editBtn.isDisplayed();
	}

	public boolean verifyCancelBtn() {
		return cancelBtn.isDisplayed();
	}

	public boolean verifyApplyBtn() {
		return applyBtn.isDisplayed();
	}

	public void clk_ApplyBtn() {
		applyBtn.click();
	}

	public boolean verifyNoteBtn() {
		return noteBtn.isDisplayed();
	}

	public String getCustInfo_PhoneNumber() {
		return custinfo_PhoneNumber.getText();
	}

	public String getCustInfo_EmailId() {
		return custinfo_EmailId.getText();
	}

	public void clk_EditBtn() {
		editBtn.click();
	}

	public WebElement btnMoveIndate() {
		return moveInDate;
	}

	public void clk_BackToDashboardBtn() {
		backToDashboardBtn.click();
	}

	public boolean IsCancelledResDisplayed() {
		return cancelledReservationHeader.isDisplayed();
	}

	public String getCustomerName() {
		return customerName.getText();
	}

	public void click_PublicStorageLogo() {
		publicStorageLogo.click();
	}

	public boolean backToDashboard_IsDisplayed() {
		return backToDashboard.isDisplayed();
	}

	public boolean restoreReservation_IsDisplayed() {
		return restoreReservation.isDisplayed();
	}

	public void clickRestoreResv() {
		restoreReservation.click();
	}

	public void clk_confirmReservationButton() {
		clk_confirmReservationButton.click();
	}

	public void clk_CreateReservationButton() {
		clk_CreateReservationButton.click();
	}

	public void clk_CancelReservationButton() {
		clk_CancelReservationButton.click();
	}

	public void clk_BackToDashBoard() {
		clk_BackToDashBoard.click();
	}

	public boolean verify_EditButton() {
		return edit_Btn.isDisplayed();
	}

	public boolean verify_NoteButton() {
		return Note_Btn.isDisplayed();
	}

	public boolean verify_ApplyButton() {
		return Apply_Btn.isDisplayed();
	}

	public boolean verify_CancelButton() {
		return cancel_Btn.isDisplayed();
	}

	public void clk_phDrpDwn() {
		phone_DrpDwn.click();
	}

	public void selValueFrmDpDwn() {
		selVal_PhDrpDwn.click();
	}

	public void txt_areaCode(String val) {
		areaCode.clear();
		areaCode.sendKeys(val);
	}

	public void txt_Exchange(String val) {
		exchange.clear();
		exchange.sendKeys(val);
	}

	public void txt_LineNum(String val) {
		lineNum.clear();
		lineNum.sendKeys(val);
	}

	public void clk_backToDshBoard() {
		backToDshBoard.click();
	}

	public boolean isViewReservationPageDisplayed() {
		return get_ViewReservationTitle.isDisplayed();
	}

	public void clk_RentBtn() {
		clk_RentBtn.click();
	}
	
	public String getReservationStatus(){
		
		return getReservnStatus.getText().trim();
	}
	
	
	public void clearEnterEmail(String text) throws InterruptedException{
		email.clear();
		Thread.sleep(2000);
		email.sendKeys(text);
	}
	public String getResEmail(){

		return resEmail.getText();
	}
	
	public String getMoveInDate(){
		String[] temp = getMoveInDate.getText().split(" ");
		String[] temp2 = temp[1].split("/");
		return temp2[2]+"-"+temp2[0]+"-"+temp2[1];
	}

}
