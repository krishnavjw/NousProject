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
import Pages.CustDashboardPages.Cust_AccActivitiesPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.TransactionReversedPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.ReversePayment;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class Payments_LookUpForPaymentAcitivitiesInCustomerLedger extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"Payments","Payments", "Payments_LookUpForPaymentAcitivitiesInCustomerLedger");
	}

	@Test(dataProvider="getLoginData")
	public void Payments_LookUpForPaymentAcitivitiesInCustomerLedger(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Payments").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "Payments_LookUpForPaymentAcitivitiesInCustomerLedger is Skipped");
			throw new SkipException("Skipping the test Payments_LookUpForPaymentAcitivitiesInCustomerLedger");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Payments_LookUpForPaymentAcitivitiesInCustomerLedger","Payments_LookUpForPaymentAcitivitiesInCustomerLedger");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
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

			//=================Handling customer facing device========================
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			Thread.sleep(5000);
			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));  

			List<WebElement> biforstSystem=driver.findElements(By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for(WebElement ele:biforstSystem)
			{
				if(biforstNum.equalsIgnoreCase(ele.getText().trim()))
				{
					Reporter.log(ele.getText()+"",true);
					ele.click();
					break;
				}
			}

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			//=================================================================================
			Thread.sleep(3000);
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			logger.log(LogStatus.PASS, "creating object for the PM home page sucessfully");

			Thread.sleep(3000);
			if(pmhomepage.isexistingCustomerModelDisplayed()){

				logger.log(LogStatus.PASS, "Existing Customer module is present in the PM DashBoard sucessfully");
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Existing Customer module is not present in the PM DashBoard");
			}

			Thread.sleep(4000);

			if(pmhomepage.get_findACustomerAtThisLocText().contains(tabledata.get("FindCustomeratLocText"))){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find a Customer at this Location is displayed successfully");
				logger.log(LogStatus.INFO, "Find a Customer at this Location is displayed successfully",image);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Find a Customer at this Location is not displayed ");
				logger.log(LogStatus.INFO, "Find a Customer at this Location is not displayed ",image);

			}

			Thread.sleep(3000);
			if(pmhomepage.get_findACustomerText().trim().contains(tabledata.get("CustomerButtontext"))){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find Customer button is displayed successfully");
				logger.log(LogStatus.INFO, "Find Customer button is displayed successfully",image);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Find Customer button  is not displayed ");
				logger.log(LogStatus.INFO, "Find Customer button is not displayed ",image);

			}


			Thread.sleep(1000);
			pmhomepage.clk_AdvSearchLnk();
			logger.log(LogStatus.PASS, "clicked on advanced search link in the PM Dashboard sucessfully");

		
			Advance_Search search=new Advance_Search(driver);
			Thread.sleep(6000);
			logger.log(LogStatus.PASS, "creating object for advance search page sucessfully");

			if(search.verifyAdvSearchPageIsOpened())
			{
				logger.log(LogStatus.PASS, "Advanced Search page is opened");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);

			}
			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.PASS, "Advanced Search page is not opened");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}

			String SiteNumber=search.getLocationNum();
			logger.log(LogStatus.PASS, "location number is:"+SiteNumber);

			Thread.sleep(2000);

			String accnum=DataBase_JDBC.executeSQLQuery("select top 1 a.accountnumber from account a join accountorder ao on ao.accountid=a.accountid join accountorderitem aoi on aoi.accountorderid=ao.accountorderid join site s on s.siteid=aoi.siteid join storageorderitem soi on soi.storageorderitemid=aoi.storageorderitemid join rentalunit ru on ru.rentalunitid=soi.rentalunitid join cltransaction clt on clt.accountorderitemid=aoi.accountorderitemid where soi.vacatedate is not null and s.sitenumber='"+SiteNumber+"' group by a.accountnumber, aoi.accountorderitemid, ru.rentalunitnumber having sum(clt.amount + clt.discountamount)>0 order by 1");
			Thread.sleep(8000);
			logger.log(LogStatus.PASS, "Fetching account number from database and account number is:"+accnum);

			Thread.sleep(2000);

			search.enterAccNum(accnum);
			logger.log(LogStatus.INFO, "Enter existing customer Account Num in Account field successfully");

			Thread.sleep(3000);
			search.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "clicking on Search button successfully");

			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			Thread.sleep(8000);

			if(cust_accdetails.isCustdbTitleDisplayed()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "customer Dashboard is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "customer Dashboard is not displayed ",image);
			}

			Thread.sleep(1000);
			String toatlDueNowAmtBeforeMakePymt=cust_accdetails.getTotalDue();
			String new_toatlDueNowAmtBeforeMakePymt = toatlDueNowAmtBeforeMakePymt.replaceAll("[$,]", "");
			Float float_toatlDueNowAmtBeforeMakePymt = Float.parseFloat(new_toatlDueNowAmtBeforeMakePymt);
			logger.log(LogStatus.PASS, "Total due now amount before making payment in customer dashbaord is:"+toatlDueNowAmtBeforeMakePymt);

			Thread.sleep(1000);
			cust_accdetails.click_AccountActivities();
			logger.log(LogStatus.INFO, "Clicked on Account activities tab");

			Thread.sleep(5000);
			Cust_AccActivitiesPage Cust_AccActivitiesPage = new Cust_AccActivitiesPage(driver);
			String Bal_BeforePayment = Cust_AccActivitiesPage.get_BalAmt().replaceAll("[$,]", "");
			Float Float_Bal_BeforePayment = Float.parseFloat(Bal_BeforePayment);
			
			cust_accdetails.clk_ExpandBtn();
			logger.log(LogStatus.INFO, "Clicked on expand button in first row");

			Thread.sleep(6000);

			
			String GLBal_BeforePayment = Cust_AccActivitiesPage.get_Amt();
			String new_GLBal_BeforePayment = GLBal_BeforePayment.replaceAll("[$,]", "");
			logger.log(LogStatus.PASS, "GL balance amount before making payment in customer dashbaord is:"+GLBal_BeforePayment);
			Reporter.log("GL balance amount before making payment in customer dashbaord is:"+GLBal_BeforePayment,true);

			Float Float_GLBal_BeforePayment = Float.parseFloat(new_GLBal_BeforePayment);

			cust_accdetails.clickMakePayment_Btn();
			logger.log(LogStatus.INFO, "clicking on Make Payment button successfully in customer dashboard");
			Thread.sleep(5000);

			PaymentsPage payments= new PaymentsPage(driver);

			if(payments.isPaymentsPageDisplayed()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Payments page is displayed successfully");
				logger.log(LogStatus.INFO, "Payments page is displayed successfully",image);

			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Payments page is not displayed ");
				logger.log(LogStatus.INFO, "Payments page is not displayed ",image);

			}
			Thread.sleep(6000);
			payments.clickOnConfirmWithCustomer_Btn();
			logger.log(LogStatus.INFO, "clicked on Confirm with customer button successfully in payment screen");
			Thread.sleep(5000);

			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);

			boolean val=driver.findElement(By.id("confirmButton")).isDisplayed();

			if(val){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "confirm with customer window is displayed successfully");
				logger.log(LogStatus.INFO, "confirm with customer window is displayed successfully",image);


			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "confirm with customer window is not displayed ");
				logger.log(LogStatus.INFO, "confirm with customer window is not displayed ",image);


			}

			String totaldue=driver.findElement(By.xpath("//span[@id='balance']")).getText().trim();
			logger.log(LogStatus.PASS, "Total due amount in confirm with customer page before making payment is:"+totaldue);

			Thread.sleep(2000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");
			Thread.sleep(5000);

			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Main page successfully");


			Thread.sleep(5000);
			String Amount= payments.get_TotalRemainingAmt();
			logger.log(LogStatus.PASS, "Toatl remaining amount in payment screen is:"+Amount);

			Thread.sleep(2000);
			payments.selectPaymentMethod("Check", driver);
			logger.log(LogStatus.INFO, "Select the Check option from Payment dropdown successfully");


			Thread.sleep(6000);
			payments.clickmanualentry();
			logger.log(LogStatus.INFO, "Clicking on Manual entry button successfully");

			payments.Enter_routingNumber(tabledata.get("BankRoutingNum"));
			logger.log(LogStatus.INFO, "Entering routing Number successfully");

			payments.Enter_accountNumber(tabledata.get("CheckingAccNum"));
			logger.log(LogStatus.INFO, "Entering Account Number successfully");

			payments.Enter_checkNumber(tabledata.get("CheckNumber"));
			logger.log(LogStatus.INFO, "Entering Check Number successfully");


			Thread.sleep(2000);
			payments.Enter_checkAmount(Amount);


			logger.log(LogStatus.INFO, "Entering Check amount successfully");

			Thread.sleep(3000);
			payments.clickapplybtn();
			logger.log(LogStatus.INFO, "Click on Apply button successfully");

			Thread.sleep(2000);
			payments.clickSubmitbtn();
			logger.log(LogStatus.INFO, "Click on submit button successfully");

			Thread.sleep(5000);
			TransactionCompletePopup transPopup=new TransactionCompletePopup(driver);

			boolean title=driver.findElement(By.xpath("//span[text()='Transaction Complete']")).isDisplayed();
			if(title){


				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Transaction Complete window is displayed successfully");
				logger.log(LogStatus.INFO, "Transaction Complete window is displayed successfully",image);


			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Transaction Complete window is not displayed ");
				logger.log(LogStatus.INFO, "Transaction Complete window is not displayed ",image);


			}

			transPopup.enterEmpNum(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Enter Employee Id  successfully");

			Thread.sleep(6000);
			transPopup.clickOk_btn();
			logger.log(LogStatus.INFO, "Click on Ok button successfully");


			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);

			boolean sucessmsg=driver.findElement(By.xpath("//div[text()='Payment Complete']")).isDisplayed();
			if(sucessmsg){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Payment Complete Thank you! message is displayed successfully in customer screen");
				logger.log(LogStatus.INFO, "Payment Complete Thank you! message is displayed successfully in customer screen",image);

			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Payment Complete Thank you! message is not displayed in customer screen ");
				logger.log(LogStatus.INFO, "Payment Complete Thank you! message is not displayed in customer screen",image);

			}
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Main page successfully");

			Thread.sleep(6000);

			if(cust_accdetails.isCustdbTitleDisplayed()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "customer Dashboard is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "customer Dashboard is not displayed ",image);
			}


			Thread.sleep(1000);
			String toatlDueNowAmtAfterMakePymt=cust_accdetails.getTotalDue();

			logger.log(LogStatus.PASS, "Total due now amount after making payment in customer dashbaord is:"+toatlDueNowAmtAfterMakePymt);

			Thread.sleep(1000);
			

			cust_accdetails.click_AccountActivities();;
			logger.log(LogStatus.INFO, "Clicked on Account Activities tab in customer dashboard screen");

			Thread.sleep(9000);

			
			String Bal_AfterPayment = Cust_AccActivitiesPage.get_BalAmt().replaceAll("[$,]", "");
			Float Float_Bal_AfterPayment = Float.parseFloat(Bal_AfterPayment);
			
			
			
			cust_accdetails.clk_ExpandBtn();
			logger.log(LogStatus.INFO, "Clicked on expand button in first row");

			Thread.sleep(6000);

			String GLBal_AfterPayment = Cust_AccActivitiesPage.get_Amt();
			logger.log(LogStatus.PASS, "GL balance amount after making payment in customer dashbaord is:"+GLBal_AfterPayment);
			Reporter.log("GL balance amount after making payment in customer dashbaord is:"+GLBal_AfterPayment,true);
			String new_GLBal_AfterPayment = GLBal_AfterPayment.replaceAll("[$,]", "");
			Float Float_GLBal_AfterPayment = Float.parseFloat(new_GLBal_AfterPayment);

			if(Float_GLBal_AfterPayment == Float_GLBal_BeforePayment && Float_Bal_AfterPayment == (Float_Bal_BeforePayment-Float_GLBal_BeforePayment))
			{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Payment acitivities in customer ledger is displayed correctly");
				logger.log(LogStatus.INFO, "Payment acitivities in customer ledger is displayed correctly",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Payment acitivities in customer ledger is not displayed correctly");
				logger.log(LogStatus.INFO, "Payment acitivities in customer ledger is not displayed correctly",image);

			}

			cust_accdetails.clk_ReversePaymntLnk();
			logger.log(LogStatus.INFO, "Clicked on Reverse Payment link");

			Thread.sleep(8000);
			ReversePayment ReversePayment = new ReversePayment(driver);
			boolean rev=driver.findElement(By.xpath("//h3[contains(text(),'Reverse Payment')]")).isDisplayed();
			if(rev){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Reverse Payment screen is displayed successfully");
				logger.log(LogStatus.INFO, "Reverse Payment screen is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Reverse Payment screen is not displayed ");
				logger.log(LogStatus.INFO, "Reverse Payment screen is not displayed ",image);

			}

			//ReversePayment.Clk_RdBtn_Yes();
			//logger.log(LogStatus.INFO, "Clicked on No radio button");

			ReversePayment.Clk_ReasonDrpDwn();
			logger.log(LogStatus.INFO, "Clicked on Reason drop down");

			//ReversePayment.SelectValueFromReasonList("Misapplied Payment");
			ReversePayment.SelectValueFromReasonList("Check Incorrect");

			logger.log(LogStatus.INFO, "Select value from Reason dropdown");


			ReversePayment.enterNote("PM received a Bad check from bank for a customer payment so reversing the payment");
			logger.log(LogStatus.INFO, "Entered note");

			ReversePayment.Clk_RevBtn();
			logger.log(LogStatus.INFO, "Clicked on Reverse button");

			Thread.sleep(6000);

			TransactionReversedPage trnspage=new TransactionReversedPage(driver);
			trnspage.enter_EmployeeId(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered EmployeeID");

			Thread.sleep(3000);
			trnspage.click_OkBtn();
			logger.log(LogStatus.INFO, "Clicked on OK button");

			Thread.sleep(6000);
			if(cust_accdetails.isCustdbTitleDisplayed()){

				logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");

			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");

			}


			Thread.sleep(1000);
			String toatlDueNowAmtAfterReversePymt=cust_accdetails.getTotalDue();
			logger.log(LogStatus.PASS, "Total due now amount after reverse payment in customer dashbaord is:"+toatlDueNowAmtAfterReversePymt);


			Thread.sleep(1000);
			//String nextPymtDueAmtAfterReversePymt=cust_accdetails.get_NextPymtDueAmount();
			//logger.log(LogStatus.PASS, "Next Payment due amount after reverse payment is:"+nextPymtDueAmtAfterReversePymt);

			if(toatlDueNowAmtAfterReversePymt.equals(toatlDueNowAmtBeforeMakePymt)){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Total due now amount before making and after reversing the payment is same and changes data are reflecting properly in Customer Dashboard sucessfully");
				logger.log(LogStatus.INFO, "Total due now amount before making and after reversing the payment is same and changes data are reflecting properly in Customer Dashboard sucessfully",image);

			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Total due now amount before making and after reversing the payment is not same and changes data are not reflecting properly in Customer Dashboard ");
				logger.log(LogStatus.INFO, "Total due now amount before making and after reversing the payment is not same and changes data are not reflecting properly in Customer Dashboard ",image);

			}



		}catch(Exception e){
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "PM Dash board page is not displayed",image);
			e.printStackTrace();
		}

	}

	@AfterMethod
	public void afterMethod(){

		System.out.println(" In After Method");
		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path,"Paymentss","Payments_LookUpForPaymentAcitivitiesInCustomerLedger" , "Status", "Pass");
		}else if (resultFlag.equals("fail")){
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Create Reservation page is not displayed",image);
			Excel.setCellValBasedOnTcname(path,"Payments","Payments_LookUpForPaymentAcitivitiesInCustomerLedger" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Payments","Payments_LookUpForPaymentAcitivitiesInCustomerLedger" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}
