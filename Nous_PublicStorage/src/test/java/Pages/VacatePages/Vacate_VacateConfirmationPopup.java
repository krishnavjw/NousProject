package Pages.VacatePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Vacate_VacateConfirmationPopup {

	//WebDriver driver;
	//WebDriverWait wait;

	public Vacate_VacateConfirmationPopup(WebDriver driver){
		//this.driver=driver;
		PageFactory.initElements(driver, this);
	}	

	@FindBy(xpath="//span[text()='Vacate Confirmation']")
	private WebElement vacateConfirmationPopup_title;

	@FindBy(xpath="//label [@id='HasUnitBeenInspected_Yes']/input")
	private WebElement hasSpaceInspected_yesRdoBtn;

	@FindBy(xpath="//label [@id='HasUnitBeenInspected_No']/span[@class='button']")
	private WebElement hasSpaceInspected_noRdoBtn;

	@FindBy(id="noteText")
	private WebElement note_Edt;

	@FindBy(id="employeeNumber")
	private WebElement empId_Edt;

	@FindBy(partialLinkText="Confirm")
	private WebElement confirm_Btn;

	@FindBy(partialLinkText="Cancel")
	private WebElement cancel_Btn;



	@FindBy(xpath="//label[@id='HasUnitBeenInspected_No']//span[@class='button']")
	private WebElement NoRadioButton;

	@FindBy(xpath="//textarea[@id='noteText']")
	private WebElement Note_TextArea;

	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement employeeNumber;


	@FindBy(xpath="(//a[contains(text(),'Confirm')])[2]")
	private WebElement ConfirmButton;



	public void click_NoRadioButton(){
		NoRadioButton.click();
	}

	public void enter_Note_TextArea(String Note){
		Note_TextArea.sendKeys(Note);
	}

	public void enter_EmpNumber(String EmpNumber){
		employeeNumber.sendKeys(EmpNumber);
	}

	public void click_ConfirmButton(){
		ConfirmButton.click();
	}

	public String getVacateConfirmationPopup_title(){
		//wait.until(ExpectedConditions.visibilityOf(vacateConfirmationPopup_title));
		return vacateConfirmationPopup_title.getText().trim();
	}

	public void clickhasSpaceInspected_yesRdoBtn(){
		//wait.until(ExpectedConditions.visibilityOf(hasSpaceInspected_yesRdoBtn));
		hasSpaceInspected_yesRdoBtn.click();
	}

	public void clickhasSpaceInspected_noRdoBtn(){
		//wait.until(ExpectedConditions.visibilityOf(hasSpaceInspected_noRdoBtn));
		hasSpaceInspected_noRdoBtn.click();
	}

	public void enterNote_Edt(String textToEnter){
		//wait.until(ExpectedConditions.visibilityOf(note_Edt));
		note_Edt.sendKeys(textToEnter);
	}

	public void enterEmpId_Edt(String empID){
		//wait.until(ExpectedConditions.visibilityOf(empId_Edt));
		empId_Edt.sendKeys(empID);
	}

	public void clickConfirm_Btn(){
		//wait.until(ExpectedConditions.visibilityOf(confirm_Btn));
		confirm_Btn.click();
	}



}
