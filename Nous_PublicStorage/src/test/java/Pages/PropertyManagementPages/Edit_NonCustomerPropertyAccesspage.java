package Pages.PropertyManagementPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Edit_NonCustomerPropertyAccesspage {
	
	
	
		@FindBy(xpath="//span[text()='Edit Non-Customer Property Access']")
		private WebElement page_Title;
		
		@FindBy(xpath="//div[@id='editNonCustomerPropertyAccessTemplate']//input[@id='FirstName']")
		private WebElement firstName;

		@FindBy(xpath="//div[@id='editNonCustomerPropertyAccessTemplate']//input[@id='LastName']")
		private WebElement lastName;

		@FindBy(xpath="//div[@id='editNonCustomerPropertyAccessTemplate']//input[@id='CompanyName']")
		private WebElement companyName;

		@FindBy(xpath="//div[@class='phone-info floatleft']//span[@aria-owns='Phone_Type_listbox']")
		private WebElement phone_list;

		@FindBy(xpath="//div[@class='phone-info floatleft']//input[@id='Phone_AreaCode']")
		private WebElement phoneAreaCode;

		@FindBy(xpath="//div[@class='phone-info floatleft']//input[@id='Phone_Exchange']")
		private WebElement phoneExchange;

		@FindBy(xpath="//div[@class='phone-info floatleft']//input[@id='Phone_LineNumber']")
		private WebElement phoneLineNum;

		@FindBy(xpath="//div[@id='editNonCustomerPropertyAccessTemplate']//input[@id='EmailAddress_Email']")
		private WebElement email;
		
		@FindBy(xpath="//div[@class='access-type-container floatleft']//span[text()='select']")
		private WebElement accessType;
		
		@FindBy(xpath="//div[input[@id='GateCodeManual']]/div/span")
		WebElement gateCodeValidationMessage;
		
		@FindBy(id="GateCodeManual")
		private WebElement code;
		
		@FindBy(xpath="//span[text()='Active']/preceding-sibling::span[@class='button']")
		private WebElement active_Radiobtn;
		
		@FindBy(xpath="//span[text()='Inactive']/preceding-sibling::span[@class='button']")
		private WebElement inactive_Radiobtn;
		
		@FindBy(id="EmployeeId")
		private WebElement employeeId;
		
		
		@FindBy(partialLinkText="Save")
		private WebElement save;
		
		@FindBy(partialLinkText=" Cancel")
		private WebElement cancel;

		private WebDriver driver;
		
		public Edit_NonCustomerPropertyAccesspage(WebDriver driver)
		{
			this.driver=driver;
			PageFactory.initElements(driver, this);
		}

		public String getGateCode(){
			return code.getAttribute("value");
		}
		
		public String getFirstName(){
			return firstName.getAttribute("value");
		}
		
		public boolean verify_page_Title(){
			return page_Title.isDisplayed();
		}
		
		public String getLastName(){
			return lastName.getAttribute("value");
		}
		
		public String getFirstname(){
			return firstName.getText();
		}
		
		public String getCode(){
			return code.getText();
		}

		
		public void clearfname(){
			firstName.clear();
		}
		public void enterFirstName(String fN)
		{
			
			firstName.sendKeys(fN);
		}

		
		public String getLastname(){
			return lastName.getText();
		}

		public void clearLastname(){
			 lastName.clear();;
		}
		
		public void enterLastName(String lN)
		{
			
			lastName.sendKeys(lN);
		}

		
		public void clearcmpnyname(){
			companyName.clear();
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
		
		public void click_AccessType(){
			accessType.click();
		}
		
		public void enter_code(String Code)
		{
			code.clear();
			code.sendKeys(Code);
		}
		
		public void click_active_Radiobtn(){
			active_Radiobtn.click();
		}
		
		public void click_inactive_Radiobtn(){
			inactive_Radiobtn.click();
		}

		public void enter_employeeId(String empid)
		{
			employeeId.sendKeys(empid);
		}
		
				
		public void click_save(){
			save.click();
		}
		
		public void click_cancel(){
			cancel.click();
		}
		
		public boolean verifyGateCodeMessage() {
			if(gateCodeValidationMessage.getText().equalsIgnoreCase("Enter a valid Gate Code")){
				return true;
			}
			else
			{
				return false;
			}
		}

		public void clearemail() {
			// TODO Auto-generated method stub
			email.clear();
		}
		
		

	}
