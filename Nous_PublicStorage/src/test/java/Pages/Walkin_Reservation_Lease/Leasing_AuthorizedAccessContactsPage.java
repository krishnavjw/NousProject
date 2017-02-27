package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Leasing_AuthorizedAccessContactsPage 
{
	@FindBy(id="AuthorizedUsersForm_AuthorizedUsers__-index-__0__FirstName")
	private WebElement firstName;
	
	@FindBy(id="AuthorizedUsersForm_AuthorizedUsers__-index-__0__MiddleInitial")
	private WebElement midName;
	
	@FindBy(id="AuthorizedUsersForm_AuthorizedUsers__-index-__0__LastName")
	private WebElement lastName;
	
	@FindBy(xpath="//div[@class='CustomerPhoneBlock floatleft']//span[text()='Select']")
	private WebElement phone_list;
	
	@FindBy(id="AuthorizedUsersForm_AuthorizedUsers__-index-__0__Phone_AreaCode")
	private WebElement ph_Areacode;
	
	@FindBy(id="AuthorizedUsersForm_AuthorizedUsers__-index-__0__Phone_Exchange")
	private WebElement ph_Exchange;
	
	@FindBy(id="AuthorizedUsersForm_AuthorizedUsers__-index-__0__Phone_LineNumber")
	private WebElement ph_Lineno;
	
	@FindBy(id="customerSaveButton")
	private WebElement saveandProceed_btn;
	
	@FindBy(id="confirmWithCustomer")
	private WebElement confirmCust_btn;
	
	@FindBy(xpath="//a[@id='notes-add']")
	private WebElement addNotesLink;
	
	public Leasing_AuthorizedAccessContactsPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public void enterFirstName(String fn) throws InterruptedException
	{
		firstName.clear();
		Thread.sleep(2000);
		firstName.sendKeys(fn);
	}
	
	public void enterLastName(String ln) throws InterruptedException
	{
		lastName.clear();
		Thread.sleep(2000);
		lastName.sendKeys(ln);
	}
	
	public void clickAddNotesBtn(){
		addNotesLink.click();
	}
	
	public void enterMiddleName(String mn)
	{
		firstName.sendKeys(mn);
	}
	
	public void clickPhonelist()
	{
		phone_list.click();
	}
	
	public void enterAreacode(String areacode) throws InterruptedException
	{
		ph_Areacode.clear();
		Thread.sleep(1000);
		ph_Areacode.sendKeys(areacode);
	}
	
	public void enterExchange(String exchange) throws InterruptedException
	{
		ph_Exchange.clear();
		Thread.sleep(1000);
		ph_Exchange.sendKeys(exchange);
	}
	
	public void enterLineNum(String lineNum) throws InterruptedException
	{
		ph_Lineno.clear();
		Thread.sleep(1000);
		ph_Lineno.sendKeys(lineNum);
	}
	
	public boolean verifySaveProceed_btn()
	{
		return saveandProceed_btn.isDisplayed();
	}
	
	public void clickSavenProceed()
	{
		saveandProceed_btn.click();
	}
	
	
	public void clickConfirmCust()
	{
		confirmCust_btn.click();
	}
	
	

}
