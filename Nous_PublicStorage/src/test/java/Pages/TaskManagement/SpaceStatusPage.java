package Pages.TaskManagement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SpaceStatusPage {
	
	
	@FindBy(xpath="//span[@class='k-icon k-i-arrow-s'][contains(text(),'select')]")
	private WebElement status_filter_dropdown;
	
	@FindBy(xpath="//a[@id='viewButton']")
	private WebElement viewbutton;
	
	@FindBy(xpath="//div[@id='maintenanceButtons']/label[1]/span[1]")
	private WebElement maintenance_yes;
	
	@FindBy(xpath="//div[@id='employeeNumberEntry']/div[3]/div[3]/div[2]/span/span/span[2]/span")
	private WebElement reason_dropdown;
	
	
	@FindBy(xpath="//div[21]/div/ul/li[3]")
	private WebElement reason_option;
	
	
	@FindBy(xpath="//div/textarea[@id='notesText']")
	private WebElement note_text;
	
	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement employee_id;
	
	@FindBy(xpath="//div/a[@class='psbutton-priority margin-left ok-button floatright'][contains(text(),'Update')]")
	private WebElement update_btn;
	
	@FindBy(xpath="//div[@id='spaceStatus']//a[contains(text(),'Back to Dashboard')]")
	private WebElement back_to_dashboard;
	
	@FindBy(xpath="//div[@id='BigSkyWorkRequestDate']/span")
	private WebElement date_btn;
	
	@FindBy(xpath="//label[@id='CompletedStatus']/span[1]")
	private WebElement completed_btn;
	
	@FindBy(xpath="//a[@id='btnSubmit']")
	private WebElement submit_btn;
	
	@FindBy(xpath="//div[@id='employeeNumberEntry']/div[5]/div[2]//span/span[2]/span")
	private WebElement lockStatus_dropdown;
	
	
	
	
	
	public SpaceStatusPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

	public void clk_maintenance_yes(){
		maintenance_yes.click();
	}

	public void clk_reason_dropdown(){
		reason_dropdown.click();
	}

	public void clk_reason_option(){
		reason_option.click();
	}

	public void enter_text(String text){
		note_text.sendKeys(text);
	}

	public void enter_emp(String empno){
		employee_id.sendKeys(empno);
	}


	public void clk_update(){
		update_btn.click();
	}
     
	public void clk_back_to_dashboard(){
		back_to_dashboard.click();
	}

	public void clk_statusfilterdropdown(){
		status_filter_dropdown.click();
	}
	
	public void clk_view(){
		viewbutton.click();
	}
    
	public void clk_on_date(){
		date_btn.click();
	}
	
	public void clk_on_completedStatus(){
		completed_btn.click();
	}
	
	public void clk_on_submit(){
		submit_btn.click();
	}
	
	public void clk_on_LockStatus(){
		lockStatus_dropdown.click();
	}
	
}
