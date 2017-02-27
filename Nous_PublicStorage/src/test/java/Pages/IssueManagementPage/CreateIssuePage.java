package Pages.IssueManagementPage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;


public class CreateIssuePage {
	
	private WebDriver driver;

	@FindBy(xpath="//span[contains(text(),'Select Issue Type')]") //html/body/div[27]/div[1]/span
	private WebElement title;
	
	@FindBy(xpath="//div[@id='addDocumentDialog']//span[contains(text(),'Select Issue Type')]")
	private WebElement issueTypeDropdown;
	
	@FindBy(xpath="//div[@class='k-list-container k-popup k-group k-reset']//ul/li[contains(text(),'Rent Rate Increase Objection')]")
	private WebElement rentRateIncre;
	
	
	@FindBy(xpath="//a[contains(text(),'Confirm')]")
	private WebElement confirmBtn;
	
	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement cancelBtn;
	
	
	
	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header']//span[text()='Select Issue Type']")
	private WebElement IssueTypeDropdown;
	
	
	@FindBy(xpath="//input[@id='RequestedCredit']")
	private WebElement CreditAmount;
	

	@FindBy(xpath="//input[@id='ConcessionAmount']")
	private WebElement ApprovedCredit;
	
	@FindBy(xpath="//a[@id='approvedeclineButton']")
	private WebElement continueBtn;
	
	@FindBy(xpath="//span[text()='Approve']/preceding-sibling::span")
	private WebElement DecisionApprove_Radio;
	
	
	@FindBy(xpath="//a[contains(text(),'Confirm')]")
	private WebElement ConfirmBtn;
	
	@FindBy(xpath="//textarea[@id='notesText']")
	private WebElement Notes_Txtarea;
	
	
	@FindBy(xpath="//ul[@id='issueTypeCombo_listbox']/li[contains(text(),'Fee Adjustment Request')]")//div[@class='k-list-container k-popup k-group k-reset']//ul/li[contains(text(),'Rent Rate Increase Objection')]
	private WebElement FeeAdjusRequest;
	
	@FindBy(xpath="//ul[@id='issueTypeCombo_listbox']/li[contains(text(),'Rent Rate Decrease Request')]")
	private WebElement rentRateDecrease;
	
	@FindBy(xpath="//div[@class='k-list-container k-popup k-group k-reset']//ul/li[contains(text(),'Move Out Billing Dispute')]")
	private WebElement moveOutBillingDispute;
	
	
	
	public CreateIssuePage(WebDriver driver){
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean verify_Title(){
		return title.isDisplayed();
	}
	
	public void click_IssueTypeDropDown(){
		issueTypeDropdown.click();
	}
	
	public void select_RentRateIncreObjection(){
		rentRateIncre.click();
	}
	
	public void click_ConfirmmBtn(){
		confirmBtn.click();
	}

	public void click_CancelBtn(){
		cancelBtn.click();
	}
	
	
	public void select_moveOutBillingDispute(){
		moveOutBillingDispute.click();
	}
	
	
	public void select_IssueType(String issueType) throws InterruptedException{
		IssueTypeDropdown.click();
		List<WebElement> taskList = driver.findElements(By.xpath("//ul[@id='issueTypeCombo_listbox']/li"));
		Actions act = new Actions(driver);
		for(WebElement ele:taskList)
		{
			act.moveToElement(ele).build().perform();
			System.out.println(ele.getText());
			if(ele.getText().trim().contains(issueType))
			{
				act.moveToElement(ele).click().build().perform();
				break;
			}
			
			Thread.sleep(1000);
		}
	}
	
	
	
	public void clk_IssueTypeDropdown(){
		IssueTypeDropdown.click();
	}
	
	
	
	public void select_DisputeType(String DisputeType){
		driver.findElement(By.xpath("//span[text()='Unused Rent in Current Month']")).click();
		List<WebElement> taskList = driver.findElements(By.xpath("//ul[@id='disputeTypeDropDownList_listbox']/li"));
		Actions act = new Actions(driver);
		for(WebElement ele:taskList)
		{
			act.moveToElement(ele).build().perform();
			System.out.println(ele.getText());
			if(ele.getText().trim().contains(DisputeType))
			{
				act.moveToElement(ele).click().build().perform();
				break;
			}
			
	
		}
		
		
	}
	
	
	public void enter_CreditAmount(String Amount){
		CreditAmount.sendKeys(Amount);
		
	}
	
	
	
	public void enter_ApprovedCredit(String Amount){
		ApprovedCredit.sendKeys(Amount);
		
	}
	
	public void click_continueBtn(){
		continueBtn.click();
		
	}
	
	
	

	public void click_DecisionApprove_Radio(){
		DecisionApprove_Radio.click();
		
	}
	
	
	public void click_ConfirmBtn(){
		ConfirmBtn.click();
		
	}
	
	
	public void enter_Notes_Txtarea(String Notes){
		Notes_Txtarea.sendKeys(Notes);
		
	}
	
	

	
	
	
	
	
	public  void SelectDateFromCalendar(String data) {
        try {

        	driver.findElement(By.xpath("//div[@id='ClaimedVacateDate']/span")).click();
               int value = Integer.parseInt(data);
               Calendar c = GregorianCalendar.getInstance();
               c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
               Calendar cal = Calendar.getInstance();
               cal.add(Calendar.DAY_OF_YEAR, value);
               SimpleDateFormat df = new SimpleDateFormat("dd",Locale.getDefault());
               String strTodaysDate = df.format(cal.getTime());
               strTodaysDate = StringUtils.stripStart(strTodaysDate,"0");
               Reporter.log("strTodaysDate: "+ strTodaysDate,true);
               Thread.sleep(2000);
               Calendar mCalendar = Calendar.getInstance();   

               String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
               mCalendar.add(Calendar.MONTH,1);
               String nmonth = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
               String ExpDay = strTodaysDate;



               String ActMonth = driver.findElement(By.xpath("(//div[@id='dualDatePicker']//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[1]")).getText();
               String ActNextMonth = driver.findElement(By.xpath("(//div[@id='dualDatePicker']//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[2]")).getText();            

               List<WebElement> CurMnthNoDays = driver.findElements(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-first']//td[@data-handler='selectDay']"));
               List<WebElement> NxtMnthNoDays = driver.findElements(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-last']//td[@data-handler='selectDay']"));


               /* if(ActMonth.equals(month) && ActNextMonth.equals(nmonth))
   {*/
               List<WebElement> Ls_Days;

               if(CurMnthNoDays.size()>=value)
               {
                     Ls_Days = CurMnthNoDays;
               }
               else
               {
                     Ls_Days = NxtMnthNoDays;
               }
               Thread.sleep(1000);
               for(int i=0;i<Ls_Days.size();i++)
               {
                     String AvailableDay = Ls_Days.get(i).getText();
                     if(AvailableDay.equals(ExpDay))
                     {
                            Ls_Days.get(i).click();
                            break;
                     }
               }
               // }


        }
        catch(Exception e)
        {
               Reporter.log("Exception:dateValidaitons"+ e ,true);
        }
 }


	public void select_FeeAdjusReq(){
		FeeAdjusRequest.click();
	}
	
	public void select_RentRateDecreaseReq(){
		rentRateDecrease.click();
	}
	
	
	
	
	
	
	
	
	
}
