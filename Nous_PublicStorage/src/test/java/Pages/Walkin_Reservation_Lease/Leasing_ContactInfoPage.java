package Pages.Walkin_Reservation_Lease;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Leasing_ContactInfoPage {
	
	WebDriver driver;

	@FindBy(xpath="//label[contains(text(),'Individual Lease')]")
	private WebElement lbl_IndvdlLease;

	@FindBy(xpath="//label[@for='lease-type']/div[@class='onoffswitch-switch']")
	private WebElement toggle_IndvdlLease;

	@FindBy(xpath="//input[@id='lesseeinfo-contact-firstname']")
	private WebElement fName;

	@FindBy(id="ContactForm_Contact_MiddleInitial")
	private WebElement MidName;

	@FindBy(xpath="//input[@id='lesseeinfo-contact-lastname']")
	private WebElement LName;

	@FindBy(id="nameHeading")
	private WebElement lbl_Name;
	
	@FindBy(linkText ="Back To Dashboard")
	private WebElement BackToDshBrd;
	
	public void click_BackToDshBrd(){
		BackToDshBrd.click();
	}
	

	@FindBy(xpath="//div[@class='editor-label-with-validation-spacing floatleft'][contains(text(),'Identification:')]")
	private WebElement lbl_Identification;

	@FindBy(xpath="//div[@class='editor-label-with-validation-spacing floatleft'][contains(text(),'Address:')]")
	private WebElement lbl_Address;

	@FindBy(xpath="//div[@class='editor-label-with-validation-spacing floatleft'][contains(text(),'Phone Number:')]")
	private WebElement lbl_PhnNo;

	@FindBy(xpath="//div[@id='emailheading'][contains(text(),'Email Address:')]")
	private WebElement lbl_Email;

	@FindBy(xpath="//span[@class='k-input'][text()='Driver License']/following-sibling::span//span[text()='select']")
	private WebElement sel_Identification;

	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header stateDrop identstateDrop']") //span[@class='k-input'][contains(text(),'State')]/..//following-sibling::input[@id='ContactForm_Identification_StateTypeID']
	private WebElement sel_State;

	@FindBy(id="ContactForm_Identification_EncryptedItem_EncryptedIdDisplay")
	private WebElement number;

	@FindBy(xpath="//input[@id='lesseeinfo-address-line1']")
	private WebElement street1;

	@FindBy(xpath="//input[@id='lesseeinfo-address-line2']")
	private WebElement street2;

	@FindBy(xpath="//input[@name='ContactForm.LesseeAddress.Address.International']/following-sibling::span[@class='button']")
	private WebElement chkBox;

	@FindBy(xpath="//input[@name='ContactForm.LesseeAddress.Address.International']/following-sibling::span[@class='button']/following-sibling::span")
	private WebElement intl;

	@FindBy(xpath="//input[@id='lesseeinfo-address-city']")
	private WebElement city;

	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header stateDrop js-stateDrop']") //input[@id='lesseeinfo-address-statecode']/preceding-sibling::span/span/span[contains(text(),'select')]")
	private WebElement sel_State2;

	@FindBy(xpath="//input[@id='lesseeinfo-address-postalcode']")
	private WebElement zipCode;

	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header phoneDrop floatleft margin-right']") //input[@id='ContactForm_LesseePhones[_-index-__0]_Phone_PhoneTypeID']/preceding-sibling::span/span/span[contains(text(),'select')]")
	private WebElement sel_PhoneType;

	@FindBy(xpath = "//span[text()='No Email']/preceding-sibling::span")
	private WebElement chkbx_Noemail;
	
	@FindBy(xpath="//input[@id='ContactForm_LesseePhones__-index-__0__Phone_AreaCode']")
	private WebElement areaCode;

	@FindBy(xpath="//input[@id='ContactForm_LesseePhones__-index-__0__Phone_Exchange']")
	private WebElement phone_Exchg;

	@FindBy(xpath="//input[@id='ContactForm_LesseePhones__-index-__0__Phone_LineNumber']")
	private WebElement phone_LineNumber;

	@FindBy(xpath="//input[@id='ContactForm_LesseePhones__-index-__0__Phone_Extension']")
	private WebElement phone_Extn;

	@FindBy(xpath="//input[@id='ContactForm_LesseeEmails__-index-__0__EmailAddress_Email']")
	private WebElement email_Addr;

	@FindBy(xpath="//input[@name='ContactForm.HasNoEmail']/following-sibling::span[@class='button']")
	private WebElement chkBox_Email;

	@FindBy(xpath="//input[@name='ContactForm.HasNoEmail']/following-sibling::span[text()='No Email']")
	private WebElement lbl_NoEmail;

	//-------------------------------------------------------------------------------------------

	@FindBy(xpath="//label[@for='lease-type']/div[@class='onoffswitch-inner']")
	private WebElement toggle_BusinessLease;

	@FindBy(xpath="//label[contains(text(),'Business Lease')]")
	private WebElement lbl_BusinessLease;

	@FindBy(xpath="//div[contains(text(),' Business Name')]")
	private WebElement lbl_BusinessName;

	@FindBy(id="companyName")
	private WebElement businessname;

	@FindBy(id="nameHeading")
	private WebElement lbl_Contact;

	@FindBy(id="lesseeinfo-contact-firstname")
	private WebElement lbl_BusFName;

	@FindBy(id="ContactForm_Contact_MiddleInitial")
	private WebElement lbl_BusMidName;

	@FindBy(id="lesseeinfo-contact-lastname")
	private WebElement lbl_BusLName;

	@FindBy(id="ContactForm_Contact_Title")
	private WebElement title;

	@FindBy(id="ContactForm_Contact_DepartmentOrUnit")
	private WebElement  dept;         

	@FindBy(xpath="//div[contains(text(),'Identification')]")
	private WebElement lbl_BusIdentification;

	@FindBy(xpath="//span[contains(text(),'Driver License')]//following-sibling::span/span[text()='select']")
	private WebElement sel_BusIdentification;

	@FindBy(xpath="//input[@id='ContactForm_Identification_StateTypeID']/preceding-sibling::span/span[contains(text(),'State')]")
	private WebElement sel_BusState;

	@FindBy(id="ContactForm_Identification_EncryptedItem_EncryptedIdDisplay")
	private WebElement lbl_BusNumber;

	@FindBy(xpath="//div[@id='lesseeAddressInput']/preceding-sibling::div[contains(text(),'Address')]")
	private WebElement lbl_BusAddress;

	@FindBy(xpath="//div[contains(text(),'Phone Number')]")
	private WebElement lbl_BusPhoneNum;

	@FindBy(id="emailheading")
	private WebElement lbl_BusEmail;

	//=========================================

	@FindBy(partialLinkText="Customer Lookup")
	private WebElement btn_CustlookUp;

	@FindBy(xpath="//div[@id='contactInfo']//div[@class='onoffswitch']")
	private WebElement toggle_BusilLease;

	@FindBy(xpath="//div[@id='lesseeAddressInput']//div[@class='verificationElement verification-failed margin']//div/a[contains(text(),'Select')]")
	private WebElement overrideAddress_Select;

	@FindBy(xpath="//div[@class='section-content phones contactphones floatleft']//div[@class='verificationElement verification-failed margin']//div[@class='floatright']/a[contains(text(),'Select')]")
	private WebElement overridePhone_Select;

	@FindBy(xpath="//input[@id='companyName']")
	private WebElement companyName;


	@FindBy(xpath="//input[@id='ContactForm_Contact_DepartmentOrUnit']")
	private WebElement  deptName;
	
	@FindBy(id="lesseeinfo-address-line1")
	private WebElement add_FirstLine;
	
	@FindBy(id="lesseeinfo-address-city")
	private WebElement add_City;
	
	@FindBy(id="lesseeinfo-address-postalcode")
	private WebElement add_Zipcode;
	
	@FindBy(xpath="//a[@id='notes-add']")
	private WebElement addNotesLink;
	
	@FindBy(xpath="//label[@id='currently-military-true']/span[1]")
	private WebElement radioYesBtn;
	
	@FindBy(xpath="//label[@id='currently-military-false']/span[1]")
	private WebElement radioNoBtn;
	
	@FindBy(id="customerVerifyButton")
	private WebElement verifyBtn;
	
	@FindBy(xpath="//a[@id='confirmWithCustomer']/span[contains(text(),'Confirm With Customer')]")
	private WebElement confirmWithCust;
	
	@FindBy(xpath="//div[@id='lesseeAddressInput']//a[contains(text(),'Use Selected Address')]")
	private WebElement useSelectedAddress;

	@FindBy(xpath="//div[@id='selectAccountDialogForm']/div[@class='account-none']/a[1]")
	private WebElement createNewCustomer;
	
	@FindBy(xpath="//div[@id='lesseeAddressInput']/div[@class='verificationElement verification-failed margin']//a[contains(text(),'Select')]")
	private WebElement overrideAddress;
	
	@FindBy(xpath="//ul[@id='phoneList']//div[@class='verificationElement verification-failed margin']//a[contains(text(),'Select')]")
	private WebElement overridePhone;
	

	@FindBy(xpath = "//div[@id='LesseeInfoFormToRefresh']//label[@class='webchamp-radio-button margin-right can-legally-rent']/span[@class='button']")
	private WebElement con_LegallyRentOnBehalf_Cmpny;
	

	@FindBy(xpath = "//input[@id='currently-military-false']/following-sibling::span[text()='No']")
	private WebElement clk_ActiveDutyMilitaryNoRadioBtn;

	@FindBy(xpath="//input[@id='currently-military-true']/following-sibling::span[text()='Yes']")
	private WebElement clk_ActiveDutyMilitaryYesRadioBtn;
	//div[@class='section-content phones contactphones floatleft']//div[@class='verificationElement verification-failed margin']//div/a[contains(text(),'Select')]

	@FindBy(xpath="//div[text()='Can this contact legally rent on behalf of the company?:']/following-sibling::div/label/input[@id='ContactForm_CanLegallyRent']/following-sibling::span[text()='Yes']")
	private WebElement clk_CanThisContactLegallyRentonBehalfOfTheCompanyYesRadioBtn;
	public Leasing_ContactInfoPage(WebDriver driver)
	{

		this.driver=driver;
		PageFactory.initElements(driver,this);
	}

	public void click_BusinessLeaseToggle()
	{
		toggle_BusilLease.click();
	}

	public void enterCompanyName(String name)
	{
		companyName.sendKeys(name);
	}

	public void enterTitle(String ctitle)
	{
		title.sendKeys(ctitle);
	}

	public void enterDepartmentName(String dept)
	{
		deptName.sendKeys(dept);
	}


	public void txt_Fname(String fname){
		fName.sendKeys(fname);
	}

	public void txt_Lname(String lname){
		LName.sendKeys(lname);
	}

	public void clickContact_State(){
		sel_State.click();
	}

	//	public void select_State(String expVal){
	//		sel_State.click();
	//		List<WebElement> ele=driver.findElements(By.xpath("//ul[@id='ContactForm_Identification_StateTypeID_listbox']/li"));
	//		System.out.println("Size of the list is:"+ele.size());
	//		Iterator<WebElement> itr=ele.iterator();
	//		while(itr.hasNext()){
	//			WebElement actualWbEle=itr.next();
	//			String actualWbEleText=actualWbEle.getText();
	//			if(actualWbEleText.equalsIgnoreCase(expVal)) {
	//				actualWbEle.click();
	//			}
	//		}
	//	}

	public void txt_Number(String dLNum){
		number.sendKeys(dLNum);
	}

	public void txt_street1(String street){
		street1.sendKeys(street);
	}

	public void txt_city(String cityName){
		city.sendKeys(cityName);
	}
	public void select_State2address(){
		sel_State2.click();
	}
		public void select_State2(String expVal){
			sel_State2.click();
			List<WebElement> ele=driver.findElements(By.xpath("//ul[@id='lesseeinfo-address-statecode_listbox']/li"));
			System.out.println("Size of the list is:"+ele.size());
			Iterator<WebElement> itr=ele.iterator();
			while(itr.hasNext()){
				WebElement actualWbEle=itr.next();
				String actualWbEleText=actualWbEle.getText();
				if(actualWbEleText.equalsIgnoreCase(expVal)){ actualWbEle.click();
				}
			}}


	public void txt_Zipcode(String zipcode){
		zipCode.sendKeys(zipcode);
	}

	//	public void select_phoneType(){
	//		sel_PhoneType.click();
	//		driver.findElement(By.xpath("//div[@id='ContactForm_LesseePhones[_-index-__0]_Phone_PhoneTypeID-list']/..//ul/li[text()='Cell']"));
	//
	//	}
	public void select_phoneType1(){
		sel_PhoneType.click();
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

	public void click_CustLookUp(){
		btn_CustlookUp.click();

	}
	public void toggleIndivBusi(){
		toggle_IndvdlLease.click();
	}
	
	public void enter_CompanyName(String company){
		businessname.sendKeys(company);
		
	}
	
	public void clickAddNotesBtn(){
		addNotesLink.click();
	}
	public void enter_FirstName(String firstname){
		lbl_BusFName.sendKeys(firstname);
		
	}
	
	public void enter_LastName(String lastname){
		lbl_BusLName.sendKeys(lastname);
		
	}
	
	
	
	public void enter_AddressFirst(String add){
		add_FirstLine.sendKeys(add);
		
	}
	
	public void enter_AddressCity(String add){
		add_City.sendKeys(add);
		
	}

	public void enter_AddressZipcode(String add){
		add_Zipcode.sendKeys(add);
		
	}
	
	public void enter_IdenNumber(String iden){
		lbl_BusNumber.sendKeys(iden);
		
	}
	
	public void enter_Title(String tit){
		title.sendKeys(tit);
	}
	
	public void select_DriBusiState(String expVal){
		sel_BusState.click();
		List<WebElement> ele=driver.findElements(By.xpath("//div[@id='ContactForm_Identification_StateTypeID-list']/ul[@id='ContactForm_Identification_StateTypeID_listbox']/li"));
		System.out.println("Size of the list is:"+ele.size()); 
		Iterator<WebElement> itr=ele.iterator();
        while(itr.hasNext()){
               WebElement actualWbEle=itr.next();
               String actualWbEleText=actualWbEle.getText();
               if(actualWbEleText.equalsIgnoreCase(expVal)) {
            	   actualWbEle.click();
        }
        }}
	
	
	public void select_BusiState(String expVal){
		sel_State2.click();
		List<WebElement> ele=driver.findElements(By.xpath("//div[@id='lesseeinfo-address-statecode-list']/ul[@id='lesseeinfo-address-statecode_listbox']/li"));
		System.out.println("Size of the list is:"+ele.size());
		Iterator<WebElement> itr=ele.iterator();
        while(itr.hasNext()){
               WebElement actualWbEle=itr.next();
               String actualWbEleText=actualWbEle.getText();
               if(actualWbEleText.equalsIgnoreCase(expVal)) {
            	   actualWbEle.click();
        }
        }}
	
	public void clk_createNewCust(){
		createNewCustomer.click();
	}
	
	public void clk_YesRadioBtn(){
		radioYesBtn.click();
	}
	
	public void clk_NoRadioBtn(){
		radioNoBtn.click();
	}
	
	
	public void clk_VerifyBtn(){
		verifyBtn.click();
	}
	
	public void clk_ConfirmBtn(){
		confirmWithCust.click();
	}
	
	
	public boolean verify_overrideAddress_Select(){
		return overrideAddress.isDisplayed();
	}
	
	public void click_overrideAddress_Select(){
		overrideAddress.click();
	}
	
	public boolean verify_overridePhone_Select(){
		return overridePhone.isDisplayed();
	}
	
	public void click_overridePhone_Select(){
		overridePhone.click();
	}
	
	public void clk_legallyonBehlf_Cmpny() {

		con_LegallyRentOnBehalf_Cmpny.click();
	}
	
	public void clk_ActiveDutyMilitaryNoRadioBtn(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(clk_ActiveDutyMilitaryNoRadioBtn)).click();
	}


	public void clk_ActiveDutyMilitaryYesRadioBtn(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(clk_ActiveDutyMilitaryYesRadioBtn)).click();
	}

	public boolean iscActiveDutyMilitaryYesRadioBtnDisplayed(){

		return clk_ActiveDutyMilitaryYesRadioBtn.isDisplayed();
	}

	public void clk_CanThisContactLegallyRentonBehalfOfTheCompanyYesRadioBtn(){

		clk_CanThisContactLegallyRentonBehalfOfTheCompanyYesRadioBtn.click();
	}
	
	public void clk_NoEmailChkbx(){
		chkbx_Noemail.click();
	}
	
	@FindBy(xpath="//a[@class='file-hub-buttons-upload psbutton-normal']")
	public WebElement upload_btn;
	
	@FindBy(xpath="//label[@class='webchamp-radio-button tax-options margin-right'][1]/span[@class='button']")
	private WebElement TaxExempt_YesRadioButton;
	
	
	@FindBy(xpath="//input[@id='ContactForm_TaxExempt_EncryptedItem_EncryptedIdDisplay']")
	private WebElement TaxExemptNumber;
	
	
	@FindBy(xpath="//div[@id='militaryTaxFormContainer']//div//label[@class='webchamp-radio-button margin-right can-legally-rent']//span[@class='button']")
	private WebElement CanThisContactLegallyRentonBehalfOfTheCompanyYesRadioBtn;
	
	
	@FindBy(xpath="//label[@class='webchamp-checkbox']/span[@class='button']")
	public WebElement NoExpirationDate_Chk;
	
	
	@FindBy(xpath="//input[@id='ContactForm_TaxExempt_ExpirationMonth']")
	public WebElement month_txt;
	
	public void clk_TaxExempt_YesRadioButton(){
		TaxExempt_YesRadioButton.click();
	}
	
	public void enter_TaxExemptNumber(String Number){
		TaxExemptNumber.sendKeys(Number);
		
	}
	
	
	
	public void clk_NoExpirationDate_Chk(){
		NoExpirationDate_Chk.click();
	}
	
	public void clk_upload_btn(){
		upload_btn.click();
	}
	
	public void clk_ContactLegallyRentonBehalfOfTheCompanyYesRadioBtn(){
		CanThisContactLegallyRentonBehalfOfTheCompanyYesRadioBtn.click();
	}

}

