package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CancelReservationPage {
	
	@FindBy(xpath = "//div[@id='reservationPage']//div[@class='k-window-titlebar k-header']/span[@class='k-window-title']")
	private WebElement cancelRes_title;

	@FindBy(xpath = "//div[@id='cancelReservationDialog']//div[text()='Reason for Cancellation:']")
	private WebElement reasonListbox_field;

	@FindBy(xpath = "//div[@id='cancelReservationDialog']//div[text()='Reason for Cancellation:']/following-sibling::div//span[@class='k-widget k-dropdown k-header cancel-reasons']")
	private WebElement reason_Listbox;

	@FindBy(xpath = "//div[@id='cancelReservationDialog']//textarea[@id='cancelReservationNotes']")
	private WebElement notes_field;

	@FindBy(xpath = "//div[@id='cancelReservationDialog']//input[@id='employeeNumber']")
	private WebElement emp_Id;

	@FindBy(xpath = "//div[@class='k-window-content k-content']//a[contains(text(),'Confirm Cancellation')]")
	private WebElement confirmCancel_btn;

	@FindBy(xpath = "//div[@class='k-window-content k-content']//a[contains(@class,'cancel-button')]")
	private WebElement cancel_btn;

	@FindBy(xpath = "//div[@id='cancelReservationDialog']//div[text()='Please select a cancellation reason.']")
	private WebElement err_CancellationReason;

	@FindBy(xpath = "//div[@id='cancelReservationDialog']//span[text()='Please enter an employee number.']")
	private WebElement err_EmployeeNum;

	public CancelReservationPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public String verifyCancelReservationTitle() {
		return cancelRes_title.getText();
	}

	public boolean verifyReasonListbox() {
		return reasonListbox_field.isDisplayed();
	}

	public void clickReasonListbox() {
		reason_Listbox.click();
	}

	public void enterNotes(String value) {
		notes_field.sendKeys(value);
	}

	public void enterEmployeeId(String value) {
		emp_Id.sendKeys(value);
	}

	public void clickConfirmCancel_btn() {
		confirmCancel_btn.click();
	}

	public void clickCancel_btn() {
		cancel_btn.click();
	}

	public boolean verifyError_CancelReason() {
		return err_CancellationReason.isDisplayed();
	}

	public boolean verifyError_EmployeeNum() {
		return err_EmployeeNum.isDisplayed();
	}

}
