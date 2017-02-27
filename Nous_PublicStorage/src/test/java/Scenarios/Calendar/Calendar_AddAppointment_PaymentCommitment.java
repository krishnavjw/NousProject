package Scenarios.Calendar;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
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
import Pages.CustDashboardPages.DTMCommitmentCalls;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Calendar_AddAppointment_PaymentCommitment extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"Calendar","Calendar", "Calendar_AddAppointment_PaymentCommitment");
	}

	@Test(dataProvider="getLoginData")
	public void Calendar_AddAppointment_PaymentCommitment(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Calendar").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Calendar_AddAppointment_PaymentCommitment","Calendar - Add Appointment Payment Commitment");
			
			String IpAddress=Generic_Class.getIPAddress();
			logger.log(LogStatus.INFO, "Autoamtion scripts running machine ip address is---->:"+IpAddress);
			Thread.sleep(2000);
			
			String siteid=	"Select distinct top 2  s.siteid, UD.rentalunitnumber as SpaceNumber, UD.lastname, UD.firstname, t.name as customertype,UD.accountnumber, UD.accountOrderItemID, "+
							"UD.paidthrudate,sum(Clt.amount+Clt.discountamount) as Currentbalance, t1.name as calltype,pc.ExpirationDate,pc.ScheduledCallDateTime "+
							"FROM "+
							"vw_unitdetails UD with(nolock) "+
							"join Type T with(nolock) on T.TypeID = UD.CustomerTypeID "+
							"join site s with(nolock) on s.siteid=UD.siteid "+
							"join cltransaction clt with(nolock) on clt.accountorderitemid=UD.accountorderitemid "+
							"left join phonecall pc with(nolock) on pc.ownertableitemid=UD.storageorderitemid and pc.iscallcompleted is null and pc.expirationdate>getutcdate() "+
							"left join type t1 with(nolock) on t1.typeid=pc.calltypeid "+
							"where 1=1 "+
							"and UD.vacatedate is null "+
							"and t1.name = 'Get PTP' "+
							"group by "+
							"UD.lastname, UD.firstname, UD.accountnumber, UD.rentalunitnumber,UD.paidthrudate,t.name,t1.name, UD.accountorderItemID,pc.ExpirationDate,pc.ScheduledCallDateTime,s.siteid "+
							"having sum(Clt.amount +Clt.discountamount) >0 ";
			
			Thread.sleep(10000);
			String SiteID=DataBase_JDBC.executeSQLQuery(siteid);
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Site id for Commitment Calls : "+SiteID);
			Thread.sleep(3000);
			String getSiteIdOldone = "select siteid from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"+IpAddress+"' ";
			String SiteIDAlreadyUsing=DataBase_JDBC.executeSQLQuery(getSiteIdOldone);
			Thread.sleep(6000);
			
			Thread.sleep(5000);
			String updateQuery="Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"+SiteIDAlreadyUsing+"' ";
			DataBase_JDBC.executeSQLQuery(updateQuery);


			Thread.sleep(5000);
			String updateSiteID="Update siteparameter set paramvalue='"+IpAddress+"' where paramcode='IP_COMPUTER_FIRST' and siteid='"+SiteID+"' ";
			DataBase_JDBC.executeSQLQuery(updateSiteID);
			Thread.sleep(5000);

			String checkSiteId="select siteid from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"+IpAddress+"' ";
			String valdateSiteId=DataBase_JDBC.executeSQLQuery(checkSiteId);
			logger.log(LogStatus.INFO, "Site id of the automation scripts running machine is----->:"+valdateSiteId);


			Thread.sleep(2000);
			driver.get(driver.getCurrentUrl());
			driver.get(driver.getCurrentUrl());
			Thread.sleep(2000);	

			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(8000);
			

			
			
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
			Thread.sleep(15000);
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_DEV"));

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
			Thread.sleep(15000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(15000);
			
			
		//================================== PM Home Page ==================================================
			
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			logger.log(LogStatus.PASS, "Created object for the PM home page sucessfully");
			Thread.sleep(3000);
			
			Actions actions = new Actions(driver);
			WebElement mainMenu = driver.findElement(By.xpath("//div[@id='main-content']//div[@class='collection-list']//table[@class='k-selectable']//a[@alt='7']"));
			actions.moveToElement(mainMenu);
			
			JavascriptExecutor je = (JavascriptExecutor) driver;
			WebElement element =driver.findElement(By.xpath("//div[@id='main-content']//div[@class='collection-list']//table[@class='k-selectable']//a[@alt='10']"));
			je.executeScript("arguments[0].scrollIntoView(true);",element);
			element.click();
			logger.log(LogStatus.INFO, "Clicked on Commitment Call");
			Thread.sleep(30000);
			String cusName = driver.findElement(By.xpath("//table[@class='k-selectable']//tr[1]/td[6]")).getText();
			Reporter.log("Customer Name is : "+cusName,true);
			driver.findElement(By.xpath("//table[@class='k-selectable']//tr[1]/td[6]")).click();
			Thread.sleep(8000);
			logger.log(LogStatus.INFO, "Clicked on Commitment Call Link Successfully");
			
			
			DTMCommitmentCalls dtmcalls = new DTMCommitmentCalls(driver);
			logger.log(LogStatus.INFO, "Created Object for DTM Commitment Calls Page");
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "Navigated to DTM Commitment Calls Page successfully");
			logger.log(LogStatus.INFO, "Navigated to DTM Commitment Calls Page successfully",image1);
			Thread.sleep(2000);
			dtmcalls.sel_PaymentDropdown();
			logger.log(LogStatus.INFO, "Clicked on Payment Dropdown");
			Thread.sleep(2000);
			dtmcalls.SelectDateFromCalendar(tabledata.get("StartDate"));
			Thread.sleep(3000);
			dtmcalls.clk_PhonechckBox();
			logger.log(LogStatus.INFO, "Clicked on Phone number check box");
			Thread.sleep(3000);
			dtmcalls.sel_DropDownValFromReason(tabledata.get("Reason"));
			logger.log(LogStatus.INFO, "Selected Reason from the Reason Dropdown is: "+tabledata.get("Reason"));
			//dtmcalls.result_Drop();
			//Thread.sleep(2000);
			//driver.findElement(By.xpath("//div[@class='k-animation-container']/div//li[2]")).click();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO,"Selected Reason in the Call Result Dropdown");
			
			dtmcalls.enter_Comment(tabledata.get("Notes"));
			Thread.sleep(3000);
			String scpathpay=Generic_Class.takeScreenShotPath();
			String imagepay=logger.addScreenCapture(scpathpay);
			logger.log(LogStatus.PASS, "Entered Notes in Comments text box");
			logger.log(LogStatus.INFO, "Entered Notes in Comments text box",imagepay);
			Thread.sleep(2000);
			dtmcalls.clk_SubmitBtn();
			Thread.sleep(3000);
			logger.log(LogStatus.PASS, "Clicked on Submit Button Successfully");
			dtmcalls.enterEmployeeNum(tabledata.get("UserName"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Employee number is: "+tabledata.get("UserName"));
			dtmcalls.clk_SaveAndClose();
			logger.log(LogStatus.PASS, "Clicked on Save And Close button Successfully");
			Thread.sleep(10000);
			
			
			//====================Clicking on Back To dashboard button in Customer dash board================
			
			driver.findElement(By.xpath("//a[contains(text(),'Back To Dashboard')]")).click();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "Clicked on Back to Dash board button Successfully");
			logger.log(LogStatus.INFO, "Navigated to PM Home page");
			
		
			//============================== Clicking on Calendar View =======================================
			
			pmhomepage.Clk_ViewCalendar();
			logger.log(LogStatus.INFO, "Created object for View Calendar link");
			Reporter.log("Clicked on view calendar link in PM home page",true);
			Thread.sleep(9000);
			
			AppointmentCalendar appointmentcalen = new AppointmentCalendar(driver);
			logger.log(LogStatus.INFO, "Created Object for Appointment Calendar");
			Thread.sleep(30000);
			
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
			Thread.sleep(12000);
			
			//Unselect All the Appointment Types Except Payment Commitment
			appointmentcalen.clk_MoveInChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_AuctionChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_VacateChkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_preMoveinchkBox();
			Thread.sleep(3000);
			appointmentcalen.clk_CustomChKBox();
			Thread.sleep(3000);
			
			logger.log(LogStatus.PASS, "Selected Payment Commitment Check box in Appointment Types");
			Thread.sleep(2000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			
			
			//============================== Checking with Displayed value =======================================
			
			
			List<WebElement> payment = driver.findElements(By.xpath("//div[starts-with(@id,'tooltip')]/div[@class='weekalldayevents']/a/div[contains(text(),'"+cusName+"')]"));
			
			Thread.sleep(2000);
			
			for(int i=0;i<payment.size();i++){
			if(payment.get(i).getText().trim().contains(cusName.trim())){
				Thread.sleep(2000);
				logger.log(LogStatus.INFO, "Displayed Title in the Calendar is: "+payment.get(i).getText());
				Reporter.log("Payment Commitment Title: "+payment.get(i).getText(),true);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Payment Commitment Details Added Succesfully in Calendar");
				logger.log(LogStatus.INFO, "Payment Commitment Details Added Succesfully in Calendar",image);
				Reporter.log("Payment Commitment Details Added Succesfully in Calendar",true);
				break;
			
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Payment Commitment Details is not displayed in the Calendar");
				logger.log(LogStatus.INFO, "Payment Commitment Details is not displayed in the Calendar",image);

				}
			}
		
			
			//============================== Checking with database value ========================================
			
			Thread.sleep(2000);
			String SqlQuery = "select top 1 title from Appointment Where 1=1 And AppointmentTypeID =4312 order by lastupdate desc";
			String Title = DataBase_JDBC.executeSQLQuery(SqlQuery);
			logger.log(LogStatus.INFO, "Payment Commitment Detail fetched from the Database is: "+Title);
			Thread.sleep(6000);

			for(int i=0;i<payment.size();i++){
			if(Title.trim().contains(payment.get(i).getText().trim())){
				Thread.sleep(2000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Payment Commitment Details Added is Verified with Database Successfully");
				logger.log(LogStatus.INFO, "Payment Commitment Details Added is Verified with Database Successfully",image);
				Reporter.log("Payment Commitment Details Added is verified with Database Successfully",true);
				break;

			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Payment Commitment Details is not displayed in the Calendar");
				logger.log(LogStatus.INFO, "Payment Commitment Details is not displayed in the Calendar",image);

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
			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_PaymentCommitment" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_PaymentCommitment" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Calendar","Calendar_AddAppointment_PaymentCommitment" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

	

	
	

}
