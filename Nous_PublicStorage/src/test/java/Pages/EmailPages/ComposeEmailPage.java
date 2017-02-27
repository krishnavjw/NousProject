package Pages.EmailPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ComposeEmailPage {

	public ComposeEmailPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[@class='compose-header']")
	private WebElement composeHeader;
	
	@FindBy(xpath="//div[@class='row to']//button[@class='add-recipient-input-button']")
	private WebElement to_AddRecipient;
	
	@FindBy(xpath="//div[@class='row cc']//button[@class='add-recipient-input-button']")
	private WebElement cc_AddRecipient;
	
	@FindBy(xpath="//div[@class='compose-fields']//div[contains(@class,'to-address')]/div/div[@class='display-name']")
	private WebElement toFieldData;
	
	@FindBy(xpath="//div[@class='compose-header']//div[@class='row subject']//input[@class='js-subject']")
	private WebElement subject;
	
	@FindBy(xpath="html/body")
	private WebElement emailBody;
	
	@FindBy(xpath="//button[text()='Save To Drafts']")
	private WebElement saveToDrafts;
	
	@FindBy(xpath="//td[@class='from-address']//span")
	private WebElement draftMail;
	
	@FindBy(xpath="//div[@class='container email-menu']//div//button[contains(text(),'Drafts')]")
	private WebElement draftsLink;
	
	@FindBy(xpath="//button[text()='Send']")
	private WebElement sendButtonLink;
	
	public boolean exists_ComposeHeader(){
		return composeHeader.isDisplayed();
	}
	
	public void click_To_AddRecipient(){
		to_AddRecipient.click();
	}
	
	public void click_cc_AddRecipient(){
		cc_AddRecipient.click();
	}
	
	public String getData_ToField(){
		return toFieldData.getText();
	}
	
	public void enter_Subject(String text){
		subject.sendKeys(text);
	}
	
	public void enter_MailBody(String text,WebDriver driver) throws InterruptedException
	{
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Rich Text Editor, body']")));
		Thread.sleep(1000);
		emailBody.sendKeys(text);
		Thread.sleep(1000);
		driver.switchTo().defaultContent();
	}
	
	public void clickOnSavetoDrafts()
	{
		saveToDrafts.click();
	}
	
	public boolean verifyDraftMail() throws InterruptedException
	{
		draftsLink.click();
		
		Thread.sleep(3000);
		if(draftMail.isDisplayed())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void clickSendButton() {
		sendButtonLink.click();
		
	}
	
	
	
}
