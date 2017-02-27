package Pages.Walkin_Reservation_Lease;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

public class CreateReservation {

	WebDriver driver;

	@FindBy(xpath = "//h3[text()='Create Reservation']")
	private WebElement createReservationtxt;

	@FindBy(xpath = "//label//span[text()='Does not have email']/preceding-sibling::span[@class='button']")
	private WebElement clk_DoesNtHvEmail;

	@FindBy(xpath = "//div//span[text()='Reservation Details']")
	private WebElement reservationDetailstxt;

	@FindBy(xpath = "//div[@class='floatright reservation-status-container']/b/following-sibling::span")
	private WebElement reservation_status;

	@FindBy(xpath = "//div/span[text()='Move In Date:']/following-sibling::div//span[text()='SELECT']")
	private WebElement mveInDate_SelLst;

	@FindBy(xpath = "//div//span[text()='Move In Time:']/following-sibling::div//span[text()='SELECT']")
	private WebElement mveInTime_Sellst;

	@FindBy(xpath = "//div//span[text()='Location Visit Date:']/following-sibling::div//span[text()='SELECT']")
	private WebElement locationVisitDate_SelLst;

	@FindBy(xpath = "//div//span[text()='Location Visit Time:']/following-sibling::div//span[text()='SELECT']")
	private WebElement locationVisitTime_SelLst;

	@FindBy(xpath = "//div//span[text()='Customer Information']")
	private WebElement customerInformationtxt;

	@FindBy(id = "ContactModel_FirstName")
	private WebElement firstName;

	@FindBy(id = "ContactModel_LastName")
	private WebElement lastName;

	@FindBy(xpath = "//span[text()='Phone:']/following-sibling::div/descendant::div[@class='floatleft']/span/span")
	private WebElement phone_SelLst;

	@FindBy(id = "ContactModel_FirstName")
	private WebElement get_ExtCustFirstName;

	@FindBy(id = "ContactModel_Phone_AreaCode")
	private WebElement phone_AreaCode;

	@FindBy(id = "ContactModel_Phone_Exchange")
	private WebElement phone_Exchnge;

	@FindBy(id = "ContactModel_Phone_LineNumber")
	private WebElement phone_LineNumber;

	@FindBy(id = "ContactModel_Email_Email")
	private WebElement email_Addr;

	@FindBy(xpath = "//label//span[text()='Does not have email']/preceding-sibling::input[@id='ContactModel_HasNoEmail']")
	private WebElement chk_doesNtHveEmail;

	@FindBy(xpath = "//div[@id='spaceInformation']//h2[text()='Space Information']")
	private WebElement get_SpaceInfomtiontext;

	@FindBy(id = "createReservationButton")
	private WebElement clk_CreateReservationButton;

	@FindBy(id = "cancelButton")
	private WebElement clk_CancelReservationButton;

	@FindBy(xpath = "//div[@class='command-row']//a[text()='Back To Dashboard']")
	private WebElement clk_BackToDashBoard;

	@FindBy(xpath = "//ul[@id='ContactModel_Phone_PhoneTypeID_listbox']/li")
	private List<WebElement> ls_PhNoType;

	@FindBy(id = "addDepositButton")
	private WebElement clk_AddDeposit;

	@FindBy(xpath = "//div[@id='SearchContract_ReservationNumber-wrapper']//input[@id='SearchContract_ReservationNumber']")
	private WebElement SearchContract_ReservationNumber;

	@FindBy(id = "SearchForTheReservation")
	private WebElement SearchForTheReservation;

	@FindBy(xpath = "//a[@class='edit-icon']")
	private WebElement edit_customer;

	@FindBy(id = "pendingConfirmationButton")
	private WebElement clk_Unconfirmed_Reservation;

	@FindBy(xpath = "//div[@id='expectedMoveInDate']//span[text()='SELECT']")
	private WebElement movindate;

	@FindBy(xpath = "//a[contains(text(),'Confirm Reservation')]")
	private WebElement clk_confirmReservationButton;
	
	
	@FindBy(xpath="//div[@class='move-in-subtotal floatright big-bold fixed-col']")
	private WebElement moveInsubtotalAmt;
	
