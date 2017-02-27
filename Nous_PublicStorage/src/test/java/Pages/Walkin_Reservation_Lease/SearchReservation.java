package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchReservation {

	public SearchReservation(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	
	@FindBy(xpath="//form[@id='advancedSearchReservation']//div[@id='SearchContract_PropertyNumber-wrapper']/input[@id='SearchContract_PropertyNumber']")
	private WebElement search_LocationNumber;
	
	@FindBy(id="SearchContract_ReservationNumber")
	private WebElement txt_Reservation;
	
	@FindBy(id="SearchForTheReservation")
	private WebElement search_btn_res;
	
	
	public String getLocationNum()
	{
		return search_LocationNumber.getAttribute("value");
	}
	
	public void setLocation(String loc){
		search_LocationNumber.clear();
		search_LocationNumber.sendKeys(loc);
	}
	
	public void enterReservationNo(String fn)
	{
		txt_Reservation.sendKeys(fn);
	}
	
	public void clickSearchbtn_Res()
	{
		search_btn_res.click();
	}
	
}
