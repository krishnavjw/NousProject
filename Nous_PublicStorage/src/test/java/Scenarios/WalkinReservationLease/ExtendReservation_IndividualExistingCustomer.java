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
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.CreateReservation_PopUp;
import Pages.Walkin_Reservation_Lease.CreateReservation;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import Scenarios.Browser_Factory;

public class ExtendReservation_IndividualExistingCustomer extends Browser_Factory {

	public ExtentTest logger;
	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";

	@AfterMethod
	public void afterMethod() {

		System.out.println(" In After Method");
		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"ExtendReservation_IndividualExistingCustomer", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Customer stops by to extend reservation without deposit", image);
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"ExtendReservation_IndividualExistingCustomer", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"ExtendReservation_IndividualExistingCustomer", "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);
	}

	@Test(dataProvider = "getLoginData")
	public void ExtendReservation_IndividualExistingCustomer(Hashtable<String, String> tabledata) throws Exception {
		logger = extent.startTest("ExtendReservation_IndividualExistingCustomer", "WalkinReservationLease");
		testcaseName = tabledata.get("TestCases");
		Reporter.log("Test case started: " + testcaseName, true);

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			logger.log(LogStatus.SKIP, "ExtendReservation_IndividualExistingCustomer is Skipped");
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);

		try {
			
			// login into application with Customer device connect
			LoginPage login = new LoginPage(driver);
			//String siteNumber = login.get_SiteNumber();
			
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

			PM_Homepage pmhomepage = new PM_Homepage(driver);
			Thread.sleep(6000);
			String siteNumber = login.get_SiteNumber();
			Thread.sleep(5000);
			String location = pmhomepage.getLocation();

			// Click on Advance Search link
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(5000);
			logger.log(LogStatus.PASS, "Clicked on Advance search link successfully");

			Advance_Search advsearch = new Advance_Search(driver);

			// Enter Account No
			String accountNum = DataBase_JDBC.executeSQLQuery("select top 1 reservationaccountid from reservation "
					+ "where siteid = (select siteid from site where sitenumber = " + location + ") and "
					+ "reservationaccountid is not null order by NEWID()");
			advsearch.enterAccNum(accountNum);
			
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "Account No Entered successfully :" + accountNum);
			advsearch.clickSearchAccbtn();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "clicked on search btn successfully");
			Reporter.log("click on serach button", true);
			Thread.sleep(10000);

			// selecting Add space from Quick linkd Dropdown
			Cust_AccDetailsPage cust = new Cust_AccDetailsPage(driver);
			Thread.sleep(5000);
			cust.sel_quickLinks_dropDown(driver);
			logger.log(LogStatus.INFO, "Selected Add space option successfully");
			Reporter.log("Selected Add space option successfully", true);

			String scpath1 = Generic_Class.takeScreenShotPath();
			String image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "cust dashboard is displayed", image1);
			Thread.sleep(10000);

			StandardStoragePage stdpage = new StandardStoragePage(driver);
			Thread.sleep(5000);
			if ("Standard Storage".contains(stdpage.get_StandardStorageTxt().trim())) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "standard stroage is displayed successfully");
				logger.log(LogStatus.INFO, "standard stroage is displayed successfully", image);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "standard stroage is not displayed");
				logger.log(LogStatus.INFO, "standard stroage is not displayed ", image);

			}
			
			
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

			Thread.sleep(10000);
			CreateReservation CreateReservation = new CreateReservation(driver);

			driver.findElement(By.xpath("//div[@id='expectedMoveInDate']//span[text()='SELECT']")).click();
			Thread.sleep(4000);
			CreateReservation.SelectDateFromCalendar(tabledata.get("MoveInDate"));
			logger.log(LogStatus.PASS, "MoveIn date is selected in calendar " + tabledata.get("MoveInDate"));
			Reporter.log("MoveIn date is selected in calendar", true);
			Thread.sleep(20000);

			// Get The value for firstName and Lastname
			String FirstName = driver.findElement(By.xpath("//div[@id='ContactModel_FirstName-wrapper']//input"))
					.getAttribute("value");
			Thread.sleep(2000);
			String LastName = driver.findElement(By.xpath("//div[@id='ContactModel_LastName-wrapper']//input"))
					.getAttribute("value");
			
			CreateReservation.sel_DropDownValFromPhNo(tabledata.get("Phone"));
			logger.log(LogStatus.PASS, "Phone type is selected in CreateReservation Page");
			Reporter.log("Phone type is selected in CreateReservation Page", true);
			Thread.sleep(1000);
			CreateReservation.enter_PhoneAreaCode(tabledata.get("PhoneAreacode"));
			Thread.sleep(1000);
			CreateReservation.enter_PhoneExchnge(tabledata.get("PhoneExchnge"));
			Thread.sleep(1000);
			CreateReservation.enter_PhoneLineNumber(tabledata.get("PhoneLneNo"));
			Thread.sleep(1000);
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
			
			Thread.sleep(8000);

			CreateReservation_PopUp rserservationpopup = new CreateReservation_PopUp(driver);
			if (rserservationpopup.get_CreateResvPopUpHeader().equalsIgnoreCase("Create Reservation")) {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Create Reservation modal window displayed successfully "
						+ rserservationpopup.get_CreateResvPopUpHeader());
				logger.log(LogStatus.INFO, "Create Reservation modal window displayed successfully", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Create Reservation modal window not  displayed successfully ");
				logger.log(LogStatus.INFO, "Create Reservation modal window not displayed successfully ", image);
			}

			// Do not enter any EMP ID and click "Create Reservation" button
			rserservationpopup.enter_EmpID(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "employee no entered successfully ");

			rserservationpopup.clk_CreateResvationBtn();
			Thread.sleep(24000);
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			
			System.out.println(" FN--"+FirstName);

			String DB_ReservationNo = DataBase_JDBC.executeSQLQuery("select top 1 reservationid from reservation"
					+ " where SiteID = (select siteid from site where sitenumber = '" + siteNumber + "')"
					+ "  and firstname = '" + FirstName + "'" + " and reservationstatustypeid = '127'"
					+ " order by lastupdate desc");

			Thread.sleep(8000);

			pmhomepage.enter_NameOrResvtn(DB_ReservationNo);
			Thread.sleep(2000);
			pmhomepage.clk_findReservation();
			Thread.sleep(5000);

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
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
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
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
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
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

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

			logger.log(LogStatus.PASS, "MoveIn date is selected in calendar- after 7 days");
			Reporter.log("MoveIn date is selected in calendar", true);
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-1000)");
			viewReservationPage.clk_ApplyBtn();
			Thread.sleep(5000);
			
			try {
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(3000);
			} catch (Exception e) {
				Reporter.log("No Javascript Popup, so Continuing!!!");
			}
			
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,2000)");
			Thread.sleep(5000);
			viewReservationPage.clk_BackToDashboardBtn();
			Thread.sleep(8000);

			pmhomepage.enter_NameOrResvtn(DB_ReservationNo);
			Thread.sleep(2000);
			pmhomepage.clk_findReservation();
			Thread.sleep(5000);
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
			Thread.sleep(3000);

			String phoneNumberUI = viewReservationPage.getCustInfo_PhoneNumber().replaceAll("-", "");
			String emailIdUI = viewReservationPage.getCustInfo_EmailId();

			if (phoneNumberUI.equalsIgnoreCase(DB_ReservationData.get(0))
					& emailIdUI.equalsIgnoreCase(DB_ReservationData.get(1))) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Phone Number and Email Id are matching with UI and DB");
				logger.log(LogStatus.PASS, "Phone Number and Email Id are matching with UI and DB", image);
				logger.log(LogStatus.PASS,
						"Expected Values: Phone Number-" + phoneNumberUI + "Expected Values: Email Id-" + emailIdUI);
				logger.log(LogStatus.PASS, "Actual Values: Phone Number-" + DB_ReservationData.get(0)
						+ "Expected Values: Email Id-" + DB_ReservationData.get(1));

			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Phone Number and Email Id are not matching with UI and DB");
				logger.log(LogStatus.FAIL, "Phone Number and Email Id are not matching with UI and DB", image);
				logger.log(LogStatus.FAIL,
						"Expected Values: Phone Number-" + phoneNumberUI + "Expected Values: Email Id-" + emailIdUI);
				logger.log(LogStatus.FAIL, "Actual Values: Phone Number-" + DB_ReservationData.get(0)
						+ "Expected Values: Email Id-" + DB_ReservationData.get(1));
				if (resultFlag.equals("pass"))
					resultFlag = "fail";

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
				"ExtendReservation_IndividualExistingCustomer");
	}
}
