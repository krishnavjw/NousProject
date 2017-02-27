package Scenarios.TransferSpace;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import Pages.HomePages.Dashboard_BifrostHostPopUp;
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
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.VehicleStoragePage;
import Scenarios.Browser_Factory;

public class Individual_AP_keepsource_channel_pricing_checked_Prepaid_customer extends Browser_Factory {

	public ExtentTest logger;

	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";
	WebDriverWait wait;
	String firstName;
	String accountNumber;
	String siteid_tobeset;
	String rentalUnitNumber;

	@DataProvider
	public Object[][] getData() {
		return Excel.getCellValue_inlist(path, "TransferSpace", "TransferSpace","Individual_AP_keepsource_channel_pricing_checked_Prepaid_customer");
	}

	@Test(dataProvider = "getData")
	public void Individual_AP_keepsource_channel_pricing_checked_Prepaid_customer(Hashtable<String, String> tabledata) throws InterruptedException {
		try {

			logger = extent.startTest("Individual_AP_keepsource_channel_pricing_checked_Prepaid_customer",
					"Individual_AP_keepsource_channel_pricing_checked_Prepaid_customer ");

			wait = new WebDriverWait(driver, 60);
			

			if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("TransferSpace").equals("Y"))) {
				resultFlag = "skip";
				throw new SkipException("Skipping the test");
			}

		
			// ======== Handling customer facing device end =============
			
			String sqlQuery = "select top 10 u.AccountNumber,u.SiteID "
					+ "from vw_unitdetails u join accountorderitempromotion aoip "
					+ "on u.accountorderitemid=aoip.accountorderitemid and aoip.expirationdate > getdate() "
					+ "left join accountorderitemproductoption aoipo "
					+ "on u.accountorderitemid=aoipo.accountorderitemid and aoipo.productoptionid=2 "
					+ "join promotion p on aoip.promotionid = p.promotionid "
					+ "join storageorderitem soi on u.storageorderitemid=soi.storageorderitemid  and soi.paymentmethodtypeid = 101 "
					+ "where  u.moveindate > getdate()-29 and u.vacatedate is null "
					+ "and u.paidthrudate >getdate() "
					+ "and u.customertypeid = 90 "
					+ "and u.siteid  in (Select ps.SiteID "
					+ "from Productsite PS Join RentalUnit RU on RU.ProductSiteID = PS. ProductSiteID "
					+ "join StorageProductFeature spf ON ps.StorageProductFeatureID = spf.StorageProductFeatureID "
					+ "Where  RentalStatusTypeID = 59  and spf.Parking = 1) "
					+ "order by u.siteid,u.moveindate";

			try {
				List<String> results = DataBase_JDBC.executeSQLQuery_List(sqlQuery);

				accountNumber = results.get(4);
				logger.log(LogStatus.INFO, "Account Number fetched from Query is:  " + accountNumber);

				siteid_tobeset = results.get(5);
				logger.log(LogStatus.INFO, "Site id related to account & to be set is:  " + siteid_tobeset);
				
				

			} catch (Exception e) {
				logger.log(LogStatus.INFO, "No Details are fetched from database query");
			}

			// Fetching IP Address & changing it accordingly

			String ipadd = Generic_Class.getIPAddress();
			Thread.sleep(1000);

			String current_siteid_query = "select SiteID from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"
					+ ipadd + "'";
			String current_siteid = DataBase_JDBC.executeSQLQuery(current_siteid_query);
			System.out.println(" th siteid_ipsdd_data is-----" + current_siteid);

		

				// Make the currently assigned site id to null
				String allocate_currentsiteidtonull_query = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"
						+ current_siteid + "'";
				DataBase_JDBC.executeSQLQuery(allocate_currentsiteidtonull_query);

				// Allocate new site id to current ip address
				String allocateip_to_newid = "Update siteparameter set paramvalue='" + ipadd
						+ "' where paramcode='IP_COMPUTER_FIRST' and siteid='" + siteid_tobeset + "'";
				DataBase_JDBC.executeSQLQuery(allocateip_to_newid);

			

				logger=extent.startTest("Individual_AP_keepsource_channel_pricing_checked_Prepaid_customer", "Individual_AP_keepsource_channel_pricing_checked_Prepaid_customer ");
				testcaseName = tabledata.get("TestCases");
				Reporter.log("Test case started: " + testcaseName, true);
				LoginPage login= new LoginPage(driver);
				login.login(tabledata.get("UserName"),tabledata.get("Password"));
				
				logger.log(LogStatus.INFO, "DM Logged in successfully");


