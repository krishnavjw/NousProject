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

public class ScheduleVacate_ScheduledVacatePage 
{

	WebDriver driver;
	@FindBy(xpath="//div[@class='k-window-titlebar k-header']/span[@id='changeScheduledVacateDateDialog_wnd_title']")
	private WebElement scheduledVacate_Title;

	@FindBy(xpath="//div[@id='changeScheduledVacateDateDialog']//div[contains(text(),'New Date:')]")
	private WebElement newDate_label;

	@FindBy(xpath="//div[@id='changeScheduledVacateDateDialog']//div[contains(text(),'New Date:')]/following-sibling::div//div[@id='schedule-vacate-date']/span")
	private WebElement newDateCalendar_icon;

	@FindBy(xpath="//div[@id='dualDatePicker']//table/tbody/tr//td[contains(@class,'ui-datepicker-today')]")
	private WebElement currentDate_Disabled;

	@FindBy(id="employeeNumber")
	private WebElement empNum_txt;

	@FindBy(id="update-vacate-button")
	private WebElement updateVacate_btn;

	@FindBy(xpath="//div[contains(text(),'Current Date:')]/following-sibling::div")
	private WebElement get_curentDate;

	@FindBy(xpath="//div[contains(text(),'Estimated Balance Due:')]/following-sibling::div")
	private WebElement get_EstBalDue;

	@FindBy(linkText="Cancel")
	private WebElement cancelBtn;

	@FindBy(linkText="Remove Vacate")
	private WebElement removeVacateBtn;

	@FindBy(linkText="Update Vacate")
	private WebElement updateVacateBtn;

	@FindBy(xpath="//div[contains(@id,'schedule-vacate-date')]/span")
	private WebElement clk_DateCalenderIcon;

	@FindBy(xpath="//span[text()='Please enter an employee number.']")
	private WebElement get_Emperrormsg;
	
    @FindBy(xpath="//div[@id='schedule-vacate-date']/span")
    private WebElement get_SchVaceteDateFromCalender;

	public ScheduleVacate_ScheduledVacatePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}

	public String verifyScheduledVacate_Title() {
		return scheduledVacate_Title.getText();	
	}

	public String verifyNewDate_field()
	{
		return newDate_label.getText().trim();
	}

	public void clickCalendarIcon()
	{
		newDateCalendar_icon.click();
	}

	public String verifyCurrentDate_IsDisabled()
	{
		return currentDate_Disabled.getAttribute("class");
	}



	public void enter_empNum_txt(String username){
		empNum_txt.sendKeys(username);
	}

	public void click_updateVacate_btn(){
		updateVacate_btn.click();
	}

	public boolean isScheduledVacatePopUpDisplayed(){
		return scheduledVacate_Title.isDisplayed();
	}
	public String get_curentDate(){

		return get_curentDate.getText().trim();
	}

	public String get_EstBalDue(){

		return get_EstBalDue.getText().trim();
	}

	public void clk_CancelBtn(){

		cancelBtn.click();

	}

	public void clk_RemoveVacateBtn(){

		removeVacateBtn.click();

	}


	public void clk_UpdateVacateBtn(){

		updateVacateBtn.click();

	}

	public boolean isCancelBtnDisplayed(){

		return cancelBtn.isDisplayed();
	}

	public boolean isRemoveVacateBtnDisplayed(){

		return removeVacateBtn.isDisplayed();
	}

	public boolean isUpdateVacateBtnDisplayed(){

		return updateVacateBtn.isDisplayed();
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



			String ActMonth = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[1]")).getText();
			String ActNextMonth = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[2]")).getText();            

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



	public void clk_VacteDateCalenderIcon(){

		clk_DateCalenderIcon.click();
	}

	public boolean isEmperrormsgDisplayed(){

		return get_Emperrormsg.isDisplayed();

	}
	
	public String get_SchVaceteDateFromCal(){
		
		return get_SchVaceteDateFromCalender.getText();
	}

}
