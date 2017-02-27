package Pages.CustDashboardPages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class DTMCommitmentCalls {

	
	private WebDriver driver;
	
	
	@FindBy(xpath="//div[@id='NewCommitmentDate']/span")
	private WebElement paymentDateDropdown;
	
	@FindBy(xpath="(//div[@id='divMain']//label/span)[1]")
	private WebElement phonecheck;
	
	@FindBy(xpath="(//div[@id='divMain']//span[contains(text(),'Select Result')])[1]")
	private WebElement resultDrop;
	
	@FindBy(xpath="//div[@id='divMain']/div[2]/div[3]//input")
	private WebElement commentText;
	
	@FindBy(xpath="//button[contains(text(),'Submit Call(s)')]")
	private WebElement submit_Btn;
	
	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement employeeNum;
	
	@FindBy(xpath="//a[contains(text(),'Save & Close')]")
	private WebElement saveAndClose;

	public DTMCommitmentCalls(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}
	
	public void sel_PaymentDropdown(){
		paymentDateDropdown.click();
	}
	
	public void clk_PhonechckBox(){
		phonecheck.click();
	}
	
	public void result_Drop(){
		resultDrop.click();
	}
	
	public void enter_Comment(String comment){
		 commentText.sendKeys(comment);
	}
	
	public void clk_SubmitBtn(){
		submit_Btn.click();
	}
	
	public void enterEmployeeNum(String emp){
		employeeNum.sendKeys(emp);
	}
	
	public void clk_SaveAndClose() {
		saveAndClose.click();
		
	}
	
	public  void SelectDateFromCalendar(String data) {
		try {


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
			String nmonth = mCalendar.getDisplayName(Calendar.YEAR, Calendar.LONG, Locale.getDefault());
			String ExpDay = strTodaysDate;


			String ActMonth = driver.findElement(By.xpath("//div[@id='dualDatePicker']//span[@class='ui-datepicker-month']")).getText();
			//String ActNextMonth = driver.findElement(By.xpath("(//div[@id='dualDatePicker']//span[@class='ui-datepicker-year'])[1]")).getText();
			if(ActMonth.equals(month))
			{
				List<WebElement> AvlDays = driver.findElements(By.xpath("//div[@id='dualDatePicker']//td[@data-handler='selectDay']"));
				for(int i=0;i<AvlDays.size();i++)
				{
					String AvailableDay = AvlDays.get(i).getText();
					if(AvailableDay.equals(ExpDay))
					{
						AvlDays.get(i).click();
						break;
					}
				}
			}

			
		}catch(Exception e)
		{
			Reporter.log("Exception:dateValidations"+ e ,true);
		}
	}
	
	public void sel_DropDownValFromReason(String value) throws InterruptedException{
		resultDrop.click();
		Thread.sleep(2000);
		List<WebElement> lsReason = driver.findElements(By.xpath("//div[@class='k-animation-container']/div//li"));
		for(int i=0;i<lsReason.size();i++)
		{
			String Reason = lsReason.get(i).getText().trim();
			Reporter.log("Reason :"+Reason,true);
			if(Reason.equals(value))
			{
				lsReason.get(i).click();
				break;
			}
		}

	}
	

	
}
