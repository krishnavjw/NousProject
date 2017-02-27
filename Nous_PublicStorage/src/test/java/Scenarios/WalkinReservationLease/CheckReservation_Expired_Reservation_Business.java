package Scenarios.WalkinReservationLease;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
import Pages.Walkin_Reservation_Lease.CreateReservation;
import Pages.Walkin_Reservation_Lease.CreateReservation_PopUp;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Scenarios.Browser_Factory;

public class CheckReservation_Expired_Reservation_Business extends Browser_Factory {

	String locationnum = null;
	public ExtentTest logger;
	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "Pass";

	@AfterMethod
	public void afterMethod() {

		System.out.println(" In After Method");
		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"CheckReservation_Expired_Reservation_Business", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Leasing for Customer verification", image);
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"CheckReservation_Expired_Reservation_Business", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"CheckReservation_Expired_Reservation_Business", "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

	@Test(dataProvider = "getLoginData")
	public void CheckReservation_Expired_Reservation_Business(Hashtable<String, String> tabledata) throws Exception {
		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}
		
		Thread.sleep(5000);
		try {
			logger = extent.startTest("CheckReservation_Expired_Reservation_Business","CheckReservation_Expired_Reservation_Business");

			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(10000);

			Robot robot = new Robot();
			WebDriverWait wait = new WebDriverWait(driver, 40);
			JavascriptExecutor jse = (JavascriptExecutor) driver;

			// ======== Handling customer facing device start =============

			String biforstNum = driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//h2/b")).getText();
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

			

			// -----------------------Clicking on Lease and space button--------------------------------
			Thread.sleep(2000);
			PM_Homepage homepage = new PM_Homepage(driver);
			logger.log(LogStatus.INFO, "Home Page object is created successfully");

			String location = homepage.getLocation();
			Thread.sleep(6000);

			homepage.clk_findAndLeaseSpace();
			logger.log(LogStatus.PASS, "Clicked on Find And Lease A Space");
			Reporter.log("Clicked on Find And Lease A Space", true);
			Thread.sleep(8000);

			// --------Selecting space and click on search button-------------
			StandardStoragePage stdstoragepage = new StandardStoragePage(driver);

			try {
				stdstoragepage.Clk_OnAvlSpace();
			} catch (Exception e) {
				Reporter.log("None of the spaces are available");
			}

			logger.log(LogStatus.PASS, "click on multiple spaces ");
			Thread.sleep(5000);
			stdstoragepage.click_Search();
			logger.log(LogStatus.PASS, "Clicked on Search button----");
			Reporter.log("Clicked on Search button again ", true);
			Thread.sleep(8000);

			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			js1.executeScript("window.scrollBy(0,400)", "");
			Thread.sleep(5000);

			List<WebElement> norows = driver.findElements(By.xpath("//div[@id='onsiteUnitGrid']//table//tbody//tr"));
			String space = "";
			if (norows.size() > 0) {
				space = driver
						.findElement(By
								.xpath("//div[@id='onsiteUnitGrid']//table/tbody/tr[1]//td[@class='grid-cell-space']"))
						.getText();
				Reporter.log("space number is:" + space, true);
			} else {

				logger.log(LogStatus.INFO, "Application is not populating any data/space details");

			}

			WebElement RdBtn_Space = driver.findElement(By
					.xpath("//td[@class='grid-cell-space'][text()='" + space + "']/../td/input[@name='selectedIds']"));
			logger.log(LogStatus.PASS,
					"check the radio button based on the space and click on the  reservation button");

			jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space);
			Thread.sleep(5000);
			jse.executeScript("arguments[0].click();", RdBtn_Space);

			Thread.sleep(2000);
			SpaceDashboard_ThisLoc thisloc_createreservation = new SpaceDashboard_ThisLoc(driver);
			thisloc_createreservation.click_Reserve();
			logger.log(LogStatus.PASS, "Clicked on Reserve button");
			Reporter.log("Clicked on Reserve button", true);
			Thread.sleep(20000);

			// --- Fill data in Create Reservation Page and Click on Create Reservation button-------------
			CreateReservation CreateReservation = new CreateReservation(driver);

			driver.findElement(By.xpath("//div[@id='expectedMoveInDate']//span[text()='SELECT']")).click();
			Thread.sleep(4000);
			CreateReservation.SelectDateFromCalendar(tabledata.get("MoveInDate"));
			logger.log(LogStatus.PASS, "MoveIn date is selected in calendar "+tabledata.get("MoveInDate"));
			Thread.sleep(2000);

			String FirstName = "Nous" + Generic_Class.get_RandmString();
			Thread.sleep(1000);
			CreateReservation.enter_FirstName(FirstName);
			
			String LastName = "Automation" + Generic_Class.get_RandmString();
			Thread.sleep(1000);
			CreateReservation.enter_LastName(LastName);
		
			Thread.sleep(1000);
			CreateReservation.sel_DropDownValFromPhNo(tabledata.get("Phone"));
			logger.log(LogStatus.PASS, "Phone type is selected in CreateReservation Page");
			
			Thread.sleep(2000);
			CreateReservation.enter_PhoneAreaCode(tabledata.get("PhoneAreacode"));
			
			Thread.sleep(1000);
			CreateReservation.enter_PhoneExchnge(tabledata.get("PhoneExchnge"));
		
			Thread.sleep(1000);
			CreateReservation.enter_PhoneLineNumber(tabledata.get("PhoneLneNo"));
			
			Thread.sleep(1000);
			CreateReservation.enter_EmailAddr(tabledata.get("Emailaddrs"));
			
		
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)", "");

