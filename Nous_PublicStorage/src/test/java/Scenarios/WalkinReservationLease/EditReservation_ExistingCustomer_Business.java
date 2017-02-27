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
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.SearchReservation;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import Scenarios.Browser_Factory;

public class EditReservation_ExistingCustomer_Business extends Browser_Factory {
	public ExtentTest logger;

	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";

	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "EditReservation_ExistingCustomer_Business",
					"Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "EditReservation_ExistingCustomer_Business",
					"Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "EditReservation_ExistingCustomer_Business",
					"Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);
	}

	@DataProvider
	public Object[][] getCustSearchData() {
		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"EditReservation_ExistingCustomer_Business");
	}

	@Test(dataProvider = "getCustSearchData")
	public void reservation_Existingcustomer_Individual(Hashtable<String, String> tabledata)
			throws InterruptedException {
		try {
			logger = extent.startTest("EditReservation_ExistingCustomer_Business",
					"Edit reservation of Existing customer-Business ");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " + testcaseName, true);

			if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
				resultFlag = "skip";
				throw new SkipException("Skipping the test");
			}

			// Login To the Application
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Login to Application  successfully");

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
			Thread.sleep(13000);

			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(13000);
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

			// Verify that the user lands on the "PM Dashboard" screen after
			// login and walkin cust title
			PM_Homepage pmhomepage = new PM_Homepage(driver);

			if (tabledata.get("walkInCustTitle").contains(pmhomepage.get_WlkInCustText().trim())) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "walkInCustTitle is displayed successfully");
				logger.log(LogStatus.INFO, "walkInCustTitle is displayed successfully", image);
				resultFlag = "pass";
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "walkInCustTitle is not displayed ");
				logger.log(LogStatus.INFO, "walkInCustTitle is not displayed ", image);

			}

			String location = pmhomepage.getLocation();

			// fetch reservation id from DB String
			String sqlQuery = "select top 1 reservationid from reservation "
					+ "where SiteID = (select siteid from site where sitenumber = '" + location + "') "
					+ "and reservationstatustypeid=127 order by NEWID()";

			String reservationnum = DataBase_JDBC.executeSQLQuery(sqlQuery);
			Reporter.log("Reservation Number fetched from Database is:  " + reservationnum);
			Thread.sleep(5000);

			pmhomepage.enter_NameOrResvtn(reservationnum);
			logger.log(LogStatus.INFO, "Reservation Id entered successfully " + reservationnum);

			pmhomepage.clk_findReservation();
			logger.log(LogStatus.INFO, "Clicked on find reservation button successfully ");

			JavascriptExecutor js = (JavascriptExecutor) driver;
			SearchReservation searchRes = new SearchReservation(driver);

			Thread.sleep(8000);

			ViewReservationPage viewRes = new ViewReservationPage(driver);
			boolean viewResHdrExists = viewRes.IsViewResDisplayed();

			String scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			String image = logger.addScreenCapture(scpath);

			if (viewResHdrExists == true) {
				logger.log(LogStatus.PASS, "View Reservation page displayed successfully");
				logger.log(LogStatus.INFO, "View Reservation page displayed successfully", image);
			} else {
				logger.log(LogStatus.FAIL, "View Reservation page not displayed");
				logger.log(LogStatus.INFO, "View Reservation page not displayed", image);
			}

			// verify resevation info
			String getReservationInfo = viewRes.getResInfo();
			scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			image = logger.addScreenCapture(scpath);

			if (getReservationInfo.contains("Reservation Number: " + reservationnum)) {
				logger.log(LogStatus.PASS, "Reservation details matched");
				logger.log(LogStatus.INFO, "Reservation details matched", image);
			} else {
				logger.log(LogStatus.FAIL, "Reservation details not matched");
				logger.log(LogStatus.INFO, "Reservation details not matched", image);
			}

			// verify Edit and Note buttons
			if (viewRes.verify_EditButton()) {
				logger.log(LogStatus.PASS, "Edit button is displayed");
				logger.log(LogStatus.INFO, "Edit button is displayed");
			} else {
				logger.log(LogStatus.FAIL, "Edit button is not displayed");
				logger.log(LogStatus.INFO, "Edit button is not displayed");
			}

			if (viewRes.verify_NoteButton()) {
				logger.log(LogStatus.PASS, "Note button is displayed");
				logger.log(LogStatus.INFO, "Note button is displayed");
			} else {
				logger.log(LogStatus.FAIL, "Note button is not displayed");
				logger.log(LogStatus.INFO, "Note button is not displayed");
			}

			// click on Edit button
			viewRes.clk_EditBtn();
			logger.log(LogStatus.INFO, "Clicked on Edit button successfully");

			// verify Apply and cancel buttons
			if (viewRes.verify_ApplyButton()) {
				logger.log(LogStatus.PASS, "Apply button is displayed");
				logger.log(LogStatus.INFO, "Apply button is displayed");
			} else {
				logger.log(LogStatus.FAIL, "Apply button is not displayed");
				logger.log(LogStatus.INFO, "Apply button is not displayed");
			}

			if (viewRes.verify_CancelButton()) {
				logger.log(LogStatus.PASS, "cancel button is displayed");
				logger.log(LogStatus.INFO, "cancel button is displayed");
			} else {
				logger.log(LogStatus.FAIL, "cancel button is not displayed");
				logger.log(LogStatus.INFO, "cancel button is not displayed");
			}

			// Edit any Reservation details and customer info
			// Change phone number
			viewRes.clk_phDrpDwn();
			Thread.sleep(5000);

			viewRes.selValueFrmDpDwn();
			logger.log(LogStatus.INFO, "Selected desired phone type from the dropdown");
			Thread.sleep(5000);

			viewRes.txt_areaCode(tabledata.get("PhoneAreacode"));
			viewRes.txt_Exchange(tabledata.get("PhoneExchnge"));
			viewRes.txt_LineNum(tabledata.get("PhoneLneNo"));
			logger.log(LogStatus.INFO, "phone number entered successfully");
			Thread.sleep(8000);

			// click on Apply btn
			viewRes.clk_ApplyBtn();
			logger.log(LogStatus.INFO, "Clicked on Apply button successfully ");
			Thread.sleep(15000);

			try {
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(3000);
			} catch (Exception e) {
				Reporter.log("No Javascript Popup, so Continuing!!!");
			}

			js.executeScript("window.scrollBy(0,2000)", "");
			viewRes.clk_backToDshBoard();
			logger.log(LogStatus.INFO, "Clicked on back to dashboard button successfully ");
			Thread.sleep(5000);

			// Enter reservation number and check whether data updated or not
			pmhomepage.enter_NameOrResvtn(reservationnum);
			logger.log(LogStatus.INFO, "Reservation Id entered successfully ");

			pmhomepage.clk_findReservation();
			logger.log(LogStatus.INFO, "Clicked on find reservation button successfully ");

			// verify the result updated result in dB
			String query = "select top 1  p.phonenumber from reservation r " + " join phone p on p.phoneid = r.phoneid "
					+ " where r.reservationid ='" + reservationnum + "'";

			String updatedPhoneNum = DataBase_JDBC.executeSQLQuery(query);

			Thread.sleep(3000);

			String PhoneNum = driver
					.findElement(By.xpath("//div[@id='contactInformation']//span[@class='phone-number margin-right']"))
					.getText();

			System.out.println(PhoneNum);

			String[] ph1 = PhoneNum.split("-");
			String getphNum = (ph1[0].concat(ph1[1])).concat(ph1[2]);
			Reporter.log("Phone number:" + getphNum, true);

			Reporter.log("DB Phone number is :" + updatedPhoneNum, true);
			scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			image = logger.addScreenCapture(scpath);
			if (updatedPhoneNum.equals(getphNum)) {
				logger.log(LogStatus.PASS, "DB and UI updated data is matching ");
				logger.log(LogStatus.INFO, "DB and UI updated data is matching", image);
				resultFlag = "pass";
			} else {
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "DB and UI updated data is not matching");
				logger.log(LogStatus.INFO, "DB and UI updated data is  notmatching", image);
			}
		}

		catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
			Reporter.log("Exception ex: " + ex, true);
			logger.log(LogStatus.FAIL, "Test Script fail due to exception");
		}

	}

}
