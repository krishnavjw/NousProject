package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Leasing_ReviewNApprovePage 
{
	@FindBy(xpath="(//div[@class='authorization-buttons']//button[text()='Approve'])[1]")
	private WebElement approve_btn_auth;
	
	@FindBy(xpath="(//div[@class='authorization-buttons']//button[text()='Approve'])[2]")
	private WebElement approve_btn_insu;
	
	@FindBy(id="PaymentButton")
	private WebElement saveProceed_btn;
	
	@FindBy(xpath="//a[@id='notes-add']")
	private WebElement addNotesLink;
	
	@FindBy(xpath="//li[@id='InsuranceAuthorization']//div[@class='authorization-buttons-container']//button[text()='Approve']")
	private WebElement insuranceApprove_btn;
	
	public Leasing_ReviewNApprovePage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
		
	}
	
	public void clickApprove_btn()
	{
		approve_btn_auth.click();
	}
	
	public void clickapprove_btn_insu()
	{
		approve_btn_insu.click();
	}
	public void clickSaveproceed_btn()
	{
		saveProceed_btn.click();
	}
	public void clickAddNotesBtn(){
		addNotesLink.click();
	}

	public void clickInsuranceApprove_Btn()
	{
		insuranceApprove_btn.click();
	}
}
