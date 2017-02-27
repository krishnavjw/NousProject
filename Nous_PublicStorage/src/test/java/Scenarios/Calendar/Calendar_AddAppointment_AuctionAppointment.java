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
import Pages.CalendarPages.AppointmentCalendar;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Calendar_AddAppointment_AuctionAppointment extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"Calendar","Calendar", "Calendar_AddAppointment_AuctionAppointment");
	}

	@Test(dataProvider="getLoginData")
	public void Calendar_AddAppointment_AuctionAppointment(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Calendar").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Calendar_AddAppointment_AuctionAppointment","Calendar - Add Appointment Auction Appointment");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			
			
			//=================Handling Customer Facing Device=============================
			
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
			Reporter.log("Clicked on View Calendar link in PM home page",true);
			Thread.sleep(9000);
			
			AppointmentCalendar appointmentcalen = new AppointmentCalendar(driver);
			logger.log(LogStatus.INFO, "Created Object for Appointment Calendar");
			Thread.sleep(3000);
			
			if(appointmentcalen.isDisplayed_Title()){
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Navigated to Add Appointment Page successfully");
				logger.log(LogStatus.INFO, "Navigated to Add Appointment Page successfully",image);
				Reporter.log("Navigated to Add Appointment Page successfully",true);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Add Appointment page is not displayed");
				logger.log(LogStatus.INFO, "Add Appointment page is not displayed",image);

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
			
			//Unselect All the Appointment Types Except Auction
			appointmentcalen.clk_MoveInChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_VacateChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_preMoveinchkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_PTPChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_CustomChKBox();
			Thread.sleep(3000);
			
			logger.log(LogStatus.PASS, "Selected Auction Check box in Appointment Types");
			Reporter.log("Selected Auction Check box in Appointment Types",true);
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "Selected Auction Check box in Calendar Page",image1);
			Thread.sleep(2000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
	
			
			//============================== Checking with Displayed value =======================================
			
			List<WebElement> auction = driver.findElements(By.xpath("//div[starts-with(@id,'tooltip')]/div[@class='hover-events']/div[contains(text(),'Auction')]"));
			
			Thread.sleep(2000);
			
			for(int i=0;i<auction.size();i++){
			if(auction.get(i).getText().trim().equalsIgnoreCase("Auction")){
				Thread.sleep(2000);
				logger.log(LogStatus.INFO, "Displayed Title in the Calendar is: "+auction.get(i).getText());
				Reporter.log("Auction Title: "+auction.get(i).getText(),true);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Auction details Added Succesfully in Calendar");
				logger.log(LogStatus.INFO, "Auction details Added Succesfully in Calendar",image);
				Reporter.log("Auction details Added Succesfully in Calendar",true);
				break;
			
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Auction Details is not displayed in the Calendar");
				logger.log(LogStatus.INFO, "Auction Details is not displayed in the Calendar",image);

				}
			}
		
			
			//============================== Checking with database value ========================================
			
			Thread.sleep(2000);
			
			String SqlQuery = "select top 1 title from Appointment Where 1=1 And AppointmentTypeID =4309 order by RecordDateTime desc";
			String Title = DataBase_JDBC.executeSQLQuery(SqlQuery);
			logger.log(LogStatus.INFO, "Auction Detail fetched from the Database is: "+Title);
			Thread.sleep(6000);

			for(int i=0;i<auction.size();i++){
			if(Title.trim().equalsIgnoreCase(auction.get(i).getText().trim())){
				Thread.sleep(2000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Auction Added is Verified with Database Successfully");
				logger.log(LogStatus.INFO, "Auction Added is Verified with Database Successfully",image);
				Reporter.log("Auction Added is verified with Database Successfully",true);
				break;

			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Auction is not displayed in the Calendar");
				logger.log(LogStatus.INFO, "Auction is not displayed in the Calendar",image);

				}
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
			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_AuctionAppointment" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_AuctionAppointment" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_AuctionAppointment" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
	
	
}
