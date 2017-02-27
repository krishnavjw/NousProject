package Pages.IssueManagementPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Create_CreditRequestIssuePage {

	private WebDriver driver;

	@FindBy(name="RequestedCredit") 
	private WebElement requestCredited;
	
	@FindBy(name="Description")
	private WebElement enterexplanation;
	
	@FindBy(linkText="Create Issue")
	private WebElement createissue_btn;
	
	public Create_CreditRequestIssuePage(WebDriver driver){
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	
	
	public void enter_RequestedcreditAmt(String value){
		requestCredited.sendKeys(value);
	}
	
	public void enter_enterexplanation(String value){
		enterexplanation.sendKeys(value);
	}
	
	public void click_createissue_btn(){
		createissue_btn.click();
	}
	
}
