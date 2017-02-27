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
import Pages.AdvSearchPages.Advance_Search;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.RestoreReservation;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import Scenarios.Browser_Factory;

public class RestoreResv_CancelOlderThan30days extends Browser_Factory {

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
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "RestoreResv_CancelOlderThan30days", "Status",
					"Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "RestoreResv_CancelOlderThan30days", "Status",
					"Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "RestoreResv_CancelOlderThan30days", "Status",
					"Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);
	}

	@DataProvider
	public Object[][] getData() {
		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"RestoreResv_CancelOlderThan30days");
	}

	@Test(dataProvider = "getData")
	public void restoreRes_CancelOlderThan30Days(Hashtable<String, String> tabledata) throws InterruptedException {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		try {

			// Login to PS Application
			logger = extent.startTest("RestoreResv_CancelOlderThan30days",
					"Restore a reservation which is cancelled and older than 30 days");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " + testcaseName, true);

			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");

			Robot robot = new Robot();
			wait = new WebDriverWait(driver, 90);
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
			
			Thread.sleep(10000);

			// ======== Handling customer facing device end =============

			// Verify User should view the "Walk-in-Customer" module
			PM_Homepage pmhomepage = new PM_Homepage(driver);

			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);


			String location = pmhomepage.getLocation();
			Thread.sleep(5000);
			
			String sqlQuery = "select siteid from site where sitenumber = "+location;
			String siteId = DataBase_JDBC.executeSQLQuery(sqlQuery);

			/*sqlQuery = "select top 1 reservationid from reservation where siteid = "+siteId+" and reservationstatustypeid = 4342 "+
							  " and recorddatetime < getdate()-31 order by recorddatetime desc";*/
			sqlQuery="select top 1 reservationid from reservation where siteid = "+siteId+" and reservationstatustypeid = 4342 "+
			         " and expirationdate < getdate()-31 order by recorddatetime desc";

			String DB_ReservationNo = DataBase_JDBC.executeSQLQuery(sqlQuery);

			pmhomepage.enter_NameOrResvtn(DB_ReservationNo);
			logger.log(LogStatus.INFO, "Entered a reservation number which is cancelled and older than 30 days");
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			pmhomepage.clk_findReservation();
			logger.log(LogStatus.INFO, "Clicked on find reservation button successfully ");
			Thread.sleep(8000);
			
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			Advance_Search advSrch = new Advance_Search(driver);
			Thread.sleep(3000);
			String message = advSrch.getQueryResultMessage();
			if(message.equals("No reservation results found matching the specified criteria.")){
				logger.log(LogStatus.PASS, "No reservation results displayed as expected since the reservation is cancelled and older than 30 days");
			}else{
				logger.log(LogStatus.FAIL, "Some reservation results displayed which is not expected since the reservation is cancelled and older than 30 days");
				resultFlag = "fail";
			}
			

			

		} catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			logger.log(LogStatus.FAIL, "Reservation results displayed which is not expected since the reservation is cancelled and older than 30 days");
			logger.log(LogStatus.FAIL, "Test script failed due to the exception " + ex);
		}

	}
}