	@FindBy(xpath="//div[@class='admin-fee-amount floatright fixed-col']")
	private WebElement getadminfeeamt;
	
	@FindBy(xpath="//div[@class='overall-total-move-in big-bold floatright fixed-col']")
	private WebElement getOveralltotalmoveinAmt;
	
	@FindBy(xpath="//div[@class='rent-subtotal floatright big-bold fixed-col']")
	private WebElement getRentsubtotalAmt;
	
	@FindBy(xpath="//div[@class='overall-total-rent big-bold floatright fixed-col']")
	private WebElement getOverAllTotalRentAmt;
	
	@FindBy(xpath="//td[contains(text(),'Reservation Confirmation')]")
	private WebElement res_email_section;
	
	@FindBy(xpath = "//div/span[text()='Move In Date:']/following-sibling::span")
	private WebElement mveInDate_displayed;
	

	@FindBy(xpath = "//div[@class='reservation-info-bar clearfix-container']//span[contains(text(),'Reservation Number:')]/following-sibling::span")
	private WebElement reservation_num;
	
	@FindBy(xpath = "//span[contains(text(),'Name:')]/following-sibling::span")
	private WebElement resname;
	
	@FindBy(xpath = "//span[@class='phone-number margin-right']")
	private WebElement resphone;
	
	@FindBy(xpath = "//span[contains(text(),'Email Address:')]/following-sibling::span")
	private WebElement resemail;
	
	
	

