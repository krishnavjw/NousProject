package Scenarios.WalkinReservationLease;

import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.CreateReservation;
import Pages.Walkin_Reservation_Lease.CreateReservation_PopUp;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_OtherLocations;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.Unreservation_popup;
import Scenarios.Browser_Factory;

public class Reservation_Movein_FollowupCall extends Browser_Factory {

	String locationnum = null;
	public ExtentTest logger;
	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "Pass";
	String scpath;
	String image;
	
	@AfterMethod
	public void afterMethod() {

		System.out.println(" In After Method");
		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "Reservation_Movein_FollowupCall", "Status",
					"Pass");

		} else if (resultFlag.equals("fail")) {
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Leasing for Customer verification", image);
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "Reservation_Movein_FollowupCall", "Status",
					"Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "Reservation_Movein_FollowupCall", "Status",
					"Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

	@DataProvider
	public Object[][] getLoginData() {
		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"Reservation_Movein_FollowupCall");
	}

	@Test(dataProvider = "getLoginData")
	public void Reservation_Movein_FollowupCall(Hashtable<String, String> tabledata) throws Exception {
		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}
		// Reporter.log("end Completed", true);
		Thread.sleep(5000);
		try {
			logger = extent.startTest("Reservation_Movein_FollowupCall", "Reservation_Movein_FollowupCall");

			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);

			Dashboard_BifrostHostPopUp Bifrostpop1 = new Dashboard_BifrostHostPopUp(driver);
			
			try {
				Bifrostpop1.clickContiDevice();
			} catch (Exception e) {
				System.out.println("Pop up is not avalable, so continuing!!");
			}

			// -------------------------------Clicking on Lease and space
			// button-----------------------------
			Thread.sleep(2000);
			PM_Homepage homepage = new PM_Homepage(driver);
			logger.log(LogStatus.INFO, "PM home page displayed successfully");

			homepage.clk_findAndLeaseSpace();
			logger.log(LogStatus.PASS, "Clicked on Find And Lease A Space");
			Reporter.log("Clicked on Find And Lease A Space", true);
			Thread.sleep(5000);

			// -------Selecting space and click on search button----------
			StandardStoragePage stdstoragepage = new StandardStoragePage(driver);
			try {
				stdstoragepage.Clk_OnAvlSpace();
			} catch (Exception e) {
				Reporter.log("None of the spaces are available");
			}
			logger.log(LogStatus.PASS, "click on multiple spaces ");
			Thread.sleep(2000);
			stdstoragepage.click_Search();
		
			Thread.sleep(10000);

			// -------Selecting space and clicking on OffReserveunit------------------
			

			SpaceDashboard_ThisLoc this_location = new SpaceDashboard_ThisLoc(driver);
			SpaceDashboard_OtherLocations other_loc = new SpaceDashboard_OtherLocations(driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			this_location.click_otherLocations();
			Thread.sleep(10000);
			
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)", "");
			Thread.sleep(2000);

			if (other_loc.OffSiteReservation_locnumber_count() == true) {
				locationnum = other_loc.OffSiteReservation_locnumber_gettext();
				jse.executeScript("arguments[0].click();", other_loc.RdBtn_Space_otherloc());
				Thread.sleep(2000);
				other_loc.clk_OffSiteSubmitBtn();
				Thread.sleep(5000);
				logger.log(LogStatus.PASS,
						"checked the radio button based on the location and click on the  reservation button");
			} else {
				resultFlag = "fail";
				logger.log(LogStatus.FAIL, "No space is avialable to select");

			}

			// ----- Fill data in Create Reservation Page and Click on Create
			// Reservation button------
			CreateReservation res = new CreateReservation(driver);
			res.clk_movindate();
			Thread.sleep(2000);
			res.SelectDateFromCalendar(tabledata.get("MoveInDate"));
			Reporter.log("MoveIn date is selected in calendar", true);
			Thread.sleep(2000);
			res.enter_FirstName(tabledata.get("FirstName"));
			

			res.enter_LastName(tabledata.get("LastName"));
			
			Thread.sleep(1000);
			res.sel_DropDownValFromPhNo(tabledata.get("Phone"));
			logger.log(LogStatus.PASS, "Phone type is selected in CreateReservation Page");
			Reporter.log("Phone type is selected in CreateReservation Page", true);
			Thread.sleep(2000);
			res.enter_PhoneAreaCode(tabledata.get("PhoneAreacode"));
			
			Thread.sleep(1000);
			res.enter_PhoneExchnge(tabledata.get("PhoneExchnge"));
			
			Thread.sleep(1000);
			res.enter_PhoneLineNumber(tabledata.get("PhoneLneNo"));
			
			Thread.sleep(2000);
			res.enter_EmailAddr("psitqa@gmail.com");
			
			Thread.sleep(2000);
			
			scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Reservation is done successfully");
			logger.log(LogStatus.INFO, "Reservation is done successfully", image);
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			res.clk_CreateReservationButton();
			Thread.sleep(3000);
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");

			// ------ Enter Note and Employeeid in Reservation Pop up----

			CreateReservation_PopUp rserservationpopup = new CreateReservation_PopUp(driver);
			rserservationpopup.enter_EmpID(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "employee no entered successfully ");
			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			Thread.sleep(4000);

			// --- DataBase Configuration to create Reservation Call 1-----

			String siteid_loc = "select SiteID from site where sitenumber= '" + locationnum + "'";
			String siteid_loc_data = DataBase_JDBC.executeSQLQuery(siteid_loc);
			System.out.println(" th siteid is-----" + siteid_loc_data);
			String ipadd = Generic_Class.getIPAddress();
			Thread.sleep(1000);
			

			String siteid_ipsdd = "select SiteID from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"
					+ ipadd + "'";
			String siteid_ipsdd_data = DataBase_JDBC.executeSQLQuery(siteid_ipsdd);

			System.out.println(" the siteid_ipsdd_data is-----: " + siteid_ipsdd_data);

			String allocateemtyip = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"
					+ siteid_ipsdd_data + "'";
			String allocateemtyipsetted = DataBase_JDBC.executeSQLQuery(allocateemtyip);

			System.out.println("allocateemtyipsetted is----: " + allocateemtyipsetted);

			String allocateip_to_newid = "Update siteparameter set paramvalue='" + ipadd
					+ "' where paramcode='IP_COMPUTER_FIRST' and siteid='" + siteid_loc_data + "'";
			String allocateip_to_newid_setted = DataBase_JDBC.executeSQLQuery(allocateip_to_newid);

			System.out.println("allocateip_to_newid_setted is----: " + allocateip_to_newid_setted);

			String reservationid = "select Top 1 ReservationID  from reservation where siteid='" + siteid_loc_data
					+ "' and FirstName='" + tabledata.get("FirstName") + "' and LastName='" + tabledata.get("LastName")
					+ "' and ReservationStatusTypeID='128'  Order by  LastUpdate DESC";
			String reser_id = DataBase_JDBC.executeSQLQuery(reservationid);

			System.out.println(" th reser_id is-----: " + reser_id);

			String phonecallid_data = "select top 1 pc.phonecallid  from phonecall pc join type t on t.typeid=pc.calltypeid "
					+ "where pc.OwnerTableItemID ='" + reser_id
					+ "' and t.name='Confirmation Call'  Order by  PhonecallID desc";

			String phonecellid = DataBase_JDBC.executeSQLQuery(phonecallid_data);

			System.out.println(" th phonecellid is----: " + phonecellid);

			String change_res_curdate = "update phonecall set Scheduledcalldatetime=getutcdate() where phonecallid='"
					+ phonecellid + "'";
			String change_res_curdate_setted = DataBase_JDBC.executeSQLQuery(change_res_curdate);

			System.out.println("change_res_curdate_setted is----: " + change_res_curdate_setted);

			Thread.sleep(10000);

			// ----Logout and login back to get confirmation Call---
			Actions act = new Actions(driver);

			WebElement user = driver.findElement(By.xpath(("//div[@id='usernav']")));
			WebElement logoff1 = driver.findElement(By.xpath("//a[contains(text(),'Log Off')]"));

			act.moveToElement(user).build().perform();
			jse.executeScript("arguments[0].click();", logoff1);
			Thread.sleep(5000);

			WebElement logoff2 = driver.findElement(
					By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'Log Off')]"));
			jse.executeScript("arguments[0].click();", logoff2);
			Thread.sleep(8000);

			try{
				login.login(tabledata.get("UserName"), tabledata.get("Password"));
				logger.log(LogStatus.INFO, " Login successfully again");
				Thread.sleep(5000);
				
			}catch(Exception e){
				logger.log(LogStatus.INFO, "Displayed PM Home page with Follow Up Call display" );
			} 

			

			// ----- Clicking on Resrvation Call 1 -----

			jse.executeScript("arguments[0].scrollIntoView();",
					driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")));
			Thread.sleep(5000);
			
			WebElement rescall = driver
					.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]"));

			if (rescall.isDisplayed()) {
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Confirmation Call 1 is displayed");
				logger.log(LogStatus.INFO, "Confirmation Call 1 is displayed", image);
			} else {
				resultFlag = "fail";
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Confirmation Call 1 not is displayed");
				logger.log(LogStatus.INFO, "Confirmation Call 1 not is displayed", image);
			}

			jse.executeScript("arguments[0].click();", rescall);

			Reporter.log("Clicked on Confirmation call 1 & proceeding further", true);
			Thread.sleep(10000);

			// ---Clicking on UnConfirmation and Enter data in Unreservation Pop------------------------------
	
			String res_num = res.get_reservationnumber();
			Thread.sleep(3000);
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			res.clk_Unreservebutton();
			Thread.sleep(3000);

			Unreservation_popup unreservation_popup = new Unreservation_popup(driver);
			unreservation_popup.clk_Not_Confirmed_radiobtn();

			
			Thread.sleep(3000);

			driver.findElement(
					By.xpath("//div[@class='call-info__call-type__dropdown-container floatleft vertical-center']"))
					.click();
			Thread.sleep(3000);

			driver.findElement(By
					.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']//ul/li[contains(text(),'FollowUp Call')]"))
					.click();
			Thread.sleep(3000);

			driver.findElement(By.xpath("(//a[contains(text(),'Edit')])[2]")).click();
			Thread.sleep(3000);

			driver.findElement(By.xpath("//div[@id='callDatePicker']")).click();
			Thread.sleep(4000);

			res.SelectDateFromCalendar_onemonth("1");
			Thread.sleep(4000);

			unreservation_popup.enter_pendingReservationNotes("not reachable");
			Thread.sleep(1000);

			unreservation_popup.enter_employeeNumber(tabledata.get("UserName"));
			Thread.sleep(1000);

			unreservation_popup.clk_Save_Continue();
			logger.log(LogStatus.INFO, "Clicked on save and continue button successfully");
			Thread.sleep(5000);

			// ------ Database Configuration for Reservation Call 2---------

			String phonecallid_data1 = "select top 1 pc.phonecallid  from phonecall pc join type t on t.typeid=pc.calltypeid "
					+ "where pc.OwnerTableItemID ='" + reser_id
					+ "' and t.name='Follow-Up Call'  Order by  PhonecallID desc";

			String phonecellid1 = DataBase_JDBC.executeSQLQuery(phonecallid_data1);
			Thread.sleep(10000);

			String change_res_curdate1 = "update phonecall set Scheduledcalldatetime=getutcdate() where phonecallid='"
					+ phonecellid1 + "'";
			DataBase_JDBC.executeSQLQuery(change_res_curdate1);
			
			if (phonecellid1.isEmpty()) {
				
				resultFlag = "fail";
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Followup call is not inserted in phone call table");
				logger.log(LogStatus.INFO, "Followup call is not inserted in phone call table", image);
				
			} else {
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Followup call is inserted in phone call table");
				logger.log(LogStatus.INFO, "Followup call is inserted in phone call table", image);
				
			}

			Thread.sleep(10000);

			// -----Logout and login back to get confirmation Call-----
			Actions act1 = new Actions(driver);

			WebElement user2 = driver.findElement(By.xpath(("//div[@id='usernav']")));
			WebElement logoff12 = driver.findElement(By.xpath("//a[contains(text(),'Log Off')]"));

			act1.moveToElement(user2).build().perform();
			jse.executeScript("arguments[0].click();", logoff12);
			Thread.sleep(5000);

			WebElement logoff22 = driver.findElement(
					By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'Log Off')]"));
			//jse.executeScript("arguments[0].click();", logoff22);
			logoff22.click();
			Thread.sleep(15000);

			try{
				login.login(tabledata.get("UserName"), tabledata.get("Password"));
				logger.log(LogStatus.INFO, " Login successfully again");
				Thread.sleep(5000);
				
			}catch(Exception e){
				logger.log(LogStatus.INFO, "Displayed PM Home page with Follow Up Call display" );
			} 
			

			

			
			jse.executeScript("arguments[0].scrollIntoView();",
					driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")));
			Thread.sleep(2000);

			String actualCall = driver
					.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")).getText();

			if (actualCall.contains("FollowUp Call")) {
				resultFlag = "pass";
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Reservation Status is  " + actualCall);
				logger.log(LogStatus.INFO, "Reservation Status is " + actualCall + "", image);
			} else {
				resultFlag = "fail";
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Reservation Status is not  " + actualCall);
				logger.log(LogStatus.INFO, "Reservation Status is not  " + actualCall + "", image);
			}

			jse.executeScript("arguments[0].click();",
					driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")));
			Thread.sleep(8000);

			String actualReservationNumber = driver
					.findElement(By.xpath("//span[text()='Reservation Number:']/following-sibling::span")).getText();

			if (actualReservationNumber.trim().equals(reser_id)) {

				String actualConfirmationStatus = driver
						.findElement(By.xpath("//span[contains(@class,'reservation-status')]")).getText();

				if (actualConfirmationStatus.equalsIgnoreCase("Unconfirmed")) {
					resultFlag = "pass";
					scpath = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath, true);
					image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "Followup Call Status is Unconfirmed");
					logger.log(LogStatus.INFO, "Followup CallStatus is Unconfirmed", image);
				} else {
					resultFlag = "fail";
					scpath = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath, true);
					image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Followup Call is not Unconfirmed");
					logger.log(LogStatus.INFO, "Followup Call Status is not Unconfirmed", image);
				}

			}
			
			Thread.sleep(4000);
			if (res_num.equalsIgnoreCase(reser_id)) {
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Reservation Number in UI is---"+res_num);
				logger.log(LogStatus.INFO, "Reservation Number in DB--"+reser_id);
			} else {
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Reservation Number in UI is---"+res_num);
				logger.log(LogStatus.INFO, "Reservation Number in DB--"+reser_id);
			}
			Thread.sleep(4000);
			String IsCallCompleted = "select top 1 pc.IsCallCompleted from phonecall pc "
					+ "join type t on t.typeid=pc.calltypeid " + "where pc.OwnerTableItemID ='" + reser_id + "'";

			String IsCallCompleted_data = DataBase_JDBC.executeSQLQuery(IsCallCompleted);

			System.out.println( "the call complete---" + IsCallCompleted_data);

			if (IsCallCompleted_data.equals("1")) {

				resultFlag = "Pass";
				logger.log(LogStatus.PASS, "Followup Call is unconfirmed---"+IsCallCompleted_data);
				

			} else {
				// In the catch block, set the variable resultFlag to “fail”
				resultFlag = "fail";
				logger.log(LogStatus.FAIL, "Followup Call is confirmed---"+IsCallCompleted_data);
				
			}
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception " + ex);
		}

	}

	public void Verify_text(String rn, String text) {
		if (rn.equalsIgnoreCase(text)) {
			logger.log(LogStatus.PASS, text + " in date is displayed");
		} else {
			logger.log(LogStatus.FAIL, text + " in date  is not displayed");
		}

	}

}