				Generic_Class generics = new Generic_Class();
				driver.manage().timeouts().implicitlyWait(6000, TimeUnit.SECONDS);
				JavascriptExecutor jse = (JavascriptExecutor)driver;

				//connecting to customer device
				Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
				Reporter.log("object created successfully",true);
				Thread.sleep(5000);
				String biforstNum = driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//h2/b")).getText();
				Reporter.log(biforstNum + "", true);

				//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, "t");
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_T);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_T); 
				Thread.sleep(2000);
				ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
				Reporter.log(tabs.size() + "", true);
				driver.switchTo().window(tabs.get(1));
				//driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));
				driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_QA")); 
				List<WebElement> biforstSystem = driver.findElements(
						By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
				for (WebElement ele : biforstSystem) {
					if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
						Reporter.log(ele.getText() + "", true);
						ele.click();
						break;
					}
				}

				//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

				Thread.sleep(5000);
				driver.switchTo().window(tabs.get(0));
				Thread.sleep(5000);
				driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
				Thread.sleep(5000);

				//=====================================================================


			PM_Homepage PM_HomePage = new PM_Homepage(driver);
			logger.log(LogStatus.PASS, "User sucessfully logged into application");

			jse.executeScript("window.scrollBy(1000,0)", "");
			
			
			
			driver.findElement(By.xpath("//a[@class='advancedsearch uppercase']")).click();
			
			Thread.sleep(5000);
			
			logger.log(LogStatus.PASS, "clicked on advanced search link in the DM Dashboard sucessfully");

			Advance_Search advSearch = new Advance_Search(driver);
			logger.log(LogStatus.PASS, "creating object for advance search page sucessfully");

			Thread.sleep(4000);
			

			advSearch.enterAccNum(accountNumber);
			logger.log(LogStatus.INFO, "Enter existing customer Account Number in Account field successfully");

			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(1000);

			advSearch.clk_SearchAccount();
			logger.log(LogStatus.INFO, "Entered into advance search page");
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Clicked Search button successfully");
			Thread.sleep(5000);

			Cust_AccDetailsPage cust = new Cust_AccDetailsPage(driver);

			cust.clickSpaceDetails_tab();
			logger.log(LogStatus.INFO, "Clicked on Space Details tab successfully");
			Thread.sleep(4000);

			String gateCodeBeforeTransfer = driver
					.findElement(By.xpath("//div[text()='Gate Code:']/following-sibling::div")).getText().trim();
			logger.log(LogStatus.INFO, "Gate Code Before Transfer is:  " + gateCodeBeforeTransfer);

			Acc_SpaceDetailsPage accSpaceDetails = new Acc_SpaceDetailsPage(driver);
			accSpaceDetails.clickTransfer_Btn(driver);
			logger.log(LogStatus.INFO, "Clicked on Transfer button successfully");
			Thread.sleep(5000);

			TransferSpaceDetails transferspace = new TransferSpaceDetails(driver);

			

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
			Thread.sleep(5000);

			TransferSelectSpacePage transferSelectSpacePage = new TransferSelectSpacePage(driver);

			StandardStoragePage stdStorage = new StandardStoragePage(driver);
			stdStorage.click_VehicleStorage();
			logger.log(LogStatus.INFO, "clicked on the vehicle straoge tab");

			Thread.sleep(3000);

			VehicleStoragePage vehicle =new VehicleStoragePage(driver);
			Thread.sleep(3000);

			logger.log(LogStatus.PASS, "selecting all the check box in the vehicle straoge page");
			vehicle.click_Car();
			Thread.sleep(1000);
			vehicle.click_Boat();
			Thread.sleep(1000);
			vehicle.click_Oversized();
			Thread.sleep(1000);

			vehicle.click_Covered();
			Thread.sleep(1000);
			vehicle.click_Uncovered();
			Thread.sleep(1000);
			vehicle.click_Enclosed();
			Thread.sleep(2000);

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
			 * 
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

			//contactinfo.clk_ActiveDutyMilitaryYesRadioBtn(driver);
			contactinfo.clk_ActiveDutyMilitaryNoRadioBtn(driver);

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
			Thread.sleep(3000);

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
			Thread.sleep(3000);

			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully>>>>>>>>>>>>>>>>>>");
			Reporter.log("Switch to Main page successfully>>>>>>>>>>>>>>>>>", true);
			Thread.sleep(10000);

			// ====Authorized Access Contacts======

			
			
			
			
			Leasing_AuthorizedAccessContactsPage autContact = new Leasing_AuthorizedAccessContactsPage(driver);
			jse.executeScript("window.scrollBy(0,-300)", "");
			Thread.sleep(10000);

			
			autContact.enterFirstName(tabledata.get("authFirstname"));
			Thread.sleep(1000);

			autContact.enterLastName(tabledata.get("authLastName"));
			Thread.sleep(1000);

			autContact.clickPhonelist();
			Thread.sleep(2000);

			List<WebElement> phone = driver.findElements(By.xpath(
					"//ul[@id='AuthorizedUsersForm_AuthorizedUsers[_-index-__0]_Phone_PhoneTypeID_listbox']/li"));
			for (WebElement ele : phone) {
				if (ele.getText().equalsIgnoreCase(tabledata.get("PhoneType"))) {
					ele.click();
					break;
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

			
				

			// ======Lease Questionaire=====

			Leasing_LeaseQuestionairePage leaseQues = new Leasing_LeaseQuestionairePage(driver);

			
			try{
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
				}catch(Exception e) {
					Reporter.log("Standard storage  is unavailable, so continuing!!");
				}
			
			leaseQues.clickvechicalstorageContent();
			Thread.sleep(3000);
			
			List<WebElement> allstoragevehicle = driver.findElements(By.xpath(
					"//ul[@id='LeaseQuestionnaireUnits_0__VehicleTypeID_listbox']//li[@class='k-item']"));

			for (WebElement storage : allstoragevehicle) {
				if (tabledata.get("vehicle_storagecontent").equalsIgnoreCase(storage.getText())) {
					storage.click();
					break;
				}
			}
			
			
			Thread.sleep(5000);

			// Selecting Insurance as "Yes"

			/*leaseQues.clickAddInsuranceYes();
			Thread.sleep(1000);

			leaseQues.clickCoverageList();
			Thread.sleep(2000);

			driver.findElement(By.xpath("//ul[@id='LeaseQuestionnaireUnits_0__InsuranceSelection_listbox']/li[2]"))
					.click();
			Thread.sleep(2000);*/

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

			
		
			
		driver.findElement(By.xpath("//input[@id='UnitForms_0__Vehicles__-index-__0__Make']")).sendKeys("Honda");
		
		driver.findElement(By.xpath("//input[@id='UnitForms_0__Vehicles__-index-__0__Model']")).sendKeys("S2");
		
		driver.findElement(By.xpath("//input[@id='UnitForms_0__Vehicles__-index-__0__Year']")).sendKeys("2005");
		
		
		driver.findElement(By.xpath("//input[@id='UnitForms_0__Vehicles__-index-__0__Color']")).sendKeys("Red");
		
		
		driver.findElement(By.xpath("(//label[@class='webchamp-radio-button owner-same-renter-button']/span[@class='button'])[1]")).click();
		
	
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[contains(text(),'Upload')]")).click();
		Thread.sleep(3000);

		String pathfile = System.getProperty("user.dir");
		System.out.println("Path is :-- " + pathfile);
		uploadFile(pathfile + File.separator + "per1.png");

		Thread.sleep(3000);
		
		
		driver.findElement(By.xpath("(//label[@class='webchamp-radio-button lienholder-button']/span[@class='button'])[2]")).click();
		Thread.sleep(3000);
		jse.executeScript("window.scrollBy(0,800)", "");
		Thread.sleep(5000);
		driver.findElement(By.xpath("UnitForms_0__Vehicles__-index-__0__LegalOwner']")).sendKeys("Basu");
		
		
		//driver.findElement(By.xpath("//a[contains(text(),'Save and Proceed')]")).click();
		driver.findElement(By.id("confirmButton")).click();
		
		
		 //===========================clicking multiple check boxes for secand====================================
		
        robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

		Thread.sleep(8000);
		driver.switchTo().window(tabs.get(1));
		logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

		List<WebElement> allCheckbox=driver.findElements(By.xpath("//section[@class='term-group term-group--active']//input[@type='checkbox']"));

        for(WebElement checkbox:allCheckbox)
        {
              checkbox.click();
        }
        Thread.sleep(5000);
        driver.findElement(By.id("confirmButton")).click();

        Thread.sleep(8000);
        
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_PAGE_DOWN);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
        Thread.sleep(6000);
        driver.switchTo().window(tabs.get(0));

        Leasing_ReviewNApprovePage review= new Leasing_ReviewNApprovePage(driver);

        Thread.sleep(8000);
    


        Thread.sleep(6000);
        
		
     
      //===========================clicking multiple check boxes for Third====================================
		
        robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

		Thread.sleep(8000);
		driver.switchTo().window(tabs.get(1));
		logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

		List<WebElement> allCheckbox2=driver.findElements(By.xpath("//section[@class='term-group term-group--active']//input[@type='checkbox']"));

        for(WebElement checkbox:allCheckbox2)
        {
              checkbox.click();
        }
        Thread.sleep(5000);
        driver.findElement(By.id("confirmButton")).click();

        Thread.sleep(8000);
        
     

        WebElement signature2 = driver.findElement(By.xpath("//div[@class='sig sigWrapper']/canvas[@class='pad js-signature-canvas']"));
        Actions actionBuilder2 = new Actions(driver);          
        Action drawAction2 = actionBuilder2.moveToElement(signature2,660,96).click().clickAndHold(signature2)
                     .moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature2).build();
        drawAction2.perform();
    
        Thread.sleep(5000);
        driver.findElement(By.id("confirmButton")).click();
        Thread.sleep(5000);
        //driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_PAGE_DOWN);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
        Thread.sleep(6000);
        driver.switchTo().window(tabs.get(0));

      //===================Leasing Review and Approve Page============================
		Leasing_ReviewNApprovePage review2 = new Leasing_ReviewNApprovePage(driver);
		Thread.sleep(8000);
		review2.clickApprove_btn();
		Thread.sleep(6000);	
		String scph;
		String ige;
		 scph = Generic_Class.takeScreenShotPath();
		ige = logger.addScreenCapture(scph);
		logger.log(LogStatus.INFO, "img ", ige);			
		driver.findElement(By.id("PaymentButton")).click();
		Thread.sleep(6000);


		PaymentsPage pyment = new PaymentsPage(driver);
		Thread.sleep(10000);
		pyment.clickOnConfirmWithCustomer_Btn();
		Thread.sleep(5000);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		Thread.sleep(3000);

		driver.switchTo().window(tabs.get(1));
		Thread.sleep(6000);
		scph = Generic_Class.takeScreenShotPath();
		ige = logger.addScreenCapture(scph);
		logger.log(LogStatus.INFO, "img ", ige);	
		driver.findElement(By.id("confirmButton")).click();


		Thread.sleep(5000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		Thread.sleep(3000);

		driver.switchTo().window(tabs.get(0));
		Thread.sleep(8000);

		Leasing_PaymentMethodsPage payment = new Leasing_PaymentMethodsPage(driver);
		Thread.sleep(2000);

		String scpath = Generic_Class.takeScreenShotPath();
		String image = logger.addScreenCapture(scpath);
		logger.log(LogStatus.PASS, "Payment page is dispalyed sucessfully ");
		logger.log(LogStatus.INFO, "Payment page is dispalyed sucessfully", image);

		String MonthlyRentDueNowAmount = payment.get_MonthlyRentDueNowAmt();
		logger.log(LogStatus.PASS,
				"Monthly Rent Due Now amount in payment page is------>:" + MonthlyRentDueNowAmount);

	

		String AdministrativeDueNowAmount = "0";

		try{
			if(payment.isAdministrativeDueNowAmtDisplayed())
				logger.log(LogStatus.FAIL, "Admin fees  charged for transfer ");
		}catch(Exception e){
			logger.log(LogStatus.PASS, "Admin fee not charged for transfer space ");
		}


		String TotalRemaingBalanceAmt = payment.get_TotalRemaingBalanceAmt();
		logger.log(LogStatus.PASS,
				"Total Remaing Balance Amount  in payment page is------>:" + TotalRemaingBalanceAmt);


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
		scph = Generic_Class.takeScreenShotPath();
		ige = logger.addScreenCapture(scph);
		logger.log(LogStatus.INFO, "img ", ige);	

		payment.clickSubmit_btn();
		Thread.sleep(3000);

		TransactionCompletePopup popup = new TransactionCompletePopup(driver);
		String path = Generic_Class.takeScreenShotPath();
		String ima = logger.addScreenCapture(path);
		logger.log(LogStatus.PASS, "TransactionCompletePopup  dispalyed sucessfully");
		logger.log(LogStatus.INFO, "TransactionCompletePopup dispalyed sucessfully", ima);
		
		Thread.sleep(5000);
		String newSpace=driver.findElement(By.xpath("//div[contains(text(),'Spaces:')]/following-sibling::div")).getText().trim();  
		String str=newSpace;
		String[] splited = str.split("\\s+");
		String s1=splited[0];
		Reporter.log("**********"+s1,true);
			
		logger.log(LogStatus.INFO, "New space number is:------------>"+s1);
		popup.enterEmpNum(tabledata.get("UserName"));
		logger.log(LogStatus.PASS, "entered the employee id");
		Thread.sleep(3000);
		popup.clickOk_btn();
		logger.log(LogStatus.PASS, "clicked on the ok button");
		Thread.sleep(10000);

		String gateCodeAfterTransfer = driver.findElement(By.xpath("//span[@class='gate-code__code-text']"))
				.getText().trim();
		logger.log(LogStatus.INFO, "Gate code for the transaction is:  " + gateCodeAfterTransfer);

		if (gateCodeBeforeTransfer.equals(gateCodeAfterTransfer)) {
			
			String pa = Generic_Class.takeScreenShotPath();
			String img1 = logger.addScreenCapture(pa);
			logger.log(LogStatus.FAIL, "Gate Codes are equal Before & After Transfer of Spaces");
			logger.log(LogStatus.INFO, "Gate Codes are equal Before & After Transfer of Spaces", img1);
		} else {
			String scp = Generic_Class.takeScreenShotPath();
			String imag = logger.addScreenCapture(scp);
			logger.log(LogStatus.PASS, "Gate Codes are not equal Before & After Transfer of Spaces");
			logger.log(LogStatus.INFO, "Gate Codes are not equal Before & After Transfer of Spaces", imag);
		}

		Thread.sleep(10000);

		try{
			popup.clickOk_btn();
			Thread.sleep(20000);
		}catch(Exception ex){

		}

		try{

			if(driver.findElement(By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'No')]")).isDisplayed()){
				driver.findElement(By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'No')]")).click();
				Thread.sleep(20000);
			}else{

			}
		}catch(Exception e){
			e.getMessage();
		}

		try{

			driver.findElement(By.xpath("//div/a[contains(text(),'OK')]")).click();
			Thread.sleep(20000);
		}catch(Exception e){
			e.getMessage();      
		}



		Thread.sleep(10000);
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
		
		
		
		Thread.sleep(3000);
		String quer="select top 1 RentalStatusTypeID from rentalunit  where RentalUnitNumber ='"+transferFromSpaceNumber+"' order by LastUpdate desc";
        String input=DataBase_JDBC.executeSQLQuery(quer);
        Thread.sleep(3000);
        String quer1="select name  from type where typeid='"+input+"'";
        String staus=DataBase_JDBC.executeSQLQuery(quer1);
        Thread.sleep(3000);
        if(staus.contains("Transfer")){
        	
        	logger.log(LogStatus.PASS, "Old space  status from databse is--------->"+staus);
        }else{
        	logger.log(LogStatus.FAIL, "Old space  status from databse is--------->"+staus);
        	
        }
        
        
        
        Thread.sleep(3000);
		String quer2="select top 1 RentalStatusTypeID from rentalunit  where RentalUnitNumber ='"+s1+"' order by LastUpdate desc";
        String input2=DataBase_JDBC.executeSQLQuery(quer2);
        Thread.sleep(3000);
        String quer3="select name  from type where typeid='"+input2+"'";
        String newpsace=DataBase_JDBC.executeSQLQuery(quer3);
        Thread.sleep(3000);
        if(newpsace.contains("Occupied")){
        	
        	logger.log(LogStatus.PASS, "Old space having status from databse is--------->"+newpsace);
        }else{
        	logger.log(LogStatus.FAIL, "Old space having status from databse is--------->"+newpsace);
        	
        }

			
			
		
		} catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
			Reporter.log("Exception ex: " + ex, true);
			logger.log(LogStatus.FAIL, "Test Script fail due to exception");
		}
	}

	public void uploadFile(String fileLocation) {
		try {
			/*
			 * WebElement element = driver .findElement((By.xpath(
			 * "//*[@id='overlayContainer']/div/div[2]/div[1]/div/div/span")));
			 * element.click();
			 */
			Thread.sleep(3000);
			// Setting clipboard with file location
			setClipboardData(fileLocation);
			// native key strokes for CTRL, V and ENTER keys
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public void setClipboardData(String string) {
		// StringSelection is a class that can be used for copy and paste
		// operations.
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	@AfterMethod
	public void afterMethod() {
		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "Transfer", "Individual_AP_Transfer_On_MoveIn_Day", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "Transfer", "Individual_AP_Transfer_On_MoveIn_Day", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "Transfer", "Individual_AP_Transfer_On_MoveIn_Day", "Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();
	}
}
