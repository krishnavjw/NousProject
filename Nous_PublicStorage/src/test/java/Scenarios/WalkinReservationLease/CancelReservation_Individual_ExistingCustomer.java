package Scenarios.WalkinReservationLease;

import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import Scenarios.Browser_Factory;

public class CancelReservation_Individual_ExistingCustomer extends Browser_Factory {

	public ExtentTest logger;
	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";
	String screen;
	String im;
	int i = 0;

	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"CancelReservation_Individual_ExistingCustomer", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"CancelReservation_Individual_ExistingCustomer", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"CancelReservation_Individual_ExistingCustomer", "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);
	}

	@Test(dataProvider = "getLoginData")
	public void CancelReservation_Individual_ExistingCustomer(Hashtable<String, String> tabledata) throws InterruptedException {
		
		logger = extent.startTest("CancelReservation_Individual_ExistingCustomer","Customer walk in to reserve a space");
		testcaseName = tabledata.get("TestCases");
		Reporter.log("Test case started: " + testcaseName, true);

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			logger.log(LogStatus.SKIP, "CancelReservation_Individual_ExistingCustomer is Skipped");
			throw new SkipException("Skipping the test");
		}
		try {

			LoginPage login = new LoginPage(driver);
			String siteNumber = login.get_SiteNumber();
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.PASS, "Login is successful");

			Robot robot = new Robot();
			WebDriverWait wait = new WebDriverWait(driver, 40);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			Thread.sleep(8000);

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

			// ============================ Handling customer facing device end ===============================

			Thread.sleep(3000);
			PM_Homepage homepage = new PM_Homepage(driver);

			String resid = "select top 1 reservationid from reservation"
					+ " where SiteID = (select siteid from site where sitenumber = '"+siteNumber+"')"
					+ " and reservationstatustypeid = '127'" + " order by NEWID()";

			String DB_ReservationNo = DataBase_JDBC.executeSQLQuery(resid);
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Fetching Reservation Number:" + DB_ReservationNo + " from DB");

			
			homepage.enter_NameOrResvtn(DB_ReservationNo);
			logger.log(LogStatus.INFO, "Enter Reservation number");
			Thread.sleep(2000);

			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "Entered Account Number" + DB_ReservationNo + "successfully", im);

			homepage.clk_findReservation();
			logger.log(LogStatus.INFO, "Clicked on Find Reservation button");
			Thread.sleep(8000);

			ViewReservationPage viewReservationPage = new ViewReservationPage(driver);

			try {
				
				String viewReservation = viewReservationPage.get_ViewReservationTitle();

				if (viewReservation.equalsIgnoreCase(tabledata.get("ViewReservationTitle"))) {
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "View reservation page is displayed");
					logger.log(LogStatus.PASS, "View reservation page is displayed", image);
				}

			} catch (Exception e) {
				logger.log(LogStatus.FAIL, "View reservation page is not Displayed");
			}

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			
			String spacenum=driver.findElement(By.xpath("//div[@class='unit-grid-link unit-grid-link-rental-unit']")).getText();
			

			viewReservationPage.clk_CancelBtn();
			logger.log(LogStatus.INFO, "Cancelled button is displayed successfully");
			logger.log(LogStatus.INFO, "Click on Cancel button successfully");
			Thread.sleep(8000);

			CancelReservationPage cancelPage = new CancelReservationPage(driver);
			String cancelTitle = cancelPage.verifyCancelReservationTitle();

			if (cancelTitle.equalsIgnoreCase(tabledata.get("CancelReservationTitle"))) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Cancel Reservation page is displayed");
				logger.log(LogStatus.PASS, "Cancel Reservation page is displayed", image);
			}

			if (cancelPage.verifyReasonListbox()) {
				logger.log(LogStatus.PASS, "Reason for Cancellation dropdown is displayed");
			}
			Thread.sleep(4000);

			cancelPage.clickReasonListbox();
			logger.log(LogStatus.INFO, "Click on Cancel for Reservation listbox successfully");

			String[] reasonTypes = { "Not Storing", "Price", "Rented Elsewhere", "Unable to Contact",
					"Duplicate Reservation", "No Reason Given" };
			Actions dragger = new Actions(driver);
			WebElement draggablePartOfScrollbar = driver.findElement(By.xpath(
					"//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul//div[@class='ps-scrollbar-y-rail']//div[@class='ps-scrollbar-y']"));
			List<WebElement> reasons = driver.findElements(By.xpath(
					"//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul/li[@class='k-item']"));

			for (WebElement reason : reasons) {
				if (reason.getText().trim().equalsIgnoreCase(reasonTypes[i])) {
					logger.log(LogStatus.PASS, "Expected Result:----->  " + reason.getText()
							+ " and Actual Results:----->  " + reasonTypes[i] + " are same ");
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
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Reason for Cancellation and Employee Id are mandatory fields");
				logger.log(LogStatus.PASS, "Reason for Cancellation and Employee Id are mandatory fields", image);
			}
			Thread.sleep(2000);

			cancelPage.clickCancel_btn();
			logger.log(LogStatus.INFO, "Clicking on Cancel button successfully");
			Thread.sleep(8000);

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);

			viewReservationPage.clk_CancelBtn();
			logger.log(LogStatus.INFO, "Click on Cancel button successfully");
			Thread.sleep(8000);

			cancelPage.clickReasonListbox();
			logger.log(LogStatus.INFO, "Click on Cancel for Reservation listbox successfully");
			Thread.sleep(2000);

			String xpath = "//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul/li[text()='"
					+ tabledata.get("CancelReason") + "']";
			driver.findElement(By.xpath(xpath)).click();
			logger.log(LogStatus.INFO, "Select Cancel Reason from drop down list successfully");
			Thread.sleep(2000);

			cancelPage.enterNotes(tabledata.get("ReasonNotes"));
			logger.log(LogStatus.INFO, "Entered the Reason Notes successfully");
			Thread.sleep(1000);

			cancelPage.enterEmployeeId(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered the Employee Number successfully");
			Thread.sleep(1000);

			cancelPage.clickConfirmCancel_btn();
			logger.log(LogStatus.INFO, "Clicking on Confirm Cancel button successfully");
			Thread.sleep(6000);

			homepage = new PM_Homepage(driver);
			homepage.enter_NameOrResvtn(DB_ReservationNo);
			Thread.sleep(2000);

			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "Entered Reservation number", im);

			homepage.clk_findReservation();
			logger.log(LogStatus.INFO, "Click on Find Reservation button");
			Thread.sleep(8000);

			WebElement cancelledStatus = driver.findElement(By.xpath("//span[contains(text(),'Canceled')]"));

			if (cancelledStatus.isDisplayed()) {
				logger.log(LogStatus.PASS,
						"Reservation status is cancelled for" + DB_ReservationNo + " in Reservations Grid ");
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Status is cancelled for the reservation");
				logger.log(LogStatus.PASS, "Status is cancelled for the reservation", image);

			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Status is not cancelled for the reservation");
				logger.log(LogStatus.FAIL, "Status is not cancelled for the reservation", image);
			}

			Thread.sleep(1000);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(1000);
			driver.findElement(By.xpath("//a[contains(text(),'Back To Dashboard')]")).click();
			Thread.sleep(8000);
			
			homepage.clk_findAndLeaseSpace();
			logger.log(LogStatus.INFO, "Click on the find and lease button");
			
			StandardStoragePage stdstoragespace= new StandardStoragePage(driver);
			Thread.sleep(5000);
			stdstoragespace.click_SpecificSpace();
			
			stdstoragespace.enter_spaceunit(spacenum);
			
			stdstoragespace.click_Search();
			logger.log(LogStatus.INFO, "Clicked on Search button");
			Reporter.log("Clicked on Search button", true);
			Thread.sleep(5000);
			
			
			List<WebElement> uiSpaces = driver.findElements(By.xpath("//table/tbody//tr//td[@class='grid-cell-space']"));
			Thread.sleep(3000);
			
			if (uiSpaces.size()>0) {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "The space is Not in HOLD after cancelling the reservation-- "+uiSpaces.get(0).getText());
				logger.log(LogStatus.INFO, "The space is Not in HOLD after cancelling the reservation", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "The space is in HOLD after cancelling the reservation");
				logger.log(LogStatus.INFO, "The space is in HOLD after cancelling the reservation ", image);
			}
			
		
			
			String phonecallid_data = "select top 1 t.name  from phonecall pc join type t on t.typeid=pc.calltypeid "
					+ "where pc.OwnerTableItemID ='" + DB_ReservationNo +"' "
					+ "Order by PhonecallID  desc";

			String phonecellid = DataBase_JDBC.executeSQLQuery(phonecallid_data);
			
			
			if (phonecellid.isEmpty()) {
				logger.log(LogStatus.PASS,"Reservation Calls are not scheduled for " + DB_ReservationNo);
				

			} else {
				
				logger.log(LogStatus.FAIL, "Reservation Calls are scheduled --"+phonecellid);
				
			}
			
			Thread.sleep(3000);
			String spacestatus = "select name from type where typeid=( "
					+ "select  RentalStatusTypeID from productSite ps "
					+ "join rentalunit ru on ru.productsiteid=ps.productsiteid "
					+ "join site s on s.siteid=ps.siteid "
					+ "where s.sitenumber='"+siteNumber+"' "
					+ "and ru.rentalunitnumber='"+spacenum+"')";

			String spacestatus_db = DataBase_JDBC.executeSQLQuery(spacestatus);
			
			
			if (spacestatus_db.equals("Vacant")) {
				logger.log(LogStatus.PASS,"The Unit Status after Cancelling Reservation is--"+spacenum+"--- " + spacestatus_db);
				

			} else {
				
				logger.log(LogStatus.FAIL, "The Unit Status after Cancelling Reservation is--"+spacenum+"--- " + spacestatus_db);
				
			}
			

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			resultFlag = "fail";
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Image", image);
			logger.log(LogStatus.FAIL, "Test script failed due to the exception " + e);
		}
	}

	@DataProvider
	public Object[][] getLoginData() {

		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"CancelReservation_Individual_ExistingCustomer");
	}

}
