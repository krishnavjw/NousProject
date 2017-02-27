package Pages.TaskManagement;

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
//import org.sqlite.util.StringUtils;
import org.testng.Reporter;

public class TaskStatusPage {
	
	WebDriver driver;
	
	
	@FindBy(xpath="//label[@id='IncompleteStatus']/span[@class='button']")
	private WebElement incomplete_RadioBtn;
	
	@FindBy(xpath="//label[@id='CompletedStatus']/span[@class='button']")
	private WebElement complete_RadioBtn;
	
	@FindBy(xpath="//input[@id='Task_Comment']")
	private WebElement commentTxtbx;
	
	@FindBy(id="btnSubmit")
	private WebElement submit_Btn;
	
	@FindBy(id="employeeNumber")
	private WebElement empNum;
	
	@FindBy(xpath="//a[contains(text(),'Confirm')]")
	private WebElement confirmBtn;
	
	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement cancelBtn;
	

	@FindBy(xpath="//a[@id='btnSaveInProgress']")
	private WebElement saveInProgress;
	
	@FindBy(xpath="//input[@id='CurrentAmount']")
	private WebElement currentAmount;
	
	
	@FindBy(xpath="//h3[contains(text(),'Edit Simple Task')]")
	private WebElement TitleTaskPage;
	
	
	@FindBy(xpath="//span[contains(text(),'Please enter a comment')]")
	private WebElement errorMess;
	
	@FindBy(xpath="//div[@id='RemoveOverlockDate']/span[text()='SELECT']")
	private WebElement dateOverlocked;
	
	
	@FindBy(xpath="//form[@id='viewTaskForm']//span[text()='Select All']/preceding-sibling::span[@class='button']")
	private WebElement sel_AllCheckbx;
	
	
	
	
	
	
	
	
	public TaskStatusPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	public void clk_CompleteRadioBtn(){
		complete_RadioBtn.click();
	}
	
	public void clk_IncompleteRadioBtn(){
		incomplete_RadioBtn.click();
	}
	
	public void enter_Comment(String comment){
		commentTxtbx.sendKeys(comment);
	}
	
	public void clk_SubmitBtn(){
		submit_Btn.click();
	}
	
	public void enter_EmployeeNumber(String emp){
		empNum.sendKeys(emp);
	}
	
	public void clk_ConfirmBtn(){
		confirmBtn.click();
	}
	
	public void clk_CancelBtn(){
		cancelBtn.click();
	}
	
	public void clk_TaskCheckbox(WebDriver driver){
		List<WebElement> listTaskType = driver.findElements(By.xpath("//ul[@class='webchamp-checkbox-list align-vertical']//li"));
		listTaskType.get(0).click();
		
	}
	
	
	
	public boolean isSelected_TaskCheckbox(WebDriver driver){
		List<WebElement> listTaskType = driver.findElements(By.xpath("//ul[@class='webchamp-checkbox-list align-vertical']//li"));
		listTaskType.get(0).isSelected();
		return true;
		
	}
	
	
	
	public void enter_Amount(String Amount){
		currentAmount.sendKeys(Amount);
	}
	
	
	public void Select_Checkbx(){
		sel_AllCheckbx.click();
	}
	
	
	
	
	public void clk_saveInProgress(){
		saveInProgress.click();
	}
	
	
	public boolean  isTaskPageTitleDisplayed(){
		return TitleTaskPage.isDisplayed();
	}
	
	
	
	public  void SelectDateFromCalendar(String data,WebDriver driver) {
        try {

        	driver.findElement(By.xpath("//div[@id='CustomerCalledDate']/span")).click();
             Thread.sleep(2000);
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

              // String ActMonth = driver.findElement(By.xpath("(//div[@id='dualDatePicker']//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[1]")).getText();
                    
               List<WebElement> CurMnthNoDays = driver.findElements(By.xpath("//td[@data-handler='selectDay']/a"));
               

               List<WebElement> Ls_Days;

               if(CurMnthNoDays.size()>=value)
               {
                     Ls_Days = CurMnthNoDays;
               }
               else
               {
                     Ls_Days = CurMnthNoDays;
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

	
	public boolean verify_ErrorMessage(){
		return errorMess.isDisplayed();
	}
	
	public void clk_DateOverlocked(){
		dateOverlocked.click();
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
	
	public  void SelectDateFromCalendar_BigSkyTask(String data,WebDriver driver) {
        try {

        	driver.findElement(By.xpath("//div[@id='BigSkyWorkRequestDate']/span")).click();
             Thread.sleep(2000);
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

              // String ActMonth = driver.findElement(By.xpath("(//div[@id='dualDatePicker']//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[1]")).getText();
                    
               List<WebElement> CurMnthNoDays = driver.findElements(By.xpath("//td[@data-handler='selectDay']/a"));
               

               List<WebElement> Ls_Days;

               if(CurMnthNoDays.size()>=value)
               {
                     Ls_Days = CurMnthNoDays;
               }
               else
               {
                     Ls_Days = CurMnthNoDays;
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
	

	
	
	
	public  void SelectDateFromCalendar_LockCut(String data,WebDriver driver) {
        try {

        	driver.findElement(By.xpath("//div[@id='CustomerRequestedLockCutDate']/span")).click();
             Thread.sleep(2000);
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

              // String ActMonth = driver.findElement(By.xpath("(//div[@id='dualDatePicker']//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[1]")).getText();
                    
               List<WebElement> CurMnthNoDays = driver.findElements(By.xpath("//td[@data-handler='selectDay']/a"));
               

               List<WebElement> Ls_Days;

               if(CurMnthNoDays.size()>=value)
               {
                     Ls_Days = CurMnthNoDays;
               }
               else
               {
                     Ls_Days = CurMnthNoDays;
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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
