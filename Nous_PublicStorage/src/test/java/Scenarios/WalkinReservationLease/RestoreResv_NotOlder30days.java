package Scenarios.WalkinReservationLease;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import Pages.Walkin_Reservation_Lease.RestoreReservation;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import Scenarios.Browser_Factory;

public class RestoreResv_NotOlder30days extends Browser_Factory {

	public ExtentTest logger;
	String path = "./src/main/resources/Resources/PS_TestData.xlsx";
	String resultFlag = "pass";
	String screen;
	String im;
	WebDriverWait wait;

	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "RestoreResv_NotOlder30days", "Status",
					"Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "RestoreResv_NotOlder30days", "Status",
					"Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "RestoreResv_NotOlder30days", "Status",
					"Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);
	}

	@DataProvider
	public Object[][] getData() {
		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"RestoreResv_NotOlder30days");
	}

	@Test(dataProvider = "getData")
	public void restoreRes_NotOld30Days(Hashtable<String, String> tabledata) throws InterruptedException {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		try {

			// Login to PS Application
			logger = extent.startTest("RestoreResv_NotOlder30days",
					"Restore a reservation which is cancelled in the last 30 days");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " + testcaseName, true);
			
			String sqlQuery1 =  "select top 1 r.reservationid,r.siteid from reservation r  "
					+ "join reservationitem ri on ri.reservationid=r.reservationid "
					+ "and ri.itemtypeid=865  "
					+ "CROSS APPLY [dbo].[udf_AvailableVacanctUnitCountForProductSite](ri.itemid) b where reservationstatustypeid=4342  "
					+ "And b.vacantunitcount>0 and r.recorddatetime > getdate()-29  "
					+ "and ISNULL(isprereservation,0)<>1  "
					+ "and ISNULL(isrestored, 0)<>1  "
					+ "and ISNULL(ismoveinextended,0)<>1";
					
			
			List<String> DB_ReservationNo = DataBase_JDBC.executeSQLQuery_List(sqlQuery1);
			
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
			
			String clearsiteidfetched = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"
					+ DB_ReservationNo.get(1) + "'";
			DataBase_JDBC.executeSQLQuery(allocateemtyip);

			String allocateip_to_newid = "Update siteparameter set paramvalue='" + ipadd
					+ "' where paramcode='IP_COMPUTER_FIRST' and siteid='" + DB_ReservationNo.get(1) + "'";
			DataBase_JDBC.executeSQLQuery(allocateip_to_newid);
			Thread.sleep(3000);

			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");

			Robot robot = new Robot();
			wait = new WebDriverWait(driver, 90);
			JavascriptExecutor jse = (JavascriptExecutor) driver;

			// ======== Handling customer facing device start =============

		/*String biforstNum = driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//h2/b")).getText();
			Reporter.log(biforstNum + "", true);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(3000);

			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));

			By biForstContainer = By.xpath("//div[a[contains(@class,'bifrost-host-container')]]");
			wait.until(ExpectedConditions.visibilityOfElementLocated(biForstContainer));

			List<WebElement> biforstSystem = driver.findElements(By.xpath(
					"//div[a[contains(@class,'bifrost-host-container')]]//span[contains(@class,'bifrost-label')]"));

			for (WebElement ele : biforstSystem) {
				if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
					Reporter.log(ele.getText() + "", true);
					jse.executeScript("arguments[0].scrollIntoView(true);", ele);
					ele.click();
					break;
				}
			}

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(3000);

			driver.switchTo().window(tabs.get(0));
			WebElement connectionDialogBox = driver.findElement(By.xpath(
					"//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']"));
			wait.until(ExpectedConditions.visibilityOf(connectionDialogBox)).click();
			*/
			
			Dashboard_BifrostHostPopUp Bifrostpop1 = new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			try {
				Bifrostpop1.clickContiDevice();
			} catch (Exception e) {
				System.out.println("Pop up is not avalable, so continuing!!");
			}
			Thread.sleep(5000);

			// ======== Handling customer facing device end =============

			// Verify User should view the "Walk-in-Customer" module
			PM_Homepage pmhomepage = new PM_Homepage(driver);
			logger.log(LogStatus.INFO, "PM Home Page object created");

			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);

			if (pmhomepage.get_WlkInCustText().equals("Walk-In Customer")) {
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"Walk-In Customer module is displayed successfully:" + pmhomepage.get_WlkInCustText());
				logger.log(LogStatus.INFO, "Walk-In Customer module is displayed successfully", image);
			} else {
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Walk-In Customer module is not displayed");
				logger.log(LogStatus.INFO, "Walk-In Customer module is not displayed", image);
			}

			String location = pmhomepage.getLocation();
			Thread.sleep(5000);
			
			String sqlQuery = "select siteid from site where sitenumber = "+location;
			String siteId = DataBase_JDBC.executeSQLQuery(sqlQuery);

			
			
			
			//String DB_ReservationNo = DataBase_JDBC.executeSQLQuery(sqlQuery);
			Reporter.log("Reservation ID is:   " + DB_ReservationNo.get(0), true);

			pmhomepage.enter_NameOrResvtn(DB_ReservationNo.get(0));
			logger.log(LogStatus.INFO, "Reservation Id entered successfully");
			Thread.sleep(3000);

			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "Entered Account Number" + DB_ReservationNo.get(0) + "successfully", im);

			pmhomepage.clk_findReservation();
			logger.log(LogStatus.INFO, "Clicked on find reservation button successfully ");
			Thread.sleep(15000);

			ViewReservationPage viewRes = new ViewReservationPage(driver);
			boolean cancelResHdrExists = viewRes.IsCancelledResDisplayed();

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			if (cancelResHdrExists == true) {
				logger.log(LogStatus.PASS, "Cancelled Reservation page displayed successfully");
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				logger.log(LogStatus.FAIL, "Cancelled Reservation page not displayed");
				logger.log(LogStatus.FAIL, "Image", image);
			}

			logger.log(LogStatus.INFO, "Verifying UI details against DB");

			// Verify the reservation details
			sqlQuery = "select firstname+' '+lastname from reservation where reservationid = '" + DB_ReservationNo.get(0)
					+ "'";
			String customerNameDB = DataBase_JDBC.executeSQLQuery(sqlQuery);
			logger.log(LogStatus.INFO, "Customer Name in DB is:--->  " + customerNameDB);

			String getReservationInfo = viewRes.getResInfo();
			logger.log(LogStatus.INFO, "Reservation Info on UI is:--->  " + getReservationInfo);

			String getCustomerName = viewRes.getCustomerName();
			logger.log(LogStatus.INFO, "Customer Name on UI is:--->  " + getCustomerName);

			if ((getReservationInfo.contains("Reservation Number: " + DB_ReservationNo.get(0)))
					&& getCustomerName.equalsIgnoreCase(customerNameDB)) {
				logger.log(LogStatus.PASS, "Reservation details matched. Reservation Number: " + DB_ReservationNo.get(0)
						+ "  Customer Name: " + customerNameDB);
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				logger.log(LogStatus.FAIL, "Reservation details not matched");
				logger.log(LogStatus.FAIL, "Image", image);
				logger.log(LogStatus.INFO, "Expected Values. Reservation Number: " + DB_ReservationNo.get(0)
						+ "  Customer Name: " + customerNameDB);
				logger.log(LogStatus.INFO, "Actual Values. Reservation Number: " + getReservationInfo
						+ "  Customer Name: " + getCustomerName);
			}

			// Verify that "Notes" section is displayed on the screen.
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			if (driver.findElements(By.xpath("//form[@id='reservationForm']//a[@id='notes-add']/span[text()]"))
					.size() != 0) {
				logger.log(LogStatus.PASS, "Note button present under reservation details");
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				logger.log(LogStatus.FAIL, "Note button not present under reservation details");
				logger.log(LogStatus.FAIL, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			// Verify that below buttons are displayed on the screen:
			// Back to Dashboard

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight);");
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			if (viewRes.backToDashboard_IsDisplayed() == true && viewRes.restoreReservation_IsDisplayed() == true) {
				logger.log(LogStatus.PASS, "Back to Dashboard & Restore Reservation buttons displayed");
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				logger.log(LogStatus.FAIL, "Back to Dashboard & Restore Reservation buttons not displayed");
				logger.log(LogStatus.FAIL, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			// Verify that "Edit" button is not available in the "Customer
			// Information" section when reservation is in "Cancelled" status
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-2000)");
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			if (!driver.findElement(By.xpath("//form[@id='reservationForm']//a[contains(text(),'Edit')]"))
					.isDisplayed()) {
				logger.log(LogStatus.PASS,
						"Edit button is not available in the Customer Information section as expected");
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				logger.log(LogStatus.FAIL,
						"Edit button is available in the Customer Information section which is not expected");
				logger.log(LogStatus.FAIL, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			// Click on "Restore Reservation" button
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight);");

			viewRes.clickRestoreResv();
			Thread.sleep(5000);

			RestoreReservation restoreRes = new RestoreReservation(driver);
			String getText = restoreRes.getText_restoreRes();
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			if (getText.contains("The reservation will be restored.")) {
				logger.log(LogStatus.PASS, "Restore Reservation confirm message displayed");
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				logger.log(LogStatus.FAIL, "Restore Reservation confirm message not displayed");
				logger.log(LogStatus.FAIL, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			if (getText.contains("Size(s):") & getText.contains("Employee ID:")) {
				logger.log(LogStatus.PASS, "Size & Employee Id displayed in Restore Reservation window");
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				logger.log(LogStatus.FAIL, "Size & Employee Id not displayed in Restore Reservation window");
				logger.log(LogStatus.FAIL, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			if (restoreRes.isCancelBtnDisplayed() & restoreRes.isYesBtnDisplayed()) {
				logger.log(LogStatus.PASS, "Cancel and Yes buttons displayed in Restore Reservation window");
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				logger.log(LogStatus.FAIL, "Cancel and Yes buttons not displayed in Restore Reservation window");
				logger.log(LogStatus.FAIL, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			if (restoreRes.isDisplayed_EmpNUm()) {
				logger.log(LogStatus.PASS, "Employee Id field displayed in Restore Reservation window");
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				logger.log(LogStatus.FAIL, "Employee Id field not displayed in Restore Reservation window");
				logger.log(LogStatus.FAIL, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			restoreRes.click_Yes();
			Thread.sleep(5000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			getText = restoreRes.getText_restoreRes();
			if (getText.contains("Please enter an employee number")) {
				logger.log(LogStatus.PASS, "Employee Id is a mandatory field");
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				logger.log(LogStatus.FAIL, "Employee Id is not a mandatory field");
				logger.log(LogStatus.FAIL, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			// Click on "Cancel" button
			restoreRes.click_Cancel();
			Thread.sleep(5000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			if (restoreRes.isDisplayed_RestoreRes() == false) {
				logger.log(LogStatus.PASS, "After clicking on cancel button, restore reservation window disappeared");
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				logger.log(LogStatus.FAIL,
						"After clicking on cancel button, restore reservation window not disappeared");
				logger.log(LogStatus.FAIL, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			// Click on "Restore Reservation" button
			// ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,2000)");
			viewRes.clickRestoreResv();
			Thread.sleep(5000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			if (restoreRes.isDisplayed_RestoreRes() == true) {
				logger.log(LogStatus.PASS, "Restore Reservation window opened");
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				logger.log(LogStatus.FAIL,
						"Restore Reservation window not opened after clicking on Restore Reservation button");
				logger.log(LogStatus.FAIL, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			// Click on "Yes" button
			Thread.sleep(2000);
			restoreRes.enter_EmployeeId(tabledata.get("UserName"));
			Thread.sleep(2000);
			restoreRes.click_Yes();
			Thread.sleep(25000);

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);

			if (pmhomepage.heading_WalkInCustomer_Exists()) {
				logger.log(LogStatus.PASS,
						"After clicking on Yes button in restore reservation window, app navigated to PM home page");
				logger.log(LogStatus.PASS, "Image", image);
			} else {
				logger.log(LogStatus.FAIL,
						"After clicking on Yes button in restore reservation window, app did not navigated to PM home page");
				logger.log(LogStatus.FAIL, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			sqlQuery = "select reservationstatustypeid from reservation where reservationid = '" + DB_ReservationNo.get(0)
					+ "'";

			logger.log(LogStatus.INFO, "Started validating DB");
			String resStatus = DataBase_JDBC.executeSQLQuery(sqlQuery);

			if (!resStatus.equals("4342")) {
				logger.log(LogStatus.INFO, "DB Validation Passed for reservation id :  " + DB_ReservationNo.get(0));
				logger.log(LogStatus.PASS,
						"Reservation status changed in the DB after restore. Now the status is -- Reservation Status:"
								+ resStatus);
			} else {
				logger.log(LogStatus.INFO, "DB Validation Failed for reservation id :  " + DB_ReservationNo.get(0));
				logger.log(LogStatus.FAIL,
						"Reservation status did not changed in the DB after restore. Now the status is -- Reservation Status:"
								+ resStatus);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}
			
			
			logger.log(LogStatus.INFO, "Navigating to the View Reservation page after restore");
			pmhomepage.enter_NameOrResvtn(DB_ReservationNo.get(0));
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image", image);
			pmhomepage.clk_findReservation();
			Thread.sleep(15000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image", image);
			

			viewRes = new ViewReservationPage(driver);
			String status = viewRes.getReservationStatus();
			if(status.equals("CONFIRMED")){
				logger.log(LogStatus.PASS, "Reservation status is Confirmed as expected after the restore");
			}else{
				logger.log(LogStatus.PASS, "Reservation status is not Confirmed after the restore");
				resultFlag = "fail";
			}
			
			logger.log(LogStatus.INFO, "Verifying whether we would be able to select a different move-in date within 7 days");
			viewRes.clk_EditBtn();
			Thread.sleep(5000);
			SelectDateFromCalendar("6");
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			logger.log(LogStatus.PASS, "Selected a different move-in date");
			
			viewRes.clk_ApplyBtn();
			logger.log(LogStatus.INFO, "Clicked on Apply button");
			Thread.sleep(15000);
			try {
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(8000);
			} catch (Exception e) {
				Reporter.log("No Javascript Popup, so Continuing!!!");
			}
			
			
			String getUIDate = viewRes.getMoveInDate();
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 6);
			dt = c.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String enteredDate= sdf.format(dt);
			
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			if(enteredDate.equals(getUIDate)){
				logger.log(LogStatus.PASS, "Move-In Date correctly updated in UI with the entered value. Move-In Date: "+getUIDate);
			}else{
				logger.log(LogStatus.FAIL, "Move-In Date not correctly updated in UI with the entered value.");
				resultFlag = "fail";
			}
			
			Thread.sleep(2000);
			
			String phonecallid_data = "select top 2 t.name  from phonecall pc join type t on t.typeid=pc.calltypeid "
					+ "where pc.OwnerTableItemID ='" + DB_ReservationNo.get(0) + "' " 
					+ "Order by PhonecallID  desc";

			List<String> calltype = DataBase_JDBC.executeSQLQuery_List(phonecallid_data);
			
			if (calltype.get(0).equalsIgnoreCase("Missed Reservation") || calltype.get(1).equalsIgnoreCase("Reminder Call")) {
			
				
				logger.log(LogStatus.PASS, "reservation Calls are rescheduled--"+calltype.get(0)+"-and -"+calltype.get(1));
			
			} else {
				
				
			
				logger.log(LogStatus.FAIL, "reservation Calls are not rescheduled--"+calltype.get(0)+"-and -"+calltype.get(1));
				
			}
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception " + ex);
		}

	}
	
	public void SelectDateFromCalendar(String data) {
		try {
			int value = Integer.parseInt(data);
			Calendar c = GregorianCalendar.getInstance();
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, value);
			SimpleDateFormat df = new SimpleDateFormat("dd", Locale.getDefault());
			String strTodaysDate = df.format(cal.getTime());
			strTodaysDate = StringUtils.stripStart(strTodaysDate, "0");
			Reporter.log("strTodaysDate: " + strTodaysDate, true);
			Thread.sleep(2000);
			Calendar mCalendar = Calendar.getInstance();
			
			 driver.findElement(By.xpath("//div[@class='move-in-date-edit clearfix-container toggle']//span[@class='dual-datepicker-control']")).click();
             Thread.sleep(2000);

			String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			mCalendar.add(Calendar.MONTH, 1);
			String nmonth = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			String ExpDay = strTodaysDate;

			String ActMonth = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[1]")).getText();
			String ActNextMonth = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[2]")).getText();
			if (ActMonth.equals(month) && ActNextMonth.equals(ActNextMonth)) {
				List<WebElement> AvlDays = driver.findElements(By.xpath("//td[@data-handler='selectDay']"));
				for (int i = 0; i < AvlDays.size(); i++) {
					String AvailableDay = AvlDays.get(i).getText();
					if (AvailableDay.equals(ExpDay)) {
						AvlDays.get(i).click();
						break;
					}
				}
			}

		} catch (Exception e) {
			Reporter.log("Exception:dateValidaitons" + e, true);
		}
	}
}
