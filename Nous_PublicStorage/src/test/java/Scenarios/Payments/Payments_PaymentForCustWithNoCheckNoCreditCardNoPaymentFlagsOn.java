package Scenarios.Payments;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.Payments.ChangePaymentRestrictionStatusPage;
import Pages.Payments.Payments_DMDashBoardPage;
import Scenarios.Browser_Factory;

public class Payments_PaymentForCustWithNoCheckNoCreditCardNoPaymentFlagsOn extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag = "pass";
	String path = "./src/main/resources/Resources/PS_TestData.xlsx";

	@DataProvider
	public Object[][] getLoginData() {
		return Excel.getCellValue_inlist(path, "Payments", "Payments",
				"Payments_PaymentForCustWithNoCheckNoCreditCardNoPaymentFlagsOn");
	}

	@Test(dataProvider = "getLoginData")
	public void Payments_PaymentForCustWithNoCheckNoCreditCardNoPaymentFlagsOn(Hashtable<String, String> tabledata)
			throws Exception {
		logger = extent.startTest("Payments_PaymentForCustWithNoCheckNoCreditCardNoPaymentFlagsOn",
				"Payment should not accept for the customer who has no check no credit card and no payments flags on");
		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("Payments").equals("Y"))) {
			resultFlag = "skip";
			logger.log(LogStatus.SKIP, "Payments_PaymentForCustWithNoCheckNoCreditCardNoPaymentFlagsOn is Skipped");
			throw new SkipException("Skipping the test Payments_PaymentForCustWithNoCheckNoCreditCardNoPaymentFlagsOn");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);

		try {
			// Logging in to the application with Customer Facing device
			// connected

			LoginPage login = new LoginPage(driver);
			login.enterUserName(tabledata.get("UserName"));
			login.enterPassword(tabledata.get("Password"));
			login.clickLogin();
			logger.log(LogStatus.PASS, "clicked on login in button sucessfully");
			logger.log(LogStatus.PASS, "Login to Application  successfully");

			// =================Handling customer facing
			// device========================
			Thread.sleep(2000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");

			Bifrostpop.clickContiDevice();

			/*
			 * String biforstNum=Bifrostpop.getBiforstNo();
			 * 
			 * Reporter.log(biforstNum+"",true);
			 * 
			 * driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,
			 * "t"); ArrayList<String> tabs = new ArrayList<String>
			 * (driver.getWindowHandles()); Reporter.log(tabs.size()+"",true);
			 * driver.switchTo().window(tabs.get(1));
			 * driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));
			 * 
			 * List<WebElement> biforstSystem=driver.findElements(By.xpath(
			 * "//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"
			 * )); for(WebElement ele:biforstSystem) {
			 * if(biforstNum.equalsIgnoreCase(ele.getText().trim())) {
			 * Reporter.log(ele.getText()+"",true); ele.click(); break; } }
			 * 
			 * driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,
			 * Keys.PAGE_DOWN); Thread.sleep(9000);
			 * driver.switchTo().window(tabs.get(0)); Thread.sleep(9000);
			 * driver.findElement(By.xpath(
			 * "//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']"
			 * )).click(); Thread.sleep(5000);
			 */
			// =================================================================================
			Thread.sleep(4000);
			Payments_DMDashBoardPage DMdashBoard = new Payments_DMDashBoardPage(driver);
			logger.log(LogStatus.INFO, "Object created to DM Dash board page sucessfully");

			if (DMdashBoard.is_DMDashBoardTitle()) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "DM dash board page is  displayed sucessfully");
				logger.log(LogStatus.INFO, "DM dash board page is  displayed sucessfully", image);
				resultFlag = "pass";
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "DM dash board page is not displayed");
				logger.log(LogStatus.INFO, "DM dash board page is not displayed", image);
			}

			DMdashBoard.click_advSearchLink();
			logger.log(LogStatus.PASS, "Clicked on advance search link in DM dash board");
			Thread.sleep(5000);

			Advance_Search advSearch = new Advance_Search(driver);
			logger.log(LogStatus.INFO, "Object created to Adv search page sucessfully");

			advSearch.enterAccNum(tabledata.get("AccountNum"));
			logger.log(LogStatus.PASS,
					"Entered the account number in account number field in advance search page sucessfully");

			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.PASS, "Clicked on  search account button in advance search page");
			Thread.sleep(5000);

			Cust_AccDetailsPage custDBPage = new Cust_AccDetailsPage(driver);
			
			Acc_SpaceDetailsPage spaceDetailsPage = new Acc_SpaceDetailsPage(driver);
			logger.log(LogStatus.INFO, "Object created for space details page sucessfully");
			
			ChangePaymentRestrictionStatusPage chgPayResStatus = new ChangePaymentRestrictionStatusPage(driver);
			logger.log(LogStatus.INFO, "Object created for ChangePaymentRestrictionStatusPage sucessfully");
			
			if(!custDBPage.is_makePayement_Link()){
				custDBPage.clickSpaceDetails_tab();
				logger.log(LogStatus.PASS, "Clicked on  space details tab in customer dash board page sucessfully");
				Thread.sleep(5000);
				
				spaceDetailsPage.click_paymentRestrictionModeChangeLink();
				logger.log(LogStatus.PASS,
						"Clicked on  payment restriction status change link in space details page sucessfully");
				Thread.sleep(5000);
				
				chgPayResStatus.click_noChecksChkbox();
				logger.log(LogStatus.PASS, "clicked on no checks check box");
				chgPayResStatus.click_noCreditCardsChkBox();
				logger.log(LogStatus.PASS, "clicked on no credit cards check box");
				chgPayResStatus.click_noPaymentsChkBox();
				logger.log(LogStatus.PASS, "clicked on no payments check box");
				chgPayResStatus.enter_noteEdt(tabledata.get("note"));
				logger.log(LogStatus.PASS, "entered the text into note edit box");
				chgPayResStatus.enter_empIdEdt(tabledata.get("UserName"));
				logger.log(LogStatus.PASS, "entered the employee id into employee id edit box");
				chgPayResStatus.click_updateBtn();
				logger.log(LogStatus.PASS, "clicked on update button");
				Thread.sleep(5000);
			}
			
			
			custDBPage.clickMakePayment_Btn();
			logger.log(LogStatus.PASS, "Clicked on  make payment button sucessfully");
			Thread.sleep(5000);

			PaymentsPage paymentPage = new PaymentsPage(driver);
			logger.log(LogStatus.INFO, "Object created to payment page sucessfully");

			if (paymentPage.getPageTitle().contains(tabledata.get("PaymentPageTitle"))) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "payments page  displayed sucessfully");
				logger.log(LogStatus.INFO, "payments page  is  displayed sucessfully", image);
				resultFlag = "pass";
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "payments page  is not displayed");
				logger.log(LogStatus.INFO, "payments page is not displayed", image);
			}

			paymentPage.clickCancelbtn();
			logger.log(LogStatus.PASS, "Clicked on  cancel button sucessfully");
			Thread.sleep(5000);

			custDBPage.clickSpaceDetails_tab();
			logger.log(LogStatus.PASS, "Clicked on  space details tab in customer dash board page sucessfully");
			Thread.sleep(5000);

			

			spaceDetailsPage.click_paymentRestrictionModeChangeLink();
			logger.log(LogStatus.PASS,
					"Clicked on  payment restriction status change link in space details page sucessfully");
			Thread.sleep(5000);

			

			if (chgPayResStatus.is_changePaymentRestrictionStatusPageTitle()) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "ChangePaymentRestrictionStatusPage displayed sucessfully");
				logger.log(LogStatus.INFO, "ChangePaymentRestrictionStatusPage is  displayed sucessfully", image);
				resultFlag = "pass";
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "ChangePaymentRestrictionStatusPage is not displayed");
				logger.log(LogStatus.INFO, "ChangePaymentRestrictionStatusPage is not displayed", image);
			}

			chgPayResStatus.click_noChecksChkbox();
			logger.log(LogStatus.PASS, "clicked on no checks check box");
			chgPayResStatus.click_noCreditCardsChkBox();
			logger.log(LogStatus.PASS, "clicked on no credit cards check box");
			chgPayResStatus.click_noPaymentsChkBox();
			logger.log(LogStatus.PASS, "clicked on no payments check box");
			chgPayResStatus.enter_noteEdt(tabledata.get("note"));
			logger.log(LogStatus.PASS, "entered the text into note edit box");
			chgPayResStatus.enter_empIdEdt(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "entered the employee id into employee id edit box");
			chgPayResStatus.click_updateBtn();
			logger.log(LogStatus.PASS, "clicked on update button");
			Thread.sleep(5000);

			String paymentRestrictionflags = tabledata.get("paymentRestrictionFlags");
			String[] expectedTokens = paymentRestrictionflags.split(",");
			ArrayList<String> capturedTokens = spaceDetailsPage.getTokens();
			boolean flag = false;
			for(String expToken : expectedTokens){
				
				for(int i=0; i<capturedTokens.size(); i++){
					if(capturedTokens.get(i).contains(expToken)){
						flag = true;
						break;
					}else{
						flag=false;
					}
				}
			}
				if (flag) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"Flags: No checks, no credit cards and no payments are displayed and are ON");
				logger.log(LogStatus.INFO, "Flags: No checks, no credit cards and no payments are displayed and are ON",
						image);
				resultFlag = "pass";
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Flags: No checks, no credit cards and no payments are not  displayed ");
				logger.log(LogStatus.INFO, "Flags: No checks, no credit cards and no payments are not displayed ",
						image);
			}

			if (!custDBPage.is_makePayement_Link()) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"customer with restriction payment flags: No checks, No credit cards and No payments ON"
								+ "is not allowed to make a payment");
				logger.log(LogStatus.INFO,
						"customer with restriction payment flags: No checks, No credit cards and No payments ON"
								+ "is not allowed to make a payment",
						image);
				resultFlag = "pass";
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL,
						"customer with restriction payment flags: No checks, No credit cards and No payments ON"
								+ "has the make payment link");
				logger.log(LogStatus.INFO,
						"customer with restriction payment flags: No checks, No credit cards and No payments ON"
								+ "has the make payment link",
						image);	
			}
			
			
			
			
			
			

		} catch (Exception e) {
			resultFlag = "fail";
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Test Case Failed ", image);
			e.printStackTrace();
		}

	}

	@AfterMethod
	public void afterMethod() {

		System.out.println(" In After Method");
		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "Payments",
					"Payments_PaymentForCustWithNoCheckNoCreditCardNoPaymentFlagsOn", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Create Reservation page is not displayed", image);
			Excel.setCellValBasedOnTcname(path, "Payments",
					"Payments_PaymentForCustWithNoCheckNoCreditCardNoPaymentFlagsOn", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "Payments",
					"Payments_PaymentForCustWithNoCheckNoCreditCardNoPaymentFlagsOn", "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

}
