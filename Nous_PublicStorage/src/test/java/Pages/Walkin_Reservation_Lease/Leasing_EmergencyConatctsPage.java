package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Leasing_EmergencyConatctsPage 
{
	@FindBy(id="EmergencyContactForm_EmergencyContacts_0__FirstName")
	private WebElement emg_FirstName;
	
	@FindBy(id="EmergencyContactForm_EmergencyContacts_0__MiddleInitial")
	private WebElement emg_MiddleName;
	
	@FindBy(id="EmergencyContactForm_EmergencyContacts_0__LastName")
	private WebElement emg_LastName;
	
	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header contact-relationship-type']//span[text()='Select']")
	private WebElement relationship_list;
	
	@FindBy(id="EmergencyContactForm_EmergencyContacts_0__Address_AddressLine1")
	private WebElement street1;

	@FindBy(id="EmergencyContactForm_EmergencyContacts_0__Address_AddressLine2")
	private WebElement street2;
	
	@FindBy(id="EmergencyContactForm_EmergencyContacts_0__Address_City")
	private WebElement city;

	@FindBy(xpath="//span[@class='k-dropdown-wrap k-state-default']/span[text()='State']")
	private WebElement state_list;

	@FindBy(id="EmergencyContactForm_EmergencyContacts_0__Address_PostalCode")
	private WebElement zipCode;

	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header phoneDrop phone-type js-phone js-phone-type float-left margin-right']//span[text()='Select']")
	private WebElement PhoneType_list;

	@FindBy(id="EmergencyContactForm_EmergencyContacts_0__Phone_AreaCode")
	private WebElement areaCode;

	@FindBy(id="EmergencyContactForm_EmergencyContacts_0__Phone_Exchange")
	private WebElement phone_Exchg;

	@FindBy(id="EmergencyContactForm_EmergencyContacts_0__Phone_LineNumber")
	private WebElement phone_LineNumber;

	
	@FindBy(id="EmergencyContactForm_EmergencyContacts_0__Email_Email")
	private WebElement email_Addr;
	
	@FindBy(linkText="Confirm With Customer")
	private WebElement confirmWithCust;
	
	@FindBy(xpath="//div[starts-with(@id,'rentalunit')]//div[contains(text(),'Authorized For:')]/following-sibling::div//span[@class='button']")
	private WebElement authorizedfor_radio;
	
	@FindBy(id="customerVerifyButton")
	private WebElement verify_btn;
	
	@FindBy(xpath="//div[starts-with(@id,'address')]//div[@class='verificationElement verification-failed margin']//div/a[contains(text(),'Select')]")
	private WebElement overrideAddress_Select;
	
	@FindBy(xpath="//div[starts-with(@id,'phone')]//div[@class='verificationElement verification-failed margin']//div/a[contains(text(),'Select')]")
	private WebElement overridePhone_Select;
	
	@FindBy(xpath="//a[@id='notes-add']")
	private WebElement addNotesLink;
	
	@FindBy(xpath="//span[text()='Customer declines to provide an Emergency Contact']/preceding-sibling::span")
	private WebElement custDeclines_chkBox;
	
	public Leasing_EmergencyConatctsPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public boolean verify_overrideAddress_Select()
	{
		return overrideAddress_Select.isDisplayed();
	}
	
	public void click_overrideAddress_Select()
	{
		overrideAddress_Select.click();
	}
	
	public boolean verify_overridePhone_Select()
	{
		return overridePhone_Select.isDisplayed();
	}
	
	public void click_overridePhone_Select()
	{
		overridePhone_Select.click();
	}
	
	
	
	public void enterEmergencyFN(String value)
	{
		emg_FirstName.sendKeys(value);
	}
	
	public void enterEmergencyMN(String value)
	{
		emg_MiddleName.sendKeys(value);
	}
	
	public void enterEmergencyLN(String value)
	{
		emg_LastName.sendKeys(value);
	}
	
	public void clickRelationship_dropdown()
	{
		relationship_list.click();
	}
	
	public void txt_street1(String street){
		street1.sendKeys(street);
	}

	public void txt_city(String cityName){
		city.sendKeys(cityName);
	}
	
	public void clickStateList()
	{
		state_list.click();
	}
	
	public void enterZipCode(String value)
	{
		zipCode.sendKeys(value);
	}
	
	public void clickPhoneType()
	{
		PhoneType_list.click();
	}
	
	
	public void txt_AreaCode(String areacode){
		areaCode.sendKeys(areacode);

	}


	public void txt_Exchg(String exchg){
		phone_Exchg.sendKeys(exchg);

	}


	public void txt_lineNumber(String lineNum){
		phone_LineNumber.sendKeys(lineNum);

	}

	public void txt_email(String email){
		email_Addr.sendKeys(email);

	}
	
	public void clickAuthorizedfor_radio()
	{
		authorizedfor_radio.click();
	}
	
	public boolean verifyCustVerify_btn()
	{
		return verify_btn.isDisplayed();
	}
	
	public void clickVerify_btn()
	{
		verify_btn.click();
	}
	
	public void clickconfirmWithCust()
	{
		confirmWithCust.click();
	}
	public void click_custDeclines_chkBox(){
		custDeclines_chkBox.click();
	}
	
	public void clickAddNotesBtn(){
		addNotesLink.click();
	}

}
