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

public class ChangePaymentCommitmentPage {
	
	WebDriver driver;
	
	@FindBy(id="confirmChangePaymentCommitmentDialog_wnd_title")
	private WebElement title;
	
	@FindBy(xpath="//div[@id='new-payment-commitment-date']/span")
	private WebElement selectDate;
	
	@FindBy(id="noteText")
	private WebElement Note;
	
	@FindBy(id="employeeNumber")
	private WebElement employeeNum;
	
	@FindBy(id="confirm-payment-commitment-button")
	private WebElement confirmBtn;
	
	@FindBy(id="cancel-payment-commitment-button")
	private WebElement cancelBtn;
	
	@FindBy(xpath="//a[contains(text(),'OK')]")
	private WebElement okBtn;
	
	public ChangePaymentCommitmentPage(WebDriver driver)
	{
		this.driver= driver;
		PageFactory.initElements(driver, this);

	}
	
	public boolean isDisplayed_Title(){
		return title.isDisplayed();
	}
	
	public void click_Selectbutton(){
		selectDate.click();
	}
	
	public void enterNotes(String note) 
	{
		Note.sendKeys(note);
	}
	
	public void enterEmployeeNum(String data)
	{
		employeeNum.sendKeys(data);
	}
	
	public void click_ConfirmBtn(){
		confirmBtn.click();
	}
	
	public void click_CancelBtn(){
		cancelBtn.click();
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


			String ActMonth = driver.findElement(By.xpath("(//div[@id='dualDatePicker']//span[@class='ui-datepicker-month'])[1]")).getText();
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


		}
		catch(Exception e)
		{
			Reporter.log("Exception:dateValidations"+ e ,true);
		}
	}

	
	public void click_OkBtn(){
		okBtn.click();
	}
}