			// Click On Reserve button
			CreateReservation.clk_CreateReservationButton();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");

			CreateReservation_PopUp rserservationpopup = new CreateReservation_PopUp(driver);
			Thread.sleep(3000);
			rserservationpopup.enter_EmpID(tabledata.get("UserName"));
			

			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			Thread.sleep(12000);

			String sqlQuery = "select top 1 reservationid from reservation "
					+ "where SiteID = (select siteid from site where sitenumber = '" + location + "') "
					+ "and FirstName='" + FirstName + "' and LastName='" + LastName + "' "
					+ " Order by  LastUpdate DESC";
			String reservationId = DataBase_JDBC.executeSQLQuery(sqlQuery);
			logger.log(LogStatus.INFO, "Fetching Reservation Number:" + reservationId + " from DB");

			String phonecallid_data = "select top 1 pc.phonecallid  from phonecall pc join type t on t.typeid=pc.calltypeid "
					+ "where pc.OwnerTableItemID ='" + reservationId
					+ "' and t.name='Missed Reservation'  Order by PhonecallID  desc";

			String phonecellid = DataBase_JDBC.executeSQLQuery(phonecallid_data);
			
			String change_res_curdate = "update phonecall set Scheduledcalldatetime=getutcdate()-2 where phonecallid='"
					+ phonecellid + "'";
			DataBase_JDBC.executeSQLQuery(change_res_curdate);

			// DB Verification
			String sqlQuery2 = "select ScheduledCallDateTime  from phonecall pc join type t on t.typeid=pc.calltypeid "
					+ "where pc.OwnerTableItemID ='" + reservationId + "' " + "and calltypeid = 4316 "
					+ "Order by  PhonecallID desc";
			String scheduledDate = DataBase_JDBC.executeSQLQuery(sqlQuery2);
			System.out.println(scheduledDate);
			String[] actualDate = scheduledDate.split(" ");

			String sqlQuery3 = "SELECT GETUTCDATE()-2 as date";
			String expected = DataBase_JDBC.executeSQLQuery(sqlQuery3);
			System.out.println(expected);
			String[] expectedDate = expected.split(" ");

			if (actualDate[0].contains(expectedDate[0])) {
				resultFlag = "pass";
				logger.log(LogStatus.PASS, "DB Validation Successfull");
				logger.log(LogStatus.INFO, "DB Validation Successfull" + "  Scheduled Date is:  " + actualDate[0]
						+ "  Expected Date is:  " + expectedDate[0]);
			} else {
				resultFlag = "fail";
				logger.log(LogStatus.FAIL, "DB Validation is UnSuccessfull");
				logger.log(LogStatus.INFO, "DB Validation is UnSuccessfull" + "  Scheduled Date is:  " + actualDate[0]
						+ "  Expected Date is:  " + expectedDate[0]);
			}

			String sqlQuery4 = "select StatusTypeID from ReservationItem " + "where Reservationid='" + reservationId
					+ "'";
			String expectedStatus = DataBase_JDBC.executeSQLQuery(sqlQuery4);

			if (Integer.parseInt(expectedStatus) == 4317) {
				resultFlag = "pass";
				logger.log(LogStatus.PASS, "Expected Status is matching");
			} else {
				resultFlag = "fail";
				logger.log(LogStatus.FAIL, "Expected Status is not matching");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			// In the catch block, set the variable resultFlag to “fail”
			resultFlag = "fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception " + ex);
		}

	}

	@DataProvider
	public Object[][] getLoginData() {
		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"CheckReservation_Expired_Reservation_Business");
	}

	public void Verify_text(String rn, String text) {
		if (rn.equalsIgnoreCase(text)) {
			logger.log(LogStatus.PASS, text + " in date is displayed");
		} else {
			logger.log(LogStatus.FAIL, text + " in date  is not displayed");
		}

	}

}
