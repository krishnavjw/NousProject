package Pages.CustDashboardPages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddOrEditInsurancePage {

	WebDriver driver;

	@FindBy(xpath = "//div[contains(text(),'Location')]")
	private WebElement txt_Location;

	@FindBy(xpath = "//div[contains(text(),'Space')]")
	private WebElement txt_Space;

	@FindBy(xpath = "//div[contains(text(),'Coverage Options')]")
	private WebElement txt_CoverageOptions;

	@FindBy(xpath = "//*[contains(text(),'Property Details')]")
	private WebElement Tooltip_PropertyDetails;

	@FindBy(xpath = "//span[@class='rental-unit-number has-insurance']")
	private WebElement txt_SpaceNumber;

	@FindBy(xpath = "//span[@class='k-input']")
	private WebElement txt_DefaultInsuranceAmt;

	@FindBy(xpath = "//div[@id='primarySignaturePanel']//a[text()='Clear']")
	private WebElement clearbtn;

	@FindBy(xpath = "//div[@id='primarySignaturePanel']//a[text()='Approve']")
	private WebElement approvebtn;

	@FindBy(xpath = "//div[@id='primarySignaturePanel']//img[contains(@class,'signature-display')]")
	private WebElement signaturePad;

	@FindBy(xpath = "//div[@id='primarySignaturePanel']//div[contains(@class,'authorization-title')]")
	private WebElement informationText;

	@FindBy(xpath = "//a[@id='confirmButton']")
	private WebElement confirmWithCustomerBtn;

	@FindBy(xpath = "//button[text()='Accept']")
	private WebElement acceptBtn;

	@FindBy(xpath = "//a[@id='paymentButton']")
	private WebElement paymentBtn;

	@FindBy(xpath = "//canvas[@class='signature-pad']")
	private WebElement AcceptInsuranceSignatureBox;

	public AddOrEditInsurancePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean isMakePaymentDisplayed() {
		return paymentBtn.isDisplayed();
	}

	public void clk_acceptBtn() {
		acceptBtn.click();
	}

	public void clickAllCheckboxesInAcceptInsurance(WebDriver driver) throws InterruptedException {
		List<WebElement> elements = driver.findElements(By.xpath("//input[@id='Checkbox1']"));
		for (WebElement element : elements) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			element.click();
			Thread.sleep(2000);
		}
	}

	public void clk_confirmWithCustomerBtn() {
		confirmWithCustomerBtn.click();
	}

	public void clk_ClearBtn() {
		clearbtn.click();
	}

	public boolean isClearBtnDisplayed() {
		return clearbtn.isDisplayed();
	}

	public boolean isApproveBtnDisplayed() {
		return approvebtn.isDisplayed();
	}

	public void clk_ApproveBtn() {
		approvebtn.click();
	}

	public boolean isSignaturePadDisplayed() {
		return signaturePad.isDisplayed();
	}

	public void drawSignature(WebDriver driver) {
		Actions builder = new Actions(driver);
		Action drawAction = builder.moveToElement(AcceptInsuranceSignatureBox, 135, 15).clickAndHold()
				.moveByOffset(200, 60).release().clickAndHold().moveByOffset(300, 70).release().doubleClick().build();
		drawAction.perform();
	}

	public boolean isInformationTextDisplayed() {
		return informationText.isDisplayed();
	}

	public String getInformationText() {
		return informationText.getText();
	}

	public boolean verify_LocationIsDisplayed() {
		return txt_Location.isDisplayed();
	}

	public boolean verify_SpaceIsDisplayed() {
		return txt_Space.isDisplayed();
	}

	public boolean verify_CoverageOptionsIsDisplayed() {
		return txt_CoverageOptions.isDisplayed();
	}

	public void mousehoverOnLocation(WebDriver driver) {
		Actions act = new Actions(driver);
		act.moveToElement(txt_Location).perform();
	}

	public boolean verify_propertyDetailsareDisplayed() {
		return Tooltip_PropertyDetails.isDisplayed();
	}

	public boolean verifySpaceIsInBold() {
		String fontWeight = txt_SpaceNumber.getCssValue("font-weight");
		if (fontWeight.equals("bold") || fontWeight.equals("700"))
			return true;
		else
			return false;
	}

	public String get_DefaultInsuranceAmt() {
		return txt_DefaultInsuranceAmt.getText();
	}

	// Generates a pop up with our message
	public void showJavaScriptPopup(WebDriver driver, String message) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("alert('" + message + "');");
		Thread.sleep(2000);
	}

	public List<String> readDropDownValues(WebDriver driver) {

		List<String> l = new ArrayList<String>();

		WebElement dropdown = driver.findElement(By.xpath("//div[@id='insurance']//span[contains(@role,'listbox')]"));
		dropdown.click();

		List<WebElement> listInDropDown = dropdown.findElements(
				By.xpath("//ul[contains(@id,'ProductOptionSiteId_listbox')]//li[contains(@class,'k-item')]"));
		for (WebElement ele : listInDropDown) {
			String dropDownValues = ele.getText();
			l.add(dropDownValues);
		}

		return l;
	}
	public void clk_MakePayment(){
		paymentBtn.click();
	}
	

	public void selectLastValueInDropDown(WebDriver driver) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		WebElement dropdown = driver.findElement(By.xpath("//div[@id='insurance']//span[contains(@role,'listbox')]"));
		js.executeScript("arguments[0].click();", dropdown);

		List<WebElement> listInDropDown = driver.findElements(
				By.xpath("//ul[contains(@id,'ProductOptionSiteId_listbox')]//li[contains(@class,'k-item')]"));
		for (int i = 0; i < listInDropDown.size(); i++) {
			if (i == listInDropDown.size() - 1) {
				js.executeScript("arguments[0].click();", listInDropDown.get(i));
			}
		}
	}
}
