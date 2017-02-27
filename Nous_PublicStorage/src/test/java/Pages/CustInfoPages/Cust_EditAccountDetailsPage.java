package Pages.CustInfoPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Cust_EditAccountDetailsPage {
 
	
	@FindBy(xpath="//span[text()='Customer Information']/preceding-sibling::span[@class='button']")
	private WebElement custInfo_RadioBtn;

	@FindBy(xpath="//span[text()='Emergency Contact']/preceding-sibling::span[@class='button']")
	private WebElement emergencyContact_RadioBtn;

	@FindBy(xpath="//span[text()='Authorized Access']/preceding-sibling::span[@class='button']")
	private WebElement authorizedAccess_RadioBtn;

	@FindBy(xpath="//span[text()='Military Status']/preceding-sibling::span[@class='button']")
	private WebElement militaryAccess_RadioBtn;

	@FindBy(xpath="//span[text()='Email Communication Preferences']/preceding-sibling::span[@class='button']")
	private WebElement email_RadioBtn;

	@FindBy(xpath="//span[text()='Manage Property/Space Access']/preceding-sibling::span[@class='button']")
	private WebElement manageProperty_RadioBtn;

	@FindBy(xpath="//span[text()='Other Customer Status']/preceding-sibling::span[@class='button']")
	private WebElement otherCustomerStatus_RadioBtn;

	@FindBy(xpath="//span[text()='Resale/Tax Exemption']/preceding-sibling::span[@class='button']")
	private WebElement resaleTaxExemption_RadioBtn;
	
	@FindBy(xpath="//span[text()='Yes']/preceding-sibling::span[@class='button']")
	private WebElement Yes_RadioBtn;
	
	@FindBy(xpath="//span[text()='No']/preceding-sibling::span[@class='button']")
	private WebElement No_RadioBtn;
	
	@FindBy(xpath="//div[@class='presence radio-group']/div[@class='bold padding-bottom']")
	private WebElement isCustomerPresent_Txt;
	
	@FindBy(xpath="//div[@class='command-row clearfix-container']//a[contains(text(),'Cancel')]")
	private WebElement cancel_Btn;

	@FindBy(xpath="//div[@class='command-row clearfix-container']//a[contains(text(),'Launch')]")
	private WebElement Launch_Btn;
	
	@FindBy(xpath="//span[text()='Edit Account Details']")
	private WebElement EditAccDetails_Title;


	public Cust_EditAccountDetailsPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}


	public void clickCustInfoRadioBtn()
	{
		custInfo_RadioBtn.click();
	}

	public void clickEmergencyContactRadioBtn()
	{
		emergencyContact_RadioBtn.click();
	}

	public void clickAuthorizedAccessRadioBtn()
	{
		authorizedAccess_RadioBtn.click();
	}

	public void clickMilitaryAccessRadioBtn()
	{
		militaryAccess_RadioBtn.click();
	}

	public void clickEmailPreferencesRadioBtn()
	{
		email_RadioBtn.click();
	}

	public void clickManagePropertyRadioBtn()
	{
		manageProperty_RadioBtn.click();
	}

	public void clickOtherCustomerStatusRadioBtn()
	{
		otherCustomerStatus_RadioBtn.click();
	}

	public void clickResaleTaxExemptionRadioBtn()
	{
		resaleTaxExemption_RadioBtn.click();
	}

	public void clickCancelBtn()
	{
		cancel_Btn.click();
	}

	public void clickLaunchBtn()
	{
		Launch_Btn.click();
	}
	
	public void clickYesRadioBtn()
	{
		Yes_RadioBtn.click();
	}
	
	public void clickNoRadioBtn()
	{
		No_RadioBtn.click();
	}
	
	public boolean isDisplayedYesRadioBtn(){
		return Yes_RadioBtn.isDisplayed();
	}
	
	public boolean isDisplayedNoRadioBtn(){
		return No_RadioBtn.isDisplayed();
	}
	
	public boolean verify_EditAccDetails_Title()
	{
		return EditAccDetails_Title.isDisplayed();
	}
}
