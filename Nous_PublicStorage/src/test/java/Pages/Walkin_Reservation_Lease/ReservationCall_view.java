package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReservationCall_view {
	
	WebDriver driver;
	@FindBy(xpath="//a[@href='/Reservation/ReservationList']")
	private WebElement reservation_icon;
	
	
	
	
	public ReservationCall_view(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}

	public void clk_reservation_icon(){
		reservation_icon.click();
	}
	
	public void clk_reservation_accnum(String accnum){
		
		driver.findElement(By.xpath("//div[@id='reservationGrid']//tr//td[contains(text(),'"+accnum+"')]")).click();
	}
	
	
}
