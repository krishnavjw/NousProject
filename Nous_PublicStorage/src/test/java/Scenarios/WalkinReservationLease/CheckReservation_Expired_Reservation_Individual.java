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
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import Scenarios.Browser_Factory;

public class CheckReservation_Expired_Reservation_Individual extends Browser_Factory {

	String locationnum = null;
	public ExtentTest logger;
	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "Pass";
	String screen;
	String im;

	@AfterMethod
	public void afterMethod() {

		System.out.println(" In After Method");
		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"CheckReservation_Expired_Reservation_Individual", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Leasing for Customer verification", image);
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"CheckReservation_Expired_Reservation_Individual", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"CheckReservation_Expired_Reservation_Individual", "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

	@Test(dataProvider = "getLoginData")
	public void CheckReservation_Expired_Reservation_Individual(Hashtable<String, String> tabledata) throws Exception {
		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}
		// Reporter.log("end Completed", true);
		Thread.sleep(5000);
		try {
			logger = extent.startTest("CheckReservation_Expired_Reservation_Individual","CheckReservation_Expired_Reservation_Individual");

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

		//=========================== Handling customer facing device end =============
			Thread.sleep(2000);
			PM_Homepage homepage = new PM_Homepage(driver);
			logger.log(LogStatus.INFO, "Home Page object is created successfully");
			
			Thread.sleep(6000);
			String siteNumber = login.get_SiteNumber();
			Thread.sleep(5000);
			
			String location = homepage.getLocation();
			Thread.sleep(6000);

	
			String sqlQuery = "select top 1 reservationid from reservation "+
					"where SiteID = (select siteid from site where sitenumber ='"+siteNumber+"') "+
					"and ExpectedMoveInDate<getdate() "+
					"and ExpirationDate>ExpectedMoveInDate "+
					"and ReservationStatusTypeID=4342 "+
					"and Expirationdate>getutcdate()-30 "+
					"Order by LastUpdate DESC ";
			
			String DB_ReservationNo = DataBase_JDBC.executeSQLQuery(sqlQuery);
			logger.log(LogStatus.INFO, "Fetching Reservation Number:" + DB_ReservationNo + " from DB");
			
			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "PM Dash board Displayed successfully", im);

			homepage.enter_NameOrResvtn(DB_ReservationNo);
			logger.log(LogStatus.INFO, "Enter Reservation number");
			Thread.sleep(12000);

			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "Entered Account Number" + DB_ReservationNo + "successfully", im);

			homepage.clk_findReservation();
			logger.log(LogStatus.INFO, "Clicked on Find Reservation button");
			
			Thread.sleep(10000);
			ViewReservationPage viewReservationPage = new ViewReservationPage(driver);

			
			Thread.sleep(12000);
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

			ViewReservationPage viewRes = new ViewReservationPage(driver);

			if (viewRes.verify_EditButton()) {
				logger.log(LogStatus.FAIL, "Edit button is displayed");
				logger.log(LogStatus.INFO, "Edit button is displayed");
			} else {
				logger.log(LogStatus.PASS, "Edit button is not displayed");
				logger.log(LogStatus.INFO, "Edit button is not displayed");
			}
			
			
			// DB Verification
			
			
			


			
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
				"CheckReservation_Expired_Reservation_Individual");
	}

	public void Verify_text(String rn, String text) {
		if (rn.equalsIgnoreCase(text)) {
			logger.log(LogStatus.PASS, text + " in date is displayed");
		} else {
			logger.log(LogStatus.FAIL, text + " in date  is not displayed");
		}

	}

}
