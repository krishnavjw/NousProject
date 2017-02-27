package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Acc_CustomerInfoPage {
	
	private WebDriver driver;
	

	@FindBy(xpath="//div[@id='customerDashboard']//div//div[@class='addresses-container clearfix-container']//div[@class='padding-bottom']")
	private WebElement address;
	
	@FindBy(xpath="//div[@id='customerDashboard']//div//div[@class='phones-container clearfix-container']")
	private WebElement phone;
	
	@FindBy(xpath="//div[@id='customerDashboard']//div//div[@class='emails-container padding-bottom clearfix-container']//div[@class='padding-bottom']/div")
	private WebElement email;
	
	@FindBy(xpath="//div[@id='customerDashboard']//div//div[@class='identifications-container half-padding-bottom clearfix-container']")
	private WebElement identification;
	
	@FindBy(xpath="//span[text()='Customer Info']")
	private WebElement custInfoPageTitle;
	
	@FindBy(xpath="//div[@id='customerDashboard']//div/ul/li/span[contains(text(),'Customer Info')]")
	private WebElement custInfoTab;
	
	@FindBy(xpath="//div[contains(text(),'Current Account:')]/following-sibling::div")
	private WebElement accNum;
	
	@FindBy(xpath="//div[contains(text(),'Military Status:')]/following-sibling::div")
	private WebElement get_MilitaryStausTxt;
	
	@FindBy(id="editAccountDetails")
	private WebElement editAccDetails;
	
	@FindBy(xpath="//a[contains(text(),'Back To Dashboard')]")
	private WebElement backToDashboard;
	
	@FindBy(xpath="//div[contains(text(),'Username')]/following-sibling::div/div")
	private WebElement username;
	
	@FindBy(xpath="//a[contains(text(),'Reset Password')]")
	private WebElement resetPassword;
	
	@FindBy(xpath="//div[contains(text(),'Military Status:')]/following-sibling::div/a")
	private WebElement viewdetails_link;
	
	
	public Acc_CustomerInfoPage(WebDriver driver){
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}	
	
	public String getCustInfoPageTitle(){
		
		return custInfoPageTitle.getText();
	}

	public String getAccNum(){
		
		return accNum.getText().trim();
	}
	
	
	public String getAddress(){
		return address.getText();
	}
	
	public String getPhoneNumber(){
		return phone.getText();
	}
	
	public String getEmailId(){
		return email.getText();
	}
	
	public String getIdentificationId(){
		return identification.getText();
	}
	
	public void clk_CustomerInfoTab(){
		custInfoTab.click();
	}
	
	public String get_MilitaryStausText(){
			
			return get_MilitaryStausTxt.getText().trim();
		}
	
	public void click_EditAccountDetails(){
		editAccDetails.click();
	}
	
	public void click_BackToDashboard(){
		backToDashboard.click();
	}
	
	public boolean verify_Username(){
		return username.isDisplayed();
	}
	
	public boolean verify_ResetPassword(){
		return resetPassword.isDisplayed();
	}
	
	public void click_ResetButton(){
		resetPassword.click();
	}


/*Anjana*/

	@FindBy(xpath="//span[text()='Other Accounts']")
	private WebElement otherAccounts;
	
	
	public boolean verify_otherAccounts() {
		return otherAccounts.isDisplayed();		
	}
	
	
	public String get_otherAccounts() {
		return otherAccounts.getText();		
	}
	
	@FindBy(xpath="//span[@class='icon help']")
	private WebElement helpicon;
	
	public boolean verify_helpicon() {
		return helpicon.isDisplayed();		
	}
	
	public boolean verifyAddress(){
		return address.isDisplayed();
	}
	
	public boolean verifyPhoneNumber(){
		return phone.isDisplayed();
	}
	
	public boolean verifyEmailId(){
		return email.isDisplayed();
	}
	
	public boolean verifyIdentificationId(){
		return identification.isDisplayed();
	}
	
	@FindBy(xpath="//div[contains(text(),'Username:')]")
	private WebElement username1;
	
	@FindBy(xpath="//div[contains(text(),'Military Status:')]")
	private WebElement militarystaus;
	
	public boolean verifyusername(){
		return username1.isDisplayed();
	}
	
	public boolean verifymilitarystaus(){
		return militarystaus.isDisplayed();
	}
	
	@FindBy(xpath="//a[contains(text(),'Change Status')]")
	private WebElement changeStatus;
	
	public boolean verifychangeStatus(){
		return changeStatus.isDisplayed();
	}
	
	public void click_changeStatus(){
		 changeStatus.click();
	}
	
	public void verify_viewdetails_link(){
		 viewdetails_link.isDisplayed();
	}
	
	
	public void click_viewdetails_link(){
		 viewdetails_link.click();
	}

}
