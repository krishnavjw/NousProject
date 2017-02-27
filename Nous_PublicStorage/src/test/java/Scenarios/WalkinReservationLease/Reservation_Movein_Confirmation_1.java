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
import Pages.Walkin_Reservation_Lease.ReservationCall_view;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_OtherLocations;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Scenarios.Browser_Factory;

public class Reservation_Movein_Confirmation_1 extends Browser_Factory {

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
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "Reservation_Movein_Confirmation_1", "Status",
					"Pass");

		} else if (resultFlag.equals("fail")) {
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Leasing for Customer verification", image);
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "Reservation_Movein_Confirmation_1", "Status",
					"Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "Reservation_Movein_Confirmation_1", "Status",
					"Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

	@DataProvider
	public Object[][] getLoginData() {
		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"Reservation_Movein_Confirmation_1");
	}

	@Test(dataProvider = "getLoginData")
	public void Reservation_Movein_Confirmation(Hashtable<String, String> tabledata) throws Exception {
		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}
		Thread.sleep(5000);
		try {
			logger = extent.startTest("Reservation_Movein_Confirmation_1", "Reservation_Movein_Confirmation_1");

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
			// -------------------------------Clicking on Lease and space button-----------------------------
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
			Thread.sleep(6000);

			// -------Selecting space and clicking on OffReserveunit
			// button----------

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
				Thread.sleep(3000);
				other_loc.clk_OffSiteSubmitBtn();
				logger.log(LogStatus.PASS,
						"checked the radio button based on the location and click on the  reservation button");
			} else {
				resultFlag = "fail";
				logger.log(LogStatus.FAIL, "No space is avialable to select");

			}

			Thread.sleep(10000);
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
			
			Thread.sleep(2000);
			res.sel_DropDownValFromPhNo(tabledata.get("Phone"));
			logger.log(LogStatus.PASS, "Phone type is selected in CreateReservation Page");
			
			Thread.sleep(2000);
			res.enter_PhoneAreaCode(tabledata.get("PhoneAreacode"));
			
			Thread.sleep(1000);
			res.enter_PhoneExchnge(tabledata.get("PhoneExchnge"));
			
			Thread.sleep(1000);
			res.enter_PhoneLineNumber(tabledata.get("PhoneLneNo"));
			
			Thread.sleep(2000);
			res.enter_EmailAddr(tabledata.get("Emailaddrs"));
			
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Created the reservation");
			logger.log(LogStatus.INFO, "Created the reservation", image);
			
			Thread.sleep(2000);
			res.clk_CreateReservationButton();
			Thread.sleep(3000);
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");

			// ------------------------------------ Enter Note and Employeeid in
			// Reservation Pop up---------------------------
			Thread.sleep(4000);
			CreateReservation_PopUp rserservationpopup = new CreateReservation_PopUp(driver);
			rserservationpopup.enter_EmpID(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "employee no entered successfully ");
			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			Thread.sleep(4000);

			// ----------------- DataBase Configuration to create Reservation
			// Call 1-------------------------------------------------
			String siteid_loc = "select SiteID from site where sitenumber= '" + locationnum + "'";
			String siteid_loc_data = DataBase_JDBC.executeSQLQuery(siteid_loc);
			
			String ipadd = Generic_Class.getIPAddress();
			Thread.sleep(1000);
			// String ipadd = tabledata.get("Ipaddress");

			String siteid_ipsdd = "select SiteID from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"
					+ ipadd + "'";
			String siteid_ipsdd_data = DataBase_JDBC.executeSQLQuery(siteid_ipsdd);
			System.out.println(" th siteid_ipsdd_data is-----" + siteid_ipsdd_data);

			String allocateemtyip = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"
					+ siteid_ipsdd_data + "'";
			DataBase_JDBC.executeSQLQuery(allocateemtyip);

			String allocateip_to_newid = "Update siteparameter set paramvalue='" + ipadd
					+ "' where paramcode='IP_COMPUTER_FIRST' and siteid='" + siteid_loc_data + "'";
			DataBase_JDBC.executeSQLQuery(allocateip_to_newid);

			String reservationid = "select Top 1 ReservationID  from reservation where siteid='" + siteid_loc_data
					+ "' and FirstName='" + tabledata.get("FirstName") + "' and LastName='" + tabledata.get("LastName")
					+ "' and ReservationStatusTypeID='128'  Order by  LastUpdate DESC";
		
			String reser_id = DataBase_JDBC.executeSQLQuery(reservationid);
			System.out.println(" th reser_id is-----" + reser_id);
			logger.log(LogStatus.INFO, "The reservation id created in DB-"+reser_id);

			String phonecallid_data = "select top 1 pc.phonecallid  from phonecall pc join type t on t.typeid=pc.calltypeid "
					+ "where pc.OwnerTableItemID ='" + reser_id
					+ "' and t.name='Confirmation Call'  Order by PhonecallID  desc";

			String phonecellid = DataBase_JDBC.executeSQLQuery(phonecallid_data);
			
			logger.log(LogStatus.INFO, "The phonecellid created for confirmation Call in DB-"+phonecellid);

			String change_res_curdate = "update phonecall set Scheduledcalldatetime=getutcdate() where phonecallid='"
					+ phonecellid + "'";
			DataBase_JDBC.executeSQLQuery(change_res_curdate);
			
			logger.log(LogStatus.INFO, "The phonecell table Scheduledcalldatetime is set to current date -");
			
			
			

			Thread.sleep(10000);

			// --------------------------------------Logout and login back to
			// get confirmation Call-------------------------------

			Actions act = new Actions(driver);

			WebElement user = driver.findElement(By.xpath(("//div[@id='usernav']")));
			WebElement logoff1 = driver.findElement(By.xpath("//a[contains(text(),'Log Off')]"));

			act.moveToElement(user).build().perform();
			jse.executeScript("arguments[0].click();", logoff1);
			Thread.sleep(5000);

			WebElement logoff2 = driver.findElement(
					By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'Log Off')]"));
			Actions action = new Actions(driver);
			action.moveToElement(logoff2).click(logoff2).build().perform();
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

			// ---------- Clicking onb Resrvation Call 1 --------

			jse.executeScript("arguments[0].scrollIntoView();",
					driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")));
			Thread.sleep(2000);

			String actualCall = driver
					.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")).getText();

			Thread.sleep(3000);
			if (actualCall.trim().equalsIgnoreCase("Confirmation Call 1")) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Confirmation Call 1 is displayed in UI ");
				logger.log(LogStatus.INFO, "Confirmation Call 1 is displayed in UI ", image);
				driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")).click();
			} else {
				resultFlag = "fail";
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Confirmation Call 1 not is displayed in UI ");
				logger.log(LogStatus.INFO, "Confirmation Call 1 not is displayed in UI ", image);
			}

			Reporter.log("Clicked on Confirmation call 1 & proceeding further", true);

			Thread.sleep(10000);

			// -------------------------------- Click on Confirm Reservation and
			// and Submit------------------------------------------------------
			
			
			
			String res_num = res.get_reservationnumber();
			
			
			if (res_num.equalsIgnoreCase(reser_id)) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Reservation Number in UI is---"+res_num);
				logger.log(LogStatus.INFO, "Reservation Number in DB--"+reser_id);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Reservation Number in UI is---"+res_num);
				logger.log(LogStatus.INFO, "Reservation Number in DB--"+reser_id);
			}
			
			
			Thread.sleep(2000);
			String res_movindate= res.get_movindate();
			
			logger.log(LogStatus.INFO, "Movin date displayed--"+res_movindate);
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Screen displayed after clicking on Confirmation Call 1");
			logger.log(LogStatus.INFO, "Screen displayed after clicking on Confirmation Call 1", image);
			
			Thread.sleep(2000);
			res.clk_confirmReservationButton();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "clicked on confirm reservation btn successfully");

			
			Thread.sleep(2000);
			CreateReservation_PopUp createRes_PopUp = new CreateReservation_PopUp(driver);

			String reservnum = createRes_PopUp.reservnum_disply();
			Verify_text(reservnum, reser_id);

			createRes_PopUp.enter_confirmresnotes(tabledata.get("Note"));
			logger.log(LogStatus.INFO, "Entered note successfully");
			Thread.sleep(5000);

			createRes_PopUp.enter_EmpID(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered Employee number successfully");
			Thread.sleep(3000);

			createRes_PopUp.clk_ConfirmBtn();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "Clicked on confirm button successfully");

			Thread.sleep(3000);

			String IsCalltypeid = "select top 1 pc.CallTypeID from phonecall pc "
					+ "join type t on t.typeid=pc.calltypeid " + "where pc.OwnerTableItemID ='" + reser_id + "'";

			String IsCalltypeid_data = DataBase_JDBC.executeSQLQuery(IsCalltypeid);
			
			
			String IsCalltype = "select name from type where typeid='" + IsCalltypeid_data + "'";

			String IsCalltype_data = DataBase_JDBC.executeSQLQuery(IsCalltypeid);
			

			String IsCallCompleted = "select top 1 pc.IsCallCompleted from phonecall pc "
					+ "join type t on t.typeid=pc.calltypeid " + "where pc.OwnerTableItemID ='" + reser_id + "'";

			String IsCallCompleted_data = DataBase_JDBC.executeSQLQuery(IsCallCompleted);

			
			Thread.sleep(2000);

			if (IsCalltypeid_data.equals("4314") && IsCallCompleted_data.equals("1")) {

				resultFlag = "Pass";
				logger.log(LogStatus.PASS, "The call type is Database is---"+IsCalltype_data);
				logger.log(LogStatus.PASS, "The Confirmation Call is completed");

			} else {
				// In the catch block, set the variable resultFlag to “fail”
				resultFlag = "fail";
				logger.log(LogStatus.FAIL, "The call type is Database is is ---"+IsCalltype_data);
				logger.log(LogStatus.PASS, "The Confirmation Call is not completed");
			}

			/*ReservationCall_view res_view = new ReservationCall_view(driver);

			res_view.clk_reservation_icon();
			Thread.sleep(6000);
			res_view.clk_reservation_accnum(reser_id);

			String status = res.gettext_reservation_status();
			Thread.sleep(2000);
			
			if (status.equalsIgnoreCase("Confirmed")) {
				Reporter.log(scpath, true);
				
				logger.log(LogStatus.PASS, "Reservation Status is Confirmed & displayed");
			
			} else {
				
				Reporter.log(scpath, true);
			
				logger.log(LogStatus.FAIL, "Reservation Status is not Confirmed");
				
			}*/
			Thread.sleep(2000);

			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			// In the catch block, set the variable resultFlag to “fail”
			resultFlag = "fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception " + ex);
		}

	}

	public void Verify_text(String rn, String text) {
		if (rn.equalsIgnoreCase(text)) {
			logger.log(LogStatus.PASS, text + " registarion number  is displayed");
		} else {
			logger.log(LogStatus.FAIL, text + " registarion number  is not displayed");
		}

	}

}
