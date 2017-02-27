package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmergencyContact_EmployeeIdPage {
	
	private WebDriver driver;
	
	public EmergencyContact_EmployeeIdPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//div[@id='employeeNumber-wrapper']//input[@id='employeeNumber']")
	private WebElement employeeId;
	
	@FindBy(xpath="//div[@class='k-window-content k-content']//a[contains(text(),'Continue')]")
	private WebElement continueBtn;
	
	
	public void enter_EmployeeId(String text){
		employeeId.sendKeys(text);
	}
	
	public void click_ContinueBtn(){
		continueBtn.click();
	}
	
	

}
