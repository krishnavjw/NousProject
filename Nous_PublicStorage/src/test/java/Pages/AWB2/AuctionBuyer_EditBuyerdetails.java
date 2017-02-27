package Pages.AWB2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;


public class AuctionBuyer_EditBuyerdetails {
	
	private WebDriver driver;
	
	@FindBy(xpath="//input[@id='AuctionBuyerInformationModel_FirstName']")
	private WebElement AWB_firstname;
	
	@FindBy(xpath="//input[@id='Addresses_OfficialAddress_StreetAddress1']")
	private WebElement address1;
	
	@FindBy(xpath="//input[@id='Addresses_OfficialAddress_StreetAddress2']")
	private WebElement address_street;
	
	@FindBy(xpath="//input[@id='Addresses_OfficialAddress_City']")
	private WebElement address_city;
	
	@FindBy(xpath="//input[@id='Phones_OfficialPhones__-index-__0__AreaCode']")
	private WebElement phone_areacode;
	
	
	@FindBy(xpath="//input[@id='Phones_OfficialPhones__-index-__0__Exchange']")
	private WebElement phone_Exchange;
	
	@FindBy(xpath="//input[@id='Phones_OfficialPhones__-index-__0__LineNumber']")
	private WebElement phone_LineNumber;
	
	
	@FindBy(xpath="//input[@id='Emails_OfficialEmails__-index-__0__Email']")
	private WebElement email;
	
	
	@FindBy(xpath="//a[@id='submitButton']")
	private WebElement submitButton;
	
	@FindBy(xpath="//textarea[@id='notesText']")
	private WebElement 	notesText;
	
	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement 	employeeNumber;
	
	@FindBy(xpath="//a[contains(text(),' Continue')]")
	private WebElement 	continue_btn;
	
	

	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header stateDrop identstateDrop']/span[1]/span[2]")
	private WebElement 	statadropdown;
	
	@FindBy(xpath="//ul[@id='AuctionBuyerInformationModel_ContactIdentification_StateTypeID_listbox']/li[5]")
	private WebElement 	stateoption;
	
	
	@FindBy(xpath="//input[@id='AuctionBuyerInformationModel_ContactIdentification_EncryptedItem_EncryptedIdDisplay']")
	private WebElement statepin;
	
	
	
	public AuctionBuyer_EditBuyerdetails(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	
	public String get_firstname(){
		return AWB_firstname.getText();
	}
	
	public String get_address1(){
		return address1.getText();
	}
	
	public String get_email(){
		return email.getText();
	}
	
	public void enter_Firstname(String fn){
		AWB_firstname.clear();
		AWB_firstname.sendKeys(fn);
	}
	
	public void enter_address1(String add1){
		address1.clear();
		address1.sendKeys(add1);
	}
	
	public void enter_addstreet(String addstreet){
		address_street.clear();
		address_street.sendKeys(addstreet);
	}
	
	public void enter_addcity(String city){
		address_city.clear();
		address_city.sendKeys(city);
	}
	
	public void enter_areacode(String areacode){
		phone_areacode.clear();
		phone_areacode.sendKeys(areacode);
	}
	
	public void enter_exchange(String exchange){
		phone_Exchange.clear();
		phone_Exchange.sendKeys(exchange);
	}
	
	public void enter_location(String loc){
		phone_LineNumber.clear();
		phone_LineNumber.sendKeys(loc);
	}
	
	public void enter_email(String emailtext){
		email.clear();
		email.sendKeys(emailtext);
	}
	
	public void clk_save(){
		submitButton.click();
	}
	
	public void enter_note(String note){
		
		notesText.sendKeys(note);
	}
	
	public void enter_emp(String emp){
		
		employeeNumber.sendKeys(emp);
	}
	
	
	public void clk_continue(){
		continue_btn.click();
	}
	
	public void clk_satedropdown(){
		statadropdown.click();
	}
	
	public void select_statte(){
		stateoption.click();
	}
	
	public void enter_statepin(String pin){
		statepin.clear();
		statepin.sendKeys(pin);
	}
}
