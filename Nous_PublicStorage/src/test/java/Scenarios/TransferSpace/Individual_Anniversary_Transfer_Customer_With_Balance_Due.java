package Scenarios.TransferSpace;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
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
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.Cust_PaymentDueDetailsPage;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Pages.TransferSpacePages.TransferSelectSpacePage;
import Pages.TransferSpacePages.TransferSpaceDetails;
import Pages.Walkin_Reservation_Lease.Leasing_AuthorizedAccessContactsPage;
import Pages.Walkin_Reservation_Lease.Leasing_ContactInfoPage;
import Pages.Walkin_Reservation_Lease.Leasing_EmergencyConatctsPage;
import Pages.Walkin_Reservation_Lease.Leasing_LeaseQuestionairePage;
import Pages.Walkin_Reservation_Lease.Leasing_MilitaryInformationPage;
import Pages.Walkin_Reservation_Lease.Leasing_PaymentMethodsPage;
import Pages.Walkin_Reservation_Lease.Leasing_ReviewNApprovePage;
import Scenarios.Browser_Factory;

public class Individual_Anniversary_Transfer_Customer_With_Balance_Due extends Browser_Factory {

	public ExtentTest logger;

	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";
	WebDriverWait wait;
	String firstName;
	String accountNumber;
	String siteid_tobeset;
	String rentalUnitID;
	String rentalUnitNumber;

	@DataProvider
	public Object[][] getData() {
		return Excel.getCellValue_inlist(path, "TransferSpace", "TransferSpace",
				"Individual_Anniversary_Transfer_Customer_With_Balance_Due");
	}

	@Test(dataProvider = "getData")
	public void Individual_Anniversary_Transfer_Customer_With_Balance_Due(Hashtable<String, String> tabledata)
			throws InterruptedException {
		try {

			logger = extent.startTest("Individual_Anniversary_Transfer_Customer_With_Balance_Due",
					"Individual_Anniversary_Transfer_Customer_With_Balance_Due starts ");

			wait = new WebDriverWait(driver, 60);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			Robot robot = new Robot();

			if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("TransferSpace").equals("Y"))) {
				resultFlag = "skip";
				throw new SkipException("Skipping the test");
			}

			// Login To the Application
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Login to Application  with DM  successfully");
			Thread.sleep(5000);

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

			// 50% Off + 15% Limited Time Discount

			String sqlQuery = "select aoip.promotionid, p.name, u.unitcost as MonthlyRent,aoipo.unitcost as Insurance,soi.rentdueday,soi.paymentmethodtypeid,u.* "
					+ "from vw_unitdetails u join accountorderitempromotion aoip "
					+ "on u.accountorderitemid=aoip.accountorderitemid and aoip.expirationdate > getdate() "
					+ "left join accountorderitemproductoption aoipo "
					+ "on u.accountorderitemid=aoipo.accountorderitemid and aoipo.productoptionid='2' "
					+ "join promotion p on aoip.promotionid = p.promotionid "
					+ "join storageorderitem soi on u.storageorderitemid=soi.storageorderitemid  and soi.paymentmethodtypeid = '101' "
					+ "where  u.moveindate > getdate()-2 and u.vacatedate is null and u.paidthrudate <getdate() "
					+ "and soi.rentdueday>'1' and u.customertypeid = '90' "
					+ "and p.name = '50% Off + 15% Limited Time Discount' order by u.siteid,u.moveindate";

			try {

				ArrayList<String> results = DataBase_JDBC.executeSQLQuery_List(sqlQuery);
				System.out.println(results);

				siteid_tobeset = results.get(7);
				logger.log(LogStatus.INFO, "Site id related to account & to be set is:  " + siteid_tobeset);
				System.out.println("Site Id to be set is:   " + siteid_tobeset);

				accountNumber = results.get(14);
				logger.log(LogStatus.INFO, "Account Number fetched from DB is: " + accountNumber);
				System.out.println("Account Number is:   " + accountNumber);

				rentalUnitNumber = results.get(15);
				logger.log(LogStatus.INFO, "Rental Unit Number fetched from DB is: " + rentalUnitNumber);
				System.out.println("Rental Unit Number is:   " + rentalUnitNumber);

				rentalUnitID = results.get(16);
				logger.log(LogStatus.INFO, "Rental Unit ID fetched from DB is: " + rentalUnitID);
				System.out.println("Rental Unit Id is:   " + rentalUnitID);

			} catch (Exception e) {
				logger.log(LogStatus.INFO, "No Details are fetched from database query");
			}

			// Fetching IP Address & changing it accordingly

			String ipadd = Generic_Class.getIPAddress();
			Thread.sleep(1000);

			String current_siteid_query = "select SiteID from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"
					+ ipadd + "'";
			String current_siteid = DataBase_JDBC.executeSQLQuery(current_siteid_query);
			System.out.println("Current Site Id is:   " + current_siteid);

