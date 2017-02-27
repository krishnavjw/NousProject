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
import Pages.AdvSearchPages.Advance_Search;
import Pages.CalendarPages.AppointmentCalendar;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.VacatePages.ScheduleVacate_ConfirmationPage;
import Pages.VacatePages.ScheduleVacate_EstimatedBalancePage;
import Pages.VacatePages.ScheduleVacate_SelectSpacePage;
import Scenarios.Browser_Factory;

public class Calendar_AddAppointment_ScheduleVacate extends Browser_Factory{
	
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"Calendar","Calendar", "Calendar_AddAppointment_ScheduleVacate");
	}

	@Test(dataProvider="getLoginData")
	public void Calendar_AddAppointment_ScheduleVacate(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Calendar").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Calendar_AddAppointment_ScheduleVacate","Calendar - Add Appointment Schedule Vacate");
			
			
			Thread.sleep(2000);
			String IpAddress=Generic_Class.getIPAddress();
			logger.log(LogStatus.INFO, "Automation scripts running machine ip address is---->:"+IpAddress);
			Thread.sleep(2000);
			String custquery="Select top 1 a.accountnumber,s.siteid,ru.rentalunitnumber,t.name as customertype,  count(distinct ru.rentalunitid) as NofCurrentunits, sum(clt.amount+clt.discountamount) as Currentbalance "+
					"from contact c with (nolock) "+
					"join customer cu with (nolock) on cu.contactid=c.contactid "+
					"join account a on a.customerid=cu.customerid "+
					"join accountorder ao with (nolock) on ao.accountid=a.accountid "+
					"join accountorderitem aoi with (nolock) on aoi.accountorderid=ao.accountorderid "+
					"join storageorderitem soi with (nolock) on soi.storageorderitemid=aoi.storageorderitemid "+ 
					"join rentalunit ru with (nolock) on ru.rentalunitid=soi.rentalunitid "+
					"join type t with (nolock) on t.typeid=cu.customertypeid "+
					"join site s with (nolock) on s.siteid=aoi.siteid "+
					"join cltransaction clt with(nolock) on clt.accountorderitemid=aoi.accountorderitemid "+
					"join glaccount gl with(nolock) on gl.glaccountid=clt.glaccountid "+
					"where soi.vacatedate is null "+
					"and soi.anticipatedvacatedate is null "+
					"group by a.accountnumber, ru.rentalunitnumber,t.name,s.siteid "+
					"having "+
					"sum(clt.amount+clt.discountamount)=0 "+
					"and count(distinct ru.rentalunitid)=1 ";

			Thread.sleep(10000);
			ArrayList<String> list=DataBase_JDBC.executeSQLQuery_List(custquery);
			Thread.sleep(20000);
			String useingingSiteId=list.get(1);
			logger.log(LogStatus.INFO, "Customer account number available site id is--->:"+useingingSiteId);
			String AccountNumber=list.get(0);	
			logger.log(LogStatus.INFO, "Customer account number is---->:"+AccountNumber);


			Thread.sleep(3000);
			String getSiteIDSetAlready="select * from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"+IpAddress+"' ";
			ArrayList<String> list1=DataBase_JDBC.executeSQLQuery_List(getSiteIDSetAlready);
			Thread.sleep(8000);
			String alreadySetSiteId=list1.get(2);

			Thread.sleep(5000);
			String updateQuery="Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"+alreadySetSiteId+"' ";
			DataBase_JDBC.executeSQLQuery(updateQuery);


			Thread.sleep(5000);
			String updateSiteID="Update siteparameter set paramvalue='"+IpAddress+"' where paramcode='IP_COMPUTER_FIRST' and siteid='"+useingingSiteId+"' ";
			DataBase_JDBC.executeSQLQuery(updateSiteID);
			Thread.sleep(5000);

			String checkSiteId="select * from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"+IpAddress+"' ";
			ArrayList<String> list2=DataBase_JDBC.executeSQLQuery_List(checkSiteId);
			String valdateSiteId=list1.get(2);
			logger.log(LogStatus.INFO, "site id of the automation scripts running machine is----->:"+valdateSiteId);


			Thread.sleep(2000);
			driver.get(driver.getCurrentUrl());
			driver.get(driver.getCurrentUrl());
			Thread.sleep(2000);	

			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			
			
			//=================Handling Customer Facing Device=============================
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
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
			
			//Clicking on Advance search Link           
			pmhomepage.clk_AdvSearchLnk();
			logger.log(LogStatus.INFO, "Clicked on Advance Search link successfully");
			Thread.sleep(5000);
			Advance_Search advSearch= new Advance_Search(driver);
			Thread.sleep(5000);

			//String SiteNumber=advSearch.getLocationNum();
			//logger.log(LogStatus.PASS, "location number is:"+SiteNumber);


		/*	Thread.sleep(1000);
			String sqlQuery= "Select Top 1 A.accountnumber "+
					"From AccountOrderItem AOI "+
					"INNER JOIN Site S ON S.SiteID = AOI.SiteID "+
					"INNER JOIN StorageOrderItem SOI ON AOI.storageOrderItemID = SOI.storageOrderItemID "+
					"INNER JOIN AccountOrder AO  ON AO.AccountOrderID = AOI.AccountOrderID "+
					"INNER JOIN Account A ON A.AccountID = AO.AccountID "+
					"join rentalunit ru on ru.rentalunitid=soi.rentalunitid "+
					"join cltransaction clt on clt.accountorderitemid=aoi.accountorderitemid "+
					"Where soi.VacateDate is null "+
					"and soi.vacatenoticedate is null "+
					"and s.sitenumber='"+SiteNumber+"' "+
					"and soi.StorageOrderItemTypeID=4300 "+
					"group by  A.AccountID,A.accountnumber, S.SiteID, SOI.vacatedate, SOI.rentalunitid, s.sitenumber, SOI.vacatenoticedate "+
					"having sum(clt.amount + clt.discountamount)=0 "+
					"order by 1 ";
					
			
			String AccountNumber=DataBase_JDBC.executeSQLQuery(sqlQuery);
			Thread.sleep(6000);
*/
			advSearch.enterAccNum(AccountNumber);
			logger.log(LogStatus.INFO, "Enter Account Number in Account field successfully and Account Number is:"+AccountNumber);
			Thread.sleep(2000);

			//Click Search button
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "");
			Thread.sleep(5000);
			
			String cus = driver.findElement(By.xpath("//h1[@class='customer-name bold']")).getText().trim();
			String[] name = cus.split("\\s+");
			String cusName = name[0];
			System.out.println(cusName);


			// Click on Space Details Tab

			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			logger.log(LogStatus.PASS, "Created object for customer dash board page");
			if(cust_accdetails.isCustdbTitleDisplayed()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "Customer Dashboard is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "Customer Dashboard is not displayed ",image);
			}
			Thread.sleep(2000);
			if(cust_accdetails.getcustCurrAccno().equals(AccountNumber)){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Account number from the dashboard matches the account number entered for search in customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "Account number from the dashboard matches the account number entered for search in customer Dashboard is displayed successfully",image);


			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Account number from the dashboard not matches the account number entered for search in customer Dashboard");
				logger.log(LogStatus.INFO, "Account number from the dashboard not matches the account number entered for search in customer Dashboard",image);


			}
			
			
			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "Clicked On Space Details Tab successfully in customer dashbaord");


			//click on Schedule vacate button

			Acc_SpaceDetailsPage spacedetails=new Acc_SpaceDetailsPage(driver);
			logger.log(LogStatus.INFO, "Created Object for Space Details page");
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			spacedetails.clickSchVacate_Btn();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on Schedule Vacate button successfully");

			Thread.sleep(3000);
			ScheduleVacate_SelectSpacePage selpage=new ScheduleVacate_SelectSpacePage(driver);
			logger.log(LogStatus.PASS, "Created object for the select space and date page");

			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			selpage.clk_VacteDateCalenderIcon();
			logger.log(LogStatus.PASS, "Clicked on calender icon in select space and date page");
			Thread.sleep(1000);
			selpage.SelectDateFromCalendar(tabledata.get("ScheduleVacateDate"));
			Thread.sleep(5000);
			String scpathvac=Generic_Class.takeScreenShotPath();
			String imagevac=logger.addScreenCapture(scpathvac);
			logger.log(LogStatus.PASS, "Selected the schedule vacate date");
			logger.log(LogStatus.INFO, "Selected the schedule vacate date",imagevac);
			Thread.sleep(2000);

			//click Continue button
			selpage.clickContinue_btn();
			logger.log(LogStatus.PASS, "Clicked on continue button successfully");
			Thread.sleep(4000);
			
			
			//click Continue button
			ScheduleVacate_EstimatedBalancePage estpage=new ScheduleVacate_EstimatedBalancePage(driver);
			logger.log(LogStatus.PASS, "Created object for the estimatedbalance due page");
			Thread.sleep(4000);

			estpage.clickContinue_btn();
			logger.log(LogStatus.PASS, "Clicked on the continue button sucessfully in estimatedbalance due page");


			Thread.sleep(5000);
			ScheduleVacate_ConfirmationPage confirm=new ScheduleVacate_ConfirmationPage(driver); 
			logger.log(LogStatus.INFO, "Created Object for Scheduled Vacate Confirmation page");
			//String customername = driver.findElement(By.xpath("//div[@id='completeVacateDialog']/div/div[1]/div[2]")).getText();
			Thread.sleep(2000);
			//Enter employee Id
			confirm.enterEmployeeID(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Employee ID Entered Successfully");
			Thread.sleep(2000);
			confirm.click_Confirm();
			logger.log(LogStatus.PASS, "Clicked on Confirm button successfully");
			Thread.sleep(4000);
			try {

				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
			} catch (Exception e) {
				e.getMessage();
			}
			Thread.sleep(8000);

			
			//==================== Clicking on Back To dashboard button ================================
			
			driver.findElement(By.xpath("//div[@id='customerDashboard']//a[contains(text(),'Back To Dashboard')]")).click();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Navigated to PM Home page");
			
			
			//=========================== View Calendar Page =============================================

			Thread.sleep(3000);
			pmhomepage.Clk_ViewCalendar();
			logger.log(LogStatus.INFO, "Created object for View Calendar link");
			Reporter.log("Clicked on view calendar link in PM home page",true);
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
			
			//Unselect All the Appointment Types Except Scheduled vacate
			appointmentcalen.clk_MoveInChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_AuctionChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_preMoveinchkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_PTPChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_CustomChKBox();
			Thread.sleep(3000);
			
			
			logger.log(LogStatus.PASS, "Selected Scheduled Vacate Check box in Appointment Types");
			Thread.sleep(3000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			
			//============================== Checking with Displayed value =======================================
			
			
			List<WebElement> vacate = driver.findElements(By.xpath("//div[starts-with(@id,'tooltip')]/div[@class='weekalldayevents']/a/div[contains(text(),'"+cusName+"')]"));
			
			Thread.sleep(2000);
			
			for(int i=0;i<vacate.size();i++){
			if(vacate.get(i).getText().trim().contains(cusName)){
				Thread.sleep(2000);
				logger.log(LogStatus.INFO, "Displayed Title in the Calendar is: "+vacate.get(i).getText());
				Reporter.log("Scheduled Vacate Title: "+vacate.get(i).getText(),true);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Scheduled Vacate Details Added Succesfully in Calendar");
				logger.log(LogStatus.INFO, "Scheduled Vacate Details Added Succesfully in Calendar",image);
				Reporter.log("Scheduled Vacate Details Added Succesfully in Calendar",true);
				break;
			
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Scheduled Vacate Details is not displayed in the Calendar");
				logger.log(LogStatus.INFO, "Scheduled Vacate Details is not displayed in the Calendar",image);

				}
			}
		
			
			//============================== Checking with database value ========================================
			
			Thread.sleep(2000);
			String SqlQuery = "select top 1 title from Appointment Where 1=1 And AppointmentTypeID =4310 order by lastupdate desc";
			String Title = DataBase_JDBC.executeSQLQuery(SqlQuery);
			logger.log(LogStatus.INFO, "Scheduled Vacate Detail fetched from the Database is: "+Title);
			Thread.sleep(6000);

			for(int i=0;i<vacate.size();i++){
			if(Title.trim().contains(vacate.get(i).getText().trim())){
				Thread.sleep(2000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Scheduled Vacate Details Added is Verified with Database Successfully");
				logger.log(LogStatus.INFO, "Scheduled Vacate Details Added is Verified with Database Successfully",image);
				Reporter.log("Scheduled Vacate Details Added is verified with Database Successfully",true);
				break;

			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Scheduled Vacate Details is not displayed in the Calendar");
				logger.log(LogStatus.INFO, "Scheduled Vacate Details is not displayed in the Calendar",image);

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
			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_ScheduleVacate" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_ScheduleVacate" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_ScheduleVacate" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

}
