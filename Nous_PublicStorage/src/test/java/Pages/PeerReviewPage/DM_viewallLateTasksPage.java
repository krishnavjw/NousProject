package Pages.PeerReviewPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DM_viewallLateTasksPage 

{
	WebDriver driver;

	@FindBy(xpath="//tr[td[contains(text(),'Late vacates')]]//td[7]/span")
	private WebElement button_reassigntask;

	
	@FindBy(id="reassignTaskAssignedEmployeeId")
	private WebElement reassign_employeeField;
	
	@FindBy(id="notesText")
	private WebElement optionalnotes_text;
	
	@FindBy(id="reassignTaskEmployeeid")
	private WebElement employeeIDField;
	
	@FindBy(xpath="//a[contains(text(),'Confirm')]")
	private WebElement confirmButton;


	
	
	
	public DM_viewallLateTasksPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	
	public void click_AssignTaskbutton(WebDriver driver)
	{
		Actions act=new Actions(driver);
		act.moveToElement(button_reassigntask).click().build().perform();
	}

	
	public void moveto_LateTask(WebDriver driver)
	{
		Actions act=new Actions(driver);
		act.moveToElement(button_reassigntask).build().perform();
	}
	
	
	
	public void enterEmployeeID_ToReassign(String str)
	{
		reassign_employeeField.clear();
		reassign_employeeField.sendKeys(str);
		
	}
	
	
	public void enterOptionalNotes(String str){
		optionalnotes_text.clear();
		optionalnotes_text.sendKeys(str);
	}
	
	
	public void enterEmployeeID(String str)
	{
		reassign_employeeField.clear();
		reassign_employeeField.sendKeys(str);
		
	}
	
	
	public void click_ConfirmButton()
	{
		confirmButton.click();
	}
	


}
