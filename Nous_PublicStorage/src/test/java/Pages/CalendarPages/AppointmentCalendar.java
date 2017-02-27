package Pages.CalendarPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AppointmentCalendar {
	
	@FindBy(xpath="//h3[contains(text(),'Appointment Calendar')]")
	private WebElement titleAppCalendar;
	
	@FindBy(xpath="//span[text()='Add New Appointment']/../../a[@class='psbutton-priority gradient float-right addappt']")
	private WebElement addNewAppointment;
	
	@FindBy(xpath="//div[@id='weekHeading']/span[@class='yearmonday']")
	private WebElement weekActive; //div[@class='outerDiv']//div[@id='apptabstrip']/ul/li[@class='k-item k-state-default k-tab-on-top k-state-active']/span[contains(text(),'Week')]
	
	
	@FindBy(xpath="//div[@class='WeekView']//span[contains(text(),'Movein')]/../span[@class='button']")
	private WebElement movein_ChkBox;
	
	@FindBy(xpath="//div[@class='WeekView']//span[contains(text(),'Auction')]/../span[@class='button']")
	private WebElement auction_ChkBox;
	
	@FindBy(xpath="//div[@class='WeekView']//span[contains(text(),'Vacate')]/../span[@class='button']")
	private WebElement vacate_ChkBox;
	
	@FindBy(xpath="//div[@class='WeekView']//span[contains(text(),'Loc Visit')]/../span[@class='button']")
	private WebElement locVisit_ChkBox;
	
	@FindBy(xpath="//div[@class='WeekView']//span[contains(text(),'Payment Commitment')]/../span[@class='button']")
	private WebElement paymentCommit_ChkBox;
	
	@FindBy(xpath="//div[@class='WeekView']//span[contains(text(),'User Generated')]/../span[@class='button']")
	private WebElement userGenerated_ChkBox;
	
	@FindBy(xpath="//div[@class='WeekView']//span[contains(text(),'Pre Move In')]/../span[@class='button']")
	private WebElement preMoveIn_ChkBox;
	
	@FindBy(xpath="//div[@class='WeekView']//span[contains(text(),'PTP')]/../span[@class='button']")
	private WebElement ptp_ChkBox;
	
	@FindBy(xpath="//div[@class='WeekView']//span[contains(text(),'Custom')]/../span[@class='button']")
	private WebElement custom_ChkBox;
	
	
	@FindBy(xpath="//div[@id='apptabstrip']//span[contains(text(),'Month')]")
	private WebElement selMonth;
	
	@FindBy(xpath="//div[@id='apptabstrip']//span[contains(text(),'Week')]")
	private WebElement selWeek;
	
	@FindBy(xpath="//div[@id='apptabstrip']//span[contains(text(),'Day')]")
	private WebElement selDay;
	
	
	
	public AppointmentCalendar(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	
	public boolean isDisplayed_Title(){
		return titleAppCalendar.isDisplayed();
	}
	
	public String getTitle(){
		return titleAppCalendar.getText();
	}
	
	public void clk_AddNewAppointment(){
		addNewAppointment.click();
	}
	
	public boolean isDisplayed_AddNewAppointment(){
		return addNewAppointment.isDisplayed();
	}
	
	public boolean isDispalyed_WeekActive(){
		return weekActive.isDisplayed();
	}
	
	public void clk_MoveInChkBox(){
		movein_ChkBox.click();
	}
	
	public void clk_AuctionChkBox(){
			auction_ChkBox.click();
	}
	
	public void clk_VacateChkBox(){
			vacate_ChkBox.click();
	}
	
	public void clk_LocVisitChkBox(){
			locVisit_ChkBox.click();
	}
	
	public void clk_PaymentChkBox(){
			paymentCommit_ChkBox.click();
	}
	
	public void clk_UsergeneratedChkBox(){
			userGenerated_ChkBox.click();
	}
	
	public void clk_preMoveinchkBox(){
		preMoveIn_ChkBox.click();
	}
	
	public void clk_PTPChkBox(){
		ptp_ChkBox.click();
	}
	
	public void clk_CustomChKBox(){
		custom_ChkBox.click();
	}
	
	
	public void clk_MonthView(){
		selMonth.click();
	}
	
	public void clk_WeekView(){
		selWeek.click();
	}
	
	public void clk_DayView(){
		selDay.click();
	}
	
}
