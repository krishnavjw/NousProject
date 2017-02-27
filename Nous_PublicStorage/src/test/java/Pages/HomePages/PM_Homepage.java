package Pages.HomePages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class PM_Homepage {

	WebDriver driver;
	@FindBy(xpath = "//h1[text()='Walk-In Customer']")
	private WebElement walkIn_CustomerHeader;

	@FindBy(xpath = "//span[text()='Find & Lease A Space']")
	private WebElement findAndLeaseSpace;

	
	
	@FindBy(xpath = "//span[contains(@class,'header-location-nickname')]")
	private WebElement Txt_SiteNo;
	
	@FindBy(id = "findReservation")
	private WebElement enterNameOrResvtn;

	@FindBy(xpath = "//span[@class='header-location-nickname']")
	private WebElement header_Location;

	@FindBy(xpath = "//form[@id='quickFindReservationForm']//span[text()='Find Reservation']")
	private WebElement findReservation;

	@FindBy(xpath = "//a[contains(text(),'Buy Merchandise')]")
	private WebElement buy_Merchandise;

	@FindBy(xpath = "//h1[text()='Existing Customer']")
	private WebElement existing_Customer;

	@FindBy(id = "SearchString")
	private WebElement findCustAddrLocation;

	@FindBy(xpath = "//span[text()='Find Customer']")
	private WebElement findCustomer;

	@FindBy(linkText = "Advanced Search")
	private WebElement advancedSearchLnk;

	
	@FindBy(xpath = "//div[text()='Find a Customer at this Location:']")
	private WebElement txt_findACustomerAtThisLoc;

	@FindBy(xpath = "//div[@id='merchpanel']/h1")
	private WebElement inProgrss_Title;

	@FindBy(xpath = "//div[@id='newcustomerpanel']/h1[text()='Walk-In Customer']")
	private WebElement heading_WalkInCustomer;

	@FindBy(xpath = "//div[@class='notification alert k-notification-wrap']")
	private WebElement notificationAlert;

	@FindBy(xpath = "//div[@id='reservations']//b[text()='No items to display ']")
	private WebElement getnoitemsToBeDisplyed_UnderReservnCallModel;

	@FindBy(xpath = "//div[@class='dashboard']//div[@id='reservationCalls']/a")
	private WebElement viewAllRes_Icon;

	@FindBy(xpath = "//div[@class='appointment-list']//div[@class='k-grid-content ps-container']")
	private WebElement appointments;

	@FindBy(xpath = "//div[@id='searchpanel']/a")
	private WebElement DM_advancedSearchLnk;
	
	@FindBy(xpath="//ul[@id='utilitybar-wrapper']//li//span[text()='Manage Property']")
	private WebElement manageProp_link;
	
	
	@FindBy(xpath="//a[@href='/PropertyManagement']/span")
	private WebElement PM_PropertyManagement_link;
	
	
	@FindBy(xpath="//div[@class='dashboard-icon-table']/ul/li/a/span[contains(text(),'Manage Property')]")
	 private WebElement ManageProperty;
	

@FindBy(xpath="//span[text()='Email']")
	private WebElement emailLink;

@FindBy(xpath="//span[contains(text(),'Calendar')]/parent::a")
private WebElement viewCalendarIcon;
	

	public PM_Homepage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String get_WlkInCustText() {

		return walkIn_CustomerHeader.getText();
	}

	public void clk_findAndLeaseSpace() {

		findAndLeaseSpace.click();
	}

	public void clk_findReservation() {
		findReservation.click();
	}

	public void enter_NameOrResvtn(String value) {
		enterNameOrResvtn.sendKeys(value);
	}

	public void clk_BuyMerchandiseLnk() {

		buy_Merchandise.click();
	}

	public String get_existingCustomerText() {
		return existing_Customer.getText();
	}

	public void enter_findCustAddrLocation(String value) {

		findCustAddrLocation.sendKeys(value);
	}

	public void clk_findCustomer() {

		findCustomer.click();
	}

	public void clk_AdvSearchLnk() {

		advancedSearchLnk.click();
	}

	/**
	 * 
	 * BRAJESH
	 */

	public String get_findACustomerAtThisLocText() {

		return txt_findACustomerAtThisLoc.getText();
	}

	public String get_findACustomerText() {

		return findCustomer.getText();
	}

	/**
	 * 
	 * Basavaraj/Rekha
	 */

	public String get_InprogressText() {
		return inProgrss_Title.getText();
	}

	public void clk_HoldBtnOnInprogessModule(String spaceNo) {

		driver.findElement(By.xpath("//td[text()='" + spaceNo + "']/preceding-sibling::td/a")).click();
	}
	
	public boolean IsHoldLinkPresentAgainstSpaceNum(String spaceNo){
		
		return driver.findElement(By.xpath("//td[text()='" + spaceNo + "']/preceding-sibling::td/a")).isDisplayed();
	}

	public String get_NoItemsToBeDisplyed_UnderReservnCallModel() {

		return getnoitemsToBeDisplyed_UnderReservnCallModel.getText();

	}

	public void click_ViewAllResIcon() throws InterruptedException {
		Actions action = new Actions(driver);
		action.moveToElement(viewAllRes_Icon).build().perform();
		Thread.sleep(2000);
		viewAllRes_Icon.click();
	}

	public boolean isexistingCustomerModelDisplayed() {

		return existing_Customer.isDisplayed();
	}

	public String getLocation() {
		String location = header_Location.getText();
		location = (location.split("-"))[0].trim();
		return location;
	}

	public boolean isFindAnExtReservationTextFieldDisplayed() {

		return enterNameOrResvtn.isDisplayed();
	}

	public boolean isFindReservationDispalyed() {

		return findReservation.isDisplayed();
	}

	public String getAppointments() {
		return appointments.getText();
	}

	public boolean isFindAndLeaseButtonDisplayed() {

		return findAndLeaseSpace.isDisplayed();
	}

	public boolean heading_WalkInCustomer_Exists() {
		return heading_WalkInCustomer.isDisplayed();
	}

	public boolean verify_findAndLeaseSpace() {
		return findAndLeaseSpace.isDisplayed();
	}

	public void clk_DMAdvSearchLnk() {

		DM_advancedSearchLnk.click();
	}

	public String getInnerText_EnterNameOrResvtn() {
		return enterNameOrResvtn.getAttribute("placeholder");
	}

	public boolean IsElemPresent_FindReservation() {
		return findReservation.isDisplayed();
	}

	public String getNotificationAlert() {
		return notificationAlert.getText();
	}
	
	public void clk_PM_PropertyManagement_link(){

		PM_PropertyManagement_link.click();
	}
	 //===================Anjana===============================
	 @FindBy(xpath="//div[@class='dashboard-icon-table']/ul/li/a/span[contains(text(),'Merchandise Returns')]")
	 private WebElement MerchandiseReturn;
	 
		public void clk_Merchand_RetLink(){

			MerchandiseReturn.click();
		}
		
		 @FindBy(xpath="//input[@id='receiptId']")
		 private WebElement Receiptnumber;
	 
		 public void enter_Receiptnumber(String number){

			 Receiptnumber.sendKeys(number);
			
			}
		
		 @FindBy(xpath="//a[contains(text(),'Find')]")
		 private WebElement Find_btn;
		 
			public void clk_Find_btn(){

				Find_btn.click();
			}
			
	 public void clickmanageProp() {
		manageProp_link.click();
	 }		
	
	 public void clk_ManageProperty(){

		 ManageProperty.click();
		}	
	 
		
	 public void Clk_ViewCalendar(){
		 viewCalendarIcon.click();
	 }
	 

	 public String get_SiteNumber() {
			String[] arr = Txt_SiteNo.getText().split("-");
			Reporter.log("Location number is: " + arr[0].trim(), true);
			return arr[0].trim();
		}



public void click_EmailLink() {
		
		emailLink.click();
		
	}


public void logout(WebDriver driver) throws InterruptedException{
		Actions act = new Actions(driver);
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		WebElement user = driver.findElement(By.xpath(("//div[@id='usernav']")));
		WebElement logoff1 = driver.findElement(By.xpath("//a[contains(text(),'Log Off')]"));

		act.moveToElement(user).build().perform();
		jse.executeScript("arguments[0].click();", logoff1);
		Thread.sleep(5000);

		WebElement logoff2 = driver.findElement(
		By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'Log Off')]"));
		jse.executeScript("arguments[0].click();", logoff2);
	
		Thread.sleep(8000);
		
	}
		 

}
