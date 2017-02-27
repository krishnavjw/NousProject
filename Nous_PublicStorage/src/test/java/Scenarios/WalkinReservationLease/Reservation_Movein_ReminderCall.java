package Scenarios.WalkinReservationLease;

import java.util.Hashtable;

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

public class Reservation_Movein_ReminderCall extends Browser_Factory {

	String locationnum = null;
	public ExtentTest logger;
	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "Pass";

	@AfterMethod
	public void afterMethod() {

		System.out.println(" In After Method");
		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "Reservation_Movein_ReminderCall", "Status",
					"Pass");

		} else if (resultFlag.equals("fail")) {
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Leasing for Customer verification", image);
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "Reservation_Movein_ReminderCall", "Status",
					"Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "Reservation_Movein_ReminderCall", "Status",
					"Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

	@DataProvider
	public Object[][] getLoginData() {
		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"Reservation_Movein_ReminderCall");
	}

	@Test(dataProvider = "getLoginData")
	public void Reservation_Movein_ReminderCall(Hashtable<String, String> tabledata) throws Exception {
		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		Thread.sleep(5000);
		try {
			logger = extent.startTest("Reservation_Movein_ReminderCall", "Reservation_Movein_ReminderCall");

			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);

			Dashboard_BifrostHostPopUp Bifrostpop1 = new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			try {
				Bifrostpop1.clickContiDevice();
			} catch (Exception e) {
				System.out.println("Pop up is not avalable, so continuing!!");
			}

			// -------------------------------Clicking on Lease and space
			// button-----------------------------
			Thread.sleep(2000);
			PM_Homepage homepage = new PM_Homepage(driver);
			logger.log(LogStatus.INFO, "Home Page object is created successfully");

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
			logger.log(LogStatus.PASS, "Clicked on Search button----");
			Reporter.log("Clicked on Search button again ", true);
			Thread.sleep(10000);

			// -------Selecting space and clicking on OffReserveunit
			// button----------

			SpaceDashboard_ThisLoc this_location = new SpaceDashboard_ThisLoc(driver);
			SpaceDashboard_OtherLocations other_loc = new SpaceDashboard_OtherLocations(driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			this_location.click_otherLocations();
			Thread.sleep(15000);

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
			res.SelectDateFromCalendar("3");
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
			logger.log(LogStatus.PASS, "enter email address");
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
			// String ipadd = tabledata.get("Ipaddress");

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

			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);

			try {
				Bifrostpop1.clickContiDevice();
			} catch (Exception e) {
				System.out.println("Pop up is not avalable, so continuing!!");
			}
			Thread.sleep(5000);

			// ----- Clicking on Resrvation Call 1 -----

			jse.executeScript("arguments[0].scrollIntoView();",
					driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")));
			Thread.sleep(5000);

			WebElement rescall = driver
					.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]"));

			if (rescall.isDisplayed()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Confirmation  Call 1 is displayed");
				logger.log(LogStatus.INFO, "Confirmation  Call 1 is displayed", image);
			} else {
				resultFlag = "fail";
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Confirmation  Call 1 not is displayed");
				logger.log(LogStatus.INFO, "Confirmation  Call 1 not is displayed", image);
			}

			jse.executeScript("arguments[0].click();", rescall);

			Reporter.log("Clicked on Confirmation call 1 & proceeding further", true);
			Thread.sleep(10000);

			// ---Clicking on UnConfirmation and Enter data in Unreservation Pop
			// up------
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			res.clk_Unreservebutton();
			Thread.sleep(3000);

			Unreservation_popup unreservation_popup = new Unreservation_popup(driver);
			unreservation_popup.clk_Not_Confirmed_radiobtn();

			logger.log(LogStatus.INFO, "Clicked on not Reached button successfully");
			Thread.sleep(3000);

			driver.findElement(
					By.xpath("//div[@class='call-info__call-type__dropdown-container floatleft vertical-center']"))
					.click();
			Thread.sleep(3000);

			driver.findElement(By
					.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']//ul/li[contains(text(),'Reminder Call')]"))
					.click();
			Thread.sleep(3000);

			driver.findElement(By.xpath("(//a[contains(text(),'Edit')])[2]")).click();
			Thread.sleep(3000);

			driver.findElement(By.xpath("//div[@id='callDatePicker']")).click();
			Thread.sleep(4000);

			res.SelectDateFromCalendar_onemonth("2");
			Thread.sleep(4000);

			unreservation_popup.enter_pendingReservationNotes("not reachable");
			logger.log(LogStatus.INFO, "Entered note successfully");
			Thread.sleep(1000);

			unreservation_popup.enter_employeeNumber(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered Employee number successfully");
			Thread.sleep(1000);

			unreservation_popup.clk_Save_Continue();
			logger.log(LogStatus.INFO, "Clicked on save and continue button successfully");
			Thread.sleep(5000);

			// ------ Database Configuration for Reservation Call 2---------

			String phonecallid_data1 = "select top 1 pc.phonecallid  from phonecall pc join type t on t.typeid=pc.calltypeid "
					+ "where pc.OwnerTableItemID ='" + reser_id
					+ "' and t.name='Reminder Call'  Order by  PhonecallID desc";

			String phonecellid1 = DataBase_JDBC.executeSQLQuery(phonecallid_data1);
			Thread.sleep(3000);

			String change_res_curdate1 = "update phonecall set Scheduledcalldatetime=getutcdate() where phonecallid='"
					+ phonecellid1 + "'";
			DataBase_JDBC.executeSQLQuery(change_res_curdate1);
			
			
			if (phonecellid1.isEmpty()) {
				
				resultFlag = "fail";
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Reminder call is not inserted in phone call table");
				logger.log(LogStatus.INFO, "Reminder call is not inserted in phone call table", image);
				
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Reminder call is inserted in phone call table");
				logger.log(LogStatus.INFO, "Reminder call is inserted in phone call table", image);
				
			}

			Thread.sleep(3000);

			// -----Logout and login back to get confirmation Call-----
			Actions act1 = new Actions(driver);

			WebElement user2 = driver.findElement(By.xpath(("//div[@id='usernav']")));
			WebElement logoff12 = driver.findElement(By.xpath("//a[contains(text(),'Log Off')]"));

			act1.moveToElement(user2).build().perform();
			jse.executeScript("arguments[0].click();", logoff12);
			Thread.sleep(5000);

			WebElement logoff22 = driver.findElement(
					By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'Log Off')]"));
			jse.executeScript("arguments[0].click();", logoff22);
			Thread.sleep(8000);

			try {
				if (Bifrostpop1.isPopUpDisplayed()) {
					try {
						Bifrostpop1.clickContiDevice();
					} catch (Exception e) {
						System.out.println("Pop up is not avalable, so continuing!!");
					}
					Thread.sleep(3000);

					act.moveToElement(user2).build().perform();
					jse.executeScript("arguments[0].click();", logoff12);
					Thread.sleep(3000);

					jse.executeScript("arguments[0].click();", logoff22);
					Thread.sleep(8000);
				}
			} catch (Exception e) {
				Reporter.log("Pop up is not available this time, so continuing!!!");
			}

			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);

			try {
				Bifrostpop1.clickContiDevice();
			} catch (Exception e) {
				System.out.println("Pop up is not avalable, so continuing!!");
			}
			Thread.sleep(5000);

			jse.executeScript("arguments[0].scrollIntoView();",
					driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")));
			Thread.sleep(2000);

			String actualCall = driver
					.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")).getText();

			if (actualCall.contains("Reminder Call")) {
				resultFlag = "pass";
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Reservation Status is  " + actualCall);
				logger.log(LogStatus.INFO, "Reservation Status is " + actualCall + "", image);
			} else {
				resultFlag = "fail";
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
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
					String scpath = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath, true);
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "Reservation Status is Unconfirmed");
					logger.log(LogStatus.INFO, "Reservation Status is Unconfirmed", image);
				} else {
					resultFlag = "fail";
					String scpath = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath, true);
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Reservation Status is not Unconfirmed");
					logger.log(LogStatus.INFO, "Reservation Status is not Unconfirmed", image);
				}

			}
			
			Thread.sleep(4000);
			String IsCallCompleted = "select top 1 pc.IsCallCompleted from phonecall pc "
					+ "join type t on t.typeid=pc.calltypeid " + "where pc.OwnerTableItemID ='" + reser_id + "'"
					+ "and pc.calltypeid=4315" ;

			String IsCallCompleted_data = DataBase_JDBC.executeSQLQuery(IsCallCompleted);

			System.out.println( "the call complete---" + IsCallCompleted_data);

			if (!IsCallCompleted_data.equals("1")) {

				resultFlag = "Pass";
				logger.log(LogStatus.PASS, "Reminder Call is unconfirmed");
				

			} else {
				// In the catch block, set the variable resultFlag to “fail”
				resultFlag = "fail";
				logger.log(LogStatus.FAIL, "Reminder Call is confirmed");
				
			}


		} catch (Exception ex) {
			ex.printStackTrace();
			// In the catch block, set the variable resultFlag to “fail”
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
	// Reminder Call
}
