package Pages.AutoPayPages;

import java.awt.Desktop.Action;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;



public class ChangeAutoPayPage 
{
	WebDriver driver;

	@FindBy(xpath="//div[@id='payment-methods']//span[@class='k-widget k-dropdown k-header payment-method-dropdown']")
	private WebElement paymentSelectdropdwn;

	@FindBy(linkText="Cancel")
	private WebElement cancel_btn;

	@FindBy(linkText="Back to Dashboard")
	private WebElement BktoDash_btn;

	@FindBy(xpath="//div[@id='changeAutoPay']//div//a[contains(text(),'Submit')]")
	private WebElement submit_btn;

	@FindBy(linkText="Apply")
	private WebElement apply_btn;

	@FindBy(linkText="Enter Manually")
	private WebElement enterManual_btn;

	@FindBy(id="routingNumber")
	private WebElement routingNumber;

	@FindBy(id="accountNumber")
	private WebElement accountNumber;

	@FindBy(id="checkNumber")
	private WebElement checkNumber;

	@FindBy(id="checkAmount")
	private WebElement checkAmount;

	@FindBy(xpath="(//span[text()='Apply to AutoPay']/preceding-sibling::span[@class='button'])[2]")
	private WebElement autopaycheckbox;

	@FindBy(xpath="//button[text()='Collect Signature(s)']")
	private WebElement Collect_Signature;

	@FindBy(xpath="//canvas")
	private WebElement canvas;

	@FindBy(xpath="//button[text()='Accept']")
	private WebElement accept;

	@FindBy(xpath="//button[text()='Approve']")
	private WebElement Approve;

	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header payment-method-dropdown']")
	private WebElement paymentDropdown;

	@FindBy(xpath="//div[@id='customerAuthPanels']//div[@id='signature-panel-area']//div//a[@class='approve-button psbutton-normal']")
	private WebElement signature_ApproveBtn;
	
	@FindBy(xpath="//div[@class='command-row clearfix-container']/a[@class='psbutton-priority floatright margin-left js-confirm']")
	private WebElement confirmWithCustomer_Btn;

	public ChangeAutoPayPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}



	public void clickOnConfirmWithCustomer_Btn(){
		confirmWithCustomer_Btn.click();
	}

	public boolean verifyConfirmWithCustomer_Btn(){
		return confirmWithCustomer_Btn.isDisplayed();
	}

	public void clickCancelbtn()
	{
		cancel_btn.click();
	}
	public void clickBkToBashbtn()
	{
		BktoDash_btn.click();
	}

	public void clickSubmitbtn()
	{
		submit_btn.click();
	}

	public void clickapplybtn()
	{
		apply_btn.click();
	}
	public void clickmanualentry()
	{
		enterManual_btn.click();
	}

	public void Enter_routingNumber(String value)
	{
		routingNumber.sendKeys(value);
	}
	public void Enter_accountNumber(String value)
	{
		accountNumber.sendKeys(value);
	}
	public void Enter_checkNumber(String value)
	{
		checkNumber.sendKeys(value);
	}
	public void Enter_checkAmount(String value)
	{
		checkAmount.clear();
		checkAmount.sendKeys(value);
	}
	public void Select_autopaycheckbox() {
		autopaycheckbox.click();
	}
	public void click_Collect_Signature()
	{
		Collect_Signature.click();
	}
	public void click_canvas()
	{
		Actions builder = new Actions(driver);
		org.openqa.selenium.interactions.Action drawAction = builder.moveToElement(canvas,135,15)  
				.click()
				.moveByOffset(200, 60) // 2nd points (x1,y1)
				.click()
				.moveByOffset(100, 70)// 3rd points (x2,y2)
				.doubleClick()
				.build();
		drawAction.perform();
	}

	public void Click_accpt() {
		accept.click();
	}

	public void Click_approve() {
		Approve.click();
	}
	
	public void click_SignatureApproveBtn() {
		signature_ApproveBtn.click();
	}
	
	public void selectPaymentMethod(String expVal,WebDriver driver){
		paymentSelectdropdwn.click();
		List<WebElement> ListWbEle=driver.findElements(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul/li"));
		for (WebElement ele : ListWbEle) {
			String actualWbEleText=ele.getText();
			if(actualWbEleText.equalsIgnoreCase(expVal)) 
				ele.click();

		}

	}

	public void clickPaymentMethodDropdown() throws InterruptedException{
		paymentSelectdropdwn.click();

	}

	public void click_CollectSignature()
	{
		Collect_Signature.click();
	}


}
