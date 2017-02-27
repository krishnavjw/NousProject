package Pages.VacatePages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;



public class Vacate_SkipVacatePage {

	WebDriver driver;
	WebDriverWait wait;

	public Vacate_SkipVacatePage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
		 wait=new WebDriverWait(driver,60);
	}

	@FindBy(xpath="//h3[text()='Skip Vacate']")
	private WebElement skipVacatePage_title;

	@FindBy(xpath="//input[@id='DmApproval']/following-sibling::span[@class='button']")
	private WebElement DMApproved_chkBox;

	@FindBy(id="rentalLockStatusType")
	private WebElement lockStatus_drpDown;

	@FindBy(xpath="//li[@id='RentalLockStatusId_option_selected']/following-sibling::li")
	private List<WebElement> lookStatus_drpDownOptions;

	@FindBy(xpath="//input[@id='LastAccessDateUnknown']/following-sibling::span[@class='button']")
	private WebElement infoNotAvailable_chkBox;

	@FindBy(id="lnkReserve")
	private WebElement continue_Btn;

	@FindBy(id="lnkCancel1")
	private WebElement cancel_Btn;
	
	@FindBy(id="lastAccessDate")
	private WebElement lastAcessDate_Calender;
	
	@FindBy(xpath="//input[@id='LastAccessDateHours']")
	private WebElement HH_Edt;
	
	@FindBy(id="LastAccessDateMinutes")
	private WebElement MM_Edt;
	
	@FindBy(xpath="//label[@class='webchamp-radio-button margin-right']/span[text()='AM']/preceding-sibling::span[@class='button']")
	private WebElement AM_RadBtn;
	
	@FindBy(xpath="//label[@class='webchamp-radio-button ']/span[text()='PM']/preceding-sibling::span[@class='button']")
	private WebElement PM_RadBtn;
	
	@FindBy(xpath="//form[@id='vacateMiscChargesForm']//span[text()='Select Charge']")
	private WebElement selCharge_DrpDown;
	
	@FindBy(xpath="//li[text()='Select Charge']/following-sibling::li")
	private List<WebElement> selCharge_DrpDownOptions;
	
	@FindBy(linkText="Add Charge")
	private WebElement addCharge_btn;
	
	@FindBy(xpath="(//div[@id='misc-charge-view']//div[@class='added-charges-container']//textarea[@class='note charge-note webchamp-textbox'])[2]")
	private WebElement textArea;
	
	@FindBy(xpath="//span[text()='Space']")
	private WebElement space_radioBtn;
	
	@FindBy(xpath="//span[text()='Property']/preceding-sibling::span[@class='button']")
	private WebElement property_radioBtn;
	
	@FindBy(xpath="//div[@id='isMaintenanceIssue']//span[text()='Yes']/preceding-sibling::span[@class='button']")
	private WebElement spaceNeedMaintenance_yesRadioBtn;
	
	@FindBy(xpath="//div[@id='isMaintenanceIssue']//span[text()='No']/preceding-sibling::span[@class='button']")
	private WebElement spaceNeedMaintenance_NoRadioBtn;
	

	public String getSkipVacatePage_title(){
		
		wait.until(ExpectedConditions.visibilityOf(skipVacatePage_title));
		return skipVacatePage_title.getText();
	}

	public void clickDMApproved_chkBox(){
		wait.until(ExpectedConditions.visibilityOf(DMApproved_chkBox));
		DMApproved_chkBox.click();
	}

	public void clickLockStatus_drpDown() throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOf(lockStatus_drpDown));
		lockStatus_drpDown.click();
		Thread.sleep(2000);
	}

	public void selFromDropDownOptions(String optionToSelect){
		Iterator<WebElement> itr=lookStatus_drpDownOptions.iterator();
		while(itr.hasNext()){
			WebElement wbEle=itr.next();
			if(wbEle.getText().contains(optionToSelect)){
				wbEle.click();
			}

		}
	}

	public void clickInfoNotAvailable_chkBox(){
		wait.until(ExpectedConditions.visibilityOf(infoNotAvailable_chkBox));
		infoNotAvailable_chkBox.click();
	}

	public void click_continue_Btn(){
		wait.until(ExpectedConditions.visibilityOf(continue_Btn));
		continue_Btn.click();
	}
	
	public void clickLastAcessDate_Calender(){
		wait.until(ExpectedConditions.visibilityOf(lastAcessDate_Calender));
		lastAcessDate_Calender.click();
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
	
	public void enterHH_Edt(String HH){
		wait.until(ExpectedConditions.visibilityOf(HH_Edt));
		HH_Edt.sendKeys(HH);
	}

	public void enterMM_Edt(String MM){
		wait.until(ExpectedConditions.visibilityOf(MM_Edt));
		MM_Edt.sendKeys(MM);
	}
	
	public void clickAM_radioBtn(){
		wait.until(ExpectedConditions.visibilityOf(AM_RadBtn));
		AM_RadBtn.click();
	}
	
	public void clickPM_radioBtn(){
		wait.until(ExpectedConditions.visibilityOf(PM_RadBtn));
		PM_RadBtn.click();
	}
	
	public void clickSelCharge_DrpDown(){
		//wait.until(ExpectedConditions.visibilityOf(selCharge_DrpDown));
		selCharge_DrpDown.click();
	}
	
	public void selectFromSelCharge_DrpDownOptions(String optionToSelect){
		Iterator<WebElement> itr=selCharge_DrpDownOptions.iterator();
		while(itr.hasNext()){
			WebElement wbEle=itr.next();
			if(wbEle.getText().contains(optionToSelect)){
				wbEle.click();
			}

		}
	}
	
	public void clickAddCharge(){
		wait.until(ExpectedConditions.visibilityOf(addCharge_btn));
		addCharge_btn.click();
	}
	
	public void enterNote_Edt(String textToEnter){
		wait.until(ExpectedConditions.visibilityOf(textArea));
		textArea.sendKeys(textToEnter);
	}
	
	public void clickSpace_radioBtn(){
		wait.until(ExpectedConditions.visibilityOf(space_radioBtn));
		space_radioBtn.click();
	}
	
	public void clickProperty_radioBtn(){
	wait.until(ExpectedConditions.visibilityOf(property_radioBtn));
	property_radioBtn.click();
	}
	
	public void clickSpaceNeedMaintenance_yesRadioBtn(){
		wait.until(ExpectedConditions.visibilityOf(spaceNeedMaintenance_yesRadioBtn));
		spaceNeedMaintenance_yesRadioBtn.click();
	}
	
	public void clickspaceNeedMaintenance_NoRadioBtn(){
	wait.until(ExpectedConditions.visibilityOf(spaceNeedMaintenance_NoRadioBtn));
	spaceNeedMaintenance_NoRadioBtn.click();
	}

}
