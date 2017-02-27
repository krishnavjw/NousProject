package Pages.CustDashboardPages;

import javax.management.loading.PrivateClassLoader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Cust_TransSpacePage 
{
	@FindBy(linkText="Back to Dashboard")
	private WebElement BkToDash_btn;
	
	@FindBy(id="addSpaceButton")
	private WebElement addSpace_btn;
	
	@FindBy(xpath="//table/tbody/tr/td[@class='grid-cell-size']//div[@class='bold']")
	private WebElement custSpace_value;
	
	@FindBy(xpath="//table/tbody/tr//td/div[@class='cell grid-cell-rent option-price text-align-right']/div[@class='bold']")
	private WebElement cust_MoRent;
	
	@FindBy(xpath="(//table/tbody/tr//td[@role='gridcell'])[5]")
	private WebElement cust_BalDue;
	
	@FindBy(linkText="View Details")
	private WebElement viewDetails_lk;
	
	@FindBy(xpath="//div[contains(text(),'Is this a maintenance issue?')]/following-sibling::div//span[text()='Yes']/preceding-Sibling::span[@class='button']")
    private WebElement mainIssueYes_radio;
	
	@FindBy(xpath="//div[contains(text(),'Is this a maintenance issue?')]/following-sibling::div//span[text()='No']/preceding-Sibling::span[@class='button']")
    private WebElement mainIssueNo_radio;
	
	@FindBy(xpath="//span[text()='For this new space, do you want to keep the sales channel pricing?']/preceding-sibling::span[@class='button']")
	private WebElement TransTo_radio;
	
	@FindBy(id="PlannedVacateDate")
	private WebElement PlVacateDate_field;
	
	// Select the current date from the calendar 
	@FindBy(xpath="//div[@id='dualDatePicker']//table/tbody/tr//td/a[contains(@class,'ui-state-active')]")
	private WebElement currentDate_calendar;
	
	//Selecting future dates using following xpath
	// xpath="//div[@id='dp1469522675782']//table/tbody//tr//td/a[@class='ui-state-default ui-state-highlight ui-state-active']/ancestor::td/following-sibling::td/a[text()='29']"
	// in place of 29 we need to pass future dates
	
	
	@FindBy(id="addSpaceButton")
	private WebElement findSpace_btn;
	
	
	public Cust_TransSpacePage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public String getCrrentDateFromCalendar()
	{
		return currentDate_calendar.getText();
	}
}
