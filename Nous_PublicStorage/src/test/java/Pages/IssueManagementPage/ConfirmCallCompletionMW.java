package Pages.IssueManagementPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmCallCompletionMW {
	private WebDriver driver;
	public ConfirmCallCompletionMW(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="employeeNumber")
	private WebElement empNumEdt;
	
	@FindBy(xpath="//a[contains(text(),'Save & Close')]")
	private WebElement saveAndcloseBtn;
	
	public void enterEmpNumEdt(String DMLogin){
		empNumEdt.sendKeys(DMLogin);
	}
	
	public void clkSaveAndCloseBtn(){
		saveAndcloseBtn.click();
	}
}
