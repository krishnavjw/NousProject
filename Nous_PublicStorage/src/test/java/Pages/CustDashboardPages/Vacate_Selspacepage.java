package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Vacate_Selspacepage {

	
	@FindBy(xpath="//span[text()='Standard Vacate']/preceding-sibling::span[@class='button']")
	private WebElement stdVacate_Radio;
	
	
	@FindBy(xpath="//span[text()='Skip Vacate']/preceding-sibling::span[@class='button']")
	private WebElement skpVacate_Radio;
	
	@FindBy(xpath="//div[@id='dualDatePicker']//table/tbody//tr//td/a[contains(@class,'ui-state-active')]')")
	private WebElement selCurrDate;
	
	@FindBy(id="LastAccessDateHours")
	private WebElement hours;
	
	@FindBy(id="LastAccessDateMinutes")
	private WebElement mins;
	
	@FindBy(xpath="//span[text()='AM']/preceding-sibling::span[@class='button']")
	private WebElement sel_AM;
	
	@FindBy(xpath="//span[text()='PM']/preceding-sibling::span[@class='button']")
	private WebElement sel_PM;

	
	@FindBy(xpath="//table/tbody/tr/td[@class='space']")
	private WebElement spaceVal;
	
	
	@FindBy(xpath="//table/tbody/tr/td[@class='space']/following-sibling::td[1]")
	private WebElement balDue;
	
	
	@FindBy(xpath="//table/tbody/tr/td[@class='past-due']")
	private WebElement pastDue;
	
	
	@FindBy(xpath="//table/tbody/tr/td[@class='space']/following-sibling::td/a[text()='View Details']")
	private WebElement viewDetails_Link;
	
	@FindBy(id="lnkCancel1")
	private WebElement cancel_Btn;
	
	@FindBy(id="lnkReserve")
	private WebElement continue_Btn;
	
	@FindBy(linkText="Back to Dashboard")
	private WebElement backToDashBrd_Btn;

	public Vacate_Selspacepage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

}
