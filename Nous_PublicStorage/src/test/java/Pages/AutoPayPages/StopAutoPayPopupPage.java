package Pages.AutoPayPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class StopAutoPayPopupPage {
	
	@FindBy(xpath="//div[@id='employeeNumber-wrapper']//input[@id='employeeNumber']")
	private WebElement employeeID;

	/*@FindBy(xpath="//div[@class='text-align-right']//a[contains(text(),'Cancel')]")
	private WebElement cancel_Btn;*/

	@FindBy(xpath="//div[@class='text-align-right']//a[contains(text(),'Confirm')]")
	private WebElement confirm_Btn;
	

	public StopAutoPayPopupPage(WebDriver driver){
		
		PageFactory.initElements(driver, this);
	}
	
	
	public void enterEmployeeID(String value){
		employeeID.sendKeys(value);
	}
	
	/*public void clickCancel_Btn(){
		cancel_Btn.click();
	}*/
	
	public void clickConfirm_Btn(){
		confirm_Btn.click();
	}
	
	
}
