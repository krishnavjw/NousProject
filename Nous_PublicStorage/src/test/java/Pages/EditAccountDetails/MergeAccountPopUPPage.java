package Pages.EditAccountDetails;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.gargoylesoftware.htmlunit.javascript.host.media.webkitAudioContext;

public class MergeAccountPopUPPage {

	@FindBy(xpath="//div//span[text()='Merge Account']")
	private WebElement isMergeAccPopUpTitleDisplayed;
	
	@FindBy(id="employeeNumber")
	private WebElement enter_EmpId;
	
	@FindBy(xpath="//textarea[@id='notesText']")
	private WebElement enter_NoteField;
	
	@FindBy(xpath="//a[contains(text(),'Confirm')]")
	private WebElement clk_ConfirmBtn;
	
	
	public MergeAccountPopUPPage(WebDriver driver){
		
		PageFactory.initElements(driver, this);
	}
	
	public boolean isMergeAccPopUpTitleDisplayed(){
		
		return isMergeAccPopUpTitleDisplayed.isDisplayed();
	}
	
	public void enter_EmpId(String empid){
		
		enter_EmpId.sendKeys(empid);
	}
	
	public void clk_ConfirmBtn(){
		
		clk_ConfirmBtn.click();
	}
	public void enter_NoteField(String note){
		
		enter_NoteField.sendKeys(note);
	}
}
