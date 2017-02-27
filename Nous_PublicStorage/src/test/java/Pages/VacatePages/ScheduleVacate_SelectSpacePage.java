package Pages.VacatePages;

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



public class ScheduleVacate_SelectSpacePage 
{
	public WebDriver driver;
	@FindBy(xpath="//div[@class='container-heading padding']/h3[text()='Schedule Vacate']")
	private WebElement schVacate_Title;

	@FindBy(xpath="//div[@class='k-grid-content ps-container']/table/tbody//tr")
	private List<WebElement> vacateSpace_gridVals;

	@FindBy(linkText="Back to Dashboard")
	private WebElement backToDash;

	@FindBy(id="lnkCancel1")
	private WebElement cancel_btn;

	@FindBy(id="lnkReserve")
	private WebElement continue_btn;

	@FindBy(xpath="//div[@id='dualDatePicker']//table/tbody//tr//td[contains(@class,' ui-datepicker-today')]")
	private WebElement vacate_currentDate;

	@FindBy(xpath="//div[contains(@id,'schedule-vacate-date')]") //(xpath="//div[contains(@id,'schedule-vacate-date')]/span")
	private WebElement clk_VacteDateCalenderIcon;

	@FindBy(xpath="//div[starts-with(@id='schedule-vacate-date')]/span")
	private WebElement click_Calender;

	@FindBy(xpath="//div[@id='customerscreenandscriptpanel']//span[contains(text(),'Script')]")
	private WebElement script;

	@FindBy(xpath="//div[@class='k-grid-content ps-container']//a[text()='View Details']")
	private WebElement viewDetails;

	@FindBy(xpath="//div[contains(@id,'schedule-vacate-date')]/span")
	private WebElement calender_Icon;

	@FindBy(xpath="//div[@id='unitgridtabstrip']//div//table/tbody/tr//td/a[contains(text(),'View Details')]")
	private WebElement viewDetails_Lnk;


	public ScheduleVacate_SelectSpacePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}

	public String verifySchVacate_Title()
	{
		return schVacate_Title.getText();
	}

	public void clickCancel_btn()
	{
		cancel_btn.click();
	}

	public void clickContinue_btn()
	{
		continue_btn.click();
	}

	public void clickBackBashboard_btn()
	{
		backToDash.click();
	}

	public void clickViewDetails_lk(String space)
	{
		for(WebElement ele: vacateSpace_gridVals)
		{
			if(space.equalsIgnoreCase(ele.getText().trim()))
			{
				String xpath="//div[@class='k-grid-content ps-container']/table/tbody//tr//td[text()='"+space+"']/following-sibling::td/a[text()='View Details']";
				driver.findElement(By.xpath(xpath)).click();
			}
		}
	}

	////div[@class='k-grid-content ps-container']/table/tbody//tr//td[text()='A412']/following-sibling::td//div[@id='schedule-vacate-date-42569961']
	public void clickCalendar_Field(String space)
	{
		for(WebElement ele: vacateSpace_gridVals)
		{
			if(space.equalsIgnoreCase(ele.getText().trim()))
			{
				String xpath="//div[@class='k-grid-content ps-container']/table/tbody//tr//td[text()='"+space+"']/following-sibling::td//div[@id='schedule-vacate-date-42569961']";
				driver.findElement(By.xpath(xpath)).click();
			}

		}


	}

	public void clk_VacteDateCalenderIcon(){

		clk_VacteDateCalenderIcon.click();
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


	public boolean verify_scheduleVacatePage(){
		return schVacate_Title.isDisplayed();
	}

	public boolean verify_CalenderIcon(){
		return calender_Icon.isDisplayed();
	}

	public void click_CalenderIcon()
	{
		calender_Icon.click();
	}
	public boolean IsScriptPresnt(){
		return script.isDisplayed();
	}

	public void click_ViewDetails(){
		viewDetails.click();
	}

	public void clickViewDetails(){
		viewDetails_Lnk.click();
	}

}
