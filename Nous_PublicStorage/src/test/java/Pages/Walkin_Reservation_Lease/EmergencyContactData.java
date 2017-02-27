package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmergencyContactData {
	

	//declines to provide emergency contact
	@FindBy(xpath="//div[@class='same-contact emergency-contact']//span[text()='Customer declines to provide an Emergency Contact']/preceding-sibling::span")
	private WebElement declines_EmgcyContact_chkBox;
	
	@FindBy(id="confirmWithCustomer")
	private WebElement cnfmWithCust;
	
	public EmergencyContactData(WebDriver driver){

		PageFactory.initElements(driver,this);
		//driver=this.driver;

	}
	
	public void click_decline_EmgcyContact(){
		declines_EmgcyContact_chkBox.click();
		
	}

	public void btn_confirmWithCust(){
		cnfmWithCust.click();
		
	}
	
}
