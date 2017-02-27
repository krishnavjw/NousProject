package Pages.CustDashboardPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FlagForMaintce {

	private WebDriver driver;
	
	@FindBy(xpath="//div[@class='k-window-titlebar k-header']//span[text()='Flag For Maintenance']")
	private WebElement flagForMaintce_PageHeader;
	
	
	@FindBy(xpath="//div[contains(text(),'Reason for flagging:')]/following-sibling::div//span[text()='SELECT']")
	private WebElement resonForFlaging_List;
	
	@FindBy(id="noteText")
	private WebElement note_TextArea;
	
	@FindBy(id="employeeNumber")
	private WebElement empId;
	
	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement cancel_Btn;
	
	@FindBy(xpath="//a[contains(text(),'Update')]")
	private WebElement update_Btn;
	

	public FlagForMaintce(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public boolean verify_FlagTitle() {
		return flagForMaintce_PageHeader.isDisplayed();
	}
	
	public void select_reason(String reason) throws InterruptedException{
		resonForFlaging_List.click();
		Thread.sleep(2000);
		List<WebElement> reasonlist = driver.findElements(By.xpath("//div[@class='k-animation-container']//ul/li"));
		for(int i=0;i<reasonlist.size();i++){
			if(reason.equalsIgnoreCase(reasonlist.get(i).getText().trim())){
				reasonlist.get(i).click();
				break;
			}
		}
		
	}
	
	public void enter_Notes(String value){
		note_TextArea.sendKeys(value);
	} 
	
	public void enter_EmployeId(String value){
		empId.sendKeys(value);
	} 
	
	public void click_CancelBtn(){
		cancel_Btn.click();
	}
	
	public void click_UpdateBtn(){
		update_Btn.click();
	}
	
	
}
