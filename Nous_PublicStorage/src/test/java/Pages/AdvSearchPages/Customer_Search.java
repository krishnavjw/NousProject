package Pages.AdvSearchPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Customer_Search {

	WebDriver driver;
	@FindBy(id="SearchContract_ReservationNumber")
	private WebElement SearchContract_ReservationNumber;

	@FindBy(id="SearchForTheReservation")
	private WebElement SearchForTheReservation;
	
	
	
	public Customer_Search(WebDriver driver){

		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	public void clk_SearchForTheReservation(){
		SearchForTheReservation.click();
	}
	
	public void enter_SearchContract_ReservationNumber(String accNum){
		SearchContract_ReservationNumber.sendKeys(accNum);

	}
	
	public void clk_Accountlink(String regNum){
		driver.findElement(By.linkText(regNum)).click();
	}
	
	
}
