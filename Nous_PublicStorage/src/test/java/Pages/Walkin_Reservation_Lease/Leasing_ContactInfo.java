package Pages.Walkin_Reservation_Lease;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.formula.functions.WeekdayFunc;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Leasing_ContactInfo {
	
	@FindBy(xpath="//label[contains(text(),'Individual Lease')]")
	private WebElement lbl_IndvdlLease;
	
	@FindBy(xpath="//label[@for='lease-type']/div[@class='onoffswitch-switch']") 
	private WebElement toggle_IndvdlLease;
	
	@FindBy(id="lesseeinfo-contact-firstname")
	private WebElement fName;
		
	@FindBy(id="ContactForm_Contact_MiddleInitial")
	private WebElement MidName;
	
	@FindBy(id="lesseeinfo-contact-lastname")
	private WebElement LName;
	
	@FindBy(id="nameHeading")
	private WebElement lbl_Name;
	
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

	@FindBy(xpath="//span[@class='k-input'][contains(text(),'State')]/..//following-sibling::input[@id='ContactForm_Identification_StateTypeID']")
	private WebElement sel_State;
	
	@FindBy(id="ContactForm_Identification_EncryptedItem_EncryptedIdDisplay")
	private WebElement number;
	
	@FindBy(id="lesseeinfo-address-line1")
	private WebElement street1;
	
	@FindBy(id="lesseeinfo-address-line2")
	private WebElement street2;
	
	@FindBy(xpath="//input[@name='ContactForm.LesseeAddress.Address.International']/following-sibling::span[@class='button']")
	private WebElement chkBox;
	
	@FindBy(xpath="//input[@name='ContactForm.LesseeAddress.Address.International']/following-sibling::span[@class='button']/following-sibling::span")
	private WebElement intl;
	
	@FindBy(id="lesseeinfo-address-city")
	private WebElement city;
	
	@FindBy(xpath="//input[@id='lesseeinfo-address-statecode']/preceding-sibling::span/span/span[contains(text(),'select')]")
	private WebElement sel_State2;
	
	@FindBy(id="lesseeinfo-address-postalcode")
	private WebElement zipCode;
	
	@FindBy(xpath="//input[@id='ContactForm_LesseePhones[_-index-__0]_Phone_PhoneTypeID']/preceding-sibling::span/span/span[contains(text(),'select')]")
	private WebElement sel_PhoneType;
	
	@FindBy(id="ContactForm_LesseePhones__-index-__0__Phone_AreaCode") 
	private WebElement areaCode;
	
	@FindBy(id="ContactForm_LesseePhones__-index-__0__Phone_Exchange")
	private WebElement phone_Exchg;
	
	@FindBy(id="ContactForm_LesseePhones__-index-__0__Phone_LineNumber")
	private WebElement phone_LineNumber;
	
	@FindBy(id="ContactForm_LesseePhones__-index-__0__Phone_Extension")
	private WebElement phone_Extn;
	
	@FindBy(id="ContactForm_LesseeEmails__-index-__0__EmailAddress_Email")
	private WebElement email_Addr;
	
	@FindBy(xpath="//input[@name='ContactForm.HasNoEmail']/following-sibling::span[@class='button']")
	private WebElement chkBox_Email;
	
	@FindBy(xpath="//input[@name='ContactForm.HasNoEmail']/following-sibling::span[text()='No Email']")
	private WebElement lbl_NoEmail;
	
	@FindBy(xpath="//div[@id='selectAccountDialogForm']/div[@class='account-none']/a[1]")
	private WebElement createNewCustomer;
	
	@FindBy(xpath="//div[@id='lesseeAddressInput']/div[@class='verificationElement verification-failed margin']//a[contains(text(),'Select')]")
	private WebElement overrideAddress;
	
	@FindBy(xpath="//ul[@id='phoneList']//div[@class='verificationElement verification-failed margin']//a[contains(text(),'Select')]")
	private WebElement overridePhone;
	
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
	
	@FindBy(id="lesseeinfo-address-line1")
	private WebElement add_FirstLine;
	
	@FindBy(id="lesseeinfo-address-city")
	private WebElement add_City;
	
	@FindBy(id="lesseeinfo-address-postalcode")
	private WebElement add_Zipcode;
	
	
	@FindBy(xpath="//div[contains(text(),'Phone Number')]")
	private WebElement lbl_BusPhoneNum;
	
	@FindBy(id="emailheading")
	private WebElement lbl_BusEmail;
	
	
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
	
	
	//=========================================
	
	@FindBy(partialLinkText="Customer Lookup")
	private WebElement btn_CustlookUp;

	private WebDriver driver;
	
	public Leasing_ContactInfo(WebDriver driver)
	{
		this.driver= driver;
		PageFactory.initElements(driver,this);
	}
	
	public void txt_Fname(String fname){
      fName.sendKeys(fname);
	}
	
	public void txt_Lname(String lname){
		LName.sendKeys(lname);
		}
	
	public void select_State(String expVal){
		sel_State.click();
		List<WebElement> ele=driver.findElements(By.xpath("//div[@id='ContactForm_Identification_StateTypeID-list']/ul[@id='ContactForm_Identification_StateTypeID_listbox']/li"));
		System.out.println("Size of the list is:"+ele.size());
		Iterator<WebElement> itr=ele.iterator();
        while(itr.hasNext()){
               WebElement actualWbEle=itr.next();
               String actualWbEleText=actualWbEle.getText();
               if(actualWbEleText.equalsIgnoreCase(expVal)) {actualWbEle.click();
        }
        }}
	
	public void txt_Number(String dLNum){
		number.sendKeys(dLNum);
	}
	
	public void txt_street1(String street){
		street1.sendKeys(street);
	}
	
	public void txt_city(String cityName){
		city.sendKeys(cityName);
	}
	
	public void select_State2(String expVal){
		sel_State2.click();
		List<WebElement> ele=driver.findElements(By.xpath("//div[@class='k-animation-container']/div[@id='lesseeinfo-address-statecode-list']/ul[@id='lesseeinfo-address-statecode_listbox']/li"));
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
	
	public void select_phoneType(){
		sel_PhoneType.click();
		driver.findElement(By.xpath("//div[@id='ContactForm_LesseePhones[_-index-__0]_Phone_PhoneTypeID-list']/..//ul/li[text()='Cell ']"));
		
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
	
	
	
	//============Business==============//
	public void toggleIndivBusi(){
		toggle_IndvdlLease.click();
	}
	
	public void enter_CompanyName(String company){
		businessname.sendKeys(company);
		
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
}
