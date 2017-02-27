//package Pages.CustDashboardPages;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//
//public class EmergncyContctpage {
//
//	@FindBy(xpath="//span[text()='Emergency Contact']")
//	private WebElement emergncyContct_windheader;
//	
//	@FindBy(xpath="//a[contains(text(),'Close')]")
//	private WebElement close_Btn;
//	
//	@FindBy(xpath="//a[contains(text(),'Edit')]")
//	private WebElement edit_Btn;
//	
//	public EmergncyContctpage(WebDriver driver){
//		PageFactory.initElements(driver, this);
//	}
//	
//}

package Pages.CustDashboardPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmergncyContctpage {

private WebDriver driver;
	
	public EmergncyContctpage(WebDriver driver){
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	@FindBy(xpath="//div[@class='emergency-contact']//div[contains(@class,'decline-emergency-contact')]//input[contains(@id,'CustomerDeclinesEmergencyContact')]/following-sibling::span[@class='button']")
	private WebElement customerDecline_checkbox;
	
	@FindBy(xpath="//div[@class='emergency-contact']//input[@id='EmergencyContacts_0__FirstName']")
	private WebElement firstName;
	
	@FindBy(xpath="//div[@id='editEmergencyContacts']//a[text()='Cancel']")
	private WebElement cancelBtn;
	
	@FindBy(xpath="//div[@class='emergency-contact']//input[@id='EmergencyContacts_0__LastName']")
	private WebElement lastName;
	
	@FindBy(xpath="//div[@class='emergency-contact']//span[contains(@class,'contact-relationship-type')]/span")
	private WebElement relationship;
	
	@FindBy(id="EmergencyContacts_0__Address_AddressLine1")
	private WebElement address1;
	
	@FindBy(id="EmergencyContacts_0__Address_City")
	private WebElement city;
	
	@FindBy(id="EmergencyContacts_0__Address_PostalCode")
	private WebElement zip;
	
	@FindBy(xpath="//div[@class='emergency-contact']/div[contains(@id,'rentalunit')]//div[contains(@class,'state-code-validate')]/span/span")
	private WebElement state;
	
	@FindBy(xpath="//div[@class='CustomerPhoneBlock']//span[contains(@class,'phone-type')]/span")
	private WebElement phoneType;
	
	@FindBy(id="EmergencyContacts_0__Phones__-index-__0__AreaCode")
	private WebElement areaCode;
	
	@FindBy(id="EmergencyContacts_0__Phones__-index-__0__Exchange")
	private WebElement exchange;
	
	@FindBy(id="EmergencyContacts_0__Phones__-index-__0__LineNumber")
	private WebElement lineNumber;
	
	@FindBy(id="EmergencyContacts_0__Email_Email")
	private WebElement email;
	
	@FindBy(id="verifyButton")
	private WebElement verifyBtn;
	
	@FindBy(id="confirmButton")
	private WebElement confirmBtn;
	
	@FindBy(xpath="//div[@class='auth-controls-container']/a[text()='Approve']")
	private WebElement approveBtn;
	
	@FindBy(id="submitButton")
	private WebElement saveBtn;
	
	@FindBy(xpath="//div[@id='primarySignaturePanel']//span[contains(text(),'Approved')]")
	private WebElement approved;
	
	
	public void click_CustomerDecline_Checkbox(){
		customerDecline_checkbox.click();
	}
	
	public boolean isDisplayed_FirstName(){
		return firstName.isDisplayed();
	}
	
	public void click_CancelBtn(){
		cancelBtn.click();
	}
	
	public void enter_FirstName(String text){
		firstName.clear();
		firstName.sendKeys(text);
	}
	
	public void enter_LastName(String text){
		lastName.clear();	
		lastName.sendKeys(text);
	}
	
	public void enter_Address1(String text){
		address1.clear();
		address1.sendKeys(text);
	}
	
	public void enter_City(String text){
		city.clear();
		city.sendKeys(text);
	}
	
	public void enter_Zip(String text){
		zip.clear();
		zip.sendKeys(text);
	}
	
	public void enter_AreaCode(String text){
		areaCode.sendKeys(text);
	}
	
	public void enter_Exchange(String text){
		exchange.sendKeys(text);
	}
	
	public void enter_LineNumber(String text){
		lineNumber.sendKeys(text);
	}
	
	public void enter_Email(String text){
		email.clear();
		email.sendKeys(text);
	}
	
	public void click_VerifyBtn(){
		verifyBtn.click();
	}
	
	public void click_ConfirmBtn(){
		confirmBtn.click();
	}
	
	public void click_ApproveBtn(){
		approveBtn.click();
	}
	
	public void click_SaveBtn(){
		saveBtn.click();
	}
	
	public boolean isApproved(){
		return approved.isDisplayed();
	}
	
	
	
	public void selectRelationship(String text) throws InterruptedException{
		relationship.click();
		Thread.sleep(3000);
		
		List<WebElement> list = driver.findElements(By.xpath("//div[@id='EmergencyContacts_0__RelationshipTypeId-list']/ul/li"));
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getText().equalsIgnoreCase(text)){
				list.get(i).click();
				break;
			}
		}
	}
	
	public void selectState(String text) throws InterruptedException{
		state.click();
		Thread.sleep(3000);
		
		List<WebElement> list = driver.findElements(By.xpath("//div[@id='EmergencyContacts_0__Address_StateCode-list']/ul/li"));
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getText().equalsIgnoreCase(text)){
				list.get(i).click();
				break;
			}
		}
	}
	
	
	public void selectPhoneType(String text) throws InterruptedException{
		phoneType.click();
		Thread.sleep(3000);
		
		List<WebElement> list = driver.findElements(By.xpath("//div[@id='EmergencyContacts_0__Phones__-index-__0__PhoneTypeID-list']/ul/li"));
		for(int i=0; i<list.size(); i++){
			Thread.sleep(1000);
			String temp = list.get(i).getText().trim();
			if(temp.contains(text)){
				list.get(i).click();
				break;
			}
		}
	}
	
}
