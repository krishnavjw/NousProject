package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Leasing_EligiblePromotionsPage 
{
	@FindBy(id="customerSaveButton")
	private WebElement savenProceed_btn;
	
	@FindBy(xpath="//span[@class='monthly monthlyRentTotal']")
	private WebElement get_SubTotalMonthlyRentAmt;

	@FindBy(xpath="//span[@class='movein moveInTotal']")
	private WebElement get_SubMoveInCostAmt;
	
	@FindBy(xpath="//a[@id='notes-add']")
	private WebElement addNotesLink;
	
	public Leasing_EligiblePromotionsPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public void clickSavenProceed()
	{
		savenProceed_btn.click();
	}
	
	public String get_SubTotalMonthlyRentAmt(){

		return get_SubTotalMonthlyRentAmt.getText();
	}
	public String get_SubMoveInCostAmt(){

		return get_SubMoveInCostAmt.getText();
	}
	
	public void clickAddNotesBtn(){
		addNotesLink.click();
	}	
			

}
