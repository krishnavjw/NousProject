package Scenarios.Payments;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
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
import Pages.MakePaymentPages.TransactionCompletePopup;
import Pages.Walkin_Reservation_Lease.Leasing_AuthorizedAccessContactsPage;
import Pages.Walkin_Reservation_Lease.Leasing_ConfirmSpace;
import Pages.Walkin_Reservation_Lease.Leasing_ContactInfoPage;
import Pages.Walkin_Reservation_Lease.Leasing_EligiblePromotionsPage;
import Pages.Walkin_Reservation_Lease.Leasing_EmergencyConatctsPage;
import Pages.Walkin_Reservation_Lease.Leasing_LeaseQuestionairePage;
import Pages.Walkin_Reservation_Lease.Leasing_PaymentMethodsPage;
import Pages.Walkin_Reservation_Lease.Leasing_RentalFeePage;
import Pages.Walkin_Reservation_Lease.Leasing_ReviewNApprovePage;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Scenarios.Browser_Factory;

public class MakePayment_WithoutInsurance extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag = "pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() {
		return Excel.getCellValue_inlist(path, "Leasing", "Leasing","Leasing_LeaseWithInsurance");
	}

	@Test(dataProvider = "getLoginData")
	public void Leasing_LeaseWithInsurance(Hashtable<String, String> tabledata) throws Exception {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("Leasing").equals("Y"))) {
			resultFlag = "skip";
			logger.log(LogStatus.SKIP, "Leasing_LeaseWithInsurance is Skipped");
			throw new SkipException("Skipping the test Leasing_LeaseWithInsurance");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);

		try {
			logger = extent.startTest("Leasing_LeaseWithInsurance","Lease with insurance");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " + testcaseName, true);
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			LoginPage loginPage = new LoginPage(driver);
			logger.log(LogStatus.PASS, "creating object for the Login Page sucessfully ");
			Thread.sleep(2000);
			loginPage.enterUserName(tabledata.get("UserName"));
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "entered username sucessfully");
			Thread.sleep(2000);
			loginPage.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.PASS, "entered password sucessfully");
			Thread.sleep(2000);
			loginPage.clickLogin();
			logger.log(LogStatus.PASS, "clicked on login in button sucessfully");
			logger.log(LogStatus.PASS, "Login to Application  successfully");

			// =================Handling customer facing device========================
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			String biforstNum = Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum + "", true);

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, "t");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));

			List<WebElement> biforstSystem = driver.findElements(
					By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for (WebElement ele : biforstSystem) {
				if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
					Reporter.log(ele.getText() + "", true);
					ele.click();
					break;
				}
			}

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			// =================================================================================
			Thread.sleep(3000);
			PM_Homepage pmhomepage = new PM_Homepage(driver);
			logger.log(LogStatus.PASS, "creating object for the PM home page sucessfully");
			if(pmhomepage.get_WlkInCustText().trim().contains("Walk-In Customer")){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Walk in customer window displayed sucessfully");
				logger.log(LogStatus.INFO, "Walk in customer window displayed sucessfully",image);

			}
			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Walk in customer window is not displayed");
				logger.log(LogStatus.INFO, "Walk in customer window is not displayed",image);

			}
			if(pmhomepage.isFindAndLeaseButtonDisplayed()){
				logger.log(LogStatus.PASS, "Find and Lease button displayed sucessfully on Walk in customer window");
			}else{

				logger.log(LogStatus.FAIL, "Find and Lease button is not displayed sucessfully on Walk in customer window");
			}

			if(pmhomepage.isFindAnExtReservationTextFieldDisplayed()){

				logger.log(LogStatus.PASS, "Or Find an Existing Reservation text field is dispalyed sucessfully on Walk in customer window");
			}else{

				logger.log(LogStatus.FAIL, "Or Find an Existing Reservation text field is not dispalyed sucessfully on Walk in customer window");
			}


			if(pmhomepage.isFindReservationDispalyed()){

				logger.log(LogStatus.PASS, "Find reservation button is dispalyed sucessfully on Walk in customer window");
			}else{

				logger.log(LogStatus.FAIL, "Find reservation button is not dispalyed sucessfully on Walk in customer window");
			}
			pmhomepage.clk_findAndLeaseSpace();
			logger.log(LogStatus.INFO, "Click on Find And Lease Space Link successfully");

			Thread.sleep(4000);
			StandardStoragePage stdStorage = new StandardStoragePage(driver);
			logger.log(LogStatus.PASS, "Creating object for the Standard stroage page sucessfully");
			Thread.sleep(1000);
			if (stdStorage.isSelected_yesradiobutton()) {

				logger.log(LogStatus.PASS,
						"New Customer Radio button selected by default when WALK-INS: FIND A SPACE page is launched sucessfully");

			} else {

				logger.log(LogStatus.FAIL,"New Customer Radio button is not selected by default when WALK-INS: FIND A SPACE page is launched sucessfully");
			}

			stdStorage.Clk_ChkBx_AvlSpace();
			logger.log(LogStatus.PASS, "Clicked on available bold space size  checkbox in StandardStorage sucessfully");

			stdStorage.click_Search();
			logger.log(LogStatus.INFO, "Click on search button successfully");

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			// =====================Fetching space number and based on that clicking the radio button========================
			Thread.sleep(10000);
			List<WebElement> norows = driver.findElements(By.xpath("//form[@id='frmReserveUnits']//div//table/tbody/tr"));
			String space = null;
			logger.log(LogStatus.INFO, "Total number of avaiable sizes count is------>:" + norows.size());
			if (norows.size() > 0) {
				Thread.sleep(5000);

				space = driver.findElement(By.xpath("//form[@id='frmReserveUnits']//div//table/tbody/tr[1]/td[4]")).getText();

				Reporter.log("space number is:" + space, true);
			} else {

				logger.log(LogStatus.FAIL,"Application is not populating any data/space details with selected size dimension");
				throw new Exception("Application is not populating any data/space details");

			}
			Thread.sleep(2000);
			WebElement RdBtn_Space = driver.findElement(By.xpath("//td[@class='grid-cell-space'][text()='" + space + "']/../td/input[@name='selectedIds']"));
			logger.log(LogStatus.PASS,"check the radio button based on the space and click on the  reservation button");
			Thread.sleep(5000);
			jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space);
			jse.executeScript("arguments[0].click();", RdBtn_Space);

			logger.log(LogStatus.PASS, "Clicked on check box of a space in this location:-------> " + space);
			Reporter.log("Clicked on check box of a space in this location: " + space, true);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			SpaceDashboard_ThisLoc thisloc = new SpaceDashboard_ThisLoc(driver);
			logger.log(LogStatus.PASS, "creating object for the This location page sucessfully");
			thisloc.click_Rent();
			logger.log(LogStatus.PASS, "clicked on the rent button sucessfully");
			Thread.sleep(5000);

			Leasing_ConfirmSpace confirmSpace = new Leasing_ConfirmSpace(driver);
			Thread.sleep(5000);
			confirmSpace.clk_ConfirmwtCust();

			logger.log(LogStatus.PASS, "Clicked on Confirm with Customer button");
			Reporter.log("Clicked on Confirm with Customer button", true);

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.PASS, "Switched to Customer Screen");
			Reporter.log("Switched to Customer Screen", true);
			Thread.sleep(9000);
			String monthlyRent=driver.findElement(By.xpath("//table[@class='spaces']/tbody//tr//td[@class='monthly-rent']")).getText();

			driver.findElement(By.xpath("//div[@class='footer-row clearfix-container']/button[@id='confirmButton']"))
			.click();
			logger.log(LogStatus.PASS, "Clicked on Confirm button in customer screen");
			Reporter.log("Clicked on Confirm button in customer screen", true);

			Thread.sleep(8000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.PASS, "Switching back to screen");
			Reporter.log("Switching back to screen", true);

			// Filling contact information

			Leasing_ContactInfoPage contactinfo = new Leasing_ContactInfoPage(driver);
			Thread.sleep(5000);
			String FN = "AUT" + Generic_Class.get_RandmString();
			contactinfo.txt_Fname(FN);
			contactinfo.txt_Lname(tabledata.get("LastName"));
			contactinfo.clickContact_State();
			List<WebElement> allstates = driver.findElements(By.xpath("//ul[@id='ContactForm_Identification_StateTypeID_listbox']/li[@class='k-item']"));

			// Identify the WebElement which will appear after scrolling down

			for (WebElement state : allstates) {
				Thread.sleep(2000);
				if (tabledata.get("StateCode").equalsIgnoreCase(state.getText())) {
					state.click();
					break;
				}
			}

			// contactinfo.select_State(tabledata.get("StateCode"));
			contactinfo.txt_Number(tabledata.get("DrivingLicenseNum"));
			contactinfo.txt_street1(tabledata.get("Street"));
			contactinfo.txt_city(tabledata.get("City"));

			contactinfo.select_State2address();
			List<WebElement> allstatesadd = driver.findElements(By.xpath("//ul[@id='lesseeinfo-address-statecode_listbox']/li[@class='k-item']"));

			for (WebElement state : allstatesadd) {
				Thread.sleep(2000);
				if (tabledata.get("StateCode").equalsIgnoreCase(state.getText())) {
					state.click();
					break;
				}
			}

			// contactinfo.select_State2(tabledata.get("State"));
			Thread.sleep(3000);
			contactinfo.txt_Zipcode(tabledata.get("Zipcode"));
			contactinfo.select_phoneType1();
			Thread.sleep(3000);

			driver.findElement(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li[contains(text(),'"+ tabledata.get("PhoneType") + " ')]")).click();
			Thread.sleep(2000);
			contactinfo.txt_AreaCode(tabledata.get("Areacode"));
			Thread.sleep(2000);
			contactinfo.txt_Exchg(tabledata.get("Exchange"));
			Thread.sleep(2000);
			contactinfo.txt_lineNumber(tabledata.get("LineNumber"));
			Thread.sleep(2000);
			contactinfo.txt_email(tabledata.get("Email"));
			Thread.sleep(2000);
			contactinfo.click_CustLookUp();

			Thread.sleep(15000);
			// Click on New Customer button on Choose an Account PopUp
			driver.findElement(By.linkText("Create New Customer")).click();

			Thread.sleep(8000);
			contactinfo.clk_ActiveDutyMilitaryNoRadioBtn(driver);

			// Click on Verify button
			Thread.sleep(5000);
			driver.findElement(By.partialLinkText("Verify")).click();
			logger.log(LogStatus.INFO, "cliked on the Verify button button ");

			Thread.sleep(9000);
			driver.findElement(By.id("confirmWithCustomerButton")).click();

			Thread.sleep(5000);
			// Click on Confirm with Cust button

			logger.log(LogStatus.INFO, "clicking on Confirm with cust button successfully");

			// Navigating to Customer screen
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");

			Thread.sleep(5000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");

			// Entering Emergency Contact details
			Thread.sleep(5000);
			Leasing_EmergencyConatctsPage emergCon = new Leasing_EmergencyConatctsPage(driver);
			Thread.sleep(2000);

			emergCon.clickAuthorizedfor_radio();
			Thread.sleep(2000);
			emergCon.click_custDeclines_chkBox();

			emergCon.clickconfirmWithCust();
			Thread.sleep(4000);

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			Thread.sleep(8000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(2000);
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");

			Leasing_AuthorizedAccessContactsPage autContact = new Leasing_AuthorizedAccessContactsPage(driver);

			Thread.sleep(8000);
			autContact.clickSavenProceed();

			Thread.sleep(8000);
			Leasing_EligiblePromotionsPage eligpromo = new Leasing_EligiblePromotionsPage(driver);

			logger.log(LogStatus.PASS, "Validating Monthly rent and Promotions in Eligible Promotion Page");

			eligpromo.clickSavenProceed();

			Thread.sleep(8000);

			Leasing_LeaseQuestionairePage leaseQues = new Leasing_LeaseQuestionairePage(driver);

			leaseQues.clickStorageContent();

			Thread.sleep(5000);

			List<WebElement> allstorage = driver.findElements(By.xpath("//ul[@id='LeaseQuestionnaireUnits_0__RentalUnitContentsTypeID_listbox']//li[@class='k-item']"));

			for (WebElement storage : allstorage) {
				if (tabledata.get("StorageContent").equalsIgnoreCase(storage.getText())) {
					storage.click();
					break;
				}
			}

			// Insurance is OFF

			Thread.sleep(5000);
			if(leaseQues.isInsuranceYesRadioBtnDisplayed()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Add insurance option available in the page");
				logger.log(LogStatus.INFO, "Add insurance option available in the page",image);

			}
			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Add insurance option is not there in the page");
				logger.log(LogStatus.INFO, "Add insurance option is not there in the page",image);

			}
			Thread.sleep(3000);
			leaseQues.clickAddInsuranceNo();
			logger.log(LogStatus.PASS, "Selecting Insurance yes Radio button to add insurnace");
			Thread.sleep(1000);

			leaseQues.clickAccessZone();
			Thread.sleep(2000);

			driver.findElement(By.xpath("//ul[@id='LeaseQuestionnaireUnits_0__GateControllerTimeZoneID_listbox']//li[2]")).click();

			Thread.sleep(5000);
			leaseQues.clickKeypadZone();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//li[contains(@id,'KeypadZone_option_selected')]/following-sibling::li[1]")).click();

			Thread.sleep(5000);
			leaseQues.clickConfirmCust();

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");

			Thread.sleep(12000);
			List<WebElement> allCheckbox = driver.findElements(By.xpath("//section[@class='term-group term-group--active']//input[@type='checkbox']"));

			for (WebElement checkbox : allCheckbox) {
				checkbox.click();
			}
			Thread.sleep(5000);
			driver.findElement(By.id("confirmButton")).click();

			Thread.sleep(3000);
			WebElement signature = driver.findElement(By.xpath("//div[@class='sig sigWrapper']/canvas[@class='pad js-signature-canvas']"));
			Actions actionBuilder = new Actions(driver);
			Action drawAction = actionBuilder.moveToElement(signature, 660, 96).click().clickAndHold(signature).moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature).build();
			drawAction.perform();

			Thread.sleep(4000);
			driver.findElement(By.id("confirmButton")).click();
			Thread.sleep(5000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));

			Leasing_ReviewNApprovePage review = new Leasing_ReviewNApprovePage(driver);

			Thread.sleep(8000);
			review.clickApprove_btn();

			Thread.sleep(8000);
			review.clickSaveproceed_btn();

			Leasing_RentalFeePage rentalfee = new Leasing_RentalFeePage(driver);

			Thread.sleep(15000);
			logger.log(LogStatus.PASS, "Validating Monthly rent and Promotions in Eligible Promotion Page");

			rentalfee.clickConfirmCust_btn();
			Thread.sleep(8000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");

			Thread.sleep(5000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");
			Thread.sleep(8000);
			Leasing_PaymentMethodsPage payment = new Leasing_PaymentMethodsPage(driver);
			logger.log(LogStatus.PASS, "creating object for the Login Page sucessfully ");
			Thread.sleep(2000);

			String screen = Generic_Class.takeScreenShotPath();
			String imga = logger.addScreenCapture(screen);
			logger.log(LogStatus.PASS, "Payment page is dispalyed sucessfully ");
			logger.log(LogStatus.INFO, "Payment page is dispalyed sucessfully", imga);
			String MonthlyRentDueNowAmount = payment.get_MonthlyRentDueNowAmt();
			logger.log(LogStatus.PASS, "Monthly Rent Due Now amount in payment page is------>:" + MonthlyRentDueNowAmount);

			String PromotionDueNowAmount = payment.get_PromotionDueNowAmt();
			logger.log(LogStatus.PASS, "Promotion  Due Now amount in payment page is------>:" + PromotionDueNowAmount);

			String InsuranceNowAmount = payment.get_InsuranceDueNowAmt();
			logger.log(LogStatus.PASS, "Insurance  Due Now amount in payment page is------>:" + InsuranceNowAmount);

			String AdministrativeDueNowAmount = payment.get_AdministrativeDueNowAmt();
			logger.log(LogStatus.PASS,"Administrative  Due Now amount in payment page is------>:" + AdministrativeDueNowAmount);

			String PayThroughDueNowAmount = payment.get_PaythroughDueNowAmt();
			logger.log(LogStatus.PASS, "PayThrough  Due Now amount in payment page is------>:" + PayThroughDueNowAmount);

			String MerchandiseAmount = payment.get_MerchandiseAmt();
			logger.log(LogStatus.PASS, "MerchandiseAmount  amount in payment page is------>:" + MerchandiseAmount);


			String TotalRemaingBalanceAmt = payment.get_TotalRemaingBalanceAmt();
			logger.log(LogStatus.PASS, "Total Remaing Balance Amount  in payment page is------>:" + TotalRemaingBalanceAmt);

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
			Thread.sleep(3000);
			payment.clk_ConfirmWthCustBtn();
			logger.log(LogStatus.PASS, "clicked on the ConfirmWthCustBtn button");
			Thread.sleep(8000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");

			Thread.sleep(5000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");
			Thread.sleep(5000);

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
			driver.findElement(By.xpath("//div[@class='k-widget k-window']//a[contains(text(),'No')]")).click();

			Thread.sleep(8000);

			if (pmhomepage.isexistingCustomerModelDisplayed()) {
				String pa = Generic_Class.takeScreenShotPath();
				String img1 = logger.addScreenCapture(pa);
				logger.log(LogStatus.PASS, "PM DashBoard dispalyed sucessfully");
				logger.log(LogStatus.INFO, "PM DashBoard dispalyed sucessfully", img1);
			} else {
				String scp = Generic_Class.takeScreenShotPath();
				String imag = logger.addScreenCapture(scp);
				logger.log(LogStatus.FAIL, "PM DashBoard dispalyed  is not displayed");
				logger.log(LogStatus.INFO, "PM DashBoard dispalyed  is not displayed", imag);
			}

			Thread.sleep(2000);
			pmhomepage.clk_AdvSearchLnk();
			logger.log(LogStatus.PASS, "clicked on the submit button");

			Thread.sleep(6000);

			Advance_Search advSearch = new Advance_Search(driver);

			String sqlQuery = "select accountid from account where customerid =(select customerid from customer where contactid = (select contactid from contact where firstname = '"+ FN + "' and lastname = '" + tabledata.get("LastName") + "'))";

			String accNUm = DataBase_JDBC.executeSQLQuery(sqlQuery);
			Thread.sleep(6000);
			logger.log(LogStatus.PASS, "fetched account number from databse and account number is:" + accNUm);

			advSearch.enterAccNum(accNUm);
			logger.log(LogStatus.PASS, "entered account number");

			Thread.sleep(5000);

			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.PASS, "clicked on the search button in advance search page");

			Cust_AccDetailsPage cust = new Cust_AccDetailsPage(driver);
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

			Double db3 = new Double(payment.getDoubleAmount(totalDueNowAmount));

			if (db3.intValue() == 0) {

				String sap = Generic_Class.takeScreenShotPath();
				String mag = logger.addScreenCapture(sap);
				logger.log(LogStatus.PASS, "Payment done  successfully and changes reflecting in customer dash board");
				logger.log(LogStatus.INFO, "Payment done  successfully and changes reflecting in customer dash board", mag);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scan = Generic_Class.takeScreenShotPath();
				String mg = logger.addScreenCapture(scan);
				logger.log(LogStatus.FAIL, "Payment not done successfully ");
				logger.log(LogStatus.INFO, "Payment not done successfully ", mg);
			}
			
			
			Thread.sleep(1000);

			// Verify Total Due Now is available

			if (cust.getTotalDueNowTxt().contains("Total Due Now")) {
				Thread.sleep(2000);
				String scp = Generic_Class.takeScreenShotPath();
			
				String img = logger.addScreenCapture(scp);
				logger.log(LogStatus.PASS,
						"Total Due Now is displayed successfully" + cust.getCustDashboardTitle());
				logger.log(LogStatus.PASS, "Total Due Now is displayed successfully", img);
			}

			else {
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
				logger.log(LogStatus.PASS,
						"Next Payment Due is displayed successfully" + cust.getCustDashboardTitle());
				logger.log(LogStatus.PASS, "Next Payment Due is displayed successfully", img);
			}

			else {
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
				logger.log(LogStatus.PASS,
						"Make Payment is displayed successfully" + cust.getCustDashboardTitle());
				logger.log(LogStatus.PASS, "Make Payment is displayed successfully", im);
			}

			else {
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
				logger.log(LogStatus.PASS,
						"Manage AutoPay is displayed successfully" + cust.getCustDashboardTitle());
				logger.log(LogStatus.PASS, "Manage AutoPay is displayed successfully", im);
			}

			else {
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
				logger.log(LogStatus.PASS,
						"Create Note is displayed successfully" + cust.getCustDashboardTitle());
				logger.log(LogStatus.PASS, "Create Note is displayed successfully", im);
			}

			else {
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
			}

			else {
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
				logger.log(LogStatus.PASS,
						"Customer Info tab displayed successfully" + cust.getCustomerInfoTabTxt());
				logger.log(LogStatus.PASS, "Customer Info tab displayed successfully", ie);
			}

			else {
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
				logger.log(LogStatus.PASS,
						" Space Details tab displayed successfully" + cust.getSpaceDetailsTabTxt());
				logger.log(LogStatus.PASS, " Space Details tab displayed successfully", im);
			}

			else {
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
			}

			else {
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
				logger.log(LogStatus.PASS,
						"  Documents tab displayed successfully" + cust.getDocumentsTabTxt());
				logger.log(LogStatus.PASS, " Documents tab displayed successfully", im);
			}

			else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String sc = Generic_Class.takeScreenShotPath();
				String im = logger.addScreenCapture(sc);
				logger.log(LogStatus.FAIL, "Documents tab not  displayed successfully");
				logger.log(LogStatus.FAIL, "Documents tab not displayed successfully", im);
			}
			
			if(cust.getCustSpaceNum().trim().contains(space)){
				logger.log(LogStatus.PASS, "Application is displaying correct space number in the customer dashboard");	
			}else{
				
				logger.log(LogStatus.PASS, "Application is not displaying correct space number in the customer dashboard");
			}
			
			
            if(cust.getEmailid(tabledata.get("Email"))){
            	
            logger.log(LogStatus.PASS, "Application is displaying correct email id in the customer info tab");	
            }else{
            	
            	logger.log(LogStatus.FAIL, "Application is not displaying correct email id in the customer info tab");	
            }
            

		}catch (Exception e) {
			resultFlag = "fail";
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "PM Dash board page is not displayed", image);
			e.printStackTrace();
		}

	}
	@AfterMethod
	public void afterMethod() {

		System.out.println(" In After Method");
		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "Leasing", "Leasing_LeaseWithInsurance", "Status","Pass");
		} else if (resultFlag.equals("fail")) {
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, " page is not displayed", image);
			Excel.setCellValBasedOnTcname(path, "Leasing", "Leasing_LeaseWithInsurance", "Status","Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "Leasing", "Leasing_LeaseWithInsurance", "Status","Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);

	}


}
