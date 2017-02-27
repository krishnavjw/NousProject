package Scenarios.WalkinReservationLease;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Pages.Walkin_Reservation_Lease.CreateReservation;
import Pages.Walkin_Reservation_Lease.CreateReservation_PopUp;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Scenarios.Browser_Factory;

public class CreateReservation_NewCustomer_Individual_PreReservation extends Browser_Factory {

	ExtentTest logger;
	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";

	@AfterMethod
	public void afterMethod() {

		if (resultFlag.equals("pass")) {

			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"CreateReservation_NewCustomer_Individual_PreReservation", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"CreateReservation_NewCustomer_Individual_PreReservation", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease",
					"CreateReservation_NewCustomer_Individual_PreReservation", "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

	@Test(dataProvider = "getData")
	public void CreateReservation_NewCustomer_Individual_PreReservation(Hashtable<String, String> tabledata)
			throws InterruptedException {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		try {

			LoginPage login = new LoginPage(driver);

			String sitenum = login.get_SiteNumber();

			logger = extent.startTest("CreateReservation_NewCustomer_Individual_PreReservation",
					"Customer walks in - makes a reservation for a space that requires deposit");
			logger.log(LogStatus.INFO, "Setting the max_reservation_days=15 ");

			DataBase_JDBC.executeSQLQuery(
					"update siteparameter set ParamValue=15 where paramcode='max_reservation_days' and siteid=(select SiteID from Site where SiteNumber='"
							+ sitenum + "')");
			logger.log(LogStatus.INFO, "Setting the ReservationDepositEnabled = blank");

			DataBase_JDBC.executeSQLQuery(
					"update siteparameter set ParamValue='' where paramcode='ReservationDepositEnabled' and siteid=(select SiteID from Site where SiteNumber='"
							+ sitenum + "')");
			logger.log(LogStatus.INFO, "Setting the site parameter as per the ReservationDepositEnabled and site num");

			
			
			
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			Robot robot = new Robot();
			WebDriverWait wait = new WebDriverWait(driver, 40);
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
			// ======== Handling customer facing device end =============

			PM_Homepage Pmpage = new PM_Homepage(driver);

			String location = Pmpage.getLocation();
			Thread.sleep(5000);
			if (tabledata.get("walkInCustTitle").contains(Pmpage.get_WlkInCustText().trim())) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "PM Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "PM Dashboard is displayed successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "PM Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "PM Dashboard is not displayed ", image);

			}

			Pmpage.clk_findAndLeaseSpace();
			logger.log(LogStatus.INFO, "Click on the find and lease button");

			StandardStoragePage StandardStoragePage = new StandardStoragePage(driver);
			Thread.sleep(5000);

			try {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Handling Pop Up");
				logger.log(LogStatus.INFO, "Pop Handled", image);
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
			} catch (Exception e) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Pop Up Unavailable");
				logger.log(LogStatus.INFO, "Pop Not Available", image);
			}

			// Verify Standard Storage selected by default
			if (driver
					.findElement(By
							.xpath("//div[@id='choose-size-search-type']/ul/li[@class='k-state-active standard-storage k-item k-tab-on-top k-state-default k-first']"))
					.isDisplayed()) {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Standard Storage tab default selected ");
				logger.log(LogStatus.INFO, "Standard Storage tab default selected ", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Standard Storage tab not default selected  ");
				logger.log(LogStatus.INFO, "Standard Storage tab not default selected  ", image);

			}

			// Verify that for PM, in WALK-INS: FIND A SPACE screen Standard
			// Storage,Vechicle Storage and Specific Space Tabs
			if (StandardStoragePage.isdisplayed_StandardStorage()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find a space page is displayed successfully");
				logger.log(LogStatus.PASS, "Standard Storage tab displayed successfully");
				logger.log(LogStatus.INFO, "Standard Storage tab displayed successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find a space page is not displayed successfully");
				logger.log(LogStatus.FAIL, "Standard Storage tab not displayed successfully ");
				logger.log(LogStatus.INFO, "Standard Storage tab not displayed successfully ", image);
			}

			if (StandardStoragePage.isdisplayed_VehicleStorage()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Vehicle Storage tab displayed successfully");
				logger.log(LogStatus.INFO, "Vehicle Storage tab displayed successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Vehicle Storage tab not displayed successfully ");
				logger.log(LogStatus.INFO, "Vehicle Storage tab not displayed successfully ", image);
			}

			if (StandardStoragePage.isdisplayed_SpecificStorage()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Specific Storage tab displayed successfully");
				logger.log(LogStatus.INFO, "Specific Storage tab displayed successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Specific Storage tab not displayed successfully ");
				logger.log(LogStatus.INFO, "Specific Storage tab not displayed successfully ", image);
			}

			if (StandardStoragePage.isSelected_yesradiobutton()) {
				logger.log(LogStatus.INFO,
						"Yes Radio button selected by default when WALK-INS: FIND A SPACE page is launched sucessfully for a New Cusomter");

			} else {
				logger.log(LogStatus.FAIL,
						"Yes Radio button is not selected by default when WALK-INS: FIND A SPACE page is launched sucessfully for a New Cusomter");
			}

			StandardStoragePage.Clk_OnAvlSpace();
			logger.log(LogStatus.INFO, "Clicked on available spaces checkbox in StandardStorage");
			Thread.sleep(2000);
			String scpath1 = Generic_Class.takeScreenShotPath();
			
			String image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "selecting checkboxes ");
			logger.log(LogStatus.INFO, "selecting checkboxes ", image1);
			StandardStoragePage.click_Search();
			logger.log(LogStatus.INFO, "Clicked on Search button");
			Reporter.log("Clicked on Search button", true);
			Thread.sleep(5000);

			String size = StandardStoragePage.getAvlSpace_Size();
			size = size.replaceAll("'", "");
			size = size.replaceAll(" ", "");
			Reporter.log("space size is--->:" + size, true);

			Reporter.log("Clicked on available spaces", true);
			String query = "SELECT RentalUnitNumber AS Space FROM DBO.RentalUnit RU"
					+ " INNER JOIN DBO.Productsite PS ON RU.ProductSiteID = PS.ProductSiteID"
					+ " INNER JOIN DBO.Site S ON S.SiteID = PS.SiteID "
					+ " INNER JOIN DBO.StorageProductFeature SPF ON PS.StorageProductFeatureID = SPF.StorageProductFeatureID"
					+ " Inner JOIN DBO.Type T1 ON T1.TypeID=RU.RentalStatusTypeID "
					+ " LEFT JOIN DBO.Type T3 ON T3.TypeID=SPF.DoorTypeID "
					+ " LEFT JOIN DBO.Type T4 ON T4.TypeID=RU.RentalLockStatusTypeID" + " WHERE 1 = 1 "
					+ " AND S.Sitenumber = '" + location + "'" + " AND T1.Name Like 'Vacant%'"
					/*+ " AND CAST(CAST(ISNULL(Width, 0) AS integer) AS varchar)+'X'+CAST(CAST(ISNULL(Length, 0) AS integer) AS varchar) Like '"
					+ size + "'"*/
					+ " ORDER BY CASE WHEN RentalUnitNumber LIKE 'APT%' THEN '1' ELSE RentalUnitNumber END";

			ArrayList<String> avlSpaces = DataBase_JDBC.executeSQLQuery_List(query);

			List<WebElement> uiSpaces = driver
					.findElements(By.xpath("//table/tbody//tr//td[@class='grid-cell-space']"));
			int cnt = 1;

			Actions dragger = new Actions(driver);
			WebElement draggablePartOfScrollbar = driver
					.findElement(By.xpath("//div[@class='ps-scrollbar-y-rail']//div[@class='ps-scrollbar-y']"));
			for (WebElement space : uiSpaces) {
				String spacenum = space.getText();
				System.out.println(spacenum);

				for (int i = 0; i < avlSpaces.size(); i++) {
					if (spacenum.equalsIgnoreCase(avlSpaces.get(i))) {
						logger.log(LogStatus.PASS, "Space Number from Database is: " + avlSpaces.get(i)
								+ "Space Number in UI is:  " + spacenum);
						if (cnt >= 3) {
							dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0, 100)
									.release().build().perform();
							Thread.sleep(1000);
						}
						cnt++;
						break;
					}
				}
			}
			Thread.sleep(3000);
			// Click On Other Location tab
			SpaceDashboard_ThisLoc spacedashboard = new SpaceDashboard_ThisLoc(driver);

			
			spacedashboard.click_otherLocations();
			Thread.sleep(3000);
			spacedashboard.click_thisLocations();
			Thread.sleep(3000);
			String path = "//div[@id='onsiteUnitGrid']/div[2]/table/tbody/tr[1]/td[1]/input";
			System.out.println("Path is:  " + path);

			WebElement RdBtn_Space = driver.findElement(By.xpath(path));
			logger.log(LogStatus.PASS,
					"check the radio button based on the space and click on the  reservation button");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", RdBtn_Space);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", RdBtn_Space);
			Thread.sleep(5000);

