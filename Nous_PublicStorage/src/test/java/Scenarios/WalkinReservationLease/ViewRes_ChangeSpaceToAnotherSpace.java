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
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.SwapSpacePage;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import Scenarios.Browser_Factory;

public class ViewRes_ChangeSpaceToAnotherSpace extends Browser_Factory {

	public ExtentTest logger;
	String path = "./src/main/resources/Resources/PS_TestData.xlsx";
	String resultFlag = "pass";
	String screen;
	String im;
	WebDriverWait wait;

	@DataProvider
	public Object[][] getViewReservationData() {
		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"ViewRes_ChangeSpaceToAnotherSpace");
	}

	@Test(dataProvider = "getViewReservationData")
	public void ViewReservationChangeSpace(Hashtable<String, String> tabledata) throws InterruptedException {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		try {

			// Login to PS Application
			logger = extent.startTest("ViewRes_ChangeSpaceToAnotherSpace",
					"View Reservation Change Space to Another Space");
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
		

			if (pmhomepage.get_WlkInCustText().equals(tabledata.get("walkInCustTitle"))) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"Walk-In Customer module is displayed successfully:" + pmhomepage.get_WlkInCustText());
				logger.log(LogStatus.INFO, "Walk-In Customer module is displayed successfully", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Walk-In Customer module is not displayed");
				logger.log(LogStatus.INFO, "Walk-In Customer module is not displayed", image);

			}

			// Enter Valid Reservation id and Click on "Find Reservation" button
			PM_Homepage PM_HomePage = new PM_Homepage(driver);
			String location = PM_HomePage.getLocation();

			String DB_ReservationNo = DataBase_JDBC.executeSQLQuery("select top 1 reservationid from reservation"
					+ " where SiteID = (select siteid from site where sitenumber = '" + location + "')"
					+ " and reservationstatustypeid = '127'" + " order by NEWID()");
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Fetching Reservation Number:" + DB_ReservationNo + " from DB");

			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "PM Dash board Displayed successfully", im);

			PM_HomePage.enter_NameOrResvtn(DB_ReservationNo);
			logger.log(LogStatus.INFO, "Enter Reservation number");
			Thread.sleep(2000);

			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "Entered Account Number" + DB_ReservationNo + "successfully", im);

			PM_HomePage.clk_findReservation();
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

			// Clicking on Edit button in Space Information Section
			ViewReservationPage viewReserv = new ViewReservationPage(driver);
			

			
			/*logger.log(LogStatus.PASS, "Click on Change button in the Space Information section");
			Thread.sleep(3000);

			jse.executeScript("arguments[0].scrollIntoView()", viewReserv.clk_EditBtnInSpaceInfo());
			Thread.sleep(2000);
			jse.executeScript("arguments[0].click();", viewReserv.clk_EditBtnInSpaceInfo());
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Clicked on Edit Button in the Space Information");

			wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath("//span[@id='chooseSizeDialog_wnd_title']"))));

			// Verify the Swap Space Model Window displaying after clicking on
			// edit button
			SwapSpacePage swapSpace = new SwapSpacePage(driver);
			logger.log(LogStatus.INFO, "Swap Space Page object is created");

			if (swapSpace.isDisplayed_SwapSpace()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Swap Space Model window is displayed successfully");
				logger.log(LogStatus.INFO, "Swap Space Model window is displayed successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Swap Space Model window is not displayed");
				logger.log(LogStatus.INFO, "Swap Space Model window is not displayed", image);
			}

			// Verify New Customer Radio button is selected by default
			StandardStoragePage StandardStoragePage = new StandardStoragePage(driver);
			Thread.sleep(5000);

			if (StandardStoragePage.isdisplayed_yesradiobutton()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "New customer Radio button is selected by default in StandardStoragePage");
				logger.log(LogStatus.INFO, "New customer Radio button is selected by default in StandardStoragePage",
						image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL,
						"New customer Radio button is not selected by default in StandardStoragePage");
				logger.log(LogStatus.INFO,
						"New customer Radio button is not selected by default in StandardStoragePage", image);
			}

			StandardStoragePage.Clk_ChkBx_AvlSpace();
			logger.log(LogStatus.INFO, "Clicked on available spaces checkbox in StandardStorage");

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
					+ " AND CAST(CAST(ISNULL(Width, 0) AS integer) AS varchar)+'X'+CAST(CAST(ISNULL(Length, 0) AS integer) AS varchar) Like '"
					+ size + "'"
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

			// Click On Other Location tab
			int lastRadioBtn = (avlSpaces.size());
			System.out.println("Last Radio Button Count is:  " + lastRadioBtn);

			String path = "//div[@id='unitgridtabstrip']//tbody/tr[" + lastRadioBtn + "]/td[1]/input";
			System.out.println("Path is:  " + path);

			WebElement RdBtn_Space = driver.findElement(By.xpath(path));
			logger.log(LogStatus.PASS,
					"check the radio button based on the space and click on the  reservation button");

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", RdBtn_Space);
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", RdBtn_Space);
			Thread.sleep(3000);

			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "Radio Button is selected successfully", im);

			// generics.Page_ScrollDown();
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
*/
			

			/*((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			jse.executeScript("arguments[0].scrollIntoView()", swapSpace.clk_SwapSpaceButton());
			Thread.sleep(3000);
			jse.executeScript("arguments[0].click();", swapSpace.clk_SwapSpaceButton());
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on Swap Space button successfully");
			Thread.sleep(15000);

			try {
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(3000);
			} catch (Exception e) {
				Reporter.log("No Javascript Popup, so Continuing!!!");
			}*/

			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "Image", im);
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "Image", im);
			logger.log(LogStatus.INFO, "Clicked on Reassign Space button");
			viewReserv.click_ReassignButton();
			Thread.sleep(5000);
			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "Image", im);
			viewReserv.selectSpaceAssignmentReason("Location Visit");
			Thread.sleep(2000);
			viewReserv.selectSpace_Reassign();
			Thread.sleep(2000);
			viewReserv.enterEmployeeId_Reassign(tabledata.get("UserName"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected the reason, space and entered employee id");
			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "Image", im);
			
			WebElement space = driver.findElement(By.xpath("(//ul[contains(@class,'spaces-info-container')]//label[@class='webchamp-radio-button']/span[@class='button'])[1]/following-sibling::span"));
			String spaceNum = space.getText();
			logger.log(LogStatus.INFO, "Selected the space: "+spaceNum);
			viewReserv.assignSelectedSpace();
			logger.log(LogStatus.INFO, "Clicked on Assign Selected Space button");
			Thread.sleep(45000);
			try {
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(8000);
			} catch (Exception e) {
				Reporter.log("No Javascript Popup, so Continuing!!!");
			}
			
			
			List<WebElement> monthlyRentInSpace = driver
					.findElements(By.xpath("//div[@id='spaceInformation']//td[@class='grid-cell-rent cost']"));
			List<WebElement> spaceSizeInSpace = driver
					.findElements(By.xpath("//table[contains(@role,'treegrid')]//td[@class='grid-cell-size']"));

			String actMonthlyRentAfterSwappingSpace = monthlyRentInSpace.get(0).getText();
			String actSpaceSizeAfterSwappingSpace = spaceSizeInSpace.get(0).getText().trim();

			screen = Generic_Class.takeScreenShotPath();
			im = logger.addScreenCapture(screen);
			//logger.log(LogStatus.PASS, "Captured Rent & Size After Values successfully", im);
			/*logger.log(LogStatus.PASS,
					"Actual Monthly Rent After Swapping is:---->  " + actMonthlyRentAfterSwappingSpace);*/
			/*logger.log(LogStatus.PASS,
					"Space Size After Swapping is:---->  " + actSpaceSizeAfterSwappingSpace);*/

			//logger.log(LogStatus.INFO, "Validating Monthly Rent Before & After Swapping of Spaces");

			/*// Verify the amount after swapping is correctly displaying
			if (Generic_Class.getAmount(expMonthlyRentBeforeSwappingSpace) == Generic_Class
					.getAmount(actMonthlyRentAfterSwappingSpace)) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Monthly Rent is changed according to the space swapped");
				logger.log(LogStatus.INFO, "Monthly Rent is changed according to the space swapped", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Monthly Rent is not changed according to the space swapped");
				logger.log(LogStatus.INFO, "Monthly Rent is not changed according to the space swapped", image);
			}*/

			String[] actualsize = actSpaceSizeAfterSwappingSpace.split("\n");

			/*logger.log(LogStatus.INFO, "Validating Space Sizes Before & After Swapping of Spaces");*/
			String changedSpaceNum = driver.findElement(By.xpath("//div[@id='spaceInformation']//table/tbody//div[@class='unit-grid-link unit-grid-link-rental-unit']")).getText();

			// Verify the space after swapping is correctly displaying
			if (spaceNum.equalsIgnoreCase(changedSpaceNum)) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Swapped space after swapping matches successfully");
				logger.log(LogStatus.INFO, "Swapped space after swapping matches successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Swapped space after swapping doesn't match");
				logger.log(LogStatus.INFO, "Swapped space after swapping dosen't match", image);
			}

			Thread.sleep(5000);

			// Clicking on Cancel Reservation Button
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);

			try {
				wait.until(ExpectedConditions.elementToBeClickable(viewReserv.cancel()));
				Thread.sleep(3000);
			} catch (Exception e) {
				Reporter.log("No Javascript Popup, so Continuing!!!");
			}

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", viewReserv.cancel());
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewReserv.cancel());
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on Cancel Reservation button");
			Thread.sleep(3000);

			// Verify the popup window is displaying
			if (viewReserv.isDisplayedCancelTitle()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Cancel Reservation Pop up is displayed");
				logger.log(LogStatus.INFO, "Cancel Reservation Pop up is displayed", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Cancel Reservation Pop up is not displayed");
				logger.log(LogStatus.INFO, "Cancel Reservation Pop up is not displayed", image);
			}

			Thread.sleep(8000);
			try {
				viewReserv.clk_CancelResBtn();
			} catch (Exception e) {
				Reporter.log("Unable to click on cancel button", true);
			}
			
			logger.log(LogStatus.INFO, "Clicked Cancel Button and returned to view reservation page");
			Thread.sleep(2000);

			// Click on Back To Dashboard button
			StandardStoragePage StandardStoragePage = new StandardStoragePage(driver);
			StandardStoragePage.click_BackToDashboard();
			logger.log(LogStatus.INFO, "Clicked on Back to dashboard button");
			Thread.sleep(10000);

		} catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception " + ex);
		}
	}

	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "ViewRes_ChangeSpaceToAnotherSpace", "Status",
					"Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "ViewRes_ChangeSpaceToAnotherSpace", "Status",
					"Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "ViewRes_ChangeSpaceToAnotherSpace", "Status",
					"Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);

	}

}
