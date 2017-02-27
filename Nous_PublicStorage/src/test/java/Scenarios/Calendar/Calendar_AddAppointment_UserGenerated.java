package Scenarios.Calendar;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.CalendarPages.AddNewAppointment_Popup;
import Pages.CalendarPages.AppointmentCalendar;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;
import sun.awt.SunHints.Value;

public class Calendar_AddAppointment_UserGenerated extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"Calendar","Calendar", "Calendar_AddAppointment_UserGenerated");
	}

	@Test(dataProvider="getLoginData")
	public void Calendar_AddAppointment_UserGenerated(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Calendar").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Calendar_AddAppointment_UserGenerated","Calendar - Add Appointment User Generated");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			Generic_Class generics = new Generic_Class();
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(6000);
			
			//=================Handling Customer Facing Device========================
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			String biforstNum = Bifrostpop.getBiforstNo();
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(13000);			
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(13000);	
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));

			List<WebElement> biforstSystem = driver.findElements(
					By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for (WebElement ele : biforstSystem) {
				if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
					Reporter.log(ele.getText() + "", true);
					ele.click();
					break;
				}
			}
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
		
		//================================== PM Home Page ===============================================
			
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			logger.log(LogStatus.PASS, "Created object for the PM home page sucessfully");
			Thread.sleep(3000);
			
			pmhomepage.Clk_ViewCalendar();
			logger.log(LogStatus.INFO, "Created object for View Calendar link");
			logger.log(LogStatus.INFO, "Clicked on View Calendar link in PM home page successfully");
			Reporter.log("Clicked on View Calendar link in PM home page",true);
			Thread.sleep(5000);
			
			AppointmentCalendar appointmentcalen = new AppointmentCalendar(driver);
			Thread.sleep(3000);
			
			if(appointmentcalen.isDisplayed_Title()){
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Navigated to Appointment Calendar Page successfully");
				logger.log(LogStatus.INFO, "Navigated to Appointment Calendar Page successfully",image);
				Reporter.log("Navigated to Appointment Calendar Page successfully",true);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Appointment Calendar page is not displayed");
				logger.log(LogStatus.INFO, "Appointment Calendar page is not displayed",image);

			}
			
			Thread.sleep(3000);
			if(appointmentcalen.isDispalyed_WeekActive()){
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Week view is displayed as default successfully");
				logger.log(LogStatus.INFO, "Week view is displayed as default successfully",image);
				Reporter.log("Week view is displayed as default successfully",true);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Week view is not displayed");
				logger.log(LogStatus.INFO, "Week view is not displayed",image);

			}
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			if(appointmentcalen.isDisplayed_AddNewAppointment()){
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Add New Appointment button is displayed successfully");
				logger.log(LogStatus.INFO, "Add New Appointment button is displayed successfully",image);
				Reporter.log("Add New Appointment button is displayed successfully",true);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Add new button is not displayed");
				logger.log(LogStatus.INFO, "Add new button is not displayed",image);

			}
			
			appointmentcalen.clk_AddNewAppointment();
			logger.log(LogStatus.PASS, "Clicked on Add new Appointment button Successfully");
			Reporter.log("Navigated to Add Appointment PopUp Window successfully",true);
			Thread.sleep(5000);
			
			AddNewAppointment_Popup appPopup = new AddNewAppointment_Popup(driver);
			logger.log(LogStatus.INFO, "Created Object for Add New Appointment Popup page");
			Thread.sleep(2000);
			if(appPopup.isDisplayed_TitlePopup()){
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Add New Appointment Popup is displayed successfully");
				logger.log(LogStatus.INFO, "Add New Appointment Popup is displayed successfully",image);
				Reporter.log("Add New Appointment Popup is displayed successfully",true);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Add new button is not displayed");
				logger.log(LogStatus.INFO, "Add new button is not displayed",image);

			}
			
			appPopup.enterTxt(tabledata.get("AppointmentTitle"));
			logger.log(LogStatus.INFO, "Entered Appointment Title is: "+tabledata.get("AppointmentTitle") );
			Thread.sleep(2000);
			appPopup.clk_StartDropdown();
			logger.log(LogStatus.INFO, "Clicked on Start Dropdown");
			Thread.sleep(2000);
			appPopup.SelectDateFromCalendar(tabledata.get("StartDate"));
			logger.log(LogStatus.PASS, "Selected Start Date From Start dropdown : "+tabledata.get("StartDate"));
			Thread.sleep(8000);
			appPopup.clk_StartTime();
			Thread.sleep(8000);
			
			// *********** Implementation Changed by Sandeep ******************//
		
			//appPopup.selectStartTime(tabledata.get("StartTime"));  
			
			List<WebElement> taskList = driver.findElements(By.xpath("//div[@id='StartTime-list']/ul[@id='StartTime_listbox']/li"));
			Actions act = new Actions(driver);

			for(WebElement ele:taskList){
				act.moveToElement(ele).build().perform();
				String val=ele.getText();

				if(ele.getText().trim().equalsIgnoreCase(tabledata.get("StartTime"))){
					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "image",image);
					act.moveToElement(ele).click().build().perform();

					break;
				}

			}
			
			
			logger.log(LogStatus.INFO, "Selected Start Time is: "+tabledata.get("StartTime"));
			Thread.sleep(8000);
			appPopup.clk_EndTime();
			Thread.sleep(8000);
			//appPopup.selectEndTime(tabledata.get("EndTime")); 
			logger.log(LogStatus.INFO, "Selected End Time is: "+tabledata.get("EndTime"));
		
			 taskList = driver.findElements(By.xpath("//div[@id='EndTime-list']/ul[@id='EndTime_listbox']/li"));
			 act = new Actions(driver);

			for(WebElement ele:taskList){
				act.moveToElement(ele).build().perform();
				String val=ele.getText();

				if(ele.getText().trim().equalsIgnoreCase(tabledata.get("EndTime"))){
					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "image",image);
					act.moveToElement(ele).click().build().perform();

					break;
				}

			}
			
			
			Thread.sleep(8000);
			appPopup.clk_AddAppointmentBtn();
			logger.log(LogStatus.PASS, "Clicked on Add Appointment Button Successfully");
			Thread.sleep(5000);
			Reporter.log("Appointment Created and Navigated to Appointment Calendar Page",true);
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "Created Appointment in the Calendar",image1);
			
			
			
			//========================= Checking Appointment in Calendar =========================
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0,2000)");
			Thread.sleep(2000);
			
			//Unselect All the Appointment Types Except User Generated
			appointmentcalen.clk_MoveInChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_AuctionChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_VacateChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_preMoveinchkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_PTPChkBox();
			Thread.sleep(3000);
			
			logger.log(LogStatus.PASS, "Selected User Generated Check box in Appointment Types");
			Reporter.log("Selected user generated check box in Appointment types",true);
			Thread.sleep(3000);
			
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			//============================== Checking with Displayed value =======================================
			
			String appMeeting = driver.findElement(By.xpath("//div[starts-with(@id,'tooltip')]/div[@class='hover-events']/div[contains(text(),'"+tabledata.get("AppointmentTitle")+"')]")).getText();
			logger.log(LogStatus.INFO, "Displayed Title in the Calendar is: "+appMeeting);
			Reporter.log("Appointment Title: "+appMeeting,true);
			Thread.sleep(2000);
			if(appMeeting.trim().equalsIgnoreCase(tabledata.get("AppointmentTitle").trim())){
				Thread.sleep(2000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Appointment details Added Succesfully in Calendar");
				logger.log(LogStatus.INFO, "Appointment details Added Succesfully in Calendar",image);
				Reporter.log("Appointment details Added Succesfully in Calendar",true);
			
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Appointment Details is not displayed in the Calendar");
				logger.log(LogStatus.INFO, "Appointment Details is not displayed in the Calendar",image);

			}
		
			
			//============================== Checking with database value ========================================
			
			Thread.sleep(2000);
			
			String SqlQuery = "select top 1 title from Appointment Where 1=1 And AppointmentTypeID =4313 order by RecordDateTime desc";
			String Title = DataBase_JDBC.executeSQLQuery(SqlQuery);
			logger.log(LogStatus.INFO, "Appointment Title fetched from the Database is: "+Title);
			Thread.sleep(6000);
			
			if(Title.trim().equalsIgnoreCase(appMeeting.trim())){
				Thread.sleep(2000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Appointment details Added is Verified with Database Successfully");
				logger.log(LogStatus.INFO, "Appointment details Added is Verified with Database Successfully",image);
				Reporter.log("Appointment details Added is verified with Database Successfully",true);

			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Appointment Details is not displayed in the Calendar");
				logger.log(LogStatus.INFO, "Appointment Details is not displayed in the Calendar",image);

			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+e);
		}

	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_UserGenerated" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_UserGenerated" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_UserGenerated" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

}
