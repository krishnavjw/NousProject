package Scenarios.WalkinReservationLease;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
import Pages.Walkin_Reservation_Lease.CancelReservationPage;
import Pages.Walkin_Reservation_Lease.CreateReservation;
import Pages.Walkin_Reservation_Lease.CreateReservation_PopUp;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.SpecificSpace;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.VehicleStoragePage;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import Scenarios.Browser_Factory;

public class CreateParkingSpaceReservation_Individual extends Browser_Factory {
	public ExtentTest logger;

	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";

	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "CreateParkingSpaceReservation_Individual",
					"Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "CreateParkingSpaceReservation_Individual",
					"Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "CreateParkingSpaceReservation_Individual",
					"Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);

	}

	@DataProvider
	public Object[][] getCustSearchData() {
		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"CreateParkingSpaceReservation_Individual");
	}

	@BeforeMethod
	public void setSiteId() throws InterruptedException {

	}

	@Test(dataProvider = "getCustSearchData")
	public void reservation_Existingcustomer_Individual(Hashtable<String, String> tabledata)
			throws InterruptedException {
		try {

			logger = extent.startTest("CreateParkingSpaceReservation_Individual",
					"CreateParkingSpaceReservation_Individual ");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " + testcaseName, true);

			if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
				resultFlag = "skip";
				throw new SkipException("Skipping the test");
			}

			JavascriptExecutor jse = (JavascriptExecutor) driver;

			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(tabledata.get("UserName"), tabledata.get("Password"));


			Dashboard_BifrostHostPopUp Bifrostpop1 = new Dashboard_BifrostHostPopUp(driver);

			try {
				Bifrostpop1.clickContiDevice();
			} catch (Exception e) {
				System.out.println("Pop up is not avalable, so continuing!!");
			}


			// Get the site id where parking space is available under vehicle
			// storage
			String query1 = "Select top 1 ps.SiteID " + "from Productsite PS "
					+ "Join RentalUnit RU on RU.ProductSiteID = PS. ProductSiteID "
					+ "join StorageProductFeature spf ON ps.StorageProductFeatureID = spf.StorageProductFeatureID "
					+ "Where  RentalStatusTypeID = 59 "
					+ "and  PS.Siteid  in (6,411, 35, 13, 428, 913, 528, 913, 42, 424, 85, 29,136) "
					+ "and spf.Parking = 1 " + "order by siteid desc";
			String siteid_tobeset = DataBase_JDBC.executeSQLQuery(query1);

			// Get the current system ip address
			String ipadd = Generic_Class.getIPAddress();
			Thread.sleep(1000);

			// Get the currently assigned site id
			String current_siteid_query = "select SiteID from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"
					+ ipadd + "'";
			String current_siteid = DataBase_JDBC.executeSQLQuery(current_siteid_query);
			System.out.println(" th siteid_ipsdd_data is-----" + current_siteid);

			// Make the currently assigned site id to null
			String allocate_currentsiteidtonull_query = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"
					+ current_siteid + "'";
			DataBase_JDBC.executeSQLQuery(allocate_currentsiteidtonull_query);

			// Allocate new site id to current ip address
			String allocateip_to_newid = "Update siteparameter set paramvalue='" + ipadd
					+ "' where paramcode='IP_COMPUTER_FIRST' and siteid='" + siteid_tobeset + "'";
			DataBase_JDBC.executeSQLQuery(allocateip_to_newid);

			Thread.sleep(8000);

			// ---Logout and login back to get confirmation Call----
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

			// Login To the Application
			loginPage.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Login to Application  successfully");

			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			Thread.sleep(10000);

			try {
				Bifrostpop1.clickContiDevice();
			} catch (Exception e) {
				System.out.println("Pop up is not avalable, so continuing!!");
			}

			Thread.sleep(20000);

			// Verify that the user lands on the "PM Dashboard" screen after
			// login and walkin cust title
			PM_Homepage pmhomepage = new PM_Homepage(driver);

			if (tabledata.get("walkInCustTitle").contains(pmhomepage.get_WlkInCustText().trim())) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "walkInCustTitle is displayed");
				logger.log(LogStatus.INFO, "walkInCustTitle is displayed", image);
				resultFlag = "pass";
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "walkInCustTitle is not displayed ");
				logger.log(LogStatus.INFO, "walkInCustTitle is not displayed ", image);

			}

			// click on find and lease a space

			if (pmhomepage.verify_findAndLeaseSpace()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "find and Lease a space btn is displayed");
				logger.log(LogStatus.INFO, "find and Lease a space btn is displayed", image);
				resultFlag = "pass";
				pmhomepage.clk_findAndLeaseSpace();
				Thread.sleep(10000);
			} else {
				logger.log(LogStatus.FAIL, "find and Lease a space btn is not displayed successfully");
				logger.log(LogStatus.INFO, "find and Lease a space btn is not displayed successfully");
			}

			// Verify Standard Storage Tab and New Customer Radio button
			// selected by default
			StandardStoragePage stndstorage = new StandardStoragePage(driver);


			VehicleStoragePage vehStorage = new VehicleStoragePage(driver);


			SpecificSpace space = new SpecificSpace(driver);


			if (stndstorage.isdisplayed_StandardStorage() && vehStorage.isdisplayed_vehicleStoragetab()
					&& space.isdisplayed_SpecificSpaceTab()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Standard,Vehicle and Specificspace tabs are displayed");
				logger.log(LogStatus.INFO, "Standard,Vehicle and Specificspace tabs are displayed", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Standard,Vehicle and Specificspace tabs are not displayed");
				logger.log(LogStatus.INFO, "Standard,Vehicle and Specificspace tabs are not displayed", image);
			}

			if (stndstorage.isdisplayed_yesradiobutton()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "New customer Radio button is selected by default");
				logger.log(LogStatus.INFO, "New customer Radio button is selected by default", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "New customer Radio button is not selected by default");
				logger.log(LogStatus.INFO, "New customer Radio button is not selected by default", image);
			}

			// click Vehicle storage tab
			vehStorage.click_vehiclestoragetab();
			Thread.sleep(50000);
			logger.log(LogStatus.INFO, "Clicked on vehicle storage tab");

			// verify vehicle size options are available with checkboxes
			boolean cartype = vehStorage.verify_car();
			boolean carlength = vehStorage.verify_car_length();
			boolean boattype = vehStorage.verify_boat();
			boolean boatlength = vehStorage.verify_boat_length();
			boolean oversize = vehStorage.verify_oversized();
			boolean oversizelength = vehStorage.verify_oversized_length();
			boolean car_chkbx = vehStorage.verify_boat_chkbx();
			boolean boat_chkbx = vehStorage.verify_car_chkbx();
			boolean oversise_chkbx = vehStorage.verify_ovrsz_chkbx();

			if (car_chkbx && cartype && carlength && boat_chkbx && boattype && boatlength && oversise_chkbx && oversize
					&& oversizelength) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"Vehicle types along with their lengths and respective chkboxes are displayed");
				logger.log(LogStatus.INFO,
						"Vehicle types along with their lengths and respective chkboxes are displayed", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL,
						"Vehicle types along with their lengths and respective chkboxes are not displayed");
				logger.log(LogStatus.INFO,
						"Vehicle types along with their lengths and respective chkboxes are not displayed", image);

			}
			// verify all the space type options

			boolean cov_chkbx = vehStorage.verify_covered_chkbox();
			boolean cov_txt = vehStorage.verify_covered();
			boolean uncov_chkbx = vehStorage.verify_uncovered_chkbox();
			boolean uncov_txt = vehStorage.verify_uncovered();
			boolean enc_chkbx = vehStorage.verify_enclosed_chkbox();
			boolean enc_txt = vehStorage.verify_enclosed();

			if (cov_chkbx && cov_txt && uncov_chkbx && uncov_txt && enc_chkbx && enc_txt) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "space types are displayed");
				logger.log(LogStatus.INFO, "space types are displayed", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "space types are displayed");
				logger.log(LogStatus.INFO, "space types are displayed", image);
			}

			// verify without checking any checkbox click on search btn
			stndstorage.click_Search();
			logger.log(LogStatus.INFO, "Clicked on search button");
			Thread.sleep(5000);
			boolean errmsg = driver.getPageSource().contains("Please select a vehicle size");
			if (errmsg) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Expected error msg is displayed");
				logger.log(LogStatus.INFO, "Expected error msg is displayed", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Expected error msg is not displayed");
				logger.log(LogStatus.INFO, "Expected error msg is not displayed", image);

			}

			driver.findElement(By.partialLinkText("OK")).click();
			Thread.sleep(8000);
			// verify all the check boxes are selectable and multiple chkboxes
			// are selectable for vehicle type
			vehStorage.click_Car();
			vehStorage.click_Boat();
			vehStorage.click_Oversized();
			Thread.sleep(5000);
			String scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS,
					"All the check boxes are selectable and multiple chkboxes are selectable for vehicle type");
			logger.log(LogStatus.INFO,
					"All the check boxes are selectable and multiple chkboxes are selectable for vehicle type", image);

			// verify all the check boxes are selectable and multiple chkboxes
			// are selectable for space type
			vehStorage.click_Covered();
			vehStorage.click_Uncovered();
			vehStorage.click_Enclosed();
			Thread.sleep(5000);
			String scpath1 = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath1, true);
			String image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS,
					"All the check boxes are selectable and multiple chkboxes are selectable for space type");
			logger.log(LogStatus.INFO,
					"All the check boxes are selectable and multiple chkboxes are selectable for space type", image1);

			stndstorage.click_Search();
			logger.log(LogStatus.INFO, "Clicked on search button");
			Thread.sleep(10000);

			// select any space
			List<WebElement> norows = driver.findElements(By.xpath("//form[@id='frmReserveUnits']//table//tbody//tr"));
			String space1 = null;
			if (norows.size() > 0) {
				space1 = driver.findElement(By.xpath("//form[@id='frmReserveUnits']//table//tbody//tr[1]/td[4]"))
						.getText();
				Reporter.log("space number is:" + space1, true);
			} else {
				logger.log(LogStatus.FAIL, "Application is not populating any data/space details");
				resultFlag = "fail";
			}

			// String Space = "4008";
			WebElement RdBtn_Space = driver.findElement(By
					.xpath("//td[@class='grid-cell-space'][text()='" + space1 + "']/../td/input[@name='selectedIds']"));

			logger.log(LogStatus.PASS,
					"check the radio button based on the space and click on the  reservation button");
			jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space);
			Thread.sleep(3000);
			jse.executeScript("arguments[0].click();", RdBtn_Space);
			logger.log(LogStatus.PASS, "Clicked on check box of a space in this location: " + space1);
			Reporter.log("Clicked on check box of a space in this location: " + space1, true);
			Thread.sleep(2000);
			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"Image", image1);


			// generics.Page_ScrollDown();
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);

			jse.executeScript("window.scrollBy(0,2000)", "");
			Thread.sleep(3000);

			SpaceDashboard_ThisLoc spdshbrd = new SpaceDashboard_ThisLoc(driver);
			spdshbrd.click_Reserve();
			Thread.sleep(5000);

			CreateReservation res = new CreateReservation(driver);

			WebDriverWait wait = new WebDriverWait(driver, 180L);
			wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath("//span[text()='Reservation Details']"))));

			if (res.verify_CreatereservationPage()) {
				String scpath2 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath2, true);
				String image2 = logger.addScreenCapture(scpath2);
				logger.log(LogStatus.PASS, "Create reservation page is displayed");
				logger.log(LogStatus.INFO, "Create reservation page is displayed", image2);
			} else {

				String scpath2 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath2, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image2 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.FAIL, "Create reservation page is not displayed");
				logger.log(LogStatus.INFO, "Create reservation page is not displayed", image2);
			}

			driver.findElement(By.xpath("//div[@id='expectedMoveInDate']//span[text()='SELECT']")).click();
			Thread.sleep(4000);
			res.SelectDateFromCalendar("4");

			// logger.log(LogStatus.PASS,"MoveIn date is selected in calendar
			// "+tabledata.get("MoveInDate"));
			Reporter.log("MoveIn date is selected in calendar", true);

			Thread.sleep(5000);
			String FirstName = "Nous" + Generic_Class.get_RandmString();
			Thread.sleep(1000);
			res.enter_FirstName(FirstName);
			logger.log(LogStatus.PASS, "enter the first name");
			String LastName = "Automation" + Generic_Class.get_RandmString();
			Thread.sleep(1000);
			res.enter_LastName(LastName);
			logger.log(LogStatus.PASS, "enter the last name");
			Thread.sleep(1000);
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
			Thread.sleep(1000);
			res.enter_EmailAddr(tabledata.get("Emailaddrs"));
			logger.log(LogStatus.PASS, "enter email address");
			Thread.sleep(2000);
			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"Image", image1);



			// generics.Page_ScrollDown();
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"Image", image1);



			WebElement move_in_subtotal = driver.findElement(By.xpath("//div[@class='move-in-subtotal floatright big-bold fixed-col']"));
			WebElement admin_fee_amount = driver.findElement(By.xpath("//div[@class='admin-fee-amount floatright fixed-col']"));
			WebElement admin_fee_tax = driver.findElement(By.xpath("//div[@class='admin-fee-taxes floatright fixed-col']"));
			WebElement rent_tax = driver.findElement(By.xpath("//div[@class='move-in-rent-taxes floatright fixed-col']"));
			WebElement overall_total_move_in = driver.findElement(By.xpath("//div[@class='overall-total-move-in big-bold floatright fixed-col']"));



			double moveinsub = Generic_Class.getDoubleAmount(move_in_subtotal.getText());
			double movein_admin_fee_amount = Generic_Class.getDoubleAmount(admin_fee_amount.getText());
			double moveintotle = Generic_Class.getDoubleAmount(overall_total_move_in.getText());
			double movein_grandtotal_ui=moveinsub + movein_admin_fee_amount;

			Double db1 = new Double(moveintotle);
			Double db2 = new Double(movein_grandtotal_ui);

			if (db1.intValue() == db2.intValue()) {

				logger.log(LogStatus.PASS, "Total Cost displayed for Move In Cost is Maching");
				logger.log(LogStatus.INFO, "Move-In Cost column is displaying after adding SubTotal + Administrative Fee + Admin Tax + Rent tax ----"+movein_grandtotal_ui);

				logger.log(LogStatus.INFO, "Move-In Cost column is displaying in--Total Cost For This Space: "+moveintotle);
			} else {

				logger.log(LogStatus.FAIL, "Total Cost displayed for Move In Cost is not  Maching");

				logger.log(LogStatus.INFO, "Move-In Cost column is displaying after adding SubTotal + Administrative Fee + Admin Tax + Rent tax ----"+movein_grandtotal_ui);

				logger.log(LogStatus.INFO, "Move-In Cost column is displaying in--Total Cost For This Space: "+moveintotle);

			}



			WebElement monthly_rent_subtotal = driver.findElement(By.xpath("//div[@class='rent-subtotal floatright big-bold fixed-col']"));
			WebElement monthly_rent_grandtotal = driver.findElement(By.xpath("//div[@class='overall-total-rent big-bold floatright fixed-col']"));

			double monthly_rentsubtotal = Generic_Class.getDoubleAmount(monthly_rent_subtotal.getText());
			double overalltotalrent = Generic_Class.getDoubleAmount(monthly_rent_grandtotal.getText());
			double rent_grandtotal_ui=monthly_rentsubtotal;

			Double db3 = new Double(overalltotalrent);
			Double db4 = new Double(rent_grandtotal_ui);


			if (db3.intValue() == db4.intValue()) {

				logger.log(LogStatus.PASS, "Total Cost displayed for Monthly Rent  is Maching");
				logger.log(LogStatus.INFO, "Monthly rent column  is displaying after adding SubTotal +  Rent tax ----"+rent_grandtotal_ui);

				logger.log(LogStatus.INFO, "Monthly rent column is displaying in--Total Cost For This Space: "+overalltotalrent);
			} else {

				logger.log(LogStatus.FAIL, "Total Cost displayed for Monthly rent Cost is not  Maching");

				logger.log(LogStatus.INFO, "Monthly rent column is displaying after adding SubTotal + Rent tax ----"+rent_grandtotal_ui);

				logger.log(LogStatus.INFO, "Monthly rent column is displaying in--Total Cost For This Space: "+overalltotalrent);

			}

			Thread.sleep(5000);
			res.clk_CreateReservationButton();
			Thread.sleep(8000);

			CreateReservation_PopUp rserservationpopup = new CreateReservation_PopUp(driver);

			if (rserservationpopup.get_CreateResvPopUpHeader().equalsIgnoreCase("Create Reservation")) {

				String scpath2 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath2, true);
				String image2 = logger.addScreenCapture(scpath2);
				logger.log(LogStatus.PASS, "Create Reservation modal window displayed successfully "
						+ rserservationpopup.get_CreateResvPopUpHeader());
				logger.log(LogStatus.INFO, "Create Reservation modal window displayed successfully", image2);
			} else {

				String scpath12 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath12, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image12 = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Create Reservation modal window not  displayed successfully ");
				logger.log(LogStatus.INFO, "Create Reservation modal window not displayed successfully ", image12);
			}




			// Do not enter any EMP ID and click "Create Reservation" button
			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			Thread.sleep(5000);

			// Verify Information Msg "Please enter an employere number" Should
			// be displayed in red color.
			if (rserservationpopup.get_empErrorMessage().contains("Please enter an employee number.")) {

				String scpath11 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath11, true);
				String image11 = logger.addScreenCapture(scpath11);
				logger.log(LogStatus.PASS,
						"Error message verified successfully " + rserservationpopup.get_empErrorMessage());
				logger.log(LogStatus.INFO, "Error message verified successfully", image11);
			} else {

				String scpath22 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath22, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image22 = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Error message not verified successfully ");
				logger.log(LogStatus.INFO, "Error message not  verified successfully ", image22);
			}

			// Enter a invalid employere number
			rserservationpopup.enter_EmpID("1245687");
			logger.log(LogStatus.PASS, "employee no entered successfully ");

			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			Thread.sleep(4000);

			// Verify Information Msg "Please enter a valid employere number"
			// Should be displayed in red color.
			if (rserservationpopup.get_empErrorMessage().contains("Please enter a valid employee number.")) {
				String scpath23 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath23, true);
				String image23 = logger.addScreenCapture(scpath23);
				logger.log(LogStatus.PASS,
						"Error message verified successfully " + rserservationpopup.get_empErrorMessage());
				logger.log(LogStatus.INFO, "Error message verified successfully", image23);
			} else {

				String scpath32 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath32, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image32 = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Error message not verified successfully ");
				logger.log(LogStatus.INFO, "Error message not  verified successfully ", image32);
			}

			// Enter a valid employere number and click "Create Reservation"
			// button
			rserservationpopup.clear_EmpID();
			Thread.sleep(2000);
			rserservationpopup.enter_EmpID(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "employee no entered successfully ");
			Thread.sleep(2000);
			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"Image", image1);

			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			Thread.sleep(15000);




			// wait for home page

			String location = pmhomepage.getLocation();

			String sqlQuery2 = "select top 1 reservationid from Reservation "
					+ "where SiteID = (select siteid from site where sitenumber = '" + location + "')  and reservationstatustypeid = 127 "
					+ "Order by Reservationid desc";
			String reservationnum = DataBase_JDBC.executeSQLQuery(sqlQuery2);

			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='newcustomerpanel']"))));

			pmhomepage.enter_NameOrResvtn(reservationnum);
			logger.log(LogStatus.INFO, "Reservation Id entered successfully ");
			Thread.sleep(2000);
			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"Image", image1);

			pmhomepage.clk_findReservation();
			logger.log(LogStatus.INFO, "Clicked on find reservation button successfully ");

			Thread.sleep(8000);
			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"Image", image1);
			ViewReservationPage viewReservationPage = new ViewReservationPage(driver);

			String viewReservation = viewReservationPage.get_ViewReservationTitle();

			if (viewReservation.equalsIgnoreCase(tabledata.get("ViewReservationTitle"))) {
				String s = Generic_Class.takeScreenShotPath();
				String i = logger.addScreenCapture(s);
				logger.log(LogStatus.PASS, "View reservation page is displayed");
				logger.log(LogStatus.PASS, "View reservation page is displayed", i);
			}

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"Image", image1);

			viewReservationPage.clk_CancelBtn();
			logger.log(LogStatus.INFO, "Click on Cancel button successfully");
			Thread.sleep(8000);
			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"Image", image1);

			CancelReservationPage cancelPage = new CancelReservationPage(driver);
			String cancelTitle = cancelPage.verifyCancelReservationTitle();

			if (cancelTitle.equalsIgnoreCase(tabledata.get("CancelReservationTitle"))) {
				String c = Generic_Class.takeScreenShotPath();
				String m = logger.addScreenCapture(c);
				logger.log(LogStatus.PASS, "Cancel Reservation page is displayed");
				logger.log(LogStatus.PASS, "Cancel Reservation page is displayed", m);
			}

			if (cancelPage.verifyReasonListbox()) {
				logger.log(LogStatus.PASS, "Reason for Cancellation dropdown is displayed");
			}
			Thread.sleep(4000);

			cancelPage.clickReasonListbox();
			logger.log(LogStatus.INFO, "Click on Cancel for Reservation listbox successfully");
			Thread.sleep(2000);
			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"Image", image1);

			String[] reasonTypes = { "Not Storing", "Price", "Rented Elsewhere", "Unable to Contact",
					"Duplicate Reservation", "No Reason Given" };
			Actions dragger = new Actions(driver);
			WebElement draggablePartOfScrollbar = driver.findElement(By.xpath(
					"//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul//div[@class='ps-scrollbar-y-rail']//div[@class='ps-scrollbar-y']"));
			List<WebElement> reasons = driver.findElements(By.xpath(
					"//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul/li[@class='k-item']"));
			int i = 0;
			for (WebElement reason : reasons)

			{
				if (reason.getText().trim().equalsIgnoreCase(reasonTypes[i])) {

					logger.log(LogStatus.PASS, "Expected Result:" + reason.getText() + " and Actual Results :"
							+ reasonTypes[i] + " are same ");
					if (i >= 2) {
						dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0, 20).release()
						.build().perform();
						Thread.sleep(1000);
					}
				} else {
					logger.log(LogStatus.FAIL, "Expected Result:" + reason.getText() + " and Actual Results :"
							+ reasonTypes[i] + " are not same ");
					if (resultFlag.equals("pass"))
						resultFlag = "fail";
				}

				i++;
			}
			Thread.sleep(2000);

			cancelPage.clickConfirmCancel_btn();
			logger.log(LogStatus.INFO, "Clicking on Confirm Cancel button successfully");
			Thread.sleep(4000);

			if (cancelPage.verifyReasonListbox() && cancelPage.verifyError_EmployeeNum()) {
				String p = Generic_Class.takeScreenShotPath();
				String a = logger.addScreenCapture(p);
				logger.log(LogStatus.PASS, "Reason for Cancellation and Employee Id are mandatory fields");
				logger.log(LogStatus.PASS, "Reason for Cancellation and Employee Id are mandatory fields", a);
			}
			Thread.sleep(2000);

			cancelPage.clickCancel_btn();

			Thread.sleep(8000);

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);

			viewReservationPage.clk_CancelBtn();

			Thread.sleep(8000);

			cancelPage.clickReasonListbox();

			Thread.sleep(2000);

			String xpath = "//ul/li[text()='Price']";
			driver.findElement(By.xpath(xpath)).click();
			logger.log(LogStatus.INFO, "Select Cancel Reason from drop down list successfully");
			Thread.sleep(2000);


			cancelPage.enterNotes(tabledata.get("ReasonNotes"));
			logger.log(LogStatus.INFO, "enter the Reason Notes successfully");
			Thread.sleep(1000);

			cancelPage.enterEmployeeId(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "enter the Employee Number successfully");
			Thread.sleep(1000);

			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"Image", image1);

			cancelPage.clickConfirmCancel_btn();
			logger.log(LogStatus.INFO, "Clicking on Confirm Cancel button successfully");
			Thread.sleep(16000);

			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"Image", image1);

			pmhomepage = new PM_Homepage(driver);
			pmhomepage.enter_NameOrResvtn(reservationnum);
			logger.log(LogStatus.INFO, "Enter Reservation number");
			Thread.sleep(2000);
			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"Image", image1);

			pmhomepage.clk_findReservation();
			logger.log(LogStatus.INFO, "Click on Find Reservation button");
			Thread.sleep(18000);

			WebElement cancelledStatus = driver.findElement(By.xpath("//span[contains(text(),'Canceled')]"));

			if (cancelledStatus.isDisplayed()) {
				logger.log(LogStatus.PASS,
						"Reservation status is cancelled for" + reservationnum + " in Reservations Grid ");
				String t = Generic_Class.takeScreenShotPath();
				String g = logger.addScreenCapture(t);
				logger.log(LogStatus.PASS, "Status is cancelled for the reservation");
				logger.log(LogStatus.PASS, "Status is cancelled for the reservation", g);

			} else {
				String h = Generic_Class.takeScreenShotPath();
				String e = logger.addScreenCapture(h);
				logger.log(LogStatus.FAIL, "Status is not cancelled for the reservation");
				logger.log(LogStatus.FAIL, "Status is not cancelled for the reservation", e);
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
			Reporter.log("Exception ex: " + ex, true);
			logger.log(LogStatus.FAIL, "Test Script fail due to exception: " + ex);
		}

	}

}
