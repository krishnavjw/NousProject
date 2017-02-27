package Pages.PropertyManagementPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNon_CustomerPropertyAccessCodePage 
{
	@FindBy(id="FirstName")
	private WebElement firstName;

	@FindBy(id="LastName")
	private WebElement lastName;

	@FindBy(id="CompanyName")
	private WebElement companyName;

	@FindBy(xpath="//div[@class='phone-info floatleft']//span[@aria-owns='Phone_Type_listbox']")
	private WebElement phone_list;

	@FindBy(xpath="//div[@class='phone-info floatleft']//input[@id='Phone_AreaCode']")
	private WebElement phoneAreaCode;

	@FindBy(xpath="//div[@class='phone-info floatleft']//input[@id='Phone_Exchange']")
	private WebElement phoneExchange;

	@FindBy(xpath="//div[@class='phone-info floatleft']//input[@id='Phone_LineNumber']")
	private WebElement phoneLineNum;

	@FindBy(id="EmailAddress_Email")
	private WebElement email;


	@FindBy(xpath="//div[@class='js-access-zone clearfix-container']//span[@aria-owns='AccessZone_listbox']")
	private WebElement accessZone_list;


	@FindBy(xpath="//div[@class='access-type-container floatleft']//span[@aria-owns='AccessType_listbox']")
	private WebElement accessType_list;

	@FindBy(xpath="//div[@class='generate-access-code floatleft margin-right']//span[text()='Yes']/preceding-sibling::span")
	private WebElement autoGenerate_yes;

	@FindBy(xpath="//div[@class='generate-access-code floatleft margin-right']//span[text()='No']/preceding-sibling::span")
	private WebElement autoGenerate_No;

	@FindBy(xpath="//div[@class='nonCustomerPropertyAccess container']//div[@id='EmployeeId-wrapper']//input[@id='EmployeeId']")
	private WebElement employeeId;

	@FindBy(xpath="//a[contains(text(),'Create')]")
	private WebElement create_btn;


	@FindBy(xpath="//span[input[@id='AccessType']]//span[text()='select']")
	private WebElement accessZone;


	@FindBy(xpath="//div[@class='nonCustomerPropertyAccess container']//div[@class='access-type-container floatleft']//span[@class='k-widget k-dropdown k-header']")
	private WebElement accessType;

	@FindBy(xpath="//label[span[text()='Yes']]/span[@class='button']")
	private WebElement yesRadio_Btn;

	@FindBy(xpath="//label[span[text()='No']]/span[@class='button']")
	private WebElement noRadio_Btn;
	
	@FindBy(xpath="//div[span[text()='Phone:']]//span[text()='select']")
	private WebElement phoneTypeRadioButton;
	
	@FindBy(xpath="//ul[@id='Phone_Type_listbox']/li")
	List<WebElement> selectPhoneTypeOptions;
	
	@FindBy(xpath="//ul[@id='AccessType_listbox']/li")
	List<WebElement> accessTypeOptions;
	
	@FindBy(xpath="//input[@id='GateCodeManual']")
	WebElement gateCodeField;
	
	@FindBy(xpath="//div[input[@id='GateCodeManual']]/div/span")
	WebElement gateCodeValidationMessage;
	
	@FindBy(xpath="html/body/div[16]/div[1]/span")
	WebElement newGateCodeMessage;
	
	@FindBy(xpath="//a[contains(text(),'OK')]")
	WebElement okButton;
	
	
	@FindBy(xpath="//ul[@id='AccessZone_listbox']//li")
	List<WebElement> accessCodeOptions;
	
	
	public CreateNon_CustomerPropertyAccessCodePage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}


	public void enterFirstName(String fN)
	{
		firstName.sendKeys(fN);
	}

	public void enterLastName(String lN)
	{
		lastName.sendKeys(lN);
	}

	public void enterCompanyName(String company)
	{
		companyName.sendKeys(company);
	}

	public void enterPhoneAreaCode(String areacode)
	{
		phoneAreaCode.sendKeys(areacode);
	}

	public void enterPhoneExchange(String exchange)
	{
		phoneExchange.sendKeys(exchange);
	}

	public void enterPhoneLineNum(String lineNum)
	{
		phoneLineNum.sendKeys(lineNum);
	}

	public void enterEmail(String emailId){
		email.sendKeys(emailId);
	}

	public void clickAccessZone(){
		accessZone.click();
	}

	public void clickAccessType(){
		accessType.click();
	}

	public boolean verifyZone(){
		return accessZone.isDisplayed();
	}

	public boolean verifyYesRadio_Btn(){
		return yesRadio_Btn.isDisplayed();
	}

	public boolean verifyNoRadio_Btn(){
		return noRadio_Btn.isDisplayed();
	}
	
	public void clickYesRadio_Btn(){
		yesRadio_Btn.click();
	}

	public void clickNoRadio_Btn(){
		noRadio_Btn.click();
	}
	
	
	
	public void enterEmployeeId(String employeeID){
		employeeId.sendKeys(employeeID);
	}


	public void selectPhoneType(String phType) 
	{
		phoneTypeRadioButton.click();
		
		
		for(int i=0;i<selectPhoneTypeOptions.size();i++){
			if(selectPhoneTypeOptions.get(i).getText().equalsIgnoreCase(phType)){
				selectPhoneTypeOptions.get(i).click();
			}
		}
		
		
	}


	public void selectAccessType(String accType) throws InterruptedException 
	{
		accessZone.click();
		Thread.sleep(1000);
		
		for(int i=0;i<accessTypeOptions.size();i++){
			if(accessTypeOptions.get(i).getText().contains(accType)){
				accessTypeOptions.get(i).click();
			}
		}
		
	}


	public void entergateCode(String value) throws InterruptedException {
		gateCodeField.clear();
		Thread.sleep(1000);
		gateCodeField.sendKeys(value);
		
	}


	public boolean verifyDuplicateGateCodeMessage() {
		if(gateCodeValidationMessage.getText().equalsIgnoreCase("Please enter a valid Gate Code")){
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void click_createButton(){
		create_btn.click();
	}
	
	public boolean verify_newGateCodeMessage(){
		return newGateCodeMessage.isDisplayed();
	}
	
	public void click_OKButton()
	{
		okButton.click();
	}
}
