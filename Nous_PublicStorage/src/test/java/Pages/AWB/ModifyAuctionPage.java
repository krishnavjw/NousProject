package Pages.AWB;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;


public class ModifyAuctionPage {
	
	private WebDriver driver;
	
	@FindBy(xpath="//span[@id='ModifyAuctionMW_wnd_title']")
	private WebElement pagetitle;
	
	@FindBy(id="txtHours")
	private WebElement hoursTxt;
	
	@FindBy(id="txtMinutes")
	private WebElement minutesTxt;
	
	@FindBy(id="txtReason")
	private WebElement reasonTxt;
	
	@FindBy(id="modifyAuctionbtnSave")
	private WebElement saveBtn;
	
	@FindBy(id="modifyAuctionbtnCancel")
	private WebElement cancelBtn;
	
	@FindBy(id="IdAM")
	private WebElement amRadio;
	
	@FindBy(id="IdPM")
	private WebElement pmRadio;
	
	
	public ModifyAuctionPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean Verify_PageTitle(){
		return pagetitle.isDisplayed();
	}
	
	public void enterHours(String hours){
		hoursTxt.sendKeys(hours);
	}
	
	public void clearHours(){
		hoursTxt.clear();
	}
	
	public void enterMinutes(String min){
		minutesTxt.sendKeys(min);
	}
	
	public void clearMinutes(){
		minutesTxt.clear();
	}
	
	public void enterReason(String reason){
		reasonTxt.sendKeys(reason);
	}
	
	public void clearReason(){
		reasonTxt.clear();
	}
	
	public void click_SaveBtn(){
		saveBtn.click();
	}
	
	public void click_CancelBtn(){
		cancelBtn.click();
	}
	
	public void click_Am(){
		amRadio.click();
	}
	
	public void click_PM(){
		pmRadio.click();
	}
	
	public void select_DateFromCalender(String year,String month,String date) throws InterruptedException{
		driver.findElement(By.xpath("//input[@id='modifyAuctionDate']/following-sibling::span/span[text()='select']")).click();
		Thread.sleep(2000);
		
		//String currentdate=driver.findElement(By.xpath("//div[@id='modifyAuctionDate_dateview']//table[@class='k-content']/tbody/tr/td[contains(@aria-label,'Current focused date ')]/a")).getText();
		WebElement select_Date=driver.findElement(By.xpath("//div[@id='modifyAuctionDate_dateview']//table[@class='k-content']/tbody/tr/td/a[@data-value='"+year+"/"+month+"/"+date+"']"));
				select_Date.click();
				}
	
	public  void SelectDateFromCalendar(String data,WebDriver driver) {
        try {

        	driver.findElement(By.xpath("//input[@id='modifyAuctionDate']/following-sibling::span/span[text()='select']")).click();
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
                    
               List<WebElement> CurMnthNoDays = driver.findElements(By.xpath("//div[@id='modifyAuctionDate_dateview']/div/table/tbody/tr/td/a"));
               

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
