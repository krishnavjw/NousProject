package Scenarios.WalkinReservationLease;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import Pages.Walkin_Reservation_Lease.CreateReservation_PopUp;
import Pages.Walkin_Reservation_Lease.CreateReservation;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import Scenarios.Browser_Factory;

public class ExtendReservation_IndividualNewCustomer extends Browser_Factory {

	public ExtentTest logger;
	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "Pass";

	@AfterMethod
	public void afterMethod() {

		System.out.println(" In After Method");
		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "ExtendReservation_IndividualNewCustomer",
					"Status", "Pass");

		} else if (resultFlag.equals("fail")) {
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Customer stops by to extend reservation without deposit", image);
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "ExtendReservation_IndividualNewCustomer",
					"Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "ExtendReservation_IndividualNewCustomer",
					"Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);
	}

	@Test(dataProvider = "getLoginData")
	public void ExtendReservation_IndividualNewCustomer(Hashtable<String, String> tabledata) throws Exception {
		logger = extent.startTest("ExtendReservation_IndividualNewCustomer", "Reservation");
		testcaseName = tabledata.get("TestCases");
		Reporter.log("Test case started: " + testcaseName, true);

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			logger.log(LogStatus.SKIP, "ExtendReservation_IndividualNewCustomer is Skipped");
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);

		try {
			
			// login into application with Customer device connect
			LoginPage login = new LoginPage(driver);
			String siteNumber = login.get_SiteNumber();
			
			login.enterUserName(tabledata.get("UserName"));
			login.enterPassword(tabledata.get("Password"));
			login.clickLogin();
			
			Reporter.log("Login Successfully", true);
			Reporter.log("Customer window Navigation", true);
			
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

			// ======== Handling customer facing device end =============

			PM_Homepage PM_HomePage = new PM_Homepage(driver);
			Thread.sleep(5000);
			PM_HomePage.clk_findAndLeaseSpace();
			logger.log(LogStatus.PASS, "Clicked on Find And Lease A Space");
			Reporter.log("Clicked on Find And Lease A Space", true);

			StandardStoragePage stdpage = new StandardStoragePage(driver);
			try {
				stdpage.Clk_OnAvlSpace();
			} catch (Exception e) {
				Reporter.log("None of the spaces are available");
			}
			logger.log(LogStatus.PASS, "Clicked on available space checkbox in StandardStorage");
			Reporter.log("Clicked on available space checkbox in StandardStorage", true);
			Thread.sleep(5000);
			stdpage.click_Search();
			logger.log(LogStatus.PASS, "click on the search button");
			Thread.sleep(5000);
			// ============Fetching space number and based on that clicking the
			// radio button========================
			List<WebElement> norows = driver
					.findElements(By.xpath("//form[@id='frmReserveUnits']//div//table/tbody/tr"));
			String space = null;
			if (norows.size() > 0) {
				Thread.sleep(5000);

				space = driver.findElement(By.xpath("//form[@id='frmReserveUnits']//div//table/tbody/tr[1]/td[4]"))
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

			logger.log(LogStatus.PASS, "Clicked on check box of a space in this location: " + space);
			Reporter.log("Clicked on check box of a space in this location: " + space, true);

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			SpaceDashboard_ThisLoc SpaceDashboard_ThisLoc = new SpaceDashboard_ThisLoc(driver);
			Thread.sleep(5000);
			SpaceDashboard_ThisLoc.click_Reserve();
			logger.log(LogStatus.PASS, "Clicked on Reserve button");
			Reporter.log("Clicked on Reserve button", true);

			CreateReservation CreateReservation = new CreateReservation(driver);
			Thread.sleep(3000);
			jse.executeScript("arguments[0].scrollIntoView()", CreateReservation.btnMoveIndate());
			Thread.sleep(5000);
			jse.executeScript("arguments[0].click();", CreateReservation.btnMoveIndate());
			logger.log(LogStatus.PASS, "Clicked on MoveIn Date button");
			Reporter.log("Clicked on MoveIn Date button", true);
			Thread.sleep(5000);
			CreateReservation.SelectDateFromCalendar(tabledata.get("MoveInDate"));
			logger.log(LogStatus.PASS, "MoveIn date is selected in calendar");
			Reporter.log("MoveIn date is selected in calendar", true);

			Thread.sleep(5000);
			String FN = "AU" + Generic_Class.get_RandmString();
			Thread.sleep(5000);
			System.out.println("First Name: " + FN);
			CreateReservation.enter_FirstName(FN);
			logger.log(LogStatus.PASS, "FirstName is entered in CreateReservation Page");
			Reporter.log("FirstName is entered in CreateReservation Page", true);
			Thread.sleep(5000);
			CreateReservation.enter_LastName(tabledata.get("LastName"));
			logger.log(LogStatus.PASS, "LastName is entered in CreateReservation Page");
			Reporter.log("LastName is entered in CreateReservation Page", true);
			Thread.sleep(5000);
			CreateReservation.sel_DropDownValFromPhNo(tabledata.get("Phone"));
			logger.log(LogStatus.PASS, "Phone type is selected in CreateReservation Page");
			Reporter.log("Phone type is selected in CreateReservation Page", true);
			Thread.sleep(5000);
			CreateReservation.enter_PhoneAreaCode(tabledata.get("PhoneAreacode"));
			Thread.sleep(5000);
			CreateReservation.enter_PhoneExchnge(tabledata.get("PhoneExchnge"));
			Thread.sleep(5000);
			CreateReservation.enter_PhoneLineNumber(tabledata.get("PhoneLneNo"));
			Thread.sleep(5000);
			logger.log(LogStatus.PASS, "Phone number is entered in CreateReservation Page");
			Reporter.log("Phone number is entered in CreateReservation Page", true);
			CreateReservation.enter_EmailAddr(tabledata.get("Emailaddrs"));
			logger.log(LogStatus.PASS, "Email is entered in CreateReservation Page");
			Reporter.log("Email is entered in CreateReservation Page", true);
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			CreateReservation.clk_CreateReservationButton();
			logger.log(LogStatus.PASS, "Clicked on Create Reservation in CreateReservation page");
			Reporter.log("Clicked on Create Reservation in CreateReservation page", true);

			CreateReservation_PopUp CreateReserVation_PopUp = new CreateReservation_PopUp(driver);
			Thread.sleep(5000);
			CreateReserVation_PopUp.enter_NotesTextArea(tabledata.get("Notes"));
			logger.log(LogStatus.PASS, "Entered notes in CreateReservation popup");
			Reporter.log("Entered notes in CreateReservation popup", true);
			Thread.sleep(5000);
			CreateReserVation_PopUp.enter_EmpID(tabledata.get("EmpID"));
			logger.log(LogStatus.PASS, "Entered Employee ID in CreateReservation popup");
			Reporter.log("Entered Employee ID in CreateReservation popup", true);
			Thread.sleep(5000);
			CreateReserVation_PopUp.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Clicked on CreateReservation in CreateReservation popup");
			Reporter.log("Clicked on CreateReservation in CreateReservation popup", true);
			Thread.sleep(15000);

			String sqlQuery = "select top 1 reservationid from reservation"
					+ " where SiteID = (select siteid from site where sitenumber = '" + siteNumber + "')"
					+ "  and firstname = '" + FN + "'" + " and reservationstatustypeid = '127'"
					+ " order by lastupdate desc";
			
			System.out.println(sqlQuery);
			String DB_ReservationNo = DataBase_JDBC.executeSQLQuery(sqlQuery);
			Thread.sleep(8000);
			System.out.println("Reservation Number" + DB_ReservationNo);
			logger.log(LogStatus.PASS, "Reservation Number: " + DB_ReservationNo);
			PM_HomePage.enter_NameOrResvtn(DB_ReservationNo);
			Thread.sleep(2000);
			PM_HomePage.clk_findReservation();
			Thread.sleep(6000);

			ViewReservationPage viewReservationPage = new ViewReservationPage(driver);
			String viewReservation = viewReservationPage.get_ViewReservationTitle();

			if (viewReservation.equalsIgnoreCase(tabledata.get("ViewReservationTitle"))) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "View reservation page is displayed");
				logger.log(LogStatus.PASS, "View reservation page is displayed", image);
			}

			if (viewReservationPage.verifyEditBtn() & viewReservationPage.verifyNoteBtn()) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit and Note Buttons are displayed in View Reservation Page");
				logger.log(LogStatus.INFO, "Edit and Note Buttons are displayed in View Reservation Page", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit and Note Buttons are not displayed in View Reservation Page");
				logger.log(LogStatus.FAIL, "Edit and Note Buttons are not displayed in View Reservation Page", image);
			}

			viewReservationPage.clk_EditBtn();
			Thread.sleep(3000);

			if (viewReservationPage.verifyCancelBtn() & viewReservationPage.verifyApplyBtn()) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Cancel and Verify Buttons are displayed in View Reservation Page");
				logger.log(LogStatus.INFO, "Cancel and Verify Buttons are displayed in View Reservation Page", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Cancel and Verify Buttons are not displayed in View Reservation Page");
				logger.log(LogStatus.FAIL, "Cancel and Verify Buttons are not displayed in View Reservation Page",
						image);
			}

			if (viewReservationPage.verifyFirstNameField()) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Fields are editable in View Reservation Page");
				logger.log(LogStatus.INFO, "Fields are editable in View Reservation Page", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Fields are not editable in View Reservation Page");
				logger.log(LogStatus.FAIL, "Fields are not editable in View Reservation Page", image);
			}

			// jse.executeScript("arguments[0].scrollIntoView()",
			// viewReservationPage.btnMoveIndate());
			Thread.sleep(5000);
			jse.executeScript("arguments[0].click();", viewReservationPage.btnMoveIndate());
			logger.log(LogStatus.PASS, "Clicked on MoveIn Date button");
			Reporter.log("Clicked on MoveIn Date button", true);
			Thread.sleep(5000);

			try {
				int value = Integer.parseInt(tabledata.get("MoveInDateEdit"));
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

				String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
				mCalendar.add(Calendar.MONTH, 1);
				String nmonth = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
				String ExpDay = strTodaysDate;

				String ActMonth = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[1]")).getText();
				String ActNextMonth = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[2]"))
						.getText();
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

			logger.log(LogStatus.PASS, "MoveIn date is selected in calendar");
			Reporter.log("MoveIn date is selected in calendar", true);
			Thread.sleep(8000);
			viewReservationPage.clk_ApplyBtn();
			Thread.sleep(8000);
			
			try {
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(3000);
			} catch (Exception e) {
				Reporter.log("No Javascript Popup, so Continuing!!!");
			}
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			viewReservationPage.clk_BackToDashboardBtn();
			Thread.sleep(8000);

			PM_HomePage.enter_NameOrResvtn(DB_ReservationNo);
			Thread.sleep(8000);
			PM_HomePage.clk_findReservation();
			Thread.sleep(8000);
			viewReservation = viewReservationPage.get_ViewReservationTitle();

			if (viewReservation.equalsIgnoreCase(tabledata.get("ViewReservationTitle"))) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "View reservation page is displayed");
				logger.log(LogStatus.PASS, "View reservation page is displayed", image);
			}

			Thread.sleep(8000);
			ArrayList<String> DB_ReservationData = DataBase_JDBC
					.executeSQLQuery_List("select top 1  p.phonenumber, e.email" + " from reservation r"
							+ " join phone p on p.phoneid = r.phoneid"
							+ " join emailaddress e on e.emailaddressid = r.emailaddressid"
							+ " where r.reservationid = '" + DB_ReservationNo + "'");
			Thread.sleep(8000);

			String phoneNumberUI = viewReservationPage.getCustInfo_PhoneNumber().replaceAll("-", "");
			String emailIdUI = viewReservationPage.getCustInfo_EmailId();

			if (phoneNumberUI.equalsIgnoreCase(DB_ReservationData.get(0))
					& emailIdUI.equalsIgnoreCase(DB_ReservationData.get(1))) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Phone Number and Email Id are matching with UI and DB");
				logger.log(LogStatus.PASS, "Phone Number and Email Id are matching with UI and DB", image);
				logger.log(LogStatus.PASS,
						"Expected Values: Phone Number-" + phoneNumberUI + " Expected Values: Email Id-" + emailIdUI);
				logger.log(LogStatus.PASS, "Actual Values: Phone Number-" + DB_ReservationData.get(0)
						+ " Expected Values: Email Id-" + DB_ReservationData.get(1));

			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Phone Number and Email Id are not matching with UI and DB");
				logger.log(LogStatus.FAIL, "Phone Number and Email Id are not matching with UI and DB", image);
				logger.log(LogStatus.FAIL,
						"Expected Values: Phone Number-" + emailIdUI + "Expected Values: Email Id-" + emailIdUI);
				logger.log(LogStatus.FAIL, "Actual Values: Phone Number-" + DB_ReservationData.get(0)
						+ "Expected Values: Email Id-" + DB_ReservationData.get(1));

			}
			Thread.sleep(8000);

		} catch (Exception e) {
			e.printStackTrace();
			// In the catch block, set the variable resultFlag to “fail”
			resultFlag = "fail";
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Customer stops by to extend reservation without deposit", image);
		}
	}

	@DataProvider
	public Object[][] getLoginData() {
		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"ExtendReservation_IndividualNewCustomer");
	}
}
