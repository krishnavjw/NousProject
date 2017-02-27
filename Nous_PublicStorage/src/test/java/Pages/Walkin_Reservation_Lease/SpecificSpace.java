package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SpecificSpace {

	@FindBy(id="txtSpecificUnit")
	private WebElement spaceNum;
	
/*##############Rekha###############################*/
	
	@FindBy(xpath="//a[@id='search-button']")
	private WebElement Btn_Search;
	
	@FindBy(xpath="//div[@class='notification alert k-notification-wrap']")
	private WebElement Txt_NotifiAlrt;
	
	@FindBy(xpath="//b[text()='No Search Results at this location.']")
	private WebElement Txt_NoRes;
	
	@FindBy(xpath="//a[@id='lnkReserve']")
	private WebElement Lnk_Reserve;
	
	@FindBy(xpath="//a[@id='lnkRentNow']")
	private WebElement Lnk_Rent;
	
	@FindBy(xpath="//a[@id='lnkHoldAndShowUnits']")
	private WebElement Lnk_Hold;
	
	@FindBy(xpath="//div[@id='choose-size-search-type']/ul/li[3]/span[2]")
	private WebElement specificSpace_Tab;
	
	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement Btn_CancelHold;
	
	public SpecificSpace(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}

	
	public void verify_defaultValue(){
		spaceNum.getAttribute("placeholder");
	}
	

	/*##############Rekha###############################*/
	
	public void Clk_SearchBtn(){
		Btn_Search.click();
	}
	
	public void enter_SpaceNum(String text){
		spaceNum.clear();
		spaceNum.sendKeys(text);
	}
	
	public String verify_NotificationMsg(){
		return Txt_NotifiAlrt.getText();
	}
	
	public boolean verify_InfoMsg_NoSrchResult()
	{
		return Txt_NoRes.isDisplayed();
	}
	
	public void Clk_ReserveBtn(){
		Lnk_Reserve.click();
	}
	
	public void Clk_RentBtn(){
		Lnk_Rent.click();
	}
	
	public void Clk_HoldBtn(){
		Lnk_Hold.click();
	}
	
	public void clk_HoldCancelBtn()
	{
		Btn_CancelHold.click();
	}
	

	public boolean isdisplayed_SpecificSpaceTab(){
		return specificSpace_Tab.isDisplayed();
	}
	
}
