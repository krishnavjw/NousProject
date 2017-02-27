package Pages.HomePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Dashboard_BifrostHostPopUp {

	@FindBy(xpath = "//input[@type='button'][@value='Continue Without Device']")
	private WebElement continueDevice_btn;

	@FindBy(xpath = "//div//h2[@class='padding']")
	private WebElement biforstHostNo;

	public Dashboard_BifrostHostPopUp(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void clickContiDevice() {
		continueDevice_btn.click();
	}

	public String getBiforstNo() {
		return biforstHostNo.getText();
	}

	public boolean isPopUpDisplayed() {
		return continueDevice_btn.isDisplayed();
	}

}
