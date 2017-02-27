package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateReservation_PopUp {

	@FindBy(xpath = "//div//span[text()='Create Reservation']")
	// @CacheLookup
	private WebElement popup_CreateResHeader;

	@FindBy(id = "notesText")
	// @CacheLookup
	private WebElement notes_TextArea;

	@FindBy(id = "employeeNumber")
	// @CacheLookup
	private WebElement emailId;

	@FindBy(xpath = "//div[@class='command-row clearfix-container']//a[contains(text(),'Cancel')]")
	// @CacheLookup
	private WebElement clk_CancelBtn;

	@FindBy(xpath = "//div[@class='command-row clearfix-container']//a[contains(text(),'Create Reservation')]")
	// @CacheLookup
	private WebElement clk_CreateResvationBtn;

	@FindBy(id = "employeeNumber")
	// @CacheLookup
	private WebElement TxtBx_EmpID;

	@FindBy(xpath = "//div[@id='employeeNumber-wrapper']/div[@class='input-validation-message']//span")
	private WebElement txt_empErrorMsg;

	@FindBy(xpath = "//span[contains(text(),'Reservation Number')]/following-sibling::span")
	private WebElement reservnum_disply;

	@FindBy(id = "confirmReservationNotes")
	private WebElement confirmresnotes;

	@FindBy(xpath = "(//a[contains(text(),'Confirm')])[2]")
	private WebElement confirmbutton;
	
	@FindBy(xpath="//span[text()='Please enter an employee number.']")
	private WebElement empIdErrorMsg;

	public CreateReservation_PopUp(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public String get_CreateResvPopUpHeader() {
		return popup_CreateResHeader.getText();
	}

	public void enter_NotesTextArea(String value) {
		notes_TextArea.sendKeys(value);
	}

	public void enter_emailId(String email) {
		emailId.sendKeys(email);
	}

	public void clk_CancelBtn() {
		clk_CancelBtn.click();
	}

	public void clk_CreateResvationBtn() {
		clk_CreateResvationBtn.click();
	}

	public void enter_EmpID(String EmpID) {
		TxtBx_EmpID.sendKeys(EmpID);
	}

	public String reservnum_disply() {
		return reservnum_disply.getText();
	}

	public String get_empErrorMessage() {
		return txt_empErrorMsg.getText();
	}

	public void clear_EmpID() {
		TxtBx_EmpID.clear();
	}

	public void enter_confirmresnotes(String notes) {
		confirmresnotes.sendKeys(notes);
	}

	public void clk_ConfirmBtn() {
		confirmbutton.click();
	}
	public boolean isCreateResvnTitleDisplayed(){

		return popup_CreateResHeader.isDisplayed();
	}
	
	public boolean isEmpIdErrorMsgDisplayed(){
		
		return empIdErrorMsg.isDisplayed();
	}


}
