package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Vacate_MisChrgespage {

	
	@FindBy(xpath="//span[text()='select']")
	private WebElement selCharge_List;
	
	@FindBy(className="dd-charge psbutton-normal margin-left")
	private WebElement addCharge;
	
	@FindBy(xpath="//div[@class='added-charges-header']/div[@class='structure name']")
	private WebElement charge_Data;
	
	@FindBy(xpath="//div[@class='added-charges-header']/div[@class='structure amount']")
	private WebElement amount;
	
	@FindBy(xpath="//div[@class='added-charges-header']/div[@class='structure note']")
	private WebElement note;
	
	@FindBy(id="lnkCancel1")
	private WebElement cancel_Btn;
	
	@FindBy(id="lnkReserve")
	private WebElement continue_Btn;
	
	@FindBy(className="psbutton-priority floatleft")
	private WebElement BackToDshBd_Btn;
	
	
	public Vacate_MisChrgespage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}	
}
