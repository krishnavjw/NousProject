package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SwapSpacePage {

	WebDriver driver;

	@FindBy(xpath = "//span[text()='Yes']/../input[@checked='checked']")
	private WebElement rdBtn_NewCustYES;

	@FindBy(xpath = "//span[contains(text(),'Vehicle Storage')]")
	private WebElement tab_VehicleStorage;

	@FindBy(xpath = "//span[contains(text(),'Car / SUV / Truck')]/../../span[@class='button']")
	private WebElement ChkBx_Car;

	@FindBy(xpath = "//a[@id='search-button']")
	private WebElement btn_Search;

	@FindBy(xpath = "//span[@id='chooseSizeDialog_wnd_title']")
	private WebElement swapSpace;

	@FindBy(xpath = "//a[@id='swapSpaceButton']")
	private WebElement swap_Spacebtn;

	public SwapSpacePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public boolean verifyYESIsSelectedForNewCustomer() {
		Boolean b = rdBtn_NewCustYES.isSelected();
		return b;
	}

	public void Clk_tab_VehicleStorage() {
		tab_VehicleStorage.click();
	}

	public void Clk_ChkBx_Car() {
		ChkBx_Car.click();
	}

	public void Clk_btn_Search() {
		btn_Search.click();
	}

	public boolean isDisplayed_SwapSpace() {
		return swapSpace.isDisplayed();
	}

	public WebElement clk_SwapSpaceButton() {
		return swap_Spacebtn;
	}

}
