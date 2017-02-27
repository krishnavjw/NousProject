/*package Scenarios.MiscellaneousCharges;

import java.awt.Robot;
import java.awt.event.KeyEvent;
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


public class Verify_The_Edit_General_Ledger_DM_login extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"Miscellaneous","Miscellaneous", "Verify_The_Edit_General_Ledger_DM_login");
	}

	@Test(dataProvider="getLoginData")
	public void Verify_The_Edit_General_Ledger_DM_login(Hashtable<String, String> tabledata) throws Exception 
	{
		

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Miscellaneous").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			
			logger=extent.startTest("Verify_The_Edit_General_Ledger_DM_login","Customer Miscellaneous Charges General Ledger- DM Login");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(8000);
			
			//=================Handling customer facing device========================
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");

			String biforstNum = Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum + "", true);

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, "t");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			
			
			Thread.sleep(5000);
			
			
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			driver.get("http://wc2qa.ps.com/CustomerScreen/Mount");

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
			driver.navigate().refresh();
			Thread.sleep(8000);
			driver.findElement(By
					.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']"))
					.click();
			Thread.sleep(10000);
			
			
			Dashboard_BifrostHostPopUp Bifrostpop1= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			Bifrostpop1.clickContiDevice();
			Thread.sleep(8000);

			
			//==================================Verifying Home page===============================================
			JavascriptExecutor js1 = (JavascriptExecutor)driver;
			DM_HomePage dmhomepage=new DM_HomePage(driver);	
			logger.log(LogStatus.PASS, "creating object for the DM home page sucessfully");
			
			dmhomepage.clk_DistrictDropDown();
			Thread.sleep(2000);
			dmhomepage.selDistrict();
			logger.log(LogStatus.INFO, "District id is selected from district dropdown");
			Thread.sleep(3000);
			dmhomepage.clk_SiteDropDown();
			Thread.sleep(2000);
			String location = dmhomepage.getSiteId();
			Thread.sleep(2000);
			dmhomepage.selSiteId();
		
			
			
			
			
			logger.log(LogStatus.INFO, "Site id is selected from Site droopdown");
			Thread.sleep(3000);
			js1.executeScript("window.scrollBy(1000,0)", "");
			Thread.sleep(4000);
			dmhomepage.clk_advancedSearchLnk();
			logger.log(LogStatus.PASS, "clicked on advanced search link in the DM Dashboard sucessfully");
			
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
			
			//String SiteNumber=dmhomepage.Sitenumber_DM();
			//logger.log(LogStatus.PASS, "location number is:"+location);

			Thread.sleep(2000);
			Advance_Search advSearch= new Advance_Search(driver);
			logger.log(LogStatus.INFO, "Created Object for Advance Search");
			
			
			
			String sqlQuery =  " Select Top 1 A.accountnumber " +
					  " From AccountOrderItem AOI " +
					  " INNER JOIN Site S ON S.SiteID = AOI.SiteID " +
					  " INNER JOIN StorageOrderItem SOI ON AOI.storageOrderItemID = SOI.storageOrderItemID " +
					  " INNER JOIN AccountOrder AO  ON AO.AccountOrderID = AOI.AccountOrderID " +
					  " INNER JOIN Account A ON A.AccountID = AO.AccountID " +
					  " join rentalunit ru on ru.rentalunitid=soi.rentalunitid " +
					  " join cltransaction clt on clt.accountorderitemid=aoi.accountorderitemid " +
					   " Where soi.VacateDate is null " +
					  " and soi.vacatenoticedate is null "+
					   "and s.sitenumber='"+location + "' " +
					  "group by  A.AccountID,A.accountnumber " +
					   "having sum(clt.amount + clt.discountamount)>0 " +
					  " order by 1";
			String accnum = DataBase_JDBC.executeSQLQuery(sqlQuery);
			Thread.sleep(15000);
			
			System.out.println(accnum);

					
			
			advSearch.enterAccNum(accnum);
			 js1.executeScript("window.scrollBy(0,-6000)", "");
			 logger.log(LogStatus.INFO, "Enter existing customer Account Number in Account field successfully");

			 Thread.sleep(3000);
			 advSearch.clickSearchAccbtn();
			 logger.log(LogStatus.INFO, "clicking on Search button successfully");
			 Thread.sleep(15000);
//===================================Customer DashBoard==============================================================
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			
			Thread.sleep(4000);
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
			
			//JavascriptExecutor js1 = (JavascriptExecutor)driver;
			js1.executeScript("window.scrollBy(1000,0)", "");
			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(4000);
			String spaceNum=cust_accdetails.spacenum_gettext();
			Thread.sleep(4000);
			
			
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			
			
			if(cust_accdetails.isEditLedgerDisplayed())
			{
				logger.log(LogStatus.FAIL, "Edit Ledger link is not displayed for DM Login",image1);
				logger.log(LogStatus.FAIL, "Edit Ledger link is not displayed for DM Login");
				Thread.sleep(3000);
				
			}
			else
			{
				logger.log(LogStatus.PASS, "Edit Ledger link is displayed for DM Login",image1);
				logger.log(LogStatus.PASS, "Edit Ledger link is not displayed for DM Login");
				Thread.sleep(3000);
				
			}
			
		
			driver.findElement(By.xpath("//a[contains(text(),' Edit General Ledger')]")).click();
			
 			Thread.sleep(5000);
 			EditLedger ledgeredit= new EditLedger(driver);
 			
 			//ledgeredit.ledger_dropdown(spaceNum, driver);
 			Thread.sleep(2000);
 			 	        
 			 String adjustmentcategory="select name from GLAccount where Adjustable='1'";

 			 ArrayList<String> adjustmentcategorylist=DataBase_JDBC.executeSQLQuery_List(adjustmentcategory);
 			
 			//==================== verify the Adjustment of ledger==============================
 			
 			JavascriptExecutor js = (JavascriptExecutor)driver;
 			List<WebElement> numledger= driver.findElements(By.xpath("//div[@id='glaGrid']//table/tbody[@role='rowgroup']/tr"));
 			
 			if(numledger.size()==adjustmentcategorylist.size()){
 				
 			ledgeredit.ledger_valueentery();
 			}else{
 				if(resultFlag.equals("pass"))
						resultFlag="fail";

					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Adjustment Category records are not Matching fron Database--- "+adjustmentcategorylist.size());
					logger.log(LogStatus.FAIL, "Adjustment Category records are not Matching from UI-------"+numledger.size());
					logger.log(LogStatus.INFO, "Adjustment Category records are not Matching ",image);
 				
 			}
 			Thread.sleep(2000);
 			ledgeredit.Enter_note(tabledata.get("Notes"));
 			Thread.sleep(4000);
 			
 			driver.findElement(By.xpath("(//input[@id='Adjustment'])[1]")).sendKeys("$2.00");
 			driver.findElement(By.xpath("(//input[@id='Adjustment'])[2]")).click();
 			
 			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
 			Thread.sleep(3000);
 		
 			
 			boolean warningMessage = driver.findElement(By.xpath("//div[@id='total-warning']")).isDisplayed();
 			
 			 boolean errormsg=driver.getPageSource().contains("New total can not be below zero");
 			 boolean errormsg2=driver.getPageSource().contains("Total amount does not equal zero");
 			
 			 if(warningMessage){

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
 			Thread.sleep(8000);
		 driver.findElement(By.xpath("//a[contains(text(),'Save')]")).click();
		 Thread.sleep(2000);
		 
		
		driver.findElement(By.id("enteredEmployeeNumber")).sendKeys(tabledata.get("UserName"));
		Thread.sleep(2000);
		
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
			e.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+e);
		}

	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "Miscellaneous","Verify_The_Edit_General_Ledger_DM_login" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Miscellaneous","Verify_The_Edit_General_Ledger_DM_login" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Miscellaneous","Verify_The_Edit_General_Ledger_DM_login" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}
*/