			if (!(current_siteid.equals(siteid_tobeset))) {

				// Make the currently assigned site id to null
				String allocate_currentsiteidtonull_query = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"
						+ current_siteid + "'";
				DataBase_JDBC.executeSQLQuery(allocate_currentsiteidtonull_query);

				// Allocate new site id to current ip address
				String allocateip_to_newid = "Update siteparameter set paramvalue='" + ipadd
						+ "' where paramcode='IP_COMPUTER_FIRST' and siteid='" + siteid_tobeset + "'";
				DataBase_JDBC.executeSQLQuery(allocateip_to_newid);

				/*
				 * Logout and login back to change according to site id set
				 * 
				 */

				Actions act = new Actions(driver);

				WebElement user = driver.findElement(By.xpath(("//div[@id='usernav']")));
				WebElement logoff1 = driver.findElement(By.xpath("//a[contains(text(),'Log Off')]"));

				act.moveToElement(user).build().perform();
				jse.executeScript("arguments[0].click();", logoff1);
				Thread.sleep(5000);

				WebElement logoff2 = driver.findElement(
						By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'Log Off')]"));
				jse.executeScript("arguments[0].click();", logoff2);
				Thread.sleep(8000);

				loginPage.login(tabledata.get("UserName"), tabledata.get("Password"));
				logger.log(LogStatus.INFO, "Click on Login button successfully");

			}

			PM_Homepage PM_HomePage = new PM_Homepage(driver);
			logger.log(LogStatus.PASS, "User sucessfully logged into application");

			if (PM_HomePage.isFindAndLeaseButtonDisplayed()) {
				logger.log(LogStatus.PASS, "Find and Lease button displayed sucessfully on Walk in customer window");
			} else {
				logger.log(LogStatus.FAIL,
						"Find and Lease button is not displayed sucessfully on Walk in customer window");
			}

			if (PM_HomePage.isFindAnExtReservationTextFieldDisplayed()) {
				logger.log(LogStatus.PASS,
						"Or Find an Existing Reservation text field is dispalyed sucessfully on Walk in customer window");
			} else {
				logger.log(LogStatus.FAIL,
						"Or Find an Existing Reservation text field is not dispalyed sucessfully on Walk in customer window");
			}

			if (PM_HomePage.isFindReservationDispalyed()) {
				logger.log(LogStatus.PASS,
						"Find reservation button is dispalyed sucessfully on Walk in customer window");
			} else {
				logger.log(LogStatus.FAIL,
						"Find reservation button is not dispalyed sucessfully on Walk in customer window");
			}

			PM_HomePage.clk_AdvSearchLnk();
			logger.log(LogStatus.PASS, "clicked on advanced search link in the PM Dashboard sucessfully");

			Advance_Search advSearch = new Advance_Search(driver);
			logger.log(LogStatus.PASS, "creating object for advance search page sucessfully");

			Thread.sleep(4000);
			String SiteNumber = advSearch.getLocationNum();
			logger.log(LogStatus.PASS, "location number is:" + SiteNumber);

			// Need to get query & add code for getting account number
			Thread.sleep(1000);

			if (advSearch.verifyAdvSearchPageIsOpened()) {
				logger.log(LogStatus.PASS, "Advanced Search page is opened");
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);

			} else {
				logger.log(LogStatus.PASS, "Advanced Search page is not opened");
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
				resultFlag = "fail";
			}

			advSearch.enterAccNum(accountNumber);
			logger.log(LogStatus.INFO, "Enter existing customer Account Number in Account field successfully");

			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(1000);

			advSearch.clk_SearchAccount();
			logger.log(LogStatus.INFO, "Entered into advance search page");
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Clicked Search button successfully");
			Thread.sleep(1000);

			Cust_AccDetailsPage cust = new Cust_AccDetailsPage(driver);

			if (cust.isCustdbTitleDisplayed()) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "customer Dashboard is displayed successfully", image);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "customer Dashboard is not displayed ", image);
			}
			Thread.sleep(5000);

			cust.clickSpaceDetails_tab();
			logger.log(LogStatus.INFO, "Clicked on Space Details tab successfully");
			Thread.sleep(4000);

			String gateCodeBeforeTransfer = driver
					.findElement(By.xpath("//div[text()='Gate Code:']/following-sibling::div")).getText().trim();
			logger.log(LogStatus.INFO, "Gate Code Before Transfer is:  " + gateCodeBeforeTransfer);

			String promotionOffer = driver
					.findElement(By.xpath("//div[text()='Move-In Promotion:']/following-sibling::div/div")).getText()
					.trim();
			logger.log(LogStatus.INFO, "Promotion Offer is:  " + promotionOffer);

			if (promotionOffer.equals("50% Off + 15% Limited Time Discount")) {
				logger.log(LogStatus.PASS, "Promotion is displayed successfully");
			} else {
				logger.log(LogStatus.FAIL, "Promotion is not displayed successfully!!!");
			}

			Acc_SpaceDetailsPage accSpaceDetails = new Acc_SpaceDetailsPage(driver);
			accSpaceDetails.clickTransfer_Btn(driver);
			logger.log(LogStatus.INFO, "Clicked on Transfer button successfully");
			Thread.sleep(5000);

			TransferSpaceDetails transferspace = new TransferSpaceDetails(driver);

			transferspace.clk_viewDetails();
			logger.log(LogStatus.INFO, "Clicked on View Details tab successfully");

			transferspace.clk_viewDetailsCloseBtn();
			logger.log(LogStatus.INFO, "Clicked on Close Details tab successfully");

			String actualDateAvailable = transferspace.getCurrentDate();

			if (actualDateAvailable.equals(transferspace.getCalendarDate())) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Planned Vacate Date is Current Date");
				logger.log(LogStatus.INFO, "Planned Vacate Date is Current Date", image);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Planned Vacate Date is not Current Date");
				logger.log(LogStatus.INFO, "Planned Vacate Date is not Current Date", image);
			}

			if (transferspace.isErrorMessageAvailable() == true) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Message is available");
				logger.log(LogStatus.INFO, "Message is available", image);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Message is not available when planned date is vacate date");
				logger.log(LogStatus.INFO, "Message is not available when planned date is vacate date", image);
			}

			String additionalChargeForCurrentVacateDate = transferspace.getAdditionCharge();
			logger.log(LogStatus.INFO, "Captured Additional Charge Amount:  " + additionalChargeForCurrentVacateDate);
			Thread.sleep(1000);

			String additionalDaysForCurrentVacateDate = transferspace.getAdditionDays();
			logger.log(LogStatus.INFO,
					"Captured Additional Days as per calender:  " + additionalDaysForCurrentVacateDate);
			Thread.sleep(1000);

			transferspace.clk_maintenanceYesRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on Maintenance yes radio button successfully");
			Thread.sleep(2000);

			if (transferspace.isKeepRateCheckBoxDisplayed() == true) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "keep Rate Checkbox is available");
				logger.log(LogStatus.INFO, "keep Rate Checkbox is available", image);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "keep Rate Checkbox is not available");
				logger.log(LogStatus.INFO, "keep Rate Checkbox is not available", image);
			}

			transferspace.selectMaintenanceTypeOptions(driver, tabledata.get("Maintenance_Type"));
			logger.log(LogStatus.INFO,
					"Selected Maintenance type Option " + tabledata.get("Maintenance_Type") + "successfully");
			Thread.sleep(2000);

			transferspace.clk_salesPricingChanelChkbx();
			logger.log(LogStatus.INFO, "Clicked on Sales Channel Pricing Checkbox successfully");
			Thread.sleep(1000);

			transferspace.clk_findSpaceBtn();
			logger.log(LogStatus.INFO, "Clicked on find space button successfully");
			Thread.sleep(1000);

			TransferSelectSpacePage transferSelectSpacePage = new TransferSelectSpacePage(driver);

			transferSelectSpacePage.Clk_OnAvlSpace();
			logger.log(LogStatus.INFO, "Clicked on Availble Space Checkbox successfully");
			Thread.sleep(1000);

			transferSelectSpacePage.clk_OnSearchBtn();
			logger.log(LogStatus.INFO, "Clicked on Search Button successfully");
			Thread.sleep(5000);

			transferSelectSpacePage.clickSpaceChkbx(driver);
			logger.log(LogStatus.INFO, "Clicked on Available spaces checkboxes Successfully");
			Thread.sleep(5000);

			transferSelectSpacePage.clk_OnSelectSpaceBtn(driver);
			logger.log(LogStatus.INFO, "Clicked on select Space button Successfully");
			Thread.sleep(8000);

			jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			logger.log(LogStatus.INFO, "Scrolled to page height");
			Thread.sleep(2000);

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(3000);

			transferspace.clk_confirmWithCustomerLink();
			logger.log(LogStatus.INFO, "Clicked on Confirm With Customer button Successfully");
			Thread.sleep(3000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(3000);

			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.PASS, "Switched to Customer Screen");
			Reporter.log("Switched to Customer Screen", true);
			Thread.sleep(10000);

			driver.findElement(By.xpath("//button[@id='confirmButton']")).click();
			logger.log(LogStatus.PASS, "Clicked on Confirm button in customer screen");
			Reporter.log("Clicked on Confirm button in customer screen", true);
			Thread.sleep(10000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(3000);

			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.PASS, "Switching back to screen");
			Reporter.log("Switching back to screen", true);
			Thread.sleep(10000);

			String source = Generic_Class.takeScreenShotPath();
			String picture = logger.addScreenCapture(source);
			logger.log(LogStatus.PASS, "Swapping Spaces confirmation page displayed successfully");
			logger.log(LogStatus.INFO, "Swapping Spaces confirmation page displayed successfully", picture);

			/*
			 * Capturing Transfer From Space Details & Transfer To Space Details
			 */

			List<WebElement> transferFromDetails = driver
					.findElements(By.xpath("//div[@id='transferFromGrid']//div[@class='bold']"));

			String transferFromSpaceNumber = transferFromDetails.get(0).getText();
			String transferFromSpaceRent = transferFromDetails.get(1).getText();

			Reporter.log("Transfer From Sapce Number is:  " + transferFromSpaceNumber, true);
			Reporter.log("Transfer From Sapce Rent is:  " + transferFromSpaceRent, true);

			List<WebElement> transferToDetails = driver
					.findElements(By.xpath("//div[@id='transferToGrid']//div[@class='bold']"));

			String transferToSpaceNumber = transferToDetails.get(0).getText();
			String transferToSpaceRent = transferToDetails.get(1).getText();

			Reporter.log("Transfer To Sapce Number is:  " + transferToSpaceNumber, true);
			Reporter.log("Transfer To Sapce Rent is:  " + transferToSpaceRent, true);

			transferspace.clk_onSubmitBtn();
			logger.log(LogStatus.INFO, "Clicked on Submit button Successfully");

			Leasing_ContactInfoPage contactinfo = new Leasing_ContactInfoPage(driver);

			// Selecting Active Military Check boxes Yes or No

			contactinfo.clk_ActiveDutyMilitaryYesRadioBtn(driver);
			// contactinfo.clk_ActiveDutyMilitaryNoRadioBtn(driver);

			logger.log(LogStatus.INFO, "clicked on Active Military No Radio button successfully");
			Thread.sleep(2000);

			jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);

			driver.findElement(By.xpath("//a[@id='confirmWithCustomerButton']/span")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm with cust button successfully");
			Thread.sleep(5000);

			// Navigating to CFS
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(3000);

			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(10000);

			driver.findElement(By.xpath("//button[@id='confirmButton']")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");
			Thread.sleep(10000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(3000);

			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");
			Thread.sleep(10000);

			Leasing_EmergencyConatctsPage emergCon = new Leasing_EmergencyConatctsPage(driver);
			Thread.sleep(4000);

			jse.executeScript("window.scrollBy(0,400)", "");
			Thread.sleep(4000);

			emergCon.click_custDeclines_chkBox();
			Thread.sleep(3000);

			jse.executeScript("window.scrollBy(0,-1000)", "");
			emergCon.clickconfirmWithCust();
			// driver.findElement(By.id("confirmWithCustomer")).click();
			Thread.sleep(4000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(8000);

			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(10000);

			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");
			Thread.sleep(10000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully>>>>>>>>>>>>>>>>>>");
			Reporter.log("Switch to Main page successfully>>>>>>>>>>>>>>>>>", true);
			Thread.sleep(10000);

			// ====Authorized Access Contacts======

			Leasing_AuthorizedAccessContactsPage autContact = new Leasing_AuthorizedAccessContactsPage(driver);
			jse.executeScript("window.scrollBy(0,-300)", "");
			Thread.sleep(10000);

			try {
				List<WebElement> removeButtons = driver.findElements(By.xpath("//a[text()='Remove']"));
				for (int i = 0; i < removeButtons.size() - 1; i++) {
					removeButtons.get(i).click();
					Thread.sleep(500);
				}
			} catch (Exception e) {
				Reporter.log("No Extra Contacts are available!!!");
			}

			autContact.enterFirstName(tabledata.get("authFirstname"));
			Thread.sleep(1000);

			autContact.enterLastName(tabledata.get("authLastName"));
			Thread.sleep(1000);

			autContact.clickPhonelist();
			Thread.sleep(2000);
			List<WebElement> phone = driver.findElements(By.xpath(
					"//ul[@id='AuthorizedUsersForm_AuthorizedUsers__-index-__0__Phone_PhoneTypeID_listbox']/li"));
			for (WebElement ele : phone) {
				if (ele.getText().contains(tabledata.get("Auth_PhoneType"))) {
					ele.click();
				}
			}
			Thread.sleep(2000);

			autContact.enterAreacode(tabledata.get("Areacode"));
			Thread.sleep(1000);

			autContact.enterExchange(tabledata.get("Exchange"));
			Thread.sleep(1000);

			autContact.enterLineNum(tabledata.get("LineNumber"));
			Thread.sleep(1000);

			autContact.clickConfirmCust();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(8000);

			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(10000);

			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");
			Thread.sleep(10000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");
			Thread.sleep(10000);

			// =====Eligible Promotions=====

			// Thread.sleep(8000);
			// Leasing_EligiblePromotionsPage eligpromo = new
			// Leasing_EligiblePromotionsPage(driver);

			// logger.log(LogStatus.PASS, "Validating Monthly rent and
			// Promotions in Eligible Promotion Page");
			// Thread.sleep(4000);

			// String SubTotalMonthlyRentAmt =
			// eligpromo.get_SubTotalMonthlyRentAmt().substring(1).replace(",",
			// "");

			// String SubMoveInCostAmt =
			// eligpromo.get_SubMoveInCostAmt().substring(1).replace(",", "");
			// Thread.sleep(4000);

			// eligpromo.clickSavenProceed();
			// Thread.sleep(8000);

			// ======Lease Questionaire=====

			Leasing_LeaseQuestionairePage leaseQues = new Leasing_LeaseQuestionairePage(driver);

			leaseQues.clickStorageContent();
			Thread.sleep(5000);

			List<WebElement> allstorage = driver.findElements(By.xpath(
					"//ul[@id='LeaseQuestionnaireUnits_0__RentalUnitContentsTypeID_listbox']//li[@class='k-item']"));

			for (WebElement storage : allstorage) {
				if (tabledata.get("StorageContent").equalsIgnoreCase(storage.getText())) {
					storage.click();
					break;
				}
			}
			Thread.sleep(5000);

			// Selecting Insurance as "Yes"

			leaseQues.clickAddInsuranceYes();
			Thread.sleep(1000);

			leaseQues.clickCoverageList();
			Thread.sleep(2000);

			driver.findElement(By.xpath("//ul[@id='LeaseQuestionnaireUnits_0__InsuranceSelection_listbox']/li[2]"))
					.click();
			Thread.sleep(2000);

			leaseQues.clickAccessZone();
			Thread.sleep(2000);

			driver.findElement(
					By.xpath("//ul[@id='LeaseQuestionnaireUnits_0__GateControllerTimeZoneID_listbox']//li[2]")).click();
			Thread.sleep(2000);

			try {
				leaseQues.clickKeypadZone();
				Thread.sleep(2000);

				driver.findElement(
						By.xpath("//li[contains(@id,'KeypadZone_option_selected')]/following-sibling::li[1]")).click();
				Thread.sleep(3000);

			} catch (Exception e) {
				Reporter.log("Keypad Zone is unavailable, so continuing!!");
			}

			leaseQues.clickSaveAndProceedBtn();
			Thread.sleep(10000);

			// ======Military Information=====

			Leasing_MilitaryInformationPage military = new Leasing_MilitaryInformationPage(driver);

			if (military.isMilitaryInfoPageDisplayed()) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Military information page is displayed successfully");
				logger.log(LogStatus.INFO, "Military information page is displayed successfully", image);

			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Military information page is not displayed");
				logger.log(LogStatus.INFO, "Military information page is not displayed", image);
			}
			Thread.sleep(1000);

			Reporter.log("Switch to Main page successfully>>>>>>>>>>>>military>>>>>", true);
			military.enterBirthDate(tabledata.get("BirthDate"));
			logger.log(LogStatus.PASS, "enter date of birth");
			Thread.sleep(2000);

			military.enterMilitaryID(tabledata.get("MilitaryID"));
			logger.log(LogStatus.PASS, "enter military id");
			Thread.sleep(2000);

			military.enterActualStratdate(tabledata.get("ActiveDutyStartDate"));
			logger.log(LogStatus.PASS, "enter active military start date");
			Thread.sleep(2000);

			military.enterUnitName(tabledata.get("UnitName"));
			logger.log(LogStatus.PASS, "enter unit name");
			Thread.sleep(2000);

			military.click_militaryBranch();
			Thread.sleep(2000);

			List<WebElement> elements = driver
					.findElements(By.xpath("//ul[@id='Form_MilitaryBranchTypeId_listbox']/li"));
			for (WebElement ele : elements) {
				if (ele.getText().trim().contains(tabledata.get("MilitaryBranch"))) {
					ele.click();
					break;
				}
			}
			logger.log(LogStatus.PASS, "selecting the military branch from dropdown");
			Thread.sleep(2000);

			military.enterCommandingOfficerFName(tabledata.get("CommandingFirstName"));
			logger.log(LogStatus.PASS, "entering rank");
			Thread.sleep(1000);

			military.enterCommandingOfficerLName(tabledata.get("CommandingLastName"));
			logger.log(LogStatus.PASS, "entering rank");
			Thread.sleep(1000);

			military.enterRank(tabledata.get("Rank"));
			logger.log(LogStatus.PASS, "entering rank");
			Thread.sleep(1000);

			military.selectPhoneType("Cell", driver);
			Thread.sleep(1000);

			military.txt_AreaCode(tabledata.get("Areacode"));
			Thread.sleep(1000);

			military.txt_Exchg(tabledata.get("Exchange"));
			Thread.sleep(1000);

			military.txt_lineNumber(tabledata.get("LineNumber"));
			Thread.sleep(1000);

			military.clickConfirmCust_btn();
			Thread.sleep(10000);
			logger.log(LogStatus.INFO, "clicking on Confirm with cust button successfully");

			// Navigating to Customer screen

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(10000);

			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");
			Thread.sleep(10000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");

			// ====clicking multiple check boxes for second====

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(10000);

			List<WebElement> allCheckbox = driver.findElements(
					By.xpath("//section[@class='term-group term-group--active']//input[@type='checkbox']"));

			for (WebElement checkbox : allCheckbox) {
				checkbox.click();
			}
			Thread.sleep(5000);

			driver.findElement(By.id("confirmButton")).click();
			Thread.sleep(8000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

			driver.switchTo().window(tabs.get(0));
			Thread.sleep(10000);

			// Leasing_ReviewNApprovePage review = new
			// Leasing_ReviewNApprovePage(driver);
			// review.clickApprove_btn();
			// Thread.sleep(10000);

			// ===clicking multiple check boxes for Third====

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			List<WebElement> allCheckbox2 = driver.findElements(
					By.xpath("//section[@class='term-group term-group--active']//input[@type='checkbox']"));

			for (WebElement checkbox : allCheckbox2) {
				checkbox.click();
			}
			Thread.sleep(5000);
			driver.findElement(By.id("confirmButton")).click();

			Thread.sleep(8000);

			WebElement signature2 = driver
					.findElement(By.xpath("//div[@class='sig sigWrapper']/canvas[@class='pad js-signature-canvas']"));
			Actions actionBuilder2 = new Actions(driver);
			Action drawAction2 = actionBuilder2.moveToElement(signature2, 660, 96).click().clickAndHold(signature2)
					.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature2).build();
			drawAction2.perform();

			Thread.sleep(5000);
			driver.findElement(By.id("confirmButton")).click();
			Thread.sleep(5000);

			// driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(6000);

			driver.switchTo().window(tabs.get(0));

			Leasing_ReviewNApprovePage review2 = new Leasing_ReviewNApprovePage(driver);

			Thread.sleep(8000);
			review2.clickApprove_btn();

			Thread.sleep(6000);

			driver.findElement(By.id("PaymentButton")).click();

			Thread.sleep(6000);

			PaymentsPage pyment = new PaymentsPage(driver);

			Thread.sleep(10000);
			logger.log(LogStatus.PASS, "Validating Monthly rent and Promotions in Eligible Promotion Page");
			Thread.sleep(2000);
			pyment.clickOnConfirmWithCustomer_Btn();
			Thread.sleep(5000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(3000);

			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");

			Thread.sleep(5000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(3000);

			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");
			Thread.sleep(8000);

			Leasing_PaymentMethodsPage payment = new Leasing_PaymentMethodsPage(driver);
			logger.log(LogStatus.PASS, "creating object for the Login Page sucessfully ");
			Thread.sleep(2000);

			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Payment page is dispalyed sucessfully ");
			logger.log(LogStatus.INFO, "Payment page is dispalyed sucessfully", image);

			String MonthlyRentDueNowAmount = payment.get_MonthlyRentDueNowAmt();
			logger.log(LogStatus.PASS,
					"Monthly Rent Due Now amount in payment page is------>:" + MonthlyRentDueNowAmount);

			String InsuranceNowAmount = payment.get_InsuranceDueNowAmt();
			logger.log(LogStatus.PASS, "Insurance  Due Now amount in payment page is------>:" + InsuranceNowAmount);

			String PromotionDueNowAmount = payment.get_PromotionDueNowAmt();
			logger.log(LogStatus.PASS, "Promotion  Due Now amount in payment page is------>:" + PromotionDueNowAmount);

			String AdministrativeDueNowAmount = "0";

			try {
				AdministrativeDueNowAmount = payment.get_AdministrativeDueNowAmt();
				logger.log(LogStatus.PASS,
						"Administrative  Due Now amount in payment page is------>:" + AdministrativeDueNowAmount);
			} catch (Exception e) {
				Reporter.log("Administrative fee is not valid for this transaction");
			}

			String PayThroughDueNowAmount = "0";

			try {
				PayThroughDueNowAmount = payment.get_PaythroughDueNowAmt();
				logger.log(LogStatus.PASS,
						"PayThrough  Due Now amount in payment page is------>:" + PayThroughDueNowAmount);
			} catch (Exception e) {
				Reporter.log("Administrative fee is not valid for this transaction");
			}

			String MerchandiseAmount = payment.get_MerchandiseAmt();
			logger.log(LogStatus.PASS, "MerchandiseAmount  amount in payment page is------>:" + MerchandiseAmount);

			String TotalRemaingBalanceAmt = payment.get_TotalRemaingBalanceAmt();
			logger.log(LogStatus.PASS,
					"Total Remaing Balance Amount  in payment page is------>:" + TotalRemaingBalanceAmt);

			double MonthlyRentamt = payment.getDoubleAmount(MonthlyRentDueNowAmount);
			double Promotionamt = payment.getDoubleAmount(PromotionDueNowAmount);
			double Insuranceamt = payment.getDoubleAmount(InsuranceNowAmount);
			double Administrativeamt = payment.getDoubleAmount(AdministrativeDueNowAmount);
			double Apythroughamt = payment.getDoubleAmount(PayThroughDueNowAmount);
			double Merchandiseamt = payment.getDoubleAmount(MerchandiseAmount);

			double TotalAmount = MonthlyRentamt + Promotionamt + Insuranceamt + Administrativeamt + Apythroughamt
					+ Merchandiseamt;
			double TotalRemUiVal = payment.getDoubleAmount(TotalRemaingBalanceAmt);

			Double db1 = new Double(TotalAmount);
			Double db2 = new Double(TotalRemUiVal);
			if (db1.intValue() == db2.intValue()) {

				String sc = Generic_Class.takeScreenShotPath();
				String im = logger.addScreenCapture(sc);
				logger.log(LogStatus.PASS, "Sumantion of all due amount and Total remaing amount are equal");
				logger.log(LogStatus.INFO, "Sumantion of all due amount and Total remaing amount are equal", im);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String scp = Generic_Class.takeScreenShotPath();
				String ima = logger.addScreenCapture(scp);
				logger.log(LogStatus.FAIL, "Sumantion of all due is amount and Total remaing amount are not equal");
				logger.log(LogStatus.INFO, "Sumantion of all due is amount and Total remaing amount are not equal",
						ima);
			}

			Thread.sleep(3000);
			payment.selectPaymentMethod("Cash", driver);
			logger.log(LogStatus.PASS, "selecting cash option from the dropdown");

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			payment.enter_CashAmount(TotalRemaingBalanceAmt);
			logger.log(LogStatus.PASS, "entered total balance amount to pay");
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			payment.clickApply_btn();
			logger.log(LogStatus.PASS, "clicked on the apply button");
			Thread.sleep(7000);

			payment.clickSubmit_btn();
			logger.log(LogStatus.PASS, "clicked on the submit button in payment page");
			Thread.sleep(3000);

			TransactionCompletePopup popup = new TransactionCompletePopup(driver);

			String path = Generic_Class.takeScreenShotPath();
			String ima = logger.addScreenCapture(path);
			logger.log(LogStatus.PASS, "TransactionCompletePopup  dispalyed sucessfully");
			logger.log(LogStatus.INFO, "TransactionCompletePopup dispalyed sucessfully", ima);

			popup.enterEmpNum(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "entered the employee id");
			Thread.sleep(3000);
			popup.clickOk_btn();
			logger.log(LogStatus.PASS, "clicked on the ok button");
			Thread.sleep(3000);

			String gateCodeAfterTransfer = driver.findElement(By.xpath("//span[@class='gate-code__code-text']"))
					.getText().trim();
			logger.log(LogStatus.INFO, "Gate code for the transaction is:  " + gateCodeAfterTransfer);

			if (gateCodeAfterTransfer.equals(gateCodeAfterTransfer)) {
				resultFlag = "pass";
				String pa = Generic_Class.takeScreenShotPath();
				String img1 = logger.addScreenCapture(pa);
				logger.log(LogStatus.PASS, "Gate Codes are equal Before & After Transfer of Spaces");
				logger.log(LogStatus.INFO, "Gate Codes are equal Before & After Transfer of Spaces", img1);
			} else {
				resultFlag = "fail";
				String scp = Generic_Class.takeScreenShotPath();
				String imag = logger.addScreenCapture(scp);
				logger.log(LogStatus.FAIL, "Gate Codes are not equal Before & After Transfer of Spaces");
				logger.log(LogStatus.INFO, "Gate Codes are not equal Before & After Transfer of Spaces", imag);
			}

			try {
				popup.clickOk_btn();
			} catch (Exception e) {
				Reporter.log("Handled Popup!!!", true);
			}

			try {
				popup.clickOk_btn();
			} catch (Exception e) {
				Reporter.log("Handled Popup!!!", true);
			}

			Thread.sleep(8000);
			PM_Homepage pmhomepage = new PM_Homepage(driver);
			if (pmhomepage.isexistingCustomerModelDisplayed()) {
				String pa = Generic_Class.takeScreenShotPath();
				String img1 = logger.addScreenCapture(pa);
				logger.log(LogStatus.PASS, "PM DashBoard dispalyed sucessfully");
				logger.log(LogStatus.INFO, "PM DashBoard dispalyed sucessfully", img1);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String scp = Generic_Class.takeScreenShotPath();
				String imag = logger.addScreenCapture(scp);
				logger.log(LogStatus.FAIL, "PM DashBoard dispalyed  is not displayed");
				logger.log(LogStatus.INFO, "PM DashBoard dispalyed  is not displayed", imag);
			}

			Thread.sleep(2000);
			pmhomepage.clk_AdvSearchLnk();
			logger.log(LogStatus.PASS, "clicked on the submit button");

			Thread.sleep(6000);

			advSearch.enterAccNum(accountNumber);
			logger.log(LogStatus.PASS, "entered account number");

			Thread.sleep(5000);

			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.PASS, "clicked on the search button in advance search page");

			Thread.sleep(8000);
			if (cust.isCustdbTitleDisplayed()) {

				String sap = Generic_Class.takeScreenShotPath();
				String mag = logger.addScreenCapture(sap);
				logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "customer Dashboard is displayed successfully", mag);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scan = Generic_Class.takeScreenShotPath();
				String mg = logger.addScreenCapture(scan);
				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "customer Dashboard is not displayed ", mg);
			}

			Thread.sleep(1000);
			String totalDueNowAmount = cust.getTotalDue();
			logger.log(LogStatus.PASS, "Total due now amount  in customer dashbaord is---> :" + totalDueNowAmount);

			Thread.sleep(1000);

			// Verify Total Due Now is available

			if (cust.getTotalDueNowTxt().contains("Total Due Now")) {
				Thread.sleep(2000);
				String scp = Generic_Class.takeScreenShotPath();

				String img = logger.addScreenCapture(scp);
				logger.log(LogStatus.PASS, "Total Due Now is displayed successfully" + cust.getCustDashboardTitle());
				logger.log(LogStatus.PASS, "Total Due Now is displayed successfully", img);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scp = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(scp);
				logger.log(LogStatus.FAIL, "Total Due Now is not displayed successfully");
				logger.log(LogStatus.FAIL, "Total Due Now is not displayed successfully", im);
			}

			// Verify Next Due is available

			if (cust.getNextPaymentDueTxt().contains("Next Payment Due")) {
				Thread.sleep(2000);
				String scp = Generic_Class.takeScreenShotPath();

				String img = logger.addScreenCapture(scp);
				logger.log(LogStatus.PASS, "Next Payment Due is displayed successfully" + cust.getCustDashboardTitle());
				logger.log(LogStatus.PASS, "Next Payment Due is displayed successfully", img);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String sc = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(sc);
				logger.log(LogStatus.FAIL, "Next Payment Due is not displayed successfully");
				logger.log(LogStatus.FAIL, "Next Payment Due is not displayed successfully", im);
			}

			// Verify Make Payment is available
			if (cust.getMakePaymentTxt().contains("Make Payment")) {
				Thread.sleep(4000);
				String sc = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(sc);
				logger.log(LogStatus.PASS, "Make Payment is displayed successfully" + cust.getCustDashboardTitle());
				logger.log(LogStatus.PASS, "Make Payment is displayed successfully", im);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String sc = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(sc);
				logger.log(LogStatus.FAIL, "Make Payment is not displayed successfully");
				logger.log(LogStatus.FAIL, "Make Payment is not displayed successfully", im);
			}

			// Verify Manage AutoPay is available

			if (cust.getManageAutoPayTxt().contains("Manage AutoPay")) {
				Thread.sleep(2000);
				String st = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(st);
				logger.log(LogStatus.PASS, "Manage AutoPay is displayed successfully" + cust.getCustDashboardTitle());
				logger.log(LogStatus.PASS, "Manage AutoPay is displayed successfully", im);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String st = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(st);
				logger.log(LogStatus.FAIL, "Manage AutoPay is not displayed successfully");
				logger.log(LogStatus.FAIL, "Manage AutoPay is not displayed successfully", im);
			}

			// Verify Create Note is available

			if (cust.getCreateNoteTxt().contains("Create Note")) {
				Thread.sleep(2000);
				String sc = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(sc);
				logger.log(LogStatus.PASS, "Create Note is displayed successfully" + cust.getCustDashboardTitle());
				logger.log(LogStatus.PASS, "Create Note is displayed successfully", im);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String sc = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(sc);
				logger.log(LogStatus.FAIL, "Create Note is not displayed successfully");
				logger.log(LogStatus.FAIL, "Create Note is not displayed successfully", im);
			}

			// Verify Important Information is available

			if (cust.getImportantInformationTxt().contains("Important Information")) {
				Thread.sleep(2000);
				String sch = Generic_Class.takeScreenShotPath();

				String ie = logger.addScreenCapture(sch);
				logger.log(LogStatus.PASS,
						"Important Information is displayed successfully" + cust.getCustDashboardTitle());
				logger.log(LogStatus.PASS, "Important Information is displayed successfully", ie);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String sch = Generic_Class.takeScreenShotPath();

				String imge = logger.addScreenCapture(sch);
				logger.log(LogStatus.FAIL, "Important Information is not displayed successfully");
				logger.log(LogStatus.FAIL, "Important Information is not displayed successfully", imge);
			}

			// Verify Customer Info Tab is available
			if (cust.getCustomerInfoTabTxt().contains("Customer Info")) {
				Thread.sleep(2000);
				String sch = Generic_Class.takeScreenShotPath();

				String ie = logger.addScreenCapture(sch);
				logger.log(LogStatus.PASS, "Customer Info tab displayed successfully" + cust.getCustomerInfoTabTxt());
				logger.log(LogStatus.PASS, "Customer Info tab displayed successfully", ie);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String scr = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(scr);
				logger.log(LogStatus.FAIL, "Customer Info tab not  displayed successfully");
				logger.log(LogStatus.FAIL, "Customer Info tab not displayed successfully", im);
			}

			// Verify Space Details tab is available
			if (cust.getSpaceDetailsTabTxt().contains("Space Details")) {
				Thread.sleep(2000);
				String scp = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(scp);
				logger.log(LogStatus.PASS, " Space Details tab displayed successfully" + cust.getSpaceDetailsTabTxt());
				logger.log(LogStatus.PASS, " Space Details tab displayed successfully", im);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String sr = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(sr);
				logger.log(LogStatus.FAIL, "Space Details tab not  displayed successfully");
				logger.log(LogStatus.FAIL, "Space Details  tab not displayed successfully", im);
			}

			// Verify Account Activities tab is available
			if (cust.getAccountActivitiesTabTxt().contains("Account Activities")) {
				Thread.sleep(2000);
				String scp = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(scp);
				logger.log(LogStatus.PASS,
						" Account Activitiestab displayed successfully" + cust.getAccountActivitiesTabTxt());
				logger.log(LogStatus.PASS, " Account Activities tab displayed successfully", im);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String sc = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(sc);
				logger.log(LogStatus.FAIL, "Account Activities tab not  displayed successfully");
				logger.log(LogStatus.FAIL, "Account Activities tab not displayed successfully", im);
			}

			// Verify Documents tab is available

			if (cust.getDocumentsTabTxt().contains("Documents")) {
				Thread.sleep(2000);
				String sc = Generic_Class.takeScreenShotPath();

				String im = logger.addScreenCapture(sc);
				logger.log(LogStatus.PASS, "  Documents tab displayed successfully" + cust.getDocumentsTabTxt());
				logger.log(LogStatus.PASS, " Documents tab displayed successfully", im);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String sc = Generic_Class.takeScreenShotPath();
				String im = logger.addScreenCapture(sc);
				logger.log(LogStatus.FAIL, "Documents tab not  displayed successfully");
				logger.log(LogStatus.FAIL, "Documents tab not displayed successfully", im);
			}

			Thread.sleep(2000);
			cust.clk_ViewDetailsLink();
			logger.log(LogStatus.PASS, "click on the view details link in customer dashboard sucessfully");

			Thread.sleep(2000);

			Cust_PaymentDueDetailsPage duepage = new Cust_PaymentDueDetailsPage(driver);
			Thread.sleep(3000);

			if (duepage.isPymtDueDetailsTitleDisplayed()) {
				Thread.sleep(2000);
				String sc = Generic_Class.takeScreenShotPath();
				String im = logger.addScreenCapture(sc);
				logger.log(LogStatus.PASS, "Payment Due Details page is displayed successfully");
				logger.log(LogStatus.PASS, "Payment Due Details page is displayed successfully", im);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String sc = Generic_Class.takeScreenShotPath();
				String im = logger.addScreenCapture(sc);
				logger.log(LogStatus.FAIL, "Payment Due Details page is not displayed");
				logger.log(LogStatus.FAIL, "Payment Due Details page is not displayed", im);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
			Reporter.log("Exception ex: " + ex, true);
			logger.log(LogStatus.FAIL, "Test Script fail due to exception");
		}
	}

	@AfterMethod
	public void afterMethod() {
		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "Transfer", "Individual_Anniversary_Transfer_Customer_With_Balance_Due",
					"Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "Transfer", "Individual_Anniversary_Transfer_Customer_With_Balance_Due",
					"Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "Transfer", "Individual_Anniversary_Transfer_Customer_With_Balance_Due",
					"Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();
	}

}
