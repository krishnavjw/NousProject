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
import org.testng.Reporter;

public class OverlockSpaceTaskPage 
{

	WebDriver driver;
	
	@FindBy(xpath="//div[@id='OverlockDate']/span")
	private WebElement dateofOverlock;
	
	@FindBy(xpath="//div[@id='OverlockDate']/span")
	private WebElement selected_DateValue;
	
	
	@FindBy(xpath="//label[@id='CompletedStatus']/span[@class='button']")
	private WebElement complete_RadioButton;
	
	@FindBy(id="btnSubmit")
	private WebElement button_Submit;
	
	@FindBy(id="employeeNumber")
	private WebElement employeeIDField;
	
	
	@FindBy(xpath="//a[contains(text(),'Confirm')]")
	private WebElement confirm_button;
	
	
	
	
	public OverlockSpaceTaskPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		

	}
	
	
	
	
	
	
		public  void SelectDateFromCalendar(String data) {
        try {

        	dateofOverlock.click();
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



             //  String ActMonth = driver.findElement(By.xpath("(//div[@id='dualDatePicker']//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[1]")).getText();
            // String ActNextMonth = driver.findElement(By.xpath("(//div[@id='dualDatePicker']//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[2]")).getText();            

               List<WebElement> CurMnthNoDays = driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']//td[@data-handler='selectDay']/a"));
           //  List<WebElement> NxtMnthNoDays = driver.findElements(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-last']//td[@data-handler='selectDay']"));


               /* if(ActMonth.equals(month) && ActNextMonth.equals(nmonth))
   {*/
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

	
		
		
		
		
		public String getselectedDateValue()
	     {
	  	 return selected_DateValue.getText();
	     }
		
		
		public void select_CompleteRadioButton()
		{
			complete_RadioButton.click();
		}
		
		
		public void click_Submitbutton()
		{
			button_Submit.click();
		}
		
		
		public void enterEmployeeID(String str)
		{
			employeeIDField.sendKeys(str);
		}
		
		
		public void click_ConfirmButton()
		{
			confirm_button.click();
		}


}
