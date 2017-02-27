package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SchduleVacte_Selspacepage {

	
	
	@FindBy(xpath="//div[@class='k-grid-content ps-container']/table/tbody//tr/td[@class='space']")
	private WebElement schspaceVal;
	
	@FindBy(xpath="//div[@class='k-grid-content ps-container']/table/tbody//tr/td[@class='space']/following-sibling::td[1]")
	private WebElement schbalanceDueAmt;
	
	@FindBy(xpath="//div[@class='k-grid-content ps-container']/table/tbody//tr/td[@class='past-due']")
	private WebElement schpastDue;
	
	@FindBy(linkText="View Details")
	private WebElement schviewDetails_Link;
	
	@FindBy(xpath="//span[text()='SELECT']")
	private WebElement schclickOnDate;
	

	@FindBy(id="lnkCancel1")
	private WebElement schcancl_Btn;
	
	@FindBy(id="lnkReserve")
	private WebElement schcontnue_Btn;
	
	
	@FindBy(linkText="Back to Dashboard")
	private WebElement schbckToDashbrd_Btn;
	
	
	public SchduleVacte_Selspacepage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	

	
}
