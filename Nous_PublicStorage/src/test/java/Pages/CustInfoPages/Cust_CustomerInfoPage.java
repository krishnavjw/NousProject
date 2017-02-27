package Pages.CustInfoPages;

import java.sql.Time;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Cust_CustomerInfoPage {

	WebDriver driver;
	WebDriverWait wait;

	@FindBy(xpath = "//div[@class='page-header container-heading clearfix-container']/h3[@class='floatleft']")
	private WebElement pageTitle;

	@FindBy(xpath = "//input[@id='IndividualInformationModel_FirstName']")
	private WebElement Txt_firstName;

	@FindBy(xpath = "//input[@id='IndividualInformationModel_MiddleInitial']")
	private WebElement Txt_MiddleName;

	@FindBy(xpath = "//input[@id='IndividualInformationModel_LastName']")
	private WebElement Txt_LastName;

	@FindBy(xpath = "//input[@id='IndividualInformationModel_ContactIdentification_EncryptedItem_EncryptedIdDisplay']")
	private WebElement Txt_Encrypted;

	/*@FindBy(xpath = "//input[@id='Addresses_OfficialAddress_StreetAddress1']")
	private WebElement Txt_Address;*/

	@FindBy(xpath = "//input[@id='Addresses_OfficialAddress_StreetAddress2']")
	private WebElement Txt_Address2;

	@FindBy(xpath = "//input[@id='Addresses_OfficialAddress_City']")
	private WebElement Txt_City;

	@FindBy(xpath = "//input[@id='lesseeinfo-address-postalcode']")
	private WebElement Txt_PostalCode;

	@FindBy(xpath = "//div[@class='international double-margin-top']/label/span[@class='button']")
	private WebElement Intl_RadioBtn;

	/*@FindBy(xpath = "//input[@id='Phones_OfficialPhones__-index-__0__AreaCode']")
	private WebElement Txt_phoneAreaCode;

	@FindBy(xpath = "Phones_OfficialPhones__-index-__0__Exchange")
	private WebElement Txt_phoneExchange;

	@FindBy(xpath = "Phones_OfficialPhones__-index-__0__LineNumber'")
	private WebElement Txt_phoneLineNumber;*/

	@FindBy(xpath = "//input[@id='Emails_OfficialEmails__-index-__0__Email']")
	private WebElement Txt_email;

	@FindBy(xpath = "//a[@class='psbutton-low-priority floatright']")
	private WebElement cancel_Btn;

	@FindBy(xpath = "//a[@id='verifyButton']")
	private WebElement verify_Btn;

	@FindBy(id = "confirmButton")
	private WebElement confirmBtn;

	@FindBy(xpath = "//span[@class='k-widget k-dropdown k-header identDrop']")
	private WebElement identification_Dropdown;

	@FindBy(xpath = "//span[@class='k-widget k-dropdown k-header phoneDrop floatleft margin-right js-phone']")
	private WebElement phoneCategory_Dropdown;

	@FindBy(xpath = "(//span[@class='k-dropdown-wrap k-state-default'])[2]")
	private WebElement state_Dropdown;

	@FindBy(linkText = "Back to Dashboard")
	private WebElement backToDashBoard_Btn;

	@FindBy(xpath = "//div[contains(text(),'Current Account:')]/..//div[@class='bold half-padding-bottom']/following-sibling::div")
	private WebElement txt_AccountNO;

	@FindBy(xpath = "//label[@class='webchamp-checkbox']//span[contains(text(),'Need new contact email address.')]")
	private WebElement newContactEmail;

	@FindBy(xpath = "//input[@id='Emails_UnofficialEmails__-index-__0__Email']")
	private WebElement txt_Email_unofficial;

	@FindBy(xpath = "//div[@class='k-window-content k-content']//a[contains(text(),'Yes')]")
	private WebElement yesBtnPleaseConfirm;

	@FindBy(xpath = "//div[@id='lesseeEmailAddresses']//ul[@id='officialEmailList']/li[2]//span[@class='icon list-delete']")
	private WebElement remove_Sign;

	@FindBy(xpath = "//div[@id='lesseeEmailAddresses']//button[contains(text(),'Add Unofficial Email Addresses')]")
	private WebElement addUnofficialBtn;

	@FindBy(xpath = "//ul[@id='officialEmailList']/li[1]//div[@class='primary-email-div section-content floatright']//span[@class='button']")
	private WebElement preferedRadiBtn;

	@FindBy(xpath = "//div[@id='lesseeEmailAddresses']/h3[starts-with(text(),'Unofficial')]")
	private WebElement UnofficialEmailAddress_Txt;

	@FindBy(xpath = "//ul[@id='unofficialEmailList']/li[1]//button[contains(text(),'Make Official')]")
	private WebElement makeOfficialLink;

	@FindBy(xpath = "//ul[@id='officialEmailList']/li//div[contains(text(),'Please enter a valid email address')]")
	private WebElement validMsg;

	@FindBy(xpath = "//a[contains(text(),'Approve')]")
	private WebElement approveBtn;

	@FindBy(xpath = "//a[contains(text(),'Save')]")
	private WebElement saveBtn;

	@FindBy(xpath = "//input[@id='Addresses_OfficialAddress_State']/preceding-sibling::span")
	private WebElement sel_State2;
	// ============================================================================================

	@FindBy(xpath = "//input[@id='Addresses_OfficialAddress_StreetAddress1']")
	private WebElement streetAddress;

	@FindBy(xpath = "//input[@id='Addresses_OfficialAddress_City']")
	private WebElement entercity;

	@FindBy(xpath = "//input[@id='Addresses_OfficialAddress_State']/preceding-sibling::span[contains(@class,'k-dropdown-wrap')]")
	private WebElement addressState;

	@FindBy(xpath = "//input[@id='IndividualInformationModel_ContactIdentification_StateTypeID']/preceding-sibling::span[contains(@class,'k-dropdown-wrap')]")
	private WebElement customerInfoState;

	@FindBy(xpath = "//input[@id='lesseeinfo-address-postalcode']")
	private WebElement postalCode;

	@FindBy(xpath = "//*[@id='officialPhoneList']/li[2]/div[2]/a[1]")
	private WebElement removeOtherPhones;

	@FindBy(xpath = "//input[contains(@id,'Phones_OfficialPhones[_-index-__0]_PhoneTypeID')]//preceding-sibling::span")
	private WebElement phoneType;

	@FindBy(xpath = "//*[@id='Phones_OfficialPhones__-index-__0__AreaCode']")
	private WebElement phoneAreaCode;

	@FindBy(xpath = "//*[@id='Phones_OfficialPhones__-index-__0__Exchange']")
	private WebElement phoneExchangeCode;

	@FindBy(xpath = "//*[@id='Phones_OfficialPhones__-index-__0__LineNumber']")
	private WebElement phoneLineNumber;

	@FindBy(xpath = "//span[text()='No Email']/preceding-sibling::span")
	private WebElement noEmailCheckBox;

	@FindBy(xpath = "//a[@id='verifyButton']")
	private WebElement verifybutton;

	@FindBy(xpath = "//span[@class='k-widget k-dropdown k-header stateDrop identstateDrop']")
	private WebElement sel_State;

	@FindBy(xpath = "//input[@id='IndividualInformationModel_ContactIdentification_IdentificationTypeID']/preceding-sibling::span")
	private WebElement proofType;

	@FindBy(xpath = "//a[@id='confirmButton']")
	private WebElement confirmWithCustomer;

	@FindBy(xpath = "//input[@id='employeeNumber']")
	public WebElement enterEmployeeNumber;

	@FindBy(xpath = "//input[contains(@placeholder,'Position')]")
	public WebElement Position;

	@FindBy(xpath = "//div[@id='lesseeEmailAddresses']//div[@class='js-no-email']//span[text()='No Email']")
	public WebElement chk_NoEmail;

	@FindBy(xpath = "//input[@id='Addresses_OfficialAddress_StreetAddress1']")
	private WebElement Txt_Address;

	@FindBy(xpath = "//input[@id='Phones_OfficialPhones__-index-__0__AreaCode']")
	private WebElement Txt_phoneAreaCode;

	@FindBy(xpath = "//input[@id='Phones_OfficialPhones__-index-__0__Exchange']")
	private WebElement Txt_phoneExchange;

	@FindBy(xpath = "//input[@id='Phones_OfficialPhones__-index-__0__LineNumber']")
	private WebElement Txt_phoneLineNumber;
	
	@FindBy(xpath="//input[@id='IndividualInformationModel_ContactIdentification_CountryTypeID']/preceding-sibling::span")
    private WebElement country1;

	public Cust_CustomerInfoPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, 60);
	}
	
	public void clk_Country(){
        country1.click();
 }

	public void enterEmployeeNum(String number) {
		enterEmployeeNumber.sendKeys(number);
	}

	public void clickConfirmWithCustomer(WebDriver driver) {
		wait.until(ExpectedConditions.elementToBeClickable(confirmWithCustomer)).click();
	}

	public void enterLicenseNumber(String licenseNumber) {
		Txt_Encrypted.clear();
		Txt_Encrypted.sendKeys(licenseNumber);
	}

	public void clk_selectIdentificationType() {
		proofType.click();
	}

	public void enterFirstName(String firstname) {
		Txt_firstName.clear();
		Txt_firstName.sendKeys(firstname);
	}

	public void enterMiddleName(String middlename) {
		Txt_MiddleName.clear();
		Txt_MiddleName.sendKeys(middlename);
	}

	public void enterLastName(String lastname) {
		Txt_LastName.clear();
		Txt_LastName.sendKeys(lastname);
	}

	public void selectState() {
		sel_State.click();
	}

	public void clk_verifyButton() {
		verifybutton.click();
	}

	public void clickNoEmailCheckBox() {
		noEmailCheckBox.click();
	}

	public void enterAreaCode(String address) {
		phoneAreaCode.clear();
		phoneAreaCode.sendKeys(address);
	}

	public void enterExchangeCode(String address) {
		phoneExchangeCode.clear();
		phoneExchangeCode.sendKeys(address);
	}

	public void enterLineNumber(String address) {
		phoneLineNumber.clear();
		phoneLineNumber.sendKeys(address);
	}

	public void enterStreetAddress(String address) {
		streetAddress.clear();
		streetAddress.sendKeys(address);
	}

	public void enterCity(String city) {
		entercity.clear();
		entercity.sendKeys(city);
	}

	public void selectStateUnderAddress() {
		addressState.click();
	}

	public void selectStateUnderCustomerInfo() {
		customerInfoState.click();
	}

	public void click_PhoneType() {
		phoneType.click();
	}

	public void selectPhone(String phonetype) throws InterruptedException {

		List<WebElement> phoneNumberType = driver.findElements(
				By.xpath("//ul[contains(@id,'Phones_OfficialPhones[_-index-__0]_PhoneTypeID_listbox')]/li"));

		for (int i = 0; i < phoneNumberType.size(); i++) {
			if (phoneType.equals(phoneNumberType.get(i).getText())) {
				phoneNumberType.get(i).click();
				break;
			}
		}
	}

	public void enterPostalCode(String code) {
		postalCode.clear();
		postalCode.sendKeys(code);
	}

	public void removeOtherPhoneNumbersifAvailable() {
		try {
			if (removeOtherPhones.isDisplayed()) {
				removeOtherPhones.click();
			}
		} catch (Exception e) {
			return;
		}
	}

	public String get_txt_AccountNO() {
		String ac = txt_AccountNO.getText();
		return ac;
	}

	public void selectState(String state) {

		Select sel = new Select(state_Dropdown);
		sel.selectByVisibleText(state);
	}

	public void click_backToDashboard() {
		backToDashBoard_Btn.click();
	}

	public boolean isDisplayedAlertNewContact() {
		return newContactEmail.isDisplayed();
	}

	public boolean isDisplayedRemoveSign() {
		return remove_Sign.isDisplayed();
	}

	public boolean isDisplayedPreferedRadioBtn() {
		return preferedRadiBtn.isDisplayed();
	}

	public boolean UnofficialEmailAddress_TxtDisplayed() {

		return UnofficialEmailAddress_Txt.isDisplayed();
	}

	public boolean isDisplayedMakeOfficialLink() {

		return makeOfficialLink.isDisplayed();
	}

	public boolean isVerifyBtn() {
		return verify_Btn.isDisplayed();
	}

	public void enterAreacode(String area) {
		Txt_phoneAreaCode.clear();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Txt_phoneAreaCode.sendKeys(area);
	}

	public void enterExchangeNum(String exchange) throws InterruptedException {
		Txt_phoneExchange.clear();
		Thread.sleep(3000);
		Txt_phoneExchange.sendKeys(exchange);
	}

	public void enterLocalNum(String local) throws InterruptedException {
		Txt_phoneLineNumber.clear();
		Thread.sleep(3000);
		Txt_phoneLineNumber.sendKeys(local);
	}

	public void clk_verifyBtn() {
		verify_Btn.click();
	}

	public void enterEmail(String email) {
		Txt_email.clear();
		Txt_email.sendKeys(email);
	}

	public boolean isDisplayedValidationMsg() {
		return validMsg.isDisplayed();
	}

	public boolean isDisplayedAddUnofficialBtn() {
		return addUnofficialBtn.isDisplayed();
	}

	public void clk_AddUnofficialBtn() {
		addUnofficialBtn.click();
	}

	public void enterEmailUnofficial(String email) {
		txt_Email_unofficial.clear();
		txt_Email_unofficial.sendKeys(email);
	}

	public void clk_MakeOfficialBtn() {
		makeOfficialLink.click();
	}

	public void clk_YesBtnPlsConfirm() {
		yesBtnPleaseConfirm.click();
	}

	public void enterAddress(String Add) {
		Txt_Address.clear();
		Txt_Address.sendKeys(Add);
	}

	public void enterAddress2(String Add2) {
		Txt_Address2.clear();
		Txt_Address2.sendKeys(Add2);
	}

	public void enterZipcode(String zipcode) {
		Txt_PostalCode.clear();
		Txt_PostalCode.sendKeys(zipcode);
	}

	public void clk_ApproveBtn() {
		approveBtn.click();
	}

	public void clk_SaveBtn() {
		saveBtn.click();
	}

	public void clk_NoEmailBtn() {
		chk_NoEmail.click();
	}

	public void enterPosition(String position) {
		Position.clear();
		Position.sendKeys(position);
	}

	public void select_State(String expVal) {
		sel_State2.click();
		List<WebElement> ele = driver.findElements(By.xpath(
				"//div[@id='Addresses_OfficialAddress_State-list']/ul[@id='Addresses_OfficialAddress_State_listbox']/li"));
		System.out.println("Size of the list is:" + ele.size());
		Iterator<WebElement> itr = ele.iterator();
		while (itr.hasNext()) {
			WebElement actualWbEle = itr.next();
			String actualWbEleText = actualWbEle.getText();
			if (actualWbEleText.equalsIgnoreCase(expVal)) {
				actualWbEle.click();
			}
		}
	}

	public void clk_ConfirmBtn() {
		confirmBtn.click();
	}

	public void select_phoneType(String expVal) {
		phoneCategory_Dropdown.click();
		List<WebElement> ele = driver
				.findElements(By.xpath("//ul[@id='Phones_OfficialPhones[_-index-__0]_PhoneTypeID_listbox']/li"));
		System.out.println("Size of the list is:" + ele.size());
		Iterator<WebElement> itr = ele.iterator();
		while (itr.hasNext()) {
			WebElement actualWbEle = itr.next();
			String actualWbEleText = actualWbEle.getText();
			if (actualWbEleText.equalsIgnoreCase(expVal)) {
				actualWbEle.click();
			}
		}
	}
	
	public String getLicense(){
		return Txt_Encrypted.getAttribute("value");
	}
	
	
	
	@FindBy(id = "Emails_OfficialEmails__-index-__0__Email")
	private WebElement officialemail_field;
	
	@FindBy(id = "Emails_OfficialEmails__-index-__0__Email")
	private WebElement unofficialemail_field;
	
	public boolean verify_officialemail_field(){
		return officialemail_field.isDisplayed();
	}
	
	public boolean verify_unofficialemail_field(){
		return unofficialemail_field.isDisplayed();
	}
	
	

}
