package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Unreservation_popup {

	WebDriver driver;

	@FindBy(xpath = "//span[contains(text(),'Not Reached')]/preceding-sibling::span[@class='button']")
	private WebElement not_reached_radiobtn;

	@FindBy(xpath = "//span[contains(text(),'Not Confirmed')]/preceding-sibling::span[@class='button']")
	private WebElement Not_Confirmed_radiobtn;

	@FindBy(id = "pendingReservationNotes")
	private WebElement pendingReservationNotes;

	@FindBy(id = "employeeNumber")
	private WebElement employeeNumber;

	@FindBy(partialLinkText = "Save & Continue")
	private WebElement Save_Continue;

	public Unreservation_popup(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void clk_not_reached_radiobtn() {
		not_reached_radiobtn.click();
	}

	public void clk_Not_Confirmed_radiobtn() {
		Not_Confirmed_radiobtn.click();
	}

	public void enter_pendingReservationNotes(String sendtext) {
		pendingReservationNotes.sendKeys(sendtext);
	}

	public void enter_employeeNumber(String sendtext) {
		employeeNumber.sendKeys(sendtext);
	}

	public void clk_Save_Continue() {
		Save_Continue.click();
	}

}