			// generics.Page_ScrollDown();
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			Thread.sleep(5000);

			// Verify Rent button is available at bottom
			if (spacedashboard.isDisplayed_Rentbutton()) {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "At the bottom Rent button displayed successfully ");
				logger.log(LogStatus.INFO, "At the bottom Rent button displayed successfully", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "At the bottom Rent button not displayed successfully ");
				logger.log(LogStatus.INFO, "At the bottom Rent button not displayed successfully ", image);
			}

			// Verify Reserve button is available at bottom
			if (spacedashboard.isDisplayed_Reservebutton()) {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "At the bottom Reserve button displayed successfully ");
				logger.log(LogStatus.INFO, "At the bottom Reserve button displayed successfully ", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "At the bottom Reserve button not displayed successfully  ");
				logger.log(LogStatus.INFO, "At the bottom Reserve button not  displayed successfully  ", image);
			}

			// Verify Hold button is available at bottom
			if (spacedashboard.isDisplayed_Holdbutton()) {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, " At the bottom Hold button displayed successfully ");
				logger.log(LogStatus.INFO, "At the bottom Hold button displayed successfully", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "At the bottom Hold button not displayed successfully ");
				logger.log(LogStatus.INFO, "At the bottom Hold button not displayed successfully ", image);
			}

			// Click On Reserve button
			spacedashboard.click_Reserve();
			logger.log(LogStatus.PASS, "Clicked on Reserve button successfully ");
			Thread.sleep(10000);

			try {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Handling Pop Up");
				logger.log(LogStatus.INFO, "Pop Handled", image);
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
			} catch (Exception e) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Pop Up Unavailable");
				logger.log(LogStatus.INFO, "Pop Not Available", image);
			}

			// Cost Validation

			logger.log(LogStatus.PASS, "Validating Calculations in Create Reservation Page");

			WebElement move_in_subtotal = driver
					.findElement(By.xpath("//div[@class='move-in-subtotal floatright big-bold fixed-col']"));
			WebElement admin_fee_amount = driver
					.findElement(By.xpath("//div[@class='admin-fee-amount floatright fixed-col']"));
			WebElement overall_total_move_in = driver
					.findElement(By.xpath("//div[@class='overall-total-move-in big-bold floatright fixed-col']"));

			Reporter.log(" 2----------------s:" + move_in_subtotal.getText(), true);
			Reporter.log(" 3----------------s:" + move_in_subtotal.getText().substring(1), true);

			int moveinsub = Generic_Class.getAmount(move_in_subtotal.getText());

			int moveinfee = Generic_Class.getAmount(admin_fee_amount.getText());

			int moveintotle = Generic_Class.getAmount(overall_total_move_in.getText());

			Reporter.log("subtotal is 4----------------s:" + moveintotle, true);

			if (moveintotle == moveinsub + moveinfee) {
				logger.log(LogStatus.PASS, "displayed total is same for move in");
			} else {

				logger.log(LogStatus.FAIL, "displayed total is not correct for move in");
			}

			WebElement rent_subtotal = driver
					.findElement(By.xpath("//div[@class='rent-subtotal floatright big-bold fixed-col']"));

			WebElement overall_total_rent = driver
					.findElement(By.xpath("//div[@class='overall-total-rent big-bold floatright fixed-col']"));

			int rentsubtotal = Generic_Class.getAmount(rent_subtotal.getText());

			int rentsub = Generic_Class.getAmount("0.0");

			int overalltotalrent = Generic_Class.getAmount(overall_total_rent.getText());

			if (overalltotalrent == rentsubtotal + rentsub) {
				logger.log(LogStatus.PASS, "Displayed total is same for Rent");
				logger.log(LogStatus.PASS, "Over all Total Rent is:  " + overalltotalrent);
				logger.log(LogStatus.PASS, "Addition of Rent Subtotal & Rent sub is:  " + (rentsubtotal + rentsub));
			} else {

				logger.log(LogStatus.PASS, "Displayed total is not same for Rent");
				logger.log(LogStatus.PASS, "Over all Total Rent is:  " + overalltotalrent);
				logger.log(LogStatus.PASS, "Addition of Rent Subtotal & Rent sub is:  " + (rentsubtotal + rentsub));
			}

			Thread.sleep(3000);
			CreateReservation res = new CreateReservation(driver);
			logger.log(LogStatus.PASS, "Create Reservation Page is displayed successfully");

			jse.executeScript("arguments[0].scrollIntoView()", res.btnMoveIndate());
			Thread.sleep(5000);
			jse.executeScript("arguments[0].click();", res.btnMoveIndate());
			logger.log(LogStatus.PASS, "Clicked on MoveIn Date button");
			Reporter.log("Clicked on MoveIn Date button", true);
			Thread.sleep(5000);

			res.SelectDateFromCalendar(tabledata.get("MoveInDate"));
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
			Thread.sleep(1000);

			// generics.Page_ScrollDown();
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);

			// Click on Create Reservation button
			res.clk_CreateReservationButton();
			Thread.sleep(3000);
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");

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
			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");

			Thread.sleep(2000);

			// Verify Information Msg "Please enter an employere number" Should
			// be displayed in red color.
			if (rserservationpopup.get_empErrorMessage().contains("Please enter an employee number.")) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"Error message verified successfully " + rserservationpopup.get_empErrorMessage());
				logger.log(LogStatus.INFO, "Error message verified successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Error message not verified successfully ");
				logger.log(LogStatus.INFO, "Error message not  verified successfully ", image);
			}

			Thread.sleep(2000);

			// Enter a invalid employee number
			rserservationpopup.enter_EmpID("1245687");
			logger.log(LogStatus.PASS, "Wrong Employee Number entered successfully ");

			Thread.sleep(2000);

			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			Thread.sleep(4000);

			Thread.sleep(2000);

			// Verify Information Msg "Please enter a valid employere number"
			// Should be displayed in red color.
			if (rserservationpopup.get_empErrorMessage().contains("Please enter a valid employee number.")) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"Error message verified successfully " + rserservationpopup.get_empErrorMessage());
				logger.log(LogStatus.INFO, "Error message verified successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Error message not verified successfully ");
				logger.log(LogStatus.INFO, "Error message not  verified successfully ", image);
			}

			String headcolor = driver
					.findElement(By
							.xpath("//div[@id='employeeNumber-wrapper']/div[@class='input-validation-message']//span"))
					.getCssValue("color");
			Thread.sleep(2000);
			System.out.println("Head Color:  " + headcolor);

			if (headcolor.equals("rgba(223, 32, 32, 1)")) {
				logger.log(LogStatus.PASS, "Error message is displayed in red color successfully");

			} else {
				logger.log(LogStatus.FAIL, "Error message is not displayed in red color");
			}

			// Enter a valid employere number and click "Create Reservation"
			// button
			rserservationpopup.clear_EmpID();
			Thread.sleep(2000);
			rserservationpopup.enter_EmpID(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "employee no entered successfully ");

			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			Thread.sleep(7000);

			try {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Handling Pop Up");
				logger.log(LogStatus.INFO, "Pop Handled", image);
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
			} catch (Exception e) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Pop Up Unavailable");
				logger.log(LogStatus.INFO, "Pop Not Available", image);
			}

			// Verify User is navigated to PM Dashboard Page
			if (tabledata.get("walkInCustTitle").contains(Pmpage.get_WlkInCustText().trim())) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "PM Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "PM Dashboard is displayed successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "PM Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "PM Dashboard is not displayed ", image);

			}

			// DB Validation
			String sqlQuery1 = " select count(*) from reservation where firstname = '" + FirstName
					+ "' and lastname = '" + LastName + "' ";
			String resultDB = DataBase_JDBC.executeSQLQuery(sqlQuery1);
			int record = Integer.parseInt(resultDB);

			if (record > 0) {
				logger.log(LogStatus.PASS, "Existing customer Business  Reservation validation successful");
			} else {
				logger.log(LogStatus.FAIL, "Existing customer Business  Reservation validation not successful");
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}
			Thread.sleep(4000);

			String sqlQuery2 = " select top 1 reservationid from reservation where firstname = '" + FirstName
					+ "' and lastname = '" + LastName + "' "
					+ " and siteid = (select siteid from site where sitenumber = " + location + ")"
					+ " order by lastupdate desc ";
			String DB_ReservationNo = DataBase_JDBC.executeSQLQuery(sqlQuery2);

			ArrayList<String> DB_ReservationData = DataBase_JDBC
					.executeSQLQuery_List("select top 1  p.phonenumber, e.email" + " from reservation r"
							+ " join phone p on p.phoneid = r.phoneid"
							+ " join emailaddress e on e.emailaddressid = r.emailaddressid"
							+ " where r.reservationid = '" + DB_ReservationNo + "'");
			Thread.sleep(3000);

			String phoneNumber = tabledata.get("PhoneAreacode") + tabledata.get("PhoneExchnge")
					+ tabledata.get("PhoneLneNo");
			String emailId = tabledata.get("Emailaddrs");

			if (phoneNumber.equalsIgnoreCase(DB_ReservationData.get(0))
					&& emailId.equalsIgnoreCase(DB_ReservationData.get(1))) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Phone Number and Email Id are matching with UI and DB");
				logger.log(LogStatus.INFO, "Phone Number and Email Id are matching with UI and DB", image);
				logger.log(LogStatus.INFO,
						"Expected Values: Phone Number-" + phoneNumber + "Expected Values: Email Id-" + emailId);
				logger.log(LogStatus.INFO, "Actual Values: Phone Number-" + DB_ReservationData.get(0)
						+ "Expected Values: Email Id-" + DB_ReservationData.get(1));
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Phone Number and Email Id are not matching with UI and DB");
				logger.log(LogStatus.FAIL, "Phone Number and Email Id are not matching with UI and DB", image);
				logger.log(LogStatus.INFO,
						"Expected Values: Phone Number-" + phoneNumber + "Expected Values: Email Id-" + emailId);
				logger.log(LogStatus.INFO, "Actual Values: Phone Number-" + DB_ReservationData.get(0)
						+ "Expected Values: Email Id-" + DB_ReservationData.get(1));
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception " + ex);
		}

	}

	@DataProvider
	public Object[][] getData() {

		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"CreateReservation_NewCustomer_Individual_PreReservation");
	}

}
