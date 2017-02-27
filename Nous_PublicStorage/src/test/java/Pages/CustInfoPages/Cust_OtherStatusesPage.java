package Pages.CustInfoPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Cust_OtherStatusesPage {
	
	@FindBy(xpath="//div[@id='editOtherStatuses']//h3[text()='Other Statuses']")
	private WebElement Pagetitle;
		
	@FindBy(xpath="//form[@id='customerStatusesForm']//div[@id='deceased-container']//div[text()='Deceased']")
	private WebElement Deceased;
		
	@FindBy(xpath="//form[@id='customerStatusesForm']//span[text()='Notified']/preceding-sibling::span[@class='button']")
	private WebElement Chkbx_Notified;
	
	@FindBy(xpath="//div[@id='deceased-container']//div[@class='vertical-center floatleft']")
	private WebElement alertmsg;
	
	@FindBy(xpath="//div[@id='bankruptcy-container']//div[@class='vertical-center floatleft']")
	private WebElement alertmsg_BankRuptcy;
	
	
	
	
	@FindBy(linkText="Scan")
	private WebElement btn_Scan;
	
	@FindBy(id="DeceasedNote")
	private WebElement notes;
	
	
	@FindBy(id="BankruptcyNote")
	private WebElement BankruptcyNoteTxt;
	
	
	@FindBy(id="submitButton")
	private WebElement btn_Save;
	
	
	
	public Cust_OtherStatusesPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public boolean verify_pageTitle()
	{
		return Pagetitle.isDisplayed();
	}

	public boolean verify_Deceased()
	{
		return Deceased.isDisplayed();
	}

	public void click_Chkbx_Notified()
	{
		Chkbx_Notified.click();
	}
	
	public boolean verify_alertmsg()
	{
		return alertmsg.isDisplayed();
	}

	
	public String get_alertmsg()
	{
		return alertmsg.getText();
	}

	public void click_Savebtn()
	{
		btn_Save.click();
	}
	
	public boolean verify_Scanbtn()
	{
		return btn_Scan.isDisplayed();
	}
	
	public boolean verify_notes()
	{
		return notes.isDisplayed();
	}
	
	public void txt_notes(String text)
	{
		 notes.sendKeys(text);
	}
	
	public boolean txt_BankruptcyNote()
	{
		return BankruptcyNoteTxt.isDisplayed();
	}
	
	public void enter_BankruptcyNote(String text)
	{
		 BankruptcyNoteTxt.sendKeys(text);
	}

	
	
	public boolean verify_alertmsg_BankRuptcy()
	{
		return alertmsg_BankRuptcy.isDisplayed();
	}

	
	public String get_alertmsg_BankRuptcy()
	{
		return alertmsg_BankRuptcy.getText();
	}

}
