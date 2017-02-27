/*package Scenarios.MiscallaneousCharges;

import java.net.Inet4Address;
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
import Pages.AdvSearchPages.Adv_SearchResultPage;
import Pages.AdvSearchPages.Advance_Search;
import Pages.AutoPayPages.Autopay_PreferencesPage;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;

import Pages.DMledgeredit.EditLedger;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;

import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;


public class Verify_The_Edit_General_Ledger extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"Miscellaneous","Miscellaneous", "Customer_MiscellaneousCharges_DM");
	}

	@Test(dataProvider="getLoginData")
	public void Customer_MiscellaneousCharges_DM(Hashtable<String, String> tabledata) throws Exception 
	{
		

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Miscellaneous").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "Payments_ReversePymtIndCustSingleSpace_Check is Skipped");
			throw new SkipException("Skipping the test Payments_ReversePymtIndCustSingleSpace_Check");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			
			logger=extent.startTest("Customer_MiscellaneousCharges_DM","Customer_MiscellaneousCharges_DM");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(10000);
			
			Dashboard_BifrostHostPopUp Bifrostpop1= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			Bifrostpop1.clickContiDevice();
			Thread.sleep(10000);

			
			//==================================Verifying Home page===============================================
			
			
			
            JavascriptExecutor js1 = (JavascriptExecutor)driver;
            DM_HomePage dmhomepage=new DM_HomePage(driver); 
            logger.log(LogStatus.PASS, "Created object for the DM home page Successfully");
            dmhomepage.dmSiteDrop();
            Thread.sleep(2000);
            dmhomepage.selSiteId();
            String location = dmhomepage.getSiteId();
            logger.log(LogStatus.INFO, "Site id is selected from Site Dropdown");
            Thread.sleep(3000);
            js1.executeScript("window.scrollBy(1000,0)", "");
            Thread.sleep(4000);
            dmhomepage.clk_advancedSearchLnk();
            logger.log(LogStatus.PASS, "Clicked on Advanced Search Link in DM Dashboard Successfully");


			
			//==============================Entering Account number and search====================================
			Thread.sleep(6000);
			Advance_Search search=new Advance_Search(driver);
			logger.log(LogStatus.PASS, "creating object for advance search page sucessfully");

			if(search.verifyAdvSearchPageIsOpened())
			{
				logger.log(LogStatus.PASS, "Advanced Search page is opened");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);

			}
			else{
				logger.log(LogStatus.PASS, "Advanced Search page is not opened");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			String SiteNumber=dmhomepage.Sitenumber_DM();
			logger.log(LogStatus.PASS, "location number is:"+SiteNumber);

			Thread.sleep(2000);
			Advance_Search advSearch= new Advance_Search(driver);
			Adv_SearchResultPage adv_searchresultPage=new Adv_SearchResultPage(driver);
			 advSearch.enterFirstName(tabledata.get("FirstName"));
				logger.log(LogStatus.INFO, "Entered First name");
			
				
				js1.executeScript("window.scrollBy(250,0)", "");
			    Thread.sleep(5000);
			    js1.executeScript("window.scrollBy(0,350)", "");
			    Thread.sleep(3000);
				advSearch.clickSearchbtn();
				Thread.sleep(10000);
				logger.log(LogStatus.INFO, "Click on Search button successfully");
				js1.executeScript("window.scrollBy(250,0)", "");
			    Thread.sleep(5000);
			    js1.executeScript("window.scrollBy(0,350)", "");
			    Thread.sleep(3000);
			//	String accnum=adv_searchresultPage.get_txt_accountNumber();
				String accnum=driver.findElement(By.xpath("//table[@class='k-selectable']/tbody/tr[1]/td[11]")).getText();
				System.out.println(" the account is----"+accnum);
				 Thread.sleep(3000);
				 
				  js1.executeScript("window.scrollBy(0,-6000)", "");
				  advSearch.enterAccNum(accnum);
					logger.log(LogStatus.INFO, "Enter existing customer Account Num in Account field successfully");

					Thread.sleep(3000);
					advSearch.clickSearchAccbtn();
					logger.log(LogStatus.INFO, "clicking on Search button successfully");
					Thread.sleep(8000);
//===================================Customer DashBoard==============================================================
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			

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
			logger.log(LogStatus.PASS, "Total due now amount before making payment in customer dashbaord is:"+toatlDueNowAmtBeforeMakePymt);
		
			Thread.sleep(5000);
			
			
			
		
			
//============================= Add mis charges======================================================
			
 	
			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(4000);
			String spaceNum=cust_accdetails.spacenum_gettext();
			Thread.sleep(4000);
			
			String s=driver.getCurrentUrl();
			String str=s.replace("Customer/Account", "Account/GeneralLedgerAdjustments");
			System.out.println(" the url is ------"+spaceNum);
			Thread.sleep(2000);
 			driver.get(str);
			
			
 			Thread.sleep(5000);
 			EditLedger ledgeredit= new EditLedger(driver);
 			
 			ledgeredit.ledger_dropdown(spaceNum, driver);
 			Thread.sleep(5000);
 			 	        
 			 String adjustmentcategory="select name from GLAccount where Adjustable='1'";

 			 ArrayList<String> adjustmentcategorylist=DataBase_JDBC.executeSQLQuery_List(adjustmentcategory);
 			
 			//==================== verify the Adjustmenr of ledger==============================
 			
 			JavascriptExecutor js = (JavascriptExecutor)driver;
 			List<WebElement> numledger= driver.findElements(By.xpath("//div[@id='glaGrid']//table//tr"));
 			
 			if(numledger.size()==adjustmentcategorylist.size()){
 				
 			ledgeredit.ledger_valueentery();
 			}else{
 				if(resultFlag.equals("pass"))
						resultFlag="fail";

					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Adjustment Category records are not Matching ");
					logger.log(LogStatus.INFO, "Adjustment Category records are not Matching ",image);
 				
 			}
 			Thread.sleep(2000);
 			ledgeredit.Enter_note(tabledata.get("Notes"));
 			Thread.sleep(2000);
 			 boolean errormsg=driver.getPageSource().contains("New total can not be below zero");
 			 boolean errormsg2=driver.getPageSource().contains("Total amount does not equal zero");
 			
 			 if(errormsg || errormsg2){

 					String scpath=Generic_Class.takeScreenShotPath();
 					String image=logger.addScreenCapture(scpath);
 					logger.log(LogStatus.PASS, "Error message is displayed");
 					logger.log(LogStatus.INFO, "Error message is displayed",image);
 				}else{

 					if(resultFlag.equals("pass"))
 						resultFlag="fail";

 					String scpath=Generic_Class.takeScreenShotPath();
 					String image=logger.addScreenCapture(scpath);
 					logger.log(LogStatus.FAIL, "Error message is not  displayed ");
 					logger.log(LogStatus.INFO, "Error message is not displayed ",image);
 				}
 			Thread.sleep(2000);
		 driver.findElement(By.xpath("//a[contains(text(),'Save')]")).click();
		 
		 
		
		driver.findElement(By.id("enteredEmployeeNumber")).sendKeys(tabledata.get("UserName"));
		
		 driver.findElement(By.xpath("//a[contains(text(),'Confirm')]")).click();
		 Thread.sleep(10000);
		 //============================= Verify the Customer DashBoard==============================================
		 
		 
		 cust_accdetails.click_AccountActivities();;
         logger.log(LogStatus.INFO, "Clicked on Account Activities tab in customer dashboard screen");

         Thread.sleep(9000);

         boolean Adjustment_Chargedisplay=cust_accdetails.VerifyAdjustmentCharge_Link();
         if(Adjustment_Chargedisplay){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "customer Dashboard is displayed Adjustment_Charge");
				logger.log(LogStatus.INFO, "customer Dashboard is displayed Adjustment_Charge",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Adjustment_Charge is not displayed ");
				logger.log(LogStatus.INFO, "Adjustment_Charge is not displayed ",image);
			}
         

         Thread.sleep(6000);

		 String toatlDueNowAmtafterMakePymt=cust_accdetails.getTotalDue();
		 
		 
		 //============================= Verify the View Details screen===========================================
		  Thread.sleep(3000);
		 driver.findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		  Thread.sleep(6000);
		 WebElement total_view_detail_due= driver.findElement(By.xpath("//div[@class='total-due-now__details__detail total-due-now__details__detail--total']"));
		  js1.executeScript("arguments[0].scrollIntoView(true);",total_view_detail_due);
		  String total_view_detail=total_view_detail_due.getText();
		  
		  if(total_view_detail.equals(toatlDueNowAmtafterMakePymt)){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Total due is displaying Correctly");
				logger.log(LogStatus.INFO, "Total due is displaying Correctly",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Total due is not displaying Correctly ");
				logger.log(LogStatus.INFO, "Total due is not displaying Correctly ",image);
			}
			
		 
//		  WebElement cancelbtn=driver.findElement(By.xpath("//a[contains(text(),'Cancel')]"));
//		  js1.executeScript("arguments[0].scrollIntoView(true);",cancelbtn);
//		  cancelbtn.click();
//		  Thread.sleep(5000);
		  
		 String missvaluequery="Select  * from CLTransaction where CLTransactionMasterID=( "

						+"Select top 1 CLTransactionMasterID from CLTransactionMaster where AccountID='"+accnum+"' order by LastUpdate desc)";


			ArrayList<String> adjusmentDB_entry=DataBase_JDBC.executeSQLQuery_List(missvaluequery);
			Thread.sleep(8000);
			logger.log(LogStatus.PASS, "Fetching Missvalue from database and account number is:"+adjusmentDB_entry.size());
		 
			if(adjusmentDB_entry.size()>0){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Adjustment displaying Correctly in DB");
				logger.log(LogStatus.INFO, "Adjustment displaying Correctly in DB",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Adjustment is not displaying Correctly in DB ");
				logger.log(LogStatus.INFO, "Adjustment is  not displaying Correctly in DB",image);
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
			Excel.setCellValBasedOnTcname(path,"Payments","Customer_MiscellaneousCharges_DM" , "Status", "Pass");
		}else if (resultFlag.equals("fail")){
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "page is not displayed",image);
			Excel.setCellValBasedOnTcname(path,"Payments","Verify_The_Edit_General_Ledger" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Payments","Verify_The_Edit_General_Ledger" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: ", true);

	}
}
*/