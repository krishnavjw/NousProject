package Pages.Walkin_Reservation_Lease;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class StandardStoragePage {

	private WebDriver driver;
	JavascriptExecutor jse;
	Actions builder;
	@FindBy(xpath = "//div[@id='choose-size-search-type']/ul/li[1]/span[2]")
	private WebElement StandardStorage;

	@FindBy(xpath = "//label[text()='Select Space By Size']")
	private WebElement btn_selectspacebysize;

	@FindBy(xpath = "//div[@class='onoffswitch']/label")
	private WebElement taggal_button;

	@FindBy(xpath = "//label[text()='Use Size Estimator']")
	private WebElement btn_UseSizeEstimator;

	@FindBy(xpath = "//div[@id='choose-size-search-type']/ul/li[2]/span[2]")
	private WebElement VehicleStorage;

	@FindBy(xpath = "//div[@id='choose-size-search-type']/ul/li[3]/span[2]")
	private WebElement SpecificStorage;

	@FindBy(xpath = "//span[text()='Climate Control']/preceding-sibling::span[@class='button']")
	private WebElement climate_control;

	@FindBy(xpath = "//span[text()='Ground Floor']/preceding-sibling::span[@class='button']")
	private WebElement ground_floor;

	@FindBy(xpath = "//span[text()='Drive-Up Access']/preceding-sibling::span[@class='button']")
	private WebElement driveup_access;

	@FindBy(xpath = "//label[@id='NotExistingCustomer']/span[text()='Yes']/preceding-sibling::span[@class='button']")
	private WebElement radio_newCust_yes;

	@FindBy(xpath = "//label[@id='IsExistingCustomer']/span[text()='No']/preceding-sibling::span[@class='button']")
	private WebElement radio_newCust_no;

	@FindBy(linkText = "Back To Dashboard")
	private WebElement back_dashboard;

	@FindBy(linkText = "Reset")
	private WebElement reset_btn;

	@FindBy(linkText = "Search")
	private WebElement search_btn;

	@FindBy(xpath = "//div[input[@id='standard-storage-input']]")
	private WebElement size_estimator;

	@FindBy(xpath = "//span[text()='25']/../../../../span[@class='button']")
	private WebElement SqFt25;

	@FindBy(xpath = "//span[text()='50']/../../../../span[@class='button']")
	private WebElement SqFt50;

	@FindBy(xpath = "//span[text()='75']/../../../../span[@class='button']")
	private WebElement SqFt75;

	@FindBy(xpath = "//span[text()='100']/../../../../span[@class='button']")
	private WebElement SqFt100;

	@FindBy(xpath = "//span[text()='150']/../../../../span[@class='button']")
	private WebElement SqFt150;

	@FindBy(xpath = "//span[text()='200']/../../../../span[@class='button']")
	private WebElement SqFt200;

	@FindBy(xpath = "//span[text()='250']/../../../../span[@class='button']")
	private WebElement SqFt250;

	@FindBy(xpath = "//span[text()='300']/../../../../span[@class='button']")
	private WebElement SqFt300;

	@FindBy(xpath = "//span[text()='Studio Apartment']/preceding-sibling::span[@class='button']")
	private WebElement studio_apartment;

	@FindBy(xpath = "//span[text()='1 Bedroom']/preceding-sibling::span[@class='button']")
	private WebElement bedroom1;

	@FindBy(xpath = "//span[text()='2 Bedrooms']/preceding-sibling::span[@class='button']")
	private WebElement bedrooms2;

	@FindBy(xpath = "//span[text()='3 Bedrooms']/preceding-sibling::span[@class='button']")
	private WebElement bedrooms3;

	@FindBy(xpath = "//span[text()='4 Bedrooms']/preceding-sibling::span[@class='button']")
	private WebElement bedrooms4;

	@FindBy(xpath = "//span[text()='5+ Bedrooms']/preceding-sibling::span[@class='button']")
	private WebElement bedrooms5;

	@FindBy(xpath = "//span[text()='Appliances']/preceding-sibling::span[@class='button']")
	private WebElement appliances;

	@FindBy(xpath = "//span[@class='leftalignedinlineblockspan dimensions']")
	private List<WebElement> Txt_AvlSpaces;

	@FindBy(xpath = "//span[text()='Basement Items']/preceding-sibling::span[@class='button']")
	private WebElement basement_items;

	@FindBy(xpath = "//span[text()='Garage Items']/preceding-sibling::span[@class='button']")
	private WebElement garage_items;

	@FindBy(xpath = "//span[text()='Attic Items']/preceding-sibling::span[@class='button']")
	private WebElement attic_items;

	@FindBy(xpath = "//div[@id='customerscreenandscriptpanel']/ul/li[1]/span[2]")
	private WebElement script;

	@FindBy(xpath = "//div[@id='customerscreenandscriptpanel']/ul/li[2]/span[2]")
	private WebElement Customer_Screen;

	@FindBy(xpath = "//div//li//span[contains(text(),'Other Locations')]")
	private WebElement clk_Other_locations;

	@FindBy(xpath = "//div//li//span[contains(text(),'At This Location')]")
	private WebElement clk_This_locations;

	@FindBy(xpath = "//input[@ id='mattress-size-false']/following-sibling::span[@class='button']")
	private WebElement radio_matress_no;

	@FindBy(xpath = "//input[@id='mattress-size-true']/following-sibling::span[@class='button']")
	private WebElement radio_matress_yes;

	@FindBy(linkText = "Cancel")
	private WebElement cancel_btn;

	@FindBy(xpath = "	//div[@id='search']//label[@id='IsExistingCustomer']//span[contains(text(),'No')]/preceding-sibling::span[@class='button']")
	private WebElement newCustomer_NoRadio_Btn;

	@FindBy(xpath = "(//div[@id='select-space-size']//span[@class='leftalignedinlineblockspan dimensions'])/../../preceding-sibling::span")
	private WebElement ChkBx_AvlSpace;
	
	
	@FindBy(xpath = "(//div[@id='select-space-size']//span[@class='leftalignedinlineblockspan dimensions'])[1]")
	private WebElement size_AvlSpace;

	@FindBy(xpath = "//h4[text()='Select a Space Size']")
	private WebElement Hdr_StdStorage;

	@FindBy(xpath = "//div[@class='notification alert k-notification-wrap']")
	private WebElement InfoMsg_SpaceSize;

	@FindBy(xpath = "//label[@id='NotExistingCustomer']/span[text()='Yes']/preceding-sibling::input[@id='NotExistingCustomer']")
	private WebElement RdBtn_newCust_yes;

	@FindBy(xpath = "(//div[@id='select-space-size']//span[@class='leftalignedinlineblockspan dimensions'])[2]/../../preceding-sibling::span")
	private WebElement ChkBx_AvlSpace2;

	@FindBy(xpath = "(//div[@id='select-space-size']//span[@class='leftalignedinlineblockspan dimensions'])[3]/../../preceding-sibling::span")
	private WebElement ChkBx_AvlSpace3;

	@FindBy(xpath = "//label[@id='IsExistingCustomer']//span[text()='No']/preceding-sibling::input[@id='IsExistingCustomer']")
	private WebElement RdBtn_ExtCust_yes;

	@FindBys(value = { @FindBy(xpath = "//span[@class='leftalignedinlineblockspan dimensions']") })
	private List<WebElement> ChkBx_boldAvlSpace;
	
	@FindBy(xpath="//span[contains(text(),'Vehicle Storage')]")
	private WebElement clk_VechcleStroageBtn;
	
	
	@FindBy(xpath="//input[@id='txtSpecificUnit']")
	private WebElement textspaceunit;

	public StandardStoragePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void Clk_OnAvlSpace() throws InterruptedException {

		/*for (int i = 0; i < checkbox; i++) {
			if (availableChkbxSize == 1) {
				ChkBx_boldAvlSpace.get(checkbox).click();
				break;
			} else if (availableChkbxSize >= 1 && availableChkbxSize <= 2) {
				for (int j = 0; j < 2; j++) {
					ChkBx_boldAvlSpace.get(checkbox).click();
				}
				break;
			}
		}*/
		
		int availableChkbxSize = ChkBx_boldAvlSpace.size();
		
		for(int i=0;i<availableChkbxSize;i++){
			ChkBx_boldAvlSpace.get(i).click();
			Thread.sleep(500);
		}
	}

	public boolean isdisplayed_bedroom1() {
		return bedroom1.isDisplayed();
	}

	public boolean isdisplayed_yesradiobutton() {
		return radio_newCust_yes.isDisplayed();
	}

	public boolean isdisplayed_noradiobutton() {
		return radio_newCust_no.isDisplayed();
	}

	public boolean isdisplayed_Searchbutton() {
		return search_btn.isDisplayed();
	}

	public boolean isdisplayed_resetbutton() {
		return reset_btn.isDisplayed();
	}

	public boolean isdisplayed_cancelbutton() {
		return cancel_btn.isDisplayed();
	}

	public String getSignInPageTitle() {
		String pageTitle = driver.getTitle();
		return pageTitle;
	}

	public void click_StandardStorage() {
		StandardStorage.click();
	}

	public void click_VehicleStorage() {
		clk_VechcleStroageBtn.click();
	}

	public void click_SpecificSpace() {
		SpecificStorage.click();
	}

	public void click_ClimateControl() {
		climate_control.click();
	}

	public void click_GroundFloor() {
		ground_floor.click();
	}

	public void click_DriveupAccess() {
		driveup_access.click();
	}

	public void click_Yes_NewCust() {
		radio_newCust_yes.click();
	}

	public void click_No_NewCust() {
		radio_newCust_no.click();
	}

	public void click_BackToDashboard() {
		back_dashboard.click();
	}

	public void click_Reset() {
		reset_btn.click();
	}

	public void click_Search() {
		search_btn.click();
	}

	public void click_SizeEstimator() {
		size_estimator.click();
	}

	public void click_SqFt25() {
		SqFt25.click();
	}

	public void click_SqFt50() {
		SqFt50.click();
	}

	public void click_SqFt75() {
		SqFt75.click();
	}

	public void click_SqFt100() {
		SqFt100.click();
	}

	public void click_SqFt150() {
		SqFt150.click();
	}

	public void click_SqFt200() {
		SqFt200.click();
	}

	public void click_SqFt250() {
		SqFt250.click();
	}

	public void click_SqFt300() {
		SqFt300.click();
	}

	public void click_StudioAptmnt() {
		studio_apartment.click();
	}

	public void click_BedRoom1() {
		bedroom1.click();
	}

	public void click_BedRooms2() {
		bedrooms2.click();
	}

	public void click_BedRooms3() {
		bedrooms3.click();
	}

	public void click_BedRooms4() {
		bedrooms4.click();
	}

	public void click_BedRooms5() {
		bedrooms5.click();
	}

	public void click_Appliances() {
		appliances.click();
	}

	public void click_BasementItems() {
		basement_items.click();
	}

	public void click_GarageItems() {
		garage_items.click();
	}

	public void click_AtticItems() {
		attic_items.click();
	}

	public boolean verifypagetitle() {
		String expectedTitle = "Walk-In: Find a Space";
		return getSignInPageTitle().contains(expectedTitle);
	}

	public boolean isdisplayed_StandardStorage() {
		return StandardStorage.isDisplayed();
	}

	public boolean isdisplayed_VehicleStorage() {
		return VehicleStorage.isDisplayed();
	}

	public boolean isdisplayed_SpecificStorage() {
		return SpecificStorage.isDisplayed();
	}

	public boolean isdisplayed_script() {
		return script.isDisplayed();
	}

	public boolean isdisplayed_Customer_Screen() {
		return Customer_Screen.isDisplayed();
	}

	public boolean isdisplayed_btn_selectspacebysize() {
		return btn_selectspacebysize.isDisplayed();
	}

	public boolean isdisplayed_btn_UseSizeEstimator() {
		return btn_UseSizeEstimator.isDisplayed();
	}

	public String get_StandardStorageTxt() {
		return StandardStorage.getText();
	}

	public void clk_OtherLocations() {
		clk_Other_locations.click();
	}

	public void clk_ThisLocations() {
		clk_This_locations.click();
	}

	public void clk_radio_matress_no() {
		radio_matress_no.click();
	}

	public void click_tagglebtn() {
		taggal_button.click();
	}

	public void clk_radio_matress_yes() {
		radio_matress_yes.click();
	}

	public boolean isdisplayed_tableheader() {
		return driver.findElement(By.xpath("//tr[@role='row']/th[contains(text(),'Size')]")).isDisplayed();
	}

	public void click_cancel() {
		cancel_btn.click();
	}

	public void Clk_ChkBx_AvlSpace() {
		ChkBx_AvlSpace.click();
	}
	
	public String getAvlSpace_Size()
	{
		return size_AvlSpace.getText();
	}

	public void clk_NewCustomerNoRadioBtn() {
		newCustomer_NoRadio_Btn.click();
	}

	public boolean isSelected_ExtCustYesRadioButton() {
		return RdBtn_ExtCust_yes.isSelected();
	}

	public void ClickOnAvlSpaces() {
		List<WebElement> ls = Txt_AvlSpaces;
		for (int i = 0; i < ls.size(); i++) {
			Txt_AvlSpaces.get(i).click();
		}
	}
	
	

	public boolean verify_StdStorageHdr() {
		return Hdr_StdStorage.isDisplayed();
	}

	public WebElement verifySpaceSizeNotificationMsg() {
		return InfoMsg_SpaceSize;
	}

	public boolean isSelected_yesradiobutton() {
		return RdBtn_newCust_yes.isSelected();
	}

	public void Clk_ChkBx_AvlSpace2() {
		ChkBx_AvlSpace2.click();
	}

	public void Clk_ChkBx_AvlSpace3() {
		ChkBx_AvlSpace3.click();
	}

	public void selectAllSpaceSizesChecboxes(WebDriver driver) throws InterruptedException {
		List<WebElement> l = driver
				.findElements(By.xpath("//div[@id='divPersonalSizes']/ul/li/label/span[@class='button']"));
		builder = new Actions(driver);

		for (int i = 1; i <= l.size(); i++) {
			String xpath = "//div[@id='divPersonalSizes']/ul/li[" + i + "]/label/span[@class='button']";
			WebElement checkbox = driver.findElement(By.xpath(xpath));
			builder.moveToElement(checkbox).click(checkbox).build().perform();
			Thread.sleep(500);
		}

	}
	
	
	public void enter_spaceunit(String value) {
		textspaceunit.sendKeys(value);
	}
	

}