	public CreateReservation(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String get_createReservationtxt() {
		return createReservationtxt.getText();
	}

	public boolean isSelectedEmail() {
		return chk_doesNtHveEmail.isSelected();
	}

	public void clkEmailCheckBox() {
		clk_DoesNtHvEmail.click();
	}

	public String get_movindate() {
		return mveInDate_displayed.getText();
	}
	
	public String get_reservationnumber() {
		return reservation_num.getText();
	}
	
	public String get_reservationDetailstxt() {
		return reservationDetailstxt.getText();
	}

	public void enter_FirstName(String fname) {
		firstName.clear();
		firstName.sendKeys(fname);
	}

	public void enter_LastName(String lname) {
		lastName.clear();
		lastName.sendKeys(lname);
	}

	public void sel_DropDownVal(String value) {
		Select sel = new Select(phone_SelLst);
		sel.selectByVisibleText(value);
	}

	public void enter_PhoneAreaCode(String areacode) {
		phone_AreaCode.clear();
		phone_AreaCode.sendKeys(areacode);
	}

	public void enter_PhoneExchnge(String exchnge) {
		phone_Exchnge.clear();
		phone_Exchnge.sendKeys(exchnge);
	}

	public void enter_PhoneLineNumber(String linenum) {
		phone_LineNumber.clear();
		phone_LineNumber.sendKeys(linenum);
	}

	public void enter_EmailAddr(String email) {
		email_Addr.clear();
		email_Addr.sendKeys(email);
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

	public WebElement btnMoveIndate() {
		return mveInDate_SelLst;
	}

	public void SelectDateFromCalendar(String data) {
		try {
			int value = Integer.parseInt(data);
			Calendar c = GregorianCalendar.getInstance();
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, value);
			SimpleDateFormat df = new SimpleDateFormat("dd", Locale.getDefault());
			String strTodaysDate = df.format(cal.getTime());
			strTodaysDate = StringUtils.stripStart(strTodaysDate, "0");
			Reporter.log("strTodaysDate: " + strTodaysDate, true);
			Thread.sleep(2000);
			Calendar mCalendar = Calendar.getInstance();

			String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			mCalendar.add(Calendar.MONTH, 1);
			String nmonth = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			String ExpDay = strTodaysDate;

			String ActMonth = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[1]")).getText();
			String ActNextMonth = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[2]")).getText();
			if (ActMonth.equals(month) && ActNextMonth.equals(ActNextMonth)) {
				List<WebElement> AvlDays = driver.findElements(By.xpath("//td[@data-handler='selectDay']"));
				for (int i = 0; i < AvlDays.size(); i++) {
					String AvailableDay = AvlDays.get(i).getText();
					if (AvailableDay.equals(ExpDay)) {
						AvlDays.get(i).click();
						break;
					}
				}
			}

		} catch (Exception e) {
			Reporter.log("Exception:dateValidaitons" + e, true);
		}
	}

	public void sel_DropDownValFromPhNo(String value) throws InterruptedException {
		phone_SelLst.click();
		Thread.sleep(2000);
		List<WebElement> lsPhType = ls_PhNoType;
		for (int i = 0; i < lsPhType.size(); i++) {
			String PhType = lsPhType.get(i).getText().trim();
			Reporter.log("PhType:" + PhType, true);
			if (PhType.contains(value)) {
				lsPhType.get(i).click();
				break;
			}
		}

	}

	public void clk_AddDepositBtn() {
		clk_AddDeposit.click();
	}

	public WebElement btnLocationVisitDate() {
		return locationVisitDate_SelLst;
	}

	public void clk_edit() {
		edit_customer.click();
	}

	public void enter_SearchContract_ReservationNumber(String accNum) {
		SearchContract_ReservationNumber.sendKeys(accNum);
	}

	public void clk_SearchForTheReservation() {
		SearchForTheReservation.click();
	}

	public void clk_Accountlink() {
		driver.findElement(By.linkText("200000199")).click();
	}

	public boolean verify_createReservationHdrIsDisplayed() {
		return createReservationtxt.isDisplayed();
	}

	public String get_ExtCustName() {
		return get_ExtCustFirstName.getAttribute("value");
	}
	
	public String get_ExtCustLastName(){
		
		return lastName.getAttribute("value");
	}

	public void clk_movindate() {
		movindate.click();
	}

	public void clk_Unreservebutton() {
		clk_Unconfirmed_Reservation.click();
	}

	public void clk_confirmReservationButton() {
		clk_confirmReservationButton.click();
	}

	public boolean verify_CreatereservationPage() {
		return createReservationtxt.isDisplayed();
	}

	public void SelectDateFromCalendar_onemonth(String data) {
		try {
			int value = Integer.parseInt(data);
			Calendar c = GregorianCalendar.getInstance();
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, value);
			SimpleDateFormat df = new SimpleDateFormat("dd", Locale.getDefault());
			System.out.println("the date is----" + df);
			String strTodaysDate = df.format(cal.getTime());
			System.out.println("the date time is----" + strTodaysDate);
			strTodaysDate = StringUtils.stripStart(strTodaysDate, "0");
			Reporter.log("strTodaysDate: " + strTodaysDate, true);
			Thread.sleep(2000);
			Calendar mCalendar = Calendar.getInstance();

			String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			mCalendar.add(Calendar.MONTH, 1);
			String nmonth = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			String ExpDay = strTodaysDate;

			String ActMonth = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[1]")).getText();

			if (ActMonth.equals(month)) {
				List<WebElement> AvlDays = driver.findElements(By.xpath("//td[@data-handler='selectDay']"));
				for (int i = 0; i < AvlDays.size(); i++) {
					String AvailableDay = AvlDays.get(i).getText();
					if (AvailableDay.equals(ExpDay)) {
						AvlDays.get(i).click();
						break;
					}
				}
			}

		} catch (Exception e) {
			Reporter.log("Exception:dateValidaitons" + e, true);
		}
	}

	public String gettext_reservation_status() {
		return reservation_status.getText();
	}
	
	
	public boolean verify_resemailIsDisplayed() {
		return res_email_section.isDisplayed();
	}
	
	public String getMoveInsubtotalAmount(){
		
		return moveInsubtotalAmt.getText().trim();
	}
	
	public String getAdminfeeamount(){
		
		return getadminfeeamt.getText().trim();
	}
	
	public String getOverallTotalMoveInAmount(){
		
		return getOveralltotalmoveinAmt.getText().trim();
	}
	
	public String getRentSubTotalAmount()
	{
		
		return getRentsubtotalAmt.getText().trim();
	}
	
	public String getOverAllTotalRentAmount(){
		
		return getOverAllTotalRentAmt.getText().trim();
	}
	
	public String get_resname() {
		return resname.getText();
	}
	
	public String get_resphone() {
		return resphone.getText();
	}
	
	public String get_resemail() {
		return resemail.getText();
	}
	
	public String getLastName(){
		return lastName.getAttribute("value");
	}

}
