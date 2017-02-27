package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Swap_VehicleStroagePage {

	@FindBy(xpath="//span[contains(text(),'Car / SUV / Truck')]/../../span[@class='button']")
	private WebElement ChkBx_Car;
	
	@FindBy(xpath="//a[@id='search-button']")
	private WebElement btn_Search;
	
	
	@FindBy(id="swapSpaceButton")
	private WebElement btn_SwapSpace;
	
	
	
	public Swap_VehicleStroagePage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	 public void Clk_ChkBx_Car()
	 {
		 ChkBx_Car.click();
	 }
	 
	 public void Clk_btn_Search()
	 {
		 btn_Search.click();
	 }
	 
	 public void clk_btn_SwapSpace(){
		 
		 btn_SwapSpace.click();
		 
	 }
	 
	 
	 
}
