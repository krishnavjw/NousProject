package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Fudiciary_Information {
	@FindBy(id="saveandproceed")
	private WebElement saveProceed_btn;
	
	
	@FindBy(id="firstName")
	private WebElement firstName;
	
	
	@FindBy(id="lastName")
	private WebElement lastName;
	
	

	@FindBy(id="title")
	private WebElement title1;
	
	@FindBy(id="confirmWithCustomer")
	private WebElement confirmWithCustomer;
	
	
	
	public Fudiciary_Information(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
		
	}
	
	public void click_saveProceed_btn()
	{
		saveProceed_btn.click();
	}
	
	public void enter_firstName(String fn)
	{
		firstName.sendKeys(fn);
	}
	public void enter_lastName(String ln)
	{
		lastName.sendKeys(ln);
	}
	public void enter_title(String title)
	{
		title1.sendKeys(title);
	}
	public void click_confirmWithCustomer()
	{
		confirmWithCustomer.click();
	}

}
