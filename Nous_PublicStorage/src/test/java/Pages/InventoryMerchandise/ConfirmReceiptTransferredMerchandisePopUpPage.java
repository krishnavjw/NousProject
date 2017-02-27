package Pages.InventoryMerchandise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmReceiptTransferredMerchandisePopUpPage {

	@FindBy(xpath="//textarea[@id='noteText']")
	private WebElement notefield;
	
	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement employeefield;
	
	@FindBy(xpath="//a[contains(text(),'Confirm')]")
	private WebElement confirmBtn;
	
	public  ConfirmReceiptTransferredMerchandisePopUpPage(WebDriver driver){
		
		PageFactory.initElements(driver, this);
	}
	
	public boolean isNotefieldDisplayed(){
		
		return notefield.isDisplayed();
	}
	
	public void enterNote(String note){
		notefield.sendKeys(note);
	}
	
	public void enterEmployeeId(String empId){
		employeefield.sendKeys(empId);
	}
	
	public void clk_ConfirmBtn(){
		
		confirmBtn.click();
	}
	
}
