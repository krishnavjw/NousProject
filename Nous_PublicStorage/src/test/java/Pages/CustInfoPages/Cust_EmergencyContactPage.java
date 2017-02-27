package Pages.CustInfoPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Cust_EmergencyContactPage {


	@FindBy(xpath="//div[@class='page-header container-heading clearfix-container']/h3[@class='floatleft']")
	private WebElement pageTitle;
	
	@FindBy(xpath="//input[@id='EmergencyContacts_0__FirstName']")
	private WebElement Txt_firstName;
	
	@FindBy(xpath="//input[@id='EmergencyContacts_0__MiddleInitial']")
	private WebElement Txt_MiddleName;
	
	@FindBy(xpath="//input[@id='EmergencyContacts_0__LastName']")
	private WebElement Txt_LastName;
	
	@FindBy(xpath="//input[@id='IndividualInformationModel_ContactIdentification_EncryptedItem_EncryptedIdDisplay']")
	private WebElement Txt_Encrypted;
	
	@FindBy(xpath="//input[@id='EmergencyContacts_0__Address_AddressLine1']")
	private WebElement Txt_AddressLine1;
	
	@FindBy(xpath="//input[@id='EmergencyContacts_0__Address_AddressLine2']")
	private WebElement Txt_AddressLine2;
	
	@FindBy(xpath="//input[@id='EmergencyContacts_0__Address_City']")
	private WebElement Txt_City;
	
	@FindBy(xpath="//input[@id='EmergencyContacts_0__Address_PostalCode")
	private WebElement Txt_PostalCode;
	
	@FindBy(xpath="//div[@class='international double-margin-top']/label/span[@class='button']")
	private WebElement Intl_RadioBtn;
	
	@FindBy(xpath="//input[@id='Phones_OfficialPhones__-index-__0__AreaCode']")
	private WebElement Txt_phoneAreaCode;
	
	@FindBy(xpath="Phones_OfficialPhones__-index-__0__Exchange")
	private WebElement Txt_phoneExchange;

	@FindBy(xpath="Phones_OfficialPhones__-index-__0__LineNumber'")
	private WebElement Txt_phoneLineNumber;

	@FindBy(xpath="Emails_OfficialEmails__-index-__0__Email")
	private WebElement Txt_email;
	
	@FindBy(xpath="//a[@class='psbutton-low-priority floatright']")
	private WebElement cancel_Btn;

	@FindBy(xpath="//a[@id='verifyButton']")
	private WebElement verify_Btn;
	
	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header identDrop']")
	private WebElement identification_Dropdown;
	
	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header phoneDrop floatleft margin-right js-phone']")
	private WebElement phoneCategory_Dropdown;
	
	@FindBy(xpath="(//span[@class='k-dropdown-wrap k-state-default'])[2]")
	private WebElement state_Dropdown;
	
	@FindBy(linkText="Back to Dashboard")
	private WebElement backToDashBoard_Btn;
	
	
	
	public Cust_EmergencyContactPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
}
