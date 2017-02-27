package Scenarios.WalkinReservationLease;

import java.util.ArrayList;
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
import Pages.Walkin_Reservation_Lease.Unreservation_popup;
import Scenarios.Browser_Factory;

public class Reservation_Movein_Confirmation_3 extends Browser_Factory {

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
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "Reservation_Movein_Confirmation_3", "Status",
					"Pass");

		} else if (resultFlag.equals("fail")) {
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Leasing for Customer verification", image);
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "Reservation_Movein_Confirmation_3", "Status",
					"Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "Reservation_Movein_Confirmation_3", "Status",
					"Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

	@DataProvider
	public Object[][] getLoginData() {
		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"Reservation_Movein_Confirmation_3");
	}

	@Test(dataProvider = "getLoginData")
	public void Reservation_Movein_Confirmation_3(Hashtable<String, String> tabledata) throws Exception {
		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}
		// Reporter.log("end Completed", true);
		Thread.sleep(5000);
		try {
			logger = extent.startTest("Reservation_Movein_Confirmation_3", "Reservation_Movein_Confirmation_3");

			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(5000);
			
			//driver.findElement(By.xpath("//a[@id='continueLink']")).click();

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
			
			
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "PM DashBoard page is displayed");
			logger.log(LogStatus.PASS, "PM DashBoard page is displayed", image);

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
			Thread.sleep(10000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)", "");
			Thread.sleep(2000);

			if (other_loc.OffSiteReservation_locnumber_count() == true) {
				locationnum = other_loc.OffSiteReservation_locnumber_gettext();
				jse.executeScript("arguments[0].click();", other_loc.RdBtn_Space_otherloc());
				Thread.sleep(3000);
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
			logger.log(LogStatus.PASS, "enter the first name");

			res.enter_LastName(tabledata.get("LastName"));
			logger.log(LogStatus.PASS, "enter the last name");
			Thread.sleep(2000);
			res.sel_DropDownValFromPhNo(tabledata.get("Phone"));
			logger.log(LogStatus.PASS, "Phone type is selected in CreateReservation Page");
			Reporter.log("Phone type is selected in CreateReservation Page", true);
			Thread.sleep(2000);
			res.enter_PhoneAreaCode(tabledata.get("PhoneAreacode"));
			logger.log(LogStatus.PASS, "enter phone area code");
			Thread.sleep(1000);
			res.enter_PhoneExchnge(tabledata.get("PhoneExchnge"));
			logger.log(LogStatus.PASS, "enter phone exchange");
			Thread.sleep(1000);
			res.enter_PhoneLineNumber(tabledata.get("PhoneLneNo"));
			logger.log(LogStatus.PASS, "enter line number");
			Thread.sleep(2000);
			res.enter_EmailAddr("psitqa@gmail.com");
			logger.log(LogStatus.PASS, "enter email address");
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			res.clk_CreateReservationButton();
			Thread.sleep(3000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Reservation Page is displayed with filled data");
			logger.log(LogStatus.PASS, "Reservation Page is displayed with filled data", image);
			
			
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");

			// ------------------------------------ Enter Note and Employeeid in
			// Reservation Pop up---------------------------
			Thread.sleep(3000);
			CreateReservation_PopUp rserservationpopup = new CreateReservation_PopUp(driver);
			rserservationpopup.enter_EmpID(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "employee no entered successfully ");
			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			Thread.sleep(4000);

			// ---- DataBase Configuration to create Reservation Call 1-------
			String siteid_loc = "select SiteID from site where sitenumber= '" + locationnum + "'";
			String siteid_loc_data = DataBase_JDBC.executeSQLQuery(siteid_loc);
			System.out.println(" th siteid is-----" + siteid_loc_data);
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

			String phonecallid_data = "select top 1 pc.phonecallid  from phonecall pc join type t on t.typeid=pc.calltypeid "
					+ "where pc.OwnerTableItemID ='" + reser_id
					+ "' and t.name='Confirmation Call'  Order by  PhonecallID desc";

			String phonecellid = DataBase_JDBC.executeSQLQuery(phonecallid_data);
			System.out.println(" th phonecellid is-----" + phonecellid);

			String change_res_curdate = "update phonecall set Scheduledcalldatetime=getutcdate() where phonecallid='"
					+ phonecellid + "'";
			DataBase_JDBC.executeSQLQuery(change_res_curdate);

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
			Thread.sleep(10000);
			
			//driver.findElement(By.xpath("//a[@id='continueLink']")).click();

			try {
				Bifrostpop1.clickContiDevice();
			} catch (Exception e) {
				System.out.println("Pop up is not avalable, so continuing!!");
			}
			Thread.sleep(5000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "PM DashBoard for Confirmation call 1");
			logger.log(LogStatus.PASS, "PM DashBoard for Confirmation call 1", image);

			// ---------- Clicking onb Resrvation Call 1 --------

			jse.executeScript("arguments[0].scrollIntoView();",
					driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")));
			Thread.sleep(2000);

			String actualCall = driver
					.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")).getText();

			Thread.sleep(3000);
			if (actualCall.trim().equalsIgnoreCase("Confirmation Call 1")) {
				driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")).click();
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

			Reporter.log("Clicked on Confirmation call 1 & proceeding further", true);

			Thread.sleep(10000);

			// -------------------------------------------Clicking on
			// UnConfirmation and Enter data in Unreservation Pop
			// up----------------------
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			res.clk_Unreservebutton();
			Thread.sleep(3000);
			Unreservation_popup unreservation_popup = new Unreservation_popup(driver);
			unreservation_popup.clk_not_reached_radiobtn();
			logger.log(LogStatus.INFO, "Clicked on not Reached button successfully");

			unreservation_popup.enter_pendingReservationNotes("not reachable");
			logger.log(LogStatus.INFO, "Entered note successfully");
			Thread.sleep(2000);

			unreservation_popup.enter_employeeNumber(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered Employee number successfully");
			Thread.sleep(3000);

			unreservation_popup.clk_Save_Continue();
			logger.log(LogStatus.INFO, "Clicked on save and continue button successfully");
			Thread.sleep(5000);

			// ------ Database Configuration for Reservation Call 2--------

			String phonecallid_data1 = "select top 1 pc.phonecallid  from phonecall pc join type t on t.typeid=pc.calltypeid "
					+ "where pc.OwnerTableItemID ='" + reser_id
					+ "' and t.name='Confirmation Call'  Order by  PhonecallID desc";

			String phonecellid1 = DataBase_JDBC.executeSQLQuery(phonecallid_data1);
			System.out.println(" th phonecellid is-----" + phonecellid1);
			Thread.sleep(10000);

			String change_res_curdate1 = "update phonecall set Scheduledcalldatetime=getutcdate() where phonecallid='"
					+ phonecellid1 + "'";
			DataBase_JDBC.executeSQLQuery(change_res_curdate1);

			Thread.sleep(10000);

			// ----Logout and login back to get confirmation Call----
			Actions act1 = new Actions(driver);

			WebElement user1 = driver.findElement(By.xpath(("//div[@id='usernav']")));
			WebElement logoff11 = driver.findElement(By.xpath("//a[contains(text(),'Log Off')]"));

			act1.moveToElement(user1).build().perform();
			jse.executeScript("arguments[0].click();", logoff11);
			Thread.sleep(5000);

			WebElement logoff21 = driver.findElement(
					By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'Log Off')]"));
			Actions action1 = new Actions(driver);
			action1.moveToElement(logoff21).click(logoff21).build().perform();
			Thread.sleep(8000);

			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(5000);
			
			//driver.findElement(By.xpath("//a[@id='continueLink']")).click();


			try {
				Bifrostpop1.clickContiDevice();
			} catch (Exception e) {
				System.out.println("Pop up is not avalable, so continuing!!");
			}
			Thread.sleep(5000);
			
			
			

			// ------ Clicking on Reservation Call 2 -------------

			jse.executeScript("arguments[0].scrollIntoView();",
					driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")));
			Thread.sleep(2000);

			String reservationCall2 = driver
					.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")).getText();

			Thread.sleep(3000);
			if (reservationCall2.trim().equalsIgnoreCase("Confirmation Call 2")) {
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Confirmation Call 2 is displayed");
				logger.log(LogStatus.INFO, "Confirmation Call 2 is displayed", image);
				driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")).click();
			} else {
				resultFlag = "fail";
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Confirmation Call 2 is not displayed");
				logger.log(LogStatus.INFO, "Confirmation Call 2 is not displayed", image);
			}

			Thread.sleep(10000);
			// -------------------------------------------Clicking on
			// UnConfirmation and Enter data in Unreservation Pop up for call
			// 2----------------------
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			res.clk_Unreservebutton();
			Thread.sleep(3000);
			Unreservation_popup unreservation_popup1 = new Unreservation_popup(driver);
			unreservation_popup1.clk_not_reached_radiobtn();
			logger.log(LogStatus.INFO, "Clicked on not Reached button successfully");

			unreservation_popup1.enter_pendingReservationNotes("not reachable");
			logger.log(LogStatus.INFO, "Entered note successfully");
			Thread.sleep(2000);

			unreservation_popup1.enter_employeeNumber(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered Employee number successfully");
			Thread.sleep(3000);

			unreservation_popup1.clk_Save_Continue();
			logger.log(LogStatus.INFO, "Clicked on save and continue button successfully");
			Thread.sleep(5000);

			// ------- Database Configuration for Reservation Call 3---------

			String phonecallid_data3 = "select top 1 pc.phonecallid  from phonecall pc join type t on t.typeid=pc.calltypeid "
					+ "where pc.OwnerTableItemID ='" + reser_id
					+ "' and t.name='Confirmation Call'  Order by  PhonecallID desc";

			String phonecellid3 = DataBase_JDBC.executeSQLQuery(phonecallid_data1);
			System.out.println(" th phonecellid is-----" + phonecellid1);
			Thread.sleep(10000);
			String change_res_curdate3 = "update phonecall set Scheduledcalldatetime=getutcdate() where phonecallid='"
					+ phonecellid3 + "'";
			DataBase_JDBC.executeSQLQuery(change_res_curdate3);
			
			
			if (phonecellid3.isEmpty()) {
				
				resultFlag = "fail";
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Confirmation Call 3 is not inserted in phone call table");
				logger.log(LogStatus.INFO, "Confirmation Call 3 is not inserted in phone call table", image);
				
			} else {
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Confirmation Call 3 is inserted in phone call table");
				logger.log(LogStatus.INFO, "Confirmation Call 3 is inserted in phone call table", image);
				
			}

			Thread.sleep(10000);

			// -----Logout and login back to get confirmation Call-----

			Actions act2 = new Actions(driver);
			WebElement user11 = driver.findElement(By.xpath(("//div[@id='usernav']")));
			WebElement logoff111 = driver.findElement(By.xpath("//a[contains(text(),'Log Off')]"));

			act2.moveToElement(user11).build().perform();
			jse.executeScript("arguments[0].click();", logoff111);
			Thread.sleep(5000);

			Actions action3 = new Actions(driver);
			WebElement logoff211 = driver.findElement(
					By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'Log Off')]"));

			action3.moveToElement(logoff211).click(logoff211).build().perform();
			Thread.sleep(15000);

			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(5000);
			
			//driver.findElement(By.xpath("//a[@id='continueLink']")).click();


			try {
				Bifrostpop1.clickContiDevice();
			} catch (Exception e) {
				System.out.println("Pop up is not avalable, so continuing!!");
			}
			Thread.sleep(5000);

			// ---------- Clicking on Reservation Call 3 --------

			jse.executeScript("arguments[0].scrollIntoView();",
					driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")));
			Thread.sleep(2000);

			String reservationCall3 = driver
					.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")).getText();

			Thread.sleep(3000);
			if (reservationCall3.trim().equalsIgnoreCase("Confirmation Call 3")) {
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Reservation Call 3 is displayed");
				logger.log(LogStatus.INFO, "Reservation Call 3 is displayed", image);
			
				driver.findElement(By.xpath("//div[@id='reservations']//a[contains(@alt,'" + reser_id + "')]")).click();
			} else {
				resultFlag = "fail";
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Reservation Call 3 not is displayed");
				logger.log(LogStatus.INFO, "Reservation Call 3 not is displayed", image);
			}
			Thread.sleep(10000);

			
			String status = res.gettext_reservation_status();
			Thread.sleep(1000);
			String name = res.get_resname();
			Thread.sleep(1000);
			String phone = res.get_resphone();
			Thread.sleep(1000);
			String email = res.get_resemail();
			Thread.sleep(1000);

			if (status.equalsIgnoreCase("UNREACHED")) {
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Reservation Status is UNREACHED & displayed");
				logger.log(LogStatus.INFO, "Reservation Status is UNREACHED & displayed", image);
			} else {
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Reservation Status is not UNREACHED");
				logger.log(LogStatus.INFO, "Reservation Status is not UNREACHED", image);
			}
			
			Thread.sleep(1000);
			 String query1 = "select CoNCAT(FirstName,' ',LastName) as name,PhoneID,EmailAddressID from reservation where ReservationID='"+reser_id+"'";

				ArrayList<String> resdetailsdb = DataBase_JDBC.executeSQLQuery_List(query1);
				
				if (name.equalsIgnoreCase(resdetailsdb.get(0))) {
					
					logger.log(LogStatus.PASS, "The Reservation Firstname & LastName in UI--"+name+"-The Names saved in DB--"+resdetailsdb.get(0));
					
				} else {
					
					logger.log(LogStatus.FAIL, "The Reservation Firstname & LastName in UI--"+name+"-The Names saved in DB--"+resdetailsdb.get(0));
					
				}
				
				Thread.sleep(1000);
				
				 String phone_db = "select CONCAT(AreaCode,'-', Exchange,'-',LineNumber) from Phone where PhoneID='"+resdetailsdb.get(1)+"'";

					String resphonedb = DataBase_JDBC.executeSQLQuery(phone_db);
					
					if (phone.equalsIgnoreCase(resphonedb)) {
						
						logger.log(LogStatus.PASS, "The Reservation Phone Number  in UI--"+phone+"-The Phone Number saved in DB--"+resphonedb);
						
					} else {
						
						logger.log(LogStatus.FAIL, "The Reservation Phone Number  in UI--"+phone+"-The Phone Number saved in DB--"+resphonedb);
						
					}	
				
					Thread.sleep(1000);
					
					 String email_db = "select Email from EmailAddress where EmailAddressID='"+resdetailsdb.get(2)+"'";

						String resemaildb = DataBase_JDBC.executeSQLQuery(email_db);
						
						if (email.equalsIgnoreCase(resemaildb)) {
							
							logger.log(LogStatus.PASS, "The Reservation email  in UI--"+email+"-The email saved in DB--"+resemaildb);
							
						} else {
							
							logger.log(LogStatus.FAIL, "The Reservation email  in UI--"+email+"-The email saved in DB--"+resemaildb);
							
						}	
			
			

			Thread.sleep(10000);

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

}
