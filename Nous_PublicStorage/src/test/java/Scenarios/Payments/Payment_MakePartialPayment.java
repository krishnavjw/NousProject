package Scenarios.Payments;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.openqa.selenium.By;
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
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class Payment_MakePartialPayment extends Browser_Factory{
	
	public ExtentTest logger;
	String resultFlag="pass";
	String path="./src/main/resources/Resources/PS_TestData.xlsx";

	@DataProvider
	public Object[][] getMakePartialPayment() 
	{
		return Excel.getCellValue_inlist(path, "Payments","Payments", "Payment_MakePartialPayment");
	}
	

	@Test(dataProvider="getMakePartialPayment")
	public void Payment_MakePartialPayment(Hashtable<String, String> tabledata) throws InterruptedException 
	{


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Payments").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}	

		try{
			
			//Login to PS Application
			logger=extent.startTest("Payment_MakePartialPayment","Make A Partial Payment");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);
			
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);

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

			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);

			PM_Homepage pmhomepage = new PM_Homepage(driver);
			logger.log(LogStatus.INFO, "PM homepage object created");
			
			//Click on Advance Search link
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(5000);
			logger.log(LogStatus.PASS, "Clicked on Advance search link successfully");
			
			
			Advance_Search advsearch=new Advance_Search(driver);
			
			//Enter Account Number in Advance search page
			advsearch.enterAccNum(tabledata.get("AccountNum"));
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "Account Number Entered successfully :"+tabledata.get("AccountNum"));
	  		advsearch.clickSearchAccbtn();
	  		Thread.sleep(6000);
	  		logger.log(LogStatus.INFO, "Clicked on Search button successfully");
	  		Reporter.log("Clicked on Search button", true); 
	  		Thread.sleep(10000);
			
	  		//Customer Dashboard Page
	  		Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
	  		logger.log(LogStatus.INFO, "Customer Account Details Page object created");
			Thread.sleep(8000);
			String totalDueNowAmt=cust_accdetails.getTotalDue().trim();
			String nextPaymentDue=cust_accdetails.getNextPaymentDueAmt().trim();
			String nextPaymentDate=cust_accdetails.getNextPaymentDate().trim();
			logger.log(LogStatus.INFO, "Total due now amount before making payment:"+totalDueNowAmt);
			logger.log(LogStatus.INFO, "Next Payment Due amount before making payment:"+nextPaymentDue);
			Thread.sleep(8000);
			
			cust_accdetails.clickMakePayment_Btn();
			logger.log(LogStatus.PASS, "Clicked on Make Payment button successfully");
			
			Thread.sleep(8000);
			PaymentsPage payments= new PaymentsPage(driver);
			logger.log(LogStatus.INFO, "Payment page object created");
			Thread.sleep(15000);
			payments.clickOnConfirmWithCustomer_Btn();
			logger.log(LogStatus.INFO, "Clicked on Confirm with Customer Button successfully");
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(8000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Customer Facing screen successfully");
			Thread.sleep(8000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "Clicked on Confirm button successfully");
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(8000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switched back to Main page successfully");
			Thread.sleep(8000);
			
			payments.selectPaymentMethod("All", driver);
			logger.log(LogStatus.INFO, "Selected the Cash option from Payment dropdown successfully");
			Thread.sleep(8000);
		
			payments.clear_CashAmount();
			Thread.sleep(3000);
			payments.enter_CashAmount(tabledata.get("EnterPartialAmount"));
			logger.log(LogStatus.INFO, "Entered Partial Amount in the Payment textbox");
			Thread.sleep(6000);
			payments.clickapplybtn();
			logger.log(LogStatus.INFO, "Clicked on apply button");
			Thread.sleep(8000);
		
			String rem_BalAmt=payments.get_totalRemaningBalAmt();
			logger.log(LogStatus.INFO, "Total remaining balance after making partial payment"+rem_BalAmt);
			
			payments.clickSubmitbtn();
			logger.log(LogStatus.INFO, "Clicked on Submit button successfully");
			
			//Enter Employee Number and click on ok button
			Thread.sleep(6000);
			TransactionCompletePopup transPopup=new TransactionCompletePopup(driver);
			logger.log(LogStatus.INFO, "Transaction Complete Popup object created");
			Thread.sleep(3000);
		
			transPopup.enterEmpNum(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered Employee Id successfully");
			
			Thread.sleep(6000);
			transPopup.clickOk_btn();
			logger.log(LogStatus.INFO, "Clicked on Ok button successfully");
			Thread.sleep(10000);
			
			
			// Verify Thank you Screen 
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(10000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			boolean mes = driver.findElement(By.xpath("//div[contains(text(),'Thank you!')]")).isDisplayed();
			
			if(mes){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
		    	String image=logger.addScreenCapture(scpath);
		    	logger.log(LogStatus.PASS, "Thank you screen is displayed successfully");
		    	logger.log(LogStatus.INFO, "Thank you screen is displayed successfully",image);
				
			}else{
				 String scpath=Generic_Class.takeScreenShotPath();
				   Reporter.log(scpath,true);
				   String image=logger.addScreenCapture(scpath);
				   if(resultFlag.equals("pass"))
	                    resultFlag="fail";
				   logger.log(LogStatus.INFO, "Thank you screen is not displayed",image);
				   logger.log(LogStatus.FAIL, "Thank you screen is not displayed");
			}
			
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(8000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			
			
			Thread.sleep(5000);
			String totalRem=cust_accdetails.getTotalDue().trim();
			String nextPayAmt=cust_accdetails.getNextPaymentDueAmt().trim();
			String nextPayDate=cust_accdetails.getNextPaymentDate().trim();
			
			
			// Verifying Date and amount total due now and next payment due
			//Verifying the remaining Amount in Customer dashboard
			
			String qry1 = "Select SUM((Amount * Quantity) + DiscountAmount) as Amount " 	
						+"From DBO.CLTransactionMaster CLM WITH (NOLOCK) "
						+"INNER JOIN DBO.Type CT WITH (NOLOCK)ON CT.TypeID = CLM.CLTransactionTypeID "
						+"INNER JOIN DBO.CLTransaction CLT WITH (NOLOCK) ON CLT.CLTransactionMasterID  = CLM.CLTransactionMasterID "
						+"Where 1 = 1 And CLM.AccountID = '"+tabledata.get("AccountNum")+"' ";
			
			String ele1 = DataBase_JDBC.executeSQLQuery(qry1);
			
			
			if(totalRem.equals(ele1)){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
		    	String image=logger.addScreenCapture(scpath);
		    	logger.log(LogStatus.PASS, "Payment details for total due now is verified successfully");
		    	logger.log(LogStatus.INFO, "Payment details for total due now is verified successfully",image);
			
			}else{
				 String scpath=Generic_Class.takeScreenShotPath();
				   Reporter.log(scpath,true);
				   String image=logger.addScreenCapture(scpath);
				   if(resultFlag.equals("pass"))
	                    resultFlag="fail";
				   logger.log(LogStatus.INFO, "Payment details for total due now is not verified",image);
				   logger.log(LogStatus.FAIL, "Payment details for total due now is not verified");
				
			}
			
			
			//Verifying the Next Payment Due Amount in Customer dashboard
			if(nextPayAmt.equals(nextPaymentDue)){ 
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
		    	String image=logger.addScreenCapture(scpath);
		    	logger.log(LogStatus.PASS, "Payment details for next payment due is verified successfully");
		    	logger.log(LogStatus.INFO, "Payment details for next payment due is verified successfully",image);
			
			}else{
				 String scpath=Generic_Class.takeScreenShotPath();
				   Reporter.log(scpath,true);
				   String image=logger.addScreenCapture(scpath);
				   if(resultFlag.equals("pass"))
	                    resultFlag="fail";
				   logger.log(LogStatus.INFO, "Payment details for next payment due is not verified",image);
				   logger.log(LogStatus.FAIL, "Payment details for next payment due is not verified");
				
			}
			
			
			//Verifying the Next Payment Date in Customer dashboard
			if(nextPayDate.equals(nextPaymentDate)){ 
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
		    	String image=logger.addScreenCapture(scpath);
		    	logger.log(LogStatus.PASS, "Date for next payment due is verified successfully");
		    	logger.log(LogStatus.INFO, "Date for next payment due is verified successfully",image);
			
			}else{
				 String scpath=Generic_Class.takeScreenShotPath();
				   Reporter.log(scpath,true);
				   String image=logger.addScreenCapture(scpath);
				   if(resultFlag.equals("pass"))
	                    resultFlag="fail";
				   logger.log(LogStatus.INFO, "Date for next payment due is not verified",image);
				   logger.log(LogStatus.FAIL, "Date for next payment due is not verified");
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
		}
	}
	
	
	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "Payments","Payment_MakePartialPayment" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Payments","Payment_MakePartialPayment" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Payments","Payment_MakePartialPayment" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

}
