package Pages.CustDashboardPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class Cust_AccActivitiesPage 
{
	private WebDriver driver;
	
	@FindBy(xpath="//span[text()='Last 20 Activities']/preceding-sibling::span[@class='button']")
	private WebElement last20Act_Radiobtn;
	
	@FindBy(xpath="//span[text()='View All Activities']/preceding-sibling::span[@class='button']")
	private WebElement viewAllAct_Radiobtn;
	
	@FindBy(xpath="//span[text()='View All Notes']/preceding-sibling::span[@class='button']")
	private WebElement viewAllNotes_Radiobtn;
	
	@FindBy(xpath="(//tr[@class='k-master-row']/td[9])[1]")
	private WebElement Txt_BalanceAmt;
	
	@FindBy(xpath="//td[text()='Late Fees:']/following-sibling::td[1]")
	private WebElement Txt_Amt;
	
	@FindBy(xpath="//span[text()='View All Activities']")
	private WebElement viewAllAct;
	
	@FindBy(xpath="//span[text()='View All Notes']")
	private WebElement viewAllNotes;
	
	@FindBy(xpath="//span[contains(text(),'View by Date Range:')]")
	private WebElement dateRange;
	
	@FindBy(xpath="//div[@id='toFilter']/span")
	private WebElement toFilterDate;
	
	@FindBy(xpath="//input[@class='activity-filter']/../span/span[contains(text(),'All')]")
	private WebElement filterDropdown;
	
	@FindBy(xpath="//b[contains(text(),'NO ACTIVITIES TO DISPLAY')]")
	private WebElement noAct;
	
	@FindBy(xpath="(//div[@id='activities-grid']//table//tbody/tr/td[3][text()='Emails']/preceding-sibling::td)[1]")
	private WebElement email_link;

	@FindBy(xpath="//div[@id='activities-grid']//tbody/tr/td//div/a[text()='Unvacate']")
	private WebElement clk_UnavteLink;

	@FindBy(xpath="(//div[@id='activities-grid']//a[text()='Resend Email'])[1]")
	private WebElement resendEmail_link;

	
	
	public Cust_AccActivitiesPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean verify_last20Act(){
		return last20Act_Radiobtn.isEnabled();
	}
	
	public boolean verify_viewAllAct(){
		return viewAllAct.isDisplayed();
	}
	
	public void clk_viewAllActRadioBtn(){
		viewAllAct_Radiobtn.click();
	}
	
	public boolean verify_viewAllNotes(){
		return viewAllNotes.isDisplayed();
	}
	
	public boolean verify_DateRange(){
		return dateRange.isDisplayed();
	}
	
	public String get_toDate(){
		return toFilterDate.getText();
	}
	
	
	public String get_BalAmt()
	{
		return Txt_BalanceAmt.getText();
	}
	
	public String get_Amt()
	{
		return Txt_Amt.getText();
	}
	
	public void sel_Filter(String data) throws InterruptedException{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		filterDropdown.click();
		Thread.sleep(2000);
		List<WebElement> list = driver.findElements(By.xpath("//div[@class='k-animation-container']//ul/li"));
		for(int i=0;i<list.size();i++){
			if(data.equals(list.get(i).getText())){
				jse.executeScript("arguments[0].scrollIntoView()", list.get(i)); 
				jse.executeScript("arguments[0].click();", list.get(i));
				break;
			}
		}
	}
	
	public boolean verify_NoAct(){
		return noAct.isDisplayed();
	}
	
	public void verify_FilteredValues(String data) throws InterruptedException{
		List<WebElement> list = driver.findElements(By.xpath("//div[@id='activities-grid']//table/tbody//td[4]"));
		for(int i=0;i<list.size();i++){
			if(data.trim().equals(list.get(i).getText().trim())){
				Reporter.log("Filerted Data verified Successfully" +list.get(i).getText().trim(),true);
			}
		}
	}
	
	public void click_email_link(){
		email_link.click();
	}
	
	public boolean isUnvacteLinkDisplayed(){

		return clk_UnavteLink.isDisplayed();
	}


	public void clkUnavteLink(){

		clk_UnavteLink.click();
	}
	
	public void click_resendEmail_link(){
		resendEmail_link.click();
	}
	
}
