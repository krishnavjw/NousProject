package Pages.AWB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.print.attribute.standard.MediaSize.NA;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class Auction_modelwindow {

	WebDriver driver;
	
	@FindBy(xpath="//button[@id='ScheduleAuctionMWOpen']")
	private WebElement btn_newaction;
	
	
	@FindBy(xpath="//span[@id='ScheduleAuctionMW_wnd_title']")
	private WebElement MW_title;
	
	@FindBy(xpath="(//span[@class='k-widget k-datepicker k-header']//span[contains(text(),'select')])[2]")
	private WebElement MW_calender;

	@FindBy(xpath="//input[@id='createtxtHours']")
	private WebElement MW_calender_HH;
	
	@FindBy(xpath="//input[@id='createtxtMinutes']")
	private WebElement MW_calender_MM;
	
	
	@FindBy(xpath="//input[@id='NewradioName']/following-sibling::span[2]")
	private WebElement MW_calender_PM_AM;

	
	@FindBy(xpath="//div[@id='ScheduleAuctionMW']//button[@id='btnCancel']")
	private WebElement MW_cancel;
	
	@FindBy(xpath="//div[@id='ScheduleAuctionMW']//button[@id='btnSave']")
	private WebElement MW_save;
	
	
	
	@FindBy(xpath="//span[contains(text(),'Scheduled Date')]/following-sibling::span/span")
	private WebElement selection_date;
	
	
	public Auction_modelwindow(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	
	public void click_btn_newaction(){
		btn_newaction.click();
	}
	
	public void click_calender(){
		MW_calender.click();
	}
	
	public String get_MW_title(){
		return MW_title.getText();
	}
	
	
	public boolean getMW_calender_HH(){
		return MW_calender_HH.isDisplayed();
	}
	
	
	public String getcalender_HH(){
		return MW_calender_HH.getText();
	}
	
	public boolean display_selection_date(){
		return selection_date.isDisplayed();
	}
	
	public String get_selection_date(){
		return selection_date.getText();
	}
	
	public boolean getMW_calender_MM(){
		return MW_calender_MM.isDisplayed();
	}
	
	public String getcalender_MM(){
		return MW_calender_MM.getText();
	}
	
	
	public boolean isdisplayMW_calender_PM_AM(){
		return MW_calender_PM_AM.isDisplayed();
	}
	
	
	public void click_MW_cancel(){
		MW_cancel.click();
	}
	
	public boolean  display_MW_cancel(){
		return MW_cancel.isDisplayed();
	}
	
	public void click_MW_save(){
		MW_save.click();
	}
	
	public boolean display_MW_save(){
		return MW_save.isDisplayed();
	}
	
	
	
	public  boolean SelectDateFromCalendar(int data) {
		
		boolean flag=false;
			try {


				int value = data;
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



				//String ActMonth = driver.findElement(By.xpath("(//div[@id='createAuctionDate_dateview']//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[1]")).getText();
				

				List<WebElement> CurMnthNoDays = driver.findElements(By.xpath("//div[@id='createAuctionDate_dateview']//td/a"));
				


				/* if(ActMonth.equals(month) && ActNextMonth.equals(nmonth))
	         {*/
				List<WebElement> Ls_Days;

				
					Ls_Days = CurMnthNoDays;
				
				Thread.sleep(1000);
				for(int i=0;i<Ls_Days.size();i++)
				{
					String AvailableDay = Ls_Days.get(i).getText();
					if(AvailableDay.equals(ExpDay))
					{
						if(Ls_Days.get(i).isSelected()){
							
							flag=true;
						}else{
							flag=false;
						}
					
					}
				}
				// }


			}
			catch(Exception e)
			{
				Reporter.log("Exception:dateValidaitons"+ e ,true);
			}
			return flag;
			
			
		}

		
	
	public  void SelectDateFromCalendarAWB(String data) {
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



		//	String ActMonth = driver.findElement(By.xpath("(//div[@id='dualDatePicker']//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[1]")).getText();
		

			List<WebElement> CurMnthNoDays = driver.findElements(By.xpath("//div[@id='createAuctionDate_dateview']//td/a"));
			


			/* if(ActMonth.equals(month) && ActNextMonth.equals(nmonth))
         {*/
			List<WebElement> Ls_Days;

			
				Ls_Days = CurMnthNoDays;
			
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


	
	
}
