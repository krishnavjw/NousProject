package Scenarios.Payments;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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
import Pages.MakePaymentPages.MerchandisePage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class Payment_BuyMerchandise extends Browser_Factory {

	public ExtentTest logger;

	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";

	@DataProvider
	public Object[][] getPaymentsData() {
		return Excel.getCellValue_inlist(path, "Payments", "Payments", "Payment_BuyMerchandise");
	}

	@Test(dataProvider = "getPaymentsData")
	public void Payment_BuyMerchandise(Hashtable<String, String> tabledata) throws InterruptedException {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("Payments").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test Payment_BuyMerchandise");
		}
		try {

			logger = extent.startTest("Payment_BuyMerchandise", "Prepayment_BuyMerchandise starts ");
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

			List<WebElement> biforstSystem = driver.findElements(By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
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
			PM_Homepage pmhome = new PM_Homepage(driver);

			String location = pmhome.getLocation();

			pmhome.clk_AdvSearchLnk();
			logger.log(LogStatus.INFO, "Entered into advance search page");

			// Advance search page
			Advance_Search advSearch = new Advance_Search(driver);

			String sqlQuery = " Select Top 1 A.accountnumber " + " From AccountOrderItem AOI "
					+ " INNER JOIN Site S ON S.SiteID = AOI.SiteID "
					+ " INNER JOIN StorageOrderItem SOI ON AOI.storageOrderItemID = SOI.storageOrderItemID "
					+ " INNER JOIN AccountOrder AO  ON AO.AccountOrderID = AOI.AccountOrderID "
					+ " INNER JOIN Account A ON A.AccountID = AO.AccountID "
					+ " join rentalunit ru on ru.rentalunitid=soi.rentalunitid "
					+ " join cltransaction clt on clt.accountorderitemid=aoi.accountorderitemid "
					+ " Where soi.VacateDate is null " + " and soi.vacatenoticedate is null " + " and s.sitenumber='"
					+ location + "' " + " and soi.StorageOrderItemTypeID=4300 "
					+ " group by  A.AccountID,A.accountnumber, S.SiteID, SOI.vacatedate, SOI.rentalunitid, s.sitenumber, SOI.vacatenoticedate "
					+ " having sum(clt.amount + clt.discountamount)>0 " + " order by 1 ";

			String AccountNumber = DataBase_JDBC.executeSQLQuery(sqlQuery);

			advSearch.enterAccNum(AccountNumber);
			logger.log(LogStatus.INFO, "Account number entered successfully:" + AccountNumber);

			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(1000);

			advSearch.clk_SearchAccount();
			logger.log(LogStatus.INFO, "Entered into advance search page");
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Clicked Search button successfully");
			Thread.sleep(1000);

			// Taking Screenshot
			String scpath2 = Generic_Class.takeScreenShotPath();
			String image2 = logger.addScreenCapture(scpath2);
			logger.log(LogStatus.PASS, "Navigated to Customer Dashboard");
			logger.log(LogStatus.INFO, "Navigated to Customer Dashboard successfully", image2);

			// Customer Dashboard
			Cust_AccDetailsPage cust_accdetails = new Cust_AccDetailsPage(driver);
			if (cust_accdetails.getCustDashboardTitle().contains("Confirm Customer's Identity and Contact Information")) {

				Thread.sleep(8000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"Customer Account Dashboard displayed successfully for an Individual Custome :"
								+ cust_accdetails.getCustDashboardTitle());
				logger.log(LogStatus.INFO, "Image", image);
			}

			else {
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer Account Dashboard not  displayed successfully ");
				logger.log(LogStatus.INFO, "Image", image);
			}

			// Getting customer DueBalance and Next Payment due with date
			String TotalDueNowBeforePayment = cust_accdetails.getTotalDue();
			logger.log(LogStatus.PASS, "Total Due Balance captured successfully :" + TotalDueNowBeforePayment);
			Thread.sleep(2000);
			String lateChargeForTotalDue = cust_accdetails.getlateChargedateForTotalDueNow();

			logger.log(LogStatus.PASS, "Late Charge For Due Balance captured successfully :" + lateChargeForTotalDue);

			Thread.sleep(2000);

			String nextPaymentDueBal = cust_accdetails.getNextPaymentDueInCustDashboard();
			logger.log(LogStatus.PASS, "Nest Payment Due Balance captured successfully :" + nextPaymentDueBal);
			Thread.sleep(2000);

			String nestPaymentDueDate = cust_accdetails.getDateForNextPaymentDue();
			logger.log(LogStatus.PASS, "Next payment Due date captured successfully :" + nestPaymentDueDate);
			Thread.sleep(2000);

			cust_accdetails.clickMakePayment_Btn();
			logger.log(LogStatus.INFO, "clicking on Make Payment button successfully");
			Thread.sleep(8000);

			PaymentsPage payments = new PaymentsPage(driver);



			payments.clicAdd_EditMerchandise();
			logger.log(LogStatus.PASS, "Clicked on Add Merchandise successfully ");
			Thread.sleep(4000);
			MerchandisePage merchandise = new MerchandisePage(driver);
			merchandise.clk_BoxesRadioBtn();
			Thread.sleep(3000);
			logger.log(LogStatus.PASS, "Clicked on Boxes radio button successfully ");

			merchandise.enter_Quantity("2");
			logger.log(LogStatus.PASS, "Entered Quantity  successfully ");
			Thread.sleep(2000);
			merchandise.clk_AddItemsToCart();
			logger.log(LogStatus.PASS, "Clicked on Add Items To Cart  successfully ");

			Thread.sleep(15000);
			merchandise.clk_CloseBtn();
			logger.log(LogStatus.PASS, "Clicked on close button successfully ");

			// Need to have DB Query which will fetch Monthly rent ans insurance
			// amount for a particular account

			payments.clickOnConfirmWithCustomer_Btn();
			logger.log(LogStatus.INFO, "clicked on Confirm with customer button successfully");
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Main page successfully");


			//Cost validation
			String standardSpaceSubtotal=driver.findElement(By.xpath("//div[@id='payment-table']/div[1]//section[@class='unit-subtotal alt row']/div[contains(text(),'Standard Space Subtotal')]/..//div[@class='second col']//span")).getText();

			String MerchandiseSubTotal =driver.findElement(By.xpath("//div[@id='payment-merchandise-details']//section[@class='row merchandise-subtotal']/div[contains(text(),'Merchandise Subtotal')]/..//div[@class='second col']/span")).getText();


			double standardSpaceSubtotalAmount =Double.parseDouble(standardSpaceSubtotal.substring(1));

			double MerchandiseSubTotalAmount =Double.parseDouble(MerchandiseSubTotal.substring(1));

			double totalAmount=Double.parseDouble(payments.getTotalDueNow().substring(1));

			if(totalAmount==standardSpaceSubtotalAmount+MerchandiseSubTotalAmount){

				Reporter.log("the total is equal to Monthly Rent and Insurance amount:", true);

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"displayed total is sum of Standard Space Subtotal " + standardSpaceSubtotalAmount + "Merchandise Subtotal :"
								+ MerchandiseSubTotalAmount + "Total Amount is :"
								+ totalAmount);
				logger.log(LogStatus.PASS, "displayed total sum of Standard Space SubTotal and Merchandise Subtotal", image);

			} else {
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL,
						" Not displayed total is sum of Standard Space Subtotal " + standardSpaceSubtotalAmount + "Merchandise Subtotal :"
								+ MerchandiseSubTotalAmount + "Total Amount is :"
								+ totalAmount);
				logger.log(LogStatus.FAIL, " Not total sum of Standard Space SubTotal and Merchandise Subtotal", image);

			}



			// Select Cash From Payment method dropdown
			Thread.sleep(3000);
			payments.selectPaymentMethod("Cash", driver);
			logger.log(LogStatus.INFO, "Select the Check option from Payment dropdown successfully");

			Thread.sleep(6000);

			// Apply Total Amount Due Now in amount filed under Payment Section

			String DueNowTotalAmount = payments.getTotalDueNow();

			payments.enter_CashAmount(DueNowTotalAmount);
			logger.log(LogStatus.PASS, "Amount entered successfully:" + DueNowTotalAmount);

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			payments.clickapplybtn();
			logger.log(LogStatus.INFO, "Click on Apply button successfully");

			Thread.sleep(2000);
			payments.clickSubmitbtn();
			logger.log(LogStatus.INFO, "Click on submit button successfully");


			Thread.sleep(5000);
			TransactionCompletePopup transPopup = new TransactionCompletePopup(driver);

			transPopup.enterEmpNum(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Enter Employee Id  successfully");

			Thread.sleep(6000);
			transPopup.clickOk_btn();
			logger.log(LogStatus.INFO, "Click on Ok button successfully");

		}

		catch (Exception ex) {
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
			Excel.setCellValBasedOnTcname(path, "Payments", "Payment_BuyMerchandise", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "Payments", "Payment_BuyMerchandise", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "Payments", "Payment_BuyMerchandise", "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
	}

}
