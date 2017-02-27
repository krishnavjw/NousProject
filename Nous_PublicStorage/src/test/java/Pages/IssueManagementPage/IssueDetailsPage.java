package Pages.IssueManagementPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IssueDetailsPage {
	private WebDriver driver;
	public IssueDetailsPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[contains(@class,'clearfix-container colu') and contains(@id,'tr')]//span[@class='button']")
	private WebElement phoneNumChxBox;
	
	@FindBy(xpath="//div[contains(@class,'clearfix-container colu') and contains(@id,'tr')]/div[@class='call-screen-call-result']//span[text()='Select Result']/..")
	private WebElement callResultDropDown;
	
	@FindBy(xpath="//div[contains(@class,'clearfix-container colu') and contains(@id,'tr')]//input[@class='no-filter webchamp-textbox showErrorBorder']")
	private WebElement commentsEdt;
	
	@FindBy(xpath="//a[@id='saveCallButton']")
	private WebElement submitCallsBtn;
	
	public void clkPhoneNumChxBox(){
		phoneNumChxBox.click();
	}
	
	public void clkCallResultDropDown(){
		callResultDropDown.click();
	}
	
	public void selDropDownOption(){
		
		
	}
	
	public void enterCommentsEdt(String comments){
		commentsEdt.sendKeys(comments);
	}
	
	public void clkSubmitCallsBtn(){
		submitCallsBtn.click();
	}
}
