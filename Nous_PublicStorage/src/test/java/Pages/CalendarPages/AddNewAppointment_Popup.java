package Pages.CalendarPages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.gargoylesoftware.htmlunit.javascript.host.media.webkitAudioContext;

public class AddNewAppointment_Popup {
	
	
	WebDriver driver;
	
	@FindBy(xpath="//span[@id='newDialog_wnd_title']")
	private WebElement title;
	
	@FindBy(xpath="//span[@id='editDialog_wnd_title']")
	private WebElement editTitle;
	
	@FindBy(xpath="//form[@id='newForm']//input[@id='Text']")
	private WebElement appointmentTitleTxt;
	
	@FindBy(xpath="//form[@id='newForm']//div[@id='Start']/span")
	private WebElement startDropdown;
	
	@FindBy(xpath="//form[@id='newForm']//div[@id='End']/span")
	private WebElement endDropdown;
	
	@FindBy(xpath="//form[@id='newForm']//input[@id='StartTime']/../span")
	private WebElement startTime;
	
	@FindBy(xpath="//form[@id='newForm']//input[@id='EndTime']/../span")
	private WebElement endTime;
	
	@FindBy(xpath="//div[@id='Desc_Loading']")
	private WebElement description;
	
	@FindBy(xpath="//form[@id='newForm']//button[contains(text(),'Add Appointment')]")
	private WebElement addAppointmentBtn;
	
	@FindBy(xpath="//form[@id='newForm']//button[contains(text(),'Cancel')]")
	private WebElement cancelBtn;
	
	
	@FindBy(xpath="//form[@id='editForm']//input[@id='eText']")
	private WebElement editAppointmentTitleTxt;
	
	@FindBy(xpath="//form[@id='editForm']//div[@id='eStart']/span")
	private WebElement editStartDropdown;
	
	@FindBy(xpath="//form[@id='editForm']//div[@id='eEnd']/span")
	private WebElement editEndDropdown;
	
	@FindBy(xpath="//form[@id='editForm']//input[@id='eStartTime']/../span")
	private WebElement editStartTime;
	
	@FindBy(xpath="//form[@id='editForm']//input[@id='eEndTime']/../span")
	private WebElement editEndTime;
	
	@FindBy(xpath="//form[@id='editForm']//span[@id='eEditControls']/button[@id='editAppt']")
	private WebElement saveBtn;
	
	@FindBy(xpath="//form[@id='editForm']//span[@id='eEditControls']/button[@id='deletebtn']")
	private WebElement deleteBtn;
	
	@FindBy(xpath="//form[@id='editForm']//span[@id='eEditControls']/button[contains(text(),'Cancel')]")
	private WebElement editCancelBtn;
	
	
	public AddNewAppointment_Popup(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isDisplayed_TitlePopup(){
		return title.isDisplayed();
	}
	
	
	public void enterTxt(String text){
		appointmentTitleTxt.sendKeys(text); 
	}
	
	public void clk_StartDropdown(){
		startDropdown.click();
	}
	
	public void clk_StartTime(){
		startTime.click();
	}
	
	public void clk_EndDropdown(){
		endDropdown.click();
	}
	
	public void clk_EndTime(){
		endTime.click();
	}
	
	public void enterDescription(String text){
		description.sendKeys(text); 
	}
	
	
	public void clk_AddAppointmentBtn(){
		addAppointmentBtn.click();
	}
	
	public void clk_CancelBtn(){
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


		}
		catch(Exception e)
		{
			Reporter.log("Exception:dateValidations"+ e ,true);
		}
	}

	
	public void selectStartTime(String data) throws InterruptedException{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		
		List<WebElement> allTime = driver.findElements(By.xpath("//div[@id='StartTime-list']/ul[@id='StartTime_listbox']/li"));
		for(int i=0;i<allTime.size();i++){
			if(data.trim().equals(allTime.get(i).getText().trim())){   
				System.out.println(allTime);
				//jse.executeScript("arguments[0].scrollIntoView(true);",allTime.get(i));
				//jse.executeScript("arguments[0].click(true);",allTime.get(i));
				allTime.get(i).click(); 
				break;
				
			}
		}
		
		
	}
	
	public void selectEndTime(String data) throws InterruptedException{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		
		List<WebElement> allTime = driver.findElements(By.xpath("//div[@id='EndTime-list']/ul[@id='EndTime_listbox']/li"));
		for(int i=0;i<allTime.size();i++){
			if(data.trim().equals(allTime.get(i).getText().trim())){    
				jse.executeScript("arguments[0].scrollIntoView(true);",allTime.get(i));
				//jse.executeScript("arguments[0].click(true);",allTime.get(i));
				allTime.get(i).click(); 
				break;
				
			}
		}
		
		
	}

	public boolean isDisplayed_EditTitlePopup(){
		return editTitle.isDisplayed();
	}
	
	public void enterEditTxt(String text) throws InterruptedException{
		editAppointmentTitleTxt.clear();
		Thread.sleep(1000);
		editAppointmentTitleTxt.sendKeys(text); 
	}
	
	public void clk_EditStartDropdown(){
		editStartDropdown.click();
	}
	
	public void clk_EditStartTime(){
		editStartTime.click();
	}
	
	public void clk_EditEndDropdown(){
		editEndDropdown.click();
	}
	
	public void clk_EditEndTime(){
		editEndTime.click();
	}
	
	public void clk_SaveBtn(){
		saveBtn.click();
	}
	
	
	public void EditSelectStartTime(String data) throws InterruptedException{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		
		List<WebElement> allTime = driver.findElements(By.xpath("//div[@id='eStartTime-list']/ul[@id='eStartTime_listbox']/li"));
		for(int i=0;i<allTime.size();i++){
			if(data.trim().equals(allTime.get(i).getText().trim())){    
				jse.executeScript("arguments[0].scrollIntoView()",allTime.get(i)); 
				jse.executeScript("arguments[0].click();",allTime.get(i));
				//allTime.get(i).click(); 
				break;
			}
		}
	}

	public void EditSelectEndTime(String data) throws InterruptedException{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		
		List<WebElement> allTime = driver.findElements(By.xpath("//div[@id='eEndTime-list']/ul[@id='eEndTime_listbox']/li"));
		for(int i=0;i<allTime.size();i++){
			if(data.trim().equals(allTime.get(i).getText().trim())){    
				jse.executeScript("arguments[0].scrollIntoView()",allTime.get(i)); 
				jse.executeScript("arguments[0].click();",allTime.get(i));
				//allTime.get(i).click(); 
				break;
				
			}
		}
		
		
	}
	

}
