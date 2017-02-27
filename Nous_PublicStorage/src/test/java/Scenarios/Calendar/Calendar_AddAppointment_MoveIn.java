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
import Pages.CalendarPages.AppointmentCalendar;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.CreateReservation;
import Pages.Walkin_Reservation_Lease.CreateReservation_PopUp;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Scenarios.Browser_Factory;

public class Calendar_AddAppointment_MoveIn extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"Calendar","Calendar", "Calendar_AddAppointment_MoveIn");
	}

	@Test(dataProvider="getLoginData")
	public void Calendar_AddAppointment_MoveIn(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Calendar").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Calendar_AddAppointment_MoveIn","Calendar - Add Appointment MoveIn");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			Generic_Class generics = new Generic_Class();
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			
			
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

			pmhomepage.clk_findAndLeaseSpace();
			logger.log(LogStatus.PASS,"Clicked on Find And Lease A Space button");
			Reporter.log("Clicked on Find And Lease A Space button",true);
			Thread.sleep(8000);
			String WalkInPageURL = driver.getCurrentUrl();

			if(WalkInPageURL.contains("Walkin"))
			{
				Reporter.log("User is navigated to Walkin page",true);
				logger.log(LogStatus.PASS,"User is navigated to Walkin page");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Walkin Page is not displayed");
				logger.log(LogStatus.INFO, "Walkin Page is not displayed",image);
				
			}

			StandardStoragePage StandardStoragePage = new StandardStoragePage(driver);
			logger.log(LogStatus.INFO, "Created Object for Standard Storage Page");

			if(StandardStoragePage.verify_StdStorageHdr())
			{

				Reporter.log("Standard Storage Tab is selected by default when walkin's page is launched",true);
				logger.log(LogStatus.PASS,"Standard Storage Tab is selected by default when walkin's page is launched");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Standard Storage Tab is not selected by default when walkin's page is launched");
				logger.log(LogStatus.INFO, "Standard Storage Tab is not selected by default when walkin's page is launched",image);
				
			}

			if(StandardStoragePage.isSelected_yesradiobutton())
			{

				Reporter.log("New Customer Radio button is selected by default when walkin's page is launched",true);
				logger.log(LogStatus.PASS,"New Customer Radio button is selected by default when walkin's page is launched");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "New Customer Radio button is not selected by default when walkin's page is launched");
				logger.log(LogStatus.INFO, "New Customer Radio button is not selected by default when walkin's page is launched",image);
				
			}

			StandardStoragePage.ClickOnAvlSpaces();
			logger.log(LogStatus.INFO,"Clicked on available spaces checkbox in StandardStorage");
			Reporter.log("Clicked on available spaces",true);
			StandardStoragePage.click_Search();
			logger.log(LogStatus.INFO,"Clicked on Search button");
			Reporter.log("Clicked on Search button",true);
			Thread.sleep(10000);

			//=====================Fetching space number and based on that clicking the radio button========================
			
			Thread.sleep(10000);
			List <WebElement> norows=driver.findElements(By.xpath("//div[@class='k-grid-content ps-container ps-active-y']//table/tbody/tr"));
			String space=null;
			logger.log(LogStatus.INFO,"Total number of avaiable sizes count is:"+norows.size());
			if(norows.size()>0){
				Thread.sleep(5000);

				space=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container ps-active-y']//table/tbody/tr[1]/td[4]")).getText();

				Reporter.log("space number is:"+space,true);
			}else{

				logger.log(LogStatus.FAIL, "Application is not populating any data/space details with selected size dimension");


			}

			Thread.sleep(2000);
			WebElement RdBtn_Space = driver.findElement(By.xpath("//td[@class='grid-cell-space'][text()='"+space+"']/../td/input[@name='selectedIds']"));
			logger.log(LogStatus.PASS, "check the radio button based on the space and click on the  reservation button");
			jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space); 
			jse.executeScript("arguments[0].click();", RdBtn_Space);

			logger.log(LogStatus.PASS,"Clicked on check box of a space in this location: " + space);
			Reporter.log("Clicked on check box of a space in this location: " + space,true);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(4000);
			
			SpaceDashboard_ThisLoc thisloc =new SpaceDashboard_ThisLoc(driver);
			logger.log(LogStatus.INFO, "Created Object for Space Dashboard This location");
			
			thisloc.click_Reserve();
			logger.log(LogStatus.INFO, "Clicked On reserve Button Successfully");
			Thread.sleep(10000);
			CreateReservation CreateReservation = new CreateReservation(driver);
			Thread.sleep(5000);

			if(CreateReservation.verify_createReservationHdrIsDisplayed())
			{
				Reporter.log("Create Reservation Screen is displayed",true);
				logger.log(LogStatus.PASS,"Create Reservation Screen is displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Create Reservation page is not displayed");
				logger.log(LogStatus.INFO, "Create Reservation page is not displayed",image);
				
			}
			
			
			driver.findElement(By.xpath("//div[@id='expectedMoveInDate']//span[text()='SELECT']")).click();
			Thread.sleep(4000);
			CreateReservation.SelectDateFromCalendar(tabledata.get("MoveInDate"));
			Thread.sleep(4000);
			Reporter.log("MoveIn date is selected in calendar", true);
			String selectedDate = driver.findElement(By.xpath("//div[@id='expectedMoveInDate']/span[@class='dual-datepicker-control']")).getText();
			Reporter.log("Selected Move in date"+selectedDate, true);
			Thread.sleep(2000);


			driver.findElement(By.xpath("//div[@id='expectedMoveInTime']/../span/span[contains(text(),'SELECT')]")).click();
			Thread.sleep(2000);
			
			// *********** Implementation Changed by Sandeep ******************//
			
			//CreateReservation.selectTime(tabledata.get("MoveInTime"));
			
			List<WebElement> taskList = driver.findElements(By.xpath("//ul[@id='expectedMoveInTime_listbox']/li"));
			Actions act = new Actions(driver);

			for(WebElement ele:taskList){
				act.moveToElement(ele).build().perform();
				String val=ele.getText();

				if(ele.getText().trim().equalsIgnoreCase(tabledata.get("MoveInTime"))){
					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "image",image);
					act.moveToElement(ele).click().build().perform();

					break;
				}

			}
			logger.log(LogStatus.INFO, "Selected Time in Move in Dropdown");
			
			Thread.sleep(5000);
			String FirstName = "Nous" + generics.get_RandmString();
			Thread.sleep(3000);
			CreateReservation.enter_FirstName(FirstName);
			Thread.sleep(2000);
			String FN= CreateReservation.get_ExtCustName();
			logger.log(LogStatus.PASS, "Entered first name is: "+FN);
			Thread.sleep(2000);
			String LastName = "Automation" + generics.get_RandmString();
			Thread.sleep(2000);
			CreateReservation.enter_LastName(LastName);
			Thread.sleep(2000);
			String LN= CreateReservation.getLastName();
			logger.log(LogStatus.PASS, "Entered last name is: "+LN);
			Thread.sleep(2000);
			CreateReservation.sel_DropDownValFromPhNo(tabledata.get("Phone"));
			logger.log(LogStatus.PASS, "Phone type is selected in CreateReservation Page is: "+tabledata.get("Phone"));
			Reporter.log("Phone type is selected in CreateReservation Page", true);
			Thread.sleep(2000);
			CreateReservation.enter_PhoneAreaCode(tabledata.get("PhoneAreacode"));
			logger.log(LogStatus.PASS, "Entered phone area code is:" +tabledata.get("PhoneAreacode"));
			Thread.sleep(2000);
			CreateReservation.enter_PhoneExchnge(tabledata.get("PhoneExchnge"));
			logger.log(LogStatus.PASS, "Entered phone exchange is:" +tabledata.get("PhoneExchnge"));
			Thread.sleep(2000);
			CreateReservation.enter_PhoneLineNumber(tabledata.get("PhoneLneNo"));
			logger.log(LogStatus.PASS, "Entered line number is: "+tabledata.get("PhoneLneNo"));
			Thread.sleep(2000);
			CreateReservation.enter_EmailAddr(tabledata.get("EmailAddress"));
			logger.log(LogStatus.PASS, "Entered email address is:" +tabledata.get("EmailAddress"));
			Thread.sleep(2000);
			// generics.Page_ScrollDown();
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			//String data = (LN, FN);

			// Click on Create Reservation button
			CreateReservation.clk_CreateReservationButton();
			Thread.sleep(3000);
			logger.log(LogStatus.PASS, "Clicked on Reservation button successfully ");
			
			CreateReservation_PopUp rserservationpopup = new CreateReservation_PopUp(driver);
			if (rserservationpopup.get_CreateResvPopUpHeader().equalsIgnoreCase("Create Reservation")) {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Create Reservation modal window displayed successfully: "
						+ rserservationpopup.get_CreateResvPopUpHeader());
				logger.log(LogStatus.INFO, "Create Reservation modal window displayed successfully", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Create Reservation modal window not displayed");
				logger.log(LogStatus.INFO, "Create Reservation modal window not displayed", image);
			}
			
			rserservationpopup.enter_EmpID(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "Employee Number entered successfully ");
			Thread.sleep(4000);

			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button Successfully ");
			Thread.sleep(8000);
			
			
			//===========================View Calendar Page =============================================

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
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0,2000)");
			Thread.sleep(2000);
			
			//Unselect All the Appointment Types Except Movein
			appointmentcalen.clk_AuctionChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_VacateChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_preMoveinchkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_PTPChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_CustomChKBox();
			Thread.sleep(3000);
			
			logger.log(LogStatus.PASS, "Selected Movein Check box in Appointment Types");
			Thread.sleep(2000);
			
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			
			
			//============================== Checking with Displayed value =======================================
			
			List<WebElement> movein = driver.findElements(By.xpath("//div[starts-with(@id,'tooltip')]/div[@class='hover-events']/div[contains(text(),'"+FN+"')]"));
			
			Thread.sleep(2000);
			
			for(int i=0;i<movein.size();i++){
			if(movein.get(i).getText().trim().contains(FN)){
				Thread.sleep(2000);
				logger.log(LogStatus.INFO, "Displayed Title in the Calendar is: "+movein.get(i).getText());
				Reporter.log("Movein Title: "+movein.get(i).getText(),true);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Movein Details Added Succesfully in Calendar");
				logger.log(LogStatus.INFO, "Movein Details Added Succesfully in Calendar",image);
				Reporter.log("Movein Details Added Succesfully in Calendar",true);
				break;
			
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Movein Details is not displayed in the Calendar");
				logger.log(LogStatus.INFO, "Movein Details is not displayed in the Calendar",image);

				}
			}
		
			
			//============================== Checking with database value ========================================
			
			Thread.sleep(2000);
			String SqlQuery = "select top 1 title from Appointment Where 1=1 And AppointmentTypeID =4308 order by RecordDateTime desc";
			String Title = DataBase_JDBC.executeSQLQuery(SqlQuery);
			logger.log(LogStatus.INFO, "Movein Detail fetched from the Database is: "+Title);
			Thread.sleep(6000);

			for(int i=0;i<movein.size();i++){
			if(Title.trim().contains(movein.get(i).getText().trim())){
				Thread.sleep(2000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Movein Details Added is Verified with Database Successfully");
				logger.log(LogStatus.INFO, "Movein Details Added is Verified with Database Successfully",image);
				Reporter.log("Movein Details Added is verified with Database Successfully",true);
				break;

			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Movein Details is not displayed in the Calendar");
				logger.log(LogStatus.INFO, "Movein Details is not displayed in the Calendar",image);

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
			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_MoveIn" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_MoveIn" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_MoveIn" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

}